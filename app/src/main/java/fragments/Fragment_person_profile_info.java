package fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectwnavigation.R;

import models.MyDataProvider;
import models.Persons;

public class Fragment_person_profile_info extends Fragment {
    private static final String P_ARG_ID = "argPersonId";

    TextView desc, contacts;
    Button btn_call;

    MyDataProvider provider;
    Context context;

    private int receivedId;

    public Fragment_person_profile_info() {
    }

    public static Fragment_person_profile_info newInstance(int id) {
        Fragment_person_profile_info fragment = new Fragment_person_profile_info();
        Bundle args = new Bundle();
        args.putInt(P_ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public void setContext(Context context) {
        this.provider = new MyDataProvider(context);
        this.context = context;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        receivedId = getArguments().getInt(P_ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person_profile_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       Persons person = provider.getPerson(receivedId);

        desc = view.findViewById(R.id.fragment_person_profile_info_desc);
        contacts = view.findViewById(R.id.fragment_person_profile_info_contacts);
        btn_call = view.findViewById(R.id.fragment_person_profile_info_btn_call);

        String description = person.getDesciption();
        if(description == null){
            description = "(Пользователь не указал о себе ничего)";
            desc.setText(description);
            desc.setTextColor(getResources().getColor(R.color.colorMutedText));
        }else {
            desc.setText(description);
        }
        contacts.setText(person.getNumber());
        super.onViewCreated(view, savedInstanceState);
    }
}
