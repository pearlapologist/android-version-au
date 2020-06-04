package com.example.projectwnavigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import models.DataConverter;
import models.MyDataProvider;
import models.Persons;

public class PersonsListAdapter extends RecyclerView.Adapter<PersonsListAdapter.MyViewHolder> {

    private Context context;
    Activity activity;

    MyDataProvider provider;
    private ArrayList<Persons> personsList;

    public PersonsListAdapter(Activity activity, Context context, ArrayList<Persons> personsList) {
        this.context = context;
        this.activity = activity;
        this.personsList = personsList;
    }


    @Override
    public int getItemCount() {
        if (personsList == null) {
            return 0;
        }
        return personsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtLastname, txtPasswd, txtCreatedDate, txtRating, txtNumber, txtId;
        LinearLayout adapter_layout;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.list_txt_name);
            txtLastname = itemView.findViewById(R.id.list_txt_lastname);
            txtCreatedDate = itemView.findViewById(R.id.list_txt_createdDate);
            txtPasswd = itemView.findViewById(R.id.list_txt_passwd);
            txtRating = itemView.findViewById(R.id.list_txt_rating);
            txtNumber = itemView.findViewById(R.id.list_txt_number);
            txtId = itemView.findViewById(R.id.list_txt_id);
            imageView = itemView.findViewById(R.id.list_imgIcon);

            adapter_layout = itemView.findViewById(R.id.persons_adapter_row_layout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_persons_list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        provider = new MyDataProvider(context);

        Persons person = personsList.get(position);
        String id = (person.getId()) + "";
        holder.txtId.setText(id);
        holder.txtName.setText(person.getName());
        holder.txtLastname.setText(person.getLastname());
        holder.txtPasswd.setText(person.getPasswd());
        String create = DataConverter.convertLongToDataString(person.getCreatedDate());
        holder.txtCreatedDate.setText(create);
        String rating = (person.getRating() + "");
        holder.txtRating.setText(rating);

        holder.txtNumber.setText(person.getNumber());
        if (person.getPhoto() != null) {
            holder.imageView.setImageBitmap(provider.decodeByteToBitmap(person.getPhoto()));
        }
        holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            showDialogUpdate(Integer.parseInt(holder.txtId.getText().toString()));
                        }
                        if (which == 1) {

                            showDialogDelete(Integer.parseInt(holder.txtId.getText().toString()));
                        }
                    }
                });
                dialog.show();
            }
        });
    }


    private void showDialogDelete(final int idRecord) {
        final androidx.appcompat.app.AlertDialog.Builder dialogDelete = new androidx.appcompat.app.AlertDialog.Builder(context);
        dialogDelete.setTitle("Warning");
        dialogDelete.setMessage("Are you sure to delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    provider.deletePerson(idRecord);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Delete successfull", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e("Delete error", e.getMessage());
                }
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void showDialogUpdate(final int position) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.person_update_dailog);
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
        String date = DataConverter.convertLongToDataString(p.getCreatedDate());
        edtCreatedDate.setText(date + "");
        if (p.getPhoto() != null) {
            imageViewIcon.setImageBitmap(provider.decodeByteToBitmap(p.getPhoto()));
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Long createdChange = DataConverter.convertDataToLong(edtCreatedDate.getText().toString());
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

}
