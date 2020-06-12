package fragments;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.Executor;
import models.MyDataProvider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_bkmk_specials#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_bkmk_specials extends Fragment implements View.OnLongClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView specialsRv;
    Spinner spinner;
    Context context;
    MyDataProvider provider;
Fragment_bkmk_specials_adapter specials_adapter;
    public Boolean contextModeEnable = false;

    ArrayList<Executor> executors;
    ArrayList<Executor> selectionList;

    private String mParam1;
    private String mParam2;

    public Fragment_bkmk_specials(Context context) {
        this.context = context;
        provider = new MyDataProvider(context);
    }
  public Fragment_bkmk_specials() {
    }


    public static Fragment_bkmk_specials newInstance(String param1, String param2) {
        Fragment_bkmk_specials fragment = new Fragment_bkmk_specials();
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
        return inflater.inflate(R.layout.fragment_brmk_specials, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        selectionList = new ArrayList<>();

        insertArray();
        specials_adapter = new Fragment_bkmk_specials_adapter(this, context, executors);
        specialsRv.setLayoutManager(new LinearLayoutManager(context));
        specialsRv.setAdapter(specials_adapter);

        super.onViewCreated(view, savedInstanceState);
    }

    public void setSelection(View v, int adapterPosition) {

    }

    void insertArray() {
        executors = provider.getExecutorsListFromBookmarks;
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.main, menu);
        selectMenu(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        selectMenu(menu);
        return true;
    }
    

    private void selectMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = menu.getMenuInflater();
        if (contextModeEnable) {
            inflater.inflate(R.menu.services_multiply_choice, menu);
        } else {
            inflater.inflate(R.menu.main, menu);
        }
    }
}
