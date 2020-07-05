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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.ApiProvider;
import models.Executor;
import models.MyDataProvider;


public class Fragment_specials extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MyDataProvider provider;
    ApiProvider apiProvider;
    Context context;


    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    Executors_adapter_frg executors_adapter;
    Spinner spinner;
    ImageView img_nospec;
    ArrayList<Executor> executors;


    public Fragment_specials() {
    }

    public Fragment_specials(Context context) {
        this.context = context;
        this.provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();
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
        recyclerView = view.findViewById(R.id.fargment_specials_rv);
        spinner = view.findViewById(R.id.fargment_specials_spinner2);
        img_nospec = view.findViewById(R.id.fargment_specials_no_spec);

        insertExecutorsArray();

        executors_adapter = new Executors_adapter_frg(context, executors);
        recyclerView.setAdapter(executors_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ArrayAdapter arrayAdapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sections));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
     /*   String[] choose = getResources().getStringArray(R.array.sections);
        String division = choose[position];*/
        String str = parent.getItemAtPosition(position).toString();
        int sectionId =apiProvider.getSectionIdByTitle(str); // provider.getSectionIdByTitle(division);
        if (executors != null) {
            executors.clear();
        }
        ArrayList<Executor>n =apiProvider.getExecutorsBySectionId(sectionId); // provider.getExecutorsBySectionId(sectionId);
        executors.addAll(n);
        executors_adapter.notifyDataSetChanged();
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void insertExecutorsArray() {
        try {
            executors = apiProvider.getAllExecutors() ; //provider.getAllExecutors();
            if (executors == null || executors.size() <= 0) {
                img_nospec.setVisibility(View.VISIBLE);
            }
            else {
          //      executors.clear();
          //      executors_adapter.notifyDataSetChanged();
                img_nospec.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e("insertExecutorsArray", e.getMessage());
        }

    }


}
