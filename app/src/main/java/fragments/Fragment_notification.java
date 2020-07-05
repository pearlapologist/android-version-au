package fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.MyDataProvider;
import models.Notify;

public class Fragment_notification extends Fragment  {
     private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    MyDataProvider provider;
    Context context;

    RecyclerView recyclerView;
    ArrayList<Notify> notifies = new ArrayList<>();
    Fragment_notification_adapter notify_adapter;
    ImageView img_no_notify;


    public Fragment_notification() {
    }
    public Fragment_notification(Context context) {
        this.context = context;
        this.provider = new MyDataProvider(context);
    }

    public static Fragment_notification newInstance(String param1, String param2) {
        Fragment_notification fragment = new Fragment_notification();
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
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.frg_notification_rv);
        img_no_notify =  view.findViewById(R.id.frg_notification_img_nonotify);
        provider = new MyDataProvider(context);

        insertArray();

        notify_adapter = new Fragment_notification_adapter(context, notifies);
        recyclerView.setAdapter(notify_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        notify_adapter.notifyDataSetChanged( );

        super.onViewCreated(view, savedInstanceState);
    }


    void insertArray() {
        notifies = provider.getAllMyNotifies();
        if (notifies == null || notifies.size() <= 0) {
          img_no_notify.setVisibility(View.VISIBLE);
        }else{
            img_no_notify.setVisibility(View.GONE);
        }
    }
}
