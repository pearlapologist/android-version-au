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

import models.Bookmarks;
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
    private String mParam1;
    private String mParam2;
    
    MyDataProvider provider;
    Context context;
    
    RecyclerView specialsRv;
    Fragment_bkmk_specials_adapter specials_adapter;
    Spinner spinner;
    ImageView img_nospecialists;
    ArrayList<Bookmarks> executors;
    
    public Boolean contextModeEnable = false;
    
    ArrayList<Bookmarks> selectionList;


    public Fragment_bkmk_specials(Context context) {
        this.context = context;
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
        setHasOptionsMenu(true);
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
        specialsRv = view.findViewById(R.id.frg_bkmk_specials_rv);
        spinner = view.findViewById(R.id.frg_bkmk_specials_spinner);
        img_nospecialists = view.findViewById(R.id.frg_bkmk_specials_no_specialists);
        provider = new MyDataProvider(context);
        selectionList = new ArrayList<>();

        insertArray();

        specials_adapter = new Fragment_bkmk_specials_adapter(this, context, executors);
        specialsRv.setAdapter(specials_adapter);  
        specialsRv.setLayoutManager(new LinearLayoutManager(context));

        super.onViewCreated(view, savedInstanceState);
    }


    void insertArray() {
   executors = provider.getExecutorsListFromMyBookmarks();
        if(executors == null || executors.size() <= 0){
           img_nospecialists.setVisibility(View.VISIBLE);
        }else{
            img_nospecialists.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        contextModeEnable = true;
        getActivity().invalidateOptionsMenu();
        specials_adapter.notifyDataSetChanged();
        return true;
    }

    public void setSelection(View v, int position) {
        if (((CheckBox) v).isChecked()) {
            selectionList.add(executors.get(position));
        } else {
            selectionList.remove(executors.get(position));
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
                 specials_adapter.removeItem(selectionList);
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
        specials_adapter.notifyDataSetChanged();
        getActivity().invalidateOptionsMenu();
    }


    private void selectMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if (contextModeEnable) {
            inflater.inflate(R.menu.services_multiply_choice, menu);
        } else {
            inflater.inflate(R.menu.main, menu);
        }
    }
}
