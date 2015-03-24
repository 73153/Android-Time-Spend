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

public class SobiproPreSearchResultEntriesActivity extends SobiproMasterActivity {
    private String IN_PARENT_ID, IN_SECTION_ID, IN_PAGELAYOUT;
    private int IN_POS;
    private JSONObject IN_OBJ;
    private String IN_FEATUREDFIRST = "No";
    private String IN_PRIVIOUS_ACTIVITY;
    private String IN_KEYWORD;
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
        addFragment(R.id.lnrFragment, new SobiproPreSearchResultEntriesListFragment(IN_SECTION_ID, IN_KEYWORD, 0,IN_PRIVIOUS_ACTIVITY));
    }

    @Override
    public void setActionListeners() {

    }

    /**
     * This method used to get intent data.
     */

    private void getIntentData() {
        try{
            IN_OBJ = new JSONObject(new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMDATA));
            IN_SECTION_ID=IN_OBJ.getString(SECTION_ID);
            IN_KEYWORD=IN_OBJ.getString("keywords");
            IN_PRIVIOUS_ACTIVITY = getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY")==null?new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMCAPTION):getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY");
        }catch (Exception e1){
            e1.printStackTrace();
        }
    }

}
