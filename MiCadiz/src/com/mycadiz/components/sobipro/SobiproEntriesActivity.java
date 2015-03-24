package com.mycadiz.components.sobipro;

import com.mycadiz.src.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This Class Contains All Method Related To SobiproEntriesActivity.
 *
 * @author tasol
 *
 */

public class SobiproEntriesActivity extends SobiproMasterActivity {
    private String IN_PARENT_ID, IN_SECTION_ID, IN_PAGELAYOUT;
    private int IN_POS;
    private String IN_FEATUREDFIRST = "No";
    private String IN_PRIVIOUS_ACTIVITY;
    public static final String ITEMCAPTION = "itemcaption";
    public static final String ITEMDATA = "itemdata";

    private ArrayList<String> pageLayouts;
    /**
     * Overrides methods
     */

    @Override
    public int setLayoutId() {
        return R.layout.sobipro_entries;
    }

    @Override
    public void initComponents() {
        pageLayouts = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.sobipro_pageLayout)));
    }

    @Override
    public void prepareViews() {
        getIntentData();
        addFragment(R.id.lnrFragment, new SobiproEntriesListFragment(IN_SECTION_ID, IN_PARENT_ID, IN_POS, IN_PAGELAYOUT, IN_FEATUREDFIRST,IN_PRIVIOUS_ACTIVITY));
    }

    @Override
    public void setActionListeners() {

    }

    /**
     * This method used to get intent data.
     */

    private void getIntentData() {
        try {
            IN_SECTION_ID = getIntent().getStringExtra("IN_SECTION_ID")==null?new JSONObject(new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMDATA)).getString(SECTION_ID): getIntent().getStringExtra("IN_SECTION_ID");
            IN_PARENT_ID = getIntent().getStringExtra("IN_PARENT_ID")==null?new JSONObject(new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMDATA)).getString(CAT_ID):getIntent().getStringExtra("IN_PARENT_ID");
            IN_PAGELAYOUT = getIntent().getStringExtra("IN_PAGELAYOUT")==null?new JSONObject(new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMDATA)).getString(PAGELAYOUT):getIntent().getStringExtra("IN_PAGELAYOUT");
            IN_POS = getIntent().getIntExtra("IN_POS", 0);
            IN_FEATUREDFIRST = getIntent().getStringExtra("IN_FEATUREDFIRST")==null?new JSONObject(new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMDATA)).getString(FEATUREDFIRST):getIntent().getStringExtra("IN_FEATUREDFIRST");
            IN_PRIVIOUS_ACTIVITY = getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY")==null?new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMCAPTION):getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
