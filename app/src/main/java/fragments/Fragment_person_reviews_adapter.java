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

public class Fragment_person_reviews_adapter extends RecyclerView.Adapter<Fragment_person_reviews_adapter.MyViewHolder>{
    private Context context;
    Activity activity;
    MyDataProvider provider;
    ArrayList<Review> reviews;


    Persons curPerson;
    private Menu review_popupMenu;
    boolean isCustomer = false;

    boolean hasReview = false;


    public Fragment_person_reviews_adapter( Activity activity,Context context, ArrayList<Review> reviews) {
        this.context = context;
        this.activity = activity;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_profile_reviews_adapter, parent, false);
        return new Fragment_person_reviews_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        provider = new MyDataProvider(context);

        final Review  review = reviews.get(position);
        int personId = review.getCustomerId();
       final Persons p = provider.getPerson(personId);
        String text = "";
        int rating = -1;
        try {
            text = review.getReview_text();
            rating =review.getAssessment();
        }catch (Exception e){
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

                activity.startActivity(intent);
            }
        });

        curPerson = provider.getLoggedInPerson();
        if (review.getCustomerId() == curPerson.getId()) {
            isCustomer = true;
        }

        holder.btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.review_popup_menu_edit:
                               showDialogUpdate(review.getId());
                                return true;
                            case R.id.order_popup_delete:
                                provider.deleteReview(position);
                                notifyDataSetChanged();
                                return true;
                            case R.id.order_popup_complain:
                                //TODO: доделать методы
                                Toast.makeText(context, "отправлена", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }

                    }
                });
                popup.inflate(R.menu.review_popup_menu);
                review_popupMenu = popup.getMenu();
                if (review_popupMenu != null) {
                    review_popupMenu.findItem(R.id.review_popup_menu_edit).setVisible(isCustomer);
                    review_popupMenu.findItem(R.id.review_popup_menu_delete).setVisible(isCustomer);
                }
                popup.show();
            }
        });
    }

    private void showDialogUpdate(final int reviewId) {
        final Dialog dialog = new Dialog(activity);

        dialog.setContentView(R.layout.dialog_review_update);
        dialog.setTitle("Редактировать заказ");

        final EditText txtAssessment = dialog.findViewById(R.id.dialog_review_update_etAssessm);
        final EditText txtText = dialog.findViewById(R.id.dialog_review_update_etText);
        Button btnSave = dialog.findViewById(R.id.dialog_review_update_btn_save);
        Button btnCancel = dialog.findViewById(R.id.dialog_review_update_btn_cancel);

        dialog.getWindow().setLayout(720, 1300);
        dialog.setCancelable(false);
        dialog.show();
        final Review review = provider.getReview(reviewId);
        txtAssessment.setText(review.getAssessment() + "");
        txtText.setText(review.getReview_text());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review.setReview_text(txtText.getText().toString().trim());
                review.setAssessment(Integer.parseInt(txtAssessment.getText().toString().trim()));

                provider.updateReview(review);
                notifyDataSetChanged();
                Toast.makeText(context, "Изменения сохранены", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    @Override
    public int getItemCount() {
        if(reviews == null){
            return 0;
        }
        return reviews.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text, assessment, name, date;
        ImageView photo;
        Button btn_popup_menu;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.fragment_profile_reviews_adapter_text);
            assessment = itemView.findViewById(R.id.fragment_profile_reviews_adapter_assessment);
            name = itemView.findViewById(R.id.fragment_profile_reviews_adapter_name);
            photo = itemView.findViewById(R.id.fragment_profile_reviews_adapter_image);
            date = itemView.findViewById(R.id.fragment_profile_reviews_adapter_created_date);
            btn_popup_menu = itemView.findViewById(R.id.fragment_profile_reviews_adapter_btn_popup);

            adapter_layout = itemView.findViewById(R.id.fragment_profile_reviews_adapter_layout);
        }
    }
}
