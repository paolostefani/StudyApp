package it.unive.stud838640.studyapp.homework;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import it.unive.stud838640.studyapp.DateTimePickerFragment;
import it.unive.stud838640.studyapp.R;
import it.unive.stud838640.studyapp.subject.Subject;
import it.unive.stud838640.studyapp.subject.SubjectManager;

/**
 * Created by paolo on 16/02/2015.
*/
public class HomeworkEditFragment extends Fragment {
    public static final String EXTRA_HOMEWORK_ID =
            "it.unive.stud838640.studyapp.homework_id";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_SUBJECTS = "subjects";
    private static int REQUEST_DATE = 0;
    private static int REQUEST_TIME = 1;
    private static int REQUEST_SUBJECT = 2;
    private FragmentManager fragmentManager;
    private Homework homework;
    private HomeworkManager homeworkManager;
    private EditText nameField, descriptionField, subjectsField;
    private EditText expiryDateField, expiryTimeField;
    private Subject selectedSubject;
    private SubjectManager subjectManager;
    private Date expiryDate, exDateDate, exDateTime;
    private TextView addTaskField;
    private List<Task> tasks;
    private List<EditText> taskFieldList;
    private LinearLayout fieldsLayout;
    private boolean isHomeworkNew;

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
        homeworkManager = HomeworkManager.get(getActivity());
        subjectManager = SubjectManager.get(getActivity());
        long homeworkId = getArguments().getLong(EXTRA_HOMEWORK_ID);
        homework = homeworkManager.getHomework(homeworkId);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
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
            isHomeworkNew = false;
            nameField.setText(homework.getName());
            descriptionField.setText(homework.getDescription());
            selectedSubject = homework.getSubject();
            subjectsField.setText(selectedSubject.getName());
            expiryDate = homework.getExpiryDate();
            exDateDate = expiryDate;
            exDateTime = expiryDate;
            expiryDateField.setText(getDateText(expiryDate));
            expiryTimeField.setText(getTimeText(expiryDate));
            tasks = homework.getTasks();
        }
        else {
            isHomeworkNew = true;
            tasks = new ArrayList<>();
        }

        fieldsLayout = (LinearLayout) v.findViewById(R.id.fields_layout);
        taskFieldList = new ArrayList<>();
        for (Task t : tasks) {
            addTaskEditText(inflater, t.getName());
        }

        addTaskField = (TextView) v.findViewById(R.id.add_task);
        addTaskField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = addTaskEditText(inflater, "");
                et.setHint(getActivity().getResources()
                        .getString(R.string.task_label) + " " + taskFieldList.size());
            }
        });

        return v;
    }

    private EditText addTaskEditText(LayoutInflater inflater, String taskName) {
        if (taskFieldList == null)
            taskFieldList = new ArrayList<>();
        View taskListItem = inflater.inflate(R.layout.list_item_edit_task, null);
        EditText et = (EditText) taskListItem.findViewById(R.id.task_name);
        et.setText(taskName);
        taskFieldList.add(et);
        fieldsLayout.addView(taskListItem);
        return et;
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
            selectedSubject = subjectManager.getSubject(subjectId);
            subjectsField.setText(selectedSubject.getName());
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_item_delete_hwork).setVisible(!isHomeworkNew);
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
                    homeworkManager.removeHomework(homework);
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
        expiryDate = getSetExpiryDate(exDateDate, exDateTime);
        hw.setExpiryDate(expiryDate);
        if (isNew) {
            homeworkManager.addHomework(homework);
            for (int i = 0; i < taskFieldList.size(); i++) {
                Task t = new Task();
                t.setName(taskFieldList.get(i).getText().toString());
                t.setDescription("");
                homeworkManager.addTask(t, homework);
            }
        }
        else {
            homeworkManager.updateHomework(homework);
            for (int i = 0; i < tasks.size(); i++) {
                tasks.get(i).setName(taskFieldList.get(i).getText().toString());
                homeworkManager.updateTask(tasks.get(i), homework);
            }
            for (int i = tasks.size(); i < taskFieldList.size(); i++) {
                Task t = new Task();
                t.setName(taskFieldList.get(i).getText().toString());
                t.setDescription("");
                homeworkManager.addTask(t, homework);
            }
        }
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
