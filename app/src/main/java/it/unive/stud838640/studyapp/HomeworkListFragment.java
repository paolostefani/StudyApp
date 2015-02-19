package it.unive.stud838640.studyapp;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        ArrayAdapter<Homework> hwAdapter = new ArrayAdapter<Homework>(getActivity(),
                android.R.layout.simple_list_item_1, homeworks);
        setListAdapter(hwAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Homework hw = (Homework) getListAdapter().getItem(position);
        Log.d("HomeworkListFragment", hw.getName());
    }
}
