package fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectwnavigation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import models.ApiProvider;
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
    ApiProvider apiProvider;
    Context context;

    Persons curPerson;

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
        apiProvider = new ApiProvider();
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
        rating = view.findViewById(R.id.fragment_executor_view_reviews_rating);
        btn_add = view.findViewById(R.id.fragment_executor_view_reviews_fb);

        curPerson = provider.getLoggedInPerson();

        Executor executor = apiProvider.getExecutor(executorId); // provider.getExecutor(executorId);

        Persons p = null;
        try {
            p = apiProvider.getPerson(executor.getPersonId());  // provider.getPerson(executor.getPersonId());
            reviews =apiProvider.getAllPersonReviewsById(p.getId()); // provider.getAllPersonReviewByPersonId(executorId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean b = false;
        ArrayList<Integer> arrId = new ArrayList<>();
        try {
            arrId =apiProvider.getLeavedReviewPersonsIdList(executorId); //provider.getLeavedReviewPersonsIdList(executorId);

            for (int i : arrId) {
                if (curPerson.getId() == i) {
                    b = true;
                }
            }
            if (b == true || curPerson.getId() == executorId) {
                btn_add.hide();
            }

        } catch (Exception e) {
            Log.e("profile reviews frag", e.getMessage());
        }

        executor_reviews_adapter_frg = new Executor_reviews_adapter_frg(context, reviews);
        reviewsRv.setAdapter(executor_reviews_adapter_frg);
        reviewsRv.setLayoutManager(new LinearLayoutManager(context));

        int rat = -1;
        if (p != null) {
            rat = p.getRating();
        }
        rating.setText(rat+"");

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonProfile_AddReviewActivity.class);
                int id = executorId;
                try {
                     id = apiProvider.getPersonIdByExecutorId(executorId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                intent.putExtra("addReview", id);
                startActivityForResult(intent, 1);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            if(resultCode == -1){
                executor_reviews_adapter_frg.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
