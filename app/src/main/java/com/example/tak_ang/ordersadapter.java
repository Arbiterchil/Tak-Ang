package com.example.tak_ang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
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

public class ordersadapter extends RecyclerView.Adapter<ordersadapter.ordersViewHolder> {
    Context context;
    List<orderitem> listorder;
    String getId;
    SessionManager sessionManager;
    private static final String URL_DELETE= "https://takangrestaurant.xyz/connect/delor.php";

    public ordersadapter(Context context,List<orderitem> listorder){

        this.context =context;
        this.listorder = listorder;
    }


    @NonNull
    @Override
    public ordersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.orders,parent,false);
        return new ordersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ordersViewHolder holder, int position) {

        String names = listorder.get(position).getName();
        int quantity = listorder.get(position).getQuantity();
        double price = listorder.get(position).getPrice();
        int item = listorder.get(position).getOrder_item_id();
        holder.name.setText(names);
        holder.quantity.setText(String.valueOf(quantity));
        holder.price.setText(String.valueOf(price));

        double satan =  quantity * price;
        String jesus = String.valueOf(satan);
        holder.total.setText(jesus);
        holder.orderit.setText(String.valueOf(item));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Order");
                builder.setMessage("Change of Heart? Delete or Not?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject1 = new JSONObject(response);
                                            String successs = jsonObject1.getString("success");
                                            if (successs.equals("1")){
                                                Toast.makeText(context, "Order Remove", Toast.LENGTH_SHORT).show();
                                                ((purposetwo)context).finish();
                                                ((purposetwo)context).overridePendingTransition( 0, 0);
                                                ((purposetwo)context).startActivity(((purposetwo) context).getIntent());
                                                ((purposetwo)context).overridePendingTransition( 0, 0);

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(context, "Ero ka!"+ e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String,String> map =new HashMap<>();
                                map.put("order_item_id",holder.orderit.getText().toString().trim());
                                return map;
                            }
                        };
                        Volley.newRequestQueue(context).add(stringRequest);

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

    @Override
    public int getItemCount() {
        return listorder.size();
    }


    class ordersViewHolder extends RecyclerView.ViewHolder {

        TextView name,price,quantity,total,orderit;
        CardView cardView ;



        public ordersViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.memes);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            total = itemView.findViewById(R.id.total);
            orderit = itemView.findViewById(R.id.orderitem);




        }
    }
}
