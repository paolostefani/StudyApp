package it.unive.stud838640.studyapp.homeworks;

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
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.unive.stud838640.studyapp.DateTimePickerFragment;
import it.unive.stud838640.studyapp.R;
import it.unive.stud838640.studyapp.profile.Profile;
import it.unive.stud838640.studyapp.profile.School;

/**
 * Created by AccStefani on 16/02/2015.
*/
public class HomeworkEditFragment extends Fragment {
    public static final String EXTRA_HOMEWORK_ID =
            "it.unive.stud838640.studyapp.homework_id";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_SUBJECTS = "subjects";
    private static int REQUEST_DATE = 0;
    private static int REQUEST_TIME = 1;
    private static int REQUEST_SUBJECT = 2;
    private static final String EX_DATE = "ex_date";
    private static final String EX_TIME = "ex_time";
    private FragmentManager fragmentManager;
    private Homework homework;
    private EditText nameField, descriptionField, subjectsField;
    private EditText expiryDateField, expiryTimeField;
    private School school;
    private School.Subject selectedSubject;
    //private String[] subjectsNames;
    private Date expiryDate, exDateDate, exDateTime;

    public static HomeworkEditFragment newInstance(long homeworkId) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_HOMEWORK_ID, homeworkId);
        HomeworkEditFragment fragment = new HomeworkEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        long homeworkId = getArguments().getLong(EXTRA_HOMEWORK_ID);
        homework = HomeworkManager.get(getActivity()).getHomework(homeworkId);
        school = Profile.get(getActivity()).getUser().getSchool();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homework_edit, container, false);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentManager = getActivity().getFragmentManager();

        nameField = (EditText) v.findViewById(R.id.hwork_name);
        descriptionField = (EditText) v.findViewById((R.id.hwork_description));

        subjectsField = (EditText) v.findViewById(R.id.hwork_subject);
        subjectsField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubjectsListDialogFragment subjectsDialog =
                        SubjectsListDialogFragment.newInstance();
                subjectsDialog.setTargetFragment(HomeworkEditFragment.this, REQUEST_SUBJECT);
                subjectsDialog.show(fragmentManager, DIALOG_SUBJECTS);
            }
        });

        expiryDateField = (EditText) v.findViewById(R.id.hwork_expiry_date);
        expiryDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerFragment dateDialog = DateTimePickerFragment
                        .newInstance(getSetDate(exDateDate), "date");
                dateDialog.setTargetFragment(HomeworkEditFragment.this, REQUEST_DATE);
                dateDialog.show(fragmentManager, DIALOG_DATE);
            }
        });

        expiryTimeField = (EditText) v.findViewById(R.id.hwork_expiry_time);
        expiryTimeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerFragment timeDialog = DateTimePickerFragment
                        .newInstance(getSetDate(exDateTime), "time");
                timeDialog.setTargetFragment(HomeworkEditFragment.this, REQUEST_TIME);
                timeDialog.show(fragmentManager, DIALOG_DATE);
            }
        });

        if (homework != null) {
            nameField.setText(homework.getName());
            descriptionField.setText(homework.getDescription());
            selectedSubject = homework.getSubject();
            subjectsField.setText(selectedSubject.getName());
            expiryDate = homework.getExpiryDate();
            exDateDate = expiryDate;
            exDateTime = expiryDate;
            expiryDateField.setText(getDateText(expiryDate));
            expiryTimeField.setText(getTimeText(expiryDate));
        }

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_DATE) {
            exDateDate = (Date) data
                    .getSerializableExtra(DateTimePickerFragment.EXTRA_DATE);
            expiryDateField.setText(getDateText(exDateDate));
        }
        if (requestCode == REQUEST_TIME) {
            exDateTime = (Date) data
                    .getSerializableExtra(DateTimePickerFragment.EXTRA_DATE);
            expiryTimeField.setText(getTimeText(exDateTime));
        }
        if (requestCode == REQUEST_SUBJECT) {
            long subjectId = data
                    .getLongExtra(SubjectsListDialogFragment.EXTRA_ID, 0);
            selectedSubject = school.getSubject(subjectId);
            subjectsField.setText(selectedSubject.getName());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_homework_edit, menu);
    }

    private long lastClickTime = 0;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000)
            return false;
        lastClickTime = SystemClock.elapsedRealtime();
        switch (item.getItemId()) {
            case R.id.menu_item_cancel:
                if (getFragmentManager().getBackStackEntryCount() == 0)
                    getActivity().finish();
                else
                    getFragmentManager().popBackStack();
                return true;
            case R.id.menu_item_delete_hwork:
                if (homework != null) {
                    HomeworkManager.get(getActivity()).removeHomework(homework);
                    getActivity().finish();
                }
                return true;
            case R.id.menu_item_save_hwork:
                boolean isNew = false;
                if (homework == null) {
                    isNew = true;
                    homework = new Homework();
                }
                saveHomework(homework, isNew);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveHomework(Homework hw, boolean isNew) {
        hw.setName(nameField.getText().toString());
        hw.setDescription(descriptionField.getText().toString());
        hw.setSubject(selectedSubject);
        hw.setOwner(Profile.get(getActivity()).getUser());
        expiryDate = getSetExpiryDate(exDateDate, exDateTime);
        hw.setExpiryDate(expiryDate);
        if (isNew)
            HomeworkManager.get(getActivity()).addHomework(homework);
        else
            HomeworkManager.get(getActivity()).updateHomework(homework);
    }

    private Date getSetExpiryDate(Date date, Date time) {
        Calendar cal = Calendar.getInstance();
        if (date != null && time != null) {
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            cal.setTime(time);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            return new GregorianCalendar(year, month, day, hour, minute).getTime();
        }
        return cal.getTime();
    }

    private Date getSetDate(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        return cal.getTime();
    }

    private String getDateText(Date date) {
        if (date != null)
            return DateFormat.format("EEEE dd MMMM yyyy", date)
                    .toString();
        return getString(R.string.date_label);
    }

    private String getTimeText(Date date) {
        if (date != null)
            return DateFormat.getTimeFormat(getActivity()).format(date);
        return getString(R.string.time_label);
    }
}
