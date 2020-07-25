package com.example.projectwnavigation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import fragments.Fragment_about;
import fragments.Fragment_bkmrk;
import fragments.Fragment_messages;
import fragments.Fragment_notification;
import fragments.Fragment_orders;
import fragments.Fragment_settings;
import fragments.Fragment_specials;
import fragments.MyProfileActivity;
import fragments.Orders_view_activity;
import models.ApiProvider;
import models.MyDataProvider;
import models.MyUtils;
import models.Notify;
import models.Persons;

public class Navigation_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    private TextView mNotifyTv;
    int notifyCounter = 0;

    MyDataProvider provider;
    ApiProvider apiProvider;
    Fragment_specials fragment_specials;
    Fragment_orders fragment_orders;
    Fragment_bkmrk fragment_bkmrk;
    Fragment_notification fragment_notification;
    Fragment_settings fragment_settings;
    Fragment_messages fragment_messages;
    Fragment_about fragment_about;

    TextView headerPersonName;
    ImageView headerImageView;

    Persons currentPerson;
    private NotificationManager nManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        provider = new MyDataProvider(this);

        fragment_specials = new Fragment_specials(getApplicationContext());
        fragment_orders = new Fragment_orders(getApplicationContext());
        fragment_bkmrk = new Fragment_bkmrk(getApplicationContext());
        fragment_notification = new Fragment_notification(getApplicationContext());
        fragment_settings = new Fragment_settings(getApplicationContext());
        fragment_messages = new Fragment_messages(getApplicationContext());
        fragment_about = new Fragment_about(this);

        nManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Заказы");

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                toogle.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                toogle.onDrawerOpened(drawerView);
                Persons cur = provider.getLoggedInPerson();
                headerPersonName.setText(cur.getName() + " " + cur.getLastname());
                if (cur.getPhoto() != null) {
                    headerImageView.setImageBitmap(MyUtils.decodeByteToBitmap(cur.getPhoto()));
                }
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                toogle.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toogle.syncState();

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                    R.anim.enter_from_right, R.anim.exit_to_right);
            transaction.addToBackStack(null);

            transaction.replace(R.id.fram, fragment_orders).commit();
            navigationView.setCheckedItem(R.id.nav_orders);
        }

        //notifivation
        mNotifyTv = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_notificatn));
        //header name
        View headerView = navigationView.getHeaderView(0);
        headerPersonName = headerView.findViewById(R.id.header_textView_profileName);
        headerImageView = headerView.findViewById(R.id.header_imageView);

        headerPersonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_profile = new Intent(Navigation_activity.this, MyProfileActivity.class);
                startActivity(intent_profile);
            }
        });


        headerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_profile = new Intent(Navigation_activity.this, MyProfileActivity.class);
                startActivity(intent_profile);
            }
        });

        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    protected void onStart() {
        currentPerson = provider.getLoggedInPerson();
        if (currentPerson == null) {
            Intent intent = new Intent(this, FirstActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            try {
                notifyCounter = apiProvider.getCountOfPersonNewNotifies(currentPerson.getId()); // provider.getCountOfAllMyNewNotifies();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (notifyCounter != 0) {
                ArrayList<Notify> notifies = null; //provider.getAllMyNotifies();
                try {
                    notifies = apiProvider.getAllPersonNotifies(currentPerson.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (notifies != null) {
                    for (Notify n : notifies) {
                        Intent intent = new Intent(getApplicationContext(), MyProfile_reviews_activity.class);
                        intent.putExtra("orderview_PersonId", n.getSrcId());
                        if (n.getSectionId() == 1) {
                            intent = new Intent(getApplicationContext(), Orders_view_activity.class);
                            intent.putExtra("orderIdFragment", n.getSrcId());
                        }
                        Notification.Builder buildr = new Notification.Builder(getApplicationContext());
                        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        buildr.setContentIntent(pIntent).setSmallIcon(R.drawable.minecraft).
                                setTicker("У вас новое уведомление").setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentTitle(n.getText());
                        Notification notification = buildr.build();
                        nManager.notify(n.getId(), notification);
                    }
                }
            }
            initializeCountDrawer();
        }
        super.onStart();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();


    }*/

    public void onBackPressed() {
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_orders:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fram, fragment_orders).commit();
                break;
            case R.id.nav_speacl:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fram, fragment_specials).commit();
                break;
            case R.id.nav_notificatn:
                if (notifyCounter != 0) {
                    try {
                        apiProvider.setPersonNotifiesToChecked(currentPerson.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    notifyCounter = 0;
                }
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fram, fragment_notification).commit();
                break;
            case R.id.nav_messages:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fram, fragment_messages).commit();
                break;
            case R.id.nav_bkmr:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fram, fragment_bkmrk).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fram, fragment_settings).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fram, fragment_about).commit();
                break;
        }

       setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void initializeCountDrawer() {
        mNotifyTv.setGravity(Gravity.CENTER_VERTICAL);
        mNotifyTv.setTypeface(null, Typeface.BOLD);
        mNotifyTv.setTextColor(getResources().getColor(R.color.colorAccent));
        if (notifyCounter == 0) {
            mNotifyTv.setText("");
        } else {
            mNotifyTv.setText(notifyCounter + "");
        }
    }
}
