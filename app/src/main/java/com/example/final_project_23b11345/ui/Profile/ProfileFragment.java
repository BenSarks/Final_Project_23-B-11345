package com.example.final_project_23b11345.ui.Profile;

import static com.example.final_project_23b11345.Utilities.Constants.PROFILE_PICTURE;
import static com.example.final_project_23b11345.Utilities.Constants.PROFILE_PICTURE_FORMAT;
import static com.example.final_project_23b11345.Utilities.Constants.TOAST_DURATION;
import static com.example.final_project_23b11345.Utilities.Constants.USER_ADDRESS;
import static com.example.final_project_23b11345.Utilities.Constants.USER_EMAIL;
import static com.example.final_project_23b11345.Utilities.Constants.USER_NAME;
import static com.example.final_project_23b11345.Utilities.Constants.USER_OBJECT;
import static com.example.final_project_23b11345.Utilities.Constants.USER_PHONE;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.final_project_23b11345.Interfaces.DataLoader;
import com.example.final_project_23b11345.R;
import com.example.final_project_23b11345.Utilities.DataManager;
import com.example.final_project_23b11345.Utilities.Notifier;
import com.example.final_project_23b11345.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements DataLoader {
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private EditText userName, userEmail, userPhone, userAddress;
    private MaterialTextView headerName;
    private MaterialButton edit_btn;
    private CircleImageView profile_pic;
    private FragmentProfileBinding binding;
    private Uri profilePic;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference;
    private boolean edit = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();
        this.loadAndSetData();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            profilePic = data.getData();
                            profile_pic.setImageURI(profilePic);
                            storageReference = FirebaseStorage.getInstance().getReference(PROFILE_PICTURE);
                            storageReference.putFile(profilePic);
                            Bitmap bitmap = null;
                            try {
                                Bitmap pic = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), profilePic);
                                DataManager.getInstance().setPic(pic);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
    }


    private void init() {
        this.headerName = this.binding.textHome;
        this.userName = this.binding.userFullName;
        this.userEmail = this.binding.userEmail;
        this.userPhone = this.binding.userPhone;
        this.userAddress = this.binding.userAddress;
        this.profile_pic = this.binding.userProfilePhoto;
        this.edit_btn = this.binding.editBtn;
        this.userEmail.setEnabled(false);
        this.userPhone.setEnabled(false);
        this.userAddress.setEnabled(false);
        this.userName.setEnabled(false);
        edit_btn.setOnClickListener(L -> editPress());
        this.userEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        this.userPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        this.userAddress.setInputType(InputType.TYPE_CLASS_TEXT);
    }


    private void setNewProfilePio() {
        //Create Intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        //Launch activity to get result
        someActivityResultLauncher.launch(intent);
    }


    private void setValuesFromDB() {
        headerName.setText(R.string.Profile_pic);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(USER_OBJECT).child(USER_NAME);
        databaseReference.get().addOnSuccessListener(dataSnapshot -> userName.setText(dataSnapshot.getValue(String.class)));
        databaseReference = FirebaseDatabase.getInstance().getReference(USER_OBJECT).child(USER_ADDRESS);
        databaseReference.get().addOnSuccessListener(dataSnapshot -> userAddress.setText(dataSnapshot.getValue(String.class)));
        databaseReference = FirebaseDatabase.getInstance().getReference(USER_OBJECT).child(USER_EMAIL);
        databaseReference.get().addOnSuccessListener(dataSnapshot -> userEmail.setText(dataSnapshot.getValue(String.class)));
        databaseReference = FirebaseDatabase.getInstance().getReference(USER_OBJECT).child(USER_PHONE);
        databaseReference.get().addOnSuccessListener(dataSnapshot -> userPhone.setText(dataSnapshot.getValue(String.class)));
        try {
            storageReference = FirebaseStorage.getInstance().getReference(PROFILE_PICTURE);
            File picFile = File.createTempFile(PROFILE_PICTURE, PROFILE_PICTURE_FORMAT);
            storageReference.getFile(picFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap pic = BitmapFactory.decodeFile(picFile.getAbsolutePath());
                    profile_pic.setImageBitmap(pic);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void editPress() {
        if (!edit) {
            edit_btn.setText(R.string.save);
            this.userEmail.setEnabled(true);
            this.userPhone.setEnabled(true);
            this.userAddress.setEnabled(true);
            this.profile_pic.setOnClickListener(L -> setNewProfilePio());
            edit = true;
        } else {
            Notifier.getInstance().toast(getString(R.string.updated_user_info), TOAST_DURATION);
            this.profile_pic.setOnClickListener(null);
            edit_btn.setText(R.string.edit);
            this.userEmail.setEnabled(false);
            this.userPhone.setEnabled(false);
            this.userAddress.setEnabled(false);
            DataManager.getInstance().updateUserInfo(userAddress.getText().toString(), userPhone.getText().toString(), userEmail.getText().toString());
            edit = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void loadAndSetData() {
        if (!DataManager.getInstance().isUserAvailable()) {
            setValuesFromDB();
        } else {
            userName.setText(DataManager.getInstance().getUserName());
            userAddress.setText(DataManager.getInstance().getUserAddress());
            userEmail.setText(DataManager.getInstance().getUserEmail());
            userPhone.setText(DataManager.getInstance().getUserPhone());
            profile_pic.setImageBitmap(DataManager.getInstance().getPic());
        }
    }
}