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

    private HomeworkManager(Context context) {
        this.context = context;
        homeworks = new ArrayList<>();

        //TODO temp added homeworks for testing
        for (int i = 0; i < 30; i++) {
            Homework h = new Homework();
            h.setName("Homework " + h.getId());
            h.setDescription("This is Homework # " + h.getId());
            h.setLeader(context.getString(R.string.you));
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis() + (1000 * 3600 * 27));
            h.setExpiryDate(cal.getTime());
            homeworks.add(h);

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
