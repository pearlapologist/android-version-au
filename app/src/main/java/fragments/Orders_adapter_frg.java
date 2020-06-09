package fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import models.Section_of_services;

public class Orders_adapter_frg extends RecyclerView.Adapter<Orders_adapter_frg.MyViewHolder>{
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
        Button btn_popup_menu;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.orders_adapter_fragment_title);
            section = itemView.findViewById(R.id.orders_adapter_fragment_section);
            price = itemView.findViewById(R.id.orders_adapter_fragment_price);
            descr = itemView.findViewById(R.id.orders_adapter_fragment_desc);
            deadline = itemView.findViewById(R.id.orders_adapter_fragment_deadline);
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

        final Order order = orders.get(position);
        holder.title.setText(order.getTitle());
        holder.descr.setText(order.getDescription());
        holder.price.setText("Бюджет: "+order.getPrice() +"");
        String created =  MyUtils.convertLongToDataString(order.getCreated_date());
        holder.createdDate.setText(created);
      String deadlinetext =  MyUtils.convertLongToDataString(order.getDeadline());
        holder.deadline.setText("До: "+deadlinetext);
       final int id = order.getId();
        Section_of_services section = provider.getSection(order.getSection());
        holder.section.setText(section.getTitle());

        curPerson = provider.getLoggedInPerson();
        if (order.getCustomerId() == curPerson.getId()) {
            isCreator = true;
        }

        holder.btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup =  new PopupMenu(context, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.order_popup_bookm:
                                    Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.order_popup_edit:
                                    return true;
                                case R.id.order_popup_delete:
                                   provider.deleteOrder(order.getId());
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


    private void showDialogUpdate(final int position) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_order_update);
        dialog.setTitle("Update");

        ImageView imageViewIcon = dialog.findViewById(R.id.update_imageview);
        final TextView edtId = dialog.findViewById(R.id.upd_edtId);
        final EditText edtName = dialog.findViewById(R.id.upd_edtName);
        final EditText edtLastname = dialog.findViewById(R.id.upd_edtLastName);
        final EditText edtPasswd = dialog.findViewById(R.id.upd_edtPasswd);
        final EditText edtRating = dialog.findViewById(R.id.upd_edtRating);
        final EditText edtNumber = dialog.findViewById(R.id.upd_edtNumber);
        final EditText edtCreatedDate = dialog.findViewById(R.id.upd_edtCreatedDate);
        Button btnUpdate = dialog.findViewById(R.id.upd_btn);
        Button btnCancel = dialog.findViewById(R.id.upd_cancel);

        dialog.getWindow().setLayout(720, 1280);
        dialog.setCancelable(false);
        dialog.show();

        Persons p = provider.getPerson(position);
        edtId.setText(p.getId() + "");
        edtName.setText(p.getName());
        edtLastname.setText(p.getLastname());
        edtPasswd.setText(p.getPasswd());

        edtNumber.setText(p.getNumber());
        edtRating.setText(p.getRating() + "");
        String date = MyUtils.convertLongToDataString(p.getCreatedDate());
        edtCreatedDate.setText(date + "");
        if (p.getPhoto() != null) {
            imageViewIcon.setImageBitmap(MyUtils.decodeByteToBitmap(p.getPhoto()));
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Long createdChange = MyUtils.convertDataToLong(edtCreatedDate.getText().toString());
                    Persons p = new Persons(position, edtName.getText().toString().trim(),
                            edtLastname.getText().toString().trim(),
                            edtPasswd.getText().toString().trim(),
                            edtNumber.getText().toString(),
                            Integer.parseInt(edtRating.getText().toString().trim()),
                            createdChange);
                    provider.updatePerson(p);
                } catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                notifyDataSetChanged();
                Toast.makeText(context, "Update successfull", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    @Override
    public int getItemCount() {
        if(orders == null){return 0;}
        return orders.size();
    }
}
