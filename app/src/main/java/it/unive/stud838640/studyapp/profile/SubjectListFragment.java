package it.unive.stud838640.studyapp.profile;

import android.app.Fragment;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by AccStefani on 07/04/2015.
 */
public class SubjectListFragment extends Fragment {

    private class SubjectAdapter extends ArrayAdapter<School.Subject>{

        public SubjectAdapter(List<School.Subject> subjects) {
            super(getActivity(), 0, subjects);
        }
    }
}
