package com.example.final_project_23b11345.ui.Packages.TabMainFragments;

import static com.example.final_project_23b11345.Utilities.Constants.PARCEL;
import static com.example.final_project_23b11345.Utilities.Constants.USER_COMPLETED;
import static com.example.final_project_23b11345.Utilities.Constants.USER_OBJECT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_23b11345.Adapters.ProcessedPackagesAdapter;
import com.example.final_project_23b11345.Interfaces.DataLoader;
import com.example.final_project_23b11345.Interfaces.DetailsCallback;
import com.example.final_project_23b11345.Model.Parcel;
import com.example.final_project_23b11345.R;
import com.example.final_project_23b11345.Utilities.DataManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProcessedPackageFragment extends Fragment implements DataLoader  {
    private  ProcessedPackagesAdapter processedPackagesAdapter;
    private  RecyclerView recyclerView;
    private final DetailsCallback detailsCallback = this::setDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_processed_package, container, false);
        recyclerView = view.findViewById(R.id.processed_Packages_RecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        this.loadAndSetData();
        return view;
    }
    private void setDetails(Parcel parcel){
        Bundle bundle =  getArguments();
        if (bundle!=null){
            bundle.putParcelable(PARCEL, parcel);
        }
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_menu);
        navController.navigate(R.id.packageDetailsFragment,bundle);
    }
    @Override
    public void loadAndSetData(){
        if(!DataManager.getInstance().isUserAvailable()) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(USER_OBJECT).child(USER_COMPLETED);
            databaseReference.get().addOnSuccessListener(dataSnapshot -> {
                ArrayList<Parcel> packages = new ArrayList<>();
                for (DataSnapshot parcelSnapshot : dataSnapshot.getChildren()) {
                    Parcel parcel = parcelSnapshot.getValue(Parcel.class);
                    packages.add(parcel);
                }
                setAdapter(packages);
            });
        }else{
            setAdapter(DataManager.getInstance().getMyUser().completed);
        }
    }


    private void setAdapter( ArrayList<Parcel> packages){
        processedPackagesAdapter =new ProcessedPackagesAdapter(requireContext(), packages);
        processedPackagesAdapter.setDetailsCallback(detailsCallback);
        recyclerView.setAdapter(processedPackagesAdapter);
    }
}
