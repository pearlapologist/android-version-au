package com.example.projectwnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.santalu.maskedittext.MaskEditText;

import models.DataConverter;
import models.DbHelper;
import models.MyDataProvider;
import models.Persons;

public class RegistrationActivity extends AppCompatActivity {
    MyDataProvider provider;
    Button btn;
    EditText name, lastname, passwd, passwd2;
    MaskEditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        provider = new MyDataProvider(this);
        name = findViewById(R.id.regist_name);
        lastname = findViewById(R.id.regist_lastname);
        number = findViewById(R.id.regist_number);
        passwd = findViewById(R.id.regist_passwd);
        passwd2 = findViewById(R.id.regist_passwd2);

        btn = findViewById(R.id.regist_btnOk);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number ==null || passwd ==null || name == null || lastname == null){
                    Toast.makeText(RegistrationActivity.this, "Заполните все необходимые поля", Toast.LENGTH_SHORT).show();
                    return;
                }
                String num = number.getRawText().trim()+"";
                String n = "+7"+num;
                if (passwd.getText().toString().trim().equals(passwd2.getText().toString().trim())) {
                    Persons person = new Persons(name.getText().toString().trim(), lastname.getText().toString().trim(),
                           n,
                            passwd.getText().toString().trim(),0);
                    provider.addPerson(person);
                 provider.setLoggedInPerson(person);
                    Intent i = new Intent(RegistrationActivity.this,AuthorizationActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else {
                    Toast.makeText(RegistrationActivity.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
