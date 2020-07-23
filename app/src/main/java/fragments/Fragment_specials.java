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

import com.example.projectwnavigation.Navigation_activity;
import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.ApiProvider;
import models.Executor;
import models.MyDataProvider;


public class Fragment_specials extends Fragment {
    MyDataProvider provider;
    ApiProvider apiProvider;
    Context context;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specials, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.fargment_specials_rv);
        spinner = view.findViewById(R.id.fargment_specials_spinner3);
        img_nospec = view.findViewById(R.id.fargment_specials_no_spec);

        ArrayAdapter arrayAdapter = new ArrayAdapter(context,
                R.layout.spinner_layout, R.id.spinner_layout_textview, getResources().getStringArray(R.array.sections));
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);

        executors_adapter = new Executors_adapter_frg(context, executors);
        recyclerView.setAdapter(executors_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        checkExecutorsArray();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                    if (executors != null) {
                        executors.clear();
                        ArrayList<Executor> executors22 = apiProvider.getAllExecutors();
                        executors.addAll(executors22);
                        executors_adapter.notifyDataSetChanged();
                    }
                    else {
                        try {
                            executors = apiProvider.getAllExecutors();
                        } catch (Exception e) {
                            Log.e("insertExecutorsArray", e.getMessage());
                        }
                    }

                } else {
                    int sectionId = position;

                    if (executors != null) {
                        executors.clear();
                    }
                    ArrayList<Executor> executors2 = apiProvider.getExecutorsBySectionId(sectionId); // provider.getExecutorsBySectionId(sectionId);
                    executors.addAll(executors2);
                    executors_adapter.notifyDataSetChanged();
                }
                checkExecutorsArray();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    void checkExecutorsArray() {
        if (executors == null || executors.size() <= 0) {
            img_nospec.setVisibility(View.VISIBLE);
        } else {
            img_nospec.setVisibility(View.GONE);
        }
    }


}
