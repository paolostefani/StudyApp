package it.unive.stud838640.studyapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by paolo on 13/02/15.
 */
public class Homework {
    public static int lastId;
    private final int HOUR_MILLI = 1000 * 3600;
    private final int DAY_MILLI = HOUR_MILLI * 24;
    private long id; // TODO change this temporary id with database id;
    private String name, description;
    private SchoolManager.Subject subject;
    private Date expiryDate;
    private int percentage;
    private User owner; // TODO change String type to Team type
    private ArrayList<Task> tasks;

    public Homework() {
        tasks = new ArrayList<>();
        expiryDate = new Date();
        id = ++lastId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SchoolManager.Subject getSubject() {
        return subject;
    }

    public void setSubject(SchoolManager.Subject subject) {
        this.subject = subject;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int[] getTimeLeft() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(expiryDate);
        long now = Calendar.getInstance().getTimeInMillis();
        long timeLeftMilli = cal.getTimeInMillis() - now;
        float daysF = (float) timeLeftMilli / (float) DAY_MILLI;
        int days = (int) daysF;
        int hours = Math.round((daysF - days) * 24);
        days = days < 0 ? 0 : days;
        hours = hours < 0 ? 0 : hours;
//        String dleft = (days > 0) ? days + " days and " : "";
//        timeLeft = days + "," + hours;
        int[] dh = {days, hours};
        return dh;
    }

    public int getPercentage() {
        return percentage;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return name;
    }
}
