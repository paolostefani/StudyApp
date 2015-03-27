package it.unive.stud838640.studyapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import it.unive.stud838640.studyapp.profile.SchoolManager;

/**
 * Created by AccStefani on 27/03/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "StudyappDB";
    public static final int DATABASE_VERSION = 1;

    // Table Schools schema
    public static abstract class TableSchools implements BaseColumns {
        public static final String TABLE_NAME = "schools";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String[] COLUMNS = {"_ID", COLUMN_NAME_NAME, COLUMN_NAME_TYPE};

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME_NAME + " TEXT, " +
                        COLUMN_NAME_TYPE + " INTEGER);";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    // Table Subjects schema
    public static abstract class TableSubjects implements BaseColumns {
        public static final String TABLE_NAME = "subjects";
        public static final String COLUMN_NAME_NAME = "string";
        public static final String COLUMN_NAME_COLOR = "color";
        public static final String COLUMN_NAME_SCHOOL = "school";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME_NAME + " TEXT, " +
                        COLUMN_NAME_COLOR + " TEXT, " +
                        COLUMN_NAME_SCHOOL + " INTEGER, " +
                        "FOREIGN KEY (" + COLUMN_NAME_SCHOOL + ") REFERENCES " +
                        TableSchools.TABLE_NAME + "(" + TableSchools._ID + "));";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    // Table Users schema
    public static abstract class TableUsers implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SCHOOL = "school";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME_NAME + " TEXT, " +
                        COLUMN_NAME_SCHOOL + " INTEGER, " +
                        "FOREIGN KEY (" + COLUMN_NAME_SCHOOL + ") REFERENCES " +
                        TableSchools.TABLE_NAME + "(" + TableSchools._ID + "));";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    // Table Subjects schema
    public static abstract class TableHomeworks implements BaseColumns {
        public static final String TABLE_NAME = "homeworks";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_EXPIRY_DATE = "expiry_date";
        public static final String COLUMN_NAME_PERCENTAGE = "completed";
        public static final String COLUMN_NAME_OWNER = "owner";
        public static final String COLUMN_NAME_SUBJECT = "subject";
        public static final String[] COLUMNS = { "_ID", COLUMN_NAME_NAME,
                COLUMN_NAME_DESCRIPTION, COLUMN_NAME_EXPIRY_DATE, COLUMN_NAME_PERCENTAGE,
                COLUMN_NAME_OWNER, COLUMN_NAME_SUBJECT};

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME_NAME + " TEXT " +
                        COLUMN_NAME_DESCRIPTION + " TEXT, " +
                        COLUMN_NAME_EXPIRY_DATE + " INTEGER, " +
                        COLUMN_NAME_PERCENTAGE + " INTEGER, " +
                        COLUMN_NAME_OWNER + " INTEGER, " +
                        COLUMN_NAME_SUBJECT + " INTEGER, " +
                        "FOREIGN KEY (" + COLUMN_NAME_OWNER + ") REFERENCES " +
                        TableUsers.TABLE_NAME + "(" + TableUsers._ID + "), " +
                        "FOREIGN KEY (" + COLUMN_NAME_SUBJECT + ") REFERENCES " +
                        TableSubjects.TABLE_NAME + "(" + TableSubjects._ID + "));";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    // Table Tasks schema
    public static abstract class TableTasks implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_COMPLETED = "completed";
        public static final String COLUMN_NAME_HOMEWORK = "homework";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME_NAME + " TEXT " +
                        COLUMN_NAME_DESCRIPTION + " TEXT, " +
                        COLUMN_NAME_COMPLETED + " INTEGER, " +
                        COLUMN_NAME_HOMEWORK + " INTEGER, " +
                        "FOREIGN KEY (" + COLUMN_NAME_HOMEWORK + ") REFERENCES " +
                        TableHomeworks.TABLE_NAME + "(" + TableHomeworks._ID + "));";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableSchools.SQL_CREATE_TABLE);
        db.execSQL(TableSubjects.SQL_CREATE_TABLE);
        db.execSQL(TableUsers.SQL_CREATE_TABLE);
        db.execSQL(TableHomeworks.SQL_CREATE_TABLE);
        db.execSQL(TableTasks.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TableSchools.SQL_DELETE_TABLE);
        db.execSQL(TableSubjects.SQL_DELETE_TABLE);
        db.execSQL(TableUsers.SQL_DELETE_TABLE);
        db.execSQL(TableHomeworks.SQL_DELETE_TABLE);
        db.execSQL(TableTasks.SQL_DELETE_TABLE);
        this.onCreate(db);
    }

    // Table School CRUD
    public Cursor getSchoolsCursor() {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TableSchools.TABLE_NAME, null);
    }

    public long addSchool(SchoolManager.School school) {
        ContentValues values = new ContentValues();
        values.put(TableSchools.COLUMN_NAME_NAME, school.getName());
        values.put(TableSchools.COLUMN_NAME_TYPE, school.getType());

        return getWritableDatabase().insert(TableSchools.TABLE_NAME, null, values);
    }

    public long updateSchool(SchoolManager.School school) {
        ContentValues values = new ContentValues();
        values.put(TableSchools.COLUMN_NAME_NAME, school.getName());
        values.put(TableSchools.COLUMN_NAME_TYPE, school.getType());

        return getWritableDatabase().update(TableSchools.TABLE_NAME, values,
                TableSchools._ID + " = " + school.getId(), null);
    }

    public void deleteSchool(SchoolManager.School school) {
        getWritableDatabase().delete(TableSchools.TABLE_NAME,
                TableSchools._ID + " = " + school.getId(), null);
    }
}
