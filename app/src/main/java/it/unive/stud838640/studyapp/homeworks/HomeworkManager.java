package it.unive.stud838640.studyapp.homeworks;

import android.content.Context;

import java.util.Collections;
import java.util.List;

import it.unive.stud838640.studyapp.db.DbHelper;
import it.unive.stud838640.studyapp.db.HomeworksDataMapper;
import it.unive.stud838640.studyapp.db.TasksDataMapper;
import it.unive.stud838640.studyapp.profile.Profile;
import it.unive.stud838640.studyapp.profile.School;
import it.unive.stud838640.studyapp.profile.User;

/**
 * Created by AccStefani on 18/02/2015.
 */
public class HomeworkManager {
    private static HomeworkManager homeworkManager;
    private Context context;
    private List<Homework> homeworks;
    private List<School.Subject> subjects;
    private User user;
    private DbHelper dbHelper;
    private HomeworksDataMapper homeworksDataMapper;
    private TasksDataMapper tasksDataMapper;

    private HomeworkManager(Context context, DbHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
        homeworksDataMapper = new HomeworksDataMapper(context, dbHelper);
        tasksDataMapper = new TasksDataMapper(context, dbHelper);
        user = Profile.get(context, dbHelper).getUser();

        homeworks = homeworksDataMapper.getAllHomeworks();
    }

    public static HomeworkManager get(Context context, DbHelper dbHelper) {
        if (homeworkManager == null) {
            homeworkManager = new HomeworkManager(context.getApplicationContext(),
                    dbHelper);
        }
        return homeworkManager;
    }

    public List<Homework> getHomeworks() {
        return Collections.unmodifiableList(homeworks);
    }

    public Homework getHomework(long id) {
        for (Homework h : homeworks) {
            if (id == h.getId())
                return h;
        }
        return null;
    }

    public void addHomework(Homework homework) {
        homeworks.add(homework);
        homeworksDataMapper.addHomework(homework);
    }

    public void removeHomework(Homework homework) {
        homeworks.remove(homework);
        homeworksDataMapper.deleteHomework(homework);
    }

    public void updateHomework(Homework homework) {
        homeworksDataMapper.updateHomework(homework);
    }

    public void addTask(Task task, Homework homework) {
        homework.addTask(task);
        tasksDataMapper.addTask(task, homework);
    }

    public void removeTask(Task task, Homework homework) {
        homework.removeTask(task);
        tasksDataMapper.deleteTask(task);
    }

    public void updateTask(Task task, Homework homework) {
        tasksDataMapper.updateTask(task, homework);
    }

}
