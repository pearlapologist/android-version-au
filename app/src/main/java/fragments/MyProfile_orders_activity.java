package fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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

public class MyProfile_orders_activity extends AppCompatActivity {
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
        loggedPerson =  provider.getLoggedInPerson();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                R.layout.spinner_layout, R.id.spinner_layout_textview, getResources().getStringArray(R.array.sections));
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);

        profile_orders_adapter = new MyProfile_orders_adapter(this, this, orders);
        recyclerView.setAdapter(profile_orders_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        checkOrderssArray();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    if (orders != null) {
                        orders.clear();

                        ArrayList<Order> orders2 = null;
                        try {
                            orders2 = apiProvider.getPersonOrdersById(loggedPerson.getId());;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        orders.addAll(orders2);
                        profile_orders_adapter.notifyDataSetChanged();
                    }else{
                        try {
                            orders = apiProvider.getPersonOrdersById(loggedPerson.getId());;
                        } catch (Exception e) {
                            Log.e("insertExecutorsArray", e.getMessage());
                        }}
                } else {
                    int sectionId = position;
                    if (orders != null) {
                        orders.clear();
                    }
                    ArrayList<Order> orders2 = null;
                    try {
                        orders2 = apiProvider.getPersonOrdersByIdNSection(loggedPerson.getId(), sectionId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    orders.addAll(orders2);
                    profile_orders_adapter.notifyDataSetChanged();
                }
                checkOrderssArray();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void checkOrderssArray() {
        if (orders == null || orders.size() <= 0) {
            img_no_orders.setVisibility(View.VISIBLE);
        } else {
            img_no_orders.setVisibility(View.GONE);
        }
    }

   /* void insertArray() {
        loggedPerson =  provider.getLoggedInPerson();

        try {
            orders =apiProvider.getPersonOrdersById(loggedPerson.getId()); // provider.getPersonOrdersById(loggedPerson.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (orders == null || orders.size() <= 0) {
          img_no_orders.setVisibility(View.VISIBLE);
        }else{
            img_no_orders.setVisibility(View.GONE);

        }
    }*/
}
