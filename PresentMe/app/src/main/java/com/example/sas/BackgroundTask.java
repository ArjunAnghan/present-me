package com.example.sas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class BackgroundTask extends AsyncTask<String, Void, String> {

    private Context ctx;
    private ProgressDialog dialog;

    BackgroundTask(Context ctx) {
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
        final String b71cO2 = strings[0];
        final String Oaos3L = strings[1];
        final String kUQNGF = strings[2];
        StringRequest request = new StringRequest(Request.Method.POST, AttributeData.getUrl() + "/getString", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.cancel();
                if (response.equals("1")) {
                    //wrong id or password
                    AlertDialog.Builder adb = new AlertDialog.Builder(ctx);
                    adb.setTitle("Alert");
                    adb.setMessage("Invalid Credentials");
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    adb.show();
                } else if (response.charAt(0) == '7') {
                    SharedPreferences.Editor editor = ctx.getSharedPreferences(AttributeData.FILENAME, MODE_PRIVATE).edit();
                    editor.putInt("loginFlag", 1);
                    editor.putString("SI", b71cO2);
                    editor.putString("group", response.split(",")[1]);
                    editor.apply();
                    Intent intent1 = new Intent(ctx, Chirp.class);
                    ctx.startActivity(intent1);
                } else if (response.equals("18")) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(ctx);
                    adb.setTitle("Alert");
                    adb.setMessage("You are blocked. Contact admin");
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    });
                    adb.show();
                } else if (response.equals("-1")) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(ctx);
                    adb.setTitle("Alert");
                    adb.setMessage("Some Problem Occured. Contact admin");
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    });
                    adb.show();
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
                } else {
                    final AlertDialog.Builder adb = new AlertDialog.Builder(ctx);
                    adb.setTitle("Alert");
                    adb.setMessage("Check internet connectivity");
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    adb.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("b71cO2", b71cO2);
                parameters.put("Oaos3L", Oaos3L);
                parameters.put("kUQNGF", kUQNGF);
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
        // Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
    }
}
