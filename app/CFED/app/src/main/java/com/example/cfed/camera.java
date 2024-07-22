package com.example.cfed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class camera extends AppCompatActivity {

    Button bt_cam;
    EditText ed_camno, ed_det;
    String camno, det;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        bt_cam=findViewById(R.id.button12);
        ed_camno=findViewById(R.id.editTextTextPersonName7);
        ed_det=findViewById(R.id.editTextTextPersonName8);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        bt_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camno=ed_camno.getText().toString();

                det=ed_det.getText().toString();
                if (camno.equalsIgnoreCase("")){
                    ed_camno.setError("Missing");
                }
                else if (det.equalsIgnoreCase("")){
                    ed_det.setError("Missing");
                }
                else {


                    RequestQueue queue = Volley.newRequestQueue(camera.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/acamera";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("valid")) {

                                    Intent ik = new Intent(getApplicationContext(), userhome.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(camera.this, "Invalid", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("camno", camno);
                            params.put("dts", det);
                            String pass;
                            params.put("lid", sh.getString("lid", ""));

                            return params;
                        }

                    };
                    queue.add(stringRequest);
                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),userhome.class);
        startActivity(i);
    }
}