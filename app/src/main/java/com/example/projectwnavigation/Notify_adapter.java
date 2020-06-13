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

import models.MyUtils;
import models.MyDataProvider;
import models.Notify;

public class Notify_adapter  {
   /* private Context context;extends RecyclerView.Adapter<Notify_adapter.MyViewHolder>
    Activity activity;
    MyDataProvider provider;
    ArrayList<Notify> notices;

    Notify_adapter(Activity activity, Context context, ArrayList<Notify> notices) {
        this.context = context;
        this.activity = activity;
        this.notices = notices;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView id, text, personId, createdDate;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            id = itemView.findViewById(R.id.notify_adapter_id);
            text = itemView.findViewById(R.id.notify_adapter_text);
            personId = itemView.findViewById(R.id.notify_adapter_personId);
            createdDate= itemView.findViewById(R.id.notify_adapter_createdDate);

            adapter_layout = itemView.findViewById(R.id.notify_adapter_row_layout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_notify_adapter_row, parent, false);
        return new Notify_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);
        holder.text.setText(notices.get(position).getText());
        holder.personId.setText(notices.get(position).getPersonid()+"");
        holder.id.setText(notices.get(position).getId() +"");
        String created =  MyUtils.convertLongToDataString(notices.get(position).getCreatedDate());
        holder.createdDate.setText(created);


        holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int d = Integer.parseInt(holder.id.getText().toString());
                Intent intent = new Intent(context, Notify_update_activity.class);
                intent.putExtra("notifyId", d);

                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }
*/
}
