package models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;

import static java.net.Proxy.Type.HTTP;

public class ApiProvider {


    public ApiProvider() {
    }
    //region Person

    public Persons addPerson(Persons person) throws Exception {
        try {
            URL url = new URL("https://aualmaty.herokuapp.com/AddPerson");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("name", person.getName());
            jsonObject.put("lastname", person.getLastname());
            jsonObject.put("numb", person.getNumber());
            jsonObject.put("passwd", person.getPasswd());
            jsonObject.put("birth", person.getBirthday());

            OutputStream os = con.getOutputStream();
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());

            return person;
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return null;
    }

    public Persons getPerson(int personId) {
        try {
            String url = "http://10.0.2.2:8080/Test/GetPersonById?id=" + personId;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);
            Persons p = getPersonFromJson(jsonObject);
            return p;

        } catch (Exception e) {
        }
        return null;
    }

    public Persons getPersonByNumbNPasswd(String passwd, String number) {
        Persons person = new Persons();
        try {
            String url = "https://aualmaty.herokuapp.com/getPersonByNumbNPasswd?num=" + number + "&passwd=" + passwd;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);

            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);

                int msg_id = jsonObject.getInt("msg_id");
                int person1Id = jsonService.getInt("person_id1");
                int whosendsId = jsonService.getInt("person_id2");
                String text = jsonService.getString("text");
                Message msg = new Message(msg_id, person1Id, whosendsId, text);

            return messages;

            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);
            Persons p = getPersonFromJson(jsonObject);
            return p;
        } catch (Exception e) {
        }
        return null;
    }

    public Persons getPersonFromJson(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("pId");
        String name = jsonObject.getString("pName");
        String lastName = jsonObject.getString("pLastName");
        String number = jsonObject.getString("pNumber");
        String created = jsonObject.getString("pCreated");
        int rating = jsonObject.getInt("pRating");
        String passwd = jsonObject.getString("pPasswd");
        //photo

        Persons p = new Persons(id, name, lastName, passwd, number, rating, created);
        return p;
    }

    public void updatePerson(Persons person) throws Exception {
        try {
            URL url = new URL("https://aualmaty.herokuapp.com/UpdatePersonData?id=" + person.getId());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("pName", person.getName());
            jsonObject.put("pLastname", person.getLastname());
            jsonObject.put("pBirthday", person.getBirthday());

            OutputStream os = con.getOutputStream();
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
    }

    public void setPersonIsExecutorField(int personId, Boolean b) {
        int value;
        if (b == true) {
            value = 1;
        } else {
            value = 0;
        }
        try {
            String surl = "https://aualmaty.herokuapp.com/SetPersonIsExecutorField?id=" + personId + "&b=" + value;
            URL url = new URL(surl);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("pId", personId);
            jsonObject.put("pval", value);

            OutputStream os = con.getOutputStream();
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();

        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
    }
    //endregion


    public Message addMessage(Message message) throws Exception {
        try {
            URL url = new URL("https://aualmaty.herokuapp.com/AddMessage");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("msg", message.getText());
            jsonObject.put("p1id", message.getPersonId());
            jsonObject.put("p2id", message.getWhosends());

            OutputStream os = con.getOutputStream();
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());

            return message;
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return null;
    }


    public ArrayList<Integer> getAllPersonConversations(int id) {
        ArrayList<Integer> personsId = new ArrayList<>();
        try {
            String url = "https://aualmaty.herokuapp.com/GetAllPersonConversationsById?id=" + id; // + personId;//http://10.0.2.2:8080/Test/TestJson?id=
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);

            JSONArray arr = jsonObject.getJSONArray("persons");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonService = arr.getJSONObject(i);
                int person1Id = jsonService.getInt("person_id1");
                personsId.add(person1Id);
            }
            return personsId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Message> getAllConvrstnMessagesByPersonsId(int id, int curPersonId) {
        ArrayList<Message> messages = new ArrayList<>();
        try {
            String url = "https://aualmaty.herokuapp.com/getConversationAllMessages?sndr=" + id + "&adrs=" + curPersonId;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);

            JSONArray arr = jsonObject.getJSONArray("messages");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonService = arr.getJSONObject(i);
                int msg_id = jsonService.getInt("msg_id");
                int person1Id = jsonService.getInt("person_id1");
                int whosendsId = jsonService.getInt("person_id2");
                String text = jsonService.getString("text");
                Message msg = new Message(msg_id, person1Id, whosendsId, text);
                messages.add(msg);
            }
            return messages;
        } catch (Exception e) {
            e.printStackTrace();
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
