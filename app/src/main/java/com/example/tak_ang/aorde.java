package com.example.tak_ang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class aorde extends AppCompatActivity {
    protected static int SPLASH_TIME = 5000;
    private static final String URL= "https://takangrestaurant.xyz/connect/chefproc.php";
    RecyclerView recyclerView;
    List<aorder> aorderList;
    CircleImageView mememes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aorde);
        recyclerView = findViewById(R.id.orche);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        aorderList =new ArrayList<>();
        heyChef();
        mememes = findViewById(R.id.refme);
//
//         new Handler()
//               .postDelayed(new Runnable(){
//                    @Override
//                    public void run(){
//
//                    }
//                },SPLASH_TIME);
        mememes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        finish();
                        overridePendingTransition( 0, 0);
                        startActivity(getIntent());
                        overridePendingTransition( 0, 0);
            }
        });
    }

    private void heyChef() {


        StringRequest stringRequest =new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int ordid =jsonObject.getInt("order_id");
                        int tabnum = jsonObject.getInt("table_id");
                        String date = jsonObject.getString("date_ordered");
                        int stats  = jsonObject.getInt("status");
                        aorder aorder = new aorder(ordid,date,tabnum,stats);
                        aorderList.add(aorder);
                        aadapter aadapter = new aadapter(aorde.this,aorderList);
                        recyclerView.setAdapter(aadapter);
                        aadapter.notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);


    }

}
