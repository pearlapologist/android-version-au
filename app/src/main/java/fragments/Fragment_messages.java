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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.ApiProvider;
import models.Message;
import models.MyDataProvider;
import models.Persons;


public class Fragment_messages extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    MyDataProvider provider;
    Context context;
    ApiProvider apiProvider;


    RecyclerView recyclerView;
    Messages_adapter_frg messages_adapter;
    ImageView nomessages;
    ArrayList<Integer> personsId;
    Persons cur;

    public Fragment_messages() {
    }

    public Fragment_messages(Context context) {
        this.context = context;
        this.provider = new MyDataProvider(context);
    }

    public static Fragment_messages newInstance(String param1, String param2) {
        Fragment_messages fragment = new Fragment_messages();
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
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.fargment_msg_rv);
        nomessages = view.findViewById(R.id.fargment_msg_nomessages);

        apiProvider = new ApiProvider();
        cur = provider.getLoggedInPerson();

        insertMessages();

        messages_adapter = new Messages_adapter_frg(context, personsId);
        recyclerView.setAdapter(messages_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        super.onViewCreated(view, savedInstanceState);
    }

    void insertMessages() {
        try {
            if (personsId != null) {
                personsId.clear();
            }

            personsId = apiProvider.getAllPersonConversations(cur.getId());
        } catch (Exception e) {
            Log.e("insertMessages", e.getMessage());
        }

        if (personsId == null || personsId.size() <= 0) {
          nomessages.setVisibility(View.VISIBLE);
        }else{
            nomessages.setVisibility(View.GONE);
        }

    }
}
