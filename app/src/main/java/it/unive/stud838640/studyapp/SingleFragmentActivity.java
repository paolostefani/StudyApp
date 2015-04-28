package it.unive.stud838640.studyapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import it.unive.stud838640.studyapp.homework.HomeworkListActivity;
import it.unive.stud838640.studyapp.subject.SubjectListActivity;

/**
 * Created by paolo on 18/02/15.
 */
public abstract class SingleFragmentActivity extends Activity {
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String[] drawerTitles;

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.content_frame, fragment)
                    .commit();
        }

        drawerTitles = getResources().getStringArray(R.array.drawer_menu_array);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.list_item_drawer_menu, drawerTitles));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        Intent i1 = new Intent(SingleFragmentActivity.this,
                                HomeworkListActivity.class);
                        startActivity(i1);
                    case 2:
                        Intent i2 = new Intent(SingleFragmentActivity.this,
                                SubjectListActivity.class);
                        startActivity(i2);
                }
                drawerLayout.closeDrawer(drawerList);
            }
        });
    }
}
