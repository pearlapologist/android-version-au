package fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectwnavigation.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import models.MyDataProvider;

public class Fragment_bkmrk extends Fragment {
    MyDataProvider provider;
    Context context;

    BottomNavigationView bottomNavigationView;
    Fragment_bkmk_specials fragment_specials;
    Fragment_bkmk_orders fragment_orders;


    public Fragment_bkmrk() {
    }

    public Fragment_bkmrk(Context context) {
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
        return inflater.inflate(R.layout.fragment_bkmrk, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragment_specials = new Fragment_bkmk_specials(context);
        fragment_orders = new Fragment_bkmk_orders(context);
        bottomNavigationView = view.findViewById(R.id.frg_bkm_navbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);

        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.frg_bkm_framelayout, fragment_specials).commit();

        super.onViewCreated(view, savedInstanceState);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selected = null;
            switch (menuItem.getItemId()) {
                case R.id.menu_bookm_navbar_specials:
                    selected = fragment_specials;
                    break;
                case R.id.menu_bookm_navbar_orders:
                   selected = fragment_orders;
                    break;
            }
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.frg_bkm_framelayout, selected).commit();

            return true;
        }
    };


}
