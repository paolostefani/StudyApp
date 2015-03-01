package it.unive.stud838640.studyapp;

import android.content.Context;

/**
 * Created by paolo on 28/02/15.
 */
public class Profile {
    private static Profile profile;
    private User user;

    private Profile(Context context) {
        user = new User(context.getString(R.string.you),
                SchoolManager.get(context).getSchool(1));
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