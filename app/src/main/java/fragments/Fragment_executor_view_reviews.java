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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectwnavigation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import models.Executor;
import models.MyDataProvider;
import models.Persons;
import models.Review;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_executor_view_reviews#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_executor_view_reviews extends Fragment {
    private static final String ARG_ID_R = "argExecutorIdReviews";

    private int executorId;

    TextView rating;
    RecyclerView reviewsRv;
    Executor_reviews_adapter_frg executor_reviews_adapter_frg;
    ArrayList<Review> reviews;
    FloatingActionButton btn_add;

    MyDataProvider provider;
    Context context;

    public Fragment_executor_view_reviews() {
    }

    public static Fragment_executor_view_reviews newInstance(int executorId) {
        Fragment_executor_view_reviews fragment = new Fragment_executor_view_reviews();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_R, executorId);
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
            executorId = getArguments().getInt(ARG_ID_R);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_executor_view_reviews, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        reviewsRv = view.findViewById(R.id.fragment_executor_view_reviews_rv);
        insertArray();
        executor_reviews_adapter_frg = new Executor_reviews_adapter_frg(getActivity(), context, reviews);
        reviewsRv.setAdapter(executor_reviews_adapter_frg);
        reviewsRv.setLayoutManager(new LinearLayoutManager(context));

        Executor executor = provider.getExecutor(executorId);
        rating = view.findViewById(R.id.fragment_executor_view_reviews_rating);
        btn_add = view.findViewById(R.id.fragment_executor_view_reviews_fb);


        Persons p = provider.getPerson(executor.getPersonId());
        int rat = -1;
        if (p != null) {
            rat = p.getRating();
        }
        rating.setText(rat+"");

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