package com.example.final_project_23b11345.Model;

import java.util.ArrayList;

public class User {
    public ArrayList<SupportRequest> userRequests;
    public ArrayList<Parcel> completed, inTransit;
    private String name, address, emailAddress, phoneNumber;
    private FireBaseLatlng userAddressLocation;

    public User() {
    }

    public User(String name, String address, String emailAddress, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.completed = new ArrayList<>();
        this.inTransit = new ArrayList<>();
        this.userRequests = new ArrayList<>();
    }

    public ArrayList<SupportRequest> getUserRequests() {
        return userRequests;
    }

    public void setUserRequests(ArrayList<SupportRequest> userRequests) {
        this.userRequests = userRequests;
    }

    public FireBaseLatlng getUserAddressLocation() {
        return userAddressLocation;
    }

    public void setUserAddressLocation(FireBaseLatlng userAddressLocation) {
        this.userAddressLocation = userAddressLocation;
    }

    public ArrayList<Parcel> getCompleted() {
        return completed;
    }

    public void setCompleted(ArrayList<Parcel> completed) {
        this.completed = completed;
    }

    public ArrayList<Parcel> getInTransit() {
        return inTransit;
    }

    public void setInTransit(ArrayList<Parcel> inTransit) {
        this.inTransit = inTransit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}