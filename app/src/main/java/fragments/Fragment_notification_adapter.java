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

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.MyDataProvider;
import models.MyUtils;
import models.Notify;
import models.Order;
import models.Persons;

public class Fragment_notification_adapter extends RecyclerView.Adapter<Fragment_notification_adapter.MyViewHolder>{
    Context context;
    MyDataProvider provider;
    ArrayList<Notify> notifies;

    Persons curPerson;

    public Fragment_notification_adapter( Context context, ArrayList<Notify> notifies) {
        this.context = context;
        this.notifies = notifies;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {
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
        curPerson = provider.getLoggedInPerson();

        final Notify notify = notifies.get(position);
        int notifyId = notify.getId();
      //  final Order order =  provider.getOrder(orderId);

        holder.title.setText(notify.getText());
        String created = MyUtils.convertLongToDataString(notify.getCreatedDate());
        holder.createdDate.setText(created);

       final int section =notify.getSectionId();
       final int src = notify.getSrcId();
       int drawb = R.drawable.ic_notification;
       if(section == 1){
           drawb= R.drawable.response;
           Order order =provider.getOrder(src);
           holder.subText.setText(order.getTitle());
       }else if(section ==2){
           drawb= R.drawable.review;
           Persons p = provider.getPerson(src);
           holder.subText.setText(p.getName()+ " " + p.getLastname());
       }else if(section ==3){
           drawb= R.drawable.msg;
           Persons p = provider.getPerson(src);
           holder.subText.setText(p.getName()+ " " + p.getLastname());
       }
        holder.icon.setImageDrawable(ContextCompat.getDrawable(context, drawb));


        holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(section == 1){
                    Intent intent = new Intent(context, Orders_view_activity.class);
                    intent.putExtra("orderIdFragment", src);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else if(section == 2){
                    int executorId = provider.getExecutorIdByPersonId(src);
                    if(executorId != 0 && executorId != -1){
                        Intent intent = new Intent(context,  Executors_view_activity.class);
                        intent.putExtra("executorIdFragment", executorId);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(context, PersonProfileActivity.class);
                        intent.putExtra("orderview_PersonId", src);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);}

                }
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
