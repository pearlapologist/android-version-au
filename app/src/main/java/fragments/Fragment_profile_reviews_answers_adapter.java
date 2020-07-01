package fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.Answer;
import models.ApiProvider;
import models.MyDataProvider;
import models.MyUtils;
import models.Persons;

public class Fragment_profile_reviews_answers_adapter  extends RecyclerView.Adapter<Fragment_profile_reviews_answers_adapter.MyViewHolder> {
    private Context context;
    MyDataProvider provider;
    ApiProvider apiProvider;
    ArrayList<Answer> answers;
    Persons curPerson;

    Menu answer_popup_menu;


    public Fragment_profile_reviews_answers_adapter(Context context, ArrayList<Answer> answers) {
        this.context = context;
        this.answers = answers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_myprofile_reviews_answers_adapter_row, parent, false);
        return new Fragment_profile_reviews_answers_adapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();

        curPerson = provider.getLoggedInPerson();

        final Answer answer = answers.get(position);
        final int whoanswers = answer.getWhoanswersId();
        final Persons p =apiProvider.getPerson(whoanswers); // provider.getPerson(whoanswers);
        String text = answer.getText();
        holder.text.setText(text);
        holder.name.setText(p.getName() + " " + p.getLastname());

        if (p.getPhoto() != null) {
            holder.photo.setImageBitmap(MyUtils.decodeByteToBitmap(p.getPhoto()));
        }
        Long l = answer.getCreatedDate();
        holder.date.setText(MyUtils.convertLongToDataString(l));


        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int executorId = provider.getExecutorIdByPersonId(whoanswers);
                if (whoanswers == curPerson.getId()) {
                    Intent intent = new Intent(context, MyProfileActivity.class);
                    context.startActivity(intent);
                } else if(executorId != 0 && executorId != -1){
                    Intent intent = new Intent(context,  Executors_view_activity.class);
                    intent.putExtra("executorIdFragment", executorId);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, PersonProfileActivity.class);
                    intent.putExtra("orderview_PersonId", p.getId());

                    context.startActivity(intent);
                }
            }
        });


        holder.btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCreater = false;
                if (whoanswers == curPerson.getId()) {
                    isCreater = true;
                }
                PopupMenu popup = new PopupMenu(context, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.review_answer_popup_menu_edit:
                                showDialogUpdate(answer);
                                return true;
                            case R.id.review_answer_popup_menu_delete:
                                showDialogDelete(answer.getId());
                                return true;
                            case R.id.review_answer_popup_menu_complain:
                                Toast.makeText(context, "жалоба отправлена", Toast.LENGTH_SHORT).show();
                                //TODO: доделать
                                return true;
                            default:
                                return false;
                        }

                    }
                });
                popup.inflate(R.menu.review_answer_popup_menu);
                answer_popup_menu = popup.getMenu();
                if (answer_popup_menu != null) {
                    answer_popup_menu.findItem(R.id.review_answer_popup_menu_edit).setVisible(isCreater);
                    answer_popup_menu.findItem(R.id.review_answer_popup_menu_delete).setVisible(isCreater);
                    answer_popup_menu.findItem(R.id.review_answer_popup_menu_complain).setVisible(!isCreater);
                }
                popup.show();
            }
        });
    }


    private void showDialogUpdate(final Answer answer) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_answer_update);

        final EditText txtText = dialog.findViewById(R.id.dialog_answer_update_text);
        Button btnSave = dialog.findViewById(R.id.dialog_answer_update_btn_save);
        Button btnCancel = dialog.findViewById(R.id.dialog_answer_update_btn_cancel);
        txtText.setText(answer.getText());

        dialog.setCancelable(true);
        dialog.show();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setText(txtText.getText().toString().trim());
                provider.updateAnswer(answer);
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
                provider.deleteAnswer(id);
                notifyDataSetChanged();
                Toast.makeText(context, "Ответ удален", Toast.LENGTH_LONG).show();
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
        if (answers == null) {
            return 0;
        }
        return answers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text, name, date;
        ImageView photo;
        Button btn_popup_menu;

        RelativeLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.myprofile_reviews_answers_adapter_row_text);
            name = itemView.findViewById(R.id.myprofile_reviews_answers_adapter_row_name);
            photo = itemView.findViewById(R.id.myprofile_reviews_answers_adapter_row_photo);
            date = itemView.findViewById(R.id.myprofile_reviews_answers_adapter_row_created);
            btn_popup_menu = itemView.findViewById(R.id.myprofile_reviews_answers_adapter_row_btn_popup);


            adapter_layout = itemView.findViewById(R.id.myprofile_reviews_answers_adapter_row_layout);

        }
    }
}
