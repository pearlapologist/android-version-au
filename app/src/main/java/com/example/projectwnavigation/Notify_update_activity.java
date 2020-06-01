package com.example.projectwnavigation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import models.DataConverter;
import models.DbHelper;
import models.MyDataProvider;
import models.Notify;
import models.Persons;

public class Notify_update_activity extends AppCompatActivity {
    EditText text, personId, id;
    TextView createdDate, name;
    Button add;
    Button delete;
    MyDataProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_update);


        text = findViewById(R.id.notify_update_text);
        personId = findViewById(R.id.notify_update_personId);
        createdDate = findViewById(R.id.notify_update_created);
        name = findViewById(R.id.notify_update_personName);
        add = findViewById(R.id.notify_update_btnOk);
        delete = findViewById(R.id.notify_update_btnDelete);
        id = findViewById(R.id.notify_update_nId);

        provider = new MyDataProvider(this);

        getAndSetNotifyIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(text.getText() + "");
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long create = DataConverter.convertDataToLongWithRawString(createdDate.getText().toString());

                Notify notify = new Notify(getIntent().getIntExtra("notifyId", Integer.parseInt(id.getText().toString())),
                        Integer.parseInt(personId.getText().toString()),
                        text.getText().toString(),
                        create);
                provider.updateNotify(notify);
                Toast.makeText(Notify_update_activity.this, "Updated", Toast.LENGTH_SHORT).show();

                text.setText("");
                id.setText("");
                name.setText("");
                personId.setText("");
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

    void getAndSetNotifyIntentData() {
        if (getIntent().hasExtra("notifyId")) {
            final int gettedId = getIntent().getIntExtra("notifyId", 42);
            Notify noty = provider.getNotify(gettedId);

            text.setText(noty.getText());
            personId.setText(noty.getPersonid() + "");
            Persons p = provider.getPerson(Integer.parseInt(personId.getText().toString()));
            if (p != null) {
                name.setText(p.getName());
            }else{
                Toast.makeText(this, "no such person!", Toast.LENGTH_SHORT).show();
            }
            String s = DataConverter.convertLongToDataString(noty.getCreatedDate());
            createdDate.setText(s);
            id.setText(noty.getId() + "");
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Вы уверены,что хотите удалить?" );
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                provider.deleteNotify(getIntent().getIntExtra("notifyId", Integer.parseInt(id.getText().toString())));
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
