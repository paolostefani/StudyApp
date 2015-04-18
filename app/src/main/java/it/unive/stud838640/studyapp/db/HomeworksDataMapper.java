package it.unive.stud838640.studyapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.unive.stud838640.studyapp.homework.Homework;
import it.unive.stud838640.studyapp.homework.Task;
import it.unive.stud838640.studyapp.subject.SubjectManager;

/**
 * Created by paolo on 28/03/15.
 */
public class HomeworksDataMapper implements BaseColumns{

    /**************** Table schema ****************/

    public static final String TABLE_NAME = "homeworks";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
    public static final String COLUMN_NAME_EXPIRY_DATE = "expiry_date";
    public static final String COLUMN_NAME_PERCENTAGE = "completed";
    public static final String COLUMN_NAME_SUBJECT_ID = "subject";
    public static final String[] COLUMNS = { "_ID", COLUMN_NAME_NAME,
            COLUMN_NAME_DESCRIPTION, COLUMN_NAME_EXPIRY_DATE, COLUMN_NAME_PERCENTAGE,
            COLUMN_NAME_SUBJECT_ID};

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_NAME + " TEXT, " +
                    COLUMN_NAME_DESCRIPTION + " TEXT, " +
                    COLUMN_NAME_EXPIRY_DATE + " INTEGER, " +
                    COLUMN_NAME_PERCENTAGE + " INTEGER, " +
                    COLUMN_NAME_SUBJECT_ID + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_NAME_SUBJECT_ID + ") REFERENCES " +
                    SubjectsDataMapper.TABLE_NAME + "(" + SubjectsDataMapper._ID + "));";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    /***********************************************/

    private Context context;
    private SQLiteDatabase dbR, dbW;
    SubjectsDataMapper subjectsDataMapper;
    TasksDataMapper tasksDataMapper;

    public HomeworksDataMapper(Context context) {
        this.context = context;
        dbR = DbHelper.get(context).getReadableDb();
        dbW = DbHelper.get(context).getWriteableDb();
        subjectsDataMapper = new SubjectsDataMapper(context);
        tasksDataMapper = new TasksDataMapper(context);
    }

    public Cursor getAllHomeworksCursor() {
        return dbR.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public List<Homework> getAllHomeworks() {
        List<Homework> homeworks = new ArrayList<>();
        Cursor cursor = getAllHomeworksCursor();
        while (cursor.moveToNext()) {
            homeworks.add(getHomework(cursor));
        }
        cursor.close();
        return homeworks;
    }

    public Homework getHomeworkById(long id) {
        Cursor cursor = dbR.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                _ID + " = " + id, null);
        cursor.moveToFirst();
        Homework h = getHomework(cursor);
        cursor.close();
        return h;
    }

    public long addHomework(Homework homework) {
        ContentValues values = getContentValues(homework);
        return dbW.insert(TABLE_NAME, null, values);
    }

    public long updateHomework(Homework homework) {
        ContentValues values = getContentValues(homework);
        return dbW.update(TABLE_NAME, values,
                _ID + " = " + homework.getId(), null);
    }

    public void deleteHomework(Homework homework) {
        dbW.delete(TABLE_NAME, _ID + " = " + homework.getId(), null);
    }

    private ContentValues getContentValues(Homework homework) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, homework.getName());
        values.put(COLUMN_NAME_DESCRIPTION, homework.getDescription());
        values.put(COLUMN_NAME_EXPIRY_DATE, homework.getExpiryDate().getTime());
        values.put(COLUMN_NAME_PERCENTAGE, homework.getPercentage());
        values.put(COLUMN_NAME_SUBJECT_ID, homework.getSubject().getId());
        return values;
    }

    private Homework getHomework(Cursor cursor) {
        Homework hw = new Homework();
        hw.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        hw.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)));
        hw.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION)));
        long date = (cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_EXPIRY_DATE)));
        hw.setExpiryDate(new Date(date));
        hw.setPercentage(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_PERCENTAGE)));
        long subjectId = (cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_SUBJECT_ID)));
        hw.setSubject(SubjectManager.get(context).getSubject(subjectId));
        List<Task> tasks = tasksDataMapper.getTasksByHomework(hw);
        for (Task t : tasks) {
            hw.addTask(t);
        }
        return hw;
    }

}
