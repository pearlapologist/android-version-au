package fragments;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.Executor;
import models.MyDataProvider;
import models.MyUtils;
import models.Persons;

public class Fragment_bkmk_specials_adapter extends RecyclerView.Adapter<Fragment_bkmk_specials_adapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    MyDataProvider provider;
    ArrayList<Executor> specials;
    Fragment_bkmk_specials fragment_bkmk_specials;
    Persons curPerson;

    public Fragment_bkmk_specials_adapter( Fragment_bkmk_specials fragment_bkmk_specials, Context context, ArrayList<Executor> specials) {
        this.context = context;
        this.fragment_bkmk_specials = fragment_bkmk_specials;
        this.specials = specials;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_bkmk_specials_adapter_row, parent, false);
        return new Fragment_bkmk_specials_adapter.MyViewHolder(view,fragment_bkmk_specials);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);

        final Executor executor = specials.get(position);

        holder.spcltn_txt.setText(executor.getSpecialztn());
        Persons p = provider.getPerson(executor.getPersonId());
        if (p.getPhoto() == null) {
            holder.photo.setImageResource(R.drawable.executors_default_image);
        } else {
            holder.photo.setImageBitmap(MyUtils.decodeByteToBitmap(p.getPhoto()));
        }
        holder.name.setText(p.getName());
        holder.rating.setText(p.getRating() + "");

        if (!fragment_bkmk_specials.contextModeEnable) {
            holder.chbox.setVisibility(View.GONE);
        } else {
            holder.chbox.setVisibility(View.VISIBLE);
            //holder.chbox.setChecked(false);
        }

        holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id = executor.getId();
                if (id == provider.getExecutorIdByPersonId(provider.getLoggedInPerson().getId())) {
                    Intent intent = new Intent(context, MyProfileActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, Executors_view_activity.class);
                    intent.putExtra("executorIdFragment", id);
                    context.startActivity(intent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (specials == null) {
            return 0;
        }
        return specials.size();
    }

    public void removeItem(ArrayList<Executor> selectionList) {
        for (int i = 0; i < selectionList.size(); i++) {
            specials.remove(selectionList.get(i));
            notifyDataSetChanged();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, spcltn_txt, rating;
        ImageView photo;
        View view;
        CheckBox chbox;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView, Fragment_bkmk_specials fragment_bkmk_specials) {
            super(itemView);

            view = itemView;
            name = itemView.findViewById(R.id.frg_bkmk_specials_adapter_name);
            rating = itemView.findViewById(R.id.frg_bkmk_specials_adapter_rating);
            spcltn_txt = itemView.findViewById(R.id.frg_bkmk_specials_adapter_spec);
            chbox = itemView.findViewById(R.id.frg_bkmk_specials_adapter_chbox);
            photo = itemView.findViewById(R.id.frg_bkmk_specials_adapter_image);

            chbox.setOnClickListener(this);
            view.setOnLongClickListener(fragment_bkmk_specials);

            adapter_layout = itemView.findViewById(R.id.frg_bkmk_specials_adapter_layout);

        }

        @Override
        public void onClick(View v) {
            fragment_bkmk_specials.setSelection(v, getAdapterPosition());
        }

    }
}
