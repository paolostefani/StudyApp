package it.unive.stud838640.studyapp;

import android.app.ListFragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by paolo on 18/02/15.
 */
public class HomeworkListFragment extends ListFragment {
    private ArrayList<Homework> homeworks;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.homeworks_title);
        homeworks = HomeworkManager.get(getActivity()).getHomeworks();
        HomeworkAdapter hwAdapter = new HomeworkAdapter(homeworks);
        setListAdapter(hwAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Homework hw = ((HomeworkAdapter) getListAdapter()).getItem(position);
        Log.d("HomeworkListFragment", hw.getName());
        Toast.makeText(getActivity(), hw.getName(), Toast.LENGTH_SHORT).show();
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

            Homework hw = getItem(position);
            Button hworkButton = (Button) convertView.findViewById(R.id.hwork_button);
            hworkButton.setText(hw.getId() + "");
            GradientDrawable bgShape = (GradientDrawable) hworkButton.getBackground();
            String color = "";
            if (position % 2 == 0)
                color = "#00ff00";
            else
                color = "#0000ff";
            bgShape.setColor(Color.parseColor(color));


            return convertView;
        }
    }

}
