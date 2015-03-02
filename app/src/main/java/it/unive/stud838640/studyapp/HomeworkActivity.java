package it.unive.stud838640.studyapp;

import android.app.Fragment;


public class HomeworkActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        String hwAction = getIntent().
                getStringExtra(HomeworkListFragment.EXTRA_HOMEWORK_ACTION);
        int homeworkId = (int) getIntent()
                .getIntExtra(HomeworkDetailsFragment.EXTRA_HOMEWORK_ID, -1);

        if (hwAction.equals("new"))
            return HomeworkEditFragment.newInstance(homeworkId);

        return HomeworkDetailsFragment.newInstance(homeworkId);
    }
}
