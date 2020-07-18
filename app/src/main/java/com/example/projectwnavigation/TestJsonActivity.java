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
                ApiProvider apiProvider = new ApiProvider();
                int id = Integer.parseInt(et.getText().toString());
                Persons p = null;
                try {
                    p = apiProvider.getPerson(id);
                    tv.setText(p.getName() +" " + p.getLastname() + " " + p.getNumber());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*Executor r = myDataProvider.getExecutor(myDataProvider.getExecutorIdByPersonId(id));
                String services = "пусто";
                if(r != null){
                    services = r.getServicesString();
                }*/
            }
        });
    }
}
