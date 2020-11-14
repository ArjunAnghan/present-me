package com.example.sas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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


public class BackgroundTask2 extends AsyncTask<String, Void, String> {

    private Context ctx;
    private ProgressDialog dialog;

    BackgroundTask2(Context ctx) {
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
        final String ILIm50 = strings[0];
        final String b71cO2 = strings[1];
        final String group = strings[2];

        StringRequest request = new StringRequest(Request.Method.POST, AttributeData.getUrl() + "/present", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.cancel();
                if (response.equals("1")) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(ctx);
                    adb.setTitle("Alert");
                    adb.setMessage("Your attendence has been done.");
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    });
                    adb.show();
                } else if (response.equals("0")) {
                    Intent intent = new Intent(ctx, Chirp.class);
                    ctx.startActivity(intent);
                } else if (response.equals("2")) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(ctx);
                    adb.setTitle("Alert");
                    adb.setMessage("Invalid session.");
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ctx, Chirp.class);
                            ctx.startActivity(intent);
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
                parameters.put("ILIm50", ILIm50);
                parameters.put("b71cO2", b71cO2);
                parameters.put("group", group);
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
