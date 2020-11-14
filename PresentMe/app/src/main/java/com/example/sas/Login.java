package com.example.sas;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Login extends AppCompatActivity {

    EditText id = null;
    EditText pass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.b71cO2);
        pass = findViewById(R.id.Oaos3L);

        SharedPreferences prefs = getSharedPreferences(AttributeData.FILENAME, MODE_PRIVATE);
        int LoginFlag = prefs.getInt("loginFlag", 0);
        if (LoginFlag != -1) {
            Intent intent = new Intent(Login.this, Chirp.class);
            Login.this.startActivity(intent);
        }
    }

    public void sendData(View view) {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        String kUQNGF = telephonyManager.getDeviceId();

        String b71cO2 = id.getText().toString().toUpperCase();
        String Oaos3L = pass.getText().toString();

//        --------------------------------------------------
        BackgroundTask backgroundTask = new BackgroundTask(Login.this);
        backgroundTask.execute(b71cO2, Oaos3L, kUQNGF);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder adb = new AlertDialog.Builder(Login.this);
        adb.setTitle("Exit");
        adb.setMessage("Are you sure to exit?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Login.this.finishAffinity();
            }
        });
        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        adb.show();
    }
}
