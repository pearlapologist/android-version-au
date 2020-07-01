package models;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

//import android.content.Context.MODE_PRIVATE;

public class MyDataProvider {

    private static final String KEY_SHARED_PREF = "sharedPrefs";
    private static final String KEY_LOGGED_ID = "id";


    public final int READ_GALLERY = 999;

    private DbHelper db;
    private Context context;
    ApiProvider apiProvider = new ApiProvider();


    public MyDataProvider(Context context) {
        this.context = context;
        this.db = new DbHelper(context);
    }

    public static final String TABLE_PERSONS = "persons";
    public static final String TABLE_ORDERS = "orders";
    public static final String TABLE_ORDERNPERSON = "ordernperson";
    public static final String TABLE_EXECUTOR = "executor";
    // public static final String TABLE_EXECUTORS = "executors";
    public static final String TABLE_SERVICE = "service";
    //  public static final String TABLE_SERVICES = "services";
    // public static final String TABLE_SECTION = "section";
    public static final String TABLE_SECTIONS = "sections";
    public static final String TABLE_BOOKMARKS = "bookmarks";
    public static final String TABLE_REVIEWS = "reviews";
    public static final String TABLE_EXECUTORNSERVICES = "executornservices";
    public static final String TABLE_NOTIFY = "notify";
    public static final String TABLE_EXECUTORNPERSON = "executor_person";
    public static final String TABLE_RESPONSES = "responses";
    public static final String TABLE_ANSWERS = "answers";
    public static final String TABLE_REVIEWNANSWERS = "reviewnanswers";


    public static final String KEY_PERSON_ID = "person_id";
    public static final String KEY_ORDER_ID = "order_id";
    public static final String KEY_EXECUTOR_ID = "executor_id";
    public static final String KEY_SERVICE_ID = "service_id";
    public static final String KEY_SECTION_ID = "section_id";
    public static final String KEY_EXECUTORNSERVICES_ID = "executnservice_id";
    public static final String KEY_NOTIFY_ID = "notify_id";
    public static final String KEY_ORDERNPERSON_PART_ID = "ordernperson_id";
    public static final String KEY_EXECUTORNPERSON_ID = "executnperson_id";
    public static final String KEY_RESPONSES_ID = "responses_id";
    public static final String KEY_REVIEWNANSWERS_ID = "reviewnanswers_id";


    public static final String KEY_PERSON_NAME = "person_name";
    public static final String KEY_PERSON_LASTNAME = "person_lastname";
    public static final String KEY_PERSON_PASSWD = "person_passw";
    public static final String KEY_PERSON_CREATED_DATE = "person_created_date";
    public static final String KEY_PERSON_NUMBER = "person_number";
    public static final String KEY_PERSON_PHOTO = "person_photo";
    public static final String KEY_PERSON_RATING = "person_rating";
    public static final String KEY_PERSON_BIRTHDAY = "person_birthday";
    public static final String KEY_PERSON_ISEXECUTOR = "isExecutor";


    public static final String KEY_ORDER_TITLE = "order_title";
    public static final String KEY_ORDER_SECTION_ID = "order_section_id";
    public static final String KEY_ORDER_PRICE = "order_price";
    public static final String KEY_ORDER_CREATED_DATE = "order_created_date";
    public static final String KEY_ORDER_DEADLINE = "order_deadline";
    public static final String KEY_ORDER_DESCRIPTION = "order_description";
    public static final String KEY_ORDER_ISANONNOTE = "order_isanonymnote";
    public static final String KEY_ORDER_CUSTOMER_ID = "customer_id";


    public static final String KEY_EXECUTOR_PERSON_ID = "executor_personId";
    public static final String KEY_EXECUTOR_SECTION_ID = "executor_sectionId";
    public static final String KEY_EXECUTOR_SPECIALIZATION = "executor_specialization";
    public static final String KEY_EXECUTOR_DESCRIPTION = "executor_description";
    public static final String KEY_EXECUTOR_COVERPHOTO = "executor_coverphoto";


    // public static final String KEY_SERVICE_PERSON_ID = "person_id";
    public static final String KEY_SERVICE_TITLE = "service_title";
    public static final String KEY_SERVICE_PRICE = "service_price";


    public static final String KEY_SECTION_TITLE = "section_title";

    public static final String KEY_BOOKMARK_PART_ID = "bookm_id";
    public static final String KEY_BOOKMARK_PERSON_ID = "bookm_person_id";
    public static final String KEY_BOOKMARK_EXECUTOR_ID = "bookm_executor_id";
    public static final String KEY_BOOKMARK_ORDER_ID = "bookm_order_id";


    public static final String KEY_ORDERNPERSON_CUSTOMER_ID = "ordernpers_customer_id";
    public static final String KEY_ORDERNPERSON_ORDER_ID = "ordernpers_order_id";
    public static final String KEY_ORDERNPERSON_EXECUTOR_ID = "ordernpers_executor_id";
    public static final String KEY_ORDERNPERSON_STATUS = "ordernpers_status";

    public static final String KEY_REVIEW_PART_ID = "review_id";
    public static final String KEY_REVIEW_EXECUTOR_ID = "review_executor_id";
    public static final String KEY_REVIEW_CUSTOMER_ID = "review_customer_id";
    public static final String KEY_REVIEW_REVIEW_TEXT = "review_text";
    public static final String KEY_REVIEW_ASSESSMENT = "review_assessment";
    public static final String KEY_REVIEW_CREATED_DATE = "review_created_date";

    public static final String KEY_ANSWER_PART_ID = "answer_id";
    public static final String KEY_ANSWER_REVIEW_ID = "answer_reviewid";
    public static final String KEY_ANSWER_WHOANSWERS_ID = "answer_whoanswers";
    public static final String KEY_ANSWER_WHOPOSTED_ID = "answer_whoposted";
    public static final String KEY_ANSWER_TEXT = "answer_text";
    public static final String KEY_ANSWER_CREATED_DATE = "answer_created_date";


    public static final String KEY_EXECUTORNSERVICES_EXECUTOR_ID = "executorId";
    public static final String KEY_EXECUTORNSERVICES_SERVICE_ID = "serviceId";

    public static final String KEY_REVIEWNANSWERS_REVIEW_ID = "reviewnanswers_reviewid";
    public static final String KEY_REVIEWNANSWERS_ANSWER_ID = "reviewnanswers_answerid";


    public static final String KEY_NOTIFY_TEXT = "notify_text";
    public static final String KEY_NOTIFY_PERSONID = "notify_personid";
    public static final String KEY_NOTIFY_CREATED_DATE = "notify_createddt";
    public static final String KEY_NOTIFY_SECTION_ID = "notify_sectionId";
    public static final String KEY_NOTIFY_SRC_ID = "notify_src";
    public static final String KEY_NOTIFY_STATUS_ID = "notify_status";

    public static final String KEY_EXECUTORNPERSON_EXECUTOR_ID = "executorId";
    public static final String KEY_EXECUTORNPERSON_PERSON_ID = "personId";

    public static final String KEY_RESPONSES_PERSON_ID = "response_personid";
    public static final String KEY_RESPONSES_ORDER_ID = "response_orderid";
    public static final String KEY_RESPONSES_TEXT = "response_text";
    public static final String KEY_RESPONSES_SUGGESTEDPRICE = "response_suggestesprice";
    public static final String KEY_RESPONSES_CREATEDDATE = "response_date";

    //region Person


    //READ PERSON
    private Persons currentPerson = null;

    public Persons getLoggedInPerson() {
        SharedPreferences sPref = context.getSharedPreferences(KEY_SHARED_PREF, MODE_PRIVATE);

        int personId = sPref.getInt(KEY_LOGGED_ID, -1);

        if (personId != -1) {
            if (currentPerson == null || personId != currentPerson.getId()) {
                Persons person = apiProvider.getPerson(personId);
                currentPerson = person;
            }
        }
        return currentPerson;
    }

