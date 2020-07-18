package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectwnavigation.MyProfile_createFormActivity;
import com.example.projectwnavigation.R;
import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;

import models.ApiProvider;
import models.Executor;
import models.MyUtils;
import models.MyDataProvider;
import models.Order;


public class Fragment_orders_add extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    MyDataProvider provider;
    ApiProvider apiProvider;
    Context context;

    EditText title, price, descr;
    MaskEditText deadline;
    Button add, cancel;
    Spinner mSpinner;
    CheckBox checkBox;

    int sectionId = 0;

    ProgressDialog pd;

    public Fragment_orders_add() {
    }

    public Fragment_orders_add(Context context) {
        this.provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();
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
        add = view.findViewById(R.id.dialog_orders_add_btnOk);
        mSpinner = view.findViewById(R.id.orders_add_section);
        cancel = view.findViewById(R.id.dialog_orders_add_btnCancel);
        checkBox = view.findViewById(R.id.orders_add_checkDeadl);
        this.provider = new MyDataProvider(context);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(context, R.array.sections,
                android.R.layout.simple_spinner_item);

        ArrayAdapter arrayAdapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sections));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter2);
        mSpinner.setSelection(1);


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             /*  Long id3 = id;
              int position3 = position;
                String str = parent.getItemAtPosition(position).toString();*/
                try {
                 //  sectionId = apiProvider.getSectionIdByTitle(str); // provider.getSectionIdByTitle(str);
                    int p =position+1;
                    sectionId = p;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long l = MyUtils.convertDataToLongWithRawString(deadline.getText().toString());
                Long curr = MyUtils.getCurentDateInLong();
                Order order = new Order(title.getText().toString().trim(),
                        provider.getLoggedInPerson().getId(),
                        sectionId,
                        Double.valueOf(price.getText().toString()),
                        descr.getText().toString(),
                        l,
                        curr);
                CreateOrderTask task = new CreateOrderTask();
                task.execute(order);
                Toast.makeText(context, "Заказ создан", Toast.LENGTH_SHORT).show();
                //provider.addOrder(order);
                Fragment_orders fragment_orders = new Fragment_orders(context);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fram, fragment_orders).commit();


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_orders fragment = new Fragment_orders(context);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fram, fragment).commit();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean b =checkBox.isChecked();
                if(b){
                    deadline.setEnabled(!b);
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private class CreateOrderTask extends AsyncTask<Order, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Пожалуйста, подождите");
                pd.setCancelable(true);
                pd.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected Void doInBackground(Order... params) {
            try {
                apiProvider.addOrder(params[0]);
            } catch (Exception e) {
                Log.e("addOrder", e.getMessage());
            } finally {
                return null;
            }
        }


        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }

        }
    }
}
