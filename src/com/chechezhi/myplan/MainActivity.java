package com.chechezhi.myplan;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private FragmentTabAdapter mTabsAdapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.main_pager);
        setContentView(mViewPager);

        final ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        mTabsAdapter = new FragmentTabAdapter(this, mViewPager);
        mTabsAdapter.addTab(bar.newTab().setText("TODO List"),
                TodoFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Finished List"),
                FinishedFragment.class, null);

        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
}
