package com.example.projectwnavigation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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

import models.MyUtils;
import models.MyDataProvider;
import models.Persons;

public class RegistrationActivity extends AppCompatActivity {
    MyDataProvider provider;
    Button btn;
    EditText name, lastname, passwd, passwd2;
    MaskEditText number;
    ImageView image;

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
                byte[] imageByte = null;
                if (image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.add_a_photo_black_20dp).getConstantState()) {
                    imageByte = MyUtils.imageViewToByte(image);
                }
                if (passwd.getText().toString().trim().
                        equals(passwd2.getText().toString().trim())) {
                    Persons person = new Persons(name.getText().toString().trim(),
                            lastname.getText().toString().trim(),
                            passwd.getText().toString().trim(),
                            imageByte,
                            n,
                            0,
                            MyUtils.getCurentDateInLong());
                    provider.addPerson(person);
                    // provider.setLoggedInPerson(person);
                    Intent i = new Intent(RegistrationActivity.this, AuthorizationActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
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
