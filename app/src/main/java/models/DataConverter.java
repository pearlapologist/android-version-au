package models;

import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataConverter {


    public static Long convertDataToLong(String res) {
        long startDate = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(res);

            startDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDate;
    }

    public static Long convertPntdStringToLong(String res) {
        long startDate = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date date = sdf.parse(res);
            startDate = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDate;
    }

    public static Long convertDataToLong(int day, int month, int year) {
        String res = day + "/" + month + "/" + year;
        long startDate = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(res);

            startDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDate;
    }

    public static Long convertDataToLongWithRawString(String str) {
        String day = str.substring(0, 2);
        String month = str.substring(3, 5);
        String year = str.substring(6, 10);
        String res = day + "/" + month + "/" + year;
        long startDate = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(res);

            startDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDate;
    }

    public static String convertLongToDataString(Long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(date));
        return dateString;
    }

    public static String getCurrentDateInString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = new Date();
        String data = df.format(dateobj);
        return data;
    }


    public static Long getCurentDateInLong(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = new Date();
        String data = df.format(dateobj);
        Long l = convertDataToLongWithRawString(data);
        return l;
    }
}
