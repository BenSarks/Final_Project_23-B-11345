package com.example.final_project_23b11345.ui.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.final_project_23b11345.R;
import com.example.final_project_23b11345.Utilities.Notifier;
import com.example.final_project_23b11345.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private EditText email_EditText, password_EditText;

    private FirebaseAuth authentication;
    private FragmentLoginBinding binding;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        authentication = FirebaseAuth.getInstance();
        View root = binding.getRoot();
        init();
        return root;

    }

    private void init() {
        this.email_EditText = this.binding.loginEmail;
        this.password_EditText = this.binding.loginPassword;
        Button login_Button = this.binding.loginButton;
        login_Button.setOnClickListener(L ->login());
    }

    private void login() {
        String email = email_EditText.getText().toString().trim();
        String password = password_EditText.getText().toString().trim();
        if(email.isEmpty()||password.isEmpty()){
             Notifier.getInstance().toast("Fill all fields",200);
        }else{
            authentication.signInWithEmailAndPassword(email,password).
                    addOnSuccessListener(authResult -> {
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_menu);
                        navController.navigate(R.id.nav_packages);
                    }).addOnFailureListener(e -> Notifier.getInstance().toast("Incorrect info",500));
        }
    }
}