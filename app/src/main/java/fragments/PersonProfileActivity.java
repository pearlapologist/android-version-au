package fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectwnavigation.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.synnapps.carouselview.ImageClickListener;

import models.ApiProvider;
import models.MyDataProvider;
import models.MyUtils;
import models.Persons;

public class PersonProfileActivity extends AppCompatActivity {
    TextView personName;
    ImageView photo;
    MyDataProvider provider;
    ApiProvider apiProvider;
    Button chat;
    // CarouselView carousel;
    BottomNavigationView bottomNavigation;

    Fragment_person_profile_info frg_person_profile_info;
    Fragment_person_profile_reviews frg_person_profile_reviews;

    int curPersonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_profile);
        provider = new MyDataProvider(this);
        apiProvider = new ApiProvider();
        personName = findViewById(R.id.person_profile_tvName);
        photo = findViewById(R.id.person_profile_image);

        if (getIntent().hasExtra("orderview_PersonId")) {
            curPersonId = getIntent().getIntExtra("orderview_PersonId", -1);
        }

        //TODO: delete this check
        if (getIntent().hasExtra("responseAdapter")) {
            curPersonId = getIntent().getIntExtra("responseAdapter", -1);
        }

        frg_person_profile_info = Fragment_person_profile_info.newInstance(curPersonId);
        frg_person_profile_reviews = Fragment_person_profile_reviews.newInstance(curPersonId);

        frg_person_profile_info.setContext(this);
        frg_person_profile_reviews.setContext(this);


        // carousel = findViewById(R.id.fragment_executor_view_carousel);
        bottomNavigation = findViewById(R.id.person_profile_navbar);
        bottomNavigation.setOnNavigationItemSelectedListener(listener);
        getAndSetExecutorIntentData();


        // carousel.setPageCount(mImages.length);
        //carousel.setImageListener(imageListener);
       /* carousel.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);
            }
        });*/
/*
       carousel.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(Executors_view_activity.this, mImagesTitle[position], Toast.LENGTH_SHORT).show();
            }
        });*/

        getSupportFragmentManager().beginTransaction().replace(R.id.person_profile_framelayout, frg_person_profile_info).commit();
    }

    private OnNavigationItemSelectedListener listener = new OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selected = frg_person_profile_info;
            switch (menuItem.getItemId()) {
                case R.id.menu_profile_info:
                    selected = frg_person_profile_info;
                    break;
                case R.id.menu_profile_reviews:
                    selected = frg_person_profile_reviews;
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.person_profile_framelayout, selected).commit();

            return true;
        }
    };

    public void getAndSetExecutorIntentData() {
        if(curPersonId != -1){
            Persons person = apiProvider.getPerson(curPersonId); //provider.getPerson(curPersonId);

            if (person.getPhoto() == null) {
                photo.setImageResource(R.drawable.executors_default_image);
            } else {
                photo.setImageBitmap(MyUtils.decodeByteToBitmap(person.getPhoto()));
            }

            personName.setText(person.getName() + " " + person.getLastname());

        } else {
            Toast.makeText(PersonProfileActivity.this, "error", Toast.LENGTH_SHORT).show();
        }
    }
}
