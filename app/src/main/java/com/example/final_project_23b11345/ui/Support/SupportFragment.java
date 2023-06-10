package com.example.final_project_23b11345.ui.Support;

import static com.example.final_project_23b11345.Utilities.Constants.SUPPORT_REQUESTS;
import static com.example.final_project_23b11345.Utilities.Constants.TOAST_DURATION;
import static com.example.final_project_23b11345.Utilities.Constants.USER_COMPLETED;
import static com.example.final_project_23b11345.Utilities.Constants.USER_IN_TRANSIT;
import static com.example.final_project_23b11345.Utilities.Constants.USER_OBJECT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_23b11345.Adapters.RequestAdapter;
import com.example.final_project_23b11345.Interfaces.DataLoader;
import com.example.final_project_23b11345.Model.Parcel;
import com.example.final_project_23b11345.Model.SupportRequest;
import com.example.final_project_23b11345.R;
import com.example.final_project_23b11345.Utilities.DataManager;
import com.example.final_project_23b11345.Utilities.Notifier;
import com.example.final_project_23b11345.databinding.FragmentSupportBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class SupportFragment extends Fragment implements DataLoader {
    private FragmentSupportBinding binding;
    private AppCompatSpinner causeSpinner, packagesSpinner;
    private ArrayAdapter<String> causeAdapter, packagesAdapter;
    private ArrayList<String> trackingNumbersList;
    private TextInputEditText supportRequest;
    private int causeSpinnerPosition = -1, packagesSpinnerPosition = -1;
    private RequestAdapter requestAdapter;
    private RecyclerView recyclerView;
    private String cause, idNumber;
    private ArrayList<SupportRequest> supportRequests;
    private LinearLayoutManager linearLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSupportBinding.inflate(inflater, container, false);
        init();
        loadAndSetData();
        return binding.getRoot();
    }

    private void init() {
        this.causeSpinner = binding.possibleSupportSpinner;
        this.packagesSpinner = binding.possibleSupportSpinnerPackages;
        this.causeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.support_options));
        this.trackingNumbersList = new ArrayList<>();
        this.packagesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, trackingNumbersList);
        MaterialButton submit_btn = binding.supportButton;
        this.supportRequest = binding.supportText;
        this.causeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                causeSpinnerPosition = position;
                cause = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                causeSpinnerPosition = -1;
            }
        });
        this.packagesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                packagesSpinnerPosition = position;
                idNumber = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                packagesSpinnerPosition = -1;
            }
        });
        submit_btn.setOnClickListener(L -> submitPress());
        loadArrayListFromFireBase();
    }

    private void loadArrayListFromFireBase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(USER_OBJECT).child(SUPPORT_REQUESTS);
        databaseReference.get().addOnSuccessListener(dataSnapshot -> {
            supportRequests = new ArrayList<>();
            for (DataSnapshot parcelSnapshot : dataSnapshot.getChildren()) {
                SupportRequest supportRequest = parcelSnapshot.getValue(SupportRequest.class);
                supportRequests.add(supportRequest);
            }
            recyclerView = binding.requestsRecyclerView;
            linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            requestAdapter = new RequestAdapter(requireContext(), supportRequests);
            recyclerView.setAdapter(requestAdapter);
        });
    }


    private void setValues() {
        causeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        causeSpinner.setAdapter(causeAdapter);
        causeSpinner.setSelection(packagesAdapter.getPosition(String.valueOf(R.string.cause)));
        packagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        packagesSpinner.setAdapter(packagesAdapter);
    }


    private void submitPress() {
        this.causeSpinner.setSelection(0);
        if (this.packagesSpinnerPosition != -1 && this.causeSpinnerPosition != -1 && !(Objects.requireNonNull(this.supportRequest.getText()).toString().isEmpty())) {
            SupportRequest newSupportRequest = new SupportRequest(idNumber, cause, supportRequest.getText().toString());
            newSupportRequest.setInProgress(true);
            newSupportRequest.setRequestStatus(SupportRequest.RequestStatus.SENT);
            supportRequests.add(newSupportRequest);
            requestAdapter.notifyItemInserted(supportRequests.size() - 1);
            if (DataManager.getInstance().isUserAvailable()) {
                DataManager.getInstance().getMyUser().userRequests.add(newSupportRequest);
                boolean secondCheck = true;
                for (Parcel parcel : DataManager.getInstance().getMyUser().inTransit
                ) {
                    if (parcel.getTrackingNumber().equals(idNumber)) {
                        parcel.setSupportRequested(true);
                        secondCheck = false;
                    }
                }
                if (secondCheck) {
                    for (Parcel parcel : DataManager.getInstance().getMyUser().completed
                    ) {
                        if (parcel.getTrackingNumber().equals(idNumber)) {
                            parcel.setSupportRequested(true);
                            secondCheck = false;
                        }
                    }
                }
            }
            DataManager.getInstance().saveToFireBase();
            this.packagesAdapter.remove(this.packagesAdapter.getItem(this.packagesSpinnerPosition));
            this.packagesSpinnerPosition = -1;
            this.packagesSpinner.setSelection(0);
            supportRequest.setText("");
            supportRequest.setHint(R.string.write);
            Notifier.getInstance().toast(getString(R.string.added_support), TOAST_DURATION);
        } else {
            Notifier.getInstance().toast(getString(R.string.added_support_fail), TOAST_DURATION);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
    }

    @Override
    public void loadAndSetData() {
        if (!DataManager.getInstance().isUserAvailable()) {
            loadArraylistFromDB(USER_IN_TRANSIT);
            loadArraylistFromDB(USER_COMPLETED);
            packagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            packagesSpinner.setAdapter(packagesAdapter);
        } else {
            for (Parcel parcel : DataManager.getInstance().getMyUser().inTransit) {
                if (!parcel.isSupportRequested()) {
                    trackingNumbersList.add(parcel.getTrackingNumber());
                }
            }
            for (Parcel parcel : DataManager.getInstance().getMyUser().completed) {
                if (!parcel.isSupportRequested()) {
                    trackingNumbersList.add(parcel.getTrackingNumber());
                }
            }
        }
        setValues();
    }

    private void loadArraylistFromDB(String childPath) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(USER_OBJECT).child(childPath);
        databaseReference.get().addOnSuccessListener(dataSnapshot -> {
            ArrayList<Parcel> packages = new ArrayList<>();
            for (DataSnapshot parcelSnapshot : dataSnapshot.getChildren()) {
                Parcel parcel = parcelSnapshot.getValue(Parcel.class);
                packages.add(parcel);
            }
            for (Parcel parcel : packages) {
                if (!parcel.isSupportRequested()) {
                    trackingNumbersList.add(parcel.getTrackingNumber());
                }
            }
        });
    }
}