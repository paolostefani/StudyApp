package it.unive.stud838640.studyapp.subject;

import android.content.Context;

import java.util.Collections;
import java.util.List;

import it.unive.stud838640.studyapp.db.SchoolsDataMapper;
import it.unive.stud838640.studyapp.db.SubjectsDataMapper;
import it.unive.stud838640.studyapp.homework.Homework;
import it.unive.stud838640.studyapp.homework.HomeworkManager;
import it.unive.stud838640.studyapp.profile.School;

/**
 * Created by paolo on 24/02/15.
 */
public class SubjectManager {
    private static SubjectManager subjectManager;
    private Context context;
    private List<Subject> subjects;
    private SubjectsDataMapper subjectsDataMapper;
    private String[] subjectColors;

    private SubjectManager(Context context) {
        this.context = context;
        subjectsDataMapper = new SubjectsDataMapper(context);
        setSubjectColors();
        subjects = subjectsDataMapper.getAllSubjects();
        if (subjects.isEmpty()) {
            addSubject(new Subject("Matematica", "#d77777"));
            addSubject(new Subject("Fisica", "#bf56ac"));
            addSubject(new Subject("Storia", "#6ba5ac"));
            addSubject(new Subject("Filosofia", "#dfa566"));
            addSubject(new Subject("Latino", "#5aa573"));
            addSubject(new Subject("Inglese", "#e08d55"));
        }
    }

    public static SubjectManager get(Context context) {
        if (subjectManager == null)
            subjectManager = new SubjectManager(context.getApplicationContext());
        return subjectManager;
    }

    public List<Subject> getSubjects() {
        return Collections.unmodifiableList(subjects);
    }

    public Subject getSubject(long id) {
        for (Subject s : subjects) {
            if (s.getId() == id)
                return s;
        }
        return null;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
        subject.setId(subjectsDataMapper.addSubject(subject));
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
        ;
    }

    public void removeSubject(School.Subject subject, School school) {
        school.removeSubject(subject);
        subjectsDataMapper.deleteSubject(subject);
    }

    public void updateSubject(School.Subject subject, School school) {
        school.getSubject(subject.getId());
        List<Homework> hws = HomeworkManager.get(context).getHomeworks();
        subjectsDataMapper.updateSubject(subject, school);
    }

    public String[] getSubjectColors() {
        return subjectColors;
    }

    private void setSubjectColors() {
        subjectColors = new String[9];
        subjectColors[0] = "#d77777";
        subjectColors[1] = "#bf56ac";
        subjectColors[2] = "#6ba5ac";
        subjectColors[3] = "#dfa566";
        subjectColors[4] = "#5aa573";
        subjectColors[5] = "#5aa573";
        subjectColors[6] = "#5aa573";
        subjectColors[7] = "#5aa573";
        subjectColors[8] = "#5aa573";

    }

}
