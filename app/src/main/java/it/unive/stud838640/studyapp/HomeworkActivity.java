package it.unive.stud838640.studyapp;

import android.app.Fragment;


public class HomeworkActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        int homeworkId = (int) getIntent()
                .getSerializableExtra(HomeworkDetailsFragment.EXTRA_HOMEWORK_ID);
        return HomeworkDetailsFragment.newInstance(homeworkId);
    }
}
