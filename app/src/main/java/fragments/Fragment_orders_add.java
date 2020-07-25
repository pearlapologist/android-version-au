package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
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
import com.google.android.material.textfield.TextInputLayout;
import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;

import models.ApiProvider;
import models.CustomArrayAdapter;
import models.Executor;
import models.MyUtils;
import models.MyDataProvider;
import models.Order;


public class Fragment_orders_add extends Fragment {
    MyDataProvider provider;
    ApiProvider apiProvider;
    Context context;
    TextInputLayout title, price, descr, deadlinelayout;
    MaskEditText deadline;
    Button add, cancel;
    Spinner mSpinner;
    CheckBox checkDeadl, ch_anon;
    int sectionId = 0;
    ProgressDialog pd;

    public Fragment_orders_add() {
    }

    public Fragment_orders_add(Context context) {
        this.provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        checkDeadl = view.findViewById(R.id.orders_add_checkDeadl);
        deadlinelayout = view.findViewById(R.id.orders_add_deadlineelayout);
        ch_anon = view.findViewById(R.id.orders_add_checkbox_isanon);
        this.provider = new MyDataProvider(context);

        CustomArrayAdapter arrayAdapter = new CustomArrayAdapter(context,
                R.layout.spinner_layout, R.id.spinner_layout_textview, getResources().getStringArray(R.array.sections), 0);

        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setSelection(1);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sectionId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = descr.getEditText().getText().toString().trim();
                if (desc.isEmpty() || desc.equals(" ")) {
                    descr.setError("Заполните поле");
                    return;
                } else {
                    descr.setError(null);
                    descr.setErrorEnabled(false);

                    String txttitle = title.getEditText().getText().toString().trim();
                    if (txttitle.isEmpty() || txttitle.equals(" ")) {
                        title.setError("Заполните поле");
                        return;
                    } else {
                        title.setError(null);
                        title.setErrorEnabled(false);

                        String txtprice = price.getEditText().getText().toString().trim();
                        if (txtprice.isEmpty() || txtprice.equals(" ")) {
                            price.setError("Заполните поле");
                            return;
                        } else {
                            price.setError(null);
                            price.setErrorEnabled(false);
                            Long l = 0L;
                            if (!checkDeadl.isChecked()) {
                                l = MyUtils.convertDataToLongWithRawString(deadline.getText().toString());
                            }
                            Long curr = MyUtils.getCurentDateInLong();
                            Order order = new Order(txttitle,
                                    provider.getLoggedInPerson().getId(),
                                    sectionId,
                                    Double.valueOf(txtprice),
                                    desc,
                                    l,
                                    curr);

                            order.setIsAnonNote(!ch_anon.isChecked());
                            CreateOrderTask task = new CreateOrderTask();
                            task.execute(order);
                            Toast.makeText(context, "Заказ создан", Toast.LENGTH_SHORT).show();

                            Fragment_orders fragment_orders = new Fragment_orders(context);
                            FragmentManager manager = getFragmentManager();
                            manager.beginTransaction().replace(R.id.fram, fragment_orders).commit();
                        }
                    }
                }
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

        checkDeadl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                deadline.setEnabled(!checkDeadl.isChecked());
                if (checkDeadl.isChecked()) {
                    deadlinelayout.setBackgroundColor(getResources().getColor(R.color.colorMutedText));
                    deadlinelayout.setBoxStrokeColor(getResources().getColor(R.color.colorMutedText));
                } else {
                    deadlinelayout.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                    deadlinelayout.setBoxStrokeColor(getResources().getColor(R.color.colorMutedText));
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

    public void onBackPressed() {
        Fragment_orders fragment_orders = new Fragment_orders(context);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.fram, fragment_orders).commit();
    }


}
