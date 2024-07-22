package com.example.cfed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Viewreply extends AppCompatActivity {

    Button  bt_add;
    ImageView bt_srh;
    EditText ed_dt;
    ListView lt_rep;
    String date;
    SharedPreferences sh;
    ArrayList<String> comp,dat,rep;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreply);
        bt_srh=findViewById(R.id.button16);
        bt_add=findViewById(R.id.button17);
        ed_dt=findViewById(R.id.editTextDate);
        lt_rep=findViewById(R.id.lv1);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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
                        Viewreply.this,
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
        url ="http://"+sh.getString("ip", "") + ":5000/v_reply1";
        RequestQueue queue = Volley.newRequestQueue(Viewreply.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    comp= new ArrayList<>();
                    dat= new ArrayList<>();
                    rep= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        comp.add(jo.getString("complaint"));
                        dat.add(jo.getString("dt"));
                        rep.add(jo.getString("rply"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lt_rep.setAdapter(new custom_reply(Viewreply.this,comp,dat,rep));
//                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Viewreply.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid",sh.getString("lid",""));

                return params;
            }
        };
        queue.add(stringRequest);

        bt_srh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date=ed_dt.getText().toString();
                String url1 ="http://"+sh.getString("ip", "") + ":5000/searchcomplaintvieww";
                RequestQueue queue1 = Volley.newRequestQueue(Viewreply.this);

                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);
                            comp= new ArrayList<>();
                            dat= new ArrayList<>();
                            rep= new ArrayList<>();


                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);
                                comp.add(jo.getString("complaint"));
                                dat.add(jo.getString("dt"));
                                rep.add(jo.getString("rply"));


                            }

                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);

                            lt_rep.setAdapter(new custom_reply(Viewreply.this,comp,dat,rep));
//                    l1.setOnItemClickListener(viewuser.this);

                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Viewreply.this, "err"+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("date",date);
                        params.put("lid",sh.getString("lid",""));
                        return params;
                    }
                };
                queue1.add(stringRequest1);

            }
        });
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i7=new Intent(getApplicationContext(),sendcomplaint.class);
                startActivity(i7);

            }
        });

    }
    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),userhome.class);
        startActivity(i);
    }
}