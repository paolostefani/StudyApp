package it.unive.stud838640.studyapp.homework;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import it.unive.stud838640.studyapp.R;
import it.unive.stud838640.studyapp.Updateable;

public class HomeworkListFragment extends android.support.v4.app.Fragment
        implements Updateable {
    public static final String EXTRA_HOMEWORK_ACTION =
            "it.unive.stud838640.studyapp.homework_action";
    private HomeworkManager homeworkManager;
    private List<Homework> homeworks;
    private GridView hwGridView;
    private HomeworkAdapter hwAdapter;
    private Handler uiCallBack;
    private UpdateTimeLeft threadUpdateTimeLeft;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        homeworkManager = HomeworkManager.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homework_grid, container, false);

        homeworks = homeworkManager.getHomeworks();
        hwGridView = (GridView) v.findViewById(R.id.hwork_gridview);
        View emptyView = v.findViewById(R.id.empty_list_text);
        hwGridView.setEmptyView(emptyView);
        hwAdapter = new HomeworkAdapter(homeworks);
        hwGridView.setAdapter(hwAdapter);
        hwGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Homework hw = hwAdapter.getItem(position);
                Intent i = new Intent(getActivity(), HomeworkActivity.class);
                i.putExtra(EXTRA_HOMEWORK_ACTION, "showdetails");
                i.putExtra(HomeworkDetailsFragment.EXTRA_HOMEWORK_ID, hw.getId());
                startActivity(i);
            }
        });

        // Contextual Action Bar implementation
        hwGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        hwGridView.setMultiChoiceModeListener(new GridView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.contextual_menu_fragment_homework_list, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_delete_hwork:
                        for (int i = hwAdapter.getCount(); i >= 0; i--) {
                            if (hwGridView.isItemChecked(i))
                                homeworkManager.removeHomework(hwAdapter.getItem(i));
                        }
                        mode.finish();
                        hwAdapter.notifyDataSetChanged();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });



        // Periodic update Homework list thread
        uiCallBack = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                hwAdapter.notifyDataSetChanged();
            }
        };
        threadUpdateTimeLeft = new UpdateTimeLeft();
        threadUpdateTimeLeft.start();

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
                Intent i1 = new Intent(getActivity(), HomeworkActivity.class);
                i1.putExtra(EXTRA_HOMEWORK_ACTION, "new");
                startActivity(i1);
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hwAdapter.swapItems(HomeworkManager.get(getActivity()).getHomeworks());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        threadUpdateTimeLeft.interrupt();
    }

    @Override
    public void update() {
        hwAdapter.swapItems(HomeworkManager.get(getActivity()).getHomeworks());
    }

    private class HomeworkAdapter extends ArrayAdapter<Homework> {
        private List<Homework> homeworks;

        public HomeworkAdapter(List<Homework> homeworks) {
            super(getActivity(), 0, homeworks);
            this.homeworks = homeworks;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_homework, null);
            }

            final Homework hw = homeworks.get(position);

            TextView hworkButton = (TextView) convertView.findViewById(R.id.hwork_button);
            GradientDrawable bgShape = (GradientDrawable) hworkButton.getBackground();
            String color = hw.getSubject().getColor();
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
                String dLeftString = "";
                String hLeftString = "";
                if (dLeft != 0) {
                    dLeftString = dLeft + d;
                    if (hLeft == 0)
                        hLeftString = "";
                    else
                        hLeftString = " " + hLeft + h;
                }
                else
                    hLeftString = hLeft + h;
                hworkTimeLeft.setText(dLeftString + hLeftString);
            }

            ProgressBar hwCompletionBar = (ProgressBar) convertView
                    .findViewById(R.id.hwork_completion_bar);
            hwCompletionBar.setProgress(hw.getPercentage());
            hwCompletionBar.getProgressDrawable()
                    .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            hwCompletionBar.setVisibility(hw.isCompleted() ? View.INVISIBLE : View.VISIBLE);

            CheckBox hwCompletionCheckBox = (CheckBox) convertView
                    .findViewById(R.id.hwork_completion_checkBox);

            hwCompletionCheckBox.setChecked(hw.isCompleted());
            hwCompletionCheckBox
                    .setVisibility(hw.isCompleted() ? View.VISIBLE : View.INVISIBLE);

            return convertView;
        }

        public void swapItems(List<Homework> homeworks) {
            this.homeworks = homeworks;
            notifyDataSetChanged();
        }
    }

    private class UpdateTimeLeft extends Thread {
        @Override
        public void run() {
            while (true) {
                uiCallBack.sendEmptyMessage(0);
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                }
                ;
            }
        }
    }

}
