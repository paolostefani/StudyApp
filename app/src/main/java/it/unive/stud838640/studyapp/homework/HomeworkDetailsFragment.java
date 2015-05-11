package it.unive.stud838640.studyapp.homework;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.unive.stud838640.studyapp.R;

/**
 * Created by paolo on 16/02/2015.
*/
public class HomeworkDetailsFragment extends Fragment {
    public static final String EXTRA_HOMEWORK_ID =
            "it.unive.stud838640.studyapp.homework_id";
    private Homework hw;
    private HomeworkManager homeworkManager;
    private TextView nameField, descrField, subjectField, expiryDateField,
            subjectCircle, completionField;
    private List<Task> tasks;
    private List<TextView> taskFieldList;
    private LinearLayout fieldsLayout;

    public static HomeworkDetailsFragment newInstance(long homeworkId) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_HOMEWORK_ID, homeworkId);
        HomeworkDetailsFragment fragment = new HomeworkDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        long homeworkId = getArguments().getLong(EXTRA_HOMEWORK_ID);
        homeworkManager = HomeworkManager.get(getActivity());
        hw = homeworkManager.getHomework(homeworkId);
        tasks = hw.getTasks();
        getActivity().setTitle(hw.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homework_details, container, false);

        nameField = (TextView) v.findViewById(R.id.hwork_name);
        nameField.setText(hw.getName());
        descrField = (TextView) v.findViewById(R.id.hwork_description);
        descrField.setText(hw.getDescription());
        subjectCircle = (TextView) v.findViewById(R.id.hwork_subject_circle);
        GradientDrawable bgShape = (GradientDrawable) subjectCircle.getBackground();
        String color = hw.getSubject().getColor();
        bgShape.setColor(Color.parseColor(color));
        subjectField = (TextView) v.findViewById(R.id.hwork_subject);
        subjectField.setText(hw.getSubject().getName());
        expiryDateField = (TextView) v.findViewById(R.id.hwork_expiry_date_button);
        expiryDateField.setText(DateFormat.format("EEEE dd MMMM yyyy\nkk:mm",
                hw.getExpiryDate()));
        TextView hworkTimeLeft = (TextView) v.findViewById(R.id.hwork_time_left);
        int dLeft = hw.getTimeLeft()[0];
        int hLeft = hw.getTimeLeft()[1];
        if (dLeft == 0 && hLeft == 0) {
            hworkTimeLeft.setTextColor(Color.parseColor("#d77777"));
            hworkTimeLeft.setText(getResources().getString(R.string.expired));
        }
        else {
            String d = dLeft > 1 ? getResources().getString(R.string.days)
                    : getResources().getString(R.string.day);
            String h = hLeft > 1 ? getResources().getString(R.string.hours)
                    : getResources().getString(R.string.hour);
            String and = getResources().getString(R.string.and);
            hworkTimeLeft.setText(dLeft + " " + d + " " + and +
                    " " + hLeft + " " + h);
        }

        completionField = (TextView) v.findViewById(R.id.hwork_completion);
        setCompletionField();

        fieldsLayout = (LinearLayout) v.findViewById(R.id.fields_layout);
        taskFieldList = new ArrayList<>();
        for (Task t : tasks) {
            addTaskEditText(inflater, t);
        }

        if (tasks.size() == 0) {
            Task t = new Task();
            t.setName(hw.getName());
            homeworkManager.addTask(t, hw);
            addTaskEditText(inflater, t);
        }

        return v;
    }

    private void setCompletionField() {
        if (hw.getPercentage() == 100)
            completionField.setTextColor(Color.parseColor("#00c853"));
        else
            completionField.setTextColor(Color.BLACK);
        completionField.setText(hw.getPercentage() + "%");
    }

    private TextView addTaskEditText(LayoutInflater inflater, Task task) {
        if (taskFieldList == null)
            taskFieldList = new ArrayList<>();
        View taskListItem = inflater.inflate(R.layout.list_item_task, null);
        TextView tv = (TextView) taskListItem.findViewById(R.id.task_name);
        tv.setText(task.getName());
        taskFieldList.add(tv);
        fieldsLayout.addView(taskListItem);

        final Task t = task;
        CheckBox taskCompleted = (CheckBox) taskListItem.findViewById(R.id.task_completed);
        taskCompleted.setChecked(t.isCompleted());
        taskCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                t.setCompleted(isChecked);
                setCompletionField();
                Log.i("COMPLETAMENTO", Integer.toString(hw.getPercentage()));
                homeworkManager.updateTask(t, hw);
                // hw.setPercentage(t.);
            }
        });

        return tv;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_homework_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_edit:
                Fragment editFragment = HomeworkEditFragment.newInstance(hw.getId());
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, editFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
