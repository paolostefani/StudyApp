package it.unive.stud838640.studyapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AccStefani on 16/02/2015.
*/
public class HomeworkFragment extends Fragment {
    public static final String EXTRA_HOMEWORK_ID =
            "it.unive.stud838640.studyapp.homework_id";
    private Homework homework;
    private EditText nameField, leaderField;
    private Button expiryDateField, expiryTimeField;
    private ArrayList<String> subjects;
    private ArrayAdapter<String> subjectsAdapter;
    private Spinner subjectsField;
    private TextView timeLeftField;

    public static HomeworkFragment newInstance(int homeworkId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_HOMEWORK_ID, homeworkId);
        HomeworkFragment fragment = new HomeworkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int homeworkId = (int) getArguments().getSerializable(EXTRA_HOMEWORK_ID);
        homework = HomeworkManager.get(getActivity()).getHomework(homeworkId);

        homework.setLeader(getActivity().getString(R.string.you));

        subjects = new ArrayList<>();
        subjects.add("Matematica");
        subjects.add("Lettere");
        subjects.add("Fisica");
        subjects.add("Inglese");
        subjects.add("Latino");
        subjectsAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, subjects);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_homework, container, false);

        nameField = (EditText) rootView.findViewById(R.id.hwork_name);
        nameField.setText(homework.getName());
        leaderField = (EditText) rootView.findViewById(R.id.hwork_leader);
        leaderField.setText(homework.getLeader());
        expiryDateField = (Button) rootView.findViewById(R.id.hwork_expiry_date);
        expiryDateField.setText(DateFormat.format("EEEE dd MMMM yyyy", homework.getExpiryDate()));
        expiryTimeField = (Button) rootView.findViewById(R.id.hwork_expiry_time);
        expiryTimeField.setText(DateFormat.format("k:m", homework.getExpiryDate()));
        subjectsField = (Spinner) rootView.findViewById(R.id.hwork_subject);
        subjectsField.setAdapter(subjectsAdapter);
        timeLeftField = (TextView) rootView.findViewById(R.id.hwork_time_left);
        timeLeftField.setText(homework.getTimeLeft());


        return rootView;
    }
}
