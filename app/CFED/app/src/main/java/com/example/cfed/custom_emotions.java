package com.example.cfed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class custom_emotions extends BaseAdapter{
    private Context context;

    ArrayList<String> a,b,c;
    SharedPreferences sh;




    public custom_emotions(Context applicationContext, ArrayList<String> a, ArrayList<String> b, ArrayList<String> c) {
        // TODO Auto-generated constructor stub
        this.context=applicationContext;
        this.a=a;
        this.b=b;
        this.c=c;

        sh= PreferenceManager.getDefaultSharedPreferences(context);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return a.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getItemViewType(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(context);
            gridView=inflator.inflate(R.layout.activity_custom_camera, null);

        }
        else
        {
            gridView=(View)convertview;

        }


        TextView tv1=(TextView)gridView.findViewById(R.id.ab);
        TextView tv2=(TextView)gridView.findViewById(R.id.cd);
        TextView tv3=(TextView)gridView.findViewById(R.id.ef);





        tv1.setText(a.get(position));
        tv2.setText(b.get(position));
        tv3.setText(c.get(position));




        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);









        return gridView;

    }

}







