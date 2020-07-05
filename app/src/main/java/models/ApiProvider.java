package models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;

import static java.net.Proxy.Type.HTTP;

public class ApiProvider {


    public ApiProvider() {
    }
    //region Person

    //CREATE PERSON
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
            String b = person.getBirthday() + "";
            jsonObject.put("birth", b);

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

            return person;
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return null;
    }

    //READ PERSON
    public Persons getPerson(int personId) {
        try {
            String url = "https://aualmaty.herokuapp.com/GetPersonById?id=" + personId;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            String lastName = jsonObject.getString("lastname");
            String number = jsonObject.getString("number");
            String created = jsonObject.getString("created");
            int rating = jsonObject.getInt("rating");
            String birthday = jsonObject.getString("birth");
            //photo

            Persons p = new Persons();
            p.setId(id);
            p.setName(name);
            p.setLastname(lastName);
            p.setNumber(number);
            Long cr = Long.valueOf(created);
            p.setCreatedDate(cr);
            p.setRating(rating);
            Long bt = Long.valueOf(birthday);
            p.setBirthday(bt);
            return p;

        } catch (Exception e) {
            Log.e("getPerson", e.getMessage());
        }
        return null;
    }

    public Persons getPersonByNumbNPasswd(String passwd, String number) {
        try {
            URL url = new URL("https://aualmaty.herokuapp.com/getPersonByNumbNPasswd");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);


            JSONObject jsonObject = new JSONObject();

            jsonObject.put("numb", number);
            jsonObject.put("passwd", passwd);

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

            JSONObject jsonObject2 = new JSONObject(response.toString());

            int id = jsonObject2.getInt("id");
            String name = jsonObject2.getString("name");
            String lastName = jsonObject2.getString("lastname");
            String numbers = jsonObject2.getString("number");
            String created = jsonObject2.getString("created");
            int rating = jsonObject2.getInt("rating");
            String birthday = jsonObject2.getString("birth");
            //photo

            Persons p = new Persons();
            p.setId(id);
            p.setName(name);
            p.setLastname(lastName);
            p.setNumber(numbers);
            p.setCreatedDate(Long.valueOf(created));
            p.setRating(rating);
            p.setBirthday(Long.valueOf(birthday));
            return p;


        } catch (Exception e) {
            Log.e("getPersonByNumbNPasswd", e.toString());
        }
        return null;
    }

    public boolean getPersonIsExecutorField(int personId) {
        Boolean b = false;
        try {
            String url = "https://aualmaty.herokuapp.com/GetPersonIsExecutorFiels?id=" + personId;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);
            int v = jsonObject.getInt("value");
            if (v == 1) {
                b = true;
            }
        } catch (Exception e) {
            Log.e("getPrsIsExecut", e.getMessage());
        }
        return b;
    }

    public int getPersonRatingById(int id) {
        int rating = -1;
        try {
            String url = "https://aualmaty.herokuapp.com/GetPersonRatingById?id=" + id;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);
            rating = jsonObject.getInt("rating");
        } catch (Exception e) {
            Log.e("getRating", e.getMessage());
        }
        return rating;
    }

    //UPDATE PERSON
    public void updatePerson(Persons person) {
        try {
            URL url = new URL("https://aualmaty.herokuapp.com/UpdatePersonById?id=" + person.getId());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("pName", person.getName());
            jsonObject.put("pLastname", person.getLastname());
            String b = person.getBirthday() + "";
            jsonObject.put("pBirthday", b);

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
            String r = response.toString();
            Log.i("updatePerson", r);
        } catch (Exception e) {
            Log.e("updatePerson", e.getMessage());
        }
    }

    public void updatePersonRatingById(int personId) {
        try {
            String surl = "https://aualmaty.herokuapp.com/UpdatePersonRatingById?id=" + personId;
            MyJsonTask task = new MyJsonTask();
            task.execute(surl);
        } catch (Exception e) {
            Log.e("updatePersonRatingById", e.getMessage());
        }
    }

    //DELETE PERSON
    public void deletePerson(int personId) {
        try {
            String surl = "https://aualmaty.herokuapp.com/DeletePersonById?id=" + personId;
            MyJsonTask task = new MyJsonTask();
            task.execute(surl);
        } catch (Exception e) {
            Log.e("deletePerson", e.getMessage());
        }
    }

    //endregion


    //region Executor
    //CREATE EXECUTOR
    public void addExecutor(Executor executor) {
        try {

            URL url = new URL("https://aualmaty.herokuapp.com/AddExecutor");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("personId", executor.getPersonId());
            jsonObject.put("sectionId", executor.getSectionId());
            jsonObject.put("spec", executor.getSpecialztn());
            jsonObject.put("desc", executor.getDescriptn());
            JSONArray services = new JSONArray();
            for (Service s : executor.getServices()) {
                JSONObject service = new JSONObject();
                service.put("title", s.getTitle());
                service.put("price", s.getPrice());
                services.put(service);
            }
            jsonObject.put("services", services);
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
            String r = response.toString();
            int eid = Integer.parseInt(r);
            executor.setId(eid);
        } catch (Exception e) {
            Log.e("addExecutor", e.getMessage());
        }
    }

    //READ EXECUTOR
    public Executor getExecutor(int id) {
        try {
            String url = "https://aualmaty.herokuapp.com/GetExecutor?id=" + id;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);
            int tid = jsonObject.getInt("id");
            int personId = jsonObject.getInt("pId");
            int sectionId = jsonObject.getInt("cId");
            String spec = jsonObject.getString("spec");
            String desc = jsonObject.getString("desc");

            Executor executor = new Executor(tid, personId, sectionId, spec, desc);

            if (executor.getServices() != null) {
                executor.getServices().clear();
            } else {
                ArrayList<Service> services = new ArrayList<>();
                JSONArray arr = jsonObject.getJSONArray("services");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonService = arr.getJSONObject(i);
                    int id2 = jsonService.getInt("sId");
                    String title = jsonService.getString("sTitle");
                    Double price = jsonService.getDouble("sPrice");
                    Service service = new Service(id2, title, price);
                    services.add(service);
                }
                executor.setServices(services);
            }

            return executor;

        } catch (Exception e) {
            Log.e("getPerson", e.getMessage());
        }
        return null;
    }

    public ArrayList<Executor> getAllExecutors() {
        ArrayList<Executor> executors = new ArrayList<>();
        try {
            String url = "https://aualmaty.herokuapp.com/GetAllExecutors";
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);

            JSONArray arr = jsonObject.getJSONArray("executors");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonService = arr.getJSONObject(i);
                int id = jsonService.getInt("id");
                int personId = jsonService.getInt("pId");
                int sectionId = jsonService.getInt("cId");
                String desc = jsonService.getString("desc");
                String spec = jsonService.getString("spec");
                Executor executor = new Executor(id, personId, sectionId, spec, desc);


                ArrayList<Service> services = new ArrayList<>();
                JSONArray arr2 = jsonObject.getJSONArray("services");
                for (int k = 0; k < arr2.length(); k++) {
                    JSONObject jsonService2 = arr2.getJSONObject(k);
                    int id2 = jsonService2.getInt("sId");
                    String title = jsonService2.getString("sTitle");
                    Double price = jsonService2.getDouble("sPrice");
                    Service service = new Service(id2, title, price);
                    services.add(service);

                    executor.setServices(services);
                }

                executors.add(executor);
            }
            return executors;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Executor> getExecutorsBySectionId(int cId) {
        ArrayList<Executor> executors = new ArrayList<>();
        try {
            String url = "https://aualmaty.herokuapp.com/GetExecutorsBySectionId";
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);

            JSONArray arr = jsonObject.getJSONArray("executors");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonService = arr.getJSONObject(i);
                int id = jsonService.getInt("id");
                int personId = jsonService.getInt("pId");
                int sectionId = jsonService.getInt("cId");
                String desc = jsonService.getString("desc");
                String spec = jsonService.getString("spec");
                Executor executor = new Executor(id, personId, sectionId, spec, desc);

                if (executor.getServices() != null) {
                    executor.getServices().clear();
                } else {
                    ArrayList<Service> services = new ArrayList<>();
                    JSONArray arr2 = jsonObject.getJSONArray("services");
                    for (int k = 0; k < arr2.length(); k++) {
                        JSONObject jsonService2 = arr2.getJSONObject(k);
                        int id2 = jsonService2.getInt("sId");
                        String title = jsonService2.getString("sTitle");
                        Double price = jsonService2.getDouble("sPrice");
                        Service service = new Service(id2, title, price);
                        services.add(service);
                    }
                    executor.setServices(services);
                }
                executors.add(executor);
            }
            return executors;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateExecutor(Executor executor) {
        try {
            URL url = new URL("https://aualmaty.herokuapp.com/UpdateExecutor?id=" + executor.getId());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("cId", executor.getSectionId());
            jsonObject.put("spec", executor.getSpecialztn());
            jsonObject.put("desc", executor.getDescriptn());
            JSONArray services = new JSONArray();
            for (Service s : executor.getServices()) {
                JSONObject service = new JSONObject();
                service.put("sTitle", s.getTitle());
                service.put("sPrice", s.getPrice());
                services.put(service);
            }
            jsonObject.put("services", services);


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
            String r = response.toString();
            Log.i("updateExecutor", r);
        } catch (Exception e) {
            Log.e("updateExecutor", e.getMessage());
        }
    }

    //DELETE EXECUTOR
    public void deleteExecutor(int id) {
        try {
            String surl = "https://aualmaty.herokuapp.com/deleteExecutor?id=" + id;
            MyJsonTask task = new MyJsonTask();
            task.execute(surl);
        } catch (Exception e) {
            Log.e("deleteExecutor", e.getMessage());
        }
    }

    //endregion


 /*   public void loadExecutorServices(Executor executor) {
        try {
            String url = "https://aualmaty.herokuapp.com/LoadExecutorServices?id=" + executor.getId();
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);
            if (executor.getServices() != null) {
                executor.getServices().clear();
            } else {
                ArrayList<Service> services = new ArrayList<>();
                JSONArray arr = jsonObject.getJSONArray("services");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonService = arr.getJSONObject(i);
                    int id = jsonService.getInt("id");
                    String title = jsonService.getString("title");
                    Double price = jsonService.getDouble("price");
                    Service service = new Service(id, title, price);
                    services.add(service);
                }
                executor.setServices(services);
            }

        } catch (Exception e) {
            Log.e("loadServices", e.getMessage());
        }
    }*/

    //region section
    public Section_of_services getSection(int sectionId) {
        try {
            String url = "https://aualmaty.herokuapp.com/GetSection?id=" + sectionId;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);
            String title = jsonObject.getString("title");

            Section_of_services division = new Section_of_services(sectionId, title);

            return division;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getSection", e.getMessage());
        }
        return null;
    }

    public ArrayList<String> getSectionListInString() {
        ArrayList<String> divisions = new ArrayList<>();
        try {
            String url = "https://aualmaty.herokuapp.com/GetSectionListInString";
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);

            JSONArray arr = jsonObject.getJSONArray("sections");
            for (int i = 0; i < arr.length(); i++) {
                String division = arr.getString(i);

                divisions.add(division);
            }
            return divisions;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getSectionListInString", e.getMessage());
        }
        return null;
    }

    public int getSectionIdByTitle(String title) {
        try {
            String url = "https://aualmaty.herokuapp.com/GetSectionIdByTitle?title=" + title;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);
            int id = jsonObject.getInt("id");

            return id;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getSectionIdByTitle", e.getMessage());
        }
        return -1;
    }

    //endregion


    //region message
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
    //endregion

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
