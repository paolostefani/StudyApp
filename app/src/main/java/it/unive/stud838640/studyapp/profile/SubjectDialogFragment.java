package it.unive.stud838640.studyapp.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import it.unive.stud838640.studyapp.R;

/**
 * Created by paolo on 10/04/15.
 */
public class SubjectDialogFragment extends DialogFragment {
    public static final String EXTRA_SUBJECT_ID =
            "it.unive.stud838640.studyapp.subject_id";
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
}
