package com.example.cfed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class videosearch extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SharedPreferences sh;
    ImageView bt_srh;
    EditText ed_dt;
    ListView lt_rep;
    String date;

    ArrayList<String> dat,vidlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videosearch);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        bt_srh=findViewById(R.id.btn15);
        ed_dt=findViewById(R.id.editTextTextPersonName133);
        lt_rep=findViewById(R.id.lv99);
        ed_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        videosearch.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                String d=year+"-";
                                monthOfYear=monthOfYear+1;

                                if (monthOfYear<10)
                                {
                                    d=d+"0"+monthOfYear+"-";
                                }else
                                {
                                    d=d+monthOfYear+"-";
                                }
                                if(dayOfMonth<10)
                                {
                                    d=d+"0"+dayOfMonth;
                                }
                                else
                                {
                                    d=d+dayOfMonth;
                                }
                                ed_dt.setText(d);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });
        bt_srh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date=ed_dt.getText().toString();
                if (date.equalsIgnoreCase("")){
                    ed_dt.setError("Missing");
                }
                else {
                    String url1 = "http://" + sh.getString("ip", "") + ":5000/videosrc_search";
                    RequestQueue queue1 = Volley.newRequestQueue(videosearch.this);

                    StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                Toast.makeText(videosrc.this, "err"+response, Toast.LENGTH_SHORT).show();

                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {

                                JSONArray ar = new JSONArray(response);
                                vidlink = new ArrayList<>();
                                dat = new ArrayList<>();


                                for (int i = 0; i < ar.length(); i++) {
                                    JSONObject jo = ar.getJSONObject(i);
//                                comp.add(jo.getString("complaint"));
                                    dat.add(jo.getString("dt"));
                                    vidlink.add(jo.getString("vidlink"));


                                }

                                // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                                //lv.setAdapter(ad);

                                lt_rep.setAdapter(new custom_videosrc(videosearch.this, vidlink, dat));
                                lt_rep.setOnItemClickListener(videosearch.this);

                            } catch (Exception e) {
                                Log.d("=========", e.toString());
                            }


                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                        Toast.makeText(videosrc.this, "err"+error, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("date", date);

                            return params;
                        }
                    };
                    queue1.add(stringRequest1);
                }

            }
        });
        String url ="http://"+sh.getString("ip", "") + ":5000/videosrc";
        RequestQueue queue = Volley.newRequestQueue(videosearch.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(videosrc.this, "err"+response, Toast.LENGTH_SHORT).show();

                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    vidlink= new ArrayList<>();
                    dat= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
//                                comp.add(jo.getString("complaint"));
                        dat.add(jo.getString("dt"));
                        vidlink.add(jo.getString("vidlink"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lt_rep.setAdapter(new custom_videosrc(videosearch.this,vidlink,dat));
                            lt_rep.setOnItemClickListener(videosearch.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                        Toast.makeText(videosrc.this, "err"+error, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String url2="http://"+sh.getString("ip","")+":5000/"+vidlink.get(i);
        Intent dwnl=new Intent(Intent.ACTION_VIEW,
                Uri.parse(url2));
        startActivity(dwnl);

    }
}