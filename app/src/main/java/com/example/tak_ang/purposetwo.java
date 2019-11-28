package com.example.tak_ang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class purposetwo extends AppCompatActivity {

    String getId;
    TextView tabId,stas,iden,chefid;
    Button pros,read,serve;
    RecyclerView recyclerView;
    List<orderitem> orderitemList;
    orderadapter orderadapters;
    SessionManager sessionManager;
    private static final String URL_RL= "https://takangrestaurant.xyz/connect/orderlist.php";
    private static final String URL_ST= "https://takangrestaurant.xyz/connect/saveprocess.php";
    private static String URL_READ = "https://takangrestaurant.xyz/connect/read.php";
    private static final String URL_CS= "https://takangrestaurant.xyz/connect/chefsave.php";
    private static final String URL_W= "https://takangrestaurant.xyz/connect/waitersave.php";
//    private static final String URL_DELETE= "https://takangrestaurant.xyz/connect/itemdel.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purposetwo);
        stas = findViewById(R.id.stastu);
        tabId = findViewById(R.id.orgy);
        pros = findViewById(R.id.process);
        read = findViewById(R.id.readyass);
        serve = findViewById(R.id.serveme);
        chefid = findViewById(R.id.chefID);
        String getme = getIntent().getStringExtra("table_id");
        String meget = getIntent().getStringExtra("status");
        tabId.setText(getme);
        stas.setText(meget);
        recyclerView = findViewById(R.id.cheflist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderitemList = new ArrayList<>();
        iden = findViewById(R.id.identi);
        sessionManager = new SessionManager(this);
        HashMap<String, String > user = sessionManager.getUserDeatils();
        getId = user.get(sessionManager.USER_ID);


        switch (stas.getText().toString()){
            case "1":
                read.setVisibility(View.GONE);
                serve.setVisibility(View.GONE);
                break;
            case "2":
                pros.setVisibility(View.GONE);
                serve.setVisibility(View.GONE);
                break;
            case "3":
                read.setVisibility(View.GONE);
                pros.setVisibility(View.GONE);
                break;
        }

        OrdType();
        proRead();
        Identify();
        WaiterMe();

    }
    private void WaiterMe() {
        serve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(purposetwo.this);
                progressDialog.setMessage("Processing......");
                progressDialog.show();

                StringRequest uprec = new StringRequest(Request.Method.POST,
                        URL_W, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            String successs = jsonObject1.getString("success");

                            if (successs.equals("1")){
                                Toast.makeText(purposetwo.this, " Waiter Inserted", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(purposetwo.this, "Ero ka!"+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(purposetwo.this, "Ero ka!"+ error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map1 = new HashMap<>();
                        map1.put("order_id",tabId.getText().toString().trim());
                        map1.put("waiter_id",chefid.getText().toString().trim());
                        return map1;
                    }
                };
                RequestQueue result = Volley.newRequestQueue(purposetwo.this);
                result.add(uprec);

                StringRequest s1 = new StringRequest(Request.Method.POST,
                        URL_ST, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject  jsonObject1 = new JSONObject(response);
                            String successs = jsonObject1.getString("success");
                            if (successs.equals("1")){
                                progressDialog.dismiss();
                                Toast.makeText(purposetwo.this, "Receipt updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(purposetwo.this,waiterside.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(purposetwo.this, "Malika! "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(purposetwo.this, "Malika! Animal "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String,String> map1 = new HashMap<>();
                        map1.put("order_id",tabId.getText().toString().trim());
                        map1.put("status","4");
                        return map1;
                    }
                };
                RequestQueue req1 = Volley.newRequestQueue(purposetwo.this);
                req1.add(s1);
//                StringRequest spree = new StringRequest(Request.Method.POST,
//                        URL_DELETE, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject1 = new JSONObject(response);
//                            String successs = jsonObject1.getString("success");
//                            if (successs.equals("1")){
//                                Toast.makeText(purposetwo.this, "Receipt updated", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(purposetwo.this, "Ero ka!"+ e.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                        Toast.makeText(purposetwo.this, "Ero ka!"+ error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String,String> map1 = new HashMap<>();
//                        map1.put("order_id",tabId.getText().toString().trim());
//                        return map1;
//                    }
//                };
//                RequestQueue res1 = Volley.newRequestQueue(purposetwo.this);
//                res1.add(spree);
            }
        });




    }
    private void Identify() {

        final ProgressDialog progressDialog = new ProgressDialog(purposetwo.this);
        progressDialog.setMessage("Processing......");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success =jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("read");
                            if (success.equals("1")){
                                for (int i= 0; i<jsonArray.length();i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    int id = object.getInt("user_id");
                                    chefid.setText(String.valueOf(id));
                                    int position = object.getInt("position");
                                    progressDialog.dismiss();
                                    iden.setText(String.valueOf(position));
                                    if (iden.getText().toString().equals("3")){
                                        serve.setVisibility(View.GONE);
                                        serve.setEnabled(false);
                                    } else if (iden.getText().toString().equals("4")){
                                        read.setVisibility(View.GONE);
                                        pros.setVisibility(View.GONE);
                                        read.setEnabled(false);
                                        pros.setEnabled(false);
                                    }
                                }
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(purposetwo.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(purposetwo.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String > params = new HashMap<>();
                params.put("user_id",getId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
    private void proRead() {
        pros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(purposetwo.this);
                progressDialog.setMessage("Processing......");
                progressDialog.show();

                StringRequest uprec = new StringRequest(Request.Method.POST,
                        URL_CS, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            String successs = jsonObject1.getString("success");

                            if (successs.equals("1")){
                                Toast.makeText(purposetwo.this, "Cook Chef Inserted", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(purposetwo.this, "Ero ka!"+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(purposetwo.this, "Ero ka!"+ error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map1 = new HashMap<>();
                        map1.put("order_id",tabId.getText().toString().trim());
                        map1.put("chef_id",chefid.getText().toString().trim());
                        return map1;
                    }
                };
                 RequestQueue result = Volley.newRequestQueue(purposetwo.this);
                result.add(uprec);
                StringRequest s1 = new StringRequest(Request.Method.POST,
                        URL_ST, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject  jsonObject1 = new JSONObject(response);
                            String successs = jsonObject1.getString("success");

                            if (successs.equals("1")){
                                progressDialog.dismiss();
                                Toast.makeText(purposetwo.this, "Receipt updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(purposetwo.this,aorde.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(purposetwo.this, "Malika! "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(purposetwo.this, "Malika! Animal "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String,String> map1 = new HashMap<>();
                        map1.put("order_id",tabId.getText().toString().trim());
                        map1.put("status","2");
                        return map1;
                    }
                };
                RequestQueue req1 = Volley.newRequestQueue(purposetwo.this);
                req1.add(s1);

            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(purposetwo.this);
                progressDialog.setMessage("Processing......");
                progressDialog.show();

                StringRequest s2 = new StringRequest(Request.Method.POST,
                        URL_ST, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject  jsonObject1 = new JSONObject(response);
                            String successs = jsonObject1.getString("success");

                            if (successs.equals("1")){
                                progressDialog.dismiss();
                                Toast.makeText(purposetwo.this, "Receipt updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(purposetwo.this,aorde.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(purposetwo.this, "Malika! "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(purposetwo.this, "Malika! Animal "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String,String> map1 = new HashMap<>();
                        map1.put("order_id",tabId.getText().toString().trim());
                        map1.put("status","3");
                        return map1;
                    }
                };
                RequestQueue req2 = Volley.newRequestQueue(purposetwo.this);
                req2.add(s2);
            }
        });
    }
    private void OrdType() {
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, URL_RL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int order_item = jsonObject.getInt("order_item_id");
                                int order_id = jsonObject.getInt("order_id");
                                final int quantity = jsonObject.getInt("quantity");
                                String name = jsonObject.getString("name");
                                final double price = jsonObject.getDouble("price");
                                final orderitem orderitem = new orderitem(order_item,order_id, quantity, name, price);
                                orderitemList.add(orderitem);
                                final ordersadapter ordersadapter = new ordersadapter
                                        (purposetwo.this, orderitemList);
                                recyclerView.setAdapter(ordersadapter);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(purposetwo.this, "Mali ka Drea "+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> par =  new HashMap<>();
                par.put("order_id",tabId.getText().toString().trim());
                return par;
            }
        };
        Volley.newRequestQueue(purposetwo.this).add(stringRequest);


    }

}
