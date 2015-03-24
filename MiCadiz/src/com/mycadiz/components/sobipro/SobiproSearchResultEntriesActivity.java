package com.mycadiz.components.sobipro;

import com.mycadiz.src.R;

/**
 * This Class Contains All Method Related To SobiproEntriesActivity.
 *
 * @author tasol
 *
 */

public class SobiproSearchResultEntriesActivity extends SobiproMasterActivity {
    private String IN_SECTION_ID;
    private String IN_CATEGORY;
    private String IN_TOWN;
    private String IN_LATITUDE;
    private String IN_LONGITUDE;
    private String IN_PRIVIOUS_ACTIVITY;
    private String IN_KEYWORD;

    /**
     * Overrides methods
     */

    @Override
    public int setLayoutId() {
        return R.layout.sobipro_entries;
    }

    @Override
    public void initComponents() {
    }

    @Override
    public void prepareViews() {
        getIntentData();
        addFragment(R.id.lnrFragment, new SobiproSearchResultEntriesListFragment(IN_SECTION_ID, IN_KEYWORD,IN_CATEGORY,IN_TOWN,IN_PRIVIOUS_ACTIVITY,IN_LATITUDE,IN_LONGITUDE));
    }

    @Override
    public void setActionListeners() {

    }

    /**
     * This method used to get intent data.
     */

    private void getIntentData() {
        try{
            IN_SECTION_ID=getIntent().getStringExtra("IN_SECTION_ID");
            IN_CATEGORY=getIntent().getStringExtra("IN_CATEGORY");
            IN_TOWN=getIntent().getStringExtra("IN_TOWN");
            IN_KEYWORD=getIntent().getStringExtra("IN_KEYWORD");
            IN_LATITUDE=getIntent().getStringExtra("IN_LATITUDE");
            IN_LONGITUDE=getIntent().getStringExtra("IN_LONGITUDE");
            IN_PRIVIOUS_ACTIVITY=getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY");
        }catch (Exception e1){
            e1.printStackTrace();
        }
    }

}
