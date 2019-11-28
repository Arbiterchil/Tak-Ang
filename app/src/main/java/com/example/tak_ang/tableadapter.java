package com.example.tak_ang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;

import static com.android.volley.Request.Method.POST;

public class tableadapter extends RecyclerView.Adapter<tableadapter.tableViewHolder> {

    Context context;
    List<tableget> tablegets;
    RequestQueue requestQueue;
    String getID,orget;
    private static String URL_TAB = "https://takangrestaurant.xyz/connect/bookingnid.php";
    private static String URL_BOKID = "https://takangrestaurant.xyz/connect/bookyawa.php";
    private static String URL_RECIP = "https://takangrestaurant.xyz/connect/recipt.php";
    private static String URL_BOK = "https://takangrestaurant.xyz/connect/taboo.php";
    private static String URL_UPDATE = "https://takangrestaurant.xyz/connect/updatetab.php";
    String s,ss;

    public tableadapter(Context context,List<tableget>tablegets,String s){

        this.context = context;
        this.tablegets = tablegets;
        this.s = s;
    }

    @NonNull
    @Override
    public tableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tablegrid,parent,false);
        return new tableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final tableViewHolder holder, int position) {
        final String tablename = tablegets.get(position).getTable_name();
        final int id = tablegets.get(position).getStatus();
        final int uid = tablegets.get(position).getTable_id();
        final int capa = tablegets.get(position).getCapacity();

        String cape = Integer.toString(capa);
        holder.cate.setText(cape);
        final String  ids = Integer.toString(uid);

        final String num = Integer.toString(id);
        final int user_id,table_id;

        if (num.equals("1")){

            String text = "Available";

            holder.staus.setText(text);

        }else if (num.equals("0"))
        {
            String text = "Not Available";

            holder.staus.setText(text);
            holder.linearLayout.setVisibility(View.GONE);
            holder.meclicks.setVisibility(View.GONE);
        }
        holder.tablename.setText(tablename);
        holder.tableid.setText(ids);
        holder.user.setText(s);

        final String tab1 = holder.tableid.getText().toString();
        final String user1 = holder.user.getText().toString();

        final int hahazero = 0;


        holder.meclicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please Wait......");
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(POST, URL_RECIP,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");
                                    if (success.equals("1")){
                                        progressDialog.dismiss();
//                                        Toast.makeText(context, "Table Number "+tab1, Toast.LENGTH_SHORT).show();

                                        Toast.makeText(context, ss, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context,menulist.class);
                                        intent.putExtra("user_id",user1);
                                        intent.putExtra("table_id",tab1);
                                        context.startActivity(intent);
                                        ((reserlist)context).finish();
                                    }
                                }catch (JSONException e){
                                    progressDialog.dismiss();
                                    e.printStackTrace();
                                    Toast.makeText(context, "Error "+e.toString(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                error.printStackTrace();
                                Toast.makeText(context, "Malaka "+error.toString(), Toast.LENGTH_SHORT).show();

                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String,String> param = new HashMap<>();
                       param.put("user_id",String.valueOf(s));
                       param.put("table_id",String.valueOf(uid));
                       param.put("chef_id","0");
                        return param;
                    }

                };
                RequestQueue requestQueues = Volley.newRequestQueue(context);
                requestQueues.add(stringRequest);

                StringRequest stringRequest1 = new StringRequest(Request.Method.POST,
                        URL_UPDATE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            String successs = jsonObject1.getString("success");

                            if (successs.equals("1")){
//                                Toast.makeText(context, "Table get", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Ero ka!"+ e.toString(), Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(context, "Erokon taka!"+ error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> zero = new HashMap<>();
                        zero.put("table_id",holder.tableid.getText().toString());
                        zero.put("status",String.valueOf(hahazero));
                        return zero;
                    }
                };

                RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                requestQueue1.add(stringRequest1);

                Date date = new Date();
                String strDateFormat = "yyyy-MM-dd hh:mm:ss a" ;
                SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
                final String timda = objSDF.format(date);

                StringRequest hehe = new StringRequest(Request.Method.POST, URL_BOK
                        , new Response.Listener<String>() {
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


                                                    StringRequest res3 = new StringRequest(Request.Method.POST,
                                                            URL_TAB, new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            try {
                                                                JSONObject object1 = new JSONObject(response);
                                                                String suckmydong = object1.getString("success");
                                                                if (suckmydong.equals("1")){
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show();
                                                                }

                                                            } catch (JSONException e) {
                                                                progressDialog.dismiss();
                                                                e.printStackTrace();
                                                                Toast.makeText(context, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Toast.makeText(context, "Error"+error.toString(), Toast.LENGTH_SHORT).show();
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

                                                    RequestQueue req3 = Volley.newRequestQueue(context);
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
                                        params.put("user_id",String.valueOf(s));

                                        return params;
                                    }
                                };

                                RequestQueue req2 =  Volley.newRequestQueue(context);
                                req2.add(res2);

                                                }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, "Error"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>map1= new HashMap<>();
                        map1.put("user_id",String.valueOf(s));
                        return map1;
                    }
                };
                RequestQueue req1 = Volley.newRequestQueue(context);
                req1.add(hehe);








            }
        });







    }


    @Override
    public int getItemCount() {
        return tablegets.size();
    }

    class  tableViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        CardView meclicks;
        TextView tablename,tableid,staus,user,dateti,cate;



        public tableViewHolder(@NonNull View itemView) {
            super(itemView);

            meclicks = itemView.findViewById(R.id.clickmen);
            tablename = itemView.findViewById(R.id.tablename);
            tableid = itemView.findViewById(R.id.tableid);
            staus = itemView.findViewById(R.id.staus);
            user = itemView.findViewById(R.id.userid);
            dateti = itemView.findViewById(R.id.dateti);
            linearLayout = itemView.findViewById(R.id.hidenseek);

            cate = itemView.findViewById(R.id.capacitu);




        }

    }


}
