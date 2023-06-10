package com.example.final_project_23b11345.ui.Packages.DetailsSubFragments;

import static com.example.final_project_23b11345.Utilities.Constants.PARCEL;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.final_project_23b11345.Model.Parcel;
import com.example.final_project_23b11345.R;
import com.example.final_project_23b11345.Utilities.DataManager;
import com.example.final_project_23b11345.databinding.FragmentPackageDetailsBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textview.MaterialTextView;


public class DetailsSubFragment extends Fragment {

private MaterialTextView sender_Name, delivery_Method, package_Id,order_Date,package_Status,delivery_Date,delivery_Address,ordering_Date;

    private FragmentPackageDetailsBinding binding;

    private LottieAnimationView lottieAnimationView;

    private FragmentContainerView fragmentContainerView;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                binding = FragmentPackageDetailsBinding.inflate(inflater, container, false);
        MapSubFragment packagesMapSubFragment = new MapSubFragment();
        init();
        Bundle bundle = getArguments();
        if (bundle != null) {
            Parcel parcel = bundle.getParcelable(PARCEL);
            if (parcel != null) {
                setParcelDetails(parcel);
                if(parcel.getPackageStatus().getValue()>4){
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    fragmentContainerView.setVisibility(View.GONE);
                }else{
                    if (parcel.getPackageStatus().getValue()<5&&parcel.getPackageStatus().getValue()>2){
                        LatLng userLatlang = DataManager.getInstance().getMyUser().getUserAddressLocation().transformToLatlng();
                        if(userLatlang!=null){
                            packagesMapSubFragment.showLocation(userLatlang, parcel.getLocation().transformToLatlng(),parcel.getDeliveryMethod()== Parcel.DeliveryMethod.EXPRESS_COURIER_SHIPPING);
                        }else{
                            packagesMapSubFragment.showLocation(null, parcel.getLocation().transformToLatlng(),parcel.getDeliveryMethod()== Parcel.DeliveryMethod.EXPRESS_COURIER_SHIPPING);
                        }
                    }
                }
            }
        }
        requireActivity().getSupportFragmentManager().beginTransaction().add(binding.subFragmentMapview.getId(), packagesMapSubFragment).commit();
        return binding.getRoot();
    }

    private void init(){
        this.lottieAnimationView = binding.completedParcelAnimation;
        this.fragmentContainerView =binding.subFragmentMapview;
        this.sender_Name = binding.textviewSenderName;
        this.delivery_Method = binding.textviewPackageMethod;
        this.package_Id = binding.textviewPackageId;
        this.order_Date = binding.textviewPackageDate;
        this.package_Status = binding.textviewPackageStatus;
        this.ordering_Date = binding.textviewPackageDate;
        this.delivery_Address = binding.textviewDeliveryAddress;
        this.delivery_Date = binding.textviewRecivedDate;
    }
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setParcelDetails(Parcel parcel){
        init();
        this.sender_Name.setText(parcel.getSendersName());
        this.delivery_Method.setText(requireContext().getResources().getStringArray(R.array.parcel_methods)[parcel.getDeliveryMethod().getValue()]);
        this.package_Id.setText(parcel.getTrackingNumber());
        this.delivery_Address.setText(parcel.getDeliveryAddress());
        this.order_Date.setText(parcel.getOrderDate());
        this.package_Status.setText(requireContext().getResources().getStringArray(R.array.parcel_status)[parcel.getPackageStatus().getValue()]);
        this.ordering_Date.setText(parcel.getOrderDate());
        if (parcel.getReceivedDate() == null ){
            this.delivery_Date.setText(R.string.soon);
        }else{
            this.delivery_Date.setText(parcel.getReceivedDate());

        }
    }
}