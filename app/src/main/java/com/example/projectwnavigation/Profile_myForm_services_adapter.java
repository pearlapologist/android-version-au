package com.example.projectwnavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import models.MyDataProvider;
import models.Service;

public class Profile_myForm_services_adapter extends RecyclerView.Adapter<Profile_myForm_services_adapter.MyViewHolder> {

    Context context;
    MyDataProvider provider;

    Profile_myForm_activity profile_myForm_activity;
    ArrayList<Service> services;

    public Profile_myForm_services_adapter(Profile_myForm_activity profile_myForm_activity, Context context, ArrayList<Service> services) {
        this.context = context;
        this.profile_myForm_activity = profile_myForm_activity;
        this.services = services;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, price;
        CheckBox checkBox;
        View view;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView, Profile_myForm_activity activity) {
            super(itemView);

            title = itemView.findViewById(R.id.profile_myForm_services_title);
            price = itemView.findViewById(R.id.profile_myForm_services_price);
            checkBox = itemView.findViewById(R.id.profile_myForm_services_checkbox);
            view = itemView;

            adapter_layout = itemView.findViewById(R.id.profile_myForm_services_adapter);
            view.setOnLongClickListener(activity);
            checkBox.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            checkBox.setChecked(true);
            profile_myForm_activity.setSelection(v, getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_profile_myform_services_adapter, parent, false);
        return new Profile_myForm_services_adapter.MyViewHolder(view, profile_myForm_activity);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);

        Service service = services.get(position);
        String title = "ничего";
        Double price = 0.0;

        try {
            title = service.getTitle();
            price = service.getPrice();
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.title.setText(title);
        holder.price.setText(price + "");

        if (!profile_myForm_activity.contextModeEnable) {
            holder.checkBox.setVisibility(View.GONE);
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        if (services == null) {
            return 0;
        }
        {
            return services.size();
        }
    }

    public void removeItem(ArrayList<Service> selectionList) {
        for (int i = 0; i < selectionList.size(); i++) {
            services.remove(selectionList.get(i));
            notifyDataSetChanged();
        }
    }
}
