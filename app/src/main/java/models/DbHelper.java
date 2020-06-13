package models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DbHelper extends SQLiteOpenHelper {
    Context context;

    public static final String DATABASE_NAME = "serviceAuction24.db";
    public static final int DATABASE_VERSION = 24;

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
                MyDataProvider.KEY_SECTION_TITLE + ") VALUES('Ит и реклама');";
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

        sql = "CREATE TABLE " + MyDataProvider.TABLE_ORDERS +
                "(" + MyDataProvider.KEY_ORDER_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_ORDER_CUSTOMER_ID + " integer, " +
                MyDataProvider.KEY_ORDER_TITLE + " text, " +
                MyDataProvider.KEY_ORDER_SECTION_ID + " integer, " +
                MyDataProvider.KEY_ORDER_PRICE + " real, " +
                MyDataProvider.KEY_ORDER_CREATED_DATE + " integer, "
                + MyDataProvider.KEY_ORDER_DEADLINE + " integer, " +
                MyDataProvider.KEY_ORDER_DESCRIPTION + " text, " +
                MyDataProvider.KEY_ORDER_ISANONNOTE + " integer)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + MyDataProvider.TABLE_ORDERNPERSON +
                "(" + MyDataProvider.KEY_ORDERNPERSON_PART_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_ORDERNPERSON_ORDER_ID + " INTEGER, " +
                MyDataProvider.KEY_ORDERNPERSON_CUSTOMER_ID + " INTEGER, " +
                MyDataProvider.KEY_ORDERNPERSON_EXECUTOR_ID + " INTEGER, " +
                MyDataProvider.KEY_ORDERNPERSON_STATUS + " TEXT)";
        sqLiteDatabase.execSQL(sql);


        sql = "create table " + MyDataProvider.TABLE_SERVICE + "(" +
                MyDataProvider.KEY_SERVICE_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_SERVICE_TITLE + " text, " +
                MyDataProvider.KEY_SERVICE_PRICE + " integer)";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO " + MyDataProvider.TABLE_SERVICE + "(" +
                MyDataProvider.KEY_SERVICE_TITLE + ", " + MyDataProvider.KEY_SERVICE_PRICE + ") VALUES ('Перевод', '2000');";
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
                MyDataProvider.KEY_EXECUTOR_DESCRIPTION + " text, " +
                MyDataProvider.KEY_EXECUTOR_COVERPHOTO + " blob)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + MyDataProvider.TABLE_NOTIFY +
                "(" + MyDataProvider.KEY_NOTIFY_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_NOTIFY_PERSONID + " integer, " +
                MyDataProvider.KEY_NOTIFY_SECTION_ID + " integer, " +
                MyDataProvider.KEY_NOTIFY_SRC_ID + " integer, " +
                MyDataProvider.KEY_NOTIFY_TEXT + " text, " +
                MyDataProvider.KEY_NOTIFY_CREATED_DATE + " integer)";
        sqLiteDatabase.execSQL(sql);


        sql = "create table " + MyDataProvider.TABLE_EXECUTORNPERSON + "(" +
                MyDataProvider.KEY_EXECUTORNPERSON_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_EXECUTORNPERSON_EXECUTOR_ID + " integer, " +
                MyDataProvider.KEY_EXECUTORNPERSON_PERSON_ID + " integer)";
        sqLiteDatabase.execSQL(sql);

        sql = "create table " + MyDataProvider.TABLE_BOOKMARKS + "(" +
                MyDataProvider.KEY_BOOKMARK_PART_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_BOOKMARK_PERSON_ID + " integer, " +
                MyDataProvider.KEY_BOOKMARK_EXECUTOR_ID + " integer, " +
                MyDataProvider.KEY_BOOKMARK_ORDER_ID + " integer)";
        sqLiteDatabase.execSQL(sql);


        sql = "CREATE TABLE " + MyDataProvider.TABLE_REVIEWS +
                "(" + MyDataProvider.KEY_REVIEW_PART_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_REVIEW_EXECUTOR_ID + " integer, " +
                MyDataProvider.KEY_REVIEW_CUSTOMER_ID + " integer, " +
                MyDataProvider.KEY_REVIEW_REVIEW_TEXT + " text, " +
                MyDataProvider.KEY_REVIEW_ASSESSMENT + " integer, " +
                MyDataProvider.KEY_REVIEW_CREATED_DATE + " integer)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + MyDataProvider.TABLE_ANSWERS +
                "(" + MyDataProvider.KEY_ANSWER_PART_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_ANSWER_REVIEW_ID + " integer, " +
                MyDataProvider.KEY_ANSWER_WHOANSWERS_ID + " integer, " +
                MyDataProvider.KEY_ANSWER_WHOPOSTED_ID + " integer, " +
                MyDataProvider.KEY_ANSWER_TEXT + " text, " +
                MyDataProvider.KEY_ANSWER_CREATED_DATE + " integer)";
        sqLiteDatabase.execSQL(sql);

        sql = "create table " + MyDataProvider.TABLE_REVIEWNANSWERS + "(" +
                MyDataProvider.KEY_REVIEWNANSWERS_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_REVIEWNANSWERS_REVIEW_ID + " integer, " +
                MyDataProvider.KEY_REVIEWNANSWERS_ANSWER_ID + " integer)";
        sqLiteDatabase.execSQL(sql);


        sql = "CREATE TABLE test_image( _id integer primary key autoincrement, title varchar, image blob)";
        sqLiteDatabase.execSQL(sql);


        sql = "CREATE TABLE " + MyDataProvider.TABLE_RESPONSES +
                "(" + MyDataProvider.KEY_RESPONSES_ID + " integer primary key autoincrement, " +
                MyDataProvider.KEY_RESPONSES_ORDER_ID + " INTEGER, " +
                MyDataProvider.KEY_RESPONSES_PERSON_ID + " INTEGER, " +
                MyDataProvider.KEY_RESPONSES_TEXT + " TEXT, " +
                MyDataProvider.KEY_RESPONSES_SUGGESTEDPRICE + " REAL, " +
                MyDataProvider.KEY_RESPONSES_CREATEDDATE + " INTEGER)";
        sqLiteDatabase.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MyDataProvider.TABLE_PERSONS);
        onCreate(db);
    }

}
