package com.example.sas;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        System.out.println("__CUSTOM >> " + DeviceUtils.checkAppSignature(this));
        //for root checking
        if (checkRootMethod1() || checkRootMethod2() || checkRootMethod3()) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Alert");
            adb.setMessage("This device is rooted! You cannot access this app!");
            adb.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.finish();
                }
            });
            adb.show();
        }

        //check permissions
        checkPermissions();

        //sharedprefs
        File f = new File("/data/data/" + getPackageName() + "/shared_prefs/" + AttributeData.FILENAME + ".xml");

        if (!f.exists()) {
            SharedPreferences.Editor editor = getSharedPreferences(AttributeData.FILENAME, MODE_PRIVATE).edit();
            editor.putInt("loginFlag", -1);
            editor.apply();

            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Alert");
            adb.setMessage("Carefully enter ID and password. Once this is entered, you won't be allowed to change it. Don't Login from any other devices and don't enter credentials of any other account from your device. ");
            adb.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent myIntent = new Intent(MainActivity.this, FingerPrintAuth.class);
                    MainActivity.this.startActivity(myIntent);
                }
            });
            adb.show();
        } else {
            SharedPreferences prefs = getSharedPreferences(AttributeData.FILENAME, MODE_PRIVATE);
            int loginFlag = prefs.getInt("loginFlag", 0);
            if (loginFlag != -1) {
                String b71cO2 = prefs.getString("SI", "");
                BackgroundTask3 task3 = new BackgroundTask3(MainActivity.this);
                task3.execute(b71cO2);
            } else {
                Intent myIntent = new Intent(MainActivity.this, FingerPrintAuth.class);
                startActivity(myIntent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle("Exit");
        adb.setMessage("Are you sure to exit?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finishAffinity();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermissions() {
        if (checkSelfPermission(Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.USE_FINGERPRINT)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.RECORD_AUDIO, Manifest.permission.USE_FINGERPRINT},
                    1000);
            return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                checkPermissions();
            }
            return;
        }
    }

    private static boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }

}

