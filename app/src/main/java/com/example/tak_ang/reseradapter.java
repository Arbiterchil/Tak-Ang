package com.example.tak_ang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class reseradapter extends RecyclerView.Adapter<reseradapter.reserViewHolder>{


    Context context;
    List<reserget>  resergets;
    String s;
    private static String URL_READ = "https://takangrestaurant.xyz/connect/read.php";
    private static String URL_RECIP = "https://takangrestaurant.xyz/connect/recipt.php";
    public reseradapter(Context context,List<reserget> resergets,String s){

        this.context = context;
        this.resergets = resergets;
        this.s = s;

    }


    @NonNull
    @Override
    public reserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.myreset,parent,false);
        return new reserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final reserViewHolder holder, int position) {
        int id = resergets.get(position).getUser_id();
       final int status = resergets.get(position).getStatus();
        int bookids = resergets.get(position).getBooking_id();
        int table = resergets.get(position).getTable_id();
        String check = resergets.get(position).getCheck_in();
        String stats = Integer.toString(status);
        holder.id.setText(s);
        holder.bookid.setText(String.valueOf(bookids));
        holder.checkin.setText(check);
        holder.tabid.setText(String.valueOf(table));
        String datetime = holder.checkin.getText().toString().trim();


       Date date = new Date();
        String strDateFormat = "yyyy-MM-dd hh:mm:ss" ;
        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
        String timda = objSDF.format(date);
        switch (stats) {
            case "0":
                if (datetime.compareTo(timda) > 0){
                    String hows = "PENDING";
                    holder.status.setText(hows);
                }else if (datetime.compareTo(timda) < 0){
                    String how = "EXPIRED";
                    holder.status.setText(how);
                    holder.meclick.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "EXPIRED by the Administrator.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if (datetime.compareTo(timda) == 0){
                    String howl= "Undentified Unknown";
                    holder.status.setText(howl);
                }


                break;
            case "1":
                String deep = "CONFIRMED";
                holder.status.setText(deep);
                holder.meclick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_RECIP,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");
                                    if (success.equals("1")){
                                        Intent intent = new Intent(context,menurestab.class);
                                        intent.putExtra("user_id",holder.id.getText().toString().trim());
                                        intent.putExtra("table_id",holder.tabid.getText().toString().trim());
                                        context.startActivity(intent);
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                    Toast.makeText(context, "Error "+e.toString(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast.makeText(context, "Malaka "+error.toString(), Toast.LENGTH_SHORT).show();

                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String,String> param = new HashMap<>();
                        param.put("user_id",String.valueOf(s));
                        param.put("table_id",holder.tabid.getText().toString().trim());
                        param.put("chef_id","0");
                        return param;
                    }

                };
                RequestQueue requestQueues = Volley.newRequestQueue(context);
                requestQueues.add(stringRequest);


//                        Intent in = new Intent(context,menurestab.class);
//                        in.putExtra("user_id",holder.id.getText().toString().trim());
//                        in.putExtra("table_id",holder.tabid.getText().toString().trim());
//                        context.startActivity(in);
                    }});
                break;
            case "2":
                String your = "SKIP";
                holder.status.setText(your);
                break;
            case "3":
                String Love = "USED";
                holder.status.setText(Love);
                break;
            case "4":
                String yourLove = "CANCELED";
                holder.status.setText(yourLove);
                holder.meclick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "This is Cancel Process cannot proceed.", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case "5":
                String yourLoves = "PENDING";
                holder.status.setText(yourLoves);
                holder.meclick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "This is Pending Process cannot proceed.", Toast.LENGTH_SHORT).show();
                    }
                });
                break;


        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_READ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("read");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String fullnames = object.getString("fullname").trim();

                            holder.fname.setText(fullnames);

                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(context, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Malaka "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String > params = new HashMap<>();
                params.put("user_id",s);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    @Override
    public int getItemCount() {
        return resergets.size();
    }

        class reserViewHolder extends RecyclerView.ViewHolder{
            TextView id,fname,checkin,tabid,bookid,status;
            CardView meclick;
            public reserViewHolder(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.idnimu);
                fname = itemView.findViewById(R.id.fname);
                checkin = itemView.findViewById(R.id.chicken);
                tabid = itemView.findViewById(R.id.tabiddaw);
                bookid = itemView.findViewById(R.id.bokod);
                status = itemView.findViewById(R.id.statuss);
                meclick = itemView.findViewById(R.id.ordermenow);
            }
        }
}
