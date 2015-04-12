package it.unive.stud838640.studyapp.profile;

import android.app.ListFragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import it.unive.stud838640.studyapp.R;

/**
 * Created by AccStefani on 07/04/2015.
 */
public class SubjectListFragment extends ListFragment {
    private static final String DIALOG_SUBJECT = "subject";
    private static int SUBJECT_TARGET = 0;
    private List<School.Subject> subjects;
    private SubjectAdapter subjectAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.subjects);
        subjects = Profile.get(getActivity()).getUser().getSchool().getSubjects();
        subjectAdapter = new SubjectAdapter(subjects);
        setListAdapter(subjectAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        School.Subject s = (School.Subject) getListAdapter().getItem(position);
        SubjectDialogFragment subjectDialog = SubjectDialogFragment
                .newInstance(s.getId());
        subjectDialog.setTargetFragment(this, SUBJECT_TARGET);
        subjectDialog.show(getActivity().getFragmentManager(), DIALOG_SUBJECT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private class SubjectAdapter extends ArrayAdapter<School.Subject>{

        public SubjectAdapter(List<School.Subject> subjects) {
            super(getActivity(), 0, subjects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_subject, null);
            }

            School.Subject s = getItem(position);

            TextView subjectName = (TextView) convertView.findViewById(R.id.subject_name);
            subjectName.setText(s.getName());
            TextView subjectColor = (TextView) convertView.findViewById(R.id.subject_color);
            GradientDrawable bgShape = (GradientDrawable) subjectColor.getBackground();
            bgShape.setColor(Color.parseColor(s.getColor()));

            return convertView;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        subjectAdapter.notifyDataSetChanged();
    }
}
