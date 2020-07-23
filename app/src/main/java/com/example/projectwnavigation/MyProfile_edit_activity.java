package com.example.projectwnavigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.santalu.maskedittext.MaskEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.DateFormat;

import fragments.Fragment_orders_add;
import fragments.MyProfileActivity;
import models.ApiProvider;
import models.MyDataProvider;
import models.MyUtils;
import models.Persons;

public class MyProfile_edit_activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button btnOk, btnCh;
    ImageView image;
    MyDataProvider provider;
    ApiProvider apiProvider;
    TextInputLayout etName, etLastname, etNumber;
    MaskEditText etBirthday;
    ProgressDialog pd;

    Long birthday = 0L;
    String name = null;
    String lastname = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        image = findViewById(R.id.profile_edit_image);
        btnOk = findViewById(R.id.profile_edit_btnOk);
        btnCh = findViewById(R.id.profile_edit_btnChoose);
        provider = new MyDataProvider(this);
        apiProvider = new ApiProvider();
        etName = findViewById(R.id.profile_edit_etName);
        etLastname = findViewById(R.id.profile_edit_etLast);
        etBirthday = findViewById(R.id.profile_edit_etBirthday);
        etNumber = findViewById(R.id.profile_edit_etNumberlayout);
        initData();

        btnCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        MyProfile_edit_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        999
                );
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateName() || !validateBirthday()) {
                    return;
                }
                try {
                    Persons pers = provider.getLoggedInPerson();
                    pers.setName(name);
                    pers.setLastname(lastname);
                    pers.setBirthday(birthday);
                    //  pers.setPhoto(MyUtils.imageViewToByte(image));

                    EditTask task2 = new EditTask();
                    task2.execute(pers);
                    String result = task2.get();
                    Toast.makeText(MyProfile_edit_activity.this, result, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MyProfile_edit_activity.this, MyProfileActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MyProfile_edit_activity.this, "Ошибка: 4", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        etNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_edit_number fragment_editnumber = new Fragment_edit_number(MyProfile_edit_activity.this);
                getSupportFragmentManager().beginTransaction().replace(R.id.fram, fragment_editnumber).commit();
               setTitle("Изменить номер");
            }
        });
    }

    private boolean validateBirthday() {
        String b = etBirthday.getText().toString().trim();
        if (b.isEmpty()) {
            etBirthday.setError("Заполните поле");
            return false;
        } else {
            etBirthday.setError(null);
            this.birthday = MyUtils.convertDataToLongWithRawString(b);
            return true;
        }
    }

    private boolean validateName() {
        String w = "\\A\\w{3,20}\\z";
        String s = etName.getEditText().getText().toString().trim();
        if (s.isEmpty()) {
            etName.setError("Заполните поле");
            return false;
        } else if (!s.matches(w)) {
            etName.setError("Инвалидное имя");
            return false;
        } else {
            etName.setError(null);
            etName.setErrorEnabled(false);
            name = s;

            String lastname = etLastname.getEditText().getText().toString().trim();
            if (lastname.isEmpty()) {
                etLastname.setError("Заполните поле");
                return false;
            } else {
                etLastname.setError(null);
                etLastname.setErrorEnabled(false);
                this.lastname = lastname;
                return true;
            }
        }
    }

    private void initData() {
        Persons person = provider.getLoggedInPerson();
        if (person.getPhoto() != null) {
            image.setImageBitmap(MyUtils.decodeByteToBitmap(person.getPhoto()));
        }
        etName.getEditText().setText(person.getName());
        etLastname.getEditText().setText(person.getLastname());
        etNumber.getEditText().setText(person.getNumber());

        if (person.getBirthday() != null) {
            if (person.getBirthday() > 1) {
                String b = MyUtils.convertLongToDataString(person.getBirthday());
                etBirthday.setText(b);
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Long l = MyUtils.convertDataToLong(dayOfMonth, month, year);
        birthday = l;
        String s = MyUtils.convertLongToDataString(l);
        etBirthday.setText(s);
    }


    private class EditTask extends AsyncTask<Persons, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MyProfile_edit_activity.this);
            pd.setMessage("Пожалуйста, подождите");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected String doInBackground(Persons... params) {
            try {
                String s = apiProvider.updatePerson(params[0]);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == provider.READ_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, provider.READ_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == provider.READ_GALLERY && resultCode == RESULT_OK && data != null) {
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
                ex.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
