package it.unive.stud838640.studyapp;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by paolo on 13/02/15.
 */
public class Homework {
    private String name, description, subject, timeLeft;
    private Date expiry;
    private int percentage;
    private String leader; // TODO change String type to Team type
    private ArrayList<Task> tasks;

    public Homework() {
        leader = "You";
        tasks = new ArrayList<>();
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public int getPercentage() {
        return percentage;
    }

    public String getLeader() {
        return leader;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
