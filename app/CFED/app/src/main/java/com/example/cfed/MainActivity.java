package com.example.cfed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText e1;
    Button b1;
    String ip;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        b1=findViewById(R.id.button);
        e1=findViewById(R.id.editTextTextPersonName);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ip=e1.getText().toString();
                if (ip.equalsIgnoreCase("")){
                    e1.setError("Missing");
                }
                else {

                    SharedPreferences.Editor edp = sh.edit();
                    edp.putString("ip", ip);
                    edp.commit();
                    Intent ik = new Intent(getApplicationContext(), login.class);
                    startActivity(ik);
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