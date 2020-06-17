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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.MyProfile_myForm_activity;
import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.Bookmarks;
import models.Executor;
import models.MyDataProvider;
import models.MyUtils;
import models.Persons;
import models.Review;

public class Executors_adapter_frg extends RecyclerView.Adapter<Executors_adapter_frg.MyViewHolder> {
    private Context context;
    MyDataProvider provider;
    ArrayList<Executor> executors;

    Persons curPerson;
    private Menu popup_menu;


    Executors_adapter_frg(Context context, ArrayList<Executor> executors) {
        this.context = context;
        this.executors = executors;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, spcltn_txt, rating;
        ImageView photo;
        Button btn_popup_menu;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.executors_adapter_frg_name);
            rating = itemView.findViewById(R.id.executors_adapter_frg_rating);
            spcltn_txt = itemView.findViewById(R.id.executors_adapter_frg_spec);
            photo = itemView.findViewById(R.id.executors_adapter_frg_photo);
            btn_popup_menu = itemView.findViewById(R.id.executors_adapter_frg_btn_popup);

            adapter_layout = itemView.findViewById(R.id.executors_adapter_frg_layout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_executors_adapter_row, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Executors_adapter_frg.MyViewHolder holder, final int position) {
        provider = new MyDataProvider(context);
        curPerson = provider.getLoggedInPerson();

        final Executor executor = executors.get(position);
        holder.spcltn_txt.setText(executor.getSpecialztn());
        final Persons p = provider.getPerson(executor.getPersonId());
        if (p.getPhoto() == null) {
            holder.photo.setImageResource(R.drawable.executors_default_image);
        } else {
            holder.photo.setImageBitmap(MyUtils.decodeByteToBitmap(p.getPhoto()));
        }
        holder.name.setText(p.getName());
        holder.rating.setText(p.getRating() + "");


        final int id = executor.getId();

        holder.btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCreator = false;
                if (p.getId() == curPerson.getId()) {
                    isCreator = true;
                }


                boolean exists = false;
                Bookmarks b = provider.getBookmarkByExecutorId(executor.getId());
                if (b != null) {
                    exists = true;
                }

                PopupMenu popup = new PopupMenu(context, v);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.order_popup_bookm:
                                provider.putExecutorInMyBookmarks(executor.getId());
                                Toast.makeText(context, "Специалист добавлен в ваши закладки", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.order_popup_edit:
                                Intent intent = new Intent(context, MyProfile_myForm_activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                return true;
                            case R.id.order_popup_delete:
                                showDialogDelete(id);
                                return true;
                            case R.id.order_popup_bookm_delete:
                                showDialogDeleteFromBookm(id);
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

                popup.inflate(R.menu.order_popup);
                popup_menu = popup.getMenu();
                if (popup_menu != null) {
                    popup_menu.findItem(R.id.order_popup_edit).setVisible(isCreator);
                    popup_menu.findItem(R.id.order_popup_delete).setVisible(isCreator);
                    popup_menu.findItem(R.id.order_popup_complain).setVisible(!isCreator);
                    popup_menu.findItem(R.id.order_popup_bookm_delete).setVisible(exists);
                    if ((exists == true) || isCreator) {
                        popup_menu.findItem(R.id.order_popup_bookm).setVisible(false);
                    }

                }
                popup.show();
            }
        });


        holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == provider.getExecutorIdByPersonId(provider.getLoggedInPerson().getId())) {
                    Intent intent = new Intent(context, MyProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, Executors_view_activity.class);
                    intent.putExtra("executorIdFragment", id);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    private void showDialogDelete(final int executorId) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        dialog.setContentView(R.layout.dialog_answer_delete);
        Button btnSave = dialog.findViewById(R.id.dialog_answer_delete_btn_save);
        Button btnCancel = dialog.findViewById(R.id.dialog_answer_delete_btn_cancel);

        dialog.setCancelable(true);
        dialog.show();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    provider.deleteExecutor(executorId);
                    executors.remove(provider.getExecutor(executorId));
                    Toast.makeText(context, "анкета успешно удалена", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
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

    private void showDialogDeleteFromBookm(final int executorId) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setContentView(R.layout.dialog_answer_delete);
        Button btnSave = dialog.findViewById(R.id.dialog_answer_delete_btn_save);
        Button btnCancel = dialog.findViewById(R.id.dialog_answer_delete_btn_cancel);

        dialog.setCancelable(true);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provider.deleteExecutorFromMyBookmarks(executorId);
                notifyDataSetChanged();
                Toast.makeText(context, "Специалист удален из ваших закладок", Toast.LENGTH_SHORT).show();
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
        if (executors == null) {
            return 0;
        }
        return executors.size();
    }

}
