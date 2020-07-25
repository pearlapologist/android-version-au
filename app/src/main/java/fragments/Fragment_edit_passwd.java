package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.projectwnavigation.R;
import com.google.android.material.textfield.TextInputLayout;

import models.ApiProvider;
import models.MyDataProvider;
import models.Persons;

public class Fragment_edit_passwd extends Fragment implements View.OnClickListener {
    MyDataProvider provider;
    ApiProvider apiProvider;
    Context context;
    Persons loggedInPerson;
    TextInputLayout oldpasswd, passwd, confirmpasswd;
    Button btn_save, btn_cancel;
    ProgressDialog pd;

    String txtpasswd = null;
    String oldtxtpasswd = null;

    public Fragment_edit_passwd() {
    }

    public Fragment_edit_passwd(Context context) {
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
        return inflater.inflate(R.layout.fragment_edit_passwd, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        oldpasswd = view.findViewById(R.id.frg_edit_passwd_et_oldpasswd);
        passwd = view.findViewById(R.id.frg_edit_passwd_et_newpasswd);
        confirmpasswd = view.findViewById(R.id.frg_edit_passwd_et_newpasswd);

        btn_save = view.findViewById(R.id.frg_edit_passwd_btn_save);
        btn_cancel = view.findViewById(R.id.frg_edit_passwd_btn_cancel);

        loggedInPerson = provider.getLoggedInPerson();

        btn_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        super.onViewCreated(view, savedInstanceState);
    }

    private boolean validatePasswd() {
        String pswd = passwd.getEditText().getText().toString().trim();
        String p = "^" +
                "(?=.*[a-zA-Z])" +    //any letter
                "(?=.*[а-яА-Я])" +
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (pswd.isEmpty()) {
            passwd.setError("Заполните поле");
            return false;
        } else if (!pswd.matches(p)) {
            passwd.setError("Пароль слишком легкий");
            return false;
        } else {
            passwd.setError(null);
            passwd.setErrorEnabled(false);
            this.txtpasswd = pswd;
            return true;
        }
    }

    private boolean validateConfirmField() {
        String p = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        String confirm = confirmpasswd.getEditText().getText().toString().trim();

        if (confirm.isEmpty()) {
            confirmpasswd.setError("Заполните поле");
            return false;
        } else if (!(txtpasswd.equals(confirm))) {
            confirmpasswd.setError("Пароли не совпадают");
            return false;
        } else {
            confirmpasswd.setError(null);
            confirmpasswd.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateOldPasswd() {
        String oldpswd = oldpasswd.getEditText().getText().toString().trim();
        if (oldpswd.isEmpty()) {
            oldpasswd.setError("Заполните поле");
            return false;
        } else {
            oldpasswd.setError(null);
            oldpasswd.setErrorEnabled(false);

            this.oldtxtpasswd = oldpswd;
            return true;
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frg_edit_passwd_btn_cancel) {
            Fragment_settings_security fragment_security = new Fragment_settings_security(context);
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.fram, fragment_security).commit();
        } else if (v.getId() == R.id.frg_edit_passwd_btn_save) {
            if (!validateOldPasswd() || !validatePasswd() || !validateConfirmField()) {
                return;
            }

            try {
                EditPasswdTask task = new EditPasswdTask();

                task.execute(txtpasswd, oldtxtpasswd);
                String result = task.get();
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Ошибка : 3", Toast.LENGTH_SHORT).show();
            }

            Fragment_settings_security fragment_security = new Fragment_settings_security(context);
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.fram, fragment_security).commit();
        }
    }

    private class EditPasswdTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Пожалуйста, подождите");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String s = apiProvider.updatePersonPsswd(provider.getLoggedInPerson().getId(), params[0], params[1]);
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