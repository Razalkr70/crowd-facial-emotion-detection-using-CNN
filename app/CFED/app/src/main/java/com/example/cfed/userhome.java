package com.example.cfed;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class userhome extends AppCompatActivity {

    Button bt_mgcam, bt_vwnt, bt_sndfb, bt_comp, bt_vid, bt_img
            ;
    SharedPreferences sh;
    RelativeLayout card,comp,cam,fb,vidsrc,noti,lg;
    TextView titletv, desctv ,pname , pemail,pnumber,mm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

//        bt_mgcam=findViewById(R.id.button10);
//        bt_vwnt=findViewById(R.id.button9);
//        bt_sndfb=findViewById(R.id.button8);
//        bt_comp=findViewById(R.id.button7);
//        bt_vid=findViewById(R.id.button6);
//        bt_lgout=findViewById(R.id.button4);
//        bt_img=findViewById(R.id.button18);

        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        card = findViewById(R.id.card);
        cam=findViewById(R.id.camera);
        comp=findViewById(R.id.complaint);
        fb=findViewById(R.id.feedback);
        vidsrc=findViewById(R.id.videosrc);
        noti=findViewById(R.id.notification);
        lg=findViewById(R.id.logout);

        titletv = findViewById(R.id.titletv);
        desctv = findViewById(R.id.desctv);
        pname = findViewById(R.id.txtname);
        pemail = findViewById(R.id.pemail);
        pnumber=findViewById(R.id.pcam);
        mm=findViewById(R.id.kkkk);
        RequestQueue queue = Volley.newRequestQueue(userhome.this);
        String url = "http://" + sh.getString("ip", "") + ":5000/uservprofile";

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
                        String name = json.getString("fname");
                        String lname = json.getString("lname");
                        String email = json.getString("email");
                        String pcount = json.getString("count");

                        pname.setText(name+" "+lname);
                        pemail.setText(email);
                        pnumber.setText(pcount);
                        mm.setText(name+" "+lname);

//                        Intent ik = new Intent(getApplicationContext(), userhome.class);
//                        startActivity(ik);

                    } else {

                        Toast.makeText(userhome.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(userhome.this, "===="+e, Toast.LENGTH_SHORT).show();
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
                params.put("lid", sh.getString("lid",""));


                return params;
            }
        };
        queue.add(stringRequest);








        card.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userhome.this,MoreInfo.class);
                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(card, "card");
                pairs[1] = new Pair<View, String>(titletv, "title");
                pairs[2] = new Pair<View, String>(desctv, "desc");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(userhome.this, pairs);

                startActivity(intent, options.toBundle());
            }
        });

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                Intent i1=new Intent(getApplicationContext(),ManageCamera.class);
                startActivity(i1);

            }
        });
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(getApplicationContext(),avgemotion.class);
                startActivity(i2);

            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3=new Intent(getApplicationContext(),sendfeedback.class);
                startActivity(i3);

            }
        });
        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4=new Intent(getApplicationContext(),Viewreply.class);
                startActivity(i4);

            }
        });
        vidsrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5=new Intent(getApplicationContext(),videosearch.class);
                startActivity(i5);

            }
        });
//        bt_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i9=new Intent(getApplicationContext(),imagsearch.class);
//                startActivity(i9);
//            }
//        });
        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i6=new Intent(getApplicationContext(),login.class);
                startActivity(i6);

            }
        });

    }
    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),userhome.class);
        startActivity(i);
    }
}