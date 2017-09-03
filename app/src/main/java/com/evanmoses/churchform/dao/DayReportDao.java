package com.evanmoses.churchform.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.evanmoses.churchform.objects.DayReport;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Evan on 6/4/2017.
 * primary
 */

public class DayReportDao extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "DayReports.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DayReportContract.DayReports.TABLE_NAME + " (" +
                     DayReportContract.DayReports._ID + " INTEGER PRIMARY KEY," +
                     DayReportContract.DayReports.COLUMN_NAME_DATE + " TEXT," +
                     DayReportContract.DayReports.COLUMN_NAME_LOCATION + " TEXT," +
                     DayReportContract.DayReports.COLUMN_NAME_INFORMATION + " TEXT," +
                     DayReportContract.DayReports.COLUMN_NAME_MILEAGE + " TEXT," +
                     DayReportContract.DayReports.COLUMN_NAME_TIMESTAMP + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DayReportContract.DayReports.TABLE_NAME;


    public DayReportDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db = getWritableDatabase();
        db.execSQL(SQL_CREATE_ENTRIES);
        db.close();
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
        values.put(DayReportContract.DayReports.COLUMN_NAME_TIMESTAMP, dr.dayAndMonth);
// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DayReportContract.DayReports.TABLE_NAME, null, values);

        db.close();

    }

    public ArrayList<DayReport> get(String select, String[] selectionArgs){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DayReport> reports = new ArrayList<>();

        String sortOrder = DayReportContract.DayReports.COLUMN_NAME_DATE + " DESC";

        try {
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
                        dr.dayAndMonth = cursor.getString(cursor.getColumnIndex(DayReportContract.DayReports.COLUMN_NAME_TIMESTAMP));
                        reports.add(dr);
                    }while(cursor.moveToNext());
                }
                cursor.close();
            }
        }catch(Exception e){
            Log.v("fixify","cursor exception: " + e.toString());
        }
        db.close();


        return reports;

    }

    public ArrayList<DayReport> all(){
        return get(null,null);
    }

    public ArrayList<DayReport> getByMonth(int month, int year){


        String beginningTimestamp,endingTimestamp;
        switch(month){
            case 0:
                beginningTimestamp=""+(year-1)+":12:31";
                endingTimestamp=""+(year)+"02:01";
                break;
            case 11:
                beginningTimestamp=""+(year)+":11:30";
                endingTimestamp=""+(year+1)+":01:01";
                break;
            case -1:
                //we don't deal with time travelers.
                //this is given by getByMonthString if something has gone wrong.
                return null;
            default:
                Calendar calBefore = Calendar.getInstance();
                calBefore.set(Calendar.MONTH,month-1);

                beginningTimestamp=""+(year)+":"+(month-1)+":"+(calBefore.getActualMaximum(Calendar.DAY_OF_MONTH));
                endingTimestamp=""+(year)+":"+(month+1)+":01";
                break;
        }

        return get(DayReportContract.DayReports.COLUMN_NAME_TIMESTAMP+"<? and "+DayReportContract.DayReports.COLUMN_NAME_TIMESTAMP+">? ",new String[]{endingTimestamp,beginningTimestamp});

    }

    public ArrayList<DayReport> getByMonthString(String monthString, int year){
        return getByMonth(getMonthNumberFromName(monthString), year);
    }

    public int getMonthNumberFromName(String monthName){
        String[] monthNames = new DateFormatSymbols().getMonths();
        int index=-1;
        for(int i=0; i<monthNames.length;i++){
            if(monthNames[i].equals(monthName)){
                index=i;
                break;
            }
        }
        return index;
    }

    public String getMonthNumberStringNormalized(int monthNumber){
        String normalizedMonthNumber;
        if(monthNumber<10){
            normalizedMonthNumber = "0" + monthNumber;
        }else{
            normalizedMonthNumber = "" + monthNumber;
        }
        return normalizedMonthNumber;
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db = getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
        db.close();
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
