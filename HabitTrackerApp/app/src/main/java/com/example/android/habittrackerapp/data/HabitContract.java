package com.example.android.habittrackerapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ryan on 7/6/2017.
 */

public final class HabitContract {
    public HabitContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.android.habittrackerapp.data";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String TEXT_TYPE = "TEXT";
    public static final String INTEGER_TYPE = "INTEGER";

    public static final class HabitEntry implements BaseColumns {
        public static final String PATH_HABIT = "habit";

        public static final int DAILY_OCCURRENCES_MIN = 1;
        public static final int DAILY_OCCURRENCES_MAX = 5;

        public static final String TABLE_NAME = "habits";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_ACTION = "action";
        public static final String COLUMN_DAILY_OCCURRENCES = "daily_occurrences";

    }

}
