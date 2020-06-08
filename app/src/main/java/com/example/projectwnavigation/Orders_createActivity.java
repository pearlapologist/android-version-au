package com.example.projectwnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.santalu.maskedittext.MaskEditText;

import models.MyUtils;
import models.MyDataProvider;
import models.Order;

public class Orders_createActivity extends AppCompatActivity {
    EditText title, price, descr;
    MaskEditText  deadline;
    Button add;
    MyDataProvider provider;
    Spinner mSpinner;
    String[] mOptions = {"Стройка", "Здоровье", "Авто", "Рукоделие"};
    int sectionId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_create);

        title = findViewById(R.id.orders_create_title);
        price = findViewById(R.id.orders_create_price);
        descr = findViewById(R.id.orders_create_desciptn);
        deadline = findViewById(R.id.orders_create_deadline);
        add = findViewById(R.id.orders_create_btn_ok);
        mSpinner = findViewById(R.id.orders_create_section);

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

        add.setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
              Long l = MyUtils.convertPntdStringToLong(deadline.getText().toString());
                Long curr = MyUtils.getCurentDateInLong();
                provider.addOrder(new Order(title.getText().toString().trim(),
                        provider.getLoggedInPerson().getId(),
                        sectionId,
                        Double.valueOf(price.getText().toString()),
                        descr.getText().toString(),
                       l,
                        curr));
            } catch (Exception e) {
                Log.e("Update error", e.getMessage());
            }
            title.setText("");
            price.setText("");
            deadline.setText("");
            descr.setText("");
            Toast.makeText(Orders_createActivity.this, "Added", Toast.LENGTH_SHORT).show();
        }
    };


}
