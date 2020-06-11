package fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.MyDataProvider;
import models.MyUtils;
import models.Persons;
import models.Review;

public class Fragment_myprofile_reviews_adapter extends RecyclerView.Adapter<Fragment_myprofile_reviews_adapter.MyViewHolder> {
    private Context context;
    Fragment_myprofile_reviews fragment;

    MyDataProvider provider;
    ArrayList<Review> reviews;

    private Menu review_popupMenu;

    public Fragment_myprofile_reviews_adapter(Fragment_myprofile_reviews fragment, Context context, ArrayList<Review> reviews) {
        this.context = context;
        this.fragment = fragment;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_myprofile_reviews_adapter_row, parent, false);
        return new Fragment_myprofile_reviews_adapter.MyViewHolder(view, fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);

        final Review review = reviews.get(position);
        int personId = review.getCustomerId();
        final Persons p = provider.getPerson(personId);

        String text = "";
        int rating = -1;

        try {
            text = review.getReview_text();
            rating = review.getAssessment();
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.text.setText(text);
        holder.assessment.setText("Оценка: "+rating);
        holder.name.setText(p.getName()+ " "+p.getLastname());

        if(p.getPhoto()!= null){
            holder.photo.setImageBitmap(MyUtils.decodeByteToBitmap(p.getPhoto()));
        }
        Long l = review.getCreatedDate();
        holder.date.setText(MyUtils.convertLongToDataString(l));

        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonProfileActivity.class);
                intent.putExtra("responseAdapter", p.getId());
                context.startActivity(intent);
            }
        });




        holder.btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.review_popup_menu_complain:
                                //TODO: доделать методы
                                Toast.makeText(context, "жалоба отправлена", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }

                    }
                });
                popup.inflate(R.menu.review_popup_menu);
                review_popupMenu = popup.getMenu();
                if (review_popupMenu != null) {
                    review_popupMenu.findItem(R.id.review_popup_menu_delete).setVisible(false);
                    review_popupMenu.findItem(R.id.review_popup_menu_edit).setVisible(false);
                }
                popup.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        if (reviews == null) {
            return 0;
        }
        return reviews.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text, assessment, name, date;
        ImageView photo;
        Button btn_popup_menu;
        View view;
        RelativeLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView, Fragment_myprofile_reviews fragment_reviews) {
            super(itemView);

            view = itemView;
            text = itemView.findViewById(R.id.myprofile_reviews_adapter_row_text);
            assessment = itemView.findViewById(R.id.myprofile_reviews_adapter_row_assessment);
            name = itemView.findViewById(R.id.myprofile_reviews_adapter_row_name);
            photo = itemView.findViewById(R.id.myprofile_reviews_adapter_row_image);
            date = itemView.findViewById(R.id.myprofile_reviews_adapter_row_created);
            btn_popup_menu = itemView.findViewById(R.id.myprofile_reviews_adapter_row_btn_popup);

            adapter_layout = itemView.findViewById(R.id.myprofile_reviews_adapter_row_layout);
            view.setOnClickListener(fragment_reviews);
        }
    }
}
