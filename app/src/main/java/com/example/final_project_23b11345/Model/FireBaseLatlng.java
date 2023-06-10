package com.example.final_project_23b11345.Model;

import com.google.android.gms.maps.model.LatLng;

public class FireBaseLatlng {

        private double latitude;
        private double longitude;

        public FireBaseLatlng() {
        }

        public FireBaseLatlng(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LatLng transformToLatlng(){
            return (new LatLng(latitude,longitude));
        }

}
