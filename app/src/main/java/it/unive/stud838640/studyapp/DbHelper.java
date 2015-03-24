package it.unive.stud838640.studyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by paolo on 22/03/15.
 */
public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
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
        Cursor cursor = getReadableDatabase().query(DbStrings.TableHomeworks.TABLE_NAME,
                );
    }

}
