package fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectwnavigation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import models.ApiProvider;
import models.MyDataProvider;
import models.Order;

public class Fragment_orders extends Fragment implements AdapterView.OnItemSelectedListener {
    MyDataProvider provider;
    ApiProvider apiProvider;
    Context context;

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ArrayList<Order> orders = new ArrayList<>();
    Orders_adapter_frg orders_adapter_frg;
    Spinner spinner;
    ImageView img_noorders;

    public Fragment_orders(Context context) {
        this.context = context;
        provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();
    }

    public Fragment_orders() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orders, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        add_button = view.findViewById(R.id.orders_listf_fb);
        recyclerView = view.findViewById(R.id.orders_listf_rv);
        img_noorders = view.findViewById(R.id.orders_listf_no_orders);
        spinner = view.findViewById(R.id.orders_listf_spinner);

        insertArray();

        orders_adapter_frg = new Orders_adapter_frg(getActivity(), context, orders);
        recyclerView.setAdapter(orders_adapter_frg);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        orders_adapter_frg.notifyDataSetChanged( );

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_orders_add addFrg = new Fragment_orders_add(context);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fram, addFrg).commit();
            }
        });

        ArrayAdapter arrayAdapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sections));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //String[] choose = getResources().getStringArray(R.array.sections);
        String str = parent.getItemAtPosition(position).toString();
        int sectionId =apiProvider.getSectionIdByTitle(str); // provider.getSectionIdByTitle(choose[position]);
        if (orders != null) {
            orders.clear();
        }
        ArrayList<Order> n = provider.getOrdersBySectionId(sectionId);
        orders.addAll(n);
        orders_adapter_frg.notifyDataSetChanged();
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    void insertArray() {
        orders = provider.getOrders();
        if (orders == null || orders.size() <= 0) {
            img_noorders.setVisibility(View.VISIBLE);
        }else{
            img_noorders.setVisibility(View.GONE);
        }
    }



}

