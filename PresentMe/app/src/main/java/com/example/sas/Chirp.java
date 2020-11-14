package com.example.sas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import io.chirp.chirpsdk.ChirpSDK;
import io.chirp.chirpsdk.interfaces.ChirpEventListener;
import io.chirp.chirpsdk.models.ChirpError;
import pl.droidsonroids.gif.GifImageView;

public class Chirp extends AppCompatActivity {

    SharedPreferences prefs = null;
    ImageView normalImage = null;
    GifImageView gif = null;

    public ChirpSDK chirpConnect = null;
    String SI = null;
    String group = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chirp);

        normalImage = findViewById(R.id.normalImage);
        gif = findViewById(R.id.gifImage);

        prefs = getSharedPreferences(AttributeData.FILENAME, MODE_PRIVATE);
        SI = prefs.getString("SI", null);
        group = prefs.getString("group", null);

        //chirp connect
        chirpConnect = new ChirpSDK(this, AttributeData.APP_KEY, AttributeData.APP_SECRET);
        ChirpError error = chirpConnect.setConfig(AttributeData.APP_CONFIG);
        if (error.getCode() == 0) {
            chirpConnect.setListener(connectEventListener);
        } else {
            Chirp.this.finish();
        }

        chirpConnect.start();
    }

    ChirpEventListener connectEventListener = new ChirpEventListener() {
        @Override
        public void onSystemVolumeChanged(float v, float v1) {
        }

        @Override
        public void onStateChanged(int i, int i1) {
        }

        @Override
        public void onSent(@NotNull byte[] bytes, int i) {
        }

        @Override
        public void onSending(@NotNull byte[] bytes, int i) {
        }

        @Override
        public void onReceiving(int i) {
            normalImage.setVisibility(View.INVISIBLE);
            Toast.makeText(Chirp.this, "Listening..", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onReceived(byte[] data, int j) {
            gif.setVisibility(View.INVISIBLE);
            try {
                normalImage.setVisibility(View.VISIBLE);
            } catch (Exception E) {
                E.printStackTrace();
            }

            String hexData = "null";
            if (data != null) {
                hexData = new String(data);
            }

            BackgroundTask2 backgroundTask2 = new BackgroundTask2(Chirp.this);
            backgroundTask2.execute(hexData, SI, group);

            Toast.makeText(Chirp.this, hexData, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        chirpConnect.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            chirpConnect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder adb = new AlertDialog.Builder(Chirp.this);
        adb.setTitle("Exit");
        adb.setMessage("Are you sure to exit?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Chirp.this.finishAffinity();
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

