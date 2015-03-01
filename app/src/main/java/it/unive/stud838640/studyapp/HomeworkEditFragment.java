package it.unive.stud838640.studyapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by AccStefani on 16/02/2015.
*/
public class HomeworkEditFragment extends Fragment {
    public static final String EXTRA_HOMEWORK_ID =
            "it.unive.stud838640.studyapp.homework_id";
    private Homework homework;
    private EditText nameField, descriptionField;
    private Button expiryDateField, expiryTimeField;
    private SchoolManager.School school;
    private ArrayAdapter<SchoolManager.Subject> subjectsAdapter;
    private Spinner subjectsField;
    private TextView timeLeftField;

    public static HomeworkEditFragment newInstance(int homeworkId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_HOMEWORK_ID, homeworkId);
        HomeworkEditFragment fragment = new HomeworkEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int homeworkId = (int) getArguments().getSerializable(EXTRA_HOMEWORK_ID);
        homework = HomeworkManager.get(getActivity()).getHomework(homeworkId);

        school = Profile.get(getActivity()).getUser().getSchool();
        subjectsAdapter = new ArrayAdapter<SchoolManager.Subject>(getActivity(),
                android.R.layout.simple_list_item_1, school.getSubjects());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homework_edit, container, false);

//        nameField = (EditText) v.findViewById(R.id.hwork_name);
//        nameField.setText(homework.getName());
//        expiryDateField = (Button) v.findViewById(R.id.hwork_expiry_date);
//        expiryDateField.setText(DateFormat.format("EEEE dd MMMM yyyy", homework.getExpiryDate()));
//        expiryTimeField = (Button) v.findViewById(R.id.hwork_expiry_time);
//        expiryTimeField.setText(DateFormat.format("k:m", homework.getExpiryDate()));
//        timeLeftField = (TextView) v.findViewById(R.id.hwork_time_left);
//        timeLeftField.setText(homework.getTimeLeft());

        subjectsField = (Spinner) v.findViewById(R.id.hwork_subject);
        subjectsField.setAdapter(subjectsAdapter);
        return v;
    }
}
