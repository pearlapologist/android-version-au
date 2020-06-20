package com.example.projectwnavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import fragments.Conversation_messages_adapter;
import models.ApiProvider;
import models.Message;
import models.MyDataProvider;

public class Conversation_view_activity extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<Message> messages;
    Conversation_messages_adapter messages_adapter;
    Button btnSend;
    MyDataProvider myProvider;
    ApiProvider apiProvider;
    TextView name;
    EditText etText;
    int personId;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_view);
        etText = findViewById(R.id.act_conversation_etText);
        rv = findViewById(R.id.act_conversation_rv);
        btnSend = findViewById(R.id.act_conversation_btnSend);
        name = findViewById(R.id.act_conversation_tvname);
        myProvider = new MyDataProvider(this);
        apiProvider = new ApiProvider();

        if (getIntent().hasExtra("msg_adapter")) {
            personId = getIntent().getIntExtra("msg_adapter", -1);
        }
        insertMessages();

        messages_adapter = new Conversation_messages_adapter(this, messages);
        rv.setAdapter(messages_adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        messages_adapter.notifyDataSetChanged();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stext = etText.getText().toString().trim();
                Message msg = new Message();
                msg.setText(stext);
                msg.setWhosends(myProvider.getLoggedInPerson().getId());
                msg.setPersonId(personId);

                SendMessageTask task = new SendMessageTask();
                task.execute(msg);

                etText.setText("");
                messages_adapter.notifyDataSetChanged();

            }
        });

    }

    void insertMessages() {
        try {
            if (messages != null) {
                messages.clear();
            }
            messages = apiProvider.getAllConvrstnMessagesByPersonsId(myProvider.getLoggedInPerson().getId(), personId);
        } catch (Exception e) {
            Log.e("insertExecutorsArray", e.getMessage());
        }
        if (messages == null || messages.size() <= 0) {
            Toast.makeText(this, "Пока что не было создано специалистов", Toast.LENGTH_SHORT).show();
        }
    }

    private class SendMessageTask extends AsyncTask<Message, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Conversation_view_activity.this);
            pd.setMessage("Пожалуйста, подождите");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Message... params) {
            try {
                apiProvider.addMessage(params[0]);
                messages_adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
        }
    }
}
