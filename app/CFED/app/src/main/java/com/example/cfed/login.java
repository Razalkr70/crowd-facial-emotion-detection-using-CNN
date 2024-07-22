package com.example.cfed;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class login extends AppCompatActivity {

    Button btlog;
    EditText ed_un, ed_pwd;
    String un, pwd;
    SharedPreferences sh;
    TextView btreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btlog = findViewById(R.id.loginbtn);
        btreg = findViewById(R.id.forgotpass);
        ed_un = findViewById(R.id.username);
        ed_pwd = findViewById(R.id.password);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        btreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(getApplicationContext(),registration.class);
                startActivity(ii);
            }
        });
        btlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                un=ed_un.getText().toString();
                pwd=ed_pwd.getText().toString();
                if (un.equalsIgnoreCase("")){
                    ed_un.setError("Missing");
                }
                else if (pwd.equalsIgnoreCase("")){
                    ed_pwd.setError("Missing");
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(login.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/login_code";

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
                                    String lid = json.getString("lid");
                                    SharedPreferences.Editor edp = sh.edit();
                                    edp.putString("lid", lid);
                                    edp.commit();
                                    Intent ik = new Intent(getApplicationContext(), userhome.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

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
                            params.put("uname", un);
                            String pass;
                            params.put("pswrd", pwd);

                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }


            }
        });

    }
    public void onBackPressed(){
        AlertDialog.Builder ald=new AlertDialog.Builder(login.this);
        ald.setTitle("Do You Want To Exit")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent in=new Intent (Intent.ACTION_MAIN);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.addCategory(Intent.CATEGORY_HOME);
                        startActivity(in);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent i=new Intent(getApplicationContext(),login.class);
                        startActivity(i);
                    }
                });
        AlertDialog al= ald.create();
        al.show();

    }

}
