package com.example.projectwnavigation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fragments.MyProfileActivity;
import models.Executor;
import models.MyDataProvider;
import models.Service;

public class MyProfile_createFormActivity extends AppCompatActivity implements View.OnLongClickListener {
    EditText spclztn, descrp;
    Button add_executor, add_service;
    MyDataProvider provider;
    Spinner mSpinner;
    int sectionId = 0;
    RecyclerView recyclerView;
    MyProfile_createform_services_adapter adapter;
    public Boolean contextModeEnable = false;

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

        insertArray();
        adapter = new MyProfile_createform_services_adapter(MyProfile_createFormActivity.this, this, services);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sections));

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setSelection(1);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             String[] choose = getResources().getStringArray(R.array.sections);
              sectionId = provider.getSectionIdByTitle(choose[position]);
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
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(MyProfile_createFormActivity.this, "Анкета создана", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MyProfile_createFormActivity.this, MyProfileActivity.class);
                startActivity(i);
            }
        });

        add_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCreate();
            }
        });
    }

    void insertArray() {
        services = new ArrayList<>();
    }

    private void showDialogCreate() {
        final Dialog dialog = new Dialog(MyProfile_createFormActivity.this);
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
                Toast.makeText(MyProfile_createFormActivity.this, "Created", Toast.LENGTH_LONG).show();
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
        // getMenuInflater().inflate(R.menu.main, menu);
        selectMenu(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        selectMenu(menu);
        return true;
    }

    private void selectMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        if (contextModeEnable) {
            inflater.inflate(R.menu.services_multiply_choice, menu);
        } else {
            inflater.inflate(R.menu.main, menu);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        contextModeEnable = true;
        invalidateOptionsMenu();
        //  toolbar.getMenu().clear();
        // toolbar.inflateMenu(R.menu.services_multiply_choice);

        adapter.notifyDataSetChanged();
        return true;
    }

    public void setSelection(View v, int position) {
        //selectionList.add(services.get(position));
        if (((CheckBox) v).isChecked()) {
            selectionList.add(services.get(position));
        } else {
            selectionList.remove(services.get(position));
            invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.multiply_delete) {
            adapter.removeItem(selectionList);
            removeContext();
        } else {
            removeContext();
        }
        return true;
    }

    private void removeContext() {
        contextModeEnable = false;
        selectionList.clear();
        adapter.notifyDataSetChanged();
        invalidateOptionsMenu();
    }

}
