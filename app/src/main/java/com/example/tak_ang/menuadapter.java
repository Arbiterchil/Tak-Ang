package com.example.tak_ang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.volley.Request.Method.POST;

public class menuadapter extends RecyclerView.Adapter<menuadapter.menuViewHolder>
        implements Filterable {


    private Context ctx;
    private List<menuget> menulists;
    private List<menuget> listmenu;
    SharedPreferences sharedPreferences;
    String s;
    SessionManager sessionManager;
    String getId;
    private static final String URL_ORDER= "https://takangrestaurant.xyz/connect/orderID.php";
    private static String URL_OS = "https://takangrestaurant.xyz/connect/ordersave.php";
    public menuadapter(Context ctx,List<menuget>menulists,String s){

        this.ctx = ctx;
        this.menulists =menulists;
        this.listmenu = menulists;
        this.s = s;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key =constraint.toString();

                if (Key.isEmpty()){
                    listmenu = menulists;
                }else{
                    List<menuget> isList = new ArrayList<>();

                    for (menuget list : menulists) {
                        String hehe = String.valueOf(list.getCategoty_id());
                        if (list.getName().toLowerCase().contains(Key.toLowerCase())) {
                            isList.add(list);
                        }if (hehe.toLowerCase().contains(Key.toLowerCase())){
                            isList.add(list);
                        }
                    }
                    listmenu = isList;

                }

                FilterResults filterResults =new FilterResults();
                filterResults.values = listmenu;


                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                listmenu =(List<menuget>) results.values;
                notifyDataSetChanged();


            }
        };
    }

    @NonNull
    @Override
    public menuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.menucard,parent,false);
        return new menuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final menuViewHolder holder, int position) {

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");
        arrayList.add("6");
        arrayList.add("7");
        arrayList.add("8");
        arrayList.add("9");
        arrayList.add("10");
        arrayList.add("11");
        arrayList.add("12");
        arrayList.add("13");
        arrayList.add("14");
        arrayList.add("15");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ctx,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spindick.setAdapter(arrayAdapter);

        final int quansel = Integer.parseInt(holder.spindick.getSelectedItem().toString().trim());
//        sessionManager = new SessionManager(ctx);
//        HashMap<String, String > user = sessionManager.getUserDeatils();
//        getId = user.get(sessionManager.USER_ID);
        final int menu_id = listmenu.get(position).getMenu_id();
        final String name = listmenu.get(position).getName();
        final String description = listmenu.get(position).getDescription();
        final int price = listmenu.get(position).getPrice();
        final String image_path = listmenu.get(position).getImage_path();
        final int category_name = listmenu.get(position).getCategoty_id();

        String path = "https://takangrestaurant.xyz/admin/item-image/"+image_path;
        Picasso.get().load(path).into(holder.circleImageView);

        String pricetag = Integer.toString(price);

        String hehe =  String.valueOf(category_name);
        switch (hehe){
            case "1":
                String h ="Main";
                holder.category.setText(h);
                break;
            case "2":
                String a ="Desserts";
                holder.category.setText(a);
                break;
            case "3":
                String h1 = "Drinks";
                holder.category.setText(h1);
                break;
            case "4":
                String a1 = "Special";
                holder.category.setText(a1);
                break;
        }


        holder.menitits.setText(name);
        holder.menupric.setText(pricetag);
        holder.menudes.setText(description);




        final String oderid = holder.idor.getText().toString();
        final int is = 1;
        final String menuid = Integer.toString(menu_id);
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
                                    int order_id = object.getInt("order_id");
                                    holder.idor.setText(String.valueOf(order_id));
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ctx, "Error daw "+e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, "Error "+error.toString(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String > params = new HashMap<>();
                params.put("table_id",s);
                params.put("status","1");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequestes);

        holder.meclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(POST, URL_OS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");
                                    if (success.equals("1")){

                                        Toast.makeText(ctx, "You Ordered "+ name
                                                , Toast.LENGTH_SHORT).show();
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                    Toast.makeText(ctx, "Error "+e.toString(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast.makeText(ctx, "Malaka "+error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String,String> param = new HashMap<>();
                        param.put("quantity",holder.spindick.getSelectedItem().toString());
                        param.put("menu_id",String.valueOf(menu_id));
                        param.put("order_id", holder.idor.getText().toString().trim());
                        return param;
                    }
                };
                RequestQueue requestQueues = Volley.newRequestQueue(ctx);
                requestQueues.add(stringRequest);
            }
        });




    }

    @Override
    public int getItemCount() {
        return listmenu.size();
    }


    class menuViewHolder extends RecyclerView.ViewHolder{

        TextView menitits,menupric,menudes,idor,category;
        CardView meclick;
        Spinner spindick;
        CircleImageView circleImageView;
        public menuViewHolder(@NonNull View itemview){
            super(itemview);
            menitits = itemview.findViewById(R.id.titlemenu);
            menupric = itemview.findViewById(R.id.pricemenu);
            menudes = itemview.findViewById(R.id.descip);
            meclick = itemview.findViewById(R.id.clickme);
            idor = itemview.findViewById(R.id.idor);
            spindick = itemview.findViewById(R.id.dickbutt);
            category = itemview.findViewById(R.id.category);
            circleImageView = itemview.findViewById(R.id.menuimage);
        }



    }



}
