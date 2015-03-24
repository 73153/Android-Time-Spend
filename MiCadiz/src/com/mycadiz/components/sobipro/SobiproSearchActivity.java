package com.mycadiz.components.sobipro;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mycadiz.common.classes.IjoomerUtilities;
import com.mycadiz.customviews.IjoomerButton;
import com.mycadiz.customviews.IjoomerEditText;
import com.mycadiz.customviews.IjoomerTextView;
import com.mycadiz.src.R;
import com.mycadiz.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Activity class for SobiproSearchActivity view
 *
 * @author tasol
 *
 */

public class SobiproSearchActivity extends SobiproMasterActivity {

    private IjoomerEditText edtSearchWhat;
    private IjoomerEditText edtSearchNear;
    private IjoomerTextView txtSearchWhat;
    private IjoomerTextView txtSearchNear;
    private ImageView imgSearch;
    private IjoomerButton btnSearch;
    private ProgressBar pbrLoading;

    private JSONObject IN_OBJ;
    private String IN_PRIVIOUS_ACTIVITY;
    private String sectionId;
    public static final String ITEMDATA = "itemdata";
    public static final String ITEMCAPTION = "itemcaption";
    private String categoryValue="";
    private String townValue="";
    private final int GET_CATEGORY=1;
    private final int GET_TOWN=2;
    String latitude = null;
    String longitude = null;

    /**
     * Overrides methods.
     */
    @Override
    public int setLayoutId() {
        return R.layout.sobipro_search;
    }

    @Override
    public void initComponents() {
        edtSearchWhat = (IjoomerEditText) findViewById(R.id.edtSearchWhat);
        edtSearchNear = (IjoomerEditText) findViewById(R.id.edtSearchNear);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        btnSearch = (IjoomerButton) findViewById(R.id.btnSearch);

        txtSearchWhat = (IjoomerTextView) findViewById(R.id.txtSearchWhat);
        txtSearchNear = (IjoomerTextView) findViewById(R.id.txtSearchNear);
        pbrLoading = (ProgressBar) findViewById(R.id.pbrLoading);

    }

