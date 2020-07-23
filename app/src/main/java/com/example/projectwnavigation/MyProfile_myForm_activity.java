package com.example.projectwnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import fragments.MyProfileActivity;
import models.ApiProvider;
import models.CustomArrayAdapter;
import models.Executor;
import models.MyDataProvider;
import models.Persons;
import models.Service;

public class MyProfile_myForm_activity extends AppCompatActivity implements View.OnLongClickListener {
    MyDataProvider provider;
    ApiProvider apiProvider;

    EditText spec, descrp;
    Button save, add_service, deleteForm;
    Spinner mSpinner;
    int sectionId = 0;
    RecyclerView recyclerView;
    MyProfile_myForm_services_adapter adapter;
    public Boolean contextModeEnable = false;

    ArrayList<Service> services;
    ProgressDialog pd;
    ArrayList<Service> selectionList;

    Executor curExecutor=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_my_form);

        provider = new MyDataProvider(this);
        apiProvider = new ApiProvider();
        final Persons p = provider.getLoggedInPerson();
        int executorId = 0;
        try {
            executorId = apiProvider.getExecutorIdByPersonId(p.getId()); //provider.getExecutorIdByPersonId(p.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        curExecutor  = apiProvider.getExecutor(executorId);  // provider.getExecutor(executorId);

        recyclerView = findViewById(R.id.myForm_rv);
        try {
            services = curExecutor.getServices();
        } catch (NullPointerException e) {
            Log.e("insertArray", e.getMessage());
        }
        adapter = new MyProfile_myForm_services_adapter(MyProfile_myForm_activity.this, this, services);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spec = findViewById(R.id.myForm_spec);
        descrp = findViewById(R.id.myForm_desc);
        save = findViewById(R.id.myForm_btn_save);
        deleteForm = findViewById(R.id.myForm_btn_deleteform);
        add_service = findViewById(R.id.myForm_btn_addService);
        mSpinner = findViewById(R.id.myForm_spinner);
        selectionList = new ArrayList<>();

        CustomArrayAdapter arrayAdapter = new CustomArrayAdapter(this,
                R.layout.spinner_layout, R.id.spinner_layout_textview, getResources().getStringArray(R.array.sections), 0);

        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setSelection(curExecutor.getSectionId());

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sectionId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spec.setText(curExecutor.getSpecialztn());
        descrp.setText(curExecutor.getDescriptn());
        descrp.setText(curExecutor.getDescriptn());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String specializtn = spec.getText().toString().trim();
                String descriptn = descrp.getText().toString().trim();

                if (specializtn.length() >= 7 && descriptn != null && descriptn.length() >= 7 && services.size() >= 1) {
                    curExecutor.setSectionId(sectionId);
                    curExecutor.setSpecialztn(specializtn);
                    curExecutor.setDescriptn(descriptn);
                    curExecutor.setServices(services);
                    try {
                        UpdateExecutorTask task = new UpdateExecutorTask();
                        task.execute(curExecutor);
                    //  apiProvider.updateExecutor(r); // provider.updateExecutor(r);
                        Toast.makeText(MyProfile_myForm_activity.this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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


        deleteForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDelete();
            }
        });
    }

    private void showDialogDelete() {
        final androidx.appcompat.app.AlertDialog.Builder dialogDelete = new androidx.appcompat.app.AlertDialog.Builder(this);
        dialogDelete.setTitle("Предупреждение");
        dialogDelete.setMessage("Вы уверены, что хотите удалить?");

        dialogDelete.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    DeleteExecutorTask task = new DeleteExecutorTask();
                    task.execute(curExecutor.getId());
               //     provider.deleteExecutor(provider.getExecutorIdByPersonId(provider.getLoggedInPerson().getId()));
                    Toast.makeText(MyProfile_myForm_activity.this, "Анкета успешно удалена", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("Delete error", e.getMessage());
                }

                dialog.dismiss();
                finish();
            }
        });

        dialogDelete.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }


    private void showDialogCreate() {
        final Dialog dialog = new Dialog(MyProfile_myForm_activity.this);
        dialog.setContentView(R.layout.createform_service_dialog);
        dialog.setTitle("Добавить услугу");

        final TextInputLayout edTitle = dialog.findViewById(R.id.createform_service_dialog_title);
        final TextInputLayout edPrice = dialog.findViewById(R.id.createform_service_dialog_price);
        Button btnSave = dialog.findViewById(R.id.createform_service_dialog_btnCreate);
        Button btnCancel = dialog.findViewById(R.id.createform_service_dialog_btnCancel);

        dialog.setCancelable(true);
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
                    edTitle.setErrorEnabled(false) ;

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

                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } catch (Exception error) {
                            Log.e("error", error.getMessage());
                        }
                }
                }
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

    private class UpdateExecutorTask extends AsyncTask<Executor, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MyProfile_myForm_activity.this);
            pd.setMessage("Пожалуйста, подождите");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Executor... params) {
            try {
                apiProvider.updateExecutor(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private class DeleteExecutorTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MyProfile_myForm_activity.this);
            pd.setMessage("Пожалуйста, подождите");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            apiProvider.deleteExecutor(params[0]);
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
