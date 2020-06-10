package com.example.projectwnavigation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;

import fragments.Orders_view_activity;
import models.MyDataProvider;
import models.MyUtils;
import models.Order;
import models.Persons;
import models.Section_of_services;

public class Profile_orders_adapter extends RecyclerView.Adapter<Profile_orders_adapter.MyViewHolder> {
    Context context;
    Activity activity;
    MyDataProvider provider;
    ArrayList<Order> orders;

    Persons curPerson;
    private Menu popup_menu;
    boolean isCreator = false;

    public Profile_orders_adapter(Activity activity, Context context, ArrayList<Order> orders) {
        this.context = context;
        this.activity = activity;
        this.orders = orders;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, section, price, descr, deadline, createdDate;
        Button btn_popup_menu;
        RelativeLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.activity_profile_orders_adapter_title);
            section = itemView.findViewById(R.id.activity_profile_orders_adapter_section);
            price = itemView.findViewById(R.id.activity_profile_orders_adapter_price);
            descr = itemView.findViewById(R.id.activity_profile_orders_adapter_desc);
            deadline = itemView.findViewById(R.id.activity_profile_orders_adapter_deadline);
            createdDate = itemView.findViewById(R.id.activity_profile_orders_adapter_created);
            btn_popup_menu = itemView.findViewById(R.id.activity_profile_orders_adapter_btn_popup);

            adapter_layout = itemView.findViewById(R.id.activity_profile_orders_adapter_layout);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_profile_orders_adapter, parent, false);
        return new Profile_orders_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        provider = new MyDataProvider(context);

        final Order order = orders.get(position);
        holder.title.setText(order.getTitle());
        holder.descr.setText(order.getDescription());
        holder.price.setText("Бюджет: " + order.getPrice() + "");
        String created = MyUtils.convertLongToDataString(order.getCreated_date());
        holder.createdDate.setText(created);
        String deadlinetext = MyUtils.convertLongToDataString(order.getDeadline());
        holder.deadline.setText("До: " + deadlinetext);
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
                PopupMenu popup = new PopupMenu(context, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.order_popup_bookm:
                                Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.order_popup_edit:
                                showDialogUpdate(orders.get(position).getId());
                                return true;
                            case R.id.order_popup_delete:
                                provider.deleteOrder(order.getId());
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
    int sectionId = -1;

    private void showDialogUpdate(final int orderId) {
        final Dialog dialog = new Dialog(activity);

        dialog.setContentView(R.layout.dialog_order_update);
        dialog.setTitle("Редактировать заказ");

        final EditText title = dialog.findViewById(R.id.dialog_orders_update_title);
        final EditText price = dialog.findViewById(R.id.dialog_orders_update_price);
        final EditText descr = dialog.findViewById(R.id.dialog_orders_update_descr);
        final MaskEditText deadline = dialog.findViewById(R.id.dialog_orders_update_deadlinee);
        Button btnSave = dialog.findViewById(R.id.dialog_orders_update_btnOk);
        Spinner spinner = dialog.findViewById(R.id.dialog_orders_update_section);
        Button btnCancel = dialog.findViewById(R.id.dialog_orders_update_btnCancel);

        ArrayList<String> sectionList = provider.getSectionListInString();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, R.id.spinner_layout_textview, sectionList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                sectionId = provider.getSectionIdByTitle(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog.getWindow().setLayout(720, 1300);
        dialog.setCancelable(false);
        dialog.show();
        final Order order = provider.getOrder(orderId);
        title.setText(order.getTitle());
        price.setText(order.getPrice() + "");
        deadline.setText(MyUtils.convertLongToDataString(order.getDeadline()));
        descr.setText(order.getDescription());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long l = MyUtils.convertPntdStringToLong(deadline.getText().toString());
                order.setSection(sectionId);
                order.setDescription(descr.getText().toString().trim());
                order.setTitle(title.getText().toString().trim());
                order.setPrice(Double.valueOf(price.getText().toString()));
                order.setDeadline(l);

                provider.updateOrder(order);
                notifyDataSetChanged();
                Toast.makeText(context, "Изменения сохранены", Toast.LENGTH_LONG).show();
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
        if (orders == null) {
            return 0;
        }
        return orders.size();
    }

}
