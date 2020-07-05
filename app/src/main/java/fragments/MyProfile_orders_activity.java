package fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.ApiProvider;
import models.MyDataProvider;
import models.Order;
import models.Persons;

public class MyProfile_orders_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    MyDataProvider provider;
    ApiProvider apiProvider;

    RecyclerView recyclerView;
    ArrayList<Order> orders = new ArrayList<>();
    MyProfile_orders_adapter profile_orders_adapter;
    Spinner spinner;
    ImageView img_no_orders;
    Persons loggedPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_orders);
        recyclerView =findViewById(R.id.activity_profile_orders_rv);
        spinner =findViewById(R.id.activity_profile_orders_spinner);
        img_no_orders =findViewById(R.id.activity_profile_orders_img_no_orders);
        provider = new MyDataProvider(this);
        apiProvider = new ApiProvider();
        insertArray();

        profile_orders_adapter = new MyProfile_orders_adapter(this, this, orders);
        recyclerView.setAdapter(profile_orders_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> sectionList = apiProvider.getSectionListInString(); //provider.getSectionListInString();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.spinner_layout, R.id.spinner_layout_textview, sectionList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    void insertArray() {
        loggedPerson =  provider.getLoggedInPerson();
        orders = provider.getPersonOrdersById(loggedPerson.getId());
        if (orders == null || orders.size() <= 0) {
          img_no_orders.setVisibility(View.VISIBLE);
        }else{
            img_no_orders.setVisibility(View.GONE);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String str = parent.getItemAtPosition(position).toString();
        //sectionId = provider.getSectionIdByTitle(str);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
