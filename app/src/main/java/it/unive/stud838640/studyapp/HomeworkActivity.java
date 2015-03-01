package it.unive.stud838640.studyapp;

import android.app.Fragment;


public class HomeworkActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        String hwAction = getIntent().
                getStringExtra(HomeworkListFragment.EXTRA_HOMEWORK_ACTION);
        if (hwAction.equals("new"))
            return HomeworkEditFragment.newInstance(-1);
        int homeworkId = (int) getIntent()
                .getSerializableExtra(HomeworkDetailsFragment.EXTRA_HOMEWORK_ID);
        return HomeworkDetailsFragment.newInstance(homeworkId);
    }
}
