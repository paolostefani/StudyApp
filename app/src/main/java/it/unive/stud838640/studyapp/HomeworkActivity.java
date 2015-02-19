package it.unive.stud838640.studyapp;

import android.app.Fragment;


public class HomeworkActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new HomeworkFragment();
    }
}
