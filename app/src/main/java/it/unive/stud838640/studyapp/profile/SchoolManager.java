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
        schools.get(0).addSubject(new School.Subject("Matematica", "#d77777"));
        schools.get(0).addSubject(new School.Subject("Fisica", "#bf56ac"));
        schools.get(0).addSubject(new School.Subject("Storia","#6ba5ac"));
        schools.get(0).addSubject(new School.Subject("Filosofia", "#dfa566"));
        schools.get(0).addSubject(new School.Subject("Latino", "#5aa573"));
        schools.add(new School("Algarotti", "Liceo Turistico"));
        schools.get(1).addSubject(new School.Subject("Storia", "#dfa566"));
        schools.get(1).addSubject(new School.Subject("Inglese", "#e08d55"));
        schools.get(1).addSubject(new School.Subject("Russo", "#5aa573"));
        schools.get(1).addSubject(new School.Subject("Tedesco", "#d77777"));
        schools.get(1).addSubject(new School.Subject("Economia Turistica", "#6ba5ac"));

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

}
