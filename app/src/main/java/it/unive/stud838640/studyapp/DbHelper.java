package it.unive.stud838640.studyapp;

import android.content.Context;
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
}
