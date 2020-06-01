package com.example.projectwnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestActivity extends AppCompatActivity {
    Button persons, executors, orders, notify, services, bookmr ,test_json, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        persons = findViewById(R.id.test_list_of_persons);
        executors = findViewById(R.id.test_list_of_executors);
        orders =  findViewById(R.id.test_list_of_orders);
        notify = findViewById(R.id.test_list_of_notify);
        services = findViewById(R.id.test_list_of_services);
        bookmr = findViewById(R.id.test_list_of_bkmrks);
        test_json = findViewById(R.id.test_json);
        image = findViewById(R.id.test_image);


        persons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TestActivity.this, Persons_controlList_activity.class);
                startActivity(intent1);
            }
        });

        executors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(TestActivity.this, Executors_list_activity.class);
                startActivity(intent2);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orders = new Intent(TestActivity.this, Orders_list_activity.class);
                startActivity(orders);
            }
        });

       notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notify = new Intent(TestActivity.this, Notify_list_activity.class);
                startActivity(notify);
            }
        });

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notify = new Intent(TestActivity.this, Service_list_activity.class);
                startActivity(notify);
            }
        });

        bookmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notify = new Intent(TestActivity.this, Bookmarks_list_activity.class);
                startActivity(notify);
            }
        });

        test_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent json = new Intent(TestActivity.this, TestJsonActivity.class);
                startActivity(json);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent json = new Intent(TestActivity.this, TestImageActivity.class);
                startActivity(json);
            }
        });
    }

}
