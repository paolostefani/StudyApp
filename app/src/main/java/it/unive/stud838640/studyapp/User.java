package it.unive.stud838640.studyapp;

/**
 * Created by paolo on 26/02/15.
 */
public class User {
    private String name;
    private SchoolManager.School school;

    public User(String name, SchoolManager.School school) {
        this.name = name;
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchoolManager.School getSchool() {
        return school;
    }

    public void setSchool(SchoolManager.School school) {
        this.school = school;
    }
}
