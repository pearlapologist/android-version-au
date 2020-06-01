package com.example.projectwnavigation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import models.DbHelper;
import models.MyDataProvider;
import models.Notify;

public class Notify_list_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button add_button;
    MyDataProvider provider;
    ArrayList<Notify> notices;
    Notify_adapter notices_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_list);
        add_button = findViewById(R.id.notify_list_btn_addNotify);

        recyclerView = findViewById(R.id.notify_list_rv);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Notify_list_activity.this, Notify_createActivity.class);
                startActivity(intent);
            }
        });


        provider = new MyDataProvider(this);

        insertArray();
        notices_adapter = new Notify_adapter(Notify_list_activity.this, this, notices);
        recyclerView.setAdapter(notices_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void insertArray() {
        notices = provider.getNotices();
    }
}
