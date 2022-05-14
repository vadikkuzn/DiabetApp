package com.example.javalinechart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Time;
import java.util.Date;

public class MyDB extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "DiabetBook.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_sugar_table";
    private static final String COLUMN_ID = "_id";
    private static final String DATE = "date";
    private static final String TIME = "time";
    private static final String BLOOD = "blood";
    private static final String FOOD = "food";
    private static final String INSULIN = "insulin";
    private static final String LANTUS = "lantus";
    private static final String COMMENT = "comment";

    private static final String TABLE_NAME_USER = "user_data";
    private static final String COLUMN_ID_USER = "_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String AGE = "age";
    private static final String GENDER = "gender";
    private static final String TYPE_DIABET = "type_diabet";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String MORNING = "morning";
    private static final String DAY = "day";
    private static final String EVENING = "evening";
    private static final String SENSIVITY = "sensivity";



    MyDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DATE + " DATE, " +
                        TIME + " TIME," +
                        BLOOD + " DOUBLE, " +
                        FOOD + " DOUBLE, " +
                        INSULIN + " DOUBLE, " +
                        LANTUS + " DOUBLE, " +
                        COMMENT + " TEXT);";

        String query1 =
                "CREATE TABLE " + TABLE_NAME_USER +
                        " (" + COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        FIRST_NAME + " TEXT, " +
                        LAST_NAME + " TEXT," +
                        AGE + " DOUBLE, " +
                        GENDER + " TEXT, " +
                        TYPE_DIABET + " TEXT, " +
                        HEIGHT + " DOUBLE, " +
                        WEIGHT + " DOUBLE, " +
                        MORNING + " DOUBLE, " +
                        DAY + " DOUBLE, " +
                        EVENING + " DOUBLE, " +
                        SENSIVITY + " DOUBLE);";

        db.execSQL(query);
        db.execSQL(query1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        onCreate(db);

    }

    void addSugar(String date, String time, Double blood, Double food, Double insulin, Double lantus, String comment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DATE, date);
        cv.put(TIME,time);
        cv.put(BLOOD,blood);
        cv.put(FOOD,food);
        cv.put(INSULIN,insulin);
        cv.put(LANTUS,lantus);
        cv.put(COMMENT,comment);


        long result =  db.insert(TABLE_NAME, null, cv);

        if(result == -1){
            Toast.makeText(context, "FAIL",Toast.LENGTH_SHORT).show();
        } else Toast.makeText(context, "Добавлено",Toast.LENGTH_SHORT).show();
    }

    Cursor readAllData(){
        String query = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db!=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    void updateData(String row_id,String date, String time, String blood, String food,String insulin, String lantus, String comment){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DATE,date);
        cv.put(TIME,time);
        cv.put(BLOOD,blood);
        cv.put(FOOD,food);
        cv.put(INSULIN,insulin);
        cv.put(LANTUS,lantus);
        cv.put(COMMENT,comment);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Fail Update", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Обновлено", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getReadableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Fail Delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }
    }





    //
    //
    /// tyt signUp
    void addUser(String first_name, String last_name, Integer age, String gender, String type_diabet, Integer height, Integer weight,
                 Double morning, Double day, Double evening, Double sensivity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FIRST_NAME, first_name);
        cv.put(LAST_NAME,last_name);
        cv.put(AGE,age);
        cv.put(GENDER,gender);
        cv.put(TYPE_DIABET,type_diabet);
        cv.put(HEIGHT,height);
        cv.put(WEIGHT,weight);
        cv.put(MORNING,morning);
        cv.put(DAY,day);
        cv.put(EVENING ,evening);
        cv.put(SENSIVITY, sensivity);


        long result =  db.insert(TABLE_NAME_USER, null, cv);

        if(result == -1){
            Toast.makeText(context, "FAIL",Toast.LENGTH_SHORT).show();
        } else Toast.makeText(context, "Добавлено",Toast.LENGTH_SHORT).show();
    }

    Cursor readAllUser(){
        String query = "SELECT * FROM "+TABLE_NAME_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db!=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    void updateUser(String row_id, String first_name, String last_name, Integer age, String gender, String type_diabet, Integer height, Integer weight,
                    Double morning, Double day, Double evening, Double sensivity){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FIRST_NAME, first_name);
        cv.put(LAST_NAME,last_name);
        cv.put(AGE,age);
        cv.put(GENDER,gender);
        cv.put(TYPE_DIABET,type_diabet);
        cv.put(HEIGHT,height);
        cv.put(WEIGHT,weight);
        cv.put(MORNING,morning);
        cv.put(DAY,day);
        cv.put(EVENING ,evening);
        cv.put(SENSIVITY, sensivity);

        long result = db.update(TABLE_NAME_USER, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Fail Update", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Обновлено", Toast.LENGTH_SHORT).show();
        }

    }

}

