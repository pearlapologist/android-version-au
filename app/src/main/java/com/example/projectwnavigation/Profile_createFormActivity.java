package com.example.projectwnavigation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import models.Executor;
import models.MyDataProvider;
import models.Service;

public class Profile_createFormActivity extends AppCompatActivity implements View.OnLongClickListener {
    EditText spclztn, descrp;
    Button add_executor, add_service;
    MyDataProvider provider;
    Spinner mSpinner;
    String[] mOptions = {"Стройка", "Здоровье", "Авто", "Рукоделие"};
    int sectionId = 0;

    RecyclerView recyclerView;
    Profile_form_services_adapter adapter;
    public Boolean contextModeEnable = false;

    int counterContext = 0;

    ArrayList<Service> services;
    ArrayList<Service> selectionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_create_form);

        spclztn = findViewById(R.id.createform_et_spcl);
        descrp = findViewById(R.id.createform_et_descr);

        add_executor = findViewById(R.id.createform_btn_save);
        add_service = findViewById(R.id.createform_btn_addService);
        mSpinner = findViewById(R.id.createform_spinnerid);
        recyclerView = findViewById(R.id.createform_recycler);

        provider = new MyDataProvider(this);

        selectionList = new ArrayList<>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, mOptions);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);

        insertArray();
        adapter = new Profile_form_services_adapter(Profile_createFormActivity.this, this, services);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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
                exec.setDescriptn(descrp.getText().toString().trim());
                exec.setPersonId(provider.getLoggedInPerson().getId());
                exec.setServices(services);
                try {
                    provider.addExecutor(exec);
                }catch(Exception e){
                    e.printStackTrace();
                }

                Toast.makeText(Profile_createFormActivity.this, "Form created", Toast.LENGTH_SHORT).show();
                spclztn.setText("");
                descrp.setText("");
                selectionList.clear();
                mSpinner.setSelection(0);
            }
        });

        add_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AlertDialog.Builder dialog = new AlertDialog.Builder(Profile_createFormActivity.this);
                showDialogCreate();
               // dialog.show();


            }
        });
    }

    void insertArray() {
        services = new ArrayList<>();
       // services = provider.getAllServices();
    }

    private void showDialogCreate() {
        final Dialog dialog = new Dialog(Profile_createFormActivity.this);
        dialog.setContentView(R.layout.createform_service_dialog);
        dialog.setTitle("Добавить услугу");

        final EditText edTitle = dialog.findViewById(R.id.createform_service_dialog_title);
        final EditText edPrice = dialog.findViewById(R.id.createform_service_dialog_price);
        Button btnSave = dialog.findViewById(R.id.createform_service_dialog_btnCreate);
        Button btnCancel = dialog.findViewById(R.id.createform_service_dialog_btnCancel);

        dialog.getWindow().setLayout(720, 1280);
        dialog.setCancelable(false);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Service service = new Service(edTitle.getText().toString().trim(),
                            Double.parseDouble(edPrice.getText().toString().trim()));
                   services.add(service);
                } catch (Exception error) {
                    Log.e("Create error", error.getMessage());
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(Profile_createFormActivity.this, "Created", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.services_multiply_choice, menu);
        return true;
    }

    @Override
    public boolean onLongClick(View v) {
        contextModeEnable = true;
        adapter.notifyDataSetChanged();
        return true;
    }

    public void setSelection(View v, int position) {
        selectionList.add(services.get(position));
        if (((CheckBox) v).isChecked()) {
            selectionList.add(services.get(position));
            counterContext++;
            updateCounter();
        } else {
            selectionList.remove(services.get(position));
            counterContext--;
            updateCounter();
        }
    }

    public void updateCounter() {
        Toast.makeText(this, counterContext + " item selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.multiply_delete) {
            adapter.removeItem(selectionList);
            removeContext();
        } else {
            removeContext();
            adapter.notifyDataSetChanged();
        }
        return true;
    }

    private void removeContext() {
        contextModeEnable = false;
        counterContext = 0;
        selectionList.clear();
        adapter.notifyDataSetChanged();
    }

}
