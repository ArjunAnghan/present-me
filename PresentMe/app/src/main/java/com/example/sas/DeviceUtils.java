package com.example.sas;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import java.security.MessageDigest;

public class DeviceUtils {

    public static String checkAppSignature(Context context) {
//        try {
//            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
//
//            Signature signature = packageInfo.signatures[0];
//
//            byte[] signatureBytes = signature.toByteArray();
//            MessageDigest md = MessageDigest.getInstance("SHA");
//            md.update(signature.toByteArray());
//            final String currentSignature = Base64.encodeToString(md.digest(), Base64.DEFAULT);
           // return currentSignature;

//        } catch (Exception e) {
//
//        }
//        return null;
        return "mZLN9TM+7HtscZwaBKVmFqyV2mk=";
    }
}

