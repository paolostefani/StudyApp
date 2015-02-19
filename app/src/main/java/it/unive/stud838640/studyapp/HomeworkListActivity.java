package it.unive.stud838640.studyapp;

import android.app.Fragment;

/**
 * Created by paolo on 18/02/15.
 */
public class HomeworkListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new HomeworkListFragment();
    }
}
