package com.example.projectwnavigation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

import models.DataConverter;
import models.MyDataProvider;
import models.Persons;

public class Persons_controlList_activity extends AppCompatActivity {
    Button add;
    RecyclerView mListView;
    ArrayList<Persons> persons;
    PersonsListAdapter persons_adapter;
    MyDataProvider provider;
    ImageView imageViewIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons_controllist);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Persons List");

        add = findViewById(R.id.cntrl_btn_addPerson);
        provider = new MyDataProvider(this);
        mListView = findViewById(R.id.listView_of_persons);
        updatePersonList();
        persons_adapter = new PersonsListAdapter(this, this, persons);
        mListView.setAdapter(persons_adapter);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Persons_controlList_activity.this, Persons_contol_addPerson.class);
                startActivity(intent);
            }
        });

    }


/*  int photoFieldColumnIndex = cursor.getColumnIndex(MyDataProvider.KEY_PERSON_PHOTO);
            byte[] image = cursor.getBlob(photoFieldColumnIndex);
            imageViewIcon.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
*//*

            edtNumber.setText(p.getNumber());
            edtRating.setText(p.getRating() + "");
            String date = DataConverter.convertLongToDataString(p.getCreatedDate());
            edtCreatedDate.setText(date + "");
            //persons.add(p);
       // }

  */
/*      imageViewIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Persons_controlList_activity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 888);
            }
        });*/




    private void updatePersonList() {
        persons = provider.getPersons();
        if (persons.size() == 0) {
            Toast.makeText(this, "No record found", Toast.LENGTH_LONG).show();
        }
    }
    //region ImageCropper

//    public static byte[] imageViewToByte(ImageView image) {
//        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//
//        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
//        return byteArray;
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 888) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //gallery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*"); //make sure to write exactly this to insert images
                startActivityForResult(galleryIntent, 888);
            } else {
                Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 888 && resultCode == RESULT_OK) {
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
                imageViewIcon.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        if (requestCode == 1) {
            Intent intent = new Intent(this, Executors_list_activity.class);
            startActivity(intent);
            recreate();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
//endregion
}
