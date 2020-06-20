package com.example.projectwnavigation;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.santalu.maskedittext.MaskEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import models.ApiProvider;
import models.MyUtils;
import models.MyDataProvider;
import models.Persons;

public class RegistrationActivity extends AppCompatActivity {
    MyDataProvider provider;
    Button btn;
    EditText name, lastname, passwd, passwd2;
    MaskEditText number;
    ImageView image;
    ApiProvider apiprovider;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        provider = new MyDataProvider(this);
        apiprovider = new ApiProvider();
        name = findViewById(R.id.regist_name);
        lastname = findViewById(R.id.regist_lastname);
        number = findViewById(R.id.regist_number);
        passwd = findViewById(R.id.regist_passwd);
        passwd2 = findViewById(R.id.regist_passwd2);
        image = findViewById(R.id.regist_image);

        btn = findViewById(R.id.regist_btnOk);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (number == null || passwd == null || name == null || lastname == null) {
                    Toast.makeText(RegistrationActivity.this, "Заполните все необходимые поля", Toast.LENGTH_SHORT).show();
                    return;
                }
                String num = number.getRawText().trim() + "";
                String n = "+7" + num;
                if (passwd.getText().toString().trim().
                        equals(passwd2.getText().toString().trim())) {
                    Persons person = new Persons();
                    person.setName(name.getText().toString().trim());
                    person.setLastname(lastname.getText().toString().trim());
                    person.setPasswd(passwd.getText().toString().trim());
                    person.setPhoto(MyUtils.imageViewToByte(image));
                    person.setNumber(n);
                    person.setRating(0);
                    person.setCreatedDate(MyUtils.getCurrentDateInString());
                    person.setBirthday(MyUtils.getCurrentDateInString());

                  //  provider.addPerson(person);
                    RegistrationTask task2 = new RegistrationTask();
                    task2.execute(person);
                    //  apiprovider.addPerson(person);


                } else {
                    Toast.makeText(RegistrationActivity.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        RegistrationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2
                );
            }
        });
    }

    private class RegistrationTask extends AsyncTask<Persons, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(RegistrationActivity.this);
            pd.setMessage("Пожалуйста, подождите");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Persons... params) {
            try {
                apiprovider.addPerson(params[0]);
                Intent i = new Intent(RegistrationActivity.this, AuthorizationActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1).start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult res = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = res.getUri();
                image.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception ex = res.getError();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
