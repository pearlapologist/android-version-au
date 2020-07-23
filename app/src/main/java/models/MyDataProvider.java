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

    //region Person

    //READ PERSON
    private Persons currentPerson = null;

    public Persons getLoggedInPerson() {
        SharedPreferences sPref = context.getSharedPreferences(KEY_SHARED_PREF, MODE_PRIVATE);

        int personId = sPref.getInt(KEY_LOGGED_ID, -1);

        if (personId != -1) {
            if (currentPerson == null || personId != currentPerson.getId()) {
                Persons person = null;
                try {
                    person = apiProvider.getPerson(personId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
}
