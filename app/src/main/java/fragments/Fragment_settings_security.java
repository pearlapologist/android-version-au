package fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projectwnavigation.R;

import models.ApiProvider;
import models.MyDataProvider;

public class Fragment_settings_security extends Fragment {
    /*MyDataProvider provider;
    ApiProvider apiProvider;*/
    Context context;
    Button btn_edit_passwd;

    public Fragment_settings_security() {
    }

    public Fragment_settings_security(Context context) {
       /* this.provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();*/
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_security, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btn_edit_passwd = view.findViewById(R.id.frg_sttngs_scr_btn_editpasswd);

        btn_edit_passwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_edit_passwd editFrg = new Fragment_edit_passwd(context);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fram, editFrg).commit();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}