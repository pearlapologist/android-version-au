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

import models.MyDataProvider;
import models.MyUtils;
import models.Persons;
import models.Respons;

public class Orders_responses_adapter extends RecyclerView.Adapter<Orders_responses_adapter.MyViewHolder> {
    Context context;
    MyDataProvider provider;
    Activity activity;
    ArrayList<Respons> responses;


    public Orders_responses_adapter( Activity activity, Context context, ArrayList<Respons> responses) {
        this.context = context;
        this.activity = activity;
        this.responses = responses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_order_responses_adapter, parent, false);
        return new Orders_responses_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);

        Respons respons = responses.get(position);
        holder.text.setText(respons.getText());
        holder.price.setText(respons.getPrice() + "");
        String created = MyUtils.convertLongToDataString(respons.getCreatedDate());
        holder.date.setText(created);
        final int id = respons.getId();
        Persons person = provider.getPerson(provider.getPersonIdByResponseId(id));
        if(person.getPhoto()!= null){
        holder.image.setImageBitmap(MyUtils.decodeByteToBitmap(person.getPhoto()));
        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonProfileActivity.class);
                intent.putExtra("responseAdapter", id);

                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(responses == null){return 0;}
        return responses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text, date, price;
        ImageView image;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.order_responses_adapter_text);
            price = itemView.findViewById(R.id.order_responses_adapter_price);
            date = itemView.findViewById(R.id.order_responses_adapter_date);
            image = itemView.findViewById(R.id.order_responses_adapter_image);

            adapter_layout = itemView.findViewById(R.id.order_responses_adapter_row_layout);
        }
    }
}
