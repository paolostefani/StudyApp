package it.unive.stud838640.studyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import it.unive.stud838640.studyapp.homework.HomeworkListFragment;
import it.unive.stud838640.studyapp.subject.SubjectListFragment;

/**
 * Created by paolo on 26/04/15.
 */
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.getAdapter().notifyDataSetChanged();
                Log.i("ONPAGESELECTED", "AI");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final int PAGE_COUNT = 2;
        private String[] tabTitles;

        private ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            tabTitles = getResources().getStringArray(R.array.tabs_title_array);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    HomeworkListFragment homeworkListFragment = new HomeworkListFragment();
                    return homeworkListFragment;
                case 1:
                    SubjectListFragment subjectListFragment = new SubjectListFragment();
                    return subjectListFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof Updateable) {
                Updateable f = (Updateable) object;
                if (f != null)
                    f.update();
            }
            return super.getItemPosition(object);
        }
    }
}
