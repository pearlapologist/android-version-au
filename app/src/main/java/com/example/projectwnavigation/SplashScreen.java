package com.example.projectwnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import models.MyDataProvider;
import models.Persons;

public class SplashScreen extends AppCompatActivity {
    ImageView background;
    TextView txtPoweredBy;
    MyDataProvider myDataProvider;

    Animation anim_slide, anim_bottom;

    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        background = findViewById(R.id.splshscrn_image);
        txtPoweredBy = findViewById(R.id.splshscrn_txtpwr);

        myDataProvider = new MyDataProvider(this);

        anim_slide = AnimationUtils.loadAnimation(this, R.anim.anim_splash_screen);
        anim_bottom = AnimationUtils.loadAnimation(this, R.anim.anim_splash_screen_bottom);

        background.setAnimation(anim_slide);
        txtPoweredBy.setAnimation(anim_bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sPref = getSharedPreferences("onBoarding", MODE_PRIVATE);
                boolean isFirstTime = sPref.getBoolean("isFirstTime", true);
                Intent i = new Intent(SplashScreen.this, Navigation_activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                 if (isFirstTime) {
                    SharedPreferences.Editor editor = sPref.edit();
                    editor.putBoolean("isFirstTime", false);
                    editor.commit();
                    i = new Intent(SplashScreen.this, OnBoadingActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(txtPoweredBy, "splash_transition_powered");
                    pairs[1] = new Pair<View, String>(background, "transition_splash_image");
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);
                        startActivity(i, options.toBundle());
                        finish();
                    } else {
                        startActivity(i);
                        finish();
                    }
                }
            }
        }, 2000);


    }
}
