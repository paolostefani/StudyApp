package it.unive.stud838640.studyapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        args.putInt(EXTRA_HOMEWORK_ID, homeworkId);
        HomeworkDetailsFragment fragment = new HomeworkDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        int homeworkId = (int) getArguments().getInt(EXTRA_HOMEWORK_ID);
        hw = HomeworkManager.get(getActivity()).getHomework(homeworkId);
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
        String color = hw.getSubject().color;
        bgShape.setColor(Color.parseColor(color));
        subjectField = (TextView) v.findViewById(R.id.hwork_subject);
        subjectField.setText(hw.getSubject().name);
        expiryDateField = (TextView) v.findViewById(R.id.hwork_expiry_date_button);
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
                transaction.replace(R.id.fragment_container, editFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
