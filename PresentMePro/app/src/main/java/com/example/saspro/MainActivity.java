package com.example.saspro;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText iTYz2f = null;
    EditText KoTetn = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("__CUSTOM >> " + DeviceUtils.checkAppSignature(this));

        iTYz2f = findViewById(R.id.iTYz2f);
        KoTetn = findViewById(R.id.KoTetn);

        checkPermissions();
    }

    public void sendData(View view) {
        String ITYz2f = iTYz2f.getText().toString().toUpperCase();
        String koTetn = KoTetn.getText().toString();

        BackgroundTask backgroundTask = new BackgroundTask(MainActivity.this);
        backgroundTask.execute(ITYz2f, koTetn);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermissions() {
        if (checkSelfPermission(Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO},
                    1000);
            return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                checkPermissions();
            }
            return;
        }
    }
}
