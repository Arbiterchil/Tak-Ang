package com.example.tak_ang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Bok extends AppCompatActivity {

    Button butlog,cancelation;
    TextView t1,t2,t3;
    DatePicker datePicker;
    Spinner h;
    String getId;
    SimpleDateFormat simpDate;
    SessionManager sessionManager;
    private static String URL_READ = "https://takangrestaurant.xyz/connect/read.php";
    private static String URL_BOK = "https://takangrestaurant.xyz/connect/resertab.php";
    private static String URL_TAB = "https://takangrestaurant.xyz/connect/bookingnid.php";
    private static String URL_BOKID = "https://takangrestaurant.xyz/connect/bokbwesit.php";
    private static final String URL_TABLE= "https://takangrestaurant.xyz/connect/updatetab.php";
    private DatePickerDialog.OnDateSetListener mDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bok);
        sessionManager = new SessionManager(this);
        HashMap<String, String > user = sessionManager.getUserDeatils();
        getId = user.get(sessionManager.USER_ID);

        h = findViewById(R.id.hour);
//        m = findViewById(R.id.minutes);
//        apm = findViewById(R.id.ampm);

        TimeShit();

        t1 = findViewById(R.id.usersss);
        t2 = findViewById(R.id.namaeh);
        t3 = findViewById(R.id.t3);
        butlog = findViewById(R.id.dbot);
        cancelation = findViewById(R.id.cancel);

        String AIDS = getIntent().getStringExtra("user_id");
        t1.setText(AIDS);


        butlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDuha();
            }
        });
        cancelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancel();
            }
        });


        namenamu();
        Datepacker();
    }
    private void Datepacker() {

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int y = cal.get(Calendar.YEAR);
                int m = cal.get(Calendar.MONTH);
                int d = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Bok.this
                        ,android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDate,y,m,d);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month = month +1;
            String dateform = year+"-"+month+"-"+dayOfMonth;
            t3.setText((dateform));
            }
        };


    }
    private void TimeShit() {


        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("10:00 am");
        arrayList.add("10:45 am");
        arrayList.add("11:30 am");
        arrayList.add("12:15 pm");
        arrayList.add("1:15 pm");
        arrayList.add("2:15 pm");
        arrayList.add("3:15 pm");
        arrayList.add("4:15 pm");
        arrayList.add("5:15 pm");
        arrayList.add("6:15 pm");
        arrayList.add("7:15 pm");
        arrayList.add("8:00 pm");
        arrayList.add("8:45 pm");
        arrayList.add("9:30 pm");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        h.setAdapter(arrayAdapter);
//        ArrayList<String> arrayLists = new ArrayList<>();
//        arrayLists.add("05");
//        arrayLists.add("10");
//        arrayLists.add("15");
//        arrayLists.add("20");
//        arrayLists.add("25");
//        arrayLists.add("30");
//        arrayLists.add("35");
//        arrayLists.add("40");
//        arrayLists.add("45");
//        arrayLists.add("50");
//        arrayLists.add("55");
//        arrayLists.add("60 ");
//        arrayLists.add("00");
//        ArrayAdapter<String> arrayAdapters = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, arrayLists);
//        arrayAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        m.setAdapter(arrayAdapters);
//
//        ArrayList<String> arrayListso = new ArrayList<>();
//        arrayListso.add("AM");
//        arrayListso.add("PM");
//        ArrayAdapter<String> arrayAdapterso = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, arrayListso);
//        arrayAdapterso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        apm.setAdapter(arrayAdapterso);
    }
    private void Cancel() {
        final String tab1s = getIntent().getStringExtra("table_id");
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,
                URL_TABLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String successs = jsonObject1.getString("success");

                    if (successs.equals("1")){
                        Toast.makeText(Bok.this, "Table Reservation Cancel", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Bok.this,tableres.class));
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Bok.this, "Ero ka!"+ e.toString(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Bok.this, "Erokon taka!"+ error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> zero = new HashMap<>();
                zero.put("table_id",tab1s);
                zero.put("status","1");
                return zero;
            }
        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);
    }
    private void saveDuha(){
        String[] timeHolder = {"10:00:00","10:45:00","11:30:00","12:15:00","13:15:00",
                "14:15:00","15:15:00","16:15:00","17:15:00"
                ,"18:15:00","19:15:00","20:00:00","20:45:00","21:30:00"};
        String dateforms = t3.getText().toString().trim();

        String timeform = h.getSelectedItem().toString().trim();

        final String s = getIntent().getStringExtra("user_id");
        final String finalform = dateforms+" "+ timeHolder[h.getSelectedItemPosition()];
//        final String finalform = dateforms+"|"+timeform;

        Toast.makeText(this, finalform, Toast.LENGTH_SHORT).show();

        final ProgressDialog progressDialog = new ProgressDialog(Bok.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.show();

        StringRequest res1 = new StringRequest(Request.Method.POST,
                URL_BOK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String suckmydick = jsonObject.getString("success");

                    if (suckmydick.equals("1")){

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

                                            final String tab1 = getIntent().getStringExtra("table_id");

                                            StringRequest res3 = new StringRequest(Request.Method.POST,
                                                    URL_TAB, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject object1 = new JSONObject(response);
                                                        String suckmydong = object1.getString("success");
                                                        if (suckmydong.equals("1")){
                                                            progressDialog.dismiss();
                                                            Toast.makeText(Bok.this, "Save Book", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(Bok.this,menustart.class));
                                                            finish();
                                                        }

                                                    } catch (JSONException e) {
                                                        progressDialog.dismiss();
                                                        e.printStackTrace();
                                                        Toast.makeText(Bok.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(Bok.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            }){
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    Map<String,String> map2 = new HashMap<>();
                                                    map2.put("table_id",tab1);
                                                    map2.put("booking_id",bokid);
                                                    return map2;
                                                }
                                            };

                                            RequestQueue req3 = Volley.newRequestQueue(Bok.this);
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
                                params.put("user_id",t1.getText().toString().trim());

                                return params;
                            }
                        };

                        RequestQueue req2 =  Volley.newRequestQueue(Bok.this);
                        req2.add(res2);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Bok.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Bok.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map1= new HashMap<>();
                map1.put("user_id",t1.getText().toString().trim());
                map1.put("check_in",finalform);
                return map1;
            }
        };
        RequestQueue req1 = Volley.newRequestQueue(this);
        req1.add(res1);
    }
    private void namenamu() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success =jsonObject.getString("success");
                            JSONArray jsonArray =jsonObject.getJSONArray("read");

                            if (success.equals("1")){
                                for (int i= 0; i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String fullnames = object.getString("fullname").trim();
                                    String emails = object.getString("email").trim();
                                    t2.setText(fullnames);

                                    final String getID = object.getString("user_id").trim();

                                }
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Bok.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(Bok.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
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
}
