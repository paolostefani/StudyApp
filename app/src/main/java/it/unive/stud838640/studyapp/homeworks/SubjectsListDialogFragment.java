package it.unive.stud838640.studyapp.homeworks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import it.unive.stud838640.studyapp.R;
import it.unive.stud838640.studyapp.profile.Profile;
import it.unive.stud838640.studyapp.profile.School;

/**
 * Created by AccStefani on 11/03/2015.
 */
public class SubjectsListDialogFragment extends DialogFragment {
    public static final String EXTRA_ID =
            "it.unive.stud838640.studyapp.subjectId";

    private List<School.Subject> subjects;
    private String title;

    public static SubjectsListDialogFragment newInstance() {
        SubjectsListDialogFragment fragment = new SubjectsListDialogFragment();
        return fragment;
    }

    private void sendResult(int resultCode, long subjectId) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_ID, subjectId);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        subjects = Profile.get(getActivity()).getUser().getSchool().getSubjects();
        title = getString(R.string.select_subject);

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setAdapter(new ArrayAdapter<School.Subject>(getActivity(),
                        android.R.layout.simple_list_item_1, subjects), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog) dialog).getListView();
                        School.Subject subject = (School.Subject) lw.getAdapter().getItem(which);
                        sendResult(Activity.RESULT_OK, subject.getId());
                    }
                })
                .create();
    }
}
