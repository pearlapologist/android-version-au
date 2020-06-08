package com.example.projectwnavigation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import models.Bookmarks;
import models.MyDataProvider;

public class Bookmarks_create_activity extends AppCompatActivity {
    EditText personId, executorId;
    Button btn;

    MyDataProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmr_create);


        personId = findViewById(R.id.bookm_creat_person_id);
        executorId = findViewById(R.id.bookm_creat_executor_id);
        btn = findViewById(R.id.bookm_creat_btnOk);
        provider = new MyDataProvider(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bookmarks bkm = new Bookmarks();
                    bkm.setPersonId(Integer.parseInt(personId.getText().toString()));
                    bkm.setExecutorId(Integer.parseInt(executorId.getText().toString()));
                    provider.addBookmark(bkm);
                Toast.makeText(Bookmarks_create_activity.this, "Added", Toast.LENGTH_SHORT).show();
                personId.setText("");
                executorId.setText("");
            }
        });
    }
}
