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
import models.Service;

public class Service_adapter extends RecyclerView.Adapter<Service_adapter.MyViewHolder> {
    private Context context;
    Activity activity;
    MyDataProvider provider;
    ArrayList<Service> services;

    Service_adapter(Activity activity, Context context, ArrayList<Service> services) {
        this.context = context;
        this.activity = activity;
        this.services = services;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView id, title, price;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.service_adapter_id);
            title = itemView.findViewById(R.id.service_adapter_title);
            price= itemView.findViewById(R.id.service_adapter_price);

            adapter_layout = itemView.findViewById(R.id.service_adapter_row_layout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_service_adapter_row, parent, false);
        return new Service_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);
        holder.title.setText(services.get(position).getTitle());
        holder.price.setText(services.get(position).getPrice()+"");
        holder.id.setText(services.get(position).getId() +"");

        holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(holder.id.getText().toString());
                Intent intent = new Intent(context, Service_update_activity.class);
                intent.putExtra("serviceId", id);

                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

}
