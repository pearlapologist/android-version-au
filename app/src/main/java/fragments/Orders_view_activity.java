package fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectwnavigation.R;

import models.DataConverter;
import models.MyDataProvider;
import models.Order;
import models.Section_of_services;

public class Orders_view_activity extends AppCompatActivity {
    TextView title, price,  descr, deadline;
    TextView createdDate;
    Button response;
    MyDataProvider provider;
    TextView spinnerSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_view);


        title = findViewById(R.id.order_view_title_act);
        price = findViewById(R.id.order_view_price_act);
        descr = findViewById(R.id.order_view_desc_act);
        createdDate = findViewById(R.id.order_view_created_act);
        deadline = findViewById(R.id.order_view_deadline_act);
        response = findViewById(R.id.order_view_btn_act);
        spinnerSection = findViewById(R.id.order_view_section_act);

        provider = new MyDataProvider(this);

        getAndSetOrderIntentData();

        response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    void getAndSetOrderIntentData() {
        if (getIntent().hasExtra("orderIdFragment")) {
            final int gettedId = getIntent().getIntExtra("orderIdFragment", -1);
            if(gettedId != -1){
            Order cur = provider.getOrder(gettedId);
            Section_of_services section = provider.getSection(cur.getSection());
             //   spinnerSection.setText(section.getTitle());
           spinnerSection.setText(cur.getSection()+"");

            title.setText(cur.getTitle());
            price.setText(cur.getPrice() + "");
            descr.setText(cur.getDescription());
            String s = DataConverter.convertLongToDataString(cur.getDeadline());
            deadline.setText(s);
            String d = DataConverter.convertLongToDataString(cur.getCreated_date());
            createdDate.setText(d);}
            else{
                Toast.makeText(this, "orderid invalid", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }
}
