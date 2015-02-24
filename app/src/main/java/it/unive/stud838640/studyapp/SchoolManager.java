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
    private static int lastId;

    private SchoolManager(Context context) {
        this.context = context;
        schools = new ArrayList<>();
        schools.add(new School("E.Fermi", "Liceo Scientifico"));
        schools.get(0).subjects.add("Matematica");
        schools.get(0).subjects.add("Fisica");
        schools.get(0).subjects.add("Storia");
        schools.get(0).subjects.add("Filosofia");
        schools.get(0).subjects.add("Latino");
        schools.add(new School("Algarotti", "Liceo Turistico"));
        schools.get(1).subjects.add("Storia");
        schools.get(1).subjects.add("Inglese");
        schools.get(1).subjects.add("Russo");
        schools.get(1).subjects.add("Tedesco");
        schools.get(1).subjects.add("Economia Turistica");
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
        public String name, type;
        public final ArrayList<String> subjects;
        public final int id; // TODO change this temporary id with database id;

        public School(String name, String type) {
            this.name = name;
            this.type = type;
            subjects = new ArrayList<>();
            id = ++lastId;
        }
    }
}
