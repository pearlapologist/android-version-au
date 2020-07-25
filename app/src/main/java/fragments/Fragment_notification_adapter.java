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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.MyProfile_reviews_activity;
import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.ApiProvider;
import models.MyDataProvider;
import models.MyUtils;
import models.Notify;
import models.Order;
import models.Persons;

public class Fragment_notification_adapter extends RecyclerView.Adapter<Fragment_notification_adapter.MyViewHolder> {
    Context context;
    MyDataProvider provider;
    ApiProvider apiProvider;
    ArrayList<Notify> notifies;

    Persons curPerson;

    public Fragment_notification_adapter(Context context, ArrayList<Notify> notifies) {
        this.context = context;
        this.notifies = notifies;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, createdDate, subText;
        ImageView icon;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.frg_notify_adapter_text);
            createdDate = itemView.findViewById(R.id.frg_notify_adapter_date);
            subText = itemView.findViewById(R.id.frg_notify_adapter_subtext);
            icon = itemView.findViewById(R.id.frg_notify_adapter_icon);

            adapter_layout = itemView.findViewById(R.id.frg_notify_adapter_layout);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_notification_adapter_row, parent, false);
        return new Fragment_notification_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();
        curPerson = provider.getLoggedInPerson();

        final Notify notify = notifies.get(position);

        holder.title.setText(notify.getText());
        String created = MyUtils.convertLongToDataString(notify.getCreatedDate());
        holder.createdDate.setText(created);

        final int section = notify.getSectionId();
        final int src = notify.getSrcId();
        int drawb = R.drawable.ic_notification;
        if (section == 1) {
            drawb = R.drawable.response;

            Order order = null;
            try {
                order =apiProvider.getOrder(src); //provider.getOrder(src);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (order != null) {
                holder.subText.setText(order.getTitle());
            }
        } else if (section == 2) {
            drawb = R.drawable.ic_contact_message;

            Persons p = null; // provider.getPerson(src);
            try {
                p = apiProvider.getPerson(src);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String name = "";
            if(p != null){
                name = p.getName() + " " + p.getLastname();
            }else{
                name = "Пользователь удален или не найден";
            }
            holder.subText.setText (name +" оставил(а) вам новый отзыв");
        } else if (section == 3) {
            drawb = R.drawable.msg;

            Persons p = null;
            try {
                p = apiProvider.getPerson(src);  // Persons p = provider.getPerson(src);
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.subText.setText(p.getName() + " " + p.getLastname());
        }
        holder.icon.setImageDrawable(ContextCompat.getDrawable(context, drawb));

        holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyProfile_reviews_activity.class);
                intent.putExtra("orderview_PersonId", src);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (section == 1) {
                    intent = new Intent(context, Orders_view_activity.class);
                    intent.putExtra("orderIdFragment", src);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (notifies == null) {
            return 0;
        }
        return notifies.size();
    }


}
