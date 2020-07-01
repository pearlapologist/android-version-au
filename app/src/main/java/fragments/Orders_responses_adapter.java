package fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.ApiProvider;
import models.MyDataProvider;
import models.MyUtils;
import models.Persons;
import models.Response;

public class Orders_responses_adapter extends RecyclerView.Adapter<Orders_responses_adapter.MyViewHolder> {
    Context context;
    MyDataProvider provider;
    ApiProvider apiProvider;
    Orders_view_activity activity;
    ArrayList<Response> responses;

    Persons curPerson;
    private Menu popup_menu;

    public Orders_responses_adapter( Orders_view_activity activity, Context context, ArrayList<Response> responses) {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();
        curPerson = provider.getLoggedInPerson();

        Response response = responses.get(position);
        holder.text.setText(response.getText());
        holder.price.setText(response.getPrice() + "");
        String created = MyUtils.convertLongToDataString(response.getCreatedDate());
        holder.date.setText(created);
        final int responsId = response.getId();
        final Persons person = apiProvider.getPerson(provider.getPersonIdByResponseId(responsId)); // provider.getPerson(provider.getPersonIdByResponseId(responsId));
        String name = "";

        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.executors_default_image);
        if (person != null) {
            name = person.getName() + " " + person.getLastname();
            if (person.getPhoto() != null) {
                bmp = MyUtils.decodeByteToBitmap(person.getPhoto());
            }
        } else {
            name = "Пользователь удален или не найден";
            bmp = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.deleteduser);
        }

        holder.personName.setText(name);
        holder.image.setImageBitmap(bmp);

        final int personId = person.getId();
        final int executorId = provider.getExecutorIdByPersonId(personId);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (person.getId() == curPerson.getId()) {
                    Intent intent = new Intent(context, MyProfileActivity.class);
                    context.startActivity(intent);
                } else if (executorId != 0 && executorId != -1) {
                    Intent intent = new Intent(context, Executors_view_activity.class);
                    intent.putExtra("executorIdFragment", executorId);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, PersonProfileActivity.class);
                    intent.putExtra("orderview_PersonId", personId);
                    context.startActivity(intent);
                }
            }
        });

        holder.btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCreator = false;
                if (personId == curPerson.getId()) {
                    isCreator = true;
                }

                PopupMenu popup = new PopupMenu(context, v);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.order_popup_edit) {
                            showDialogUpdate(responsId);
                            return true;
                        } else if (item.getItemId() == R.id.order_popup_delete) {
                            showDialogDelete(position);
                            return true;
                        } else if (item.getItemId() == R.id.order_popup_complain) {
                            //TODO: доделать методы
                            Toast.makeText(context, "отправлена", Toast.LENGTH_SHORT).show();
                            return true;
                        }

                        return false;
                    }
                }

                );
                popup.inflate(R.menu.order_popup);
                popup_menu = popup.getMenu();
                if (popup_menu != null) {
                    popup_menu.findItem(R.id.order_popup_edit).setVisible(isCreator);
                    popup_menu.findItem(R.id.order_popup_delete).setVisible(isCreator);
                    popup_menu.findItem(R.id.order_popup_complain).setVisible(!isCreator);

                    popup_menu.findItem(R.id.order_popup_bookm_delete).setVisible(false);
                    popup_menu.findItem(R.id.order_popup_bookm).setVisible(false);
                }
                popup.show();
            }
        });

    }

    private void showDialogUpdate(final int id) {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_response_update);
        dialog.setTitle("Редактировать заказ");

        final EditText price = dialog.findViewById(R.id.dialog_response_update_price);
        final EditText descr = dialog.findViewById(R.id.dialog_response_update_descr);
        Button btnSave = dialog.findViewById(R.id.dialog_response_update_btn_save);
        Button btnCancel = dialog.findViewById(R.id.dialog_response_update_btn_cancel);

        dialog.setCancelable(true);
        dialog.show();
        final Response response = provider.getRespons(id);
        price.setText(response.getPrice() + "");
        descr.setText(response.getText());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setText(descr.getText().toString().trim());
                response.setPrice(Double.valueOf(price.getText().toString()));

                provider.updateRespons(response);
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

    private void showDialogDelete(final int position) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);

        dialogDelete.setTitle("Внимание!");
        dialogDelete.setMessage("Вы уверены, что хотите удалить свой отклик?");
        dialogDelete.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    provider.deleteRespons(responses.get(position).getId());
                    //notifyItemRemoved(position);
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("showDialogDelete", e.getMessage());
                }
            }
        });

        dialogDelete.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }


    @Override
    public int getItemCount() {
        if (responses == null) {
            return 0;
        }
        return responses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text, date, price, personName;
        ImageView image;
        LinearLayout adapter_layout;
        Button btn_popup_menu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            personName = itemView.findViewById(R.id.order_responses_adapter_pname);
            text = itemView.findViewById(R.id.order_responses_adapter_text);
            price = itemView.findViewById(R.id.order_responses_adapter_price);
            date = itemView.findViewById(R.id.order_responses_adapter_date);
            image = itemView.findViewById(R.id.order_responses_adapter_image);
            btn_popup_menu = itemView.findViewById(R.id.order_responses_adapter_btn_popup);

            adapter_layout = itemView.findViewById(R.id.order_responses_adapter_row_layout);
        }
    }
}
