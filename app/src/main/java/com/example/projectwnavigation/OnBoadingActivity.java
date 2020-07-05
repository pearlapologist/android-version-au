package com.example.projectwnavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoadingActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout lLayout;
    Button btn_start, btn_skip,btn_next;
    ViewPager_adapter viewPager_adapter;
    TextView[] curSlide;
    Animation anim;
    int iCurPosiition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboading);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        lLayout = findViewById(R.id.nbrng_lLayout);
        viewPager = findViewById(R.id.nbrng_viewpager);
        btn_start =findViewById(R.id.nbrng_btn_start);
        btn_skip =findViewById(R.id.nbrng_btn_skip);
        btn_next =findViewById(R.id.nbrng_btn_next);

        viewPager_adapter = new ViewPager_adapter(this);
        viewPager.setAdapter(viewPager_adapter);
        addCurSlide(0);
        viewPager.addOnPageChangeListener(pager_listener);

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(OnBoadingActivity.this, Navigation_activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(i);
                finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          viewPager.setCurrentItem(iCurPosiition+1);
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(OnBoadingActivity.this, Navigation_activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(i);
                finish();
            }
        });
    }

    private void addCurSlide(int position) {
        curSlide = new TextView[2];
        lLayout.removeAllViews();
        for (int i = 0; i < curSlide.length; i++) {
            curSlide[i] = new TextView(this);
            curSlide[i].setText(Html.fromHtml("&#8226"));
            curSlide[i].setTextSize(35);
            lLayout.addView(curSlide[i]);
        }

        if (curSlide.length > 0) {
            curSlide[position].setTextColor(getResources().getColor(R.color.colorYellow));
        }
    }

    ViewPager.OnPageChangeListener pager_listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addCurSlide(position);
            iCurPosiition = position;

            if(position == 0){
                btn_start.setVisibility(View.INVISIBLE);
                btn_next.setVisibility(View.VISIBLE);
                btn_skip.setVisibility(View.VISIBLE);
            }else{
                anim = AnimationUtils.loadAnimation(OnBoadingActivity.this , R.anim.anim_splash_screen_bottom);
                btn_start.setAnimation(anim);
                btn_start.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.INVISIBLE);
                btn_skip.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
