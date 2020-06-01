package com.example.projectwnavigation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.santalu.maskedittext.MaskEditText;

import models.DataConverter;
import models.DbHelper;
import models.MyDataProvider;
import models.Order;

public class Orders_update_activity extends AppCompatActivity {
    EditText title, price,  descr, id;
    MaskEditText deadline;
    TextView createdDate;
    Button add, delete;
    MyDataProvider provider;
    Spinner mSpinner;
    String[] mOptions = {"Стройка", "Здоровье", "Авто", "Рукоделие"};
    int sectionId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_update);


        title = findViewById(R.id.orders_update_title);
        price = findViewById(R.id.orders_update_price);
        descr = findViewById(R.id.orders_update_descr);
        createdDate = findViewById(R.id.orders_update_created);
        deadline = findViewById(R.id.orders_update_deadline);
        add = findViewById(R.id.orders_update_btnOk);
        mSpinner = findViewById(R.id.orders_update_section);
        id = findViewById(R.id.orders_update_id);
        delete = findViewById(R.id.orders_update_btnDelete);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, mOptions);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);

        provider = new MyDataProvider(this);


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int a = parent.getId();
                switch (position) {
                    case 1:
                        sectionId = 1;
                    case 2:
                        sectionId = 2;
                    case 3:
                        sectionId = 3;
                    case 4:
                        sectionId = 4;
                    case 5:
                        sectionId = 5;
                    case 6:
                        sectionId = 6;
                    case 7:
                        sectionId = 7;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getAndSetOrderIntentData();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order ord = new Order();
                ord.setId(getIntent().getIntExtra( "orderId", Integer.parseInt(id.getText().toString())));
                ord.setTitle(title.getText().toString());
                ord.setSection(sectionId);
                ord.setPrice(Double.parseDouble(price.getText().toString()));
                ord.setDescription(  descr.getText().toString());
                Long l = DataConverter.convertPntdStringToLong(deadline.getText().toString());
                ord.setDeadline(l);
                provider.updateOrder(ord);
                Toast.makeText(Orders_update_activity.this, "Updated", Toast.LENGTH_SHORT).show();

                title.setText("");
                id.setText("");
                price.setText("");
                mSpinner.setSelection(0);
                descr.setText("");
                deadline.setText("");
                createdDate.setText("");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    void getAndSetOrderIntentData() {
        if (getIntent().hasExtra("orderId")) {
            final int gettedId = getIntent().getIntExtra("orderId", -1);
            Order cur = provider.getOrder(gettedId);
            mSpinner.setSelection(cur.getSection());

            title.setText(cur.getTitle());
            price.setText(cur.getPrice() + "");
            descr.setText(cur.getDescription());
            String s = DataConverter.convertLongToDataString(cur.getDeadline());
            deadline.setText(s);
            String d = DataConverter.convertLongToDataString(cur.getCreated_date());
            createdDate.setText(d);
            id.setText(cur.getId() + "");
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }


    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this order?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                provider.deleteOrder(getIntent().getIntExtra("orderId", Integer.parseInt(id.getText().toString())));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
