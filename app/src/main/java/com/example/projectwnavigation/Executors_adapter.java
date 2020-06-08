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

import models.Executor;
import models.MyDataProvider;

public class Executors_adapter extends RecyclerView.Adapter<Executors_adapter.MyViewHolder> {

    private Context context;
    Activity activity;
    MyDataProvider provider;
    ArrayList<Executor> executors;

    Executors_adapter(Activity activity, Context context, ArrayList<Executor> executors) {
        this.context = context;
        this.activity = activity;
        this.executors = executors;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_executors_adapter_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        provider = new MyDataProvider(context);

        String name = "";
        holder.executorId_txt.setText(executors.get(position).getId() + "");
        holder.spcltn_txt.setText(executors.get(position).getSpecialztn());

        if (String.valueOf(executors.get(position).getPersonId()).length() >= 0 ) {
            int personId = executors.get(position).getPersonId();
             name = provider.getPersonNameFromExecutorId(personId);
        }

        holder.personId_txt.setText(name);
        holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int d = Integer.parseInt(holder.executorId_txt.getText().toString());

                Intent intent = new Intent(context, Executor_update_activity.class);
                intent.putExtra("executorId", d);

                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(executors == null){
            return 0;
        }
        return executors.size();
    }

  public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView executorId_txt, personId_txt,  spcltn_txt;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            executorId_txt = itemView.findViewById(R.id.orders_adapter_layout);
            personId_txt = itemView.findViewById(R.id.executors_adapter_name);
            spcltn_txt = itemView.findViewById(R.id.executors_adapter_spcl);


            adapter_layout = itemView.findViewById(R.id.executor_adapter_row_layout);
        }
    }
}
