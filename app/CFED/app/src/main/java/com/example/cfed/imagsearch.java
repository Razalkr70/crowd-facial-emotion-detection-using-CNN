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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class imagsearch extends AppCompatActivity {

    Button bt_srh;
    EditText ed_dt;
    ListView lt_rep;
    String date;
    SharedPreferences sh;
    ArrayList<String> dat,img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagsearch);

        bt_srh=findViewById(R.id.button19);
        ed_dt=findViewById(R.id.editTextDate3);
        lt_rep=findViewById(R.id.lv10);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        bt_srh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date=ed_dt.getText().toString();
                sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String url ="http://"+sh.getString("ip", "") + ":5000/view";
                RequestQueue queue = Volley.newRequestQueue(imagsearch.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);
                            img= new ArrayList<>();
                            dat= new ArrayList<>();


                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);
//                                comp.add(jo.getString("complaint"));
                                dat.add(jo.getString("dt"));


                            }

                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);

//                            lt_rep.setAdapter(new custom_reply(imagsearch.this,comp,dat,rep));
//                    l1.setOnItemClickListener(viewuser.this);

                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        Toast.makeText(Viewreply.this, "err"+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();

                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),userhome.class);
        startActivity(i);
    }
}