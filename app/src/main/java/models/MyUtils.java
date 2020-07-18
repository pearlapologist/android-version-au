package models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyUtils {


    public static Long convertDataToLong(String res) {
        long startDate = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(res);
            startDate = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDate;
    }

    public static Long convertDataToLong(int day, int month, int year) {
//        String res = day + "/" + month + "/" + year;
        String res = year + "-" + month + "-" + day;
        long startDate = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(res);

            startDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDate;
    }

    public static Long convertDataToLongWithRawString(String str) {
        String day = str.substring(8, 10);
        String month = str.substring(5, 7);
        String year = str.substring(0, 4);
        String res = year + "-" + month + "-" + day;
        long startDate = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(res);
            startDate = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public static String convertLongToDataString(Long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(new Date(date));
        return dateString;
    }

    public static String getCurrentDateInString() {
        Date date = new Date();
        String mDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return mDate;
    }


    public static Long getCurentDateInLong(){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dateobj = new Date();
            String data = df.format(dateobj);
            Long l = convertDataToLongWithRawString(data);
            return l;
    }


    public static Bitmap decodeByteToBitmap(byte[] image) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        return bitmap;
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
