package it.unive.stud838640.studyapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by paolo on 27/03/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "StudyappDB";
    public static final int DATABASE_VERSION = 1;

    private static DbHelper dbHelper;
    private SQLiteDatabase readableDb, writeableDb;

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DbHelper get(Context context) {
        if (dbHelper == null)
            dbHelper = new DbHelper(context.getApplicationContext());
        return dbHelper;
    }

    public SQLiteDatabase getReadableDb() {
        if (readableDb == null || !readableDb.isOpen())
            readableDb = getReadableDatabase();
        return readableDb;
    }

    public SQLiteDatabase getWriteableDb() {
        if (writeableDb == null || !writeableDb.isOpen())
            writeableDb = getWritableDatabase();
        return writeableDb;
    }

    public void closeDb() {
        super.close();
        if (readableDb != null) {
            readableDb.close();
            readableDb = null;
        }
        if (writeableDb != null) {
            writeableDb.close();
            writeableDb = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SubjectsDataMapper.SQL_CREATE_TABLE);
        db.execSQL(HomeworksDataMapper.SQL_CREATE_TABLE);
        db.execSQL(TasksDataMapper.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SubjectsDataMapper.SQL_DELETE_TABLE);
        db.execSQL(HomeworksDataMapper.SQL_DELETE_TABLE);
        db.execSQL(TasksDataMapper.SQL_DELETE_TABLE);
        this.onCreate(db);
    }
}
