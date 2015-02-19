package it.unive.stud838640.studyapp;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by paolo on 13/02/15.
 */
public class Homework {
    public static int lastId;
    private int id; // TODO change this temporary id with database id;
    private String name, description, subject, timeLeft;
    private Date expiryDate;
    private int percentage;
    private String leader; // TODO change String type to Team type
    private ArrayList<Task> tasks;

    public Homework() {
        tasks = new ArrayList<>();
        expiryDate = new Date();
        id = ++lastId;
    }

    public int getId() {
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

    @Override
    public String toString() {
        return name;
    }
}
