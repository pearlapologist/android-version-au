package com.example.projectwnavigation;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.NavigationUI;

import fragments.Fragment_bkmrk;
import fragments.Fragment_notification;
import fragments.Fragment_orders;
import fragments.Fragment_profile;
import fragments.Fragment_settings;
import fragments.Fragment_specials;
import models.DbHelper;
import models.MyDataProvider;
import models.Persons;

public class Navigation_activity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView mNotifyTv;
    int notifyCounter = 0;

    MyDataProvider provider;
    Fragment_specials fragment_specials;
    Fragment_orders fragment_orders;
    Fragment_bkmrk fragment_bkmrk;
    Fragment_notification fragment_notification;
    Fragment_settings fragment_settings;
    Fragment_profile fragment_profile;
    TextView headerPersonName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        provider = new MyDataProvider(this);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navListener);

        fragment_specials = new Fragment_specials(getApplicationContext());
        fragment_orders = new Fragment_orders(getApplicationContext());
        fragment_bkmrk = new Fragment_bkmrk(getApplicationContext());
        fragment_notification = new Fragment_notification(getApplicationContext());
        fragment_settings = new Fragment_settings(getApplicationContext());
        fragment_profile = new Fragment_profile(getApplicationContext());


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.fram, fragment_profile, "PROFILE_FRAGMENT").commit();

       // getSupportFragmentManager().beginTransaction().replace(R.id.fram, fragment_orders).commit();
        //notifivation
        mNotifyTv = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_notificatn));
        //header name
        View headerView = navigationView.getHeaderView(0);
        headerPersonName = headerView.findViewById(R.id.header_textView_profileName);

        headerPersonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fram, fragment_profile).commit();
                setTitle("Профиль");
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
//                Intent intent_profile = new Intent(Navigation_activity.this, ProfileActivity.class);
//                startActivity(intent_profile);
            }
        });


        ImageView headerImageView = headerView.findViewById(R.id.header_imageView);
        headerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_profile = new Intent(Navigation_activity.this, ProfileActivity.class);
                startActivity(intent_profile);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Persons currentPerson = provider.getLoggedInPerson();
        ;
        if (currentPerson == null) {
            Intent intent = new Intent(this, FirstActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return;
        }
        headerPersonName.setText(currentPerson.getName() + " " + currentPerson.getLastname());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_test) {
            Intent testIntent = new Intent(Navigation_activity.this, TestActivity.class);
            startActivity(testIntent);
        } else if (id == R.id.action_exit) {
            provider.setLoggedInPerson(null);
            onStart();
        }
        return super.onOptionsItemSelected(item);
    }

    private NavigationView.OnNavigationItemSelectedListener navListener
            = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            Fragment fragment = fragment_orders;
            if (id == R.id.nav_speacl) {
                fragment = fragment_specials;
            } else if (id == R.id.nav_orders) {
                fragment = fragment_orders;
            } else if (id == R.id.nav_bkmr) {
                fragment = fragment_bkmrk;
            } else if (id == R.id.nav_notificatn) {
                fragment = fragment_notification;
                initializeCountDrawer();
            } else if (id == R.id.nav_settings) {
                fragment = fragment_settings;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fram, fragment).commit();
            setTitle(item.getTitle());
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    };

    private void initializeCountDrawer() {
        mNotifyTv.setGravity(Gravity.CENTER_VERTICAL);
        mNotifyTv.setTypeface(null, Typeface.BOLD);
        mNotifyTv.setTextColor(getResources().getColor(R.color.colorAccent));
        mNotifyTv.setText(notifyCounter + "");
        notifyCounter++;
    }
}
