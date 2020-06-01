package com.example.projectwnavigation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.santalu.maskedittext.MaskEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

import models.DbHelper;
import models.MyDataProvider;
import models.Persons;

public class Persons_contol_addPerson extends AppCompatActivity {

    EditText mAddName, mAddLastname, mAddPasswd, mAddRating;
    MaskEditText mAddNumber;
    Button mBtnAdd, mBtnList;
    ImageView mImageView;
    final int REQUEST_CODE_GALLERY = 999;

    MyDataProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons_contol_add_person);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Record");

        mImageView = findViewById(R.id.add_imageView);
        mBtnAdd =  findViewById(R.id.add_btn_save);
        mBtnList =  findViewById(R.id.add_btn_back);
        mAddName =  findViewById(R.id.add_edtName);
        mAddLastname =  findViewById(R.id.add_edtLastname);
        mAddPasswd =  findViewById(R.id.add_edtPasswd);
        mAddRating =  findViewById(R.id.add_edtRating);
        mAddNumber =  findViewById(R.id.add_edtNumber);

        provider = new MyDataProvider(this);

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = mAddPasswd.getText().toString().trim();
                String b = a.replaceAll(" ", "");

                String num = mAddNumber.getRawText().trim()+"";
                String n = "+7"+num;
                Persons p  = new Persons(mAddName.getText().toString().trim(),
                        mAddLastname.getText().toString().trim(),
                        n,
                        b,
                        Integer.parseInt(mAddRating.getText().toString().trim()));
                try {
                provider.addPerson(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mAddName.setText("");
                mAddLastname.setText("");
                mAddPasswd.setText("");
                mAddRating.setText("");
                mAddNumber.setText("");
                mImageView.setImageResource(R.drawable.add_a_photo_black_20dp);

                Toast.makeText(Persons_contol_addPerson.this, "Added successfully", Toast.LENGTH_LONG).show();
            }

        });
        mBtnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Persons_contol_addPerson.this, Persons_controlList_activity.class);
                startActivity(intent3);
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Persons_contol_addPerson.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
            }
        });
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return byteArray;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //gallery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*"); //make sure to write exactly this to insert images
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON) //enable image guidlines
                    .setAspectRatio(1, 1)// image will be square
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                //set image choosed from gallery to image view
                mImageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}