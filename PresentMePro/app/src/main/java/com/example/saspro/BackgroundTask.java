package com.example.saspro;

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

        final String iTYz2f = strings[0];
        final String KoTetn = strings[1];
        // final String pass2=strings[1];

        // Request string creation
        StringRequest request = new StringRequest(Request.Method.POST, AttributeData.getUrl() + "/loginFaculty", new Response.Listener<String>() {

            // This is on response
            @Override
            public void onResponse(String response) {
                dialog.cancel();
                if (response.equals("0")) {
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
                } else if (response.charAt(0) == '1') {
                    // Storing professor credentials
                    SharedPreferences.Editor editor = ctx.getSharedPreferences(AttributeData.FILENAME, MODE_PRIVATE).edit();
                    editor.putString("iTYz2f", strings[0]);
                    editor.putString("KoTetn", strings[1]);
                    editor.apply();

                    // New activity lectureGenerator
                    Intent intent = new Intent(ctx, LeactureGenerator.class);
                    intent.putExtra("groups", response);
                    ctx.startActivity(intent);
                } else if (response.equals("2")) {
                    // When account is not activated
                    AlertDialog.Builder adb = new AlertDialog.Builder(ctx);
                    adb.setTitle("Alert");
                    adb.setMessage("Account not activated. Contact admin");
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
                    AlertDialog.Builder adb = new AlertDialog.Builder(ctx);
                    adb.setTitle("Alert");
                    adb.setMessage("Check internet connectivity");
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
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
                parameters.put("iTYz2f", iTYz2f);
                parameters.put("KoTetn", KoTetn);
                parameters.put("cert", DeviceUtils.checkAppSignature(ctx));

                return parameters;
            }
        };
        requestQueue.add(request);

        return null;
    }

    // This will shown while sending data
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
    }
}
