package com.example.tak_ang;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

public class results extends AppCompatActivity {


    RecyclerView recyclerView;
    List<orderitem>orderitemList;
    orderadapter orderadapters;
    Button b;
    SessionManager sessionManager;
    String getId,orget;
    TextView idord,totalseen,tabs;
    private static final String URL_RL= "https://takangrestaurant.xyz/connect/orderlist.php";

    private static final String URL_TABLE= "https://takangrestaurant.xyz/connect/updatetab.php";

    private static final String URL_RECEPIT = "https://takangrestaurant.xyz/connect/recep.php";

    private static final String URL_ORDER= "https://takangrestaurant.xyz/connect/orderID.php";

//    private static final String URL_DELETE= "https://tak-ang.000webhostapp.com/connect/itemdel.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int w = dm.widthPixels;
        int h = dm.heightPixels;

        getWindow().setLayout((int)(w*.8),(int)(h*.7));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)) ;

        recyclerView = findViewById(R.id.temlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderitemList = new ArrayList<>();
        b = findViewById(R.id.b);

        idord =findViewById(R.id.orderid);
        totalseen = findViewById(R.id.totalseen);
        sessionManager = new SessionManager(this);

        HashMap<String, String > user = sessionManager.getUserDeatils();
        getId = user.get(sessionManager.USER_ID);

        String tabs1 = getIntent().getStringExtra("table_id");

        tabs = findViewById(R.id.tabs);
        tabs.setText(tabs1);


        getOrderID();
    }
    private void getOrderID() {
        StringRequest stringRequestes = new StringRequest(Request.Method.POST, URL_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String suckDick = jsonObject.getString("success");
                            JSONArray jsonArray =  jsonObject.getJSONArray("read");
                            if (suckDick.equals("1")){
                                for (int i= 0; i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                        final String order_id = object.getString("order_id").trim();
                                        idord.setText(order_id);

                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_RL,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        final JSONArray jsonArray = new JSONArray(response);
                                                        for (int i = 0;i<jsonArray.length();i++){
                                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                            int order_item = jsonObject.getInt("order_item_id");
                                                            int order_id = jsonObject.getInt("order_id");
                                                            final int quantity = jsonObject.getInt("quantity");
                                                            String name = jsonObject.getString("name");
                                                            final double price  = jsonObject.getDouble("price");



                                                            final orderitem orderitem = new orderitem(order_item,order_id,quantity,name,price);
                                                            orderitemList.add(orderitem);

                                                            final orderadapter orderadapter = new orderadapter
                                                                    (results.this,orderitemList);
                                                            recyclerView.setAdapter(orderadapter);


                                                            b.setOnClickListener(new View.OnClickListener() {
                                                                @RequiresApi(api = Build.VERSION_CODES.N)
                                                                @Override
                                                                public void onClick(View v) {

                                                                    double total= 0;
                                                                    for (orderitem orderitem1: orderitemList) {

                                                                        total += orderitem1.getQuantity() * orderitem1.getPrice();

                                                                        totalseen.setText(String.valueOf(total));

                                                                    }

                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(results.this);
                                                                    builder.setTitle("Confirm Purchase");
                                                                    builder.setMessage("The Total for all is " +total+ " Want to Proceed?");
                                                                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int which) {

                                                                            final ProgressDialog progressDialog = new ProgressDialog(results.this);
                                                                            progressDialog.setMessage("Processing......");
                                                                            progressDialog.show();

//                                                                            StringRequest spree = new StringRequest(Request.Method.POST,
//                                                                                    URL_DELETE, new Response.Listener<String>() {
//                                                                                @Override
//                                                                                public void onResponse(String response) {
//                                                                                    try {
//                                                                                        JSONObject jsonObject1 = new JSONObject(response);
//                                                                                        String successs = jsonObject1.getString("success");
//
//                                                                                        if (successs.equals("1")){
//                                                                                            Toast.makeText(results.this, "Receipt updated", Toast.LENGTH_SHORT).show();
//                                                                                        }
//
//                                                                                    } catch (JSONException e) {
//                                                                                        e.printStackTrace();
//                                                                                        Toast.makeText(results.this, "Ero ka!"+ e.toString(), Toast.LENGTH_SHORT).show();
//
//                                                                                    }
//                                                                                }
//                                                                            }, new Response.ErrorListener() {
//                                                                                @Override
//                                                                                public void onErrorResponse(VolleyError error) {
//                                                                                    error.printStackTrace();
//                                                                                    Toast.makeText(results.this, "Ero ka!"+ error.toString(), Toast.LENGTH_SHORT).show();
//
//
//                                                                                }
//                                                                            }){
//                                                                                @Override
//                                                                                protected Map<String, String> getParams() throws AuthFailureError {
//                                                                                    Map<String,String> map1 = new HashMap<>();
//                                                                                    map1.put("order_id",idord.getText().toString());
//                                                                                    return map1;
//                                                                                }
//                                                                            };
//                                                                            RequestQueue res1 = Volley.newRequestQueue(results.this);
//                                                                            res1.add(spree);


                                                                            final int me = 1;


                                                                            StringRequest uprec = new StringRequest(Request.Method.POST,
                                                                                    URL_RECEPIT, new Response.Listener<String>() {
                                                                                @Override
                                                                                public void onResponse(String response) {
                                                                                    try {
                                                                                        JSONObject jsonObject1 = new JSONObject(response);
                                                                                        String successs = jsonObject1.getString("success");

                                                                                        if (successs.equals("1")){
                                                                                            Toast.makeText(results.this, "Receipt updated", Toast.LENGTH_SHORT).show();
                                                                                        }

                                                                                    } catch (JSONException e) {
                                                                                        e.printStackTrace();
                                                                                        Toast.makeText(results.this, "Ero ka!"+ e.toString(), Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            }, new Response.ErrorListener() {
                                                                                @Override
                                                                                public void onErrorResponse(VolleyError error) {
                                                                                    error.printStackTrace();
                                                                                    Toast.makeText(results.this, "Ero ka!"+ error.toString(), Toast.LENGTH_SHORT).show();

                                                                                }
                                                                            }){
                                                                                @Override
                                                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                                                    Map<String,String> map1 = new HashMap<>();
                                                                                    map1.put("order_id",idord.getText().toString());
                                                                                    map1.put("total",totalseen.getText().toString());
                                                                                    map1.put("status",String.valueOf(me));
                                                                                    return map1;
                                                                                }
                                                                            };
                                                                            final RequestQueue result = Volley.newRequestQueue(results.this);
                                                                            result.add(uprec);

                                                                            StringRequest stri1 = new StringRequest(Request.Method.POST,
                                                                                    URL_TABLE, new Response.Listener<String>() {
                                                                                @Override
                                                                                public void onResponse(String response) {

                                                                                    try {
                                                                                        JSONObject jsonObject1 = new JSONObject(response);
                                                                                        String successs = jsonObject1.getString("success");

                                                                                        if (successs.equals("1")){
                                                                                            Toast.makeText(results.this, "Table change", Toast.LENGTH_SHORT).show();
//                                                                                            startActivity(new Intent(results.this,reserlist.class));
                                                                                            Intent in = new Intent(results.this,menustart.class);
                                                                                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                            startActivity(in);
                                                                                            finish();

                                                                                            progressDialog.dismiss();
                                                                                        }

                                                                                    } catch (JSONException e) {
                                                                                        progressDialog.dismiss();
                                                                                        e.printStackTrace();
                                                                                        Toast.makeText(results.this, "Ero ka!"+ e.toString(), Toast.LENGTH_SHORT).show();

                                                                                    }

                                                                                }
                                                                            }, new Response.ErrorListener() {
                                                                                @Override
                                                                                public void onErrorResponse(VolleyError error) {
                                                                                    error.printStackTrace();
                                                                                    Toast.makeText(results.this, "Erokon taka!"+ error.toString(), Toast.LENGTH_SHORT).show();

                                                                                }
                                                                            }){
                                                                                @Override
                                                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                                                    Map<String,String> zero = new HashMap<>();
                                                                                    zero.put("table_id",tabs.getText().toString().trim());
                                                                                    zero.put("status","1");
                                                                                    return zero;
                                                                                }
                                                                            };

                                                                            RequestQueue requestQueue1 = Volley.newRequestQueue(results.this);
                                                                            requestQueue1.add(stri1);





                                                                        }
                                                                    });
                                                                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            dialog.dismiss();
                                                                        }
                                                                    });
                                                                    AlertDialog alert = builder.create();
                                                                    alert.show();
                                                                }
                                                            });



                                                        }
                                                    }catch (JSONException e){

                                                        e.printStackTrace();
                                                        Toast.makeText(results.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                            , new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            Toast.makeText(results.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String,String> prams = new HashMap<>();
                                            prams.put("order_id",idord.getText().toString());
                                            return prams;
                                        }
                                    };
                                    Volley.newRequestQueue(results.this).add(stringRequest);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(results.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(results.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String > params = new HashMap<>();
                params.put("table_id",tabs.getText().toString());
                params.put("status","1");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequestes);



    }





}