    public void setLoggedInPerson(Persons person) {
        SharedPreferences sPref = context.getSharedPreferences(KEY_SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();

        if (person != null) {
            editor.putInt(KEY_LOGGED_ID, person.getId());
        } else {
            editor.putInt(KEY_LOGGED_ID, -1);
        }
        editor.apply();
        currentPerson = person;
    }

    //UPDATE PERSON

   public void updatePerson(Persons person) {
     /*   SQLiteDatabase sqlDatabase = db.getWritableDatabase();
        sqlDatabase.beginTransaction();
        try {
            String sql = "UPDATE " + TABLE_PERSONS + " SET " +
                    KEY_PERSON_NAME + "= ?," +
                    KEY_PERSON_LASTNAME + "= ?," +
                    KEY_PERSON_PASSWD + "= ?," +
                    KEY_PERSON_RATING + "= ?," +
                    KEY_PERSON_NUMBER + "= ?," +
                    KEY_PERSON_PHOTO + "= ?" +
                    " WHERE " + KEY_PERSON_ID + "= ?";
            SQLiteStatement statement = sqlDatabase.compileStatement(sql);

            statement.bindString(1, person.getName());
            statement.bindString(2, person.getLastname());
            statement.bindString(3, person.getPasswd());
            statement.bindDouble(4, person.getRating());
            statement.bindString(5, person.getNumber());
            statement.bindBlob(6, person.getPhoto());
            statement.bindDouble(7, person.getId());
            statement.execute();
            sqlDatabase.setTransactionSuccessful();
        } finally {
            sqlDatabase.endTransaction();
            sqlDatabase.close();
        }*/
    }

    public void setPersonIsExecutorField(int personId, Boolean b) {
      /*  SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        int value;
        if (b == true) {
            value = 1;
        } else {
            value = 0;
        }
        try {
            String sql = "UPDATE " + TABLE_PERSONS + " SET " +
                    KEY_PERSON_ISEXECUTOR + "= " + value +
                    " WHERE " + KEY_PERSON_ID + "= " + personId;
            sqLiteDatabase.execSQL(sql);

            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }*/
    }


    public boolean getPersonIsExecutorField(int personId) {
       Boolean b = false;
     /*   SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();

        String sql = "SELECT " + KEY_PERSON_ISEXECUTOR + " FROM " + TABLE_PERSONS +
                " WHERE " + KEY_PERSON_ID + "=" + personId;
        try {
            Cursor c = sqLiteDatabase.rawQuery(sql, null);
            if (c.moveToFirst()) {
                int v = c.getInt(c.getColumnIndex(KEY_PERSON_ISEXECUTOR));
                if (v == 1) {
                    b = true;
                }
            }
            sqLiteDatabase.setTransactionSuccessful();
            c.close();
        } finally {
            sqLiteDatabase.endTransaction();
        }*/
        return b;
    }


    public int getPersonRatingById(int id) {
       int rating = -1;
       /* SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();

        try {
            String sql = "select (select sum(" + KEY_REVIEW_ASSESSMENT + ") from " + TABLE_REVIEWS + " where " + KEY_REVIEW_EXECUTOR_ID +
                    " = " + id + ")/(select count(" + KEY_REVIEW_CUSTOMER_ID + ") from " + TABLE_REVIEWS + " where " + KEY_REVIEW_EXECUTOR_ID +
                    " = " + id + ")";
            Cursor c = sqLiteDb.rawQuery(sql, null);
            if (c.moveToFirst()) {
                rating = c.getInt(0);
            }
            sqLiteDb.setTransactionSuccessful();
            c.close();
        } finally {
            sqLiteDb.endTransaction();

        }*/
        return rating;
    }


    public void updatePersonRatingById(int personId) {
      /*  int rating = getPersonRatingById(personId);
        SQLiteDatabase sqlDatabase = db.getWritableDatabase();
        sqlDatabase.beginTransaction();
        try {
            String sql = "UPDATE " + TABLE_PERSONS + " SET " +
                    KEY_PERSON_RATING + "= ?" +
                    " WHERE " + KEY_PERSON_ID + "= ?";
            SQLiteStatement statement = sqlDatabase.compileStatement(sql);

            statement.bindDouble(1, rating);
            statement.bindDouble(2, personId);
            statement.execute();
            sqlDatabase.setTransactionSuccessful();
        } finally {
            sqlDatabase.endTransaction();
            sqlDatabase.close();
        }*/
    }
    /*select count(customer_id) from reviews where executor_id = 2
select (select sum(assessment) from reviews where executor_id  = 2)/2
    select sum(assessment) from reviews where executor_id  = 2*/

    //DELETE PERSON
    public void deletePerson(int personId) {
       /* SQLiteDatabase sqL = db.getWritableDatabase();
        sqL.beginTransaction();
        try {
            int result = sqL.delete(TABLE_PERSONS, KEY_PERSON_ID + " = " + personId, null);
            if (result == -1) {
                Toast.makeText(db.context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(db.context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
            }
            sqL.setTransactionSuccessful();
        } finally {
            sqL.endTransaction();
        }*/
    }
    //endregion

    //region Executor
    //CREATE EXECUTOR
    public void addExecutor(Executor executor) {
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "INSERT INTO " + TABLE_EXECUTOR + "(" + KEY_EXECUTOR_PERSON_ID +
                    ", " + KEY_EXECUTOR_SECTION_ID + ", " + KEY_EXECUTOR_SPECIALIZATION +
                    ", " + KEY_EXECUTOR_DESCRIPTION + ") VALUES (" + executor.getPersonId()
                    + ", " + executor.getSectionId() + " , '" + executor.getSpecialztn()
                    + "', '" + executor.getDescriptn() + "')";
            sqLiteDb.execSQL(sql);
            String sqlMax = "SELECT MAX(" + KEY_EXECUTOR_ID + ") FROM " + TABLE_EXECUTOR;
            int maxId = 0;
            Cursor c = sqLiteDb.rawQuery(sqlMax, null);
            if (c.moveToFirst()) {
                maxId = c.getInt(0);
            }
            executor.setId(maxId);
            onExecutorCreate(executor);
            createExecutorServices(executor);
            onExecutorServicesCreate(executor);
           setPersonIsExecutorField(executor.getPersonId(), true);
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    //READ EXECUTOR
    private Executor getExecutorFromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(KEY_EXECUTOR_ID));
        int personId = c.getInt(c.getColumnIndex(KEY_EXECUTOR_PERSON_ID));
        int sectionId = c.getInt(c.getColumnIndex(KEY_EXECUTOR_SECTION_ID));
        String spcltn = c.getString(c.getColumnIndex(KEY_EXECUTOR_SPECIALIZATION));
        String dscrp = c.getString(c.getColumnIndex(KEY_EXECUTOR_DESCRIPTION));

        Executor executor = new Executor(id, personId, sectionId, spcltn, dscrp);
        return executor;
    }

