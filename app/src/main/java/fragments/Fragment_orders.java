package fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.projectwnavigation.Navigation_activity;
import com.example.projectwnavigation.Orders_adapter;
import com.example.projectwnavigation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import models.DataConverter;
import models.Executor;
import models.MyDataProvider;
import models.Order;
import models.Persons;

public class Fragment_orders extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MyDataProvider provider;
    Context context;

    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
   FloatingActionButton add_button;
    ArrayList<Order> orders;
    Orders_adapter_frg orders_adapter_frg;



    public Fragment_orders(Context context) {
        this.provider = new MyDataProvider(context);
        this.context = context;
    }

/*
    public static Fragment_orders newInstance(String param1, String param2) {
        Fragment_orders fragment = new Fragment_orders();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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

        provider = new MyDataProvider(context);

        insertArray();

        orders_adapter_frg = new Orders_adapter_frg(getActivity(), context, orders);
        recyclerView.setAdapter(orders_adapter_frg);
       recyclerView.setLayoutManager(new LinearLayoutManager(context));

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Orders_add addFrg = new Orders_add(context);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fram, addFrg).commit();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }


    void insertArray() {
        orders = provider.getOrders();
    }

}

