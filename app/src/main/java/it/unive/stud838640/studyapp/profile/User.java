package it.unive.stud838640.studyapp.profile;

/**
 * Created by paolo on 26/02/15.
 */
public class User {
    private long id;
    private String name;
    private SchoolManager.School school;

    public User(String name, SchoolManager.School school) {
        this.name = name;
        this.school = school;
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

    public SchoolManager.School getSchool() {
        return school;
    }

    public void setSchool(SchoolManager.School school) {
        this.school = school;
    }
}
