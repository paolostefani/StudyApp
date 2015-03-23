package it.unive.stud838640.studyapp;

import android.provider.BaseColumns;

/**
 * Created by paolo on 22/03/15.
 */
public final class DbStrings {

    public static final String DATABASE_NAME = "StudyappDB";
    public static final int DATABASE_VERSION = 1;

    public static abstract class TableHomeworks implements BaseColumns {
        public static final String TABLE_NAME = "homeworks";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_EXPIRY_DATE = "expiry_date";
        public static final String COLUMN_NAME_COMPLETED = "completed";
        public static final String COLUMN_NAME_OWNER = "owner";
        public static final String COLUMN_NAME_SUBJECT = "subject";
        public static final String SQL_CREATE_TABLE_HOMEWORKS =
                "CREATE TABLE " + TABLE_NAME + " (" +
                 _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_DESCRIPTION + " TEXT, " +
                COLUMN_NAME_EXPIRY_DATE + " INTEGER, " +
                COLUMN_NAME_COMPLETED + " INTEGER, " +
                COLUMN_NAME_OWNER + " TEXT, " +
                COLUMN_NAME_SUBJECT + " INTEGER)";
        public static final String SQL_DELETE_TABLE_HOMEWORKS =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class TableTasks implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_COMPLETED = "completed";
        public static final String COLUMN_NAME_HOMEWORK = "homework";
    }

    public static abstract class TableUsers implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SCHOOL = "school";
    }

    public static abstract class TableSchools implements BaseColumns {
        public static final String TABLE_NAME = "schools";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TYPE = "type";
    }

    public static abstract class TablesSubjects implements BaseColumns {
        public static final String TABLE_NAME = "subjects";
        public static final String COLUMN_NAME_NAME = "string";
        public static final String COLUMN_NAME_COLOR = "color";
        public static final String COLUMN_NAME_SCHOOL = "school";
    }
}
