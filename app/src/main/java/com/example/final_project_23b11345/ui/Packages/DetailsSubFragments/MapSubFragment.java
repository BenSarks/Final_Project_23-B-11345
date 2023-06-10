package com.example.final_project_23b11345.ui.Packages.DetailsSubFragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.final_project_23b11345.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapSubFragment extends Fragment {
    LatLngBounds bounds;
    MarkerOptions markerOptions;
    SupportMapFragment supportMapFragment;

    boolean isItDelivery;

    LatLng  parcel,user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_packages_map_sub, container, false);
        this.supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        if (this.supportMapFragment != null) {
            this.supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    try {
                        // Customise the styling of the base map using a JSON object defined
                        // in a raw resource file.
                        boolean success = googleMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        requireContext(), R.raw.style_json));
                        if (!success) {
                            Log.e("TAG", "Style parsing failed.");
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.e("TAG", "Can't find style. Error: ", e);
                    }

                    if (!isItDelivery) {
                        if(parcel!=null){
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(parcel);
                            googleMap.addMarker(markerOptions);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(parcel, 15));
                        }
                    } else {
                        if(user!=null){
                            googleMap.addMarker(new MarkerOptions().position(user).title(String.valueOf(R.string.home)));
                            googleMap.addMarker(new MarkerOptions().position(parcel).title(String.valueOf(R.string.parcel_loaction)));
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(user);
                            builder.include(parcel);
                            LatLngBounds bounds = builder.build();
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                        }
                    }
                }
            });
        }
        return view;

    }

    private void showPathOrPoint(LatLng user, LatLng parcel, boolean isItDelivery) {
//
//        GeoApiContext geoApiContext = new GeoApiContext.Builder()
//                .apiKey("AIzaSyDBt6_D8o0dKb_lFr-UYYXjaYwn15TbfyM")
//                .build();
//        DirectionsApiRequest request = DirectionsApi.getDirections(geoApiContext, parcel.latitude + "," + parcel.longitude, user.latitude + "," + user.longitude);
//        request.mode(TravelMode.DRIVING);
//        request.setCallback(new PendingResult.Callback<DirectionsResult>() {
//            @Override
//            public void onResult(DirectionsResult result) {
//                if (result.routes != null && result.routes.length > 0) {
//                    DirectionsRoute route = result.routes[0];
//
//                    if (route.legs != null) {
//                        for (int i = 0; i < route.legs.length; i++) {
//                            if (route.legs[i].steps != null) {
//                                for (int j = 0; j < route.legs[i].steps.length; j++) {
//                                    com.google.maps.model.LatLng stepLatLng = route.legs[i].steps[j].startLocation;
//                                    LatLng latLng = new LatLng(stepLatLng.lat, stepLatLng.lng);
//                                    points.add(latLng);
//                                }
//                            }
//                        }
//                        for (LatLng latLng : points
//                        ) {
//                            Log.d("onResult: ", latLng.toString());
//                        }
//                        PolylineOptions polylineOptions = new PolylineOptions();
//                        for (LatLng point : points) {
//                            polylineOptions.add(point);
//                        }
//                        polylineOptions.width(12);
//                        polylineOptions.clickable(true);
//                        polylineOptions.color(Color.RED);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable e) {
//
//            }
//        });
    }

    public void showLocation(LatLng user, LatLng parcel, boolean isItDelivery) {
        this.user = user;
        this.parcel = parcel;
        this.isItDelivery = isItDelivery;
    }

}