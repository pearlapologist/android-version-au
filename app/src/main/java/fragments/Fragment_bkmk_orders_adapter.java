package fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.ApiProvider;
import models.Bookmarks;
import models.MyDataProvider;
import models.MyUtils;
import models.Order;
import models.Persons;
import models.Section_of_services;

public class Fragment_bkmk_orders_adapter extends RecyclerView.Adapter<Fragment_bkmk_orders_adapter.MyViewHolder> {
    private Context context;
    MyDataProvider provider;
    ApiProvider apiProvider;
    ArrayList<Bookmarks> orders;
    Fragment_bkmk_orders fragment_bkmk_orders;

    Persons curPerson;


    public Fragment_bkmk_orders_adapter(Fragment_bkmk_orders fragment_bkmk_orders, Context context, ArrayList<Bookmarks> orders) {
        this.context = context;
        this.fragment_bkmk_orders = fragment_bkmk_orders;
        this.orders = orders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_bkmk_orders_adapter_row, parent, false);
        return new Fragment_bkmk_orders_adapter.MyViewHolder(view, fragment_bkmk_orders);

    }

    Order order = null;

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();
        curPerson = provider.getLoggedInPerson();

        Bookmarks bookm = orders.get(position);
        final int orderId = bookm.getOrderId();


        try {
            order = apiProvider.getOrder(orderId); // provider.getOrder(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.title.setText(order.getTitle());
        holder.descr.setText(order.getDescription());
        holder.price.setText(order.getPrice() + "");
        String created = MyUtils.convertLongToDataString(order.getCreated_date());
        holder.createdDate.setText(created);
        String deadlinetext = MyUtils.convertLongToDataString(order.getDeadline());
        holder.deadline.setText("" + deadlinetext);


        Section_of_services section = null;
        try {
            section = apiProvider.getSection(order.getSection());// provider.getSection(order.getSection());
            holder.section.setText(section.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (fragment_bkmk_orders.contextModeEnable) {
            holder.chbox.setVisibility(View.VISIBLE);
        } else {
            holder.chbox.setVisibility(View.GONE);
        }

        holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Orders_view_activity.class);
                intent.putExtra("orderIdFragment", orderId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_bookm_delete:
                                showDialogDelete(order.getId());
                                return true;
                            case R.id.menu_bookm_complain:
                                //TODO: доделать методы
                                Toast.makeText(context, "отправлена", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }

                    }
                });
                popup.inflate(R.menu.menu_bookm_popup);
                popup.show();
            }
        });


    }

    Order deleteOrder = null;

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
                    deleteOrder = apiProvider.getOrder(orderId); // provider.getOrder(orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                orders.remove(deleteOrder);
                DeleteOrderFromBookmTask task = new DeleteOrderFromBookmTask();
                task.execute(curPerson.getId(), orderId);
                //provider.deleteOrderFromMyBookmarks(orderId);
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

    @Override
    public int getItemCount() {
        if (orders == null) {
            return 0;
        }
        return orders.size();
    }

    public void removeItem(ArrayList<Bookmarks> selectionList) {
        for (int i = 0; i < selectionList.size(); i++) {
            orders.remove(selectionList.get(i));
            DeleteOrderFromBookmTask task = new DeleteOrderFromBookmTask();
            task.execute(curPerson.getId(), selectionList.get(i).getOrderId());
            //provider.deleteOrderFromMyBookmarks(selectionList.get(i).getOrderId());
            notifyDataSetChanged();
        }
        Toast.makeText(context, "Успешно удалено из закладок", Toast.LENGTH_SHORT).show();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, section, price, descr, deadline, createdDate;
        View view;
        CheckBox chbox;
        Button btn_popup_menu;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView, Fragment_bkmk_orders fragment_bkmk_orders) {
            super(itemView);

            view = itemView;
            title = itemView.findViewById(R.id.frg_bkmk_order_adapter_title);
            section = itemView.findViewById(R.id.frg_bkmk_order_adapter_section);
            price = itemView.findViewById(R.id.frg_bkmk_order_adapter_price);
            descr = itemView.findViewById(R.id.frg_bkmk_order_adapter_desc);
            deadline = itemView.findViewById(R.id.frg_bkmk_order_adapter_deadline);
            createdDate = itemView.findViewById(R.id.frg_bkmk_order_adapter_created);
            btn_popup_menu = itemView.findViewById(R.id.frg_bkmk_order_adapter_btn_popup);
            chbox = itemView.findViewById(R.id.frg_bkmk_order_adapter_checkbox);

            chbox.setOnClickListener(this);
            view.setOnLongClickListener(fragment_bkmk_orders);

            adapter_layout = itemView.findViewById(R.id.frg_bkmk_order_adapter_layout);

        }

        @Override
        public void onClick(View v) {
            fragment_bkmk_orders.setSelection(v, getAdapterPosition());
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
