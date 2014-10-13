package com.chechezhi.myplan.ui;

import android.app.ActionBar;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chechezhi.myplan.R;

public class MainActivity extends FragmentActivity {
    private static final int MENU_ID_ADD_PLAN = 0;
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
        bar.setDisplayOptions(0, ActionBar.NAVIGATION_MODE_LIST);

        mTabsAdapter = new FragmentTabAdapter(this, mViewPager);
        mTabsAdapter.addTab(bar.newTab().setText(getString(R.string.todo_list)), TodoFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText(getString(R.string.finished_list)), FinishedFragment.class, null);

        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem actionItem = menu.add(0, MENU_ID_ADD_PLAN, 0, R.string.add_plan);
        actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
        case MENU_ID_ADD_PLAN:
            AddPlanFragment f = new AddPlanFragment();
            f.show(getSupportFragmentManager(), "Add Plan");
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void notifyDataChange() {
        // TODO Auto-generated method stub
        
    }

}
