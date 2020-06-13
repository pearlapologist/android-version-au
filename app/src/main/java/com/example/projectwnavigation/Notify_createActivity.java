package com.example.projectwnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import models.MyUtils;
import models.MyDataProvider;
import models.Notify;

public class Notify_createActivity extends AppCompatActivity {
   /* EditText id, personId, text;
    MyDataProvider provider;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_create);

        id = findViewById(R.id.notify_create_nId);
        personId = findViewById(R.id.notify_create_personId);
        text = findViewById(R.id.notify_create_text);
        btnOk = findViewById(R.id.notify_create_btnOk);
        provider = new MyDataProvider(this);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Long curr = MyUtils.getCurentDateInLong();
                    provider.createNotify(new Notify(Integer.parseInt(personId.getText().toString()),
                            text.getText().toString().trim(),
                            curr));
                } catch (Exception e) {
                    Log.e("Update error", e.getMessage());
                }
                id.setText("");
                personId.setText("");
                text.setText("");
                Toast.makeText(Notify_createActivity.this, "Added", Toast.LENGTH_SHORT).show();
            }
        });


    }
*/
}
