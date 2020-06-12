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
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class TestActivity extends AppCompatActivity {
    Button persons, executors, orders, notify, services, bookmr, test_json, image, choose;
    Button btn;
    ImageView imagetest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        persons = findViewById(R.id.test_list_of_persons);
        executors = findViewById(R.id.test_list_of_executors);
        orders = findViewById(R.id.test_list_of_orders);
        notify = findViewById(R.id.test_list_of_notify);
        services = findViewById(R.id.test_list_of_services);
        bookmr = findViewById(R.id.test_list_of_bkmrks);
        test_json = findViewById(R.id.test_json);
        image = findViewById(R.id.test_image);
        choose = findViewById(R.id.test_choose);
        imagetest = findViewById(R.id.imageViewtest2);

        persons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TestActivity.this, Persons_controlList_activity.class);
                startActivity(intent1);
            }
        });

        executors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(TestActivity.this, Executors_list_activity.class);
                startActivity(intent2);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orders = new Intent(TestActivity.this, Orders_list_activity.class);
                startActivity(orders);
            }
        });

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notify = new Intent(TestActivity.this, Notify_list_activity.class);
                startActivity(notify);
            }
        });

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notify = new Intent(TestActivity.this, Service_list_activity.class);
                startActivity(notify);
            }
        });

        bookmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notify = new Intent(TestActivity.this, Bookmarks_list_activity.class);
                startActivity(notify);
            }
        });

        test_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent json = new Intent(TestActivity.this, TestJsonActivity.class);
                startActivity(json);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent json = new Intent(TestActivity.this, TestImageActivity.class);
                startActivity(json);
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        TestActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        999
                );

            }
        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 999) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 999);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 999 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1).start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult res = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = res.getUri();
                imagetest.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception ex = res.getError();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
