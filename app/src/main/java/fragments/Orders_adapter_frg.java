package fragments;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.Navigation_activity;
import com.example.projectwnavigation.R;
import com.google.android.material.textfield.TextInputLayout;
import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;

import models.ApiProvider;
import models.Bookmarks;
import models.CustomArrayAdapter;
import models.Executor;
import models.MyUtils;
import models.MyDataProvider;
import models.Order;
import models.Persons;
import models.Section_of_services;

public class Orders_adapter_frg extends RecyclerView.Adapter<Orders_adapter_frg.MyViewHolder> {
    Context context;
    Activity activity;
    MyDataProvider provider;
    ApiProvider apiProvider;
    ArrayList<Order> orders;

    Persons curPerson;
    private Menu popup_menu;

    public Orders_adapter_frg(Activity activity, Context context, ArrayList<Order> orders) {
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

            title = itemView.findViewById(R.id.orders_adapter_fragment_title);
            section = itemView.findViewById(R.id.orders_adapter_fragment_section);
            price = itemView.findViewById(R.id.orders_adapter_fragment_price);
            descr = itemView.findViewById(R.id.orders_adapter_fragment_desc);
            deadline = itemView.findViewById(R.id.orders_adapter_fragment_deadline);
            createdDate = itemView.findViewById(R.id.orders_adapter_fragment_created);
            btn_popup_menu = itemView.findViewById(R.id.orders_adapter_fragment_btn_popup);

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
    public void onBindViewHolder(@NonNull Orders_adapter_frg.MyViewHolder holder, final int position) {
        provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();
        curPerson = provider.getLoggedInPerson();
        final Order order = orders.get(position);

        holder.title.setText(order.getTitle());
        holder.descr.setText(order.getDescription());
        holder.price.setText(order.getPrice() + "");
        String created = MyUtils.convertLongToDataString(order.getCreated_date());
        holder.createdDate.setText(created);
        Long deadlinelong = order.getDeadline();
        if (deadlinelong != 0) {
            holder.deadline.setText(MyUtils.convertLongToDataString(deadlinelong));
        }else{
            holder.deadline.setText("Не ограничено");
        }
        final int id = order.getId();
        Section_of_services section = null;
        try {
            section = apiProvider.getSection(order.getSection());    // provider.getSection(order.getSection());
            holder.section.setText(section.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCreator = false;
                if (order.getCustomerId() == curPerson.getId()) {
                    isCreator = true;
                }
                boolean exists = false;
                Bookmarks b = null; // provider.getBookmarkByOrderId(order.getId());
                try {
                    b = apiProvider.getPersonBookmarkByOrderId(curPerson.getId(), order.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (b != null) {
                    exists = true;
                }
                PopupMenu popup = new PopupMenu(context, v);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.order_popup_bookm) {
                            PutOrderInBookmTask putOrder = new PutOrderInBookmTask();
                            putOrder.execute(curPerson.getId(), order.getId());
                            // provider.putOrderInMyBookmarks(order.getId());
                            Toast.makeText(context, "Заказ добавлен в ваши закладки", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (item.getItemId() == R.id.order_popup_edit) {
                            showDialogUpdate(order.getId());
                            return true;
                        } else if (item.getItemId() == R.id.order_popup_delete) {
                            showDialogDelete(order.getId());
                            return true;
                        } else if (item.getItemId() == R.id.order_popup_bookm_delete) {
                            showDialogDeleteFromBookm(order.getId());
                            return true;
                        } else if (item.getItemId() == R.id.order_popup_complain) {
                            //TODO: доделать методы
                            Toast.makeText(context, "отправлена", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                });
                popup.inflate(R.menu.order_popup);
                popup_menu = popup.getMenu();
                if (popup_menu != null) {
                    popup_menu.findItem(R.id.order_popup_edit).setVisible(isCreator);
                    popup_menu.findItem(R.id.order_popup_delete).setVisible(isCreator);
                    popup_menu.findItem(R.id.order_popup_complain).setVisible(!isCreator);
                    popup_menu.findItem(R.id.order_popup_bookm_delete).setVisible(exists);
                    if ((exists == true) || isCreator) {
                        popup_menu.findItem(R.id.order_popup_bookm).setVisible(false);
                    }
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

    private void showDialogDeleteFromBookm(final int orderId) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setContentView(R.layout.dialog_answer_delete);
        Button btnSave = dialog.findViewById(R.id.dialog_answer_delete_btn_save);
        Button btnCancel = dialog.findViewById(R.id.dialog_answer_delete_btn_cancel);
        dialog.setCancelable(true);
        dialog.show();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteOrderFromBookmTask task = new DeleteOrderFromBookmTask();
                task.execute(curPerson.getId(), orderId);
                // provider.deleteOrderFromMyBookmarks(orderId);
                notifyDataSetChanged();
                Toast.makeText(context, "Заказ удален из ваших закладок", Toast.LENGTH_SHORT).show();
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

    int sectionId = -1;
    Order order = null;

    private void showDialogUpdate(final int orderId) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_order_update);
        dialog.setTitle("Редактировать заказ");

        final TextInputLayout title = dialog.findViewById(R.id.dialog_orders_update_title);
        final TextInputLayout price = dialog.findViewById(R.id.dialog_orders_update_price);
        final TextInputLayout descr = dialog.findViewById(R.id.dialog_orders_update_descr);
        final MaskEditText deadline = dialog.findViewById(R.id.dialog_orders_update_deadlinee);
        Button btnSave = dialog.findViewById(R.id.dialog_orders_update_btnOk);
        Spinner spinner = dialog.findViewById(R.id.dialog_orders_update_section);
        Button btnCancel = dialog.findViewById(R.id.dialog_orders_update_btnCancel);

        CustomArrayAdapter arrayAdapter = new CustomArrayAdapter(context,
                R.layout.spinner_layout, R.id.spinner_layout_textview, context.getResources().getStringArray(R.array.sections), 0);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sectionId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        dialog.setCancelable(true);
        dialog.show();
        try {
            order = apiProvider.getOrder(orderId);
            title.getEditText().setText(order.getTitle());
            price.getEditText().setText(order.getPrice() + "");
            deadline.setText(MyUtils.convertLongToDataString(order.getDeadline()));
            descr.getEditText().setText(order.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = descr.getEditText().getText().toString().trim();
                if (desc.isEmpty() || desc.equals(" ")) {
                    descr.setError("Заполните поле");
                    return;
                } else {
                    descr.setError(null);
                    descr.setErrorEnabled(false);

                    String txttitle = title.getEditText().getText().toString().trim();
                    if (txttitle.isEmpty() || txttitle.equals(" ")) {
                        title.setError("Заполните поле");
                        return;
                    } else {
                        title.setError(null);
                        title.setErrorEnabled(false);

                        String txtprice = price.getEditText().getText().toString().trim();
                        if (txtprice.isEmpty() || txtprice.equals(" ")) {
                            price.setError("Заполните поле");
                            return;
                        } else {
                            price.setError(null);
                            price.setErrorEnabled(false);

                            Long l = MyUtils.convertPntdStringToLong(deadline.getText().toString());
                            order.setSection(sectionId);
                            order.setDescription(desc);
                            order.setTitle(txttitle);
                            order.setPrice(Double.valueOf(txtprice));
                            order.setDeadline(l);

                            UpdateOrderTask task = new UpdateOrderTask();
                            task.execute(order);  // provider.updateOrder(order);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Изменения сохранены", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    private void showDialogDelete(final int orderId) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        dialog.setContentView(R.layout.dialog_answer_delete);
        Button btnSave = dialog.findViewById(R.id.dialog_answer_delete_btn_save);
        Button btnCancel = dialog.findViewById(R.id.dialog_answer_delete_btn_cancel);

        dialog.setCancelable(true);
        dialog.show();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    orders.remove(apiProvider.getOrder(orderId)); //provider.getOrder(orderId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DeleteOrderTask task = new DeleteOrderTask();
                task.execute(orderId);
                //provider.deleteOrder(orderId);
                notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(context, "Заказ успешно удален", Toast.LENGTH_SHORT).show();
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


    private class UpdateOrderTask extends AsyncTask<Order, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Order... params) {
            try {
                apiProvider.updateOrder(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
        }
    }

    private class DeleteOrderTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            try {
                apiProvider.deleteOrder(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
        }
    }

    private class PutOrderInBookmTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            try {
                apiProvider.putOrderInPersonBookmarks(params[0], params[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
        }
    }

    private class DeleteOrderFromBookmTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            try {
                apiProvider.deleteOrderFromPersonBookmarks(params[0], params[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
        }
    }

}
