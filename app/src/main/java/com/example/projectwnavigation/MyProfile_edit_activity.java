package com.example.projectwnavigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

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
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.DateFormat;

import fragments.MyProfileActivity;
import models.ApiProvider;
import models.MyDataProvider;
import models.MyUtils;
import models.Persons;

public class MyProfile_edit_activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    Button btnOk, btnCh;
    ImageView image;
    MyDataProvider provider;
    ApiProvider apiProvider;
    EditText etName, etLastname, etBirthday, etNumber;
    ProgressDialog pd;


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
        etNumber = findViewById(R.id.profile_edit_etNumber);

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
                try {
                    Persons pers = provider.getLoggedInPerson();
                    pers.setName(etName.getText().toString().trim());
                    pers.setLastname(etLastname.getText().toString().trim());
                    pers.setNumber(etNumber.getText().toString().trim());
                    pers.setBirthday(MyUtils.getCurentDateInLong());
                  //  pers.setPhoto(MyUtils.imageViewToByte(image));

                    EditTask task2 = new EditTask();
                    task2.execute(pers);
                    Toast.makeText(MyProfile_edit_activity.this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MyProfile_edit_activity.this, MyProfileActivity.class);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MyProfile_edit_activity.this, "error", Toast.LENGTH_SHORT).show();
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
    }

    private void initData() {
        Persons person = provider.getLoggedInPerson();
        if (person.getPhoto() != null) {
            image.setImageBitmap(MyUtils.decodeByteToBitmap(person.getPhoto()));
        }
        etName.setText(person.getName());
        etLastname.setText(person.getLastname());
        etNumber.setText(person.getNumber());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Long l = MyUtils.convertDataToLong(dayOfMonth, month, year);
        String s = MyUtils.convertLongToDataString(l);
        //String s = DateFormat.getDateInstance(DateFormat.FULL).format(year,month,dayOfMonth);
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    private class EditTask extends AsyncTask<Persons, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MyProfile_edit_activity.this);
            pd.setMessage("Пожалуйста, подождите");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Persons... params) {
            try {
                apiProvider.updatePerson(params[0]);
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
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