    @Override
    public void prepareViews() {
        try {
            IN_OBJ = new JSONObject(new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMDATA));
            IN_PRIVIOUS_ACTIVITY = getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY")!=null?getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY"):new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString("itemcaption");
            sectionId=IN_OBJ.getString(SECTION_ID);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setActionListeners() {

        edtSearchNear.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                edtSearchNear.clearFocus();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftKeyboard();
                    boolean isValid =true;
                    if(edtSearchWhat.getText().toString().trim().length()<=0){
                        edtSearchWhat.setError(getString(R.string.validation_value_required));
                        isValid=false;
                    }
                    if(isValid) {
                        pbrLoading.setVisibility(View.VISIBLE);
                        if(edtSearchNear.getText().toString().trim().length()>0) {
                            IjoomerUtilities.getLatLong(edtSearchNear.getText().toString().trim(), new WebCallListener() {
                                @Override
                                public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                    JSONObject json = IjoomerUtilities.jsonObjectForMap;

                                    if (json != null && json.length() > 0) {
                                        try {
                                            String status = json.getString("status");
                                            if (status.equalsIgnoreCase("OK")) {
                                                JSONArray results = json.getJSONArray("results");
                                                JSONObject obj = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                                                if (obj != null && obj.length() > 0) {
                                                    latitude = obj.getString("lat");
                                                    longitude = obj.getString("lng");
                                                }
                                                try {
                                                    IjoomerUtilities.jsonObjectForMap = null;
                                                    pbrLoading.setVisibility(View.GONE);
                                                    loadNew(SobiproSearchResultEntriesActivity.class, SobiproSearchActivity.this, false, "IN_SECTION_ID", sectionId, "IN_CATEGORY", categoryValue, "IN_TOWN", townValue, "IN_KEYWORD",edtSearchWhat.getText().toString(), "IN_PRIVIOUS_ACTIVITY", new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMCAPTION), "IN_LATITUDE", latitude, "IN_LONGITUDE", longitude);
                                                } catch (Throwable e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        } catch (Throwable e) {
                                            e.printStackTrace();
                                        }
                                        pbrLoading.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onProgressUpdate(int progressCount) {

                                }
                            });
                        }else{
                            try {
                                loadNew(SobiproSearchResultEntriesActivity.class, SobiproSearchActivity.this, false, "IN_SECTION_ID", sectionId, "IN_CATEGORY", categoryValue, "IN_TOWN", townValue, "IN_KEYWORD",edtSearchWhat.getText().toString(), "IN_PRIVIOUS_ACTIVITY", new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMCAPTION), "IN_LATITUDE",getLatitude(), "IN_LONGITUDE", getLongitude());
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        txtSearchNear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    loadNewResult(SobiproSearchCategoryAndTownListActivity.class,SobiproSearchActivity.this,GET_TOWN,"IN_SECTION_ID",sectionId,"IN_TYPE","town","IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        });
        txtSearchWhat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    loadNewResult(SobiproSearchCategoryAndTownListActivity.class,SobiproSearchActivity.this,GET_CATEGORY,"IN_SECTION_ID",sectionId,"IN_TYPE","category","IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        });
        btnSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categoryValue.trim().length() <= 0 && townValue.trim().length() <= 0) {
                    IjoomerUtilities.getCustomOkDialog(getString(R.string.sobipro_search), getString(R.string.sobipro_category_town_validation), getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
                        @Override
                        public void NeutralMethod() {

                        }
                    });
                } else {
                    pbrLoading.setVisibility(View.VISIBLE);
                    if(!txtSearchNear.getText().equals(getString(R.string.sobipro_select_town))) {
                        IjoomerUtilities.getLatLong(townValue.trim(), new WebCallListener() {
                            @Override
                            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                JSONObject json = IjoomerUtilities.jsonObjectForMap;

                                if (json != null && json.length() > 0) {
                                    try {
                                        String status = json.getString("status");
                                        if (status.equalsIgnoreCase("OK")) {
                                            JSONArray results = json.getJSONArray("results");
                                            JSONObject obj = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                                            if (obj != null && obj.length() > 0) {
                                                latitude = obj.getString("lat");
                                                longitude = obj.getString("lng");
                                            }
                                            try {
                                                IjoomerUtilities.jsonObjectForMap = null;
                                                pbrLoading.setVisibility(View.GONE);
                                                loadNew(SobiproSearchResultEntriesActivity.class, SobiproSearchActivity.this, false, "IN_SECTION_ID", sectionId, "IN_CATEGORY", categoryValue, "IN_TOWN", townValue, "IN_KEYWORD", "", "IN_PRIVIOUS_ACTIVITY", new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMCAPTION), "IN_LATITUDE", latitude, "IN_LONGITUDE", longitude);
                                            } catch (Throwable e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                    pbrLoading.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onProgressUpdate(int progressCount) {

                            }
                        });
                    }else{
                        try {
                            loadNew(SobiproSearchResultEntriesActivity.class, SobiproSearchActivity.this, false, "IN_SECTION_ID", sectionId, "IN_CATEGORY", categoryValue, "IN_TOWN", townValue, "IN_KEYWORD", "", "IN_PRIVIOUS_ACTIVITY", new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMCAPTION), "IN_LATITUDE", getLatitude(), "IN_LONGITUDE", getLongitude());
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });

        imgSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid =true;
                if(edtSearchWhat.getText().toString().trim().length()<=0){
                    edtSearchWhat.setError(getString(R.string.validation_value_required));
                    isValid=false;
                }
                if(isValid) {
                    pbrLoading.setVisibility(View.VISIBLE);
                    if (edtSearchNear.getText().toString().trim().length()>0){
                        IjoomerUtilities.getLatLong(edtSearchNear.getText().toString().trim(), new WebCallListener() {
                            @Override
                            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                JSONObject json = IjoomerUtilities.jsonObjectForMap;

                                if (json != null && json.length() > 0) {
                                    try {
                                        String status = json.getString("status");
                                        if (status.equalsIgnoreCase("OK")) {
                                            JSONArray results = json.getJSONArray("results");
                                            JSONObject obj = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                                            if (obj != null && obj.length() > 0) {
                                                latitude = obj.getString("lat");
                                                longitude = obj.getString("lng");
                                            }
                                            try {
                                                IjoomerUtilities.jsonObjectForMap = null;
                                                pbrLoading.setVisibility(View.GONE);
                                                loadNew(SobiproSearchResultEntriesActivity.class, SobiproSearchActivity.this, false, "IN_SECTION_ID", sectionId, "IN_CATEGORY", categoryValue, "IN_TOWN", townValue, "IN_KEYWORD",edtSearchWhat.getText().toString(), "IN_PRIVIOUS_ACTIVITY", new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMCAPTION), "IN_LATITUDE", latitude, "IN_LONGITUDE", longitude);
                                            } catch (Throwable e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                    pbrLoading.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onProgressUpdate(int progressCount) {

                            }
                        });
                    }else{
                        try {
                            loadNew(SobiproSearchResultEntriesActivity.class, SobiproSearchActivity.this, false, "IN_SECTION_ID", sectionId, "IN_CATEGORY", categoryValue, "IN_TOWN", townValue, "IN_KEYWORD",edtSearchWhat.getText().toString(), "IN_PRIVIOUS_ACTIVITY", new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMCAPTION), "IN_LATITUDE",getLatitude(), "IN_LONGITUDE", getLongitude());
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==GET_CATEGORY){
                categoryValue = data.getStringExtra("value");
                txtSearchWhat.setText(data.getStringExtra("title"));
            }else if(requestCode==GET_TOWN){
                townValue = data.getStringExtra("value");
                txtSearchNear.setText(data.getStringExtra("title"));
            }
        }
    }

}
