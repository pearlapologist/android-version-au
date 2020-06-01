package com.example.projectwnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import models.DbHelper;
import models.Executor;
import models.MyDataProvider;

public class Profile_createFormActivity extends AppCompatActivity {
    EditText spclztn, descrp;
    Button add_executor, add_service;
    MyDataProvider provider;
    Spinner mSpinner;
    String[] mOptions = {"Стройка", "Здоровье", "Авто", "Рукоделие"};
    int sectionId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_create_form);

        spclztn = findViewById(R.id.createform_et_spcl);
        descrp = findViewById(R.id.createform_et_descr);

        add_executor = findViewById(R.id.createform_btn_add);
        add_service = findViewById(R.id.createform_btn_addService);
        mSpinner = findViewById(R.id.createform_spinnerid);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, mOptions);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);

        provider = new MyDataProvider(this);


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int a = parent.getId();
                switch (position) {
                    case 1:
                        sectionId = 1;
                    case 2:
                        sectionId = 2;
                    case 3:
                        sectionId = 3;
                    case 4:
                        sectionId = 4;
                    case 5:
                        sectionId = 5;
                    case 6:
                        sectionId = 6;
                    case 7:
                        sectionId = 7;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_executor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executor exec = new Executor();
                exec.setSectionId(sectionId);
                exec.setSpecialztn(spclztn.getText().toString().trim());
               exec.setDescriptn(  descrp.getText().toString().trim());
               exec.setPersonId(-1);
                provider.addExecutor(exec);


                Toast.makeText(Profile_createFormActivity.this, "Form created", Toast.LENGTH_SHORT).show();
                spclztn.setText("");
                descrp.setText("");
                mSpinner.setSelection(0);
            }
        });

        add_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile_createFormActivity.this, Service_createActivity.class);
                startActivityForResult(i,1);

            }
        });
    }
}
