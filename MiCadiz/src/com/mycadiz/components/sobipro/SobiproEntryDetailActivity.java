package com.mycadiz.components.sobipro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.mycadiz.common.classes.IjoomerUtilities;
import com.mycadiz.src.R;
import com.smart.framework.CustomAlertNeutral;

import java.util.ArrayList;

/**
 * This Class Contains All Method Related To SobiproEntryDetailActivity.
 *
 * @author tasol
 *
 */
public class SobiproEntryDetailActivity extends SobiproMasterActivity {
    private String IN_PAGELAYOUT;
    private ViewPager viewPager;
    private EntryDetailAdapter adapter;
    String IN_TABLE;
    ArrayList<String> IN_ENTRY_ID_ARRAY;
    int IN_ENTRY_INDEX;
    private String IN_PRIVIOUS_ACTIVITY;

    /**
     * Overrides method
     */
    @Override
    public int setLayoutId() {
        return R.layout.sobipro_entry_detail;
    }

    @Override
    public void initComponents() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new EntryDetailAdapter(getSupportFragmentManager());
        getIntentData();
    }

    @Override
    public void prepareViews() {
        if (IN_ENTRY_ID_ARRAY != null) {
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(IN_ENTRY_INDEX);
            viewPager.setOffscreenPageLimit(1);
        } else {
            IjoomerUtilities.getCustomOkDialog(getScreenCaption(), getString(getResources().getIdentifier("code204", "string", getPackageName())), getString(R.string.ok),
                    R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
                @Override
                public void NeutralMethod() {

                }
            });
        }
    }

    @Override
    public void setActionListeners() {
    }

    /**
     * Class methods
     */

    /**
     * This method used to get intent data.
     */

    private void getIntentData() {
        try {
            IN_ENTRY_INDEX = getIntent().getIntExtra("IN_ENTRY_INDEX", 0);
            IN_ENTRY_ID_ARRAY = getIntent().getStringArrayListExtra("IN_ENTRY_ID_ARRAY");
            IN_TABLE = getIntent().getStringExtra("IN_TABLE");
            IN_PAGELAYOUT = getIntent().getStringExtra("IN_PAGELAYOUT");
            IN_PRIVIOUS_ACTIVITY = getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Custom class Adapter
     */
    private class EntryDetailAdapter extends FragmentStatePagerAdapter {

        public EntryDetailAdapter(FragmentManager fm) {
            super(fm);
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int pos) {
            System.gc();
            return new SobiproEntryDetailFragment(IN_ENTRY_ID_ARRAY.get(pos), IN_TABLE, IN_PAGELAYOUT,IN_PRIVIOUS_ACTIVITY);
        }

        @Override
        public int getCount() {
            return IN_ENTRY_ID_ARRAY.size();
        }
    }

}
