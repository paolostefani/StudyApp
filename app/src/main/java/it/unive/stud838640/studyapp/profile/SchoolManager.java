package it.unive.stud838640.studyapp.profile;

import android.content.Context;

import java.util.Collections;
import java.util.List;

import it.unive.stud838640.studyapp.db.SchoolsDataMapper;
import it.unive.stud838640.studyapp.db.SubjectsDataMapper;

/**
 * Created by paolo on 24/02/15.
 */
public class SchoolManager {
    private static SchoolManager schoolManager;
    private Context context;
    private List<School> schools;
    private static int lastSchoolId, lastSubjectId;
    private SchoolsDataMapper schoolsDataMapper;
    private SubjectsDataMapper subjectsDataMapper;

    private SchoolManager(Context context) {
        this.context = context;
        schoolsDataMapper = new SchoolsDataMapper(context);
        subjectsDataMapper = new SubjectsDataMapper(context);

        schools = schoolsDataMapper.getAllSchools();
        if (schools.isEmpty()) {

            // Default Schools
            School school = new School("E.Fermi", "Liceo Scientifico");
            addSchool(school);
            addSubject(new School.Subject("Matematica", "#d77777"), school);
            addSubject(new School.Subject("Fisica", "#bf56ac"), school);
            addSubject(new School.Subject("Storia", "#6ba5ac"), school);
            addSubject(new School.Subject("Filosofia", "#dfa566"), school);
            addSubject(new School.Subject("Latino", "#5aa573"), school);

            school = new School("Algarotti", "Liceo Turistico");
            addSubject(new School.Subject("Storia", "#dfa566"), school);
            addSubject(new School.Subject("Inglese", "#e08d55"), school);
            addSubject(new School.Subject("Russo", "#5aa573"), school);
            addSubject(new School.Subject("Tedesco", "#d77777"), school);
            addSubject(new School.Subject("Economia Turistica", "#6ba5ac"), school);
            addSchool(school);
        }
    }

    public static SchoolManager get(Context context) {
        if (schoolManager == null)
            schoolManager = new SchoolManager(context.getApplicationContext());
        return schoolManager;
    }

    public List<School> getSchools() {
        return Collections.unmodifiableList(schools);
    }

    public School getSchool(long id) {
        for (School s : schools) {
            if (s.getId() == id)
                return s;
        }
        return null;
    }

    public void addSchool(School school) {
        schools.add(school);
        school.setId(schoolsDataMapper.addSchool(school));
    }

    public void removeSchool(School school) {
        schools.remove(school);
        schoolsDataMapper.deleteSchool(school);
    }

    public void updateSchool(School school) {
        schoolsDataMapper.updateSchool(school);
    }

    public void addSubject(School.Subject subject, School school) {
        school.addSubject(subject);
        subject.setId(subjectsDataMapper.addSubject(subject, school))   ;
    }

    public void removeSubject(School.Subject subject, School school) {
        school.removeSubject(subject);
        subjectsDataMapper.deleteSubject(subject);
    }

    public void updateSubject(School.Subject subject, School school) {
        subjectsDataMapper.updateSubject(subject, school);
    }

}
