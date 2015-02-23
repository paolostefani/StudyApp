package it.unive.stud838640.studyapp;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by paolo on 18/02/15.
 */
public class HomeworkListFragment extends Fragment {
    private ArrayList<Homework> homeworks;
    private GridView hwGridView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.homeworks_title);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homework_grid, container, false);
        hwGridView = (GridView) v.findViewById(R.id.hwork_gridview);
        homeworks = HomeworkManager.get(getActivity()).getHomeworks();
        hwGridView.setAdapter(new HomeworkAdapter(homeworks));
        
        return v;
    }

    private class HomeworkAdapter extends ArrayAdapter<Homework> {

        public HomeworkAdapter(ArrayList<Homework> homeworks) {
            super(getActivity(), 0, homeworks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_homework, null);
            }

            final Homework hw = getItem(position);

            Button hworkButton = (Button) convertView.findViewById(R.id.hwork_button);
            //hworkButton.setText(hw.getId() + "");
            GradientDrawable bgShape = (GradientDrawable) hworkButton.getBackground();
            String color = "";
            if (position % 2 == 0)
                color = "#ff3000";
            else
                color = "#ff2090";
            bgShape.setColor(Color.parseColor(color));

            TextView hworkName = (TextView) convertView.findViewById(R.id.hwork_name);
            hworkName.setText(hw.getName());

            TextView hworkTimeLeft = (TextView) convertView.findViewById(R.id.hwork_time_left);
            String[] timeLeft = hw.getTimeLeft().split(",");
            String d = getResources().getString(R.string.d_day);
            String h = getResources().getString(R.string.h_hours);
            hworkTimeLeft.setText(timeLeft[0] + d + " " + timeLeft[1] + h);


            hworkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), HomeworkActivity.class);
                    i.putExtra(HomeworkFragment.EXTRA_HOMEWORK_ID, hw.getId());
                    startActivity(i);
                }
            });

            return convertView;
        }
    }

}
