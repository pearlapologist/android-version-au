package com.example.projectwnavigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.santalu.maskedittext.MaskEditText;

import models.ApiProvider;
import models.MyDataProvider;
import models.Persons;

public class Activity_edit_number extends AppCompatActivity {
    MyDataProvider provider;
    ApiProvider apiProvider;
    Persons loggedInPerson;
    MaskEditText etNumber;
    Button btn_save, btn_cancel;
    String number;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_edit_number);

        this.provider = new MyDataProvider(this);
        apiProvider = new ApiProvider();

        etNumber = findViewById(R.id.edit_numb_etnumber);
        btn_save = findViewById(R.id.edit_numb_btn_save);
        btn_cancel = findViewById(R.id.edit_numb_btn_cancel);

        loggedInPerson = provider.getLoggedInPerson();
        etNumber.setText(loggedInPerson.getNumber());

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_edit_number.this, MyProfile_edit_activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateNumber()) {
                    return;
                }
                try {
                    loggedInPerson.setNumber(number);

                   EditNumberTask task = new EditNumberTask();
                    task.execute(loggedInPerson);
                    String result = task.get();
                    Toast.makeText(Activity_edit_number.this, result, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Activity_edit_number.this, MyProfile_edit_activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Activity_edit_number.this, "Ошибка : 3", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateNumber() {
        String num = etNumber.getRawText().trim();
        String n = "+7" + num;

        if (n.isEmpty() || n.length() == 0) {
            etNumber.setError("Заполните поле");
            return false;
        } else if (n.length() < 9) {
            etNumber.setError("Заполните поле корректно");
            return false;
        } else {
            etNumber.setError(null);
            this.number = n;
            return true;
        }
    }

    private class EditNumberTask extends AsyncTask<Persons, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Activity_edit_number.this);
            pd.setMessage("Пожалуйста, подождите");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected String doInBackground(Persons... params) {
            try {
                String s = apiProvider.updatePersonNumber(params[0].getId(), params[0].getNumber());
                return s;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
        }
    }
}
