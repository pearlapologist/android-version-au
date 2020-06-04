package com.example.projectwnavigation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

import models.DataConverter;
import models.MyDataProvider;
import models.Persons;

public class Persons_controlList_activity extends AppCompatActivity {
    Button add;
    RecyclerView mListView;
    ArrayList<Persons> persons;
    PersonsListAdapter persons_adapter;
    MyDataProvider provider;
    ImageView imageViewIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons_controllist);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Persons List");

        add = findViewById(R.id.cntrl_btn_addPerson);
        provider = new MyDataProvider(this);
        mListView = findViewById(R.id.listView_of_persons);
        updatePersonList();
        persons_adapter = new PersonsListAdapter(this, this, persons);
        mListView.setAdapter(persons_adapter);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Persons_controlList_activity.this, Persons_contol_addPerson.class);
                startActivity(intent);
            }
        });

    }

    private void updatePersonList() {
        persons = provider.getPersons();
        if (persons.size() == 0) {
            Toast.makeText(this, "No record found", Toast.LENGTH_LONG).show();
        }
    }

}
