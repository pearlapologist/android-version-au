package fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
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

import com.example.projectwnavigation.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.lang.reflect.Field;

import models.Executor;
import models.MyDataProvider;
import models.Persons;
import models.Section_of_services;

public class Executors_view_activity extends AppCompatActivity {


    TextView personName, section;
    ImageView photo;
    MyDataProvider provider;
    Button chat;
    CarouselView carousel;
    int[] mImages = new int[]{R.drawable.minecraft, R.drawable.add_a_photo_black_20dp};
    String[] mImagesTitle = new String[]{"minecraft", "add"};

    BottomNavigationView bottomNavigation;

    Fragment_executor_view_reviews fragment_reviews;
    Fragment_executor_view_profile fragment_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_executor_view);
        provider = new MyDataProvider(this);
        personName = findViewById(R.id.fragment_executor_view_name);
        section = findViewById(R.id.fragment_executor_view_section);
        // btn = findViewById(R.id.executor_update_btn_add);

        fragment_reviews = Fragment_executor_view_reviews.newInstance(getIntent().getIntExtra("executorIdFragment", 42));
        fragment_profile = Fragment_executor_view_profile.newInstance(getIntent().getIntExtra("executorIdFragment", 42));

        fragment_profile.setContext(this);
        fragment_reviews.setContext(this);


        carousel = findViewById(R.id.fragment_executor_view_carousel);
        bottomNavigation = findViewById(R.id.fragment_executor_view_bottomnav);
        bottomNavigation.setOnNavigationItemSelectedListener(listener);
        getAndSetExecutorIntentData();


        carousel.setPageCount(mImages.length);
        carousel.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {

                imageView.setImageResource(mImages[position]);
            }
        });

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
        if (getIntent().hasExtra("executorIdFragment")) {
            final int gettedId = getIntent().getIntExtra("executorIdFragment", 42);
            Executor cur = provider.getExecutor(gettedId);
            String personname = " ";
            String lastname = " ";

            Persons p = provider.getPerson(cur.getPersonId());
            if (p != null) {
                personname = p.getName();
                lastname = p.getLastname();

                photo.setImageBitmap(provider.decodeByteToBitmap(p.getPhoto()));
            }

            personName.setText(personname + lastname);



           /* int sectionId= cur.getSectionId();
            Section_of_services f = provider.getSection(sectionId);
            section.setText(f.getTitle());*/
        } else {
            Toast.makeText(Executors_view_activity.this, "error", Toast.LENGTH_SHORT).show();
        }
    }


}