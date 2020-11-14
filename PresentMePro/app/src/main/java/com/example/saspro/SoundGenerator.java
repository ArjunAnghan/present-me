package com.example.saspro;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import io.chirp.chirpsdk.ChirpSDK;
import io.chirp.chirpsdk.interfaces.ChirpEventListener;
import io.chirp.chirpsdk.models.ChirpError;

public class SoundGenerator extends AppCompatActivity {

    Button pRLA2p = null;
    Button Kre2x2 = null;

    String LOTS2f = null;
    public ChirpSDK chirpConnect = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_generator);

        pRLA2p = findViewById(R.id.pRLA2p);
        Kre2x2 = findViewById(R.id.Kre2x2);

        SharedPreferences prefs = getSharedPreferences(AttributeData.FILENAME, MODE_PRIVATE);
        LOTS2f = prefs.getString("LOTS2f", null);

        chirpConnect = new ChirpSDK(this, AttributeData.APP_KEY, AttributeData.APP_SECRET);
        ChirpError error = chirpConnect.setConfig(AttributeData.APP_CONFIG);
        if (error.getCode() == 0) {
            chirpConnect.setListener(connectEventListener);
        } else {
            SoundGenerator.this.finish();
        }

        chirpConnect.start();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    playSound();
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                pRLA2p.setEnabled(true);
                Kre2x2.setEnabled(true);
            }
        }, 9000);
    }

    ChirpEventListener connectEventListener = new ChirpEventListener() {
        @Override
        public void onSystemVolumeChanged(float v, float v1) {

        }

        @Override
        public void onStateChanged(int i, int i1) {

        }

        @Override
        public void onSent(@NotNull byte[] data, int i) {
            String hexData = "null";
            if (data != null) {
                hexData = new String(data);
            }
        }

        @Override
        public void onSending(@NotNull byte[] data, int i) {
            String hexData = "null";
            if (data != null) {
                hexData = new String(data);
            }
        }

        @Override
        public void onReceiving(int i) {

        }

        @Override
        public void onReceived(@Nullable byte[] bytes, int i) {

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

    public void playSound() {
        byte[] payload = LOTS2f.getBytes();
        chirpConnect.send(payload);
    }

    public void endSession(View view) {
        BackgroundTask3 backgroundTask3 = new BackgroundTask3(SoundGenerator.this);
        backgroundTask3.execute(LOTS2f);
    }

    public void playAgain(View view) {
        pRLA2p.setEnabled(false);
        BackgroundTask4 task4 = new BackgroundTask4(SoundGenerator.this);
        task4.execute(LOTS2f);
    }

    private class BackgroundTask4 extends AsyncTask<String, Void, String> {

        private Context ctx;
        private ProgressDialog dialog;

        BackgroundTask4(Context ctx) {
            this.ctx = ctx;
            dialog = new ProgressDialog(ctx);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Please wait...");
            dialog.show();
        }

        @Override
        protected String doInBackground(final String... strings) {
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            final String LOTS2f = strings[0];

            StringRequest request = new StringRequest(Request.Method.POST, AttributeData.getUrl() + "/updateTime", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("__CUSTOM >> " + response);
                    if (response.equals("1")) {
                        dialog.cancel();
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    playSound();
                                    Thread.currentThread().sleep(4000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        t.start();

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pRLA2p.setEnabled(true);
                                Kre2x2.setEnabled(true);
                            }
                        }, 9000);
                    } else if (response.equals("401")) {
                        final AlertDialog.Builder adb = new AlertDialog.Builder(ctx);
                        adb.setTitle("Alert");
                        adb.setMessage("Invalid application config.");
                        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        });
                        adb.show();
                    }
                    // It will store encrypted key into shared  preference
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("LOTS2f", LOTS2f);
                    parameters.put("cert", DeviceUtils.checkAppSignature(ctx));
                    return parameters;
                }
            };
            requestQueue.add(request);

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }
}