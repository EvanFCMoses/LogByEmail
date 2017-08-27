package com.evanmoses.churchform.dao;

import android.provider.BaseColumns;

/**
 * Created by Evan on 6/4/2017.
 * creates 'contract' for
 */

final class DayReportContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DayReportContract() {}

    /* Inner class that defines the table contents */
    static class DayReports implements BaseColumns {
        static final String TABLE_NAME = "report";
        static final String COLUMN_NAME_DATE = "date";
        static final String COLUMN_NAME_MILEAGE = "mileage";
        static final String COLUMN_NAME_INFORMATION = "information";
        static final String COLUMN_NAME_LOCATION = "location";
        static final String COLUMN_NAME_TIMESTAMP = "timestamp";
    }

}