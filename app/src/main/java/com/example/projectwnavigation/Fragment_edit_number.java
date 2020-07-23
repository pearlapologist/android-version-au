package com.example.projectwnavigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.santalu.maskedittext.MaskEditText;

import fragments.MyProfileActivity;
import models.ApiProvider;
import models.MyDataProvider;
import models.Persons;

public class Fragment_edit_number extends Fragment {

    MyDataProvider provider;
    ApiProvider apiProvider;
    Persons loggedInPerson;
    Context context;
    MaskEditText etNumber;
    Button btn_save, btn_cancel;
    String number;
    ProgressDialog pd;

    public Fragment_edit_number() {
    }

    public Fragment_edit_number(Context context) {
        this.provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();
        this.context = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_number, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etNumber = view.findViewById(R.id.frg_edit_numb_etnumber);
        btn_save = view.findViewById(R.id.frg_edit_numb_btn_save);
        btn_cancel = view.findViewById(R.id.frg_edit_numb_btn_cancel);

       loggedInPerson = provider.getLoggedInPerson();
        etNumber.setText(loggedInPerson.getNumber());

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyProfile_edit_activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateNumber()) {
                    return;
                }
                try {
                    loggedInPerson.setNumber(number);

                   EditNumberTask task = new EditNumberTask();
                    task.execute(loggedInPerson);
                    String result = task.get();
                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, MyProfile_edit_activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Ошибка : 3", Toast.LENGTH_SHORT).show();
                }
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private boolean validateNumber() {
        String num = etNumber.getRawText().trim();
        String n = "+7" + num;

        if (n.isEmpty() || n.length() == 0) {
            etNumber.setError("Заполните поле");
            return false;
        } else if (n.length() < 9) {
            etNumber.setError("Заполните поле корректно");
            return false;
        } else {
            etNumber.setError(null);
            this.number = n;
            return true;
        }
    }

    private class EditNumberTask extends AsyncTask<Persons, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Пожалуйста, подождите");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected String doInBackground(Persons... params) {
            try {
                String s = apiProvider.updatePersonNumber(params[0].getId(), params[0].getNumber());
                return s;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
        }
    }
}