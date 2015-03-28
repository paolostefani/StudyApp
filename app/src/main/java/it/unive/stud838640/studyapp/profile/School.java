package it.unive.stud838640.studyapp.profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by paolo on 28/03/15.
 */
public class School {
    private long id;
    private String name, type;
    private ArrayList<Subject> subjects;

    public School(String name, String type) {
        this.name = name;
        this.type = type;
        subjects = new ArrayList<>();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Subject> getSubjects() {
        return Collections.unmodifiableList(subjects);
    }

    public Subject getSubject(long id) {
        for (Subject s : subjects) {
            if (s.id == id)
                return s;
        }
        return null;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void addSubject(Subject subject, int index) {
        subjects.add(index, subject);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }

    @Override
    public String toString() {
        return name;
    }

    public static class Subject {
        private long id;
        private String name, color;

        public Subject(String name, String color) {
            this.name = name;
            this.color = color;
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

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
