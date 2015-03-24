package it.unive.stud838640.studyapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.Date;

import it.unive.stud838640.studyapp.homeworks.Homework;

/**
 * Created by paolo on 22/03/15.
 */
public class HomeworksDbHelper extends SQLiteOpenHelper implements BaseColumns {

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

    public static final String SQL_CREATE_TABLE_HOMEWORKS =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_DESCRIPTION + " TEXT, " +
                    COLUMN_NAME_EXPIRY_DATE + " INTEGER, " +
                    COLUMN_NAME_PERCENTAGE + " INTEGER, " +
                    COLUMN_NAME_OWNER + " INTEGER, " +
                    COLUMN_NAME_SUBJECT + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_NAME_OWNER + ") REFERENCES " +
                    UsersDbHelper.TABLE_NAME + "(" + UsersDbHelper._ID + "), " +
                    "FOREIGN KEY (" + COLUMN_NAME_SUBJECT + ") REFERENCES " +
                    SubjectsDbHelper.TABLE_NAME + "(" + SubjectsDbHelper._ID + "));";

    public static final String SQL_DELETE_TABLE_HOMEWORKS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public HomeworksDbHelper(Context context) {
        super(context, DbStrings.DATABASE_NAME, null, DbStrings.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbStrings.TableHomeworks.SQL_CREATE_TABLE_HOMEWORKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbStrings.TableHomeworks.SQL_DELETE_TABLE_HOMEWORKS);
        this.onCreate(db);
    }

    /***** Homeworks table *****/
    public long addHomework(Homework homework) {
        ContentValues values = new ContentValues();
        values.put(DbStrings.TableHomeworks.COLUMN_NAME_NAME,
                homework.getName());
        values.put(DbStrings.TableHomeworks.COLUMN_NAME_DESCRIPTION,
                homework.getDescription());
        values.put(DbStrings.TableHomeworks.COLUMN_NAME_OWNER,
                homework.getOwner().getId());
        values.put(DbStrings.TableHomeworks.COLUMN_NAME_SUBJECT,
                homework.getSubject().id);
        values.put(DbStrings.TableHomeworks.COLUMN_NAME_EXPIRY_DATE,
                homework.getExpiryDate().getTime());
        values.put(DbStrings.TableHomeworks.COLUMN_NAME_PERCENTAGE,
                homework.getPercentage());
        return getWritableDatabase().insert(DbStrings.TableHomeworks.TABLE_NAME,
                null, values);
    }

    public Homework getHomework(Long id) {
        Cursor cursor = getReadableDatabase().query(
                DbStrings.TableHomeworks.TABLE_NAME,
                DbStrings.TableHomeworks.COLUMNS,
                DbStrings.TableHomeworks._ID + " = " + id.toString(),
                null, null, null, null);

        Homework hw = new Homework();
        hw.setId(cursor.getLong(cursor.getColumnIndex(
                DbStrings.TableHomeworks._ID)));
        hw.setName(cursor.getString(cursor.getColumnIndex(
                DbStrings.TableHomeworks.COLUMN_NAME_NAME)));
        hw.setDescription(cursor.getString(cursor.getColumnIndex(
                DbStrings.TableHomeworks.COLUMN_NAME_DESCRIPTION)));
        long expiryDate = cursor.getLong(cursor.getColumnIndex(
                DbStrings.TableHomeworks.COLUMN_NAME_EXPIRY_DATE ));
        hw.setExpiryDate(new Date(expiryDate));

        return hw;
    }

}
