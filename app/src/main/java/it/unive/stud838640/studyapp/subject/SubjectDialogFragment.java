package it.unive.stud838640.studyapp.subject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import it.unive.stud838640.studyapp.R;

/**
 * Created by paolo on 10/04/15.
 */
public class SubjectDialogFragment extends DialogFragment {
    public static final String EXTRA_SUBJECT_ID =
            "it.unive.stud838640.studyapp.subject_id";
    private static final String DIALOG_COLOR = "color";
    private static final int REQUEST_COLOR = 0;
    private Subject subject;
    private SubjectManager subjectManager;
    private String title, subjectColor;
    private boolean isSubjectNew;
    private GradientDrawable bgColorButton;

    public static SubjectDialogFragment newInstance(long subjectId) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_SUBJECT_ID, subjectId);
        SubjectDialogFragment fragment = new SubjectDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        subjectManager = SubjectManager.get(getActivity());
        long subjectId = getArguments().getLong(EXTRA_SUBJECT_ID);
        subject = subjectManager.getSubject(subjectId);
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_subject, null);

        final EditText nameField = (EditText) v.findViewById(R.id.subject_name);

        final Button colorButton = (Button) v.findViewById(R.id.subject_color);
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubjectColorDialogFragment colorDialog = SubjectColorDialogFragment
                        .newInstance();
                colorDialog.setTargetFragment(SubjectDialogFragment.this, REQUEST_COLOR);
                colorDialog.show(getActivity().getSupportFragmentManager(), DIALOG_COLOR);
            }
        });
        bgColorButton = (GradientDrawable) colorButton.getBackground();

        if (subject != null) {
            isSubjectNew = false;
            title = subject.getName();
            nameField.setText(subject.getName());
            subjectColor = subject.getColor();
            bgColorButton.setColor(Color.parseColor(subjectColor));
        } else {
            isSubjectNew = true;
            subjectColor = "#ef5350";
            bgColorButton.setColor(Color.parseColor(subjectColor));
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = nameField.getText().toString();
                                if (isSubjectNew)
                                    subject = new Subject("","");
                                subject.setName(name);
                                subject.setColor(subjectColor);
                                if (isSubjectNew)
                                    subjectManager.addSubject(subject);
                                else
                                    subjectManager.updateSubject(subject);
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
            subjectColor = data.getStringExtra(SubjectColorDialogFragment.EXTRA_COLOR);
            bgColorButton.setColor(Color.parseColor(subjectColor));
        }
    }
}
