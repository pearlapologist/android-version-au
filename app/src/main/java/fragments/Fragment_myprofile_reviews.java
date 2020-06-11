package fragments;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.TextView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.MyDataProvider;
import models.Persons;
import models.Review;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_myprofile_reviews#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_myprofile_reviews extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    TextView rating;
    RecyclerView reviewsRv;
    Fragment_myprofile_reviews_adapter fragment_myprofile_reviews_adapter;
    ArrayList<Review> reviews;

   // int statusId = 0;

    MyDataProvider provider;
    Context context;

    Persons curPerson;

    public Fragment_myprofile_reviews(Context context) {
        this.provider = new MyDataProvider(context);
        this.context = context;
    }

    public Fragment_myprofile_reviews() {
    }


    public static Fragment_myprofile_reviews newInstance(String param1, String param2) {
        Fragment_myprofile_reviews fragment = new Fragment_myprofile_reviews();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myprofile_reviews, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        reviewsRv = view.findViewById(R.id.fragment_myprofile_reviews_rv);
        rating = view.findViewById(R.id.fragment_myprofile_reviews_rating);

        curPerson = provider.getLoggedInPerson();
        insertArray();
        fragment_myprofile_reviews_adapter = new Fragment_myprofile_reviews_adapter(this, context, reviews);
        reviewsRv.setAdapter(fragment_myprofile_reviews_adapter);
        reviewsRv.setLayoutManager(new LinearLayoutManager(context));



       int rat = -1;
        if (curPerson != null) {
            rat = curPerson.getRating();
        }
        rating.setText(rat+"");

        super.onViewCreated(view, savedInstanceState);
    }


    void insertArray() {
        reviews = provider.getAllPersonReviewByPersonId(curPerson.getId());
    }

    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.createform_service_dialog);
        dialog.setTitle("Добавить услугу");
/*

        final EditText edTitle = dialog.findViewById(R.id.createform_service_dialog_title);
        final EditText edPrice = dialog.findViewById(R.id.createform_service_dialog_price);
        Button btnSave = dialog.findViewById(R.id.createform_service_dialog_btnCreate);
        Button btnCancel = dialog.findViewById(R.id.createform_service_dialog_btnCancel);
*/

        dialog.getWindow().setLayout(720, 800);
        dialog.setCancelable(true);
        dialog.show();
    }
}