    public Executor getExecutor(int executorId) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        Executor executor = new Executor();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                            + TABLE_EXECUTOR + " where " + KEY_EXECUTOR_ID + "=" + executorId,
                    null);
            if (c.moveToFirst()) {
                executor = getExecutorFromCursor(c);
                loadExecutorServices(executor);
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
            return executor;
        }
    }

    public ArrayList<Executor> getAllExecutors() {
        ArrayList<Executor> result = new ArrayList<>();
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                    + TABLE_EXECUTOR + " order by " + KEY_EXECUTOR_ID +
                    " desc", null);
            while (c.moveToNext()) {
                Executor executor = getExecutorFromCursor(c);
                loadExecutorServices(executor);
                result.add(executor);
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
        return result;
    }

    public ArrayList<Executor> getExecutorsBySectionId(int cId) {
        ArrayList<Executor> result = new ArrayList<>();
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                    + TABLE_EXECUTOR + " where " + KEY_EXECUTOR_SECTION_ID + " = " + cId + " order by " + KEY_EXECUTOR_ID +
                    " desc", null);
            while (c.moveToNext()) {
                Executor executor = getExecutorFromCursor(c);
                loadExecutorServices(executor);
                result.add(executor);
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
        return result;
    }


    public ArrayList<Service> getExecutorServices(int executorId) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        ArrayList<Service> result = new ArrayList<>();
        try {
            String sql = "SELECT " + TABLE_SERVICE + "." + KEY_SERVICE_ID + ", "
                    + TABLE_SERVICE + "." + KEY_SERVICE_TITLE + ", "
                    + TABLE_SERVICE + "." + KEY_SERVICE_PRICE + " FROM " + TABLE_SERVICE
                    + " JOIN " + TABLE_EXECUTORNSERVICES + " ON " + TABLE_SERVICE + "."
                    + KEY_SERVICE_ID + "=" + TABLE_EXECUTORNSERVICES + "." +
                    KEY_EXECUTORNSERVICES_SERVICE_ID + " WHERE " + TABLE_EXECUTORNSERVICES
                    + "." + KEY_EXECUTORNSERVICES_EXECUTOR_ID + "=" + executorId;
        /*
select service.id, service.title, service.price from service
join executor_services
on service.id = executor_services.service_id
where executor_services.executor_id = 1

 */
            Executor executor = this.getExecutor(executorId);
            Cursor c2 = sqLiteDb.rawQuery(sql, null);
            if (executor != null) {
                while (c2.moveToNext()) {
                    Service service = this.getServiceFromCursor(c2);
                    result.add(service);
                }
                sqLiteDb.setTransactionSuccessful();
                c2.close();
            }
        } finally {
            sqLiteDb.endTransaction();
        }

        return result;
    }

    //UPDATE EXECUTOR
    public void updateExecutor(Executor executor) {
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "UPDATE " + TABLE_EXECUTOR + " SET " +
                    KEY_EXECUTOR_SPECIALIZATION + "='" + executor.getSpecialztn() + "', " +
                    KEY_EXECUTOR_DESCRIPTION + "=' " + executor.getDescriptn() + "', " +
                    KEY_EXECUTOR_PERSON_ID + "= " + executor.getPersonId() + ", " +
                    KEY_EXECUTOR_SECTION_ID + "= " + executor.getSectionId() +
                    " WHERE " + KEY_EXECUTOR_ID + "=" + executor.getId();
            sqLiteDb.execSQL(sql);
            updateExecutorServices(executor);
            updateExecutorNServices(executor);

            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    //DELETE EXECUTOR
    public void deleteExecutor(int id) {
        SQLiteDatabase sqLDb = db.getWritableDatabase();
        try {
            int personId = getPersonIdByExecutorId(id);
            setPersonIsExecutorField(personId, false);
            onExecutorDelete(id);

            String sql = "DELETE FROM " + TABLE_EXECUTOR + " WHERE " + KEY_EXECUTOR_ID + " = " + id;
            sqLDb.execSQL(sql);
        } finally {

        }
    }

    //endregion

    //region Service
    //CREATE SERVICE
    public void addService(Service service) {

        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "INSERT INTO " + TABLE_SERVICE + "(" + KEY_SERVICE_TITLE + ", " +
                    KEY_SERVICE_PRICE + ") VALUES ( '" + service.getTitle() + "',  "
                    + service.getPrice() + ")";
            sqLiteDb.execSQL(sql);

            String sqlMaxId = "SELECT MAX(" + KEY_SERVICE_ID + ") FROM " +
                    TABLE_SERVICE;
            int maxId = 0;
            Cursor c = sqLiteDb.rawQuery(sqlMaxId, null);
            if (c.moveToFirst()) {
                maxId = c.getInt(0);
            }
            service.setId(maxId);

            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }


    //READ SERVICE

    public Service getService(int serviceId) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                            + TABLE_SERVICE + " where " + KEY_SERVICE_ID + " = " + serviceId,
                    null);
            if (c.moveToFirst()) {
                Service service = getServiceFromCursor(c);
                return service;
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }

        return null;
    }

    public void createExecutorServices(Executor executor) {
        if (executor.getServices() == null || executor.getId() == 0) {
            return;
        }
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            for (Service service : executor.getServices()) {
                String sql = "INSERT INTO " + TABLE_SERVICE + "(" +
                        KEY_SERVICE_PRICE + ", " + KEY_SERVICE_TITLE +
                        ") VALUES (" + service.getPrice() + ", '" + service.getTitle() + "')";
                sqLiteDb.execSQL(sql);

                String sqlMaxId = "SELECT MAX(" + KEY_SERVICE_ID + ") FROM " +
                        TABLE_SERVICE;
                int maxId = 0;
                Cursor c = sqLiteDb.rawQuery(sqlMaxId, null);
                if (c.moveToFirst()) {
                    maxId = c.getInt(0);
                }
                service.setId(maxId);
            }
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }


    public Service getServiceFromCursor(Cursor c) {

        int id = c.getInt(c.getColumnIndex(KEY_SERVICE_ID));
        String title = c.getString(c.getColumnIndex(KEY_SERVICE_TITLE));
        Double price = c.getDouble(c.getColumnIndex(KEY_SERVICE_PRICE));
        Service service = new Service(id, title, price);
        return service;
    }

    //UPDATE SERVICE
    public void updateService(Service service) {
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "UPDATE " + TABLE_SERVICE + " SET " +
                    KEY_SERVICE_TITLE + "='" + service.getTitle() + "', " +
                    KEY_SERVICE_PRICE + "=" + service.getPrice() +
                    " WHERE " + KEY_SERVICE_ID + " = " + service.getId();
            sqLiteDb.execSQL(sql);
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    public void updateExecutorServices(Executor executor) {
        if (executor.getServices() == null || executor.getId() == 0) {
            return;
        }
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
           /* ArrayList<Integer> servicesId = getExecutorsServicesId(executor.getId());
            for (int i = 0; i < servicesId.size(); i++) {
                String sql = "DELETE FROM " + TABLE_SERVICE +
                        " WHERE " + KEY_SERVICE_ID + "=" + i;
                sqLiteDb.execSQL(sql);}*/
            deleteExecutorServices(executor.getId());
            String sql = "DELETE FROM " + TABLE_EXECUTORNSERVICES
                    + " WHERE " + KEY_EXECUTORNSERVICES_EXECUTOR_ID + "=" + executor.getId();
            sqLiteDb.execSQL(sql);


            for (Service service : executor.getServices()) {
                String sql2 = "INSERT INTO " + TABLE_SERVICE + "(" +
                        KEY_SERVICE_PRICE + ", " + KEY_SERVICE_TITLE +
                        ") VALUES (" + service.getPrice() + ", '" + service.getTitle() + "')";
                sqLiteDb.execSQL(sql2);

                String sqlMaxId = "SELECT MAX(" + KEY_SERVICE_ID + ") FROM " +
                        TABLE_SERVICE;
                int maxId = 0;
                Cursor c = sqLiteDb.rawQuery(sqlMaxId, null);
                if (c.moveToFirst()) {
                    maxId = c.getInt(0);
                }
                service.setId(maxId);
            }
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    public void deleteExecutorServices(int executorId) {
        if (executorId == 0) {
            return;
        }
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "DELETE FROM " + TABLE_SERVICE + " WHERE " + KEY_SERVICE_ID + " IN ( SELECT " + KEY_SERVICE_ID + " FROM " +
                    TABLE_EXECUTORNSERVICES + " WHERE " + KEY_EXECUTORNSERVICES_EXECUTOR_ID + " = " + executorId + ")";
            sqLiteDb.execSQL(sql);
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }


    //DELETE SERVICE
    public void deleteService(int serviceId) {
        SQLiteDatabase sqL = db.getWritableDatabase();
        sqL.beginTransaction();
        try {
            int result = sqL.delete(TABLE_SERVICE, KEY_SERVICE_ID + " = " + serviceId, null);
            if (result == -1) {
                Toast.makeText(db.context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(db.context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
            }
            sqL.setTransactionSuccessful();
        } finally {
            sqL.endTransaction();
        }
    }
    //endregion

    //region Section
    //CREATE SECTION
    public void addSection(Section_of_services section) {
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "INSERT INTO " + TABLE_SECTIONS + "(" + KEY_SECTION_TITLE + ") VALUES ( '"
                    + section.getTitle() + ")";
            sqLiteDb.execSQL(sql);
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    //READ SECTION
    public Section_of_services getSection(int sectionId) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        Section_of_services section = new Section_of_services();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                            + TABLE_SECTIONS + " where " + KEY_SECTION_ID + "=" + sectionId,
                    null);
            if (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex(KEY_SECTION_ID));
                String title = c.getString(c.getColumnIndex(KEY_SECTION_TITLE));
                section = new Section_of_services(id, title);
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
        return section;
    }


    public ArrayList<Section_of_services> getSections() {
        ArrayList<Section_of_services> result = new ArrayList<>();
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                            + TABLE_SECTIONS + " order by " + KEY_SECTION_ID,
                    null);
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex(KEY_SECTION_ID));
                String title = c.getString(c.getColumnIndex(KEY_SECTION_TITLE));
                Section_of_services section = new Section_of_services(id, title);
                result.add(section);
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
        return result;
    }

    public ArrayList<String> getSectionListInString() {
        ArrayList<String> result = new ArrayList<>();
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.enableWriteAheadLogging();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                            + TABLE_SECTIONS + " order by " + KEY_SECTION_ID,
                    null);
            while (c.moveToNext()) {
                String title = c.getString(c.getColumnIndex(KEY_SECTION_TITLE));
                result.add(title);
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
        return result;
    }

    public int getSectionIdByTitle(String title) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        int id = -1;
        try {
            Cursor cursor = sqLiteDb.rawQuery("select " + KEY_SECTION_ID + " from "
                    + TABLE_SECTIONS + " where " + KEY_SECTION_TITLE + " = '" + title + "'", null);
            if (cursor.moveToFirst()) {
                id = cursor.getInt(cursor.getColumnIndex(KEY_SECTION_ID));
            }
            cursor.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
        return id;
    }

//endregion


    //region Order
    //CREATE ORDER
    public void addOrder(Order order) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            int i = 0;
            if (order.isAnonNote()) {
                i = 1;
            }

            String sql = "INSERT INTO " + TABLE_ORDERS + "(" + KEY_ORDER_CUSTOMER_ID + ", " + KEY_ORDER_TITLE +
                    ", " + KEY_ORDER_SECTION_ID + ", " + KEY_ORDER_PRICE + ", "
                    + KEY_ORDER_DESCRIPTION + ", " + KEY_ORDER_DEADLINE + ", " + KEY_ORDER_CREATED_DATE + ", " + KEY_ORDER_ISANONNOTE +
                    ") VALUES (" + order.getCustomerId() + ", '" + order.getTitle() + "', " + order.getSection() + " , "
                    + order.getPrice() + " , '"
                    + order.getDescription() + "' , "
                    + order.getDeadline() + " , "
                    + MyUtils.getCurentDateInLong() + " , " + i + ")";
            sqLiteDatabase.execSQL(sql);

            String sqlMaxId = "SELECT MAX(" + KEY_ORDER_ID + ") FROM " +
                    TABLE_ORDERS;
            int maxId = 0;
            Cursor c = sqLiteDatabase.rawQuery(sqlMaxId, null);
            if (c.moveToFirst()) {
                maxId = c.getInt(0);
            }
            order.setId(maxId);
            onOrderCreate(order);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }

    }

    //READ ORDER
    public Order getOrder(int orderId) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        Order order = new Order();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                            + TABLE_ORDERS + " where " + " " + KEY_ORDER_ID + "=" + orderId,
                    null);
            if (c.moveToFirst()) {
                order = getOrderFromCursor(c);
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
            return order;
        }
    }


    private Order getOrderFromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(KEY_ORDER_ID));
        String title = c.getString(c.getColumnIndex(KEY_ORDER_TITLE));
        int customerId = c.getInt(c.getColumnIndex(KEY_ORDER_CUSTOMER_ID));
        int sectionId = c.getInt(c.getColumnIndex(KEY_ORDER_SECTION_ID));
        double price = c.getDouble(c.getColumnIndex(KEY_ORDER_PRICE));
        String dscrp = c.getString(c.getColumnIndex(KEY_ORDER_DESCRIPTION));
        Long deadline = c.getLong(c.getColumnIndex(KEY_ORDER_DEADLINE));
        Long createdDate = c.getLong(c.getColumnIndex(KEY_ORDER_CREATED_DATE));

        Order order = new Order(id, customerId, title, sectionId, price, dscrp, deadline, createdDate);
        return order;
    }


    public ArrayList<Order> getOrders() {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        ArrayList<Order> result = new ArrayList<>();
        try {
            Cursor c = sqLiteDb.rawQuery("SELECT * FROM "
                    + TABLE_ORDERS + " ORDER BY " + KEY_ORDER_CREATED_DATE +
                    " DESC", null);
            while (c.moveToNext()) {
                Order order = getOrderFromCursor(c);
                result.add(order);
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
            return result;
        }
    }

    public ArrayList<Order> getPersonOrdersById(int personId) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        ArrayList<Order> result = new ArrayList<>();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                    + TABLE_ORDERS + " where " + KEY_ORDER_CUSTOMER_ID + "= " + personId + " order by " + KEY_ORDER_CREATED_DATE +
                    " desc", null);
            while (c.moveToNext()) {
                Order order = getOrderFromCursor(c);
                result.add(order);
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
            return result;
        }
    }

    public ArrayList<Order> getOrders(Order_search search) {
        if (search == null) {
            search = new Order_search();
        }

        ArrayList<Order> result = new ArrayList<>();
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_ORDERS + " WHERE 1=1";
        if (search.getPersonId() != 0) {
            sql += " AND (" + KEY_ORDERNPERSON_CUSTOMER_ID + "=" + search.getPersonId() +
                    " OR " + KEY_ORDERNPERSON_EXECUTOR_ID + "=" + search.getPersonId() + ")";
        } else if (search.getPersonId1ThatISend() != 0) {
            sql += " AND " + KEY_ORDERNPERSON_CUSTOMER_ID + "=" + search.getPersonId1ThatISend();
        } else if (search.getPersonId2ThatIAccept() != 0) {
            sql += " AND " + KEY_ORDERNPERSON_EXECUTOR_ID + "=" + search.getPersonId2ThatIAccept();
        }
        if (search.getStatuses().isEmpty()) {
            String statuses = "";
            for (String s : search.getStatuses()) {
                statuses += "'" + s + "', ";
            }
            if (statuses.endsWith(", ")) {
                statuses = statuses.substring(0, statuses.length() - 2);
            }
            sql += " AND " + KEY_ORDERNPERSON_STATUS + " IN(" + statuses + ")";
        }
        sql += " ORDER BY " + KEY_ORDERNPERSON_PART_ID + " DESC";
        Cursor c = sqLiteDb.rawQuery(sql, null);
        while (c.moveToNext()) {
            Order order = this.getOrderFromCursor(c);
            result.add(order);
        }
        return result;
    }

    public ArrayList<Order> getOrdersBySectionId(int rId) {
        ArrayList<Order> result = new ArrayList<>();
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                    + TABLE_ORDERS + " where " + KEY_ORDER_SECTION_ID + " = " + rId + " order by " + KEY_ORDER_CREATED_DATE +
                    " desc", null);
            while (c.moveToNext()) {
                Order order = getOrderFromCursor(c);
                result.add(order);
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
        return result;
    }


    //UPDATE ORDER
    public void updateOrder(Order order) {
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "UPDATE " + TABLE_ORDERS + " SET " +
                    KEY_ORDER_TITLE + "='" + order.getTitle() + "', " +
                    KEY_ORDER_SECTION_ID + "=' " + order.getSection() + "', " +
                    KEY_ORDER_PRICE + "= " + order.getPrice() + ", " +
                    KEY_ORDER_DESCRIPTION + "= '" + order.getDescription() + "', " +
                    KEY_ORDER_DEADLINE + "= " + order.getDeadline() +
                    " WHERE " + KEY_ORDER_ID + " = " + order.getId();
            sqLiteDb.execSQL(sql);
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }
    //DELETE ORDER

    public void deleteOrder(int orderId) {
        SQLiteDatabase sqL = db.getWritableDatabase();
        sqL.beginTransaction();
        try {
            int result = sqL.delete(TABLE_ORDERS, KEY_ORDER_ID + " = " + orderId, null);
            if (result == -1) {
                Toast.makeText(db.context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(db.context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
            }
            onOrderDelete(orderId);
            sqL.setTransactionSuccessful();
        } finally {
            sqL.endTransaction();
        }
    }

    //endregion

    //region Notification
    //CREATE NOTIFY

    public void createNotify(Notify notify) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            String sql = "INSERT INTO " + TABLE_NOTIFY + "(" + KEY_NOTIFY_PERSONID + "," + KEY_NOTIFY_TEXT + "," +
                    KEY_NOTIFY_CREATED_DATE + "," + KEY_NOTIFY_SECTION_ID + "," + KEY_NOTIFY_SRC_ID + "," + KEY_NOTIFY_STATUS_ID +
                    ") VALUES (" + notify.getPersonId() + " , '" + notify.getText() + "', "
                    + notify.getCreatedDate() + ", " + notify.getSectionId() + ", " + notify.getSrcId() + ", " + notify.getStatus() + ")";
            sqLiteDatabase.execSQL(sql);

            String sqlMaxId = "SELECT MAX(" + KEY_NOTIFY_ID + ") FROM " +
                    TABLE_NOTIFY;
            int maxId = 0;
            Cursor c = sqLiteDatabase.rawQuery(sqlMaxId, null);
            if (c.moveToFirst()) {
                maxId = c.getInt(0);
                notify.setId(maxId);
            }
            c.close();
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    //READ NOTIFICATION
    public Notify getNotify(int notifyId) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                            + TABLE_NOTIFY + " where " + KEY_NOTIFY_ID + "=" + notifyId,
                    null);
            if (c.moveToFirst()) {
                int id = c.getInt(c.getColumnIndex(KEY_NOTIFY_ID));
                int personId = c.getInt(c.getColumnIndex(KEY_NOTIFY_PERSONID));
                String text = c.getString(c.getColumnIndex(KEY_NOTIFY_TEXT));
                Long createdDate = c.getLong(c.getColumnIndex(KEY_NOTIFY_CREATED_DATE));
                int sectionId = c.getInt(c.getColumnIndex(KEY_NOTIFY_SECTION_ID));
                int srcId = c.getInt(c.getColumnIndex(KEY_NOTIFY_SRC_ID));
                int status = c.getInt(c.getColumnIndex(KEY_NOTIFY_STATUS_ID));

                Notify notify = new Notify(id, personId, text, createdDate, sectionId, srcId, status);
                return notify;
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
        return null;
    }

    public ArrayList<Notify> getAllMyNotifies() {
        ArrayList<Notify> result = new ArrayList<>();
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                    + TABLE_NOTIFY + " where " + KEY_NOTIFY_PERSONID + "=" + getLoggedInPerson().getId()
                    + " order by " + KEY_NOTIFY_CREATED_DATE + " desc ", null);
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex(KEY_NOTIFY_ID));
                int personId = c.getInt(c.getColumnIndex(KEY_NOTIFY_PERSONID));
                String text = c.getString(c.getColumnIndex(KEY_NOTIFY_TEXT));
                int srcId = c.getInt(c.getColumnIndex(KEY_NOTIFY_SRC_ID));
                Long createdDate = c.getLong(c.getColumnIndex(KEY_NOTIFY_CREATED_DATE));
                int sectionId = c.getInt(c.getColumnIndex(KEY_NOTIFY_SECTION_ID));
                int status = c.getInt(c.getColumnIndex(KEY_NOTIFY_STATUS_ID));

                Notify notify = new Notify(id, personId, text, createdDate, sectionId, srcId, status);
                result.add(notify);
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
        return result;
    }


    public int getCountOfAllMyNewNotifies() {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        int count = 0;
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select count(*) from "
                    + TABLE_NOTIFY + " where " + KEY_NOTIFY_PERSONID +
                    "=" + getLoggedInPerson().getId() + " and " + KEY_NOTIFY_STATUS_ID + " = " + 0, null);
            if (c.moveToFirst()) {
                count = c.getInt(0);
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
        return count;
    }

    public void setMyNotifiesToChecked() {
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "UPDATE " + TABLE_NOTIFY + " SET " +
                    KEY_NOTIFY_STATUS_ID + " = 1 " +
                    " WHERE " + KEY_NOTIFY_PERSONID + "=" + getLoggedInPerson().getId();
            sqLiteDb.execSQL(sql);
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    //DELETE NOTIFICATION
    public void deleteNotify(int id) {
        SQLiteDatabase sqL = db.getWritableDatabase();
        sqL.beginTransaction();
        try {
            String sql = "DELETE FROM  " + TABLE_NOTIFY + " WHERE " + KEY_NOTIFY_ID + "=" + id;
            sqL.execSQL(sql);
            sqL.setTransactionSuccessful();
        } finally {
            sqL.endTransaction();
        }
    }

    public void deleteAllNotification() {
        SQLiteDatabase sqL = db.getWritableDatabase();
        sqL.beginTransaction();
        try {
            String sql = "DELETE FROM " + TABLE_NOTIFY;
            sqL.execSQL(sql);
            sqL.setTransactionSuccessful();
        } finally {
            sqL.endTransaction();
        }
    }
//endregion

    //region Bookmark
    //CREATE BOOKMARK

    public void putExecutorInMyBookmarks(int executorId) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        String sql = "";
        try {
            Executor e = getExecutor(executorId);
            if (e != null) {
                sql = "INSERT INTO " + TABLE_BOOKMARKS + "(" + KEY_BOOKMARK_PERSON_ID +
                        ", " + KEY_BOOKMARK_EXECUTOR_ID + ", " + KEY_BOOKMARK_ORDER_ID +
                        ") VALUES (" + getLoggedInPerson().getId() + " , "
                        + executorId + " , " + 0 + ")";
            }
            sqLiteDatabase.execSQL(sql);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public void putOrderInMyBookmarks(int orderId) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        String sql = "";
        try {
            Order r = getOrder(orderId);
            if (r != null) {
                sql = "INSERT INTO " + TABLE_BOOKMARKS + "(" + KEY_BOOKMARK_PERSON_ID +
                        ", " + KEY_BOOKMARK_EXECUTOR_ID + ", " + KEY_BOOKMARK_ORDER_ID +
                        ") VALUES (" + getLoggedInPerson().getId() + " , "
                        + 0 + " , " + orderId + ")";
            }
            sqLiteDatabase.execSQL(sql);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }


    //READ BOOKMARK

    public Bookmarks getBookmarkByOrderId(int orderId) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                            + TABLE_BOOKMARKS + " where" + " " + KEY_BOOKMARK_ORDER_ID + "=" + orderId +
                            " and " + KEY_BOOKMARK_PERSON_ID + " = " + getLoggedInPerson().getId(),
                    null);
            if (c.moveToFirst()) {
                int id = c.getInt(c.getColumnIndex(KEY_BOOKMARK_PART_ID));
                int personId = c.getInt(c.getColumnIndex(KEY_BOOKMARK_PERSON_ID));

                Bookmarks bookmark = new Bookmarks(id, personId, 0, orderId);
                return bookmark;
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
        return null;
    }

    public Bookmarks getBookmarkByExecutorId(int executorId) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        Bookmarks b = new Bookmarks();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                            + TABLE_BOOKMARKS + " where" + " " + KEY_BOOKMARK_EXECUTOR_ID + "=" + executorId +
                            " and " + KEY_BOOKMARK_PERSON_ID + " = " + getLoggedInPerson().getId(),
                    null);
            if (c.moveToFirst()) {
                int id = c.getInt(c.getColumnIndex(KEY_BOOKMARK_PART_ID));
                int personId = c.getInt(c.getColumnIndex(KEY_BOOKMARK_PERSON_ID));

                b.setId(id);
                b.setPersonId(personId);
                b.setExecutorId(executorId);
                b.setOrderId(0);
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
        return b;
    }


    public ArrayList<Bookmarks> getExecutorsListFromMyBookmarks() {
        ArrayList<Bookmarks> result = new ArrayList<>();
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                    + TABLE_BOOKMARKS + " where " + KEY_BOOKMARK_PERSON_ID + " = " + getLoggedInPerson().getId() + " and " + KEY_BOOKMARK_EXECUTOR_ID + " is not 0 " +
                    " order by " + KEY_BOOKMARK_PART_ID + " desc", null);
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex(KEY_BOOKMARK_PART_ID));
                int personId = c.getInt(c.getColumnIndex(KEY_BOOKMARK_PERSON_ID));
                int executorId = c.getInt(c.getColumnIndex(KEY_BOOKMARK_EXECUTOR_ID));
                Bookmarks bookm = new Bookmarks(id, personId, executorId, 0);
                result.add(bookm);
            }
            sqLiteDb.setTransactionSuccessful();
            c.close();
        } finally {
            sqLiteDb.endTransaction();
        }
        return result;
    }

    public ArrayList<Bookmarks> getOrdersListFromMyBookmarks() {
        ArrayList<Bookmarks> result = new ArrayList<>();
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "SELECT * FROM "
                    + TABLE_BOOKMARKS + " WHERE " + KEY_BOOKMARK_PERSON_ID + " = " + getLoggedInPerson().getId() + " AND " + KEY_BOOKMARK_ORDER_ID + " IS NOT 0 " +
                    " ORDER BY " + KEY_BOOKMARK_PART_ID + " DESC";
            Cursor c = sqLiteDb.rawQuery(sql, null);
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex(KEY_BOOKMARK_PART_ID));
                int personId = c.getInt(c.getColumnIndex(KEY_BOOKMARK_PERSON_ID));
                int orderId = c.getInt(c.getColumnIndex(KEY_BOOKMARK_ORDER_ID));
                Bookmarks bookm = new Bookmarks(id, personId, 0, orderId);
                result.add(bookm);
            }
            sqLiteDb.setTransactionSuccessful();
            c.close();
        } catch (Exception e) {
            Log.e("getOrdersListFromMyb", e.getMessage());
        } finally {
            sqLiteDb.endTransaction();
        }
        return result;
    }


    //DELETE BOOKMARK
    public void deleteBookm(int id) {
        SQLiteDatabase sqL = db.getWritableDatabase();
        sqL.beginTransaction();
        try {
            int result = sqL.delete(TABLE_BOOKMARKS, KEY_BOOKMARK_PART_ID + " = " + id, null);
            if (result == -1) {
                Toast.makeText(db.context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(db.context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
            }
            sqL.setTransactionSuccessful();
        } finally {
            sqL.endTransaction();
        }
    }

    public void deleteExecutorFromMyBookmarks(int executorId) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        String sql = "";
        try {
            Executor r = getExecutor(executorId);
            if (r != null) {
                sql = "DELETE FROM " + TABLE_BOOKMARKS +
                        " WHERE " + KEY_BOOKMARK_EXECUTOR_ID + " = " + executorId + " and "
                        + KEY_BOOKMARK_PERSON_ID + " = " + getLoggedInPerson().getId();
            }
            sqLiteDatabase.execSQL(sql);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public void deleteOrderFromMyBookmarks(int orderId) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        String sql = "";
        try {
            Order r = getOrder(orderId);
            if (r != null) {
                sql = "DELETE FROM " + TABLE_BOOKMARKS + " WHERE " + KEY_BOOKMARK_ORDER_ID +
                        " = " + orderId;
            }
            sqLiteDatabase.execSQL(sql);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }
//endregion


    //region Review
    //CREATE Review
    public void addReview(Review review) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        String sql = "INSERT INTO " + TABLE_REVIEWS + "(" + KEY_REVIEW_EXECUTOR_ID +
                ", " + KEY_REVIEW_CUSTOMER_ID + ", " + KEY_REVIEW_REVIEW_TEXT + ", "
                + KEY_REVIEW_ASSESSMENT + ", " + KEY_REVIEW_CREATED_DATE + ") VALUES (" + review.getExecutrId() + ", " + review.getCustomerId() + ", '"
                + review.getReview_text() + "', "
                + review.getAssessment() + ", " + review.getCreatedDate() + ")";
        sqLiteDatabase.execSQL(sql);

        String sqlMaxId = "SELECT MAX(" + KEY_REVIEW_PART_ID + ") FROM " +
                TABLE_REVIEWS;
        int maxId = 0;
        Cursor c = sqLiteDatabase.rawQuery(sqlMaxId, null);
        if (c.moveToFirst()) {
            maxId = c.getInt(0);
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        review.setId(maxId);
    }

    //READ Review
    public Review getReview(int id) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        Review review = new Review();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                            + TABLE_REVIEWS + " where " + " " + KEY_REVIEW_PART_ID + "=" + id,
                    null);
            if (c.moveToFirst()) {
                review = getReviewFromCursor(c);
                loadReviewAnswers(review);
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
        return review;
    }


    private Review getReviewFromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(KEY_REVIEW_PART_ID));
        int executorId = c.getInt(c.getColumnIndex(KEY_REVIEW_EXECUTOR_ID));
        int customerId = c.getInt(c.getColumnIndex(KEY_REVIEW_CUSTOMER_ID));
        String text = c.getString(c.getColumnIndex(KEY_REVIEW_REVIEW_TEXT));
        int assessment = c.getInt(c.getColumnIndex(KEY_REVIEW_ASSESSMENT));
        Long createddate = c.getLong(c.getColumnIndex(KEY_REVIEW_CREATED_DATE));

        Review review = new Review(id, executorId, customerId, text, assessment);
        review.setCreatedDate(createddate);
        return review;
    }

    public ArrayList<Review> getAllPersonReviewByPersonId(int personId) {
        ArrayList<Review> result = new ArrayList<>();
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                    + TABLE_REVIEWS + " where " + KEY_REVIEW_EXECUTOR_ID + " = " + personId + " order by "
                    + KEY_REVIEW_CREATED_DATE +
                    " desc", null);
            while (c.moveToNext()) {
                Review r = getReviewFromCursor(c);
                loadReviewAnswers(r);
                result.add(r);
            }
            sqLiteDb.setTransactionSuccessful();
            c.close();
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        } finally {
            sqLiteDb.endTransaction();
            return result;
        }

    }


    public ArrayList<Integer> getLeavedReviewPersonsIdList(int personId) {
        Persons person = null;// getPerson(personId);
        if (person == null) {
            return null;
        }
        ArrayList<Integer> arrID = new ArrayList();
        SQLiteDatabase database = db.getReadableDatabase();
        database.beginTransaction();
        try {
            String sql = "SELECT " + KEY_REVIEW_CUSTOMER_ID + " FROM " + TABLE_REVIEWS + " WHERE " +
                    KEY_REVIEW_EXECUTOR_ID + "=" + personId;
            Cursor c = database.rawQuery(sql, null);
            while (c.moveToNext()) {
                arrID.add(c.getInt(0));
            }
            database.setTransactionSuccessful();
            c.close();

        } finally {
            database.endTransaction();
            return arrID;
        }
    }

    //UPDATE Review
    public void updateReview(Review review) {
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "UPDATE " + TABLE_REVIEWS + " SET " +
                    KEY_REVIEW_REVIEW_TEXT + "='" + review.getReview_text() + "', " +
                    KEY_REVIEW_ASSESSMENT + "=" + review.getAssessment() +
                    " WHERE " + KEY_REVIEW_PART_ID + " = " + review.getId();
            sqLiteDb.execSQL(sql);
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    //DELETE Review

    public void deleteReview(int id) {
        SQLiteDatabase sqL = db.getWritableDatabase();
        sqL.beginTransaction();
        try {
            int result = sqL.delete(TABLE_REVIEWS, KEY_REVIEW_PART_ID + " = " + id, null);
            if (result == -1) {
                Toast.makeText(db.context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(db.context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
            }
            sqL.setTransactionSuccessful();
        } finally {
            sqL.endTransaction();
        }
    }

    //endregion

    //region Executor and Person
    //CREATE EXECUTNPERSON TABLE
    public void onExecutorCreate(Executor executor) {
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "INSERT INTO " + TABLE_EXECUTORNPERSON + "(" + KEY_EXECUTORNPERSON_EXECUTOR_ID +
                    ", " + KEY_EXECUTORNPERSON_PERSON_ID +
                    ") VALUES (" + executor.getId() + " , "
                    + executor.getPersonId() + ")";
            sqLiteDb.execSQL(sql);
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    //READ EXECUTORNPERSON

    public int getExecutorIdByPersonId(int personId) {
        Persons person =null;// this.getPerson(personId);
        if (person == null) {
            return -1;
        }
        int id = 0;
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor cursor = sqLiteDb.rawQuery("SELECT " + KEY_EXECUTORNPERSON_EXECUTOR_ID +
                    " FROM " + TABLE_EXECUTORNPERSON + " WHERE " + KEY_EXECUTORNPERSON_PERSON_ID + "= " + personId, null);
            if (cursor.moveToFirst()) {
                int executorId = cursor.getInt(0);
                Executor executor = getExecutor(executorId);
                if (executor == null) {
                    id = -1;
                } else {
                    id = executorId;
                }
            }
            sqLiteDb.setTransactionSuccessful();
            cursor.close();
        } finally {
            sqLiteDb.endTransaction();
            return id;
        }

    }

    public int getPersonIdByExecutorId(int executorId) {
        Executor r = this.getExecutor(executorId);
        if (r == null) {
            return -1;
        }

        int id = -1;
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor cursor = sqLiteDb.rawQuery("SELECT " + KEY_EXECUTORNPERSON_PERSON_ID +
                    " FROM " + TABLE_EXECUTORNPERSON + " WHERE " + KEY_EXECUTORNPERSON_EXECUTOR_ID + "= " + executorId, null);
            if (cursor.moveToFirst()) {
                int personId = cursor.getInt(0);
                Persons p = null; //getPerson(personId);
                if (p != null) {
                    id = personId;
                }
            }
            sqLiteDb.setTransactionSuccessful();
            cursor.close();
        } finally {
            sqLiteDb.endTransaction();
        }
        return id;
    }

    //DELETE EXECUTORNPERSON

    public void onExecutorDelete(int executorId) {
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "DELETE FROM " + TABLE_EXECUTORNPERSON + " WHERE " +
                    KEY_EXECUTORNPERSON_EXECUTOR_ID + " = "
                    + executorId;
            sqLiteDb.execSQL(sql);
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }
//endregion

    //region Executor and services

    public void onExecutorServicesCreate(Executor executor) {
        if (executor.getServices() == null || executor.getId() == 0) {
            return;
        }
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            for (Service service : executor.getServices()) {
                String sql = "INSERT INTO " + TABLE_EXECUTORNSERVICES + "(" +
                        KEY_EXECUTORNSERVICES_EXECUTOR_ID + ", " + KEY_EXECUTORNSERVICES_SERVICE_ID +
                        ") VALUES (" + executor.getId() + ", " + service.getId() + ")";
                sqLiteDb.execSQL(sql);
            }
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    // READ EXECUTORNSERVICES
    public void loadExecutorServices(Executor executor) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "SELECT " + TABLE_SERVICE + "." + KEY_SERVICE_ID + ", "
                    + TABLE_SERVICE + "." + KEY_SERVICE_TITLE + ", " + TABLE_SERVICE + "." + KEY_SERVICE_PRICE
                    + " FROM " +
                    TABLE_SERVICE + " JOIN " + TABLE_EXECUTORNSERVICES + " ON "
                    + TABLE_SERVICE + "." + KEY_SERVICE_ID + "=" + TABLE_EXECUTORNSERVICES
                    + "." + KEY_EXECUTORNSERVICES_SERVICE_ID + " WHERE " + TABLE_EXECUTORNSERVICES +
                    "." + KEY_EXECUTORNSERVICES_EXECUTOR_ID + "=" + executor.getId();

            //SELECT distinct Service.ed, service.title, service.price from service
         /*join executernservices
         on service._id = executernservices.serviceid
         where executernservices.executorid = executorid
          */
            Cursor c2 = sqLiteDb.rawQuery(sql, null);
            if (executor.getServices() != null) {
                executor.getServices().clear();
            } else {
                ArrayList<Service> services = new ArrayList<>();
                while (c2.moveToNext()) {
                    Service service = getServiceFromCursor(c2);
                    services.add(service);
                }
                executor.setServices(services);
                c2.close();
                sqLiteDb.setTransactionSuccessful();
            }
        } finally {
            {
                sqLiteDb.endTransaction();
            }
        }
    }


    //UPDATE EXECUTORNSERVICES
    public void updateExecutorNServices(Executor executor) {
        if (executor.getServices() == null || executor.getId() == 0) {
            return;
        }
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            deleteFromExecutorNServicesByExecutorId(executor.getId());
            for (Service service : executor.getServices()) {
                String sql = "INSERT INTO " + TABLE_EXECUTORNSERVICES + "(" +
                        KEY_EXECUTORNSERVICES_EXECUTOR_ID + ", " + KEY_EXECUTORNSERVICES_SERVICE_ID +
                        ") VALUES (" + executor.getId() + ", " + service.getId() + ")";
                sqLiteDb.execSQL(sql);
            }
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    //DELETE EXECUTORNSERVICES

    public void deleteFromExecutorNServicesByExecutorId(int id) {
        SQLiteDatabase sqLDb = db.getWritableDatabase();
        sqLDb.beginTransaction();
        try {
            String sql = "DELETE FROM " + TABLE_EXECUTORNSERVICES + " WHERE " + KEY_EXECUTORNSERVICES_EXECUTOR_ID + " = " + id;
            sqLDb.execSQL(sql);
            sqLDb.setTransactionSuccessful();
        } finally {
            sqLDb.endTransaction();
        }
    }


    //endregion

    //region ORDERNPERSON
    //CREATE ORDERNPERSON
    public void addOrderNPerson(OrderNPerson orNPer) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        String sql = "INSERT INTO " + TABLE_ORDERNPERSON + "(" + KEY_ORDERNPERSON_ORDER_ID +
                ", " + KEY_ORDERNPERSON_CUSTOMER_ID + ", " + KEY_ORDERNPERSON_EXECUTOR_ID + ", " +
                ") VALUES ('" + orNPer.getOrderId() + "', " + orNPer.getCustomerId() + " , "
                + orNPer.getExecutorId() + ")";
        sqLiteDatabase.execSQL(sql);
//KEY_ORDERNPERSON_STATUS +
        String sqlMaxId = "SELECT MAX(" + KEY_ORDERNPERSON_PART_ID + ") FROM " +
                TABLE_ORDERNPERSON;
        int maxId = 0;
        Cursor c = sqLiteDatabase.rawQuery(sqlMaxId, null);
        if (c.moveToFirst()) {
            maxId = c.getInt(0);
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        orNPer.setId(maxId);
    }

    public void onOrderCreate(Order order) {
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "INSERT INTO " + TABLE_ORDERNPERSON + "(" + KEY_ORDERNPERSON_ORDER_ID +
                    ", " + KEY_ORDERNPERSON_CUSTOMER_ID +
                    ") VALUES (" + order.getId() + " , "
                    + order.getCustomerId() + ")";
            sqLiteDb.execSQL(sql);
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    //READ OrderNPerson TABLE
    public OrderNPerson getOrderNPerson(int ordNPerId) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                            + TABLE_ORDERNPERSON + " where" + " " + KEY_ORDERNPERSON_PART_ID + "=" + ordNPerId,
                    null);
            if (c.moveToFirst()) {
                int id = c.getInt(c.getColumnIndex(KEY_ORDERNPERSON_PART_ID));
                int orderId = c.getInt(c.getColumnIndex(KEY_ORDERNPERSON_ORDER_ID));
                int customerId = c.getInt(c.getColumnIndex(KEY_ORDERNPERSON_CUSTOMER_ID));
                int executorId = c.getInt(c.getColumnIndex(KEY_ORDERNPERSON_EXECUTOR_ID));
                //  KEY_ORDERNPERSON_STATUS
                OrderNPerson order_personn = new OrderNPerson(id, orderId, customerId, executorId);
                return order_personn;
            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
        return null;
    }

    public int getCustomerIdByOrderId(int orderId) {
        Order order = this.getOrder(orderId);
        if (order == null) {
            return -1;
        }
        int personId = -1;
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor cursor = sqLiteDb.rawQuery("SELECT " + KEY_ORDERNPERSON_CUSTOMER_ID +
                    " FROM " + TABLE_ORDERNPERSON + " WHERE " + KEY_ORDERNPERSON_ORDER_ID + "= " + orderId, null);
            if (cursor.moveToFirst()) {
                personId = cursor.getInt(0);
            }
            sqLiteDb.setTransactionSuccessful();
            cursor.close();
        } finally {
            sqLiteDb.endTransaction();
            return personId;
        }
    }

    public void onOrderDelete(int orderId) {
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "delete from " + TABLE_ORDERNPERSON + " where " +
                    KEY_ORDERNPERSON_ORDER_ID + "= " + orderId;
            sqLiteDb.execSQL(sql);
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }
    //endregion


    public void addImage(String name, byte[] image) {
        SQLiteDatabase database = db.getWritableDatabase();
        String sql = "INSERT INTO test_image VALUES (NULL, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindBlob(2, image);

        statement.executeInsert();
    }

    public void updateData(String name, byte[] image, int id) {
        SQLiteDatabase database = db.getWritableDatabase();

        String sql = "UPDATE test_image SET title = ?,  image = ? WHERE _id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindBlob(2, image);
        statement.bindDouble(3, id);

        statement.execute();
        database.close();
    }

    public void deleteData(int id) {
        SQLiteDatabase database = db.getWritableDatabase();

        String sql = "DELETE FROM test_image WHERE _id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, id);

        statement.execute();
        database.close();
    }

    public ArrayList<TestEntity> getAllDataFromTestImage() {
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM test_image", null);
        ArrayList<TestEntity> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("title"));
            byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));

            result.add(new TestEntity(name, image, id));
        }
        return result;
    }

    public ArrayList<Integer> getIdArrayFromTestImage() {
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT _id FROM test_image", null);
        ArrayList<Integer> arrID = new ArrayList<Integer>();
        while (c.moveToNext()) {
            arrID.add(c.getInt(0));
        }
        return arrID;
    }


    //region Responses

    //create responses
    public void addRespons(Response response) {

        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "INSERT INTO " + TABLE_RESPONSES + "(" + KEY_RESPONSES_ORDER_ID + ", " + KEY_RESPONSES_PERSON_ID + ", "
                    + KEY_RESPONSES_TEXT + ", " + KEY_RESPONSES_SUGGESTEDPRICE + ", " + KEY_RESPONSES_CREATEDDATE +
                    ") VALUES (?,?,?,?,?)";
            SQLiteStatement statement = sqLiteDb.compileStatement(sql);
            statement.clearBindings();

            statement.bindDouble(1, response.getOrderId());
            statement.bindDouble(2, response.getPersonId());
            statement.bindString(3, response.getText());
            statement.bindDouble(4, response.getPrice());
            statement.bindLong(5, response.getCreatedDate());
            statement.executeInsert();

            String sqlMaxId = "SELECT MAX(" + KEY_RESPONSES_ID + ") FROM " +
                    TABLE_RESPONSES;
            int maxId = 0;
            Cursor c = sqLiteDb.rawQuery(sqlMaxId, null);
            if (c.moveToFirst()) {
                maxId = c.getInt(0);
            }
            response.setId(maxId);
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    //get responses
    public int getPersonIdByResponseId(int responsId) {
        Response response = this.getRespons(responsId);
        if (response == null) {
            return -1;
        }
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        int id = 0;
        try {
            Cursor cursor = sqLiteDb.rawQuery("SELECT " + KEY_RESPONSES_PERSON_ID +
                    " FROM " + TABLE_RESPONSES + " WHERE " + KEY_RESPONSES_ID + "= " + responsId, null);
            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
            }
            sqLiteDb.setTransactionSuccessful();
            cursor.close();
        } finally {
            sqLiteDb.endTransaction();
        }
        return id;
    }


    public Response getRespons(int responseId) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                            + TABLE_RESPONSES + " where " + KEY_RESPONSES_ID + "=" + responseId,
                    null);
            if (c.moveToFirst()) {
                Response r = getResponsFromCursor(c);
                return r;
            }
            sqLiteDb.setTransactionSuccessful();
            c.close();
        } finally {
            sqLiteDb.endTransaction();
        }
        return null;
    }

    //TODO: птимизировать
    public ArrayList<Integer> getRespondedPersonsIdListByOrderId(int orderId) {
        Order order = this.getOrder(orderId);
        if (order == null) {
            return null;
        }
        ArrayList<Integer> arrID = new ArrayList();
        SQLiteDatabase database = db.getReadableDatabase();
        database.beginTransaction();
        try {
            String sql = "SELECT " + KEY_RESPONSES_PERSON_ID + " FROM " + TABLE_RESPONSES + " WHERE " +
                    KEY_RESPONSES_ORDER_ID + "=" + orderId;
            Cursor c = database.rawQuery(sql, null);
            while (c.moveToNext()) {
                arrID.add(c.getInt(0));
            }

            database.setTransactionSuccessful();
            c.close();
        } finally {
            database.endTransaction();
            return arrID;
        }
    }

    public ArrayList<Response> getAllOrderResponsesByOrderId(int orderId) {
        ArrayList<Response> result = new ArrayList<>();
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                    + TABLE_RESPONSES + " where " + KEY_RESPONSES_ORDER_ID + " = " + orderId + " order by " + KEY_RESPONSES_CREATEDDATE +
                    " desc", null);
            while (c.moveToNext()) {
                Response r = getResponsFromCursor(c);
                result.add(r);
            }
            sqLiteDb.setTransactionSuccessful();
            c.close();
        } finally {
            sqLiteDb.endTransaction();
        }
        return result;
    }

    public Response getResponsFromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(KEY_RESPONSES_ID));
        int orderId = c.getInt(c.getColumnIndex(KEY_RESPONSES_ORDER_ID));
        int personId = c.getInt(c.getColumnIndex(KEY_RESPONSES_PERSON_ID));
        String text = c.getString(c.getColumnIndex(KEY_RESPONSES_TEXT));
        Double price = c.getDouble(c.getColumnIndex(KEY_RESPONSES_SUGGESTEDPRICE));
        Long created = c.getLong(c.getColumnIndex(KEY_RESPONSES_CREATEDDATE));

        Response r = new Response(id, orderId, personId, text, price, created);
        return r;
    }


    //UPDATE Responses
    public void updateRespons(Response r) {
        SQLiteDatabase sqlDatabase = db.getWritableDatabase();
        sqlDatabase.beginTransaction();
        try {
            String sql = "UPDATE " + TABLE_RESPONSES + " SET " +
                    KEY_RESPONSES_TEXT + "= ?," +
                    KEY_RESPONSES_SUGGESTEDPRICE + "= ?" +
                    " WHERE " + KEY_RESPONSES_ID + "= ?";
            SQLiteStatement statement = sqlDatabase.compileStatement(sql);

            statement.bindString(1, r.getText());
            statement.bindDouble(2, r.getPrice());
            statement.bindDouble(3, r.getId());
            statement.execute();
            sqlDatabase.setTransactionSuccessful();
        } finally {
            sqlDatabase.endTransaction();
            sqlDatabase.close();
        }
    }


    //DELETE Responses
    public void deleteRespons(int id) {
        SQLiteDatabase sqL = db.getWritableDatabase();
        sqL.beginTransaction();
        try {
            int result = sqL.delete(TABLE_RESPONSES, KEY_RESPONSES_ID + " = " + id, null);
            if (result == -1) {
                Toast.makeText(db.context, "Ошибка при удалении", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(db.context, "Успешно удалено", Toast.LENGTH_SHORT).show();
            }
            sqL.setTransactionSuccessful();
        } finally {
            sqL.endTransaction();
        }
    }
    //endregion


    //region Answer
    //CREATE Answer
    public void addAnswer(Answer answer) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        String sql = "INSERT INTO " + TABLE_ANSWERS + "(" + KEY_ANSWER_REVIEW_ID +
                ", " + KEY_ANSWER_WHOANSWERS_ID + ", " + KEY_ANSWER_WHOPOSTED_ID + ", "
                + KEY_ANSWER_TEXT + ", " + KEY_ANSWER_CREATED_DATE + ") VALUES (" + answer.getReviewId() + ", " + answer.getWhoanswersId() + ", "
                + answer.getWhopostedId() + ", '"
                + answer.getText() + "', " + answer.getCreatedDate() + ")";
        sqLiteDatabase.execSQL(sql);

        String sqlMaxId = "SELECT MAX(" + KEY_ANSWER_PART_ID + ") FROM " +
                TABLE_ANSWERS;
        int maxId = 0;
        Cursor c = sqLiteDatabase.rawQuery(sqlMaxId, null);
        if (c.moveToFirst()) {
            maxId = c.getInt(0);
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        answer.setId(maxId);
    }

    //READ Answer
    public Answer getAnswer(int id) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        Answer answer = new Answer();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                            + TABLE_REVIEWS + " where " + " " + KEY_REVIEW_PART_ID + "=" + id,
                    null);
            if (c.moveToFirst()) {
                answer = getAnswerFromCursor(c);

            }
            c.close();
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
            return answer;
        }
    }


    private Answer getAnswerFromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(KEY_ANSWER_PART_ID));
        int reviewId = c.getInt(c.getColumnIndex(KEY_ANSWER_REVIEW_ID));
        int whoanswersId = c.getInt(c.getColumnIndex(KEY_ANSWER_WHOANSWERS_ID));
        int whoposted = c.getInt(c.getColumnIndex(KEY_ANSWER_WHOPOSTED_ID));
        String text = c.getString(c.getColumnIndex(KEY_ANSWER_TEXT));
        Long created = c.getLong(c.getColumnIndex(KEY_ANSWER_CREATED_DATE));

        Answer answer = new Answer(id, reviewId, whoanswersId, whoposted, text, created);
        return answer;
    }


    public ArrayList<Answer> getAllReviewAnswersByReviewId(int reviewId) {
        ArrayList<Answer> result = new ArrayList<>();
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            Cursor c = sqLiteDb.rawQuery("select * from "
                    + TABLE_ANSWERS + " where " + KEY_ANSWER_REVIEW_ID + " = " + reviewId + " order by "
                    + KEY_ANSWER_CREATED_DATE +
                    " desc", null);
            while (c.moveToNext()) {
                Answer answer = getAnswerFromCursor(c);
                result.add(answer);
            }
            sqLiteDb.setTransactionSuccessful();
            c.close();
        } finally {
            sqLiteDb.endTransaction();
            return result;
        }

    }


    //UPDATE Answer
    public void updateAnswer(Answer answer) {
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "UPDATE " + TABLE_ANSWERS + " SET " +
                    KEY_ANSWER_TEXT + "='" + answer.getText() + "'" +
                    " WHERE " + KEY_ANSWER_PART_ID + " = " + answer.getId();
            sqLiteDb.execSQL(sql);
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    //DELETE Answer

    public void deleteAnswer(int id) {
        SQLiteDatabase sqL = db.getWritableDatabase();
        sqL.beginTransaction();
        try {
            int result = sqL.delete(TABLE_ANSWERS, KEY_ANSWER_PART_ID + " = " + id, null);
            if (result == -1) {
                Toast.makeText(db.context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(db.context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
            }
            sqL.setTransactionSuccessful();
        } finally {
            sqL.endTransaction();
        }
    }
    //endregion

    //region Answersnreviews
    //CREATE REVIEWNANSWERS
    //TODO: удалить этот не нужный метод
  /*  public void onReviewAnswersCreate(Review review) {
        if (review.getAnswers() == null || review.getId() == 0) {
            return;
        }
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            for (Answer answer : review.getAnswers()) {
                String sql = "INSERT INTO " + TABLE_REVIEWNANSWERS + "(" +
                        KEY_REVIEWNANSWERS_REVIEW_ID + ", " + KEY_REVIEWNANSWERS_ANSWER_ID +
                        ") VALUES (" + answer.getReviewId() + ", " + answer.getId()+ ")";
                sqLiteDb.execSQL(sql);
            }
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }*/

    // READ REVIEWNANSWERS
    public void loadReviewAnswers(Review review) {
        SQLiteDatabase sqLiteDb = db.getReadableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "SELECT * FROM " +
                    TABLE_ANSWERS + " JOIN " + TABLE_REVIEWNANSWERS + " ON "
                    + TABLE_ANSWERS + "." + KEY_ANSWER_PART_ID + "=" + TABLE_REVIEWNANSWERS
                    + "." + KEY_REVIEWNANSWERS_ANSWER_ID + " WHERE " + TABLE_REVIEWNANSWERS +
                    "." + KEY_REVIEWNANSWERS_REVIEW_ID + "=" + review.getId();

            Cursor c2 = sqLiteDb.rawQuery(sql, null);
            if (review.getAnswers().size() > 0) {
                review.getAnswers().clear();
            }

            while (c2.moveToNext()) {
                Answer answer = getAnswerFromCursor(c2);
                review.getAnswers().add(answer);
            }
            c2.close();
            sqLiteDb.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        } finally {
            {
                sqLiteDb.endTransaction();
            }
        }
    }


    //UPDATE REVIEWNANSWERS
    public void updateReviewNAnswers(Review review) {
        if (review.getAnswers() == null || review.getId() == 0) {
            return;
        }
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        sqLiteDb.beginTransaction();
        try {
            String sql = "DELETE FROM " + TABLE_REVIEWNANSWERS +
                    " WHERE " + KEY_REVIEWNANSWERS_REVIEW_ID + "=" + review.getId();
            sqLiteDb.execSQL(sql);

            for (Answer answer : review.getAnswers()) {
                sql = "INSERT INTO " + TABLE_REVIEWNANSWERS + "(" +
                        KEY_REVIEWNANSWERS_REVIEW_ID + ", " + KEY_REVIEWNANSWERS_ANSWER_ID +
                        ") VALUES (" + review.getId() + ", " + answer.getId() + ")";
                sqLiteDb.execSQL(sql);
            }
            sqLiteDb.setTransactionSuccessful();
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    //DELETE REVIEWNANSWERS
    public void deleteFromReviewNAnswersByAnswerId(int answerId) {
        SQLiteDatabase sqL = db.getWritableDatabase();
        sqL.beginTransaction();
        try {
            int result = sqL.delete(TABLE_REVIEWNANSWERS, KEY_REVIEWNANSWERS_ANSWER_ID + " = " + answerId, null);
            if (result == -1) {
                Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
            }
            sqL.setTransactionSuccessful();
        } finally {
            sqL.endTransaction();
        }
    }


    //endregion


}
