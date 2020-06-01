package fragments;

import android.app.Activity;
import android.app.Person;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.MyDataProvider;
import models.Persons;
import models.Review;

public class Executor_reviews_adapter_frg extends RecyclerView.Adapter<Executor_reviews_adapter_frg.MyViewHolder>{
    private Context context;
    Activity activity;
    MyDataProvider provider;
    ArrayList<Review> reviews;

    public Executor_reviews_adapter_frg(Activity activity, Context context,  ArrayList<Review> reviews) {
        this.context = context;
        this.activity = activity;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_executor_reviews_adapter_row, parent, false);
        return new Executor_reviews_adapter_frg.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);


        Review  review = reviews.get(position);
        int personId = review.getCustomerId();
        Persons p = provider.getPerson(personId);

        String text = "";
        int rating = -1;

        try {
            text = review.getReview_text();
            rating =review.getAssessment();
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.text.setText(text);
        holder.assessment.setText(rating + "");
        holder.name.setText(p.getName()+ p.getLastname());
    }

    @Override
    public int getItemCount() {
        if(reviews == null){
            return 0;
        }
        return reviews.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text, assessment, name;
        ImageView photo;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.executor_reviews_adapter_frg_text);
            assessment = itemView.findViewById(R.id.executor_reviews_adapter_frg_assessment);
            name = itemView.findViewById(R.id.executor_reviews_adapter_frg_name);
            photo = itemView.findViewById(R.id.executor_reviews_adapter_frg_photo);
            adapter_layout = itemView.findViewById(R.id.executor_reviews_adapter_frg_layout);
        }
    }
}
