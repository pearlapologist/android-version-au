package fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectwnavigation.Conversation_view_activity;
import com.example.projectwnavigation.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.lang.reflect.Field;

import models.ApiProvider;
import models.Executor;
import models.MyDataProvider;
import models.MyUtils;
import models.Persons;
import models.Section_of_services;

public class Executors_view_activity extends AppCompatActivity {


    TextView personName, section;
    ImageView photo;
    MyDataProvider provider;
    ApiProvider apiProvider;
    Button chat;
    CarouselView carousel;
    int[] mImages = new int[]{R.drawable.minecraft, R.drawable.add_a_photo_black_20dp};
    String[] mImagesTitle = new String[]{"minecraft", "add"};
    Executor cur= null;

    BottomNavigationView bottomNavigation;

    Fragment_executor_view_reviews fragment_reviews;
    Fragment_executor_view_profile fragment_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_executor_view);
        provider = new MyDataProvider(this);
        apiProvider = new ApiProvider();
        personName = findViewById(R.id.fragment_executor_view_name);
        section = findViewById(R.id.fragment_executor_view_section);
        photo = findViewById(R.id.fragment_executor_view_photo);
        chat = findViewById(R.id.fragment_executor_view_chat);



        // btn = findViewById(R.id.executor_update_btn_add);



        if (getIntent().hasExtra("executorIdFragment")) {
            final int executorId = getIntent().getIntExtra("executorIdFragment", 42);
            fragment_reviews = Fragment_executor_view_reviews.newInstance(executorId);
            fragment_profile = Fragment_executor_view_profile.newInstance(executorId);

            fragment_profile.setContext(this);
            fragment_reviews.setContext(this);

           cur =apiProvider.getExecutor(executorId); // provider.getExecutor(executorId);
        }



        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Executors_view_activity.this, Conversation_view_activity.class);
                intent.putExtra("msg_adapter", cur.getPersonId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Executors_view_activity.this.startActivity(intent);
            }
        });
        carousel = findViewById(R.id.fragment_executor_view_carousel);
        bottomNavigation = findViewById(R.id.fragment_executor_view_bottomnav);
        bottomNavigation.setOnNavigationItemSelectedListener(listener);
        getAndSetExecutorIntentData();


        carousel.setPageCount(mImages.length);
        carousel.setImageListener(imageListener);
       /* carousel.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);
            }
        });*/

        carousel.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(Executors_view_activity.this, mImagesTitle[position], Toast.LENGTH_SHORT).show();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_executor_view_frameLayout, fragment_profile).commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selected = null;
            switch (menuItem.getItemId()) {
                case R.id.executor_nav_profile:
                    selected = fragment_profile;
                    break;
                case R.id.executor_nav_reviews:
                    selected = fragment_reviews;
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_executor_view_frameLayout, selected).commit();

            return true;
        }
    };

    public void getAndSetExecutorIntentData() {
       if(cur !=null) {
           Persons p =apiProvider.getPerson(cur.getPersonId());  // provider.getPerson(cur.getPersonId());
           if (p.getPhoto() == null) {
               photo.setImageResource(R.drawable.executors_default_image);
           } else {
               photo.setImageBitmap(MyUtils.decodeByteToBitmap(p.getPhoto()));
           }

           personName.setText(p.getName() + " " + p.getLastname());

           Section_of_services sectiontlt = apiProvider.getSection(cur.getSectionId()); //provider.getSection(cur.getSectionId());
           section.setText(sectiontlt.getTitle());
       }  else {
            Toast.makeText(Executors_view_activity.this, "error", Toast.LENGTH_SHORT).show();
        }
    }


    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            final int gettedId = getIntent().getIntExtra("executorIdFragment", 42);
            Executor cur =apiProvider.getExecutor(gettedId); // provider.getExecutor(gettedId);
            if (cur.getCoverPhoto() != null) {
                imageView.setImageBitmap(MyUtils.decodeByteToBitmap(cur.getCoverPhoto()));
            }
        }
    };

}