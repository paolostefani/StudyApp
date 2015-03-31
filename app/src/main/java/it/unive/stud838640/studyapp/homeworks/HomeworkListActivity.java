package it.unive.stud838640.studyapp.homeworks;

import android.app.Fragment;

import it.unive.stud838640.studyapp.SingleFragmentActivity;
import it.unive.stud838640.studyapp.db.DbHelper;

/**
 * Created by paolo on 18/02/15.
 */
public class HomeworkListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new HomeworkListFragment();
    }

    @Override
    protected void onStop() {
        super.onStop();
        DbHelper.get(this).closeDb();
    }
}
