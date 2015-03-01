package it.unive.stud838640.studyapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by AccStefani on 18/02/2015.
 */
public class HomeworkManager {
    private static HomeworkManager homeworkManager;
    private Context context;
    private ArrayList<Homework> homeworks;
    private ArrayList<SchoolManager.Subject> subjects;
    private User user;
    private SchoolManager.School school;

    private HomeworkManager(Context context) {
        this.context = context;
        homeworks = new ArrayList<>();
        user = Profile.get(context).getUser();
        school = user.getSchool();

        //TODO temp added homeworks for testing
        for (int i = 0; i < 30; i++) {
            Homework hw = new Homework();
            hw.setName("Homework " + hw.getId());
            hw.setDescription(context.getResources().getString(R.string.lorem_ipsum_short));
            hw.setLeader(user.getName());
            hw.setSubject(school.getSubject((i % school.getSubjects().size()) + 1));
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis() + (1000 * 3600 * 27));
            hw.setExpiryDate(cal.getTime());
            homeworks.add(hw);

        }

    }

    public static HomeworkManager get(Context context) {
        if (homeworkManager == null) {
            homeworkManager = new HomeworkManager(context.getApplicationContext());
        }
        return homeworkManager;
    }

    public ArrayList<Homework> getHomeworks() {
        return homeworks;
    }

    public Homework getHomework(int id) {
        for (Homework h : homeworks) {
            if (id == h.getId())
                return h;
        }
        return null;
    }
}
