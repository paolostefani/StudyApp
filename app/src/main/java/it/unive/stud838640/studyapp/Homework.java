package it.unive.stud838640.studyapp;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by paolo on 13/02/15.
 */
public class Homework {
    private String name, description, subject, timeLeft;
    private Date expiryDate, expiryTime;
    private int percentage;
    private String leader; // TODO change String type to Team type
    private ArrayList<Task> tasks;

    public Homework() {
        tasks = new ArrayList<>();
        expiryDate = new Date();
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
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

    public void setLeader(String leader) { this.leader = leader; }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
