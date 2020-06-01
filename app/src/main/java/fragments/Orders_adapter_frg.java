package fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.DataConverter;
import models.MyDataProvider;
import models.Order;
import models.Section_of_services;

public class Orders_adapter_frg extends RecyclerView.Adapter<Orders_adapter_frg.MyViewHolder> {
    private Context context;
    Activity activity;
    MyDataProvider provider;
    ArrayList<Order> orders;

    public  Orders_adapter_frg(Activity activity, Context context, ArrayList<Order> orders) {
        this.context = context;
        this.activity = activity;
        this.orders = orders;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView  title, section, price, descr, deadline, createdDate;
        private ImageView image;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.orders_adapter_fragment_image);
            title = itemView.findViewById(R.id.orders_adapter_fragment_title);
            section = itemView.findViewById(R.id.orders_adapter_fragment_section);
            // sectionId_txt = itemView.findViewById(R.id.executors_adapter_);
            price = itemView.findViewById(R.id.orders_adapter_fragment_price);
            descr = itemView.findViewById(R.id.orders_adapter_fragment_desc);
            deadline = itemView.findViewById(R.id.orders_adapter_fragment_deadline);
            createdDate= itemView.findViewById(R.id.orders_adapter_fragment_created);

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
        String created =  DataConverter.convertLongToDataString(order.getCreated_date());
        holder.createdDate.setText(created);
        String deadlinetext =  DataConverter.convertLongToDataString(order.getDeadline());
        holder.deadline.setText(deadlinetext);
       final int id = order.getId();
        Section_of_services section = provider.getSection(order.getSection());
        holder.section.setText(section.getTitle());

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
    public int getItemCount() {
        return orders.size();
    }
}
