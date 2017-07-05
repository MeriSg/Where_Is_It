package com.meri_sg.where_is_it.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo on 29-Nov-16.
 */

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, Contract.SavedItem.TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the items table in DB
        String command="CREATE TABLE " + Contract.SavedItem.TABLE_NAME +
                "(" + Contract.SavedItem.ID+ " INTEGER PRIMARY KEY autoincrement," + Contract.SavedItem.ITEMNAME + " TEXT,"
                + Contract.SavedItem.IMG + " TEXT," + Contract.SavedItem.IMGTIME + " TEXT," + Contract.SavedItem.COMMENTS + " TEXT,"
                + Contract.SavedItem.ALERTON + " TEXT," + Contract.SavedItem.ALERTTIME + " TEXT,"
                + Contract.SavedItem.PLACEDESCRIPTION + " TEXT," +Contract.SavedItem.LAT + " REAL,"
                + Contract.SavedItem.LNG +" REAL)";


        try {
            db.execSQL(command);
        } catch (SQLiteException ex) {

        }


        //create the places table in DB
        String commandA="CREATE TABLE " + Contract.places.TABLE_NAME +
                "(" + Contract.places.ID+ " INTEGER PRIMARY KEY autoincrement," + Contract.places.PLACENAME+ " TEXT)";
        try {
            db.execSQL(commandA);
        } catch (SQLiteException ex) {

        }

        //create the places table in DB
        String commandB="CREATE TABLE " + Contract.inside.TABLE_NAME +
                "(" + Contract.inside.ID+ " INTEGER PRIMARY KEY autoincrement," + Contract.inside.INSIDENAME + " TEXT)";
        try {
            db.execSQL(commandB);
        } catch (SQLiteException ex) {

        }

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
