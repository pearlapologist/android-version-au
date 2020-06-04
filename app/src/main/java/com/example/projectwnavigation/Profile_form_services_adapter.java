package com.example.projectwnavigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import models.DataConverter;
import models.MyDataProvider;
import models.Service;

public class Profile_form_services_adapter extends RecyclerView.Adapter<Profile_form_services_adapter.MyViewHolder> {
    private Context context;
    MyDataProvider provider;

    Profile_createFormActivity profile_createFormActivity;
    ArrayList<Service> services;

    public Profile_form_services_adapter(Profile_createFormActivity profile_createFormActivity, Context context, ArrayList<Service> services) {
        this.context = context;
        this.profile_createFormActivity = profile_createFormActivity;
        this.services = services;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_profile_form_services_adapter_row, parent, false);
        return new Profile_form_services_adapter.MyViewHolder(view, profile_createFormActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);
        holder.title.setText(services.get(position).getTitle());
        holder.price.setText(services.get(position).getPrice() + "");

        if (!profile_createFormActivity.contextModeEnable) {
            holder.checkBox.setVisibility(View.GONE);
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }

        /*holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int d = Integer.parseInt(holder.id.getText().toString());
                Intent intent = new Intent(context, Orders_update_activity.class);
                intent.putExtra("orderId", d);

                activity.startActivityForResult(intent, 1);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, price;
        LinearLayout adapter_layout;
        CheckBox checkBox;
        View view;

        public MyViewHolder(@NonNull View itemView, Profile_createFormActivity activity) {
            super(itemView);

            title = itemView.findViewById(R.id.profile_form_serv_adapter_title);
            price = itemView.findViewById(R.id.profile_form_serv_adapter_price);
            checkBox = itemView.findViewById(R.id.profile_form_serv_adapter_check);
            view = itemView;

            adapter_layout = itemView.findViewById(R.id.profile_form_serv_adapter_row);
            view.setOnLongClickListener(activity);
            checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            profile_createFormActivity.setSelection(v, getAdapterPosition());
        }

    }

    public void removeItem(ArrayList<Service> selectionList) {
        for (int i = 0; i < selectionList.size(); i++) {
            services.remove(selectionList.get(i));
            notifyDataSetChanged();
        }
    }
}
