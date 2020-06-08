package com.example.projectwnavigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import models.MyDataProvider;
import models.MyUtils;
import models.Persons;

public class Profile_edit_activity extends AppCompatActivity {
    Button btnOk, btnCh;
    ImageView image;
    MyDataProvider provider;
    EditText etName, etLastname, etBirthday, etNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        image = findViewById(R.id.profile_edit_image);
        btnOk = findViewById(R.id.profile_edit_btnOk);
        btnCh = findViewById(R.id.profile_edit_btnChoose);
        provider = new MyDataProvider(this);
        etName = findViewById(R.id.profile_edit_etName);
        etLastname = findViewById(R.id.profile_edit_etLast);
        etBirthday = findViewById(R.id.profile_edit_etBirthday);
        etNumber = findViewById(R.id.profile_edit_etNumber);

        initData();

        btnCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        Profile_edit_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
                    pers.setPhoto(MyUtils.imageViewToByte(image));
                    provider.updatePerson(pers);
                    Toast.makeText(Profile_edit_activity.this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
                    image.setImageResource(R.mipmap.ic_add_image);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Profile_edit_activity.this, "error", Toast.LENGTH_SHORT).show();
                }
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
