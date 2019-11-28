package com.example.tak_ang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class menulist extends AppCompatActivity {
    private static final String URL= "https://takangrestaurant.xyz/connect/refe.php";

        List<menuget> menugets;
        RecyclerView recyclerView;
        EditText search;
        TextView userers,table;
        Button b;
        Dialog dialog;
        String getId,orget;
        Spinner spindicks;
        menuadapter menuadapter;
    SessionManager sessionManager;
    CharSequence sequence;
    private static final String URL_TABLE= "https://takangrestaurant.xyz/connect/updatetab.php";
    private static final String URL_ORDER= "https://takangrestaurant.xyz/connect/orderID.php";
    private static final String URL_category= "https://takangrestaurant.xyz/connect/categ.php";
    private static final String URL_Deleter= "https://takangrestaurant.xyz/connect/deleter.php";
    private static final String BOOKDEL= "https://takangrestaurant.xyz/connect/bookdel.php";
    private static String URL_BOKID = "https://takangrestaurant.xyz/connect/bookyawa.php";
    private static String URL_BODOLS = "https://takangrestaurant.xyz/connect/bodels.php";
    private ArrayList<categorynames> categorynames = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menulist);
        b = findViewById(R.id.receiptall);
        search = findViewById(R.id.searchme);
        spindicks = findViewById(R.id.spinmydick);
        recyclerView = findViewById(R.id.listmenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        menugets = new ArrayList<>();
        userers = findViewById(R.id.userid);
        table = findViewById(R.id.tableid);

        final String tab1 = getIntent().getStringExtra("table_id");
        table.setText(tab1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent ins = new Intent(menulist.this,results.class);
               ins.putExtra("table_id",tab1);
               startActivity(ins);
            }
        });
        SearchLoad();
        Categoryname();
    }
    private void Categoryname() {

//    StringRequest stringRequestes = new StringRequest(Request.Method.POST
//            , URL_category, new Response.Listener<String>() {
//        @Override
//        public void onResponse(String response) {
//            try {
//
//                JSONArray jsonArray = new JSONArray(response);
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    names.add(jsonObject.getString("name"));
//                }
//
//                spindicks.setAdapter(new ArrayAdapter<>
//                        (menulist.this, android.R.layout.simple_spinner_dropdown_item, names));
//
//
//            }catch (JSONException e){
//                e.printStackTrace();
//            }
//
//        }
//    }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//
//        }
//    }){
//        @Override
//        protected Map<String, String> getParams() throws AuthFailureError {
//            return super.getParams();
//        }
//    };
//    RequestQueue reqs = Volley.newRequestQueue(this);
//    reqs.add(stringRequestes);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Main");
        arrayList.add("Desserts");
        arrayList.add("Drinks");
        arrayList.add("Special");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spindicks.setAdapter(arrayAdapter);





    }
    private void SearchLoad() {

        StringRequest stringRequest =new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0 ; i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int menu_id = jsonObject.getInt("menu_id");
                                 String name = jsonObject.getString("name");
                                 String description = jsonObject.getString("description");
                                 int price =  jsonObject.getInt("price");
                                 String image_path = jsonObject.getString("image_path");
                                 int category_name = jsonObject.getInt("category_id");

                                menuget menuget =new menuget(menu_id,name,description,price,image_path,category_name);
                                menugets.add(menuget);

                                String myString = getIntent().getStringExtra("table_id");
                                Toast.makeText(menulist.this, "Table Number :"+myString, Toast.LENGTH_SHORT).show();
                                final menuadapter menuadapter = new menuadapter(menulist.this,menugets,myString);

                                recyclerView.setAdapter(menuadapter);
                                menuadapter.notifyDataSetChanged();
                                search.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                    }
                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        menuadapter.getFilter().filter(s);
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                    }
                                });

                                spindicks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String[] timeHolder = {"1","2","3","4"};
                                        menuadapter.getFilter().filter(timeHolder[spindicks.getSelectedItemPosition()]);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });



                            }
                        }catch (JSONException e){
                                    e.printStackTrace();
                            Toast.makeText(menulist.this, "Something Error "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(menulist.this, "Something Error "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

    }
//    private void getOrderID() {
//
//        final String hahasange =getIntent().getStringExtra("user_id");
//
//        StringRequest stringRequestes = new StringRequest(Request.Method.POST, URL_ORDER,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String suckDick = jsonObject.getString("success");
//                            JSONArray jsonArray =  jsonObject.getJSONArray("Data");
//
//                            if (suckDick.equals("1")){
//
//                                for (int i= 0; i<jsonArray.length();i++){
//                                    JSONObject object = jsonArray.getJSONObject(i);
//                                    final String alshow = object.getString("status").trim();
//                                        final String order_id = object.getString("order_id").trim();
//                                        table.setText(order_id);
//
//
//                                }
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(menulist.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(menulist.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
//
//            }
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String > params = new HashMap<>();
//                params.put("user_id",hahasange);
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequestes);
//
//    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        final String user1 = getIntent().getStringExtra("user_id");
        final String tabi = getIntent().getStringExtra("table_id");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Deleter,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            String successs = jsonObject1.getString("success");
                            if (successs.equals("1")){
                                Toast.makeText(menulist.this, "Table Menu Cancel", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(menulist.this, "Ero ka!"+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(menulist.this, "haha"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> map1 = new HashMap<>();
                map1.put("table_id",table.getText().toString());
                return map1;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);



        StringRequest stri1 = new StringRequest(Request.Method.POST,
                URL_TABLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String successs = jsonObject1.getString("success");

                    if (successs.equals("1")){
                        Toast.makeText(menulist.this, "Table change Cancel", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(menulist.this,menustart.class));
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(menulist.this, "Ero ka!"+ e.toString(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(menulist.this, "Erokon taka!"+ error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> zero = new HashMap<>();
                zero.put("table_id",table.getText().toString().trim());
                zero.put("status","1");
                return zero;
            }
        };
        Volley.newRequestQueue(this).add(stri1);

                                StringRequest res2 =  new StringRequest(Request.Method.POST,
                                        URL_BOKID, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject1 = new JSONObject(response);
                                            String success =jsonObject1.getString("success");
                                            JSONArray jsonArray =jsonObject1.getJSONArray("read");
                                            if (success.equals("1")) {
                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                    JSONObject object = jsonArray.getJSONObject(i);
                                                    final String bokid = object.getString("booking_id").trim();
                                                    StringRequest res3 = new StringRequest(Request.Method.POST,
                                                            URL_BODOLS, new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            try {
                                                                JSONObject object1 = new JSONObject(response);
                                                                String suckmydong = object1.getString("success");
                                                                if (suckmydong.equals("1")){
                                                                    StringRequest nirvana = new StringRequest(Request.Method.POST, BOOKDEL,
                                                                            new Response.Listener<String>() {
                                                                                @Override
                                                                                public void onResponse(String response) {

                                                                                    try {
                                                                                        JSONObject  jsonObject = new JSONObject(response);
                                                                                        String suckmydick = jsonObject.getString("success");

                                                                                        if (suckmydick.equals("1")){
                                                                                            Toast.makeText(menulist.this, "All Green.", Toast.LENGTH_SHORT).show();
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
                                                        protected Map<String, String> getParams() {
                                                            Map<String,String> pa = new HashMap<>();
                                                            pa.put("user_id",String.valueOf(user1));
                                                            return pa;
                                                        }
                                                    };
                                                    Volley.newRequestQueue(menulist.this).add(nirvana);

                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                                Toast.makeText(menulist.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Toast.makeText(menulist.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }){
                                                        @Override
                                                        protected Map<String, String> getParams() {
                                                            Map<String,String> map2 = new HashMap<>();
                                                            map2.put("booking_id",bokid);
                                                            return map2;
                                                        }
                                                    };

                                                    RequestQueue req3 = Volley.newRequestQueue(menulist.this);
                                                    req3.add(res3);


                                                }
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
                                        Map<String,String > params = new HashMap<>();
                                        params.put("user_id",String.valueOf(user1));

                                        return params;
                                    }
                                };

                                RequestQueue req2 =  Volley.newRequestQueue(menulist.this);
                                req2.add(res2);







    }
}
