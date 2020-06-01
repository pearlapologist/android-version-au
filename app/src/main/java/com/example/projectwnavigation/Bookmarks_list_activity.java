package com.example.projectwnavigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import models.Bookmarks;
import models.DbHelper;
import models.MyDataProvider;

public class Bookmarks_list_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button add_button;
    MyDataProvider provider;
    ArrayList<Bookmarks> bookmarks;
    Bookmarks_adapter bookm_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks_list);

        add_button = findViewById(R.id.bookmr_list_btn_add);
        provider = new MyDataProvider(this);

        recyclerView = findViewById(R.id.bookmr_list_rv);

        insertArray();

        bookm_adapter = new Bookmarks_adapter(Bookmarks_list_activity.this, this, bookmarks);
        recyclerView.setAdapter(bookm_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bookmarks_list_activity.this, Bookmarks_create_activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Intent intent = new Intent(this, Bookmarks_list_activity.class);
            startActivity(intent);
            recreate();
        }
    }

    void insertArray() {
        bookmarks = provider.getBookmarks();
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
        builder.setMessage("Are u sure u want to delete all data ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Bookmarks_list_activity.this, "Delete", Toast.LENGTH_SHORT).show();
                provider.deleteAllNotification();
                Intent intent = new Intent(Bookmarks_list_activity.this, Bookmarks_list_activity.class);
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

