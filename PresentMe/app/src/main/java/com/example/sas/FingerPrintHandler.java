package com.example.sas;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.M)
public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback{

    private Context context;
    public FingerPrintHandler(Context context)
    {
        this.context = context;
    }

    void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject)
    {
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal , 0,this,null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        Toast.makeText(context,"There is Some Error.",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
//        -------------------------------------------------
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setTitle("Alert");
        adb.setMessage("Authentication Failed");
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               System.exit(0);
            }
        });
        adb.show();
//        -------------------------------------------------
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        Toast.makeText(context,helpString,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
//        --------------------------------------------------
        Intent myIntent = new Intent(context, Login.class);
        context.startActivity(myIntent);
//        ----------------------------------------------------
    }

}
