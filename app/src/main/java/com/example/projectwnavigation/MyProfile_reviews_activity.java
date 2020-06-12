package com.example.projectwnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fragments.Fragment_myprofile_reviews;
import models.MyDataProvider;

public class MyProfile_reviews_activity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    MyDataProvider provider;
    Fragment_myprofile_reviews fragment_myprofile_reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile_reviews);

        provider = new MyDataProvider(this);

        fragment_myprofile_reviews = new Fragment_myprofile_reviews(this);

        bottomNavigation = findViewById(R.id.profile_reviews_navbar);
        bottomNavigation.setOnNavigationItemSelectedListener(listener);
        getPersonReviews();


        getSupportFragmentManager().beginTransaction().replace(R.id.profile_reviews_framelayout, fragment_myprofile_reviews)
                .commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selected = null;
         /*   switch (menuItem.getItemId()) {
                default:*/
                    selected = fragment_myprofile_reviews;
                 //   break;
                /*case R.id.executor_nav_reviews:
                    selected = fragment_reviews;
                    break; R.id.navbar_profile_reviews_to_me*/
           // }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_executor_view_frameLayout, selected).commit();

            return true;
        }
    };

    public void getPersonReviews() {

             }
}
