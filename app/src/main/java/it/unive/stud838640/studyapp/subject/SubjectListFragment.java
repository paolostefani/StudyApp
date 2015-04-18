package it.unive.stud838640.studyapp.subject;

import android.app.ListFragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import it.unive.stud838640.studyapp.R;

/**
 * Created by paolo on 07/04/2015.
 */
public class SubjectListFragment extends ListFragment {
    private static final String DIALOG_SUBJECT = "subject";
    private static int SUBJECT_TARGET = 0;
    private SubjectManager subjectManager;
    private SubjectAdapter subjectAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.subjects);
        subjectManager = SubjectManager.get(getActivity());
        subjectAdapter = new SubjectAdapter(subjectManager.getSubjects());
        setListAdapter(subjectAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_subject_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_subject:
                showSubjectDialog(-1);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Subject s = (Subject) getListAdapter().getItem(position);
        showSubjectDialog(s.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private class SubjectAdapter extends ArrayAdapter<Subject>{

        public SubjectAdapter(List<Subject> subjects) {
            super(getActivity(), 0, subjects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_subject, null);
            }

            Subject s = getItem(position);

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

    private void showSubjectDialog(long subjectId) {
        SubjectDialogFragment subjectDialog = SubjectDialogFragment
                .newInstance(subjectId);
        subjectDialog.setTargetFragment(this, SUBJECT_TARGET);
        subjectDialog.show(getActivity().getFragmentManager(), DIALOG_SUBJECT);
    }
}
