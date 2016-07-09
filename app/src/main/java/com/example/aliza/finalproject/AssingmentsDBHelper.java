package com.example.aliza.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amalia on 20/03/2016.
 */
public class AssingmentsDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FinalProj.DB";

    private static final String SQL_CREATE_ENTRIES_FAV =
            "CREATE TABLE " + Constant.Shows.TABLE_FAVORITE + "(" +
                    Constant.Shows._ID + " INTEGER PRIMARY KEY," +
                    Constant.Shows.SHOW_ID_FAV + " TEXT NOT NULL); ";

    private static final String SQL_CREATE_ENTRIES_SCHDL =
            "CREATE TABLE " + Constant.Shows.TABLE_SCHEDULE + "(" +
                    Constant.Shows._ID + " INTEGER PRIMARY KEY," +
                    Constant.Shows.SHOW_ID_SCHDL + " TEXT NOT NULL); ";

    private static final String SQL_DELETE_ENTRIES="DROP TABLE IF EXISTS " + Constant.Shows.TABLE_FAVORITE + "," +
            Constant.Shows.TABLE_SCHEDULE;

    private static final String SQL_DELETE_FAV = "DELETE FROM " + Constant.Shows.TABLE_FAVORITE + " WHERE " +
            Constant.Shows.SHOW_ID_FAV + "=";
    private static final String SQL_DELETE_SCHDL = "DELETE FROM " + Constant.Shows.TABLE_SCHEDULE + " WHERE " +
            Constant.Shows.SHOW_ID_SCHDL + "=";

    public AssingmentsDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void DeleteFavorite(SQLiteDatabase db, int show_id)
    {
        db.delete(Constant.Shows.TABLE_FAVORITE,Constant.Shows.SHOW_ID_FAV+"=?",new String[]{show_id+""});
        db.execSQL(SQL_DELETE_FAV + show_id+"");
    }

    public void DeleteSchedule(SQLiteDatabase db, int show_id)
    {
        db.delete(Constant.Shows.TABLE_SCHEDULE,Constant.Shows.SHOW_ID_SCHDL+"=?",new String[]{show_id+""});
        db.execSQL(SQL_DELETE_SCHDL + show_id+"");
    }

    public void InsertFavorite(Context context, SQLiteDatabase db, int show_id)
    {
        //save the show id in the DB
        long id;
        AssingmentsDBHelper dbHelper = new AssingmentsDBHelper(context);
        db = dbHelper.getWritableDatabase();

        ContentValues values;
        values = new ContentValues();
        values.put(Constant.Shows.SHOW_ID_FAV,show_id);

        id = db.insert(Constant.Shows.TABLE_FAVORITE,null,values);
        db.close();
    }

    public void InsertSchedule(Context context, SQLiteDatabase db, int show_id)
    {
        //save the episode id in the DB
        long id;
        AssingmentsDBHelper dbHelper = new AssingmentsDBHelper(context);
        db = dbHelper.getWritableDatabase();
        ContentValues values;
        values = new ContentValues();
        values.put(Constant.Shows.SHOW_ID_SCHDL,show_id);

        id = db.insert(Constant.Shows.TABLE_SCHEDULE,null,values);
        db.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_FAV);
        db.execSQL(SQL_CREATE_ENTRIES_SCHDL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
}

