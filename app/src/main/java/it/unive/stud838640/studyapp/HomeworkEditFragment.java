package it.unive.stud838640.studyapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by AccStefani on 16/02/2015.
*/
public class HomeworkEditFragment extends Fragment {
    public static final String EXTRA_HOMEWORK_ID =
            "it.unive.stud838640.studyapp.homework_id";
    private Homework hw;
    private EditText nameField, descriptionField;
    private Button expiryDateField, expiryTimeField;
    private SchoolManager.School school;
    private ArrayAdapter<SchoolManager.Subject> subjectsAdapter;
    private Spinner subjectsField;

    public static HomeworkEditFragment newInstance(int homeworkId) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_HOMEWORK_ID, homeworkId);
        HomeworkEditFragment fragment = new HomeworkEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        int homeworkId = (int) getArguments().getInt(EXTRA_HOMEWORK_ID);
        hw = HomeworkManager.get(getActivity()).getHomework(homeworkId);

        school = Profile.get(getActivity()).getUser().getSchool();
        subjectsAdapter = new ArrayAdapter<SchoolManager.Subject>(getActivity(),
                android.R.layout.simple_list_item_1, school.getSubjects());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homework_edit, container, false);

        nameField = (EditText) v.findViewById(R.id.hwork_name);
        descriptionField = (EditText) v.findViewById((R.id.hwork_description));
//        expiryDateField = (Button) v.findViewById(R.id.hwork_expiry_date);
//        expiryDateField.setText(DateFormat.format("EEEE dd MMMM yyyy", hw.getExpiryDate()));
//        expiryTimeField = (Button) v.findViewById(R.id.hwork_expiry_time);
//        expiryTimeField.setText(DateFormat.format("k:m", hw.getExpiryDate()));

        subjectsField = (Spinner) v.findViewById(R.id.hwork_subject);
        subjectsField.setAdapter(subjectsAdapter);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_homework_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_cancel:
                addHomework();
                return true;
            case R.id.menu_item_save_hwork:
                // TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addHomework() {
        Homework newHw = new Homework();
        newHw.setName(nameField.getText().toString());
        newHw.setDescription(descriptionField.getText().toString());
        HomeworkManager.get(getActivity()).getHomeworks().add(newHw);
    }
}
