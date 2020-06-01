package models;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiProvider {


    public ApiProvider() {
    }

    public Persons getPerson(int personId) {
        try {
            String url = "http://10.0.2.2:8080/Test/TestJson?id=" + personId;//http://10.0.2.2:8080/Test/TestJson?id=
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);
            String name = jsonObject.getString("name");
            String lastName = jsonObject.getString("lastName");
            String number = jsonObject.getString("number");
            long created = jsonObject.getLong("created");
            int rating = jsonObject.getInt("rating");

            Persons p = new Persons();
            p.setName(name);
            p.setLastname(lastName);
            p.setNumber(number);
            p.setCreatedDate(created);
            p.setRating(rating);

           /* JSONArray arr = jsonObject.getJSONArray("services");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonService = arr.getJSONObject(i);
                int serviceid = jsonService.getInt("id");
                String title = jsonService.getString("title");
                Double price = jsonService.getDouble("price");
                Service service = new Service();
                service.setId(serviceid);
                service.setTitle(title);
                service.setPrice(price);

                executor.getServices().add(service);

            }*/
            return p;


        } catch (Exception e) {
        }
        return null;
    }

    private class MyJsonTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                String buffer = "";
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer += (line + "\n");

                }
                return buffer;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
