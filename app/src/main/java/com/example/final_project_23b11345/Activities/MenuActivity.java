package com.example.final_project_23b11345.Activities;

import static com.example.final_project_23b11345.Utilities.Constants.USER_EMAIL;
import static com.example.final_project_23b11345.Utilities.Constants.USER_NAME;
import static com.example.final_project_23b11345.Utilities.Constants.USER_OBJECT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.final_project_23b11345.R;
import com.example.final_project_23b11345.Utilities.DataManager;
import com.example.final_project_23b11345.databinding.ActivityMenuBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MenuActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseUser user;
    private MaterialTextView drawerUserName, drawerUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth.getInstance();
        DataManager.init(this);
        com.example.final_project_23b11345.databinding.ActivityMenuBinding binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMenu.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.nav_header_menu, binding.navView, false);
        this.drawerUserName =  navigationView.getHeaderView(0).findViewById(R.id.drawer_textview_name);
        this.drawerUserEmail = navigationView.getHeaderView(0).findViewById(R.id.drawer_textview_email);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_packages, R.id.nav_support)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        // handel each option
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_profile) {
                navController.navigate(R.id.nav_profile);
            } else if (itemId == R.id.nav_packages) {
                navController.navigate(R.id.nav_packages);
            } else if (itemId == R.id.nav_support) {
                navController.navigate(R.id.nav_support);
            } else if (itemId == R.id.nav_additional_menu_logout) {
                navigateToLoginFragment(true);
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            navigateToLoginFragment(false);
        }
        setValues();
    }

    private void setValues() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(USER_OBJECT).child(USER_NAME);
        databaseReference.get().addOnSuccessListener(dataSnapshot -> drawerUserName.setText(dataSnapshot.getValue(String.class)));
        databaseReference = FirebaseDatabase.getInstance().getReference(USER_OBJECT).child(USER_EMAIL);
        databaseReference.get().addOnSuccessListener(dataSnapshot -> drawerUserEmail.setText(dataSnapshot.getValue(String.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_Dark_Mode) {
            onNightModeButtonClick();
            return true;
        } else if (itemId == R.id.action_english) {
            LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("en");
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(appLocale.toLanguageTags()));

            return true;
        } else if (itemId == R.id.action_hebrew) {
            LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("iw");
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(appLocale.toLanguageTags()));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToLoginFragment(boolean signOut) {
        if (signOut) {
            FirebaseAuth.getInstance().signOut();
            user = null;
        }
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        navController.navigate(R.id.nav_login);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
    }
    public void onNightModeButtonClick() {
        if (DataManager.getInstance().changeMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}