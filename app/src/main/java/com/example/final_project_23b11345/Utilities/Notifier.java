package com.example.final_project_23b11345.Utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

public class Notifier {
    @SuppressLint({"StaticFieldLeak"})
    private static Notifier instance = null;
    private static Vibrator vibrator;
    private final Context context;

    private Notifier(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new Notifier(context);
            vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        }

    }

    public static Notifier getInstance() {
        return instance;
    }

    public void toast(String text, int length) {
        Toast.makeText(this.context, text, length).show();
    }

    @SuppressLint({"ObsoleteSdkInt"})
    public void vibrate(long length) {
        if (VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(length, -1));
        } else {
            vibrator.vibrate(length);
        }

    }
}

