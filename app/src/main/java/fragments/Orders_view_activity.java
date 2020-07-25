package fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.google.android.material.textfield.TextInputLayout;

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

    ProgressDialog pd;

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
                try {
                    cur = apiProvider.getOrder(gettedId); // provider.getOrder(gettedId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        insertRespones();
        adapter = new Orders_responses_adapter(this, this, responses);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Section_of_services section = null; // provider.getSection(cur.getSection());
        try {
            section = apiProvider.getSection(cur.getSection());
            spinnerSection.setText(section.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }


        title.setText(cur.getTitle());
        price.setText("" + cur.getPrice());
        descr.setText(cur.getDescription());
        Long deadlinelong = cur.getDeadline();
        if (deadlinelong != 0) {
            deadline.setText(MyUtils.convertLongToDataString(deadlinelong));
        }else{
            deadline.setText("Не ограничено");
        }
        String d = MyUtils.convertLongToDataString(cur.getCreated_date());
        createdDate.setText(d);

        boolean b = false;
        ArrayList<Integer> arrId = new ArrayList<>();
        try {
            arrId = apiProvider.getRespondedPersonsIdListByOrderIdId(cur.getId());  //provider.getRespondedPersonsIdListByOrderId(cur.getId());
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
            btn_response.setBackgroundColor(getResources().getColor(R.color.colorMutedGrayLight));
        }


        btn_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCreate();
            }
        });


        if (cur.isAnonNote()) {
            btn_viewprofile.setEnabled(false);
            btn_viewprofile.setBackgroundColor(getResources().getColor(R.color.colorMutedGrayLight));
        }

        btn_viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int personId = 0;
                try {
                    personId = apiProvider.getCustomerIdByOrderId(cur.getId());  //provider.getCustomerIdByOrderId(cur.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int executorId = 0;
                try {
                    executorId = apiProvider.getExecutorIdByPersonId(personId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // provider.getExecutorIdByPersonId(personId);

                if (personId == curPerson.getId()) {
                    Intent intent = new Intent(Orders_view_activity.this, MyProfileActivity.class);
                    startActivity(intent);
                } else if (executorId != 0 & executorId != -1) {
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
        try {
            responses = apiProvider.getOrderResponsesById(cur.getId());  //provider.getAllOrderResponsesByOrderId(cur.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialogCreate() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_createresponse);
        dialog.setTitle("Добавить отклик");

        final TextInputLayout edDesc = dialog.findViewById(R.id.dialog_createresponse_desc);
        final TextInputLayout edPrice = dialog.findViewById(R.id.dialog_createresponse_price);
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
                            edDesc.getEditText().getText().toString().trim(),
                            Double.parseDouble(edPrice.getEditText().getText().toString().trim()),
                            date);

                    AddResponseTask addResponseTask = new AddResponseTask();
                    addResponseTask.execute(response);
                    //provider.addRespons(response);
                    adapter.notifyItemInserted(response.getId());
                    responses.add(response);
                    Notify notify = new Notify(cur.getCustomerId(), "У вашего заказа новый отклик", MyUtils.getCurentDateInLong(), 1, cur.getId(), 0);
                    CreateNotifyTask task = new CreateNotifyTask();
                    task.execute(notify);

                    //provider.createNotify(notify);
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


    private class CreateNotifyTask extends AsyncTask<Notify, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Orders_view_activity.this);
            pd.setMessage("Пожалуйста, подождите");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Notify... params) {
            try {
                apiProvider.createNotify(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
        }
    }

    private class AddResponseTask extends AsyncTask<Response, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Orders_view_activity.this);
            pd.setMessage("Пожалуйста, подождите");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Response... params) {
            try {
                apiProvider.addResponse(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
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
