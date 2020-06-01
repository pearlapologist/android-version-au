package com.example.projectwnavigation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import models.Bookmarks;
import models.DbHelper;
import models.Executor;
import models.MyDataProvider;

public class Bookmarks_update_activity extends AppCompatActivity {
    EditText personId, executorId;
    TextView tvId;
    Button btnOk, btnDelete;

    MyDataProvider provider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks_update);

        personId = findViewById(R.id.bookm_update_person_id);
        executorId = findViewById(R.id.bookm_update_executor_id);
        btnOk = findViewById(R.id.bookm_update_btnOk);
        btnDelete = findViewById(R.id.bookm_update_btnDelete);
        tvId = findViewById(R.id.bookm_update_id);

        provider = new MyDataProvider(this);


        getAndSetBookmIntentData();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bookmarks b = new Bookmarks() {   };
                b.setId(getIntent().getIntExtra("bookmId", Integer.parseInt(tvId.getText().toString())));
                b.setExecutorId(Integer.parseInt(executorId.getText().toString()));
                b.setPersonId(Integer.parseInt(personId.getText().toString()));

                provider.updateBookmark(b);
                Toast.makeText(Bookmarks_update_activity.this, "Updated", Toast.LENGTH_SHORT).show();

                personId.setText("");
                executorId.setText("");
                tvId.setText("");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Bookmarks_update_activity.this);
                builder.setMessage("Are you sure you want to delete bookmark?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        provider.deleteBookm(getIntent().getIntExtra("bookmId", -1));
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

    void getAndSetBookmIntentData() {
        if (getIntent().hasExtra("bookmId")) {
            final int gettedId = getIntent().getIntExtra("bookmId", -1);
            Bookmarks book = provider.getBookmark(gettedId);

            tvId.setText(book.getId()+"");
            personId.setText(book.getPersonId() + "");
            executorId.setText( book.getExecutorId() + "");

        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        getAndSetBookmIntentData();
        super.onResume();
    }
}
