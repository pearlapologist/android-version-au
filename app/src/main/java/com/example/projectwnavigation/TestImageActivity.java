package com.example.projectwnavigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import models.MyDataProvider;

public class TestImageActivity extends AppCompatActivity {
    EditText etTitle;
    Button btnAdd, btnList, btnChoose;
    ImageView imageView;

    MyDataProvider provider;
    final int READ_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image);

        etTitle = findViewById(R.id.testImage_title);
        btnAdd = findViewById(R.id.testImage_add);
        btnChoose = findViewById(R.id.testImage_choose);
        btnList = findViewById(R.id.testImage_list);
        imageView = findViewById(R.id.testImage_image);

        provider = new MyDataProvider(this);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        TestImageActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_GALLERY
                );

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    provider.addImage(
                            etTitle.getText().toString().trim(),
                            provider.imageViewToByte(imageView)
                    );
                    Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    etTitle.setText("");
                    imageView.setImageResource(R.mipmap.ic_add_image);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestImageActivity.this, TestImageList.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, READ_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == READ_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1).start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult res = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = res.getUri();
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception ex = res.getError();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
