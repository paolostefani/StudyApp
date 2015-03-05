package it.unive.stud838640.studyapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by AccStefani on 16/02/2015.
*/
public class HomeworkEditFragment extends Fragment {
    public static final String EXTRA_HOMEWORK_ID =
            "it.unive.stud838640.studyapp.homework_id";
    private static final String DIALOG_DATE = "date";
    private static int REQUEST_DATE = 0;
    private static int REQUEST_TIME = 1;
    private Homework hw;
    private EditText nameField, descriptionField;
    private Button expiryDateField, expiryTimeField;
    private SchoolManager.School school;
    private ArrayAdapter<SchoolManager.Subject> subjectsAdapter;
    private Spinner subjectsField;
    private SchoolManager.Subject selectedSubject;
    private Date expiryDate;
    private int exYear, exMonth, exDay, exHour, exMinute;

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

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        nameField = (EditText) v.findViewById(R.id.hwork_name);
        descriptionField = (EditText) v.findViewById((R.id.hwork_description));
        expiryDateField = (Button) v.findViewById(R.id.hwork_expiry_date);
        expiryDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                DateTimePickerFragment dateDialog = DateTimePickerFragment
                        .newInstance(Calendar.getInstance().getTime(), "date");
                dateDialog.setTargetFragment(HomeworkEditFragment.this, REQUEST_DATE);
                dateDialog.show(fm, DIALOG_DATE);
            }
        });

        expiryTimeField = (Button) v.findViewById(R.id.hwork_expiry_time);
        expiryTimeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                DateTimePickerFragment timeDialog = DateTimePickerFragment
                        .newInstance(Calendar.getInstance().getTime(), "time");
                timeDialog.setTargetFragment(HomeworkEditFragment.this, REQUEST_TIME);
                timeDialog.show(fm, DIALOG_DATE);
            }
        });
//        expiryTimeField.setText(DateFormat.format("k:m", hw.getExpiryDate()));

        subjectsField = (Spinner) v.findViewById(R.id.hwork_subject);
        subjectsField.setAdapter(subjectsAdapter);
        selectedSubject = (SchoolManager.Subject) subjectsField.getSelectedItem();
        subjectsField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = (SchoolManager.Subject) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DateTimePickerFragment.EXTRA_DATE);
            expiryDateField.setText(DateFormat
                    .format("EEEE dd MMMM yyyy", date));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            exYear = calendar.get(Calendar.YEAR);
            exMonth = calendar.get(Calendar.MONTH);
            exDay = calendar.get(Calendar.DAY_OF_MONTH);
        }
        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data
                    .getSerializableExtra(DateTimePickerFragment.EXTRA_DATE);
            expiryTimeField.setText(DateFormat
                    .getTimeFormat(getActivity()).format(date));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            exHour = calendar.get(Calendar.HOUR);
            exMinute = calendar.get(Calendar.MINUTE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_homework_edit, menu);
    }

    private long lastClickTime = 0;
    @Override
    public synchronized boolean onOptionsItemSelected(MenuItem item) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000)
            return false;
        lastClickTime = SystemClock.elapsedRealtime();
        switch (item.getItemId()) {
            case R.id.menu_item_cancel:
                getActivity().finish();
                return true;
            case R.id.menu_item_save_hwork:
                addHomework();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addHomework() {
        Homework newHw = new Homework();
        newHw.setName(nameField.getText().toString());
        newHw.setDescription(descriptionField.getText().toString());
        newHw.setSubject(selectedSubject);
        expiryDate = new GregorianCalendar(exYear, exMonth, exDay, exHour, exMinute)
                .getTime();
        newHw.setExpiryDate(expiryDate);
        HomeworkManager.get(getActivity()).addHomework(newHw);
        getActivity().finish();
    }
}
