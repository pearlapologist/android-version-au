package com.example.projectwnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Verify_numberActivity extends AppCompatActivity {

    Button btnSend;
    EditText etCode;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_number);
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        btnSend = findViewById(R.id.verify_num_btn);
        etCode = findViewById(R.id.verify_num_code);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "1234567";
                String number = "+77084067075";
              sendMsg(number, msg);

             /*   final Intent emailInten = new Intent(Intent.ACTION_SEND);
                emailInten.setType("plain/text");
                emailInten.putExtra(Intent.EXTRA_EMAIL, new String[] {"bayan.iskanova@gmail.com"});
                emailInten.putExtra(Intent.EXTRA_SUBJECT, "Подтверждение аккаунта" );
                emailInten.putExtra(Intent.EXTRA_TEXT, "ссылка на приложение" );
                startActivity(Intent.createChooser(emailInten, "Отправка письма..."));
           */
            }
        });
    }

    private void sendMsg(String number, String msg) {
        String SENT = "Message sent";
        String DELIVERED = "Message delivered";

        PendingIntent sendPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,new Intent(DELIVERED), 0);

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number, null, msg, sendPI, deliveredPI);
    }


}
