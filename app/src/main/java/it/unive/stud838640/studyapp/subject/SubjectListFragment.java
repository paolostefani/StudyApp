package it.unive.stud838640.studyapp.subject;

import android.support.v4.app.ListFragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
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
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ListView listView = (ListView) v.findViewById(android.R.id.list);

        // Contextual Action Bar implementation
        listView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.contextual_menu_fragment_homework_list, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_delete_hwork:
                        for (int i = 0; i < subjectAdapter.getCount(); i++) {
                            if (getListView().isItemChecked(i))
                                subjectManager.removeSubject(subjectAdapter.getItem(i));
                        }
                        mode.finish();
                        subjectAdapter.notifyDataSetChanged();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        return v;
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
        subjectDialog.show(getActivity().getSupportFragmentManager(), DIALOG_SUBJECT);
    }
}
