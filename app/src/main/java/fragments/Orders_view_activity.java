package fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.ApiProvider;
import models.MyUtils;
import models.MyDataProvider;
import models.Notify;
import models.Order;
import models.Persons;
import models.Response;
import models.Section_of_services;

public class Orders_view_activity extends AppCompatActivity {
    TextView title, price, descr, deadline;
    TextView createdDate;
    Button btn_response, btn_viewprofile;

    MyDataProvider provider;
    ApiProvider apiProvider;
    TextView spinnerSection;
    RecyclerView recyclerView;
    ArrayList<Response> responses = new ArrayList<>();
    Orders_responses_adapter adapter;
    Order cur;

    Persons curPerson;
    boolean isCreator = false;
    private Menu options_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_view);

        title = findViewById(R.id.order_view_title_act);
        price = findViewById(R.id.order_view_price_act);
        descr = findViewById(R.id.order_view_desc_act);
        createdDate = findViewById(R.id.order_view_created_act);
        deadline = findViewById(R.id.order_view_deadline_act);
        btn_response = findViewById(R.id.order_view_btn_act);
        spinnerSection = findViewById(R.id.order_view_section_act);
        recyclerView = findViewById(R.id.order_view_recycler_act);
        btn_viewprofile = findViewById(R.id.order_view_btnViewPrf);
        provider = new MyDataProvider(this);
        apiProvider = new ApiProvider();

        curPerson = provider.getLoggedInPerson();

        if (getIntent().hasExtra("orderIdFragment")) {
            final int gettedId = getIntent().getIntExtra("orderIdFragment", -1);
            if (gettedId != -1) {
                cur = provider.getOrder(gettedId);
            }
        }

        insertRespones();
        adapter = new Orders_responses_adapter(this, this, responses);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Section_of_services section =apiProvider.getSection(cur.getSection()); // provider.getSection(cur.getSection());
        spinnerSection.setText(section.getTitle());

        title.setText(cur.getTitle());
        price.setText("" + cur.getPrice());
        descr.setText(cur.getDescription());
        String s = MyUtils.convertLongToDataString(cur.getDeadline());
        deadline.setText(s);
        String d = MyUtils.convertLongToDataString(cur.getCreated_date());
        createdDate.setText(d);

        boolean b = false;
        ArrayList<Integer> arrId = new ArrayList<>();
        try {
            arrId = provider.getRespondedPersonsIdListByOrderId(cur.getId());
        } catch (Exception e) {
            Log.e("Orders view activity", e.getMessage());
        }
        for (int i : arrId) {
            if (curPerson.getId() == i) {
                b = true;
            }
        }
        if (b == true || curPerson.getId() == cur.getCustomerId()) {
            btn_response.setEnabled(false);
        }


        btn_response.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        showDialogCreate();
    }
    });

        btn_viewprofile.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        int personId = provider.getCustomerIdByOrderId(cur.getId());
        int executorId = provider.getExecutorIdByPersonId(personId);

        if (personId == curPerson.getId()) {
            Intent intent = new Intent(Orders_view_activity.this, MyProfileActivity.class);
            startActivity(intent);
        } else if (executorId != 0 && executorId != -1) {
            Intent intent = new Intent(Orders_view_activity.this, Executors_view_activity.class);
            intent.putExtra("executorIdFragment", executorId);
            startActivity(intent);
        } else {
            Intent intent = new Intent(Orders_view_activity.this, PersonProfileActivity.class);
            intent.putExtra("orderview_PersonId", personId);
            startActivity(intent);
        }
    }
    });

}

    public void insertRespones() {
        responses = provider.getAllOrderResponsesByOrderId(cur.getId());
    }

    private void showDialogCreate() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_createresponse);
        dialog.setTitle("Добавить отклик");

        final EditText edDesc = dialog.findViewById(R.id.dialog_createresponse_desc);
        final EditText edPrice = dialog.findViewById(R.id.dialog_createresponse_price);
        Button btnSave = dialog.findViewById(R.id.dialog_createresponse_btnsave);
        Button btnCancel = dialog.findViewById(R.id.dialog_createresponse_btnCancel);

        dialog.setCancelable(true);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long date = MyUtils.getCurentDateInLong();
                try {
                    Response response = new Response(cur.getId(), curPerson.getId(),
                            edDesc.getText().toString().trim(),
                            Double.parseDouble(edPrice.getText().toString().trim()),
                            date);

                    provider.addRespons(response);
                    adapter.notifyItemInserted(response.getId());
                    responses.add(response);
                    Notify notify = new Notify(cur.getCustomerId(), "У вашего заказа новый отклик", MyUtils.getCurentDateInLong(), 1, cur.getId(), 0);
                    provider.createNotify(notify);
                } catch (Exception error) {
                    Log.e("error", error.getMessage());
                }
                dialog.dismiss();
                btn_response.setClickable(false);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public void checkIfCreator() {
        if (cur.getCustomerId() == curPerson.getId()) {
            isCreator = true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        options_menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.order_options_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        checkIfCreator();
        if (options_menu != null) {
            options_menu.findItem(R.id.order_options_menu_edit).setVisible(isCreator);
            options_menu.findItem(R.id.order_options_menu_delete).setVisible(isCreator);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.order_options_menu_savetobookm:
                Toast.makeText(this, "added", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.order_options_menu_edit:
                return true;
            case R.id.order_options_menu_delete:
                Toast.makeText(this, "added", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.order_options_menu_complain:
                Toast.makeText(this, "отправлена", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
