package com.example.projectwnavigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import models.DbHelper;
import models.Executor;
import models.MyDataProvider;


public class Executors_list_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button add_button;
    MyDataProvider provider;
    Executors_adapter executors_adapter;
    ArrayList<Executor> executors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executors_list);
        add_button = findViewById(R.id.executors_list_btn_addExecutor);

        provider = new MyDataProvider(this);

        recyclerView = findViewById(R.id.executors_list_rv);
        insertArray();
        executors_adapter = new Executors_adapter(Executors_list_activity.this, this, executors);
        recyclerView.setAdapter(executors_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Executors_list_activity.this, Profile_createFormActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Intent intent = new Intent(this, Executors_list_activity.class);
            startActivity(intent);
            recreate();
        }
    }

    void insertArray() {
        try{
        executors = provider.getExecutors();}
        catch(Exception e){
            Log.e("Error!", e.getMessage());
        }
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
        builder.setMessage("Are u sure u want to delete all data ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Executors_list_activity.this, "Delete", Toast.LENGTH_SHORT).show();
                provider.deleteAllExecutors();
                Intent intent = new Intent(Executors_list_activity.this, Executors_list_activity.class);
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
