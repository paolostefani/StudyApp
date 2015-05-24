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
            addSubject(new Subject("Matematica", "#ef5350"));
            addSubject(new Subject("Fisica", "#ec407a"));
            addSubject(new Subject("Storia", "#ab47bc"));
            addSubject(new Subject("Filosofia", "#5c6bc0"));
            addSubject(new Subject("Latino", "#26c6da"));
            addSubject(new Subject("Inglese", "#66bb6a"));
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
//        subjectColors[5] = "#5aa573";
//        subjectColors[6] = "#5aa573";
//        subjectColors[7] = "#5aa573";
//        subjectColors[8] = "#5aa573";
//        subjectColors[0] = "#ef5350";
//        subjectColors[1] = "#ec407a";
//        subjectColors[2] = "#ab47bc";
//        subjectColors[3] = "#5c6bc0";
//        subjectColors[4] = "#26c6da";
        subjectColors[5] = "#66bb6a";
        subjectColors[6] = "#d4e157";
        subjectColors[7] = "#ffa726";
        subjectColors[8] = "#78909c";
    }

}
