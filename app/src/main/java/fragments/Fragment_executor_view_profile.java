package fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.Executor;
import models.MyDataProvider;
import models.Persons;
import models.Service;

public class Fragment_executor_view_profile extends Fragment {
    private static final String ARG_ID = "argExecutorId";

    TextView spec, desc, contacts;
    RecyclerView servicesRv;
    Executor_services_adapter_frg executor_services_adapter_frg;
    ArrayList<Service> services;
    Button number;

    MyDataProvider provider;
    Context context;

    private int gottenExecutorId;

    public Fragment_executor_view_profile() {

    }


    public static Fragment_executor_view_profile newInstance(int executorId) {
        Fragment_executor_view_profile fragment = new Fragment_executor_view_profile();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, executorId);
        fragment.setArguments(args);
        return fragment;
    }

    public void setContext(Context context) {
        this.provider = new MyDataProvider(context);
        this.context = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            gottenExecutorId = getArguments().getInt(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_executor_view_profile, container, false);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        servicesRv = view.findViewById(R.id.fragment_executor_view_profile_rv);
        insertArray();
        executor_services_adapter_frg = new Executor_services_adapter_frg(getActivity(), context, services);
        servicesRv.setAdapter(executor_services_adapter_frg);
        servicesRv.setLayoutManager(new LinearLayoutManager(context));
        Executor executor = provider.getExecutor(gottenExecutorId);

        spec = view.findViewById(R.id.fragment_executor_view_profile_spec);
        desc = view.findViewById(R.id.fragment_executor_view_profile_desc);
        contacts = view.findViewById(R.id.fragment_executor_view_profile_contacts);
        number = view.findViewById(R.id.fragment_executor_view_profile_contacts_btn);
        String special = executor.getSpecialztn();

        spec.setText(special);

        Persons p = provider.getPerson(executor.getPersonId());
        String numb = "телефон";
        if (p != null) {
            numb = p.getNumber();
        }
        contacts.setText(numb);

        desc.setText(executor.getDescriptn());
        super.onViewCreated(view, savedInstanceState);
    }

    void insertArray() {
        Executor executor = provider.getExecutor(gottenExecutorId);
        try{
        services = provider.getExecutorServices(executor.getId());}
        catch (NullPointerException e){
            Log.e("insertArray", e.getMessage());
        }
    }

}
