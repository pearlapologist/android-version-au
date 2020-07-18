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
    }

    //READ PERSON
    public Persons getPerson(int personId) throws Exception {
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
            int reviewscount = jsonObject.getInt("rcount");
            //photo

            Persons p = null;
            if(0<id){
                p=new Persons();
                p.setId(id);
                p.setName(name);
                p.setLastname(lastName);
                p.setNumber(number);
                Long cr = Long.valueOf(created);
                p.setCreatedDate(cr);
                p.setRating(rating);
                Long bt = Long.valueOf(birthday);
                p.setBirthday(bt);
                p.setReviewscount(reviewscount);
        }

            return p;
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
                JSONArray arr2 = jsonService.getJSONArray("services");
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
            String url = "https://aualmaty.herokuapp.com/GetExecutorsBySectionId?id=" + cId;
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

    public void updateExecutor(Executor executor) throws Exception {
            URL url = new URL("https://aualmaty.herokuapp.com/UpdateExecutor?id=" + executor.getId());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("sId", executor.getSectionId());
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
    public Section_of_services getSection(int sectionId) throws Exception {
        String url = "https://aualmaty.herokuapp.com/GetSection?id=" + sectionId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);
        String title = jsonObject.getString("title");

        Section_of_services division = new Section_of_services(sectionId, title);

        return division;
    }

    public ArrayList<String> getSectionListInString() throws Exception{
        ArrayList<String> divisions = new ArrayList<>();
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
    }

    public int getSectionIdByTitle(String title) throws Exception{
       String str = title.trim();
        str = str.replaceAll("\\s", "%20");
        String url = "https://aualmaty.herokuapp.com/GetSectionIdByTitle?title=" + str;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);
            int id = jsonObject.getInt("id");

            return id;
    }

    //endregion

    //region order
    //CREATE ORDER
    public void addOrder(Order order) throws Exception {
        try {
            URL url = new URL("https://aualmaty.herokuapp.com/AddOrder");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("customer", order.getCustomerId());
            jsonObject.put("title", order.getTitle());
            jsonObject.put("section", order.getSection());
            jsonObject.put("price", order.getPrice());
            jsonObject.put("desc", order.getDescription());
            String b = order.getDeadline() + "";
            jsonObject.put("deadline", b);

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
            order.setId(eid);
        } finally {
        }
    }

    //READ Order
    public Order getOrder(int orderId) throws Exception {
        Order order = null;
        try {
            String url = "https://aualmaty.herokuapp.com/GetOrder?id=" + orderId;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);
            int id = jsonObject.getInt("id");
            String title = jsonObject.getString("title");
            int customerId = jsonObject.getInt("customer");
            int sectionId = jsonObject.getInt("section");
            Double price = jsonObject.getDouble("price");
            String desc = jsonObject.getString("desc");
            String deadline = jsonObject.getString("dealine");
            String created = jsonObject.getString("created");

            Long cr = Long.valueOf(created);
            Long d = Long.valueOf(deadline);

            order = new Order(id, customerId, title, sectionId, price, desc, d, cr);
        } finally {
            return order;
        }
    }


    public ArrayList<Order> getOrders() throws Exception {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            String url = "https://aualmaty.herokuapp.com/GetAllOrders";
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);

            JSONArray arr = jsonObject.getJSONArray("orders");
            Order order = null;
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonOrder = arr.getJSONObject(i);
                int id = jsonOrder.getInt("id");
                String title = jsonOrder.getString("title");
                int customerId = jsonOrder.getInt("customer");
                int sectionId = jsonOrder.getInt("section");
                Double price = jsonOrder.getDouble("price");
                String desc = jsonOrder.getString("desc");
                String deadline = jsonOrder.getString("dealine");
                String created = jsonOrder.getString("created");

                Long cr = Long.valueOf(created);
                Long d = Long.valueOf(deadline);

                order = new Order(id, customerId, title, sectionId, price, desc, d, cr);

                orders.add(order);
            }
        } finally {
            return orders;
        }
    }

    public ArrayList<Order> getOrdersBySectionId(int rId) throws Exception {
        ArrayList<Order> orders = new ArrayList<>();
        String url = "https://aualmaty.herokuapp.com/GetOrdersBySectionId?id=" + rId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);

        JSONArray arr = jsonObject.getJSONArray("orders");
        Order order = null;
        for (int i = 0; i < arr.length(); i++) {
            JSONObject jsonOrder = arr.getJSONObject(i);
            int id = jsonOrder.getInt("id");
            String title = jsonOrder.getString("title");
            int customerId = jsonOrder.getInt("customer");
            int sectionId = jsonOrder.getInt("section");
            Double price = jsonOrder.getDouble("price");
            String desc = jsonOrder.getString("desc");
            String deadline = jsonOrder.getString("dealine");
            String created = jsonOrder.getString("created");

            Long cr = Long.valueOf(created);
            Long d = Long.valueOf(deadline);

            order = new Order(id, customerId, title, sectionId, price, desc, d, cr);
            orders.add(order);
        }
        return orders;
    }

    public ArrayList<Order> getPersonOrdersById(int personId) throws Exception {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            String url = "https://aualmaty.herokuapp.com/GetPersonOrdersById?id=" + personId;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();

            JSONObject jsonObject = new JSONObject(result);

            JSONArray arr = jsonObject.getJSONArray("orders");
            Order order;
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonOrder = arr.getJSONObject(i);
                int id = jsonOrder.getInt("id");
                String title = jsonOrder.getString("title");
                int customerId = jsonOrder.getInt("customer");
                int sectionId = jsonOrder.getInt("section");
                Double price = jsonOrder.getDouble("price");
                String desc = jsonOrder.getString("desc");
                String deadline = jsonOrder.getString("dealine");
                String created = jsonOrder.getString("created");

                Long cr = Long.valueOf(created);
                Long d = Long.valueOf(deadline);

                order = new Order(id, customerId, title, sectionId, price, desc, d, cr);

                orders.add(order);
            }

        } finally {
            return orders;
        }
    }

    //UPDATE ORDER
    public void updateOrder(Order order) throws Exception {
        try {
            URL url = new URL("https://aualmaty.herokuapp.com/UpdateOrder?id=" + order.getId());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("title", order.getTitle());
            jsonObject.put("section", order.getSection());
            jsonObject.put("price", order.getPrice());
            jsonObject.put("desc", order.getDescription());
            String b = order.getDeadline() + "";
            jsonObject.put("deadline", b);


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
        } finally {

        }
    }

    //DELETE ORDER
    public void deleteOrder(int orderId) throws Exception {
        try {
            String surl = "https://aualmaty.herokuapp.com/DeleteOrder?id=" + orderId;
            MyJsonTask task = new MyJsonTask();
            task.execute(surl);
        } finally {

        }
    }
    //endregion

    //region Notify

    //CREATE NOTIFY
    public void createNotify(Notify notify) throws Exception {
        try {
            URL url = new URL("https://aualmaty.herokuapp.com/CreateNotify");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();


            jsonObject.put("pId", notify.getPersonId());
            jsonObject.put("text", notify.getText());
            jsonObject.put("section", notify.getSectionId());
            jsonObject.put("src", notify.getSrcId());

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
            notify.setId(eid);
        } finally {
        }
    }

    //READ Notify

    public ArrayList<Notify> getAllPersonNotifies(int id) throws Exception {

        ArrayList<Notify> notifies = new ArrayList<>();
        try {
            String url = "https://aualmaty.herokuapp.com/GetAllPersonNotifies?id=" + id;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();
            JSONObject jsonObject = new JSONObject(result);

            JSONArray arr = jsonObject.getJSONArray("notifies");
            Notify notify = null;
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonOrder = arr.getJSONObject(i);
                int nId = jsonOrder.getInt("id");
                int personid = jsonOrder.getInt("pId");
                String text = jsonOrder.getString("text");
                int srcId = jsonOrder.getInt("srcId");
                int sectionId = jsonOrder.getInt("section");
                int status = jsonOrder.getInt("status");
                String created = jsonOrder.getString("created");
                Long cr = Long.valueOf(created);

                notify = new Notify(nId, personid, text, cr, sectionId, srcId, status);
                notifies.add(notify);
            }

        } finally {
            return notifies;
        }
    }

    public int getCountOfPersonNewNotifies(int id) throws Exception {
        ArrayList<Notify> notifies = new ArrayList<>();
        int count = -1;
        try {
            String url = "https://aualmaty.herokuapp.com/GetCountOfPersonNewNotifies?id=" + id;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
            String result = task.get();
            JSONObject jsonObject = new JSONObject(result);
            count = jsonObject.getInt("count");

        } finally {
            return count;
        }
    }

    public void setPersonNotifiesToChecked(int id) throws Exception {
        ArrayList<Notify> notifies = new ArrayList<>();
        try {
            String url = "https://aualmaty.herokuapp.com/SetPersonNotifiesToChecked?id=" + id;
            MyJsonTask task = new MyJsonTask();
            task.execute(url);
        } finally {
        }
    }
    //endregion

    //region Bookmark
    //CREATE BOOKMARK
    public void putExecutorInPersonBookmarks(int personId, int executorId) throws Exception {
        String url = "https://aualmaty.herokuapp.com/PutExecutorInPersonBookmarks?pId=" + personId + "&cId=" + executorId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
    }


    //READ BOOKMARK
    public void putOrderInPersonBookmarks(int personId, int orderId) throws Exception {
        String url = "https://aualmaty.herokuapp.com/PutOrderInPersonBookmarks?pId=" + personId + "&rId=" + orderId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
    }

    public Bookmarks getPersonBookmarkByOrderId(int personId, int orderId) throws Exception {
        Bookmarks bookmark = null;
        String url = "https://aualmaty.herokuapp.com/GetPersonBookmarkByOrderId?pId=" + personId + "&rId=" + orderId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);
        int id = jsonObject.getInt("id");

        if(id >0){
        bookmark = new Bookmarks(id, personId, 0, orderId);}

        return bookmark;
    }

    public Bookmarks getPersonBookmarkByExecutorId(int personId, int executorId) throws Exception {
        Bookmarks bookmark = null;
        String url = "https://aualmaty.herokuapp.com/GetPersonBookmarkByExecutorId?pId=" + personId + "&cId=" + executorId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);
        int id = jsonObject.getInt("id");

        bookmark = new Bookmarks(id, personId, executorId, 0);

        return bookmark;
    }

    public ArrayList<Bookmarks> getExecutorsListFromPersonBookmarks(int personId) throws Exception {
        ArrayList<Bookmarks> result = new ArrayList<>();
        String url = "https://aualmaty.herokuapp.com/GetExecutorsListFromPersonBookmarks?pId=" + personId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String sresult = task.get();

        JSONObject jsonObject = new JSONObject(sresult);

        JSONArray arr = jsonObject.getJSONArray("executors");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject jsonService = arr.getJSONObject(i);
            int id = jsonService.getInt("id");
            int executorId = jsonService.getInt("cId");
            Bookmarks bookm = new Bookmarks(id, personId, executorId, 0);

            result.add(bookm);
        }
        return result;
    }

    public ArrayList<Bookmarks> getOrdersListFromPersonBookmarks(int personId) throws Exception {
        ArrayList<Bookmarks> result = new ArrayList<>();
        String url = "https://aualmaty.herokuapp.com/GetOrdersListFromPersonBookmarks?pId=" + personId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String sresult = task.get();
        JSONObject jsonObject = new JSONObject(sresult);

        JSONArray arr = jsonObject.getJSONArray("orders");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject jsonService = arr.getJSONObject(i);
            int id = jsonService.getInt("id");
            int orderId = jsonService.getInt("rId");
            Bookmarks bookm = new Bookmarks(id, personId, 0, orderId);
            result.add(bookm);
        }
        return result;
    }

    //DELETE BOOKMARK
    public void deleteExecutorFromPersonBookmarks(int personId, int executorId) throws Exception {
        String url = "https://aualmaty.herokuapp.com/DeleteExecutorFromPersonBookmarks?pId=" + personId + "&cId=" + executorId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
    }

    public void deleteOrderFromPersonBookmarks(int personId, int orderId) throws Exception {
        String url = "https://aualmaty.herokuapp.com/DeleteOrderFromPersonBookmarks?pId=" + personId + "&rId=" + orderId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
    }
    //endregion

    //region Review
    //CREATE Review
    public void addReview(Review review) throws Exception {
        try {
            URL url = new URL("https://aualmaty.herokuapp.com/AddReview");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("executorId", review.getExecutrId());
            jsonObject.put("customerId", review.getCustomerId());
            jsonObject.put("text", review.getReview_text());
            jsonObject.put("assessment", review.getAssessment());

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
            review.setId(eid);
        } finally {
        }
    }

    //READ Review
    public Review getReview(int id) throws Exception {
        String url = "https://aualmaty.herokuapp.com/GetReview?id=" + id;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);
        int executorId = jsonObject.getInt("executorId");
        int customerId = jsonObject.getInt("customerId");
        String text = jsonObject.getString("text");
        int assessment = jsonObject.getInt("assessment");
        String created = jsonObject.getString("created");
        Long cr = Long.valueOf(created);

        Review review = new Review(id, executorId, customerId, text, assessment);
        review.setCreatedDate(cr);

        ArrayList<Answer> answers = new ArrayList<>();
        JSONArray arr = jsonObject.getJSONArray("answers");
        if (arr.length() > 0) {
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonService = arr.getJSONObject(i);
                int id2 = jsonService.getInt("id");
                int reviewId = jsonService.getInt("reviewId");
                int whoanswersId = jsonService.getInt("whoanswersId");
                int whoposted = jsonService.getInt("whoposted");
                String text2 = jsonService.getString("text");
                String created2 = jsonObject.getString("created");
                Long cr2 = Long.valueOf(created2);

                Answer answer = new Answer(id2, reviewId, whoanswersId, whoposted, text2, cr2);

                answers.add(answer);
            }
            review.setAnswers(answers);
        }

        return review;

    }

    public ArrayList<Review> getAllPersonReviewsById(int personId) throws Exception {
        ArrayList<Review> reviews = new ArrayList<>();

        String url = "https://aualmaty.herokuapp.com/GetAllPersonReviewByPersonId?pId=" + personId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);

        JSONArray arr = jsonObject.getJSONArray("reviews");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject jsonService = arr.getJSONObject(i);
            int id = jsonService.getInt("id");
            int executorId = jsonService.getInt("executorId");
            int customerId = jsonService.getInt("customerId");
            String text = jsonService.getString("text");
            int assessment = jsonService.getInt("assessment");
            String created = jsonService.getString("created");
            Long cr = Long.valueOf(created);

            Review review = new Review(id, executorId, customerId, text, assessment);
            review.setCreatedDate(cr);

            ArrayList<Answer> answers = new ArrayList<>();
            JSONArray arrAnswers = jsonService.getJSONArray("answers");
            if (arrAnswers.length() > 0) {
                for (int j = 0; j < arrAnswers.length(); j++) {
                    JSONObject jsonAnswer = arrAnswers.getJSONObject(j);
                    int id2 = jsonAnswer.getInt("id");
                    int reviewId = jsonAnswer.getInt("reviewId");
                    int whoanswersId = jsonAnswer.getInt("whoanswersId");
                    int whoposted = jsonAnswer.getInt("whoposted");
                    String text2 = jsonAnswer.getString("text");
                    String created2 = jsonAnswer.getString("created");
                    Long cr2 = Long.valueOf(created2);

                    Answer answer = new Answer(id2, reviewId, whoanswersId, whoposted, text2, cr2);
                    answers.add(answer);
                }
                review.setAnswers(answers);
            }

            reviews.add(review);
        }
        return reviews;
    }

    public ArrayList<Integer> getLeavedReviewPersonsIdList(int personId) throws Exception {
        ArrayList<Integer> arrId = new ArrayList<>();

        String url = "https://aualmaty.herokuapp.com/GetLeavedReviewPersonsIdList?pId=" + personId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);

        JSONArray arr = jsonObject.getJSONArray("ids");
        for (int i = 0; i < arr.length(); i++) {
            int id = arr.getInt(i);
            arrId.add(id);
        }
        return arrId;
    }

    //UPDATE REVIEW
    public void updateReview(Review review) throws Exception {
        URL url = new URL("https://aualmaty.herokuapp.com/UpdateReview?id=" + review.getId());

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("text", review.getReview_text());
        jsonObject.put("assessment", review.getAssessment());

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
    }

    //DELETE REVIEW
    public void deleteReview(int id, int executorId) throws Exception {
        String surl = "https://aualmaty.herokuapp.com/DeleteReview?id=" + id+"&executorId="+executorId;
        MyJsonTask task = new MyJsonTask();
        task.execute(surl);
    }

    //endregion


    //region Executor and Person
    //READ EXECUTORNPERSON
    public int getExecutorIdByPersonId(int personId) throws Exception {
        String url = "https://aualmaty.herokuapp.com/GetExecutorIdByPersonId?pId=" + personId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);
        int executorId = jsonObject.getInt("rId");
        return executorId;
    }

    //TODO: delete getPersonIdByExecutorId method

    public int getPersonIdByExecutorId(int executorId) throws Exception {
        String url = "https://aualmaty.herokuapp.com/GetPersonIdByExecutorId?cId=" + executorId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);
        int personId = jsonObject.getInt("personId");
        return personId;
    }

    //endregion

    //region OrderNPerson
    public int getCustomerIdByOrderId(int orderId) throws Exception {
        String url = "https://aualmaty.herokuapp.com/GetCustomerIdByOrderId?rId=" + orderId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);
        int personId = jsonObject.getInt("cId");
        return personId;
    }
    //endregion

    //region Response
    //CREATE RESPONSE
    public void addResponse(Response response) throws Exception {
        try {
            URL url = new URL("https://aualmaty.herokuapp.com/AddResponse");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("orderId", response.getOrderId());
            jsonObject.put("personId", response.getPersonId());
            jsonObject.put("text", response.getText());
            jsonObject.put("price", response.getPrice());

            OutputStream os = con.getOutputStream();
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder builder = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                builder.append(responseLine.trim());
            }
            String r = builder.toString();
            int eid = Integer.parseInt(r);
            response.setId(eid);
        } finally {
        }
    }

    //READ RESPONSE
    public int getPersonIdByResponseId(int responseId) throws Exception {
        String url = "https://aualmaty.herokuapp.com/GetPersonIdByResponseId?id=" + responseId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);
        int id = jsonObject.getInt("personId");
        return id;
    }

    public Response getResponse(int responseId) throws Exception {
        String url = "https://aualmaty.herokuapp.com/GetResponse?id=" + responseId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);
        int id = jsonObject.getInt("id");
        int orderId = jsonObject.getInt("orderId");
        int personId = jsonObject.getInt("personId");
        String text = jsonObject.getString("text");
        Double price = jsonObject.getDouble("price");
        String created = jsonObject.getString("created");
        Long cr = Long.valueOf(created);

        Response response = new Response(id, orderId, personId, text, price, cr);
        return response;
    }

    public ArrayList<Integer> getRespondedPersonsIdListByOrderIdId(int orderId) throws Exception {
        ArrayList<Integer> arrId = new ArrayList<>();

        String url = "https://aualmaty.herokuapp.com/GetRespondedPersonsIdListByOrderId?orderId="+orderId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);

        JSONArray arr = jsonObject.getJSONArray("persons");
        for (int i = 0; i < arr.length(); i++) {
            int id = arr.getInt(i);

            arrId.add(id);
        }
        return arrId;
    }

    public ArrayList<Response> getOrderResponsesById(int orderId) throws Exception {
        ArrayList<Response> responses = new ArrayList<>();
        String url = "https://aualmaty.herokuapp.com/GetOrderResponsesById?id=" + orderId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);

        JSONArray arr = jsonObject.getJSONArray("responses");
        Response response = null;
        for (int i = 0; i < arr.length(); i++) {
            JSONObject jsonResponse = arr.getJSONObject(i);
            int id = jsonResponse.getInt("id");
            int orderId2 = jsonResponse.getInt("orderId");
            int personId = jsonResponse.getInt("personId");
            String text = jsonResponse.getString("text");
            Double price = jsonResponse.getDouble("price");
            String created = jsonResponse.getString("created");
            Long cr = Long.valueOf(created);

            response = new Response(id, orderId2, personId, text, price, cr);
            responses.add(response);
        }
        return responses;
    }

    //UPDATE RESPONSE
    public void updateResponse(Response r) throws Exception {
        URL url = new URL("https://aualmaty.herokuapp.com/UpdateResponse?id=" + r.getId());

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("text", r.getText());
        jsonObject.put("price", r.getPrice());

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
    }

    //DELETE RESPONSE
    public void deleteResponse(int id) throws Exception {
        String surl = "https://aualmaty.herokuapp.com/DeleteResponse?id=" + id;
        MyJsonTask task = new MyJsonTask();
        task.execute(surl);
    }
    //endregion

    //region Answer
    //CREATE ANSWER
    public void addAnswer(Answer answer) throws Exception {
        try {
            URL url = new URL("https://aualmaty.herokuapp.com/AddAnswer");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("reviewId", answer.getReviewId());
            jsonObject.put("whoanswersId", answer.getWhoanswersId());
            jsonObject.put("whopostedId", answer.getWhopostedId());
            jsonObject.put("text", answer.getText());

            OutputStream os = con.getOutputStream();
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder builder = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                builder.append(responseLine.trim());
            }
            String r = builder.toString();
            int eid = Integer.parseInt(r);
            answer.setId(eid);
        } finally {
        }
    }

    //READ ANSWER
    //TODO: удалить, если не будет нигде использоватться
    public Answer getAnswer(int id) throws Exception {
        String url = "https://aualmaty.herokuapp.com/GetAnswer?id=" + id;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);
        int reviewId = jsonObject.getInt("reviewId");
        int whoanswersId = jsonObject.getInt("whoanswersId");
        int whoposted = jsonObject.getInt("whopostedId");
        String text = jsonObject.getString("text");
        String created = jsonObject.getString("created");
        Long cr = Long.valueOf(created);

        Answer answer = new Answer(id, reviewId, whoanswersId, whoposted, text, cr);
        return answer;
    }

    public ArrayList<Answer> getAllReviewAnswersById(int reviewId) throws Exception {
        ArrayList<Answer> answers = new ArrayList<>();
        String url = "https://aualmaty.herokuapp.com/GetAllReviewAnswersById?id=" + reviewId;
        MyJsonTask task = new MyJsonTask();
        task.execute(url);
        String result = task.get();

        JSONObject jsonObject = new JSONObject(result);

        JSONArray arr = jsonObject.getJSONArray("answers");
        Answer answer = null;
        for (int i = 0; i < arr.length(); i++) {
            JSONObject jsonResponse = arr.getJSONObject(i);
            int answerId = jsonResponse.getInt("id");
            int whoanswersId = jsonResponse.getInt("whoanswersId");
            int whoposted = jsonResponse.getInt("whopostedId");
            String text = jsonResponse.getString("text");
            String created = jsonResponse.getString("created");
            Long cr = Long.valueOf(created);

            answer = new Answer(answerId, reviewId, whoanswersId, whoposted, text, cr);
            answers.add(answer);
        }
        return answers;
    }

    //UPDATE ANSWER
    public void updateAnswer(Answer answer) throws Exception {
        URL url = new URL("https://aualmaty.herokuapp.com/UpdateAnswer?id=" + answer.getId());

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("text", answer.getText());

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
    }

    //DELETE ANSWER
    public void deleteAnswer(int id) throws Exception {
        String surl = "https://aualmaty.herokuapp.com/DeleteAnswer?id=" + id;
        MyJsonTask task = new MyJsonTask();
        task.execute(surl);
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
