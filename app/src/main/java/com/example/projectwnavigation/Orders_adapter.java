package com.example.projectwnavigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import models.DataConverter;
import models.DbHelper;
import models.MyDataProvider;
import models.Order;

public class Orders_adapter extends RecyclerView.Adapter<Orders_adapter.MyViewHolder>{
    private Context context;
    Activity activity;
    MyDataProvider provider;
    ArrayList<Order> orders;

    public  Orders_adapter(Activity activity, Context context, ArrayList<Order> orders) {
        this.context = context;
        this.activity = activity;
        this.orders = orders;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView id, title, section, price, descr, deadline, createdDate;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.orders_adapter_title);
            section = itemView.findViewById(R.id.orders_adapter_section);
            // sectionId_txt = itemView.findViewById(R.id.executors_adapter_);
            id = itemView.findViewById(R.id.executors_adapter_id);
            price = itemView.findViewById(R.id.orders_adapter_price);
            descr = itemView.findViewById(R.id.orders_adapter_descr);
            deadline = itemView.findViewById(R.id.orders_adapter_deadline);
            createdDate= itemView.findViewById(R.id.orders_adapter_created);

            adapter_layout = itemView.findViewById(R.id.orders_adapter_row_layout);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_orders_adapter_row, parent, false);
        return new Orders_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Orders_adapter.MyViewHolder holder, int position) {
       provider = new MyDataProvider(context);
       holder.title.setText(orders.get(position).getTitle());
       holder.descr.setText(orders.get(position).getDescription());
        holder.price.setText(orders.get(position).getPrice() +"");
       String created =  DataConverter.convertLongToDataString(orders.get(position).getCreated_date());
        holder.createdDate.setText(created);
        String deadlinetext =  DataConverter.convertLongToDataString(orders.get(position).getDeadline());
        holder.deadline.setText(deadlinetext);
        holder.id.setText(orders.get(position).getId()+"");
        holder.section.setText(orders.get(position).getSection()+"");

        holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int d = Integer.parseInt(holder.id.getText().toString());
                Intent intent = new Intent(context, Orders_update_activity.class);
                intent.putExtra("orderId", d);

                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


}
