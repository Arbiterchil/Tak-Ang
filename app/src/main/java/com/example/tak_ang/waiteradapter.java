package com.example.tak_ang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class waiteradapter extends RecyclerView.Adapter<waiteradapter.waiterViewHolder> {


    Context context;
    List<aorder> aordeList;


    public waiteradapter(Context context,List<aorder> aordeList){
        this.context = context;
        this.aordeList = aordeList;
    }

    @NonNull
    @Override
    public waiterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chefpros,parent,false);
        return new waiterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final waiterViewHolder holder, int position) {
        final int haha = aordeList.get(position).getOrder_id();
        String dat = aordeList.get(position).getDate_ordered();
        int hoho = aordeList.get(position).getTable_id();
        final int hehe = aordeList.get(position).getStatus();
        holder.dateo.setText(dat);
        String sta = Integer.toString(hehe);
        switch (sta){
            case "1":
                String h1 = "UNPAID";
                holder.stats.setText(h1);
                break;
            case "2":
                String h2 = "PROCESSING";
                holder.stats.setText(h2);
                break;
            case "3":
                String h3 = "READY";
                holder.stats.setText(h3);
                break;
            case "4":
                String h4 = "SERVED";
                holder.stats.setText(h4);
                break;
        }

        holder.ord.setText(String.valueOf(haha));
        holder.tabnu.setText(String.valueOf(hoho));
        holder.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ordes = holder.ord.getText().toString().trim();
                String stats = String.valueOf(hehe);
                Intent ins = new Intent(context,purposetwo.class);
                ins.putExtra("table_id",ordes);
                ins.putExtra("status",stats);
                context.startActivity(ins);
                ((waiterside)context).finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return aordeList.size();
    }

    class waiterViewHolder extends  RecyclerView.ViewHolder{

        TextView dateo,tabnu,stats,ord;
        CardView me;

        public waiterViewHolder(@NonNull View itemView) {
            super(itemView);

            me= itemView.findViewById(R.id.cheme);
            dateo = itemView.findViewById(R.id.dateor);
            tabnu = itemView.findViewById(R.id.tabnum);
            stats = itemView.findViewById(R.id.statsnimu);
            ord = itemView.findViewById(R.id.orderDI);

        }
    }


}
