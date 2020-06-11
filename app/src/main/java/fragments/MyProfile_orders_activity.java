package fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.MyDataProvider;
import models.Order;
import models.Persons;

public class MyProfile_orders_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    MyDataProvider provider;
    RecyclerView recyclerView;
    ArrayList<Order> orders = new ArrayList<>();
    MyProfile_orders_adapter profile_orders_adapter;
    Spinner spinner;
    Persons loggedPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_orders);
        recyclerView =findViewById(R.id.activity_profile_orders_rv);
        spinner =findViewById(R.id.activity_profile_orders_spinner);
        provider = new MyDataProvider(this);
        insertArray();

        profile_orders_adapter = new MyProfile_orders_adapter(this, this, orders);
        recyclerView.setAdapter(profile_orders_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> sectionList = provider.getSectionListInString();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.spinner_layout, R.id.spinner_layout_textview, sectionList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    void insertArray() {
        loggedPerson =  provider.getLoggedInPerson();
        orders = provider.getPersonOrdersById(loggedPerson.getId());
        if (orders == null || orders.size() <= 0) {
            Toast.makeText(this, "Ваш список заказов пуст", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String str = parent.getItemAtPosition(position).toString();
        //sectionId = provider.getSectionIdByTitle(str);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
