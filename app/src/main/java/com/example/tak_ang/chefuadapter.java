package com.example.tak_ang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class chefuadapter extends RecyclerView.Adapter<chefuadapter.chefuViewHolder> implements
        Filterable {

    Context context;
    List<chefuget> chefugetList;
    List<chefuget> chefugets;
    private static final String URL_MENUSAVE= "https://takangrestaurant.xyz/connect/menusave.php";

    public chefuadapter(Context context,List<chefuget>chefugetList){
        this.context = context;
        this.chefugetList = chefugetList;
        this.chefugets = chefugetList;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key =constraint.toString();
                if (Key.isEmpty()){
                    chefugets = chefugetList;
                }else{
                    List<chefuget> isList = new ArrayList<>();
                    for (chefuget list : chefugetList){
                        if (list.getCategoryname().toLowerCase().contains(Key.toLowerCase())){
                            isList.add(list);
                        }
                    }
                    chefugets = isList;

                }

                FilterResults filterResults =new FilterResults();
                filterResults.values = chefugets;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                chefugets =(List<chefuget>) results.values;
                notifyDataSetChanged();
            }
        };

    }

    @NonNull
    @Override
    public chefuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chefmenu,parent,false);
        return new chefuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final chefuViewHolder holder, int position) {

        String name = chefugets.get(position).getName();
        String categ = chefugets.get(position).getCategoryname();
        int status = chefugets.get(position).getStatus();
        int menid = chefugets.get(position).getMenu_id();

        holder.menuID.setText(String.valueOf(menid));

        String stas = Integer.toString(status);


        switch (stas){
            case "0":
                holder.c.setVisibility(View.VISIBLE);
                holder.x.setVisibility(View.GONE);
                break;
            case "1":
                holder.c.setVisibility(View.GONE);
                holder.x.setVisibility(View.VISIBLE);
                break;
        }

        holder.menuname.setText(name);
        holder.categoryname.setText(categ);

        holder.x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stri1 = new StringRequest(Request.Method.POST,
                        URL_MENUSAVE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            String successs = jsonObject1.getString("success");

                            if (successs.equals("1")){
                                Toast.makeText(context, "Table change", Toast.LENGTH_SHORT).show();
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
                        zero.put("menu_id",holder.menuID.getText().toString().trim());
                        zero.put("status","0");
                        return zero;
                    }
                };

                RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                requestQueue1.add(stri1);
            }
        });


        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stri12 = new StringRequest(Request.Method.POST,
                        URL_MENUSAVE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            String successs = jsonObject1.getString("success");

                            if (successs.equals("1")){
                                Toast.makeText(context, "Table change", Toast.LENGTH_SHORT).show();
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
                        zero.put("menu_id",holder.menuID.getText().toString().trim());
                        zero.put("status","1");
                        return zero;
                    }
                };

                RequestQueue requestQueue12 = Volley.newRequestQueue(context);
                requestQueue12.add(stri12);



            }
        });


    }

    @Override
    public int getItemCount() {
        return chefugets.size();
    }

    class chefuViewHolder extends RecyclerView.ViewHolder {
        TextView menuname,categoryname,menuID;
        CardView meclick;
        CircleImageView x,c;
            public chefuViewHolder(@NonNull View itemView) {
                super(itemView);

                menuID = itemView.findViewById(R.id.menu_id);
                menuname = itemView.findViewById(R.id.menunames);
                categoryname = itemView.findViewById(R.id.categoryname);
                x = itemView.findViewById(R.id.x);
                c = itemView.findViewById(R.id.check);


            }
        }


}
