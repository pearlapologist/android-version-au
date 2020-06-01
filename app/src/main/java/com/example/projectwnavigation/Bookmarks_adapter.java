package com.example.projectwnavigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import models.Bookmarks;
import models.DbHelper;
import models.Executor;
import models.MyDataProvider;

public class Bookmarks_adapter extends RecyclerView.Adapter<Bookmarks_adapter.MyViewHolder> {
    private Context context;
    Activity activity;
    MyDataProvider provider;
    ArrayList<Bookmarks> bookmarks;

    Bookmarks_adapter(Activity activity, Context context, ArrayList<Bookmarks> bookmarks) {
        this.context = context;
        this.activity = activity;
        this.bookmarks = bookmarks;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView id, title;
        private ImageView image;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.bookm_adapter_title);
            id = itemView.findViewById(R.id.bookm_adapter_id);
            image = itemView.findViewById(R.id.bookm_adapter_image);

            adapter_layout = itemView.findViewById(R.id.bookm_adapter_row_layout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_bookmarks_adapter_row, parent, false);
        return new Bookmarks_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);

        Bookmarks bookm = bookmarks.get(position);
        int id = bookm.getId();
        holder.id.setText(id + "");

        Executor executor = provider.getExecutor(bookm.getExecutorId());

        String txtdesc = "";
        if (executor != null) {
            txtdesc = executor.getSpecialztn();
        }
        holder.title.setText(txtdesc);

        holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int d = Integer.parseInt(holder.id.getText().toString());
                Intent intent = new Intent(context, Bookmarks_update_activity.class);
                intent.putExtra("bookmId", d);

                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bookmarks == null) {
            return 0;
        }
        return bookmarks.size();
    }

}
