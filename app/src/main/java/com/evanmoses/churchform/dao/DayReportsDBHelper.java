package com.evanmoses.churchform.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Evan on 9/7/2017.
 */

public class DayReportsDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "DayReports.sqlite";

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


    public DayReportsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
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
