package it.unive.stud838640.studyapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import it.unive.stud838640.studyapp.homeworks.Homework;
import it.unive.stud838640.studyapp.homeworks.Task;

/**
 * Created by paolo on 28/03/15.
 */
public class TasksDataMapper implements BaseColumns{

    /**************** Table schema ****************/

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
                    HomeworksDataMapper.TABLE_NAME +
                    "(" + HomeworksDataMapper._ID + "));";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    /***********************************************/

    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public TasksDataMapper(Context context, SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.context = context;
    }

    public Cursor getAllTasksCursor() {
        db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public List<Task> getTasksByHomework(Homework homework) {
        List<Task> tasks = new ArrayList<>();

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                 COLUMN_NAME_HOMEWORK + " = " + homework.getId(), null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            tasks.add(getTask(cursor));
        }
        return tasks;
    }

    public Task getTaskById(long id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                _ID + " = " + id, null);
        return getTask(cursor);
    }

    public long addTask(Task task, Homework homework) {
        ContentValues values = getContentValues(task, homework);
        db = dbHelper.getWritableDatabase();
        return db.insert(TABLE_NAME, null, values);
    }

    public long updateTask(Task task, Homework homework) {
        ContentValues values = getContentValues(task, homework);
        db = dbHelper.getWritableDatabase();
        return db.update(TABLE_NAME, values,
                _ID + " = " + task.getId(), null);
    }

    public void deleteTask(Task task) {
        db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, _ID + " = " + task.getId(), null);
    }

    private ContentValues getContentValues(Task task, Homework homework) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, task.getName());
        values.put(COLUMN_NAME_DESCRIPTION, task.getDescription());
        int isCompleted = task.isCompleted() ? 1 : 0;
        values.put(COLUMN_NAME_COMPLETED, isCompleted);
        values.put(COLUMN_NAME_HOMEWORK, homework.getId());
        return values;
    }

    private Task getTask(Cursor cursor) {
        Task t = new Task();
        t.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        t.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)));
        t.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION)));
        int isCompletedInt = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_COMPLETED));
        boolean isCompleted = (isCompletedInt == 1) ? true : false;
        t.setCompleted(isCompleted);
        return t;
    }

}
