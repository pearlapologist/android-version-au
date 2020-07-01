package fragments;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.Conversation_view_activity;
import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.ApiProvider;
import models.Message;
import models.MyDataProvider;
import models.MyUtils;
import models.Persons;

public class Messages_adapter_frg extends RecyclerView.Adapter<Messages_adapter_frg.MyViewHolder> {
    private Context context;
    MyDataProvider provider;
    ApiProvider apiProvider;
    ArrayList<Integer> personsId;

    Persons curPerson;
    private Menu popup_menu;


    Messages_adapter_frg(Context context, ArrayList<Integer> personsId) {
        this.context = context;
        this.personsId = personsId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_msg_adapter_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();
        curPerson = provider.getLoggedInPerson();

        final int personId = personsId.get(position);

        final Persons person1 = apiProvider.getPerson(personId);  // provider.getPerson(personId);


        if (person1 == null) {
            holder.photo.setImageResource(R.drawable.executors_default_image);
            holder.name.setText("Пользователь не найден, либо удален");
        } else {
            holder.name.setText(person1.getName() + " " + person1.getLastname());

            if (person1.getPhoto() == null) {
                holder.photo.setImageBitmap(MyUtils.decodeByteToBitmap(person1.getPhoto()));
            }
        }


        holder.btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(context, v);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.order_popup_delete:
                                // showDialogDelete(id);
                                return true;
                            case R.id.order_popup_complain:
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
                    popup_menu.findItem(R.id.order_popup_edit).setVisible(false);
                    popup_menu.findItem(R.id.order_popup_delete).setVisible(true);
                    popup_menu.findItem(R.id.order_popup_complain).setVisible(true);
                    popup_menu.findItem(R.id.order_popup_bookm_delete).setVisible(false);
                    popup_menu.findItem(R.id.order_popup_bookm).setVisible(false);

                }
                popup.show();
            }
        });


        holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personId == curPerson.getId()) {
                    Intent intent = new Intent(context, MyProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, Conversation_view_activity.class);
                    intent.putExtra("msg_adapter", personId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

        holder.photo.setOnClickListener(new View.OnClickListener() {
            int executorId = provider.getExecutorIdByPersonId(personId);

            @Override
            public void onClick(View v) {

                if (personId == curPerson.getId()) {
                    Intent intent = new Intent(context, MyProfileActivity.class);
                    context.startActivity(intent);
                } else if (executorId != 0 && executorId != -1) {
                    Intent intent = new Intent(context, Executors_view_activity.class);
                    intent.putExtra("executorIdFragment", executorId);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, PersonProfileActivity.class);
                    intent.putExtra("orderview_PersonId", personId);
                    context.startActivity(intent);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        if (personsId == null) {
            return 0;
        }
        return personsId.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        ImageView photo;
        Button btn_popup_menu;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.frg_msg_adapter_pName);
            photo = itemView.findViewById(R.id.frg_msg_adapter_pPhoto);
            btn_popup_menu = itemView.findViewById(R.id.frg_msg_adapter_btn_popup);

            adapter_layout = itemView.findViewById(R.id.frg_msg_adapter_layout);
        }
    }
}
