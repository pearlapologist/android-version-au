package com.example.projectwnavigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import models.MyDataProvider;
import models.Persons;

public class ProfileActivity extends AppCompatActivity {
    TextView tv_name, tv_rating, tv_status, tv_registr;
    Button btn_orders, btn_form, btn_reviews;
    ImageView photo;
    MyDataProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tv_name = findViewById(R.id.profile_name);
        tv_rating = findViewById(R.id.profile_rating);
        tv_status = findViewById(R.id.profile_status);
        tv_registr = findViewById(R.id.profile_registDate);
        btn_orders = findViewById(R.id.profile_orders);
        btn_form = findViewById(R.id.profile_form);
        btn_reviews = findViewById(R.id.profile_reviews);
        photo = findViewById(R.id.profile_image);
        provider = new MyDataProvider(this);


        initData();

        btn_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orders = new Intent(ProfileActivity.this, Profile_orders_activity.class);
                startActivity(orders);
            }
        });
        btn_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent form = new Intent(ProfileActivity.this, Profile_createFormActivity.class);
                startActivity(form);
            }
        });

        btn_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviews = new Intent(ProfileActivity.this, Profile_reviews_activity.class);
                startActivity(reviews);
            }
        });

    }

    private void initData() {
        Persons person = provider.getLoggedInPerson();
        if (person.getPhoto() != null) {
            photo.setImageBitmap(provider.decodeByteToBitmap(person.getPhoto()));
        }
        tv_name.setText(person.getName() + " " + person.getLastname());
        int rating = person.getRating();
        if (rating != -1) {
            tv_rating.setText("Рейтинг: " + rating + "");
        }
        tv_status.setText("Статус: " + "Онлайн");

        tv_registr.setText("Дата регистрации " + person.getCreatedDateinString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.profile_menu_edit:
                Intent edit = new Intent(ProfileActivity.this, Profile_edit_activity.class);
                startActivityForResult(edit, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            initData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
