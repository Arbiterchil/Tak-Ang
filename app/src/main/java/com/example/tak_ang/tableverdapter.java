package com.example.tak_ang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static com.android.volley.Request.Method.POST;

public class tableverdapter extends RecyclerView.Adapter<tableverdapter.tableViewHolds> {


    Context context;
    List<tablever> tablegets;
    RequestQueue requestQueue;
    String getID,orget;
    private static String URL_RECIP = "https://takangrestaurant.xyz/connect/recipt.php";
    private static String URL_UPDATE = "https://takangrestaurant.xyz/connect/updatetab.php";
    String s;


    public tableverdapter(Context context ,List<tablever>tablegets, String s){

        this.context = context;
        this.tablegets = tablegets;
        this.s = s;
    }


    @NonNull
    @Override
    public tableverdapter.tableViewHolds onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tableresgrid,parent,false);
        return new tableViewHolds(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final tableverdapter.tableViewHolds holder, int position) {

        final String tablename = tablegets.get(position).getTable_name();
        final int id = tablegets.get(position).getStatus();
        final int uid = tablegets.get(position).getTable_id();
        final String  ids = Integer.toString(uid);
        final int cap = tablegets.get(position).getCapacity();

        final String fuck = Integer.toString(cap);

        holder.capisa.setText(fuck);

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

        final int hahazero = 1;

        holder.meclicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please Wait......");
                progressDialog.show();
//                StringRequest stringRequest = new StringRequest(POST, URL_RECIP,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try{
//                                    JSONObject jsonObject = new JSONObject(response);
//                                    String success = jsonObject.getString("success");
//                                    if (success.equals("1")){
//                                        progressDialog.dismiss();
//                                        Toast.makeText(context, "Table Number "+tab1, Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(context,Bok.class);
//                                        intent.putExtra("user_id",user1);
//                                        intent.putExtra("table_id",tab1);
//                                        context.startActivity(intent);
//                                        ((tableres)context).finish();
//                                    }
//                                }catch (JSONException e){
//                                    progressDialog.dismiss();
//                                    e.printStackTrace();
//                                    Toast.makeText(context, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                progressDialog.dismiss();
//                                error.printStackTrace();
//                                Toast.makeText(context, "Malaka "+error.toString(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        })
//                {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        Map<String,String> param = new HashMap<>();
//                        param.put("user_id",String.valueOf(s));
//                        param.put("table_id",String.valueOf(uid));
//                        param.put("chef_id","0");
//                        return param;
//                    }
//
//                };
//                RequestQueue requestQueues = Volley.newRequestQueue(context);
//                requestQueues.add(stringRequest);

                StringRequest stringRequest1 = new StringRequest(Request.Method.POST,
                        URL_UPDATE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            String successs = jsonObject1.getString("success");

                            if (successs.equals("1")){
                                Toast.makeText(context, "Table get", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context,Bok.class);
                                        intent.putExtra("user_id",user1);
                                        intent.putExtra("table_id",tab1);
                                        context.startActivity(intent);
                                        ((tableres)context).finish();

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



            }
        });

    }
    @Override
    public int getItemCount()
    {
        return tablegets.size();
    }

    class tableViewHolds extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        CardView meclicks;
        TextView tablename,tableid,staus,user,dateti,capisa;

        public tableViewHolds(@NonNull View itemView) {
            super(itemView);

            meclicks = itemView.findViewById(R.id.clickmen);
            tablename = itemView.findViewById(R.id.tablename);
            tableid = itemView.findViewById(R.id.tableid);
            staus = itemView.findViewById(R.id.staus);
            user = itemView.findViewById(R.id.userid);
            dateti = itemView.findViewById(R.id.dateti);
            linearLayout = itemView.findViewById(R.id.hidenseek);
            capisa = itemView.findViewById(R.id.capacitus);
        }
    }


}
