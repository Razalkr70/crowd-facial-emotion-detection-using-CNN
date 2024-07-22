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

public class sendfeedback extends AppCompatActivity {

    Button bt_snd;
    EditText ed_fb;
    String fb;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendfeedback);

        bt_snd=findViewById(R.id.button13);
        ed_fb=findViewById(R.id.editTextTextPersonName9);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        bt_snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fb=ed_fb.getText().toString();
                if (fb.equalsIgnoreCase("")){
                    ed_fb.setError("Missing");
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(sendfeedback.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/afeedback";

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

                                    Toast.makeText(sendfeedback.this, "Invalid", Toast.LENGTH_SHORT).show();

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
                            params.put("fdbk", fb);

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