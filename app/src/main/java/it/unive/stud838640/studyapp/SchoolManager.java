package it.unive.stud838640.studyapp;

import android.content.Context;

import java.util.ArrayList;

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
        schools.get(0).addSubject("Matematica", "#d77777");
        schools.get(0).addSubject("Fisica", "#bf56ac");
        schools.get(0).addSubject("Storia","#6ba5ac");
        schools.get(0).addSubject("Filosofia", "#dfa566");
        schools.get(0).addSubject("Latino", "#5aa573");
        schools.add(new School("Algarotti", "Liceo Turistico"));
        schools.get(1).addSubject("Storia", "#dfa566");
        schools.get(1).addSubject("Inglese", "#e08d55");
        schools.get(1).addSubject("Russo", "#5aa573");
        schools.get(1).addSubject("Tedesco", "#d77777");
        schools.get(1).addSubject("Economia Turistica", "#6ba5ac");
    }

    public static SchoolManager get(Context context) {
        if (schoolManager == null)
            schoolManager = new SchoolManager(context.getApplicationContext());
        return schoolManager;
    }

    public ArrayList<School> getSchools() {
        return schools;
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

        public ArrayList<Subject> getSubjects() {
            return subjects;
        }

        public int getId() {
            return id;
        }

        public void addSubject(String name, String color) {
            subjects.add(new Subject(name, color));
        }

        public void removeSubject(int id) {
            for (int i = 0; i < subjects.size(); i++) {
                if (subjects.get(i).id == id) {
                    subjects.remove(i);
                    break;
                }
            }
        }
    }

    public class Subject {
        public String name, color;
        public final int id; // TODO change this temporary id with database id;

        public Subject(String name, String color) {
            this.name = name;
            this.color = color;
            id = ++lastSubjectId;
        }
    }
}
