package com.example.projectwnavigation;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.santalu.maskedittext.MaskEditText;

import java.util.concurrent.ExecutionException;

import models.ApiProvider;
import models.DbHelper;
import models.MyDataProvider;
import models.Persons;

public class AuthorizationActivity extends AppCompatActivity {
    MyDataProvider provider;
    ApiProvider apiprovider;
    EditText  etPasswd;
    MaskEditText etNumb;
    Button btn, btnToReg;
    ProgressDialog pd;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        provider = new MyDataProvider(this);
        apiprovider = new ApiProvider();
        etNumb = findViewById(R.id.author_numb);
        etPasswd = findViewById(R.id.author_passwd);
        btnToReg = findViewById(R.id.author_btnToRegistratn);
        txt = findViewById(R.id.author_txt);

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

              AuthTask task2 = new AuthTask();
                Persons p = null;
                try {
                    p = task2.execute(password, n).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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

        btnToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AuthorizationActivity.this, RegistrationActivity.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(txt, "transition_aualmaty");
                if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AuthorizationActivity.this, pairs);
                    startActivity(i, options.toBundle());
                }else{
                    startActivity(i);
                }
            }
        });
    }

   private class AuthTask extends AsyncTask<String, Persons, Persons> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AuthorizationActivity.this);
            pd.setMessage("Пожалуйста, подождите");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected Persons doInBackground(String... params) {
            Persons p= new Persons();
            try {

            p = apiprovider.getPersonByNumbNPasswd(params[0], params[1]);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return p;
        }
        @Override
        protected void onPostExecute(Persons s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
        }
    }
}
