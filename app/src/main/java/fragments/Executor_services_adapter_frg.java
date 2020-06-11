package fragments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.MyDataProvider;
import models.Service;

public class Executor_services_adapter_frg extends RecyclerView.Adapter<Executor_services_adapter_frg.MyViewHolder> {
    private Context context;
    Activity activity;
    MyDataProvider provider;
    ArrayList<Service> services;


    Executor_services_adapter_frg(Activity activity, Context context, ArrayList<Service> services) {
        this.context = context;
        this.activity = activity;
        this.services = services;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_executor_services_adapter_row, parent, false);
        return new Executor_services_adapter_frg.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);
        Service service = services.get(position);

        String title = "ничего";
        Double price = 0.0;

        try {
            title = service.getTitle();
            price = service.getPrice();
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.title.setText(title);
        holder.price.setText(" -"+ price);
    }

    @Override
    public int getItemCount() {
        if(services == null){
            return 0;
        }
        return services.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, price;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.executor_services_adapter_title);
            price = itemView.findViewById(R.id.executor_services_adapter_frg_price);
            adapter_layout = itemView.findViewById(R.id.executor_services_adapter_frg_layout);
        }
    }
}
