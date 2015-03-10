package it.unive.stud838640.studyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by paolo on 09/03/15.
 */
public class ListDialogFragment extends DialogFragment {
    public static final String EXTRA_LIST =
            "it.unive.stud838640.studyapp.dialogList";
    public static final String EXTRA_ITEM_WHICH =
            "it.unive.stud838640.studyapp.itemWhich";

    private String[] items;
    private String title;

    public static ListDialogFragment newInstance(String[] items) {
        Bundle args = new Bundle();
        args.putStringArray(EXTRA_LIST, items);
        ListDialogFragment fragment = new ListDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, int itemWhich) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_ITEM_WHICH,itemWhich);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        items = getArguments().getStringArray(EXTRA_LIST);
        title = getString(R.string.select_subject);

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK, which);
                    }
                })
                .create();
    }
}
