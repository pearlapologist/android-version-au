package fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.ApiProvider;
import models.Bookmarks;
import models.MyDataProvider;
import models.Persons;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_bkmk_orders#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_bkmk_orders extends Fragment implements View.OnLongClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public static Fragment_bkmk_orders newInstance(String param1, String param2) {
        Fragment_bkmk_orders fragment = new Fragment_bkmk_orders();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    MyDataProvider provider;
    ApiProvider apiProvider;
    Context context;

    RecyclerView ordersRv;
    Fragment_bkmk_orders_adapter orders_adapter;
    ImageView img_noorders;
    Spinner spinner;
    ArrayList<Bookmarks> orders;

    public Boolean contextModeEnable = false;

    ArrayList<Bookmarks> selectionList;

    Persons curPerson;

    public Fragment_bkmk_orders() {
    }

    public Fragment_bkmk_orders(Context context) {
        this.context = context;
        provider = new MyDataProvider(context);
        apiProvider=new ApiProvider();
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
        return inflater.inflate(R.layout.fragment_bkmk_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ordersRv = view.findViewById(R.id.frg_bkmk_orders_rv);
        spinner = view.findViewById(R.id.frg_bkmk_orders_spinenr);
        img_noorders = view.findViewById(R.id.frg_bkmk_orders_no_orders);
        selectionList = new ArrayList<>();
        MyDataProvider provider2 = new MyDataProvider(context);
        curPerson = provider2.getLoggedInPerson();
        insertArray();

        orders_adapter = new Fragment_bkmk_orders_adapter(this, context, orders);
        ordersRv.setAdapter(orders_adapter);
        ordersRv.setLayoutManager(new LinearLayoutManager(context));
        super.onViewCreated(view, savedInstanceState);
    }


    void insertArray() {
        try {
            orders =apiProvider.getOrdersListFromPersonBookmarks(curPerson.getId()); // provider.getOrdersListFromMyBookmarks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(orders == null || orders.size() <= 0){
            img_noorders.setVisibility(View.VISIBLE);
        }else{
            img_noorders.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        contextModeEnable = true;
        getActivity().invalidateOptionsMenu();
        orders_adapter.notifyDataSetChanged();
        return true;
    }

    public void setSelection(View v, int adapterPosition) {
        if (((CheckBox) v).isChecked()) {
            selectionList.add(orders.get(adapterPosition));
        } else {
            selectionList.remove(orders.get(adapterPosition));
            getActivity().invalidateOptionsMenu();
        }
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        selectMenu(menu, getActivity().getMenuInflater());
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        selectMenu(menu, inflater);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.multiply_delete) {
            showDialogDelete(selectionList);
        } else {
            removeContext();
        }
        return true;
    }

    private void selectMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if (contextModeEnable) {
            inflater.inflate(R.menu.services_multiply_choice, menu);
        } else {
            inflater.inflate(R.menu.main, menu);
        }
    }



    private void showDialogDelete(final ArrayList<Bookmarks> selectionList) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setContentView(R.layout.dialog_answer_delete);
        Button btnSave = dialog.findViewById(R.id.dialog_answer_delete_btn_save);
        Button btnCancel = dialog.findViewById(R.id.dialog_answer_delete_btn_cancel);

        dialog.setCancelable(true);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orders_adapter.removeItem(selectionList);
                removeContext();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    private void removeContext() {
        contextModeEnable = false;
        selectionList.clear();
        orders_adapter.notifyDataSetChanged();
        getActivity().invalidateOptionsMenu();
    }
}
