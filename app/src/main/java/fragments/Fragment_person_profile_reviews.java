package fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectwnavigation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import models.MyDataProvider;
import models.Persons;
import models.Review;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_person_profile_reviews#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_person_profile_reviews extends Fragment {
    private static final String R_ARG_ID = "argPersonId";

    private int personId;

    TextView rating;
    RecyclerView reviewsRv;
    Fragment_person_reviews_adapter fragment_person_reviews_adapter;
    ArrayList<Review> reviews;
    FloatingActionButton btn_add;

    MyDataProvider provider;
    Context context;


    public Fragment_person_profile_reviews() {
    }

    public static Fragment_person_profile_reviews newInstance(int personID) {
        Fragment_person_profile_reviews fragment = new Fragment_person_profile_reviews();
        Bundle args = new Bundle();
        args.putInt(R_ARG_ID, personID);
        fragment.setArguments(args);
        return fragment;
    }

    public void setContext(Context context) {
        this.provider = new MyDataProvider(context);
        this.context = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            personId = getArguments().getInt(R_ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person_profile_reviews, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        reviewsRv = view.findViewById(R.id.fragment_person_profile_reviews_rv);
        insertArray();
        fragment_person_reviews_adapter = new Fragment_person_reviews_adapter(getActivity(), context, reviews);
        reviewsRv.setAdapter(fragment_person_reviews_adapter);
        reviewsRv.setLayoutManager(new LinearLayoutManager(context));

       Persons person = provider.getPerson(personId);
        rating = view.findViewById(R.id.fragment_person_profile_reviews_rating);
        btn_add = view.findViewById(R.id.fragment_person_profile_reviews_fb);

        rating.setText(person.getRating() +"");

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO onclick method
            }
        });

        super.onViewCreated(view, savedInstanceState);

    }

    void insertArray() {
        reviews = provider.getReviews();
    }
}
