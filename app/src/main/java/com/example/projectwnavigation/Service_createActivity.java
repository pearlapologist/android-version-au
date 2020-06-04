package com.example.projectwnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import models.DbHelper;
import models.MyDataProvider;
import models.Service;

public class Service_createActivity extends AppCompatActivity {
    EditText  title, price;
    Button btn;

    MyDataProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_create);

        title = findViewById(R.id.service_create_title);
        price = findViewById(R.id.service_create_price);
        btn = findViewById(R.id.service_create_btn_ok);
        provider = new MyDataProvider(this);

       // Intent intent = getIntent();
        Bundle extra = getIntent().getBundleExtra("extraServicesCreate");
        final ArrayList<Service> services = (ArrayList<Service>) extra.getSerializable("servicesCreate");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                services.add(new Service(title.getText().toString().trim(),
                        Double.parseDouble(price.getText().toString())));

                Bundle extra = new Bundle();
                extra.putSerializable("servicesCreate2", services);
                Intent i = new Intent(Service_createActivity.this, Profile_createFormActivity.class);
                i.putExtra("extraServicesCreate2", extra);
                setResult(RESULT_OK, i);

                Toast.makeText(Service_createActivity.this, "Added", Toast.LENGTH_SHORT).show();
                title.setText("");
                price.setText("");
               finish();
                   /* provider.addService(new Service(title.getText().toString().trim(),
                            Double.parseDouble(price.getText().toString())));*/

            }
        });
    }
}
