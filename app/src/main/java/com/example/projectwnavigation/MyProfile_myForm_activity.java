package com.example.projectwnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

import fragments.MyProfileActivity;
import models.Executor;
import models.MyDataProvider;
import models.Persons;
import models.Service;

public class MyProfile_myForm_activity extends AppCompatActivity implements View.OnLongClickListener {
    MyDataProvider provider;

    EditText spec, descrp;
    Button save, add_service;
    Spinner mSpinner;
    int sectionId = 0;
    RecyclerView recyclerView;
    MyProfile_myForm_services_adapter adapter;
    public Boolean contextModeEnable = false;

    ArrayList<Service> services;
    ArrayList<Service> selectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_my_form);

        provider = new MyDataProvider(this);
        Persons p = provider.getLoggedInPerson();
        int executorId = provider.getExecutorIdByPersonId(p.getId());
        final Executor r = provider.getExecutor(executorId);

        recyclerView = findViewById(R.id.myForm_rv);
        try {
            services = r.getServices();
        } catch (NullPointerException e) {
            Log.e("insertArray", e.getMessage());
        }
        adapter = new MyProfile_myForm_services_adapter(MyProfile_myForm_activity.this, this, services);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spec = findViewById(R.id.myForm_spec);
        descrp = findViewById(R.id.myForm_desc);
        save = findViewById(R.id.myForm_btn_save);
        add_service = findViewById(R.id.myForm_btn_addService);
        mSpinner = findViewById(R.id.myForm_spinner);


        selectionList = new ArrayList<>();

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

        spec.setText(r.getSpecialztn());
        descrp.setText(r.getDescriptn());
        descrp.setText(r.getDescriptn());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String specializtn = spec.getText().toString().trim();
                String descriptn = descrp.getText().toString().trim();

                if (specializtn.length() >= 7 && descriptn != null && descriptn.length() >= 7 && services.size() >= 1) {
                    r.setSectionId(sectionId);
                    r.setSpecialztn(specializtn);
                    r.setDescriptn(descriptn);
                    r.setPersonId(provider.getLoggedInPerson().getId());
                    r.setServices(services);
                    try {
                        provider.updateExecutor(r);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(MyProfile_myForm_activity.this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MyProfile_myForm_activity.this, MyProfileActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        });

        add_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCreate();
            }
        });
    }


    private void showDialogCreate() {
        final Dialog dialog = new Dialog(MyProfile_myForm_activity.this);
        dialog.setContentView(R.layout.createform_service_dialog);
        dialog.setTitle("Добавить услугу");

        final EditText edTitle = dialog.findViewById(R.id.createform_service_dialog_title);
        final EditText edPrice = dialog.findViewById(R.id.createform_service_dialog_price);
        Button btnSave = dialog.findViewById(R.id.createform_service_dialog_btnCreate);
        Button btnCancel = dialog.findViewById(R.id.createform_service_dialog_btnCancel);

        dialog.getWindow().setLayout(720, 800);
        dialog.setCancelable(true);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Service service = new Service(edTitle.getText().toString().trim(),
                            Double.parseDouble(edPrice.getText().toString().trim()));
                    services.add(service);
                } catch (Exception error) {
                    Log.e("error", error.getMessage());
                }
                adapter.notifyDataSetChanged();
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
        adapter.notifyDataSetChanged();
        return true;
    }

    public void setSelection(View v, int position) {
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
