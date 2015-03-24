package it.unive.stud838640.studyapp.profile;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by paolo on 24/02/15.
 */
public class SchoolManager {
    private static SchoolManager schoolManager;
    private Context context;
    private ArrayList<School> schools;
    private static int lastSchoolId, lastSubjectId;

    private SchoolManager(Context context) {
        this.context = context;
        schools = new ArrayList<>();
        schools.add(new School("E.Fermi", "Liceo Scientifico"));
        schools.get(0).addSubject(new Subject("Matematica", "#d77777"));
        schools.get(0).addSubject(new Subject("Fisica", "#bf56ac"));
        schools.get(0).addSubject(new Subject("Storia","#6ba5ac"));
        schools.get(0).addSubject(new Subject("Filosofia", "#dfa566"));
        schools.get(0).addSubject(new Subject("Latino", "#5aa573"));
        schools.add(new School("Algarotti", "Liceo Turistico"));
        schools.get(1).addSubject(new Subject("Storia", "#dfa566"));
        schools.get(1).addSubject(new Subject("Inglese", "#e08d55"));
        schools.get(1).addSubject(new Subject("Russo", "#5aa573"));
        schools.get(1).addSubject(new Subject("Tedesco", "#d77777"));
        schools.get(1).addSubject(new Subject("Economia Turistica", "#6ba5ac"));

    }

    public static SchoolManager get(Context context) {
        if (schoolManager == null)
            schoolManager = new SchoolManager(context.getApplicationContext());
        return schoolManager;
    }

    public List<School> getSchools() {
        return Collections.unmodifiableList(schools);
    }

    public School getSchool(int id) {
        for (School s : schools) {
            if (s.getId() == id)
                return s;
        }
        return null;
    }

    public void addSchool(School school) {
        schools.add(school);
    }

    public void removeSchool(School school) {
        schools.remove(school);
    }

    public class School {
        private String name, type;
        private ArrayList<Subject> subjects;
        private int id; // TODO change this temporary id with database id;

        public School(String name, String type) {
            this.name = name;
            this.type = type;
            subjects = new ArrayList<>();
            id = ++lastSchoolId;
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

        public int getId() {
            return id;
        }

        public Subject getSubject(int id) {
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
    }

    public static class Subject {
        public String name, color;
        public final int id; // TODO change this temporary id with database id;

        public Subject(String name, String color) {
            this.name = name;
            this.color = color;
            id = ++lastSubjectId;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
