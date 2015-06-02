package it.unive.stud838640.studyapp.homework;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import it.unive.stud838640.studyapp.subject.Subject;

public class Homework {
    private final int HOUR_MILLI = 1000 * 3600;
    private final int DAY_MILLI = HOUR_MILLI * 24;
    private long id;
    private String name, description;
    private Subject subject;
    private Date expiryDate;
    private List<Task> tasks;

    public Homework() {
        expiryDate = new Date();
        tasks = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
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
        if (hours == 24) {
            days++;
            hours = 0;
        }
        days = days < 0 ? 0 : days;
        hours = hours < 0 ? 0 : hours;
        int[] dh = {days, hours};
        return dh;
    }

    public int getPercentage() {
        int tasksCompletedCount = 0;
        for (Task t : getTasks()) {
            if (t.isCompleted())
                tasksCompletedCount++;
        }
        float percentage = ((float) tasksCompletedCount / getTasks().size()) * 100;
        return Math.round(percentage);
    }

    public boolean isCompleted() {
        return getPercentage() == 100;
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    @Override
    public String toString() {
        return name;
    }
}
