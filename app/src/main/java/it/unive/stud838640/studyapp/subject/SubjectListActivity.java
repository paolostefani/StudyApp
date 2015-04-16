package it.unive.stud838640.studyapp.subject;

import android.app.Fragment;

import it.unive.stud838640.studyapp.SingleFragmentActivity;

/**
 * Created by paolo on 07/04/2015.
 */
public class SubjectListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SubjectListFragment();
    }
}
