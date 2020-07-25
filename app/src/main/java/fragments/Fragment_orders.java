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

import com.example.projectwnavigation.Navigation_activity;
import com.example.projectwnavigation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import models.ApiProvider;
import models.Executor;
import models.MyDataProvider;
import models.Order;

public class Fragment_orders extends Fragment {
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
        spinner = view.findViewById(R.id.orders_listf_spinner2);

        ArrayAdapter arrayAdapter = new ArrayAdapter(context,
                R.layout.spinner_layout, R.id.spinner_layout_textview, getResources().getStringArray(R.array.sections));
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);

        orders_adapter_frg = new Orders_adapter_frg(getActivity(), context, orders);
        recyclerView.setAdapter(orders_adapter_frg);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        checkOrderssArray();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    if (orders != null) {
                        orders.clear();
                        ArrayList<Order> orders2 = null;
                        try {
                            orders2 = apiProvider.getOrders();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        orders.addAll(orders2);
                        orders_adapter_frg.notifyDataSetChanged();
                    }else{
                        try {
                            orders = apiProvider.getOrders();
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
                        orders2 = apiProvider.getOrdersBySectionId(sectionId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    orders.addAll(orders2);
                    orders_adapter_frg.notifyDataSetChanged();
                }
                checkOrderssArray();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_orders_add addFrg = new Fragment_orders_add(context);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fram, addFrg).commit();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    void checkOrderssArray() {
        if (orders == null || orders.size() <= 0) {
            img_noorders.setVisibility(View.VISIBLE);
        } else {
            img_noorders.setVisibility(View.GONE);
        }
    }

}

