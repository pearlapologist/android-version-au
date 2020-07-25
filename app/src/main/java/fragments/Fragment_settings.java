package fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projectwnavigation.FirstActivity;
import com.example.projectwnavigation.R;

import models.MyDataProvider;


public class Fragment_settings extends Fragment implements View.OnClickListener {
    MyDataProvider provider;
    Context context;

    Button btn_tosecurity, btn_exit;

    public Fragment_settings() {
    }
    public Fragment_settings(Context context) {
        this.context = context;
      this.provider = new MyDataProvider(context);
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
        btn_exit = view.findViewById(R.id.frg_settings_btn_exit);

        btn_tosecurity.setOnClickListener(this);
        btn_exit.setOnClickListener(this);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.frg_settings_btn_exit:
                provider.setLoggedInPerson(null);
                Intent intent = new Intent(context, FirstActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.frg_settings_btn_tosecurity:
                Fragment_settings_security addFrg = new Fragment_settings_security(context);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fram, addFrg).commit();
                break;
        }
    }
}
