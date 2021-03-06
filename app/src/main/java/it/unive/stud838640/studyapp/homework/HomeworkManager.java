package it.unive.stud838640.studyapp.homework;

import android.content.Context;

import java.util.Collections;
import java.util.List;

import it.unive.stud838640.studyapp.db.HomeworksDataMapper;
import it.unive.stud838640.studyapp.db.TasksDataMapper;

public class HomeworkManager {
    private static HomeworkManager homeworkManager;
    private List<Homework> homeworks;
    private HomeworksDataMapper homeworksDataMapper;
    private TasksDataMapper tasksDataMapper;

    private HomeworkManager(Context context) {
        homeworksDataMapper = new HomeworksDataMapper(context);
        tasksDataMapper = new TasksDataMapper(context);
        homeworks = homeworksDataMapper.getAllHomeworks();
    }

    public static HomeworkManager get(Context context) {
        if (homeworkManager == null) {
            homeworkManager = new HomeworkManager(context.getApplicationContext());
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
        homework.setId(homeworksDataMapper.addHomework(homework));
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
        task.setId(tasksDataMapper.addTask(task, homework));
    }

    public void removeTask(Task task, Homework homework) {
        homework.removeTask(task);
        tasksDataMapper.deleteTask(task);
    }

    public void updateTask(Task task, Homework homework) {
        tasksDataMapper.updateTask(task, homework);
    }
}
