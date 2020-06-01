package com.example.projectwnavigation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import models.DbHelper;
import models.MyDataProvider;
import models.Service;

public class Service_update_activity extends AppCompatActivity {
    EditText id, title, price;
    MyDataProvider provider;
    Button btnOk, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_update);

        id = findViewById(R.id.service_update_id);
        title = findViewById(R.id.service_update_title);
        price = findViewById(R.id.service_update_price);
        btnOk = findViewById(R.id.service_update_btnOk);
        delete = findViewById(R.id.service_update_btnDelete);

        provider = new MyDataProvider(this);

        getAndSetExecutorIntentData();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Service service = new Service();
                service.setId(getIntent().getIntExtra("serviceId", Integer.parseInt(id.getText().toString())));
                service.setTitle(title.getText().toString());
                service.setPrice(Double.parseDouble(price.getText().toString()));
                provider.updateService(service);
                Toast.makeText(Service_update_activity.this, "Updated", Toast.LENGTH_SHORT).show();

                id.setText("");
                title.setText("");
                price.setText("");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Service_update_activity.this);
                builder.setMessage("Are you sure you want to delete this service?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        provider.deleteService(getIntent().getIntExtra("serviceId", Integer.parseInt(id.getText().toString())));
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
        });
    }

    private void getAndSetExecutorIntentData() {
        if (getIntent().hasExtra("serviceId")) {
            final int gettedId = getIntent().getIntExtra("serviceId", 42);
            Service srv = provider.getService(gettedId);
            id.setText(gettedId + "");
            title.setText(srv.getTitle());
            price.setText(srv.getPrice() + "");
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }

}
