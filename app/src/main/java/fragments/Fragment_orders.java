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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectwnavigation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import models.MyDataProvider;
import models.Order;

public class Fragment_orders extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String ARG_PARAM_CHANGED = "change_param";
    MyDataProvider provider;
    Context context;

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ArrayList<Order> orders = new ArrayList<>();
    Orders_adapter_frg orders_adapter_frg;
    Spinner spinner;
    boolean datachanged = false;

    public Fragment_orders(Context context) {
        this.context = context;
    }

    public Fragment_orders() {
    }

    public static Fragment_orders newInstance(boolean changed) {
        Fragment_orders fragment = new Fragment_orders();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM_CHANGED, changed);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           datachanged = getArguments().getBoolean(ARG_PARAM_CHANGED);
        }
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
        spinner = view.findViewById(R.id.orders_listf_spinner);
        provider = new MyDataProvider(context);
        insertArray();

        orders_adapter_frg = new Orders_adapter_frg(getActivity(), context, orders);
        recyclerView.setAdapter(orders_adapter_frg);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_orders_add addFrg = new Fragment_orders_add(context);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fram, addFrg).commit();
            }
        });

        ArrayList<String> sectionList = provider.getSectionListInString();
        ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_layout, R.id.spinner_layout_textview, sectionList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        super.onViewCreated(view, savedInstanceState);
    }


    void insertArray() {
            orders = provider.getOrders();
        if (orders == null || orders.size() <= 0) {
            Toast.makeText(getContext(), "Пока что не было создано заказов", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        if(datachanged ==true){
            orders.clear();
            insertArray();
            datachanged =false;
        }
        super.onStart();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.orders_listf_spinner){
            String value = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}

