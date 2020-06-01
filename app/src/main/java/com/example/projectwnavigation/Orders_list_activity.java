package com.example.projectwnavigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import models.DbHelper;
import models.MyDataProvider;
import models.Order;

public class Orders_list_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button add_button;
    MyDataProvider provider;
    ArrayList<Order> orders;
    Orders_adapter orders_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);

        add_button = findViewById(R.id.orders_list_btn_addOrder);

        recyclerView = findViewById(R.id.orders_list_rv);


        provider = new MyDataProvider(this);

        insertArray();
        orders_adapter = new Orders_adapter(Orders_list_activity.this, this, orders);
        recyclerView.setAdapter(orders_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Orders_list_activity.this, Orders_createActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(recyclerView);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Intent intent = new Intent(this, Orders_list_activity.class);
            startActivity(intent);
            recreate();
        }
    }

    void insertArray() {
        orders = provider.getOrders();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_menu ) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all?" );
        builder.setMessage("Are you sure you want to delete all data ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Orders_list_activity.this, "Delete", Toast.LENGTH_SHORT).show();
                provider.deleteAllOrders();
                Intent intent = new Intent(Orders_list_activity.this, Executors_list_activity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }


}
