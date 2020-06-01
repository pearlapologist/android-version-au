package com.example.projectwnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import models.ApiProvider;
import models.Executor;
import models.MyDataProvider;
import models.Persons;

public class TestJsonActivity extends AppCompatActivity {
    TextView tv;
    Button btn;
    EditText et;
    MyDataProvider myDataProvider ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_json);
        tv= findViewById(R.id.testJsonTv);
        btn= findViewById(R.id.testJsonBtn);
        et= findViewById(R.id.testJsonEt);
        myDataProvider = new MyDataProvider(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiProvider provider = new ApiProvider();
                int id = Integer.parseInt(et.getText().toString());
                Persons p = provider.getPerson(id);
                /*Executor r = myDataProvider.getExecutor(myDataProvider.getExecutorIdByPersonId(id));
                String services = "пусто";
                if(r != null){
                    services = r.getServicesString();
                }*/
                tv.setText(p.getName() +" " + p.getLastname() + " " + p.getNumber());

            }
        });
    }
}
