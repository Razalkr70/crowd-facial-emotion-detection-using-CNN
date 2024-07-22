package com.example.cfed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class ViewNotifications extends AppCompatActivity  {
    ListView lt_not;
    SharedPreferences sh;

    ArrayList<String> date,time,emotion,image,cameraid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notifications);

        lt_not=findViewById(R.id.lv3);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        String url ="http://"+sh.getString("ip", "") + ":5000/notificationv";
        RequestQueue queue = Volley.newRequestQueue(ViewNotifications.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    date= new ArrayList<>();
                    time= new ArrayList<>();
                    emotion= new ArrayList<>();
                    image= new ArrayList<>();
                    cameraid= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        date.add(jo.getString("date"));
                        time.add(jo.getString("time"));
                        emotion.add(jo.getString("emotion"));
                        image.add(jo.getString("image"));
                        cameraid.add(jo.getString("cameraid"));



                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    //AlertDialog.Builder l1;
                    lt_not.setAdapter(new custom_notification(ViewNotifications.this,date,time,emotion,image,cameraid));
//                    lt_not.setOnItemClickListener(ViewNotifications.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ViewNotifications.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("eid",getIntent().getStringExtra("eid"));

                return params;
            }
        };
        queue.add(stringRequest);


    }
    @Override
    public void onBackPressed(){

        Intent i=new Intent(getApplicationContext(),userhome.class);
        startActivity(i);
    }


}