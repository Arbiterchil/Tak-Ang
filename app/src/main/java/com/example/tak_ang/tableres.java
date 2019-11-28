package com.example.tak_ang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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

public class tableres extends AppCompatActivity {
    private static final String URL= "https://takangrestaurant.xyz/connect/table.php";
    List<tablever> tables;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableres);

        recyclerView = findViewById(R.id.tables);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        tables = new ArrayList<>();

        heyTab();

    }

    private void heyTab() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0;i<jsonArray.length();i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int table_id = jsonObject.getInt("table_id");
                                String table_name = jsonObject.getString("table_name");
                                int capacity = jsonObject.getInt("capacity");
                                int status = jsonObject.getInt("status");

                                tablever tablever = new tablever(table_id,table_name,capacity,status);
                                tables.add(tablever);

                                String myString = getIntent().getStringExtra("user_id");

                                final tableverdapter tableadapter = new tableverdapter(tableres.this,tables,myString);
                                recyclerView.setAdapter(tableadapter);
                                tableadapter.notifyDataSetChanged();


                            }
                        }catch (JSONException e){

                            e.printStackTrace();
                            Toast.makeText(tableres.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(tableres.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> s = new HashMap<>();
                s.put("status","1");
                return s ;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);


    }




}
