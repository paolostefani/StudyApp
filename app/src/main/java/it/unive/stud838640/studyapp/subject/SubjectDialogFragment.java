package it.unive.stud838640.studyapp.subject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import it.unive.stud838640.studyapp.R;
import it.unive.stud838640.studyapp.profile.Profile;
import it.unive.stud838640.studyapp.profile.School;
import it.unive.stud838640.studyapp.profile.SchoolManager;

/**
 * Created by paolo on 10/04/15.
 */
public class SubjectDialogFragment extends DialogFragment {
    public static final String EXTRA_SUBJECT_ID =
            "it.unive.stud838640.studyapp.subject_id";
    private static final String DIALOG_COLOR = "color";
    private static final int REQUEST_COLOR = 0;
    private School.Subject subject;
    private String title;

    public static SubjectDialogFragment newInstance(long subjectId) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_SUBJECT_ID, subjectId);
        SubjectDialogFragment fragment = new SubjectDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        long subjectId = getArguments().getLong(EXTRA_SUBJECT_ID);
        subject = Profile.get(getActivity()).getUser().getSchool()
                .getSubject(subjectId);
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_subject, null);

        final EditText nameField = (EditText) v.findViewById(R.id.subject_name);
        if (subject != null) {
            nameField.setText(subject.getName());
            title = subject.getName();
        }

        final EditText colorField = (EditText) v.findViewById(R.id.subject_color);
        colorField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubjectColorDialogFragment colorDialog = SubjectColorDialogFragment
                        .newInstance();
                colorDialog.setTargetFragment(SubjectDialogFragment.this, REQUEST_COLOR);
                colorDialog.show(getActivity().getFragmentManager(), DIALOG_COLOR);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                subject.setName(nameField.getText().toString());
                                School school = Profile.get(getActivity())
                                        .getUser().getSchool();
                                SchoolManager.get(getActivity())
                                        .updateSubject(subject, school);
                                getTargetFragment().onResume();
                            }
                        })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_COLOR) {
            String color = data.getStringExtra(SubjectColorDialogFragment.EXTRA_COLOR);
            subject.setColor(color);
        }
    }
}
