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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.Answer;
import models.MyDataProvider;
import models.MyUtils;
import models.Persons;
import models.Review;

public class Fragment_person_reviews_adapter extends RecyclerView.Adapter<Fragment_person_reviews_adapter.MyViewHolder>{
    private Context context;
    MyDataProvider provider;
    ArrayList<Review> reviews;

    Persons curPerson;
    private Menu review_popupMenu;

    Fragment_executor_reviews_answers_adapter exec_rev_answers_adapter;
    private RecyclerView.RecycledViewPool recyclerpool = new RecyclerView.RecycledViewPool();;


    public Fragment_person_reviews_adapter(Context context, ArrayList<Review> reviews) {
        this.context = context;
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
        curPerson = provider.getLoggedInPerson();

        final Review  review = reviews.get(position);
        ArrayList<Answer> answers = review.getAnswers();
        if (answers == null) {
            answers = new ArrayList<>();
        }

        final int personId = review.getCustomerId();
       final Persons p = provider.getPerson(personId);
        String text = review.getReview_text();
        int rating =review.getAssessment();

        holder.text.setText(text);
        holder.assessment.setText("Оценка: "+rating);
        holder.name.setText(p.getName()+ " "+p.getLastname());

        if(p.getPhoto()!= null){
            holder.photo.setImageBitmap(MyUtils.decodeByteToBitmap(p.getPhoto()));
        }
        Long l = review.getCreatedDate();
        holder.date.setText(MyUtils.convertLongToDataString(l));

        exec_rev_answers_adapter = new Fragment_executor_reviews_answers_adapter(context, answers);
        LinearLayoutManager lmanager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        lmanager.setInitialPrefetchItemCount(answers.size());

        holder.answersRv.setLayoutManager(lmanager);
        holder.answersRv.setAdapter(exec_rev_answers_adapter);
        holder.answersRv.setRecycledViewPool(recyclerpool);


        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int executorId = provider.getExecutorIdByPersonId(personId);

                if (personId == curPerson.getId()) {
                    Intent intent = new Intent(context, MyProfileActivity.class);
                    context.startActivity(intent);
                }  else if(executorId != 0 && executorId != -1){
                    Intent intent = new Intent(context,  Executors_view_activity.class);
                    intent.putExtra("executorIdFragment", executorId);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, PersonProfileActivity.class);
                    intent.putExtra("orderview_PersonId", personId);

                    context.startActivity(intent);
                }
            }
        });




        holder.btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCustomer = false;
                if (review.getCustomerId() == curPerson.getId()) {
                isCustomer = true;
            }
                PopupMenu popup = new PopupMenu(context, v);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.review_popup_menu_edit:
                               showDialogUpdate(review.getId());
                                return true;
                            case R.id.review_popup_menu_delete:
                                showDialogDelete(review.getId());
                                return true;
                            case R.id.review_popup_menu_complain:
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
                    review_popupMenu.findItem(R.id.review_popup_menu_complain).setVisible(!isCustomer);
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

    private void showDialogUpdate(final int reviewId) {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_review_update);
        dialog.setTitle("Редактировать отзыв");

        final EditText txtAssessment = dialog.findViewById(R.id.dialog_review_update_etAssessm);
        final EditText txtText = dialog.findViewById(R.id.dialog_review_update_etText);
        Button btnSave = dialog.findViewById(R.id.dialog_review_update_btn_save);
        Button btnCancel = dialog.findViewById(R.id.dialog_review_update_btn_cancel);

        dialog.setCancelable(true);
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


    private void showDialogDelete(final int id) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_answer_delete);

        Button btnSave = dialog.findViewById(R.id.dialog_answer_delete_btn_save);
        Button btnCancel = dialog.findViewById(R.id.dialog_answer_delete_btn_cancel);

        dialog.setCancelable(true);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provider.deleteReview(id);
                notifyDataSetChanged();
                Toast.makeText(context, "Отзыв удален", Toast.LENGTH_LONG).show();
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
        View view;
        RecyclerView answersRv;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            text = itemView.findViewById(R.id.fragment_profile_reviews_adapter_text);
            assessment = itemView.findViewById(R.id.fragment_profile_reviews_adapter_assessment);
            name = itemView.findViewById(R.id.fragment_profile_reviews_adapter_name);
            photo = itemView.findViewById(R.id.fragment_profile_reviews_adapter_image);
            date = itemView.findViewById(R.id.fragment_profile_reviews_adapter_created_date);
            btn_popup_menu = itemView.findViewById(R.id.fragment_profile_reviews_adapter_btn_popup);
            answersRv = itemView.findViewById(R.id.fragment_profile_reviews_adapter_answersrecycler);

            adapter_layout = itemView.findViewById(R.id.fragment_profile_reviews_adapter_layout);
        }
    }
}
