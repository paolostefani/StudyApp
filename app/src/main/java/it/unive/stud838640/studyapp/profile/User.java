package it.unive.stud838640.studyapp.profile;

/**
 * Created by paolo on 26/02/15.
 */
public class User {
    private long id;
    private String name;
    private School school;

    public User() {
    }

    public void setId(long id) {
        this.id = id;
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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
