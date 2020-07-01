package fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.ApiProvider;
import models.Message;
import models.MyDataProvider;
import models.Persons;

public class Conversation_messages_adapter extends RecyclerView.Adapter<Conversation_messages_adapter.MyViewHolder>{
    private Context context;
    MyDataProvider provider;
    ApiProvider apiProvider;
    ArrayList<Message> messages;

    Persons curPerson;
    private Menu popup_menu;


   public Conversation_messages_adapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_conversation_messages_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();

        curPerson = provider.getLoggedInPerson();

        final Message message = messages.get(position);
        holder.txt.setText(message.getText());
        final Persons p =apiProvider.getPerson(message.getWhosends()); //provider.getPerson(message.getWhosends());
        holder.name.setText(p.getName());

        final int id = message.getId();

       /* holder.btn_popup_menu.setOnClickListener(new View.OnClickListener() {
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
        });*/


       /* holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
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
        });*/

    }

    @Override
    public int getItemCount() {
            if (messages == null) {
                return 0;
            }
            return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, txt;
       // Button btn_popup_menu;
        RelativeLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.act_conversn_name);
           txt = itemView.findViewById(R.id.act_conversn_text);
            //btn_popup_menu = itemView.findViewById(R.id.act_conversn_layout);

            adapter_layout = itemView.findViewById(R.id.act_conversn_layout);
        }
    }
}
