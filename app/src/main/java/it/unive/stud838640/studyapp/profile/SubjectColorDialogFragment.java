package it.unive.stud838640.studyapp.profile;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import it.unive.stud838640.studyapp.R;

/**
 * Created by paolo on 11/04/15.
 */
public class SubjectColorDialogFragment extends DialogFragment {
    public static final String EXTRA_COLOR =
            "it.unive.stud838640.studyapp.subjectColor";
    private String[] colors;
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
