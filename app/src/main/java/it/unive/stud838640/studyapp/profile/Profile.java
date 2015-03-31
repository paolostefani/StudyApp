package it.unive.stud838640.studyapp.profile;

import android.content.Context;

import java.util.List;

import it.unive.stud838640.studyapp.R;
import it.unive.stud838640.studyapp.db.UsersDataMapper;

/**
 * Created by paolo on 28/02/15.
 */
public class Profile {
    private static Profile profile;
    private User user;
    private UsersDataMapper usersDataMapper;

    private Profile(Context context) {
        usersDataMapper = new UsersDataMapper(context);
        List<User> users = usersDataMapper.getAllUsers();

        if (users.isEmpty()) {
            user = new User();
            user.setName(context.getString(R.string.you));
            user.setSchool(SchoolManager.get(context).getSchools().get(0));
            usersDataMapper.addUser(user);
        }
        else
            user = users.get(0);
    }

    public static Profile get(Context context) {
        if (profile == null)
            profile = new Profile(context.getApplicationContext());
        return profile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}