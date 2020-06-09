package fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectwnavigation.R;
import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;

import models.MyUtils;
import models.MyDataProvider;
import models.Order;


public class Fragment_orders_add extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    MyDataProvider provider;
    Context context;

    EditText title, price, descr;
    MaskEditText deadline;
    Button add;
    Spinner mSpinner;

    int sectionId = 0;


    public Fragment_orders_add() {
    }

    public Fragment_orders_add(Context context) {
        this.provider = new MyDataProvider(context);
        this.context = context;
    }


    public static Fragment_orders_add newInstance(String param1, String param2) {
        Fragment_orders_add fragment = new Fragment_orders_add();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        return inflater.inflate(R.layout.fragment_orders_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        title = view.findViewById(R.id.orders_add_title);
        price = view.findViewById(R.id.orders_add_price);
        descr = view.findViewById(R.id.orders_add_descr);
        deadline = view.findViewById(R.id.orders_add_deadlinee);
        add = view.findViewById(R.id.orders_add_btnOk);
        mSpinner = view.findViewById(R.id.orders_add_section);


        ArrayList<String> mOptions = provider.getSectionListInString();

        ArrayAdapter arrayAdapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, mOptions);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                sectionId = provider.getSectionIdByTitle(str);

                //case 0:
                /*        sectionId = 1;
                        break;
                    case 1:
                        sectionId = 2;
                        break;*/

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Long l = MyUtils.convertPntdStringToLong(deadline.getText().toString());
                    Long curr = MyUtils.getCurentDateInLong();
                    Order order = new Order(title.getText().toString().trim(),
                            provider.getLoggedInPerson().getId(),
                            sectionId,
                            Double.valueOf(price.getText().toString()),
                            descr.getText().toString(),
                            l,
                            curr);
                    provider.addOrder(order);

                } catch (Exception e) {
                    Log.e("Update error", e.getMessage());
                }
                title.setText("");
                price.setText("");
                deadline.setText("");
                descr.setText("");
                Toast.makeText(context, "Заказ создан", Toast.LENGTH_SHORT).show();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}