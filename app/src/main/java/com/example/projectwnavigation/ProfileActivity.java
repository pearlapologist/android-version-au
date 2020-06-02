package com.example.projectwnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    TextView tv_name, tv_rating, tv_status;
    Button btn_edit, btn_orders,  btn_form, btn_reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       tv_name= findViewById(R.id.profile_name);
        tv_rating= findViewById(R.id.profile_rating);
        tv_status= findViewById(R.id.profile_status);
        btn_edit = findViewById(R.id.profile_edit);
        btn_orders = findViewById(R.id.profile_orders);
        btn_form = findViewById(R.id.profile_form);
        btn_reviews = findViewById(R.id.profile_reviews);


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(ProfileActivity.this, Profile_edit_activity.class);
                startActivity(edit);
            }
        });
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

                  /*add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Orders_add addFrg = new Orders_add(context);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fram, addFrg).commit();
            }
        });*/
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

    @Override
    public void finish() {
        super.finish();
     }
}
