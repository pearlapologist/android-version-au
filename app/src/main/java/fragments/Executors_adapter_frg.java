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

import models.Executor;
import models.MyDataProvider;
import models.Persons;

public class Executors_adapter_frg extends RecyclerView.Adapter<Executors_adapter_frg.MyViewHolder> {
    private Context context;
    Activity activity;
    MyDataProvider provider;
    ArrayList<Executor> executors;

    Executors_adapter_frg(Activity activity, Context context, ArrayList<Executor> executors) {
        this.context = context;
        this.activity = activity;
        this.executors = executors;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, spcltn_txt;
        ImageView photo;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.executors_adapter_frg_name);
            spcltn_txt = itemView.findViewById(R.id.executors_adapter_frg_desc);
            photo = itemView.findViewById(R.id.executors_adapter_frg_photo);

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


        Executor executor = executors.get(position);

        holder.spcltn_txt.setText(executor.getSpecialztn());
        String nametxt = "";
        Persons p = provider.getPerson(executor.getPersonId());
        if (p!=null ) {
            nametxt = p.getName();
           holder.photo.setImageBitmap(provider.decodeByteToBitmap(p.getPhoto()));
        }
        else{
            holder.photo.setImageResource(R.drawable.ic_add);
        }

        holder.name.setText(nametxt);

        final int id = executor.getId();
        holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Executors_view_activity.class);
                intent.putExtra("executorIdFragment", id);

                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(executors == null){
            return 0;
        }
        return executors.size();
    }

}
