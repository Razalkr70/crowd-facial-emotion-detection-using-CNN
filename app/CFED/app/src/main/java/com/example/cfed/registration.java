package com.example.cfed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class registration extends AppCompatActivity {
    EditText passwordEditText;
    ImageButton passwordToggle;
    Button bt_reg;
    EditText ed_fn, ed_ln, ed_pl, ed_po, ed_pin, ed_ph, ed_mail, ed_usnm, ed_pas,pas;
    String fname,lname,place,post, pin, phn, mail, usnm, pass;
    SharedPreferences sh;
    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        passwordEditText = findViewById(R.id.editTextTextPersonName11);
        passwordToggle = findViewById(R.id.passwordToggle);
        bt_reg=findViewById(R.id.button5);
        ed_fn=findViewById(R.id.editTextTextPersonName3);
        ed_ln=findViewById(R.id.editTextTextPersonName4);
        ed_pl=findViewById(R.id.editTextTextPersonName6);
        ed_po=findViewById(R.id.editTextTextPersonName5);
        ed_pin=findViewById(R.id.editTextNumber);
        ed_ph=findViewById(R.id.editTextPhone2);
        ed_mail=findViewById(R.id.editTextTextEmailAddress);
        ed_usnm=findViewById(R.id.editTextTextPersonName10);
        passwordEditText = findViewById(R.id.editTextTextPersonName11);
        passwordToggle = findViewById(R.id.passwordToggle);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        // Toggle password visibility
        passwordToggle.setOnClickListener(v -> {
            // Toggle the password visibility state
            togglePasswordVisibility();
        });

        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = ed_fn.getText().toString();
                lname = ed_ln.getText().toString();
                place = ed_pl.getText().toString();
                post = ed_po.getText().toString();
                pin = ed_pin.getText().toString();
                phn = ed_ph.getText().toString();
                mail = ed_mail.getText().toString();
                usnm = ed_usnm.getText().toString();
                pass = passwordEditText.getText().toString();
                if (fname.equalsIgnoreCase("")){
                    ed_fn.setError("Missing");
                }
                else  if (lname.equalsIgnoreCase("")){
                    ed_ln.setError("Missing");
                }
                else  if (place.equalsIgnoreCase("")){
                    ed_pl.setError("Missing");
                }
                else  if (post.equalsIgnoreCase("")){
                    ed_po.setError("Missing");
                }
                else  if (pin.equalsIgnoreCase("")){
                    ed_pin.setError("Missing");
                }
                else if (phn.equalsIgnoreCase("")){
                    ed_ph.setError("Missing");
                }
                else if (mail.equalsIgnoreCase("")){
                    ed_mail.setError("Missing");
                } else if (usnm.equalsIgnoreCase("")){
                    ed_usnm.setError("Missing");
                } else if (pass.equalsIgnoreCase("")) {
                    passwordEditText.setError("Missing");
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(registration.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/register";

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

                                    Intent ik = new Intent(getApplicationContext(), login.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(registration.this, "Invalid ", Toast.LENGTH_SHORT).show();

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

                            params.put("fname", fname);
                            params.put("lname", lname);
                            params.put("place", place);
                            params.put("post", post);
                            params.put("pin", pin);
                            params.put("phone", phn);
                            params.put("email", mail);
                            params.put("un", usnm);
                            params.put("pw", pass);
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

    private void togglePasswordVisibility() {
        int currentInputType = passwordEditText.getInputType();
        if (currentInputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            // Show password
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            passwordToggle.setImageResource(R.drawable.ic_baseline_visibility_off_24); // Set icon to hide
        } else {
            // Hide password
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordToggle.setImageResource(R.drawable.ic_baseline_visibility_24); // Set icon to show
        }

        // Move cursor to the end of the text
        passwordEditText.setSelection(passwordEditText.getText().length());



    }

    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),login.class);
        startActivity(i);
    }
}