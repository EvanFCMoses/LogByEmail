package com.evanmoses.churchform.dao;

import android.provider.BaseColumns;

/**
 * Created by Evan on 6/4/2017.
 */

public final class DayReportContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DayReportContract() {}

    /* Inner class that defines the table contents */
    public static class DayReports implements BaseColumns {
        public static final String TABLE_NAME = "report";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_MILEAGE = "mileage";
        public static final String COLUMN_NAME_INFORMATION = "information";
        public static final String COLUMN_NAME_LOCATION = "location";
    }

}