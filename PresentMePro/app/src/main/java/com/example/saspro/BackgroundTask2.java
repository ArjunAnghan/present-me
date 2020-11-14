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
        final String iTYz2f = strings[0];
        final String TDLl0w = strings[1];
        final String cXYZ06 = strings[2];
        final String groups = strings[3];

        StringRequest request = new StringRequest(Request.Method.POST, AttributeData.getUrl() + "/generateLecture", new Response.Listener<String>() {
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
                    SharedPreferences.Editor editor = ctx.getSharedPreferences(AttributeData.FILENAME, MODE_PRIVATE).edit();
                    editor.putString("LOTS2f", response);
                    editor.apply();

                    Intent intent = new Intent(ctx, SoundGenerator.class);
                    ctx.startActivity(intent);
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

                System.out.println("__CUSTOM >> " + iTYz2f + TDLl0w + cXYZ06 + groups);

                parameters.put("iTYz2f", iTYz2f);
                parameters.put("TDLl0w", TDLl0w);
                parameters.put("cXYZ06", cXYZ06);
                parameters.put("groups", groups);
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
