package it.unive.stud838640.studyapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import it.unive.stud838640.studyapp.profile.User;

/**
 * Created by paolo on 28/03/15.
 */
public class UsersDataMapper implements BaseColumns{

    /**************** Table schema ****************/

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_SCHOOL = "school";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_NAME + " TEXT, " +
                    COLUMN_NAME_SCHOOL + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_NAME_SCHOOL + ") REFERENCES " +
                    SchoolsDataMapper.TABLE_NAME + "(" + SchoolsDataMapper._ID + "));";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    /***********************************************/

    private Context context;
    private SQLiteDatabase dbR, dbW;
    private SchoolsDataMapper schoolsDataMapper;

    public UsersDataMapper(Context context) {
        this.context = context;
        dbR = DbHelper.get(context).getReadableDb();
        dbW = DbHelper.get(context).getWriteableDb();
        schoolsDataMapper = new SchoolsDataMapper(context);
    }


    public Cursor getAllUsersCursor() {
        return dbR.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        Cursor cursor = getAllUsersCursor();
        while (cursor.moveToNext()) {
            users.add(getUser(cursor));
        }
        cursor.close();
        return users;
    }

    public User getUserById(long id) {
        Cursor cursor = dbR.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                _ID + " = " + id, null);
        if (cursor.getCount() == 0)
            return null;
        cursor.moveToFirst();
        User u = getUser(cursor);
        cursor.close();
        return u;
    }

    public long addUser(User user) {
        ContentValues values = getContentValues(user);
        return dbW.insert(TABLE_NAME, null, values);
    }

    public long updateUser(User user) {
        ContentValues values = getContentValues(user);
        return dbW.update(TABLE_NAME, values,
                _ID + " = " + user.getId(), null);
    }

    public void deleteUser(User user) {
        dbW.delete(TABLE_NAME, _ID + " = " + user.getId(), null);
    }

    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, user.getName());
        values.put(COLUMN_NAME_SCHOOL, user.getSchool().getId());
        return values;
    }

    private User getUser(Cursor cursor) {
        User u = new User();
        u.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        u.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)));
        long schoolId = cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_SCHOOL));
        u.setSchool(schoolsDataMapper.getSchoolById(schoolId));
        return u;
    }

}
