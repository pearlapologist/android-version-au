package com.example.projectwnavigation;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import models.DbHelper;
import models.Executor;
import models.MyDataProvider;
import models.Persons;

public class MainActivity extends AppCompatActivity {
//    TextView lbl;
//    DbHelper db;
//    MyDataProvider provider;
//TODO: delete this activity
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        lbl = (TextView) findViewById(R.id.txtlbl);
//        db = new DbHelper(getApplicationContext());
//        provider = new MyDataProvider(db);
//
//        ArrayList<Persons> persons = provider.getPersons();
//        String str = "";
//        for (Persons s : persons) {
//            str += s.getId() + " " + s.getName() + " " + s.getPasswd()+" "
//                    + s.getCreatedDate() + "\n";
//        }
//
//        ArrayList<Executor> executors = provider.getExecutors();
//        String str2 = "";
//        for (Executor e : executors) {
//            str2 += e.getId() + " " + e.getPersonId() + " " + e.getSectionId() +" "
//                    + e.getSpecialztn()+" " + e.getDescriptn() + "\n";
//        }
//
//        lbl.setText(str2);
//        //  Persons persons1 = new Persons();
//        //serviceses.
//        // person1.
//    }
//
//    public void btnClicked(View v) {
//        Random rand = new Random();
//        Persons person1 = new Persons(1, "Маша", "абвгде" + rand.nextInt(60),
//                Calendar.getInstance());
//        Executor executor1 = new Executor(1, 1, 4,
//                "Фотограф", "типа текст" + rand.nextInt(60));
//        provider.addPerson(person1);
//        provider.addExecutor(executor1);
//
//        person1 = new Persons(2, "Дмитрий", "аабвгде" + rand.nextInt(60),
//                Calendar.getInstance());
//        executor1 = new Executor(2, 2, 1,
//                "Грузчик", "типа текст" + rand.nextInt(60));
//        provider.addPerson(person1);
//        provider.addExecutor(executor1);
//
//        person1 = new Persons(3, "Ольга", "абтвгде" + rand.nextInt(60),
//                Calendar.getInstance());
//        executor1 = new Executor(3, 3, 6,
//                "Репетитор", "типа текст" + rand.nextInt(60));
//        provider.addPerson(person1);
//        provider.addExecutor(executor1);
//
//        person1 = new Persons(4, "Алексей", "абвггде" + rand.nextInt(60),
//                Calendar.getInstance());
//        executor1 = new Executor(4, 4, 10,
//                "Монтажник", "типа текст" + rand.nextInt(60));
//        provider.addPerson(person1);
//        provider.addExecutor(executor1);
//    }


}
