package com.example.projectwnavigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.santalu.maskedittext.MaskEditText;

import models.ApiProvider;
import models.DbHelper;
import models.MyDataProvider;
import models.Persons;

public class AuthorizationActivity extends AppCompatActivity {
    MyDataProvider provider;
    ApiProvider apiprovider;
    EditText  etPasswd;
    MaskEditText etNumb;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        provider = new MyDataProvider(this);
        apiprovider = new ApiProvider();
        etNumb = findViewById(R.id.author_numb);
        etPasswd = findViewById(R.id.author_passwd);

      btn = findViewById(R.id.author_btnOk);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = "";
                if (etNumb.getText() != null) {
                    String num = etNumb.getRawText().trim()+"";
                    n = "+7"+num;
                }
                String password = "";
                if (etPasswd.getText() != null) {
                    password = etPasswd.getText().toString();
                }

                Persons p = apiprovider.getPersonByNumbNPasswd(password, n);
                //provider.getPersonByPasswdNNumb(password, n);

                if (p != null) {
                    provider.setLoggedInPerson(p);

                    Intent i = new Intent(AuthorizationActivity.this, Navigation_activity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    Toast.makeText(AuthorizationActivity.this, "Пользователь не найден", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
