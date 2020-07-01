package fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.Answer;
import models.ApiProvider;
import models.MyDataProvider;
import models.MyUtils;
import models.Persons;
import models.Review;

public class Fragment_myprofile_reviews_adapter extends RecyclerView.Adapter<Fragment_myprofile_reviews_adapter.MyViewHolder> {
    private Context context;
    MyDataProvider provider;
    ApiProvider apiProvider;
    ArrayList<Review> reviews;
    private Menu review_popupMenu;
    Persons curPerson;

    Fragment_myprofile_reviews_answers_adapter answers_adapter;
    private RecyclerView.RecycledViewPool recyclerpool = new RecyclerView.RecycledViewPool();

    public Fragment_myprofile_reviews_adapter(Context context, ArrayList<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text, assessment, name, date;
        RecyclerView answersRv;
        ImageView photo;
        Button btn_popup_menu;
        View view;
        RelativeLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            text = itemView.findViewById(R.id.myprofile_reviews_adapter_row_text);
            assessment = itemView.findViewById(R.id.myprofile_reviews_adapter_row_assessment);
            name = itemView.findViewById(R.id.myprofile_reviews_adapter_row_name);
            photo = itemView.findViewById(R.id.myprofile_reviews_adapter_row_image);
            date = itemView.findViewById(R.id.myprofile_reviews_adapter_row_created);
            btn_popup_menu = itemView.findViewById(R.id.myprofile_reviews_adapter_row_btn_popup);
            answersRv = itemView.findViewById(R.id.myprofile_reviews_adapter_row_answersrv);

            adapter_layout = itemView.findViewById(R.id.myprofile_reviews_adapter_row_layout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_myprofile_reviews_adapter_row, parent, false);
        return new Fragment_myprofile_reviews_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();

        curPerson = provider.getLoggedInPerson();

        final Review review = reviews.get(position);
        ArrayList<Answer> answers = review.getAnswers();
        if(answers == null) {
            answers = new ArrayList<>();
        }
        int personId = review.getCustomerId();
        final Persons p =apiProvider.getPerson(personId); // provider.getPerson(personId);

        String text = "";
        int rating = -1;

        text = review.getReview_text();

        rating = review.getAssessment();

        holder.text.setText(text);
        holder.assessment.setText("Оценка: "+rating);
        holder.name.setText(p.getName()+ " "+p.getLastname());

        if(p.getPhoto()!= null){
            holder.photo.setImageBitmap(MyUtils.decodeByteToBitmap(p.getPhoto()));
        }
        Long l = review.getCreatedDate();
        holder.date.setText(MyUtils.convertLongToDataString(l));

        answers_adapter = new Fragment_myprofile_reviews_answers_adapter(context, answers);

        LinearLayoutManager lmanager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        lmanager.setInitialPrefetchItemCount(answers.size());

        holder.answersRv.setLayoutManager(lmanager);
        holder.answersRv.setAdapter(answers_adapter);
        holder.answersRv.setRecycledViewPool(recyclerpool);


        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int personId =  p.getId();
                int executorId = provider.getExecutorIdByPersonId(personId);

                 if(executorId != 0 && executorId != -1){
                    Intent intent = new Intent(context,  Executors_view_activity.class);
                    intent.putExtra("executorIdFragment", executorId);
                     context.startActivity(intent);
                }
                else{
                    Intent intent = new Intent(context, PersonProfileActivity.class);
                    intent.putExtra("orderview_PersonId", personId);
                     context.startActivity(intent);}
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

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_createanswer);

                final EditText etText = dialog.findViewById(R.id.dialog_createanswer_text);
                Button btnSave = dialog.findViewById(R.id.dialog_createanswer_btn_save);
                Button btnCancel = dialog.findViewById(R.id.dialog_createanswer_btncancel);

                dialog.setCancelable(true);
                dialog.show();

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Answer answer = new Answer(review.getId(), curPerson.getId(), review.getCustomerId(),
                                    etText.getText().toString().trim(), MyUtils.getCurentDateInLong());
                            provider.addAnswer(answer);
                            review.getAnswers().add(answer);
                            provider.updateReviewNAnswers(review);
                        }catch(Exception e){
                            Log.e("errpr", e.getMessage());
                        }

                        notifyDataSetChanged();
                        Toast.makeText(context, "ответ отправлен", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                return true;
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



}
