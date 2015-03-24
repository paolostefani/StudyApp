package it.unive.stud838640.studyapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import it.unive.stud838640.studyapp.profile.SchoolManager;

/**
 * Created by paolo on 22/03/15.
 */
public class SchoolsDbHelper extends SQLiteOpenHelper implements BaseColumns {

    public static final String TABLE_NAME = "schools";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_TYPE = "type";
    public static final String[] COLUMNS = { "_ID", COLUMN_NAME_NAME, COLUMN_NAME_TYPE };

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_NAME + " TEXT, " +
                    COLUMN_NAME_TYPE + " INTEGER);";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public SchoolsDbHelper(Context context) {
        super(context, DbStrings.DATABASE_NAME, null, DbStrings.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        this.onCreate(db);
    }

    public Cursor getCursor() {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public long addSchool(SchoolManager.School school) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, school.getName());
        values.put(COLUMN_NAME_TYPE, school.getType());

        return getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    public long updateSchool(SchoolManager.School school) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, school.getName());
        values.put(COLUMN_NAME_TYPE, school.getType());

        return getWritableDatabase().update(TABLE_NAME, values,
                _ID + " = " + school.getId(), null);
    }

    public void deleteSchool(SchoolManager.School school) {
        getWritableDatabase().delete(TABLE_NAME, _ID + " = " + school.getId(), null);
    }
}
