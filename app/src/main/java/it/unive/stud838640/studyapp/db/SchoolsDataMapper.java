package it.unive.stud838640.studyapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import it.unive.stud838640.studyapp.profile.School;

/**
 * Created by paolo on 28/03/15.
 */
public class SchoolsDataMapper implements BaseColumns{

    /**************** Table schema ****************/

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

    /***********************************************/

    private Context context;
    SQLiteDatabase dbR, dbW;
    SubjectsDataMapper subjectsDataMapper;

    public SchoolsDataMapper(Context context) {
        dbR = DbHelper.get(context).getReadableDb();
        dbW = DbHelper.get(context).getWriteableDb();
        this.context = context;
        subjectsDataMapper = new SubjectsDataMapper(context);
    }


    public Cursor getAllSchoolsCursor() {
        return dbR.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public List<School> getAllSchools() {
        List<School> schools = new ArrayList<>();

        Cursor cursor = getAllSchoolsCursor();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            schools.add(getSchool(cursor));
        }
        cursor.close();

        return schools;
    }

    public School getSchoolById(long id) {
        Cursor cursor = dbR.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                _ID + " = " + id, null);
        return getSchool(cursor);
    }

    public long addSchool(School school) {
        ContentValues values = getContentValues(school);
        return dbW.insert(TABLE_NAME, null, values);
    }

    public long updateSchool(School school) {
        ContentValues values = getContentValues(school);
        return dbW.update(TABLE_NAME, values,
                _ID + " = " + school.getId(), null);
    }

    public void deleteSchool(School school) {
        dbW.delete(TABLE_NAME, _ID + " = " + school.getId(), null);
    }

    private ContentValues getContentValues(School school) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, school.getName());
        values.put(COLUMN_NAME_TYPE, school.getType());
        return values;
    }

    private School getSchool(Cursor cursor) {
        School s = new School("","");
        s.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        s.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)));
        s.setType(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TYPE)));
        List<School.Subject> subjects = subjectsDataMapper.getSubjectsBySchool(s);
        for (School.Subject sub : subjects) {
            s.addSubject(sub);
        }
        cursor.close();
        return s;
    }

}
