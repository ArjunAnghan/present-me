package com.example.sas;

import android.Manifest;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class FingerPrintAuth extends AppCompatActivity {

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print_auth);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            if (fingerprintManager.isHardwareDetected()) {
                if (keyguardManager.isKeyguardSecure()) {
                    if (fingerprintManager.hasEnrolledFingerprints()) {
                        FingerPrintHandler fingerprintHandler = new FingerPrintHandler(this);
                        fingerprintHandler.startAuth(fingerprintManager, null);

                    } else {
//                        -----------------------
                        AlertDialog.Builder adb = new AlertDialog.Builder(this);
                        adb.setTitle("Alert");
                        adb.setMessage("Please set up fingerprint");
                        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);

                            }
                        });
                        adb.show();
//                    --------------------
                    }
                } else {
                    AlertDialog.Builder adb = new AlertDialog.Builder(this);
                    adb.setTitle("Alert");
                    adb.setMessage("Please set up a lockscreen");
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    });
                    adb.show();
                }
            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setTitle("Alert");
                adb.setMessage("Please grant permission");
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
                adb.show();
            } else {
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setTitle("Alert");
                adb.setMessage("Fingerprint Sensor not detected");
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
                adb.show();
            }
        } else {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Alert");
            adb.setMessage("Your Android Version Is Much Lower For It.");
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    FingerPrintAuth.this.finish();
                }
            });
            adb.show();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder adb = new AlertDialog.Builder(FingerPrintAuth.this);
        adb.setTitle("Exit");
        adb.setMessage("Are you sure to exit?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                FingerPrintAuth.this.finishAffinity();
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

