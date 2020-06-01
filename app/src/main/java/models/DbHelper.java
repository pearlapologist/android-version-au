package models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

//import android.support.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    Context context;

    public static final String DATABASE_NAME = "serviceAuction3.db";
    public static final int DATABASE_VERSION = 3;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public DbHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + MyDataProvider.TABLE_SECTIONS +
                "(" + MyDataProvider.KEY_SECTION_ID + " integer primary key autoincrement, "
                + MyDataProvider.KEY_SECTION_TITLE + " text)";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Стройка');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Грузоперевозки');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Ремонт и сервис');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Дизайн и фото');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Красота');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Образование и спорт');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Здоровье');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Бытовые услуги');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Ремонт и сервис');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Авто');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Рукоделие');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Недвижимость');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Животные');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Ит услуги и реклама');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO " + MyDataProvider.TABLE_SECTIONS + "(" +
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Разное');";
        sqLiteDatabase.execSQL(sql);


        sql = "CREATE TABLE " + MyDataProvider.TABLE_PERSONS +
                "(" + MyDataProvider.KEY_PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MyDataProvider.KEY_PERSON_NAME + " varchar, " +
                MyDataProvider.KEY_PERSON_LASTNAME + " varchar, " +
                MyDataProvider.KEY_PERSON_PASSWD + " text, " +
                MyDataProvider.KEY_PERSON_PHOTO + " blob, " +
                MyDataProvider.KEY_PERSON_NUMBER + " text, " +
                MyDataProvider.KEY_PERSON_RATING + " integer, " +
                MyDataProvider.KEY_PERSON_ISEXECUTOR + " integer, " +
                MyDataProvider.KEY_PERSON_CREATED_DATE + " integer)";
        sqLiteDatabase.execSQL(sql);

        Long create = DataConverter.getCurentDateInLong();
        sql = "INSERT INTO " + MyDataProvider.TABLE_PERSONS + "(" +  MyDataProvider.KEY_PERSON_NAME +
                        ", " +  MyDataProvider.KEY_PERSON_LASTNAME + ", " +  MyDataProvider.KEY_PERSON_NUMBER +
                        ", " +  MyDataProvider.KEY_PERSON_PASSWD +"," +  MyDataProvider.KEY_PERSON_RATING+","+
                MyDataProvider.KEY_PERSON_CREATED_DATE + ") VALUES (" + " 'Bayan', 'Iskanova', '87084067075'," +
                " 'asdzxc', 0, " +create + ")";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + MyDataProvider.TABLE_ORDERS +
                "(" + MyDataProvider.KEY_ORDER_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_ORDER_TITLE + " text, " +
                MyDataProvider.KEY_ORDER_SECTION_ID + " integer, " +
                MyDataProvider.KEY_ORDER_PRICE + " real, " +
                MyDataProvider.KEY_ORDER_CREATED_DATE + " integer, "
                + MyDataProvider.KEY_ORDER_DEADLINE + " integer, " +
                MyDataProvider.KEY_ORDER_DESCRIPTION + " text)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + MyDataProvider.TABLE_EXECUTORNORDER +
                "(" + MyDataProvider.KEY_EXECUTORNORDER_PART_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_EXECUTORNORDER_ORDER_ID + " INTEGER, " +
                MyDataProvider.KEY_EXECUTORNORDER_EXECUTOR_ID + " INTEGER, " +
                MyDataProvider.KEY_EXECUTORNORDER_CUSTOMER_ID + " INTEGER, " +
                MyDataProvider.KEY_EXECUTORNORDER_STATUS + " TEXT)";
        sqLiteDatabase.execSQL(sql);


        sql = "create table " + MyDataProvider.TABLE_SERVICE + "(" +
                MyDataProvider.KEY_SERVICE_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_SERVICE_TITLE + " text, " +
                MyDataProvider.KEY_SERVICE_PRICE + " integer)";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO " + MyDataProvider.TABLE_SERVICE + "(" +
                MyDataProvider.KEY_SERVICE_TITLE + ", " + MyDataProvider.KEY_SERVICE_PRICE + ") VALUES('Перевод', '2000');";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO " + MyDataProvider.TABLE_SERVICE + "(" +
                MyDataProvider.KEY_SERVICE_TITLE + ", " + MyDataProvider.KEY_SERVICE_PRICE + ") VALUES('Ремонт бытовой техники', '2500');";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO " + MyDataProvider.TABLE_SERVICE + "(" +
                MyDataProvider.KEY_SERVICE_TITLE + ", " + MyDataProvider.KEY_SERVICE_PRICE + ") VALUES('Портрет', '2000');";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO " + MyDataProvider.TABLE_SERVICE + "(" +
                MyDataProvider.KEY_SERVICE_TITLE + ", " + MyDataProvider.KEY_SERVICE_PRICE + ") VALUES('Строительство', '5000');";
        sqLiteDatabase.execSQL(sql);


        sql = "create table " + MyDataProvider.TABLE_EXECUTORNSERVICES + "(" +
                MyDataProvider.KEY_EXECUTORNSERVICES_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_EXECUTORNSERVICES_EXECUTOR_ID + " integer, " +
                MyDataProvider.KEY_EXECUTORNSERVICES_SERVICE_ID + " integer)";
        sqLiteDatabase.execSQL(sql);


        sql = "CREATE TABLE " + MyDataProvider.TABLE_EXECUTOR +
                "(" + MyDataProvider.KEY_EXECUTOR_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_EXECUTOR_PERSON_ID + " integer, " +
                MyDataProvider.KEY_EXECUTOR_SECTION_ID + " integer, " +
                MyDataProvider.KEY_EXECUTOR_SPECIALIZATION + " text, " +
                MyDataProvider.KEY_EXECUTOR_DESCRIPTION + " text)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + MyDataProvider.TABLE_NOTIFY +
                "(" + MyDataProvider.KEY_NOTIFY_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_NOTIFY_PERSON_ID + " integer, " +
                MyDataProvider.KEY_NOTIFY_TEXT + " text, " +
                MyDataProvider.KEY_NOTIFY_CREATED_DATE + " integer)";
        sqLiteDatabase.execSQL(sql);


        sql = "create table " + MyDataProvider.TABLE_EXECUTORNPERSON+ "(" +
                MyDataProvider.KEY_EXECUTORNPERSON_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_EXECUTORNPERSON_EXECUTOR_ID + " integer, " +
                MyDataProvider.KEY_EXECUTORNPERSON_PERSON_ID + " integer)";
        sqLiteDatabase.execSQL(sql);

        sql = "create table " + MyDataProvider.TABLE_BOOKMARKS + "(" +
                MyDataProvider.KEY_BOOKMARK_PART_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_BOOKMARK_PERSON_ID+ " integer, " +
                MyDataProvider.KEY_BOOKMARK_EXECUTOR_ID + " integer)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + MyDataProvider.TABLE_REVIEWS +
                "(" + MyDataProvider.KEY_REVIEW_PART_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_REVIEW_EXECUTOR_ID + " integer, " +
                MyDataProvider.KEY_REVIEW_CUSTOMER_ID + " integer, " +
                MyDataProvider.KEY_REVIEW_REVIEW_TEXT + " text, " +
                MyDataProvider.KEY_REVIEW_ASSESSMENT + " integer)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE test_image( _id integer primary key autoincrement, title varchar, image blob)";
        sqLiteDatabase.execSQL(sql);


       /* sql = "insert into executorNorder (order_id, executor_id, customer_id, status) VALUES (1,1,2,'new')";
        sqLiteDatabase.execSQL(sql);

        sql = "insert into executorNorder (order_id, executor_id, customer_id, status) VALUES (2,2,3,'new')";
        sqLiteDatabase.execSQL(sql);

        sql = "insert into executorNorder (order_id, executor_id, customer_id, status) VALUES (3,3,4,'new')";
        sqLiteDatabase.execSQL(sql);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MyDataProvider.TABLE_PERSONS);
        onCreate(db);
    }

}
