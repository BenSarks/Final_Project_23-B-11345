package com.example.final_project_23b11345.Utilities;

import static com.example.final_project_23b11345.Utilities.Constants.PROFILE_PICTURE;
import static com.example.final_project_23b11345.Utilities.Constants.PROFILE_PICTURE_FORMAT;
import static com.example.final_project_23b11345.Utilities.Constants.USER_OBJECT;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.final_project_23b11345.Model.Parcel;
import com.example.final_project_23b11345.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class DataManager {
    //the following class was created in order to minimize and centralize the requests to the firebase and storage
    private static DataManager instance = null;
    private User myUser;
    private DatabaseReference databaseReference;
    private boolean dark_mode = false;
    private Bitmap pic =null;

    public Bitmap getPic() {
        return pic;
    }
    public void setPic(Bitmap pic) {
        this.pic = pic;
    }
    private StorageReference storageReference;

    public boolean changeMode(){
        this.dark_mode = !dark_mode;
        return this.dark_mode;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
    }

    public static DataManager getInstance() {
        return instance;
    }

    public DataManager(Context context) {
        this.databaseReference = FirebaseDatabase.getInstance().getReference(USER_OBJECT);
        databaseReference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                myUser = dataSnapshot.getValue(User.class);
            }
        }).addOnFailureListener(e -> myUser=null);

        try {
            storageReference = FirebaseStorage.getInstance().getReference(PROFILE_PICTURE);
            File picFile = File.createTempFile(PROFILE_PICTURE, PROFILE_PICTURE_FORMAT);
            storageReference.getFile(picFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    pic = BitmapFactory.decodeFile(picFile.getAbsolutePath());
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUserAvailable(){
        return myUser != null;
    }
    public User getMyUser() {
        return myUser;
    }
    public String getUserName() {
        return myUser.getName();

    } public String getUserAddress() {
        return myUser.getAddress();

    } public String getUserPhone() {
        return myUser.getPhoneNumber();

    } public String getUserEmail() {
        return myUser.getEmailAddress();

    } public ArrayList<Parcel>  getUserInTransitArrayList() {
        return myUser.inTransit;

    } public ArrayList<Parcel>  getUserCompletedArrayList() {
        return myUser.completed;
    }
    public int getInTransitSize(){
        return myUser.inTransit.size();
    }
    public int getCompletedSize(){
        return myUser.completed.size();
    }
    public Parcel getCompletedAtIndex(int index){
        if(myUser.completed.size()>index){
            return myUser.completed.get(index);
        }
        return null;
    }
    public Parcel getInTransitAtIndex(int index){
        if(myUser.inTransit.size()>index){
            return myUser.inTransit.get(index);
        }
        return null;
    }
    public void saveToFireBase(){
        databaseReference.setValue(myUser);
    }

    public void updateUserInfo(String address, String phone, String Email){
        if(!Objects.equals(myUser.getAddress(), address)){
            myUser.setAddress(address);
            if (myUser.inTransit != null) {
                for (Parcel parcel : myUser.inTransit) {
                    if(parcel.getDeliveryMethod() == Parcel.DeliveryMethod.EXPRESS_COURIER_SHIPPING)
                        parcel.setDeliveryAddress(address);// update each parcel that in transit as well
                }
            }
        }
        if(!Objects.equals(myUser.getPhoneNumber(), phone)){
            myUser.setPhoneNumber(phone);
        } if(!Objects.equals(myUser.getPhoneNumber(), Email)){
            myUser.setEmailAddress(Email);
        }
        saveToFireBase();
    }
}
