package it.unive.stud838640.studyapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import it.unive.stud838640.studyapp.subject.Subject;

/**
 * Created by paolo on 28/03/15.
 */
public class SubjectsDataMapper implements BaseColumns{

    /**************** Table schema ****************/

    public static final String TABLE_NAME = "subjects";
    public static final String COLUMN_NAME_NAME = "string";
    public static final String COLUMN_NAME_COLOR = "color";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_NAME + " TEXT, " +
                    COLUMN_NAME_COLOR + " TEXT);";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    /***********************************************/

    private Context context;
    private SQLiteDatabase dbR, dbW;

    public SubjectsDataMapper(Context context) {
        this.context = context;
        dbR = DbHelper.get(context).getReadableDb();
        dbW = DbHelper.get(context).getWriteableDb();
    }


    public Cursor getAllSubjectsCursor() {
        return dbR.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();

        Cursor cursor = getAllSubjectsCursor();
        while (cursor.moveToNext()) {
            subjects.add(getSubject(cursor));
        }
        cursor.close();
        return subjects;
    }

    public Subject getSubjectById(long id) {
        Cursor cursor = dbR.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                _ID + " = " + id, null);
        cursor.moveToFirst();
        Subject s = getSubject(cursor);
        cursor.close();
        return s;
    }

    public long addSubject(Subject subject) {
        ContentValues values = getContentValues(subject);
        return dbW.insert(TABLE_NAME, null, values);
    }

    public long updateSubject(Subject subject) {
        ContentValues values = getContentValues(subject);
        return dbW.update(TABLE_NAME, values,
                _ID + " = " + subject.getId(), null);
    }

    public void deleteSubject(Subject subject) {
        dbW.delete(TABLE_NAME, _ID + " = " + subject.getId(), null);
    }

    private ContentValues getContentValues(Subject subject) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, subject.getName());
        values.put(COLUMN_NAME_COLOR, subject.getColor());
        return values;
    }

    private Subject getSubject(Cursor cursor) {
        Subject s = new Subject("", "");
        s.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        s.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)));
        s.setColor(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COLOR)));
        return s;
    }

}
