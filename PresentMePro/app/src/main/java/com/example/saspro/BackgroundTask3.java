package com.example.saspro;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

// This is for send slot and subject data to server
public class BackgroundTask3 extends AsyncTask<String, Void, String> {

    private Context ctx;
    private ProgressDialog dialog;

    BackgroundTask3(Context ctx) {
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

        StringRequest request = new StringRequest(Request.Method.POST, AttributeData.getUrl() + "/commit", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.cancel();

                if (response.equals("401")) {
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
                    // response is encrypted key
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                    System.exit(0);
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
        // Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
    }
}
