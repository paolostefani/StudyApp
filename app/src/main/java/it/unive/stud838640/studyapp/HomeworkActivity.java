package it.unive.stud838640.studyapp;

import android.app.Fragment;


public class HomeworkActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        int homeworkId = (int) getIntent()
                .getSerializableExtra(HomeworkFragment.EXTRA_HOMEWORK_ID);
        return HomeworkFragment.newInstance(homeworkId);
    }
}
