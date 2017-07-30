package com.evanmoses.churchform.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.evanmoses.churchform.objects.DayReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 6/4/2017.
 * primary
 */

public class DayReportDao extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DayReports.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DayReportContract.DayReports.TABLE_NAME + " (" +
                     DayReportContract.DayReports._ID + " INTEGER PRIMARY KEY," +
                     DayReportContract.DayReports.COLUMN_NAME_DATE + " TEXT," +
                     DayReportContract.DayReports.COLUMN_NAME_LOCATION + " TEXT," +
                     DayReportContract.DayReports.COLUMN_NAME_INFORMATION + " TEXT," +
                     DayReportContract.DayReports.COLUMN_NAME_MILEAGE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DayReportContract.DayReports.TABLE_NAME;



    public DayReportDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    public void insert(DayReport dr){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DayReportContract.DayReports.COLUMN_NAME_DATE, dr.date);
        values.put(DayReportContract.DayReports.COLUMN_NAME_INFORMATION, dr.information);
        values.put(DayReportContract.DayReports.COLUMN_NAME_LOCATION, dr.location);
        values.put(DayReportContract.DayReports.COLUMN_NAME_MILEAGE, dr.mileage);
// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DayReportContract.DayReports.TABLE_NAME, null, values);


    }


    public List<DayReport> get(String select, String[] selectionArgs){
        SQLiteDatabase db = this.getReadableDatabase();
        List<DayReport> reports = new ArrayList<>();

        String sortOrder = DayReportContract.DayReports.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.query(
                DayReportContract.DayReports.TABLE_NAME,
                null,
                select,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        if(cursor!=null){
            if(cursor.moveToFirst()){
                do {
                    DayReport dr = new DayReport();
                    dr.date = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DayReportContract.DayReports.COLUMN_NAME_DATE)));
                    dr.information = cursor.getString(cursor.getColumnIndex(DayReportContract.DayReports.COLUMN_NAME_INFORMATION));
                    dr.location = cursor.getString(cursor.getColumnIndex(DayReportContract.DayReports.COLUMN_NAME_LOCATION));
                    dr.mileage = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DayReportContract.DayReports.COLUMN_NAME_MILEAGE)));
                    reports.add(dr);
                }while(cursor.moveToNext());
            }
        }

        return reports;

    }

    public List<DayReport> all(){
        return get(null,null);
    }







    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
