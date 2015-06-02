package it.unive.stud838640.studyapp.subject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import it.unive.stud838640.studyapp.R;

public class SubjectColorDialogFragment extends DialogFragment {
    public static final String EXTRA_COLOR =
            "it.unive.stud838640.studyapp.subjectColor";
    private SubjectManager subjectManager;
    private String title;

    public static SubjectColorDialogFragment newInstance() {
        SubjectColorDialogFragment fragment = new SubjectColorDialogFragment();
        return fragment;
    }

    private void sendResult(int resultCode, String color) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_COLOR, color);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
        this.dismiss();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        subjectManager = SubjectManager.get(getActivity());

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_color, null);
        GridView colorGridView = (GridView) v.findViewById(R.id.color_gridview);
        ColorAdapter colorAdapter = new ColorAdapter(subjectManager.getSubjectColors());
        colorGridView.setAdapter(colorAdapter);

        title = getString(R.string.select_color);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(title)
                .create();
    }

    private class ColorAdapter extends ArrayAdapter<String> {

        public ColorAdapter(String[] colors) {
            super(getActivity(), 0, colors);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_color, null);
            }

            final String color = getItem(position);
            final Button colorButton = (Button) convertView.findViewById(R.id.color_button);
            GradientDrawable bgShape = (GradientDrawable) colorButton.getBackground();
            bgShape.setColor(Color.parseColor(color));

            colorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendResult(Activity.RESULT_OK, color);
                }
            });

            return convertView;
        }
    }
}
