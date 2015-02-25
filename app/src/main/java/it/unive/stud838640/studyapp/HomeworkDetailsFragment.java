package it.unive.stud838640.studyapp;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by AccStefani on 16/02/2015.
*/
public class HomeworkDetailsFragment extends Fragment {
    public static final String EXTRA_HOMEWORK_ID =
            "it.unive.stud838640.studyapp.homework_id";
    private Homework hw;
    private TextView nameField, leaderField, descrField,
            subjectField, expiryDateField, timeLeftField, subjectCircle;
    private ArrayAdapter<String> subjectsAdapter;

    public static HomeworkDetailsFragment newInstance(int homeworkId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_HOMEWORK_ID, homeworkId);
        HomeworkDetailsFragment fragment = new HomeworkDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int homeworkId = (int) getArguments().getSerializable(EXTRA_HOMEWORK_ID);
        hw = HomeworkManager.get(getActivity()).getHomework(homeworkId);
        getActivity().setTitle(hw.getName());
    }

    @Nullable
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
        String color = hw.getSubject().color;
        bgShape.setColor(Color.parseColor(color));
        subjectField = (TextView) v.findViewById(R.id.hwork_subject);
        subjectField.setText(hw.getSubject().name);
        expiryDateField = (TextView) v.findViewById(R.id.hwork_expiry_date);
        expiryDateField.setText(DateFormat.format("EEEE dd MMMM yyyy\nkk:mm",
                hw.getExpiryDate()));
        TextView hworkTimeLeft = (TextView) v.findViewById(R.id.hwork_time_left);
        String[] timeLeft = hw.getTimeLeft().split(",");
        String d = getResources().getString(R.string.day);
        String h = getResources().getString(R.string.hours);
        String and = getResources().getString(R.string.and);
        hworkTimeLeft.setText(timeLeft[0] + " " + d + " " + and +
                " " + timeLeft[1] + " " + h);

        return v;
    }
}
