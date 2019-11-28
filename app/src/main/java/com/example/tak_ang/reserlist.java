package com.example.tak_ang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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


public class reserlist extends AppCompatActivity {
    private static final String URL= "https://takangrestaurant.xyz/connect/table.php";
    tableadapter tableadapter;
    List<tableget> tables;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    CircleImageView mememe;
    @Override




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserlist);
        recyclerView = findViewById(R.id.tables);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        tables = new ArrayList<>();
        HeyTables();

        mememe = findViewById(R.id.refme);
        mememe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);
            }
        });


    }
    private void HeyTables() {
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


                                tableget tableget = new tableget(table_id,table_name,capacity,status);
                                tables.add(tableget);
                                String myString = getIntent().getStringExtra("user_id");
                                final tableadapter tableadapter = new tableadapter(reserlist.this,tables,myString);
                                recyclerView.setAdapter(tableadapter);
                                tableadapter.notifyDataSetChanged();


                            }
                        }catch (JSONException e){

                            e.printStackTrace();
                            Toast.makeText(reserlist.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(reserlist.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
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