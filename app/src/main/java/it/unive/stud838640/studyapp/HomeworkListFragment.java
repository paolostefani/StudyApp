package it.unive.stud838640.studyapp;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

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
            hworkButton.setText(hw.getId() + "");
            GradientDrawable bgShape = (GradientDrawable) hworkButton.getBackground();
            String color = "";
            if (position % 2 == 0)
                color = "#00ff00";
            else
                color = "#0000ff";
            bgShape.setColor(Color.parseColor(color));

            hworkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("HomeworkListFragment", hw.getName());
                    Toast.makeText(getActivity(), hw.getName(), Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }
    }

}
