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
import models.Service;

public class Service_list_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button add_button;
    MyDataProvider provider;
    Service_adapter service_adapter;
    ArrayList<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        add_button = findViewById(R.id.services_list_btn_addService);

        provider = new MyDataProvider(this);

        recyclerView = findViewById(R.id.services_list_rv);
        insertArray();
        service_adapter = new Service_adapter(Service_list_activity.this, this, services);
        recyclerView.setAdapter(service_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Service_list_activity.this, Service_createActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void insertArray() {
        services = provider.getServices();
    }



}
