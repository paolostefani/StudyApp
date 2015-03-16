package it.unive.stud838640.studyapp;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by paolo on 18/02/15.
 */
public class HomeworkListFragment extends Fragment {
    public static final String EXTRA_HOMEWORK_ACTION =
            "it.unive.stud838640.studyapp.homework_action";
    private List<Homework> homeworks;
    private GridView hwGridView;
    private HomeworkAdapter hwAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.homeworks_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homework_grid, container, false);
        hwGridView = (GridView) v.findViewById(R.id.hwork_gridview);
        homeworks = HomeworkManager.get(getActivity()).getHomeworks();
        hwAdapter = new HomeworkAdapter(homeworks);
        hwGridView.setAdapter(hwAdapter);
        
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_homework_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_hwork:
                Intent i = new Intent(getActivity(), HomeworkActivity.class);
                i.putExtra(EXTRA_HOMEWORK_ACTION, "new");
                startActivity(i);
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hwAdapter.notifyDataSetChanged();
        //hwGridView.invalidateViews();
    }

    private class HomeworkAdapter extends ArrayAdapter<Homework> {

        public HomeworkAdapter(List<Homework> homeworks) {
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
            String color = hw.getSubject().color;
            bgShape.setColor(Color.parseColor(color));

            TextView hworkName = (TextView) convertView.findViewById(R.id.hwork_name);
            hworkName.setText(hw.getName());

            TextView hworkTimeLeft = (TextView) convertView.findViewById(R.id.hwork_time_left);
            int dLeft = hw.getTimeLeft()[0];
            int hLeft = hw.getTimeLeft()[1];
            if (dLeft == 0 && hLeft == 0) {
                hworkTimeLeft.setTypeface(null, Typeface.BOLD);
                hworkTimeLeft.setText(getResources().getString(R.string.expired));
            }
            else {
                String d = getResources().getString(R.string.d_day);
                String h = getResources().getString(R.string.h_hours);
                hworkTimeLeft.setText(dLeft + d + " " + hLeft + h);
            }

            hworkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), HomeworkActivity.class);
                    i.putExtra(EXTRA_HOMEWORK_ACTION, "showdetails");
                    i.putExtra(HomeworkDetailsFragment.EXTRA_HOMEWORK_ID, hw.getId());
                    startActivity(i);
                }
            });

            return convertView;
        }
    }

    private class UpdateTimeLeftTask extends AsyncTask

}
