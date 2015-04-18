package it.unive.stud838640.studyapp.subject;

import android.content.Context;

import java.util.Collections;
import java.util.List;

import it.unive.stud838640.studyapp.db.SubjectsDataMapper;

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
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
        subjectsDataMapper.deleteSubject(subject);
    }

    public void updateSubject(Subject subject) {
        subjectsDataMapper.updateSubject(subject);
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
