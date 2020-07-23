package com.example.projectwnavigation;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.santalu.maskedittext.MaskEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import models.ApiProvider;
import models.MyUtils;
import models.MyDataProvider;
import models.Persons;

public class RegistrationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    MyDataProvider provider;
    Button btn, btnToAuth;
    MaskEditText number, etBirthday;
    ImageView image;
    ApiProvider apiprovider;
    ProgressDialog pd;
    TextInputLayout name, lastname, passwd, passwd2;

    Long txtbirthday = 0L;
    String txtnumb = null;
    String txtname = null;
    String txtlastname = null;
    String  txtpasswd=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        provider = new MyDataProvider(this);
        apiprovider = new ApiProvider();
        name = findViewById(R.id.regist_name);
        lastname = findViewById(R.id.regist_lastname);
        number = findViewById(R.id.regist_number);
        etBirthday = findViewById(R.id.regist_birthday);
        passwd = findViewById(R.id.regist_passwd);
        passwd2 = findViewById(R.id.regist_passwd2);
        image = findViewById(R.id.regist_image);
        btn = findViewById(R.id.regist_btnOk);
        btnToAuth = findViewById(R.id.regist_btnToAuth);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateName() || !validatePasswd() || !validateBirthday() || !validateNumber()) {
                    return;
                }
                try {
                    Persons person = new Persons();
                    person.setName(txtname);
                    person.setLastname(txtlastname);
                    person.setPasswd(txtpasswd);
                    //person.setPhoto(MyUtils.imageViewToByte(image));
                    person.setNumber(txtnumb);
                    person.setBirthday(txtbirthday);

                    RegistrationTask task2 = new RegistrationTask();
                    task2.execute(person);
                } catch (Exception e) {
                    Log.e("onclick", e.getMessage());
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

        btnToAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this, AuthorizationActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    private boolean validateNumber() {
        String num = number.getRawText().trim();
        String n = "+7" + num;

        if (n.isEmpty() || n.length() == 0) {
            number.setError("Заполните поле");
            return false;
        } else if (n.length() < 9) {
            number.setError("Заполните поле корректно");
            return false;
        } else {
            number.setError(null);
            txtnumb = n;
            return true;
        }
    }

    private boolean validateBirthday() {
        String b = etBirthday.getText().toString().trim();
        if (b.isEmpty()) {
            etBirthday.setError("Заполните поле");
            return false;
        } else {
            etBirthday.setError(null);
            this.txtbirthday = MyUtils.convertDataToLongWithRawString(b);
            return true;
        }
    }

    private Boolean validateName() {
        String w = "\\A\\w{3,20}\\z";
        String s = name.getEditText().getText().toString().trim();
        if (s.isEmpty()) {
            name.setError("Заполните поле");
            return false;
        } else if (!s.matches(w)) {
            name.setError("Инвалидное имя");
            return false;
        } else {
            name.setError(null);
            name.setErrorEnabled(false);
            this.txtname = s;

            String l = lastname.getEditText().getText().toString().trim();
            if (l.isEmpty()) {
                lastname.setError("Заполните поле");
                return false;
            } else {
                lastname.setError(null);
                lastname.setErrorEnabled(false);
                this.txtlastname = l;
                return true;
            }
        }
    }

    private Boolean validatePasswd() {
        String c = passwd2.getEditText().getText().toString().trim();
        String p = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        String s = passwd.getEditText().getText().toString().trim();
        if (s.isEmpty()) {
            passwd.setError("Заполните поле");
            return false;
        } else if (c.isEmpty()) {
            passwd2.setError("Заполните поле");
            return false;
        } else if (!s.matches(p)) {
            passwd.setError("Пароль слишком легкий");
            return false;
        } else if (!(s.equals(c))) {
            passwd2.setError("Пароли не совпадают");
            return false;
        } else {
            passwd.setError(null);
            passwd.setErrorEnabled(false);

            passwd2.setError(null);
            passwd2.setErrorEnabled(false);

            this.txtpasswd = s;
            return true;
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Long l = MyUtils.convertDataToLong(dayOfMonth, month, year);
        this.txtbirthday = l;
        String s = MyUtils.convertLongToDataString(l);
        etBirthday.setText(s);
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
