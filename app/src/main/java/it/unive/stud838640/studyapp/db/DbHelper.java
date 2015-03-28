package it.unive.stud838640.studyapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AccStefani on 27/03/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "StudyappDB";
    public static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SchoolsDataMapper.SQL_CREATE_TABLE);
        db.execSQL(SubjectsDataMapper.SQL_CREATE_TABLE);
        db.execSQL(UsersDataMapper.SQL_CREATE_TABLE);
        db.execSQL(HomeworksDataMapper.SQL_CREATE_TABLE);
        db.execSQL(TasksDataMapper.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SchoolsDataMapper.SQL_DELETE_TABLE);
        db.execSQL(SubjectsDataMapper.SQL_DELETE_TABLE);
        db.execSQL(UsersDataMapper.SQL_DELETE_TABLE);
        db.execSQL(HomeworksDataMapper.SQL_DELETE_TABLE);
        db.execSQL(TasksDataMapper.SQL_DELETE_TABLE);
        this.onCreate(db);
    }
}
