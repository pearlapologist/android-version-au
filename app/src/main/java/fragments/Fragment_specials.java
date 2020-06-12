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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectwnavigation.Executors_adapter;
import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.Executor;
import models.MyDataProvider;


public class Fragment_specials extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MyDataProvider provider;
    Context context;


    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    Executors_adapter_frg executors_adapter;
    Spinner spinner;
    ArrayList<Executor> executors;


    public Fragment_specials() {
    }

    public Fragment_specials(Context context) {
        this.context = context;
        this.provider = new MyDataProvider(context);
    }

    public static Fragment_specials newInstance(String param1, String param2) {
        Fragment_specials fragment = new Fragment_specials();
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
        return inflater.inflate(R.layout.fragment_specials, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        insertExecutorsArray();
        recyclerView = view.findViewById(R.id.fargment_specials_rv);
        executors_adapter = new Executors_adapter_frg(context, executors);
        recyclerView.setAdapter(executors_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        spinner = view.findViewById(R.id.fargment_specials_spinner);

        ArrayList<String> sectionList = provider.getSectionListInString();
        ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_layout, R.id.spinner_layout_textview, sectionList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        super.onViewCreated(view, savedInstanceState);
    }

    void insertExecutorsArray() {
        try{
        executors = provider.getExecutors();
       }
        catch (Exception e){
            Log.e("Error!!!", e.getMessage());
        }
        if (executors == null || executors.size() <= 0) {
            Toast.makeText(context, "Пока что не было создано специалистов", Toast.LENGTH_SHORT).show();
        }
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
