package fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.MyUtils;
import models.MyDataProvider;
import models.Order;
import models.Persons;

public class Orders_adapter_frg extends RecyclerView.Adapter<Orders_adapter_frg.MyViewHolder> implements PopupMenu.OnMenuItemClickListener{
     Context context;
    Activity activity;
    MyDataProvider provider;
    ArrayList<Order> orders;

    Persons curPerson;
    private Menu popup_menu;
    boolean isCreator = false;

    public  Orders_adapter_frg(Activity activity, Context context, ArrayList<Order> orders) {
        this.context = context;
        this.activity = activity;
        this.orders = orders;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView  title, section, price, descr, deadline, createdDate;
        ImageView btn_popup_menu;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.orders_adapter_fragment_title);
            section = itemView.findViewById(R.id.orders_adapter_fragment_section);
            price = itemView.findViewById(R.id.orders_adapter_fragment_price);
            descr = itemView.findViewById(R.id.orders_adapter_fragment_desc);
//            deadline = itemView.findViewById(R.id.orders_adapter_fragment_deadline);
            createdDate= itemView.findViewById(R.id.orders_adapter_fragment_created);
            btn_popup_menu= itemView.findViewById(R.id.orders_adapter_fragment_btn_popup);

            adapter_layout = itemView.findViewById(R.id.orders_adapter_fragment_row_layout);
        }
    }

    @NonNull
    @Override
    public Orders_adapter_frg.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_orders_adapter, parent, false);
        return new Orders_adapter_frg.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Orders_adapter_frg.MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);

        Order order = orders.get(position);
        holder.title.setText(order.getTitle());
        holder.descr.setText(order.getDescription());
        holder.price.setText(order.getPrice() +"");
        String created =  MyUtils.convertLongToDataString(order.getCreated_date());
        holder.createdDate.setText(created);
       /* String deadlinetext =  MyUtils.convertLongToDataString(order.getDeadline());
        holder.deadline.setText(deadlinetext);*/
       final int id = order.getId();
       // Section_of_services section = provider.getSection(order.getSection());
        holder.section.setText(order.getSection()+"");

        curPerson = provider.getLoggedInPerson();
        if (order.getCustomerId() == curPerson.getId()) {
            isCreator = true;
        }

        holder.btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup =  new PopupMenu(context, v);
                popup.setOnMenuItemClickListener(Orders_adapter_frg.this);
                popup.inflate(R.menu.order_popup);
                popup_menu = popup.getMenu();
                if (popup_menu != null) {
                    popup_menu.findItem(R.id.order_popup_edit).setVisible(isCreator);
                    popup_menu.findItem(R.id.order_popup_delete).setVisible(isCreator);
                }
                popup.show();
            }
        });

        holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Orders_view_activity.class);
                intent.putExtra("orderIdFragment", id);
                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.order_popup_bookm:
                Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.order_popup_edit:
                return true;
            case R.id.order_popup_delete:
                Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.order_popup_complain:
                Toast.makeText(context, "отправлена", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }



    @Override
    public int getItemCount() {
        if(orders == null){return 0;}
        return orders.size();
    }
}
