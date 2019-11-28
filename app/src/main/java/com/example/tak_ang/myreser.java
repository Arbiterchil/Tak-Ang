package com.example.tak_ang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myreser extends AppCompatActivity {

    RecyclerView recyclerView;
    List<reserget> resergetList;
    private static final String URL_RL= "https://takangrestaurant.xyz/connect/myreserve.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreser);

        recyclerView = findViewById(R.id.recycleme);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resergetList = new ArrayList<>();
        Myreservation();
    }
    private void Myreservation() {


        final String user_id = getIntent().getStringExtra("user_id");

        final String suckmyDick = Integer.toString(Integer.parseInt(user_id));

        StringRequest stringRequest = new StringRequest(Request.Method.POST
                , URL_RL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        int book_id = jsonObject.getInt("booking_id");
                        String chekin = jsonObject.getString("check_in");
                        int status = jsonObject.getInt("status");
                        int table_id = jsonObject.getInt("table_id");
                        int user_id = jsonObject.getInt("user_id");


                        reserget reserget = new reserget(book_id,chekin,user_id,table_id,status);
                        resergetList.add(reserget);

                        reseradapter reseradapter = new reseradapter(myreser.this,resergetList,suckmyDick);
                        recyclerView.setAdapter(reseradapter);
                        reseradapter.notifyDataSetChanged();

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
                Map<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
