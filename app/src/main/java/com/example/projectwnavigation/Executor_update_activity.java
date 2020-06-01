package com.example.projectwnavigation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import models.DbHelper;
import models.Executor;
import models.MyDataProvider;

import static com.example.projectwnavigation.R.*;


public class Executor_update_activity extends AppCompatActivity {
    EditText et_id, personName, special, descr;

    MyDataProvider provider;

    Button btn, addService, delete;
    Spinner mSpinner;
    String[] mOptions = {"Стройка", "Здоровье", "Авто", "Рукоделие"};
    int sectionId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_executors_upd);

        et_id = findViewById(id.executor_update_et_id);
        personName = findViewById(id.executor_update_et_personId);
        special = findViewById(id.executor_update_et_spcl);
        descr = findViewById(id.executor_update_et_descr);
        btn = findViewById(id.executor_update_btn_add);
        delete = findViewById(id.executor_update_btn_delete);
        addService = findViewById(id.executor_update_btn_addService);
        mSpinner = findViewById(id.executor_update_spinnerid);

        provider = new MyDataProvider(this);


        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, mOptions);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);

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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        getAndSetExecutorIntentData();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executor exc = new Executor(){};
                exc.setId(getIntent().getIntExtra("executorId", Integer.parseInt(et_id.getText().toString())));
               exc.setSectionId(sectionId);
                exc.setSpecialztn(special.getText().toString());
                exc.setDescriptn(descr.getText().toString());
                provider.updateExecutor(exc);
                Toast.makeText(Executor_update_activity.this, "Updated", Toast.LENGTH_SHORT).show();

                et_id.setText("");
                personName.setText("");
                special.setText("");
                descr.setText("");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    public void getAndSetExecutorIntentData() {
        if (getIntent().hasExtra("executorId")) {
            final int gettedId = getIntent().getIntExtra("executorId", 42);
            Executor cur = provider.getExecutor(gettedId);
            mSpinner.setSelection(cur.getSectionId());
            et_id.setText(gettedId + "");
            String personname = " ";
            if(provider.getPerson(provider.getExecutor(cur.getId()).getPersonId()) != null){
             personname = provider.getPerson(provider.getExecutor(cur.getId()).getPersonId()).getName();
            }
            personName.setText(personname);
            special.setText(cur.getSpecialztn());
            descr.setText(cur.getDescriptn());
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }


    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
       String name = provider.getPersonNameFromExecutorId(Integer.valueOf(et_id.getText().toString()));
        builder.setTitle("Delete " + name + "?" );
        builder.setMessage("Are you sure you want to delete " + name + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                provider.deleteExecutor(getIntent().getIntExtra("executorId", Integer.parseInt(et_id.getText().toString())));
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
