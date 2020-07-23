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

import models.MyDataProvider;


public class Fragment_settings extends Fragment {
 //   MyDataProvider provider;
    Context context;

    Button btn_tosecurity;

    public Fragment_settings() {
    }
    public Fragment_settings(Context context) {
        this.context = context;
       // this.provider = new MyDataProvider(context);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btn_tosecurity = view.findViewById(R.id.frg_settings_btn_tosecurity);

        btn_tosecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_settings_security addFrg = new Fragment_settings_security(context);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fram, addFrg).commit();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}
