package com.example.final_project_23b11345;

import android.app.Application;

import com.example.final_project_23b11345.Utilities.DataManager;
import com.example.final_project_23b11345.Utilities.Notifier;
import com.google.firebase.FirebaseApp;

public class App extends Application {

    public void onCreate() {
        Notifier.init(this);
        FirebaseApp.initializeApp(this);
        DataManager.init(this);
        super.onCreate();
    }
}