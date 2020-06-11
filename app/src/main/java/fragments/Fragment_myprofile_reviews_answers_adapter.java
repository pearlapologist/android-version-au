package fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.Answer;
import models.MyDataProvider;
import models.MyUtils;
import models.Persons;

public class Fragment_myprofile_reviews_answers_adapter  extends RecyclerView.Adapter<Fragment_myprofile_reviews_answers_adapter.MyViewHolder>{
    private Context context;
    Activity activity;
    MyDataProvider provider;
    ArrayList<Answer> answers;

    private Menu review_popupMenu;
    Persons curPerson;

    public Fragment_myprofile_reviews_answers_adapter(Activity activity, Context context,  ArrayList<Answer> answers) {
        this.context = context;
        this.activity = activity;
        this.answers = answers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_myprofile_reviews_answers_adapter_row, parent, false);
        return new Fragment_myprofile_reviews_answers_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);

        final Answer answer = answers.get(position);
        int executorId = answer.getExecutorId();
        final Persons p = provider.getPerson(executorId);
        String text = "";
        int rating = -1;

        try {
            text = answer.getText();
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.text.setText(text);
        holder.name.setText(p.getName()+ " "+p.getLastname());

        if(p.getPhoto()!= null){
            holder.photo.setImageBitmap(MyUtils.decodeByteToBitmap(p.getPhoto()));
        }
        Long l = answer.getCreatedDate();
        holder.date.setText(MyUtils.convertLongToDataString(l));

        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonProfileActivity.class);
                intent.putExtra("responseAdapter", p.getId());

                activity.startActivity(intent);
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
                            case R.id.review_popup_menu_edit:
                                showDialogUpdate(review.getId());
                                return true;
                            case R.id.review_popup_menu_delete:
                                provider.deleteReview(review.getId());
                                notifyDataSetChanged();
                                return true;
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
                    review_popupMenu.findItem(R.id.review_popup_menu_edit).setVisible(isCustomer);
                    review_popupMenu.findItem(R.id.review_popup_menu_delete).setVisible(isCustomer);
                    review_popupMenu.findItem(R.id.review_popup_menu_complain).setVisible(!isCustomer);
                }
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
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
