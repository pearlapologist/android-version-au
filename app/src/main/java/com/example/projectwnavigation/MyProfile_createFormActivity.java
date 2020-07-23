package com.example.projectwnavigation;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import fragments.MyProfileActivity;
import models.ApiProvider;
import models.CustomArrayAdapter;
import models.Executor;
import models.MyDataProvider;
import models.Persons;
import models.Service;

public class MyProfile_createFormActivity extends AppCompatActivity implements View.OnLongClickListener {
    TextInputLayout spclztn, descrp;
    Button add_executor, add_service;
    MyDataProvider provider;
    ApiProvider apiProvider;
    Spinner mSpinner;
    int sectionId = 0;
    RecyclerView recyclerView;
    MyProfile_createform_services_adapter adapter;
    public Boolean contextModeEnable = false;
    ProgressDialog pd;

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
        apiProvider = new ApiProvider();

        selectionList = new ArrayList<>();

        services = new ArrayList<>();
        adapter = new MyProfile_createform_services_adapter(MyProfile_createFormActivity.this, this, services);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        CustomArrayAdapter arrayAdapter = new CustomArrayAdapter(this,
                R.layout.spinner_layout, R.id.spinner_layout_textview, getResources().getStringArray(R.array.sections), 0);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setSelection(1);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sectionId = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_executor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateSpec() || !validateDesc()) {
                    return;
                }
                Executor exec = new Executor();
                exec.setSectionId(sectionId);
                exec.setSpecialztn(spclztn.getEditText().getText().toString().trim());
                exec.setDescriptn(descrp.getEditText().getText().toString().trim());
                exec.setPersonId(provider.getLoggedInPerson().getId());
                exec.setServices(services);

                try {
                    CreateExecutorTask task = new CreateExecutorTask();
                    task.execute(exec);
                    // provider.addExecutor(exec);
                    Toast.makeText(MyProfile_createFormActivity.this, "Анкета создана", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(MyProfile_createFormActivity.this, MyProfileActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

    private boolean validateDesc() {
        String d = descrp.getEditText().getText().toString().trim();
        if (d.isEmpty() || d.equals(" ")) {
            descrp.setError("Заполните поле");
            return false;
        } else {
            descrp.setError(null);
            descrp.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateSpec() {
        String s = spclztn.getEditText().getText().toString().trim();
        if (s.isEmpty()) {
            spclztn.setError("Заполните поле");
            return false;
        } else {
            spclztn.setError(null);
            spclztn.setErrorEnabled(false);
            return true;
        }
    }

    private void showDialogCreate() {
        final Dialog dialog = new Dialog(MyProfile_createFormActivity.this);
        dialog.setContentView(R.layout.createform_service_dialog);
        dialog.setTitle("Добавить услугу");

        final TextInputLayout edTitle = dialog.findViewById(R.id.createform_service_dialog_title);
        final TextInputLayout edPrice = dialog.findViewById(R.id.createform_service_dialog_price);
        Button btnSave = dialog.findViewById(R.id.createform_service_dialog_btnCreate);
        Button btnCancel = dialog.findViewById(R.id.createform_service_dialog_btnCancel);

        dialog.setCancelable(false);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title3 = edTitle.getEditText().getText().toString().trim().trim();
                if (title3.isEmpty()) {
                    edTitle.setError("Заполните поле");
                    return;
                } else {
                    edTitle.setError(null);
                    edTitle.setErrorEnabled(false);

                    String price3 = edPrice.getEditText().getText().toString().trim();
                    if (price3.isEmpty()) {
                        edPrice.setError("Заполните поле");
                        return;
                    } else {
                        edPrice.setError(null);
                        edPrice.setErrorEnabled(false);

                        try {
                            Service service = new Service(title3,
                                    Double.parseDouble(price3));
                            services.add(service);
                        } catch (Exception error) {
                            Log.e("Create error", error.getMessage());
                        }
                    }
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


    private class CreateExecutorTask extends AsyncTask<Executor, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MyProfile_createFormActivity.this);
            pd.setMessage("Пожалуйста, подождите");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Executor... params) {
            apiProvider.addExecutor(params[0]);
            return null;
        }


        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
        }
    }
}
