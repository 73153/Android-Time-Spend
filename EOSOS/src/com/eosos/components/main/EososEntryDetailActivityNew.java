package com.eosos.components.main;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eosos.common.classes.IjoomerUtilities;
import com.eosos.common.configuration.IjoomerApplicationConfiguration;
import com.eosos.customviews.IjoomerMultiPurposeSelector;
import com.eosos.customviews.IjoomerRadioButton;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.library.EososDataProvider;
import com.eosos.src.R;
import com.eosos.weservice.WebCallListenerWithCacheInfo;
import com.smart.framework.CustomAlertNeutral;

public class EososEntryDetailActivityNew extends EososMasterActivity {
    private IjoomerMultiPurposeSelector selectorDesc, selectorContact, selectorShare, selectorDays;
    private IjoomerRadioButton btnDescription, btnContact, btnShare, btnDays;
    private ArrayList<HashMap<String, String>> data;
    private EososDataProvider dataProvider;
    public String IN_ID = "1";
    private ImageView imgMap;
    public String IN_SECTION_ID = "54";
    private LinearLayout lnrFragment;
    private ArrayList<Boolean> daysList;

    private EososEntryContactInfoFragment mEososEntryContactInfoFragment;
    private EososEntryDescriptionFragment mEososEntryDescriptionFragment;
    private EososEntryDaysFragment mEososEntryDaysFragment;
    private EososRateListFragment mEososRateListFragment;

    @Override
    public int setLayoutId() {
        return R.layout.eosos_entry_detail_new;
    }

    @Override
    public void initComponents() {
        btnDescription = (IjoomerRadioButton) findViewById(R.id.btnDescription);
        btnContact = (IjoomerRadioButton) findViewById(R.id.btnContact);
        btnShare = (IjoomerRadioButton) findViewById(R.id.btnShare);
        btnDays = (IjoomerRadioButton) findViewById(R.id.btnDays);
        lnrFragment = (LinearLayout) findViewById(R.id.lnrFragment);
        selectorDesc = new IjoomerMultiPurposeSelector(EososEntryDetailActivityNew.this);
        selectorContact = new IjoomerMultiPurposeSelector(EososEntryDetailActivityNew.this);
        selectorShare = new IjoomerMultiPurposeSelector(EososEntryDetailActivityNew.this);
        selectorDays = new IjoomerMultiPurposeSelector(EososEntryDetailActivityNew.this);
        dataProvider = new EososDataProvider(EososEntryDetailActivityNew.this);
        daysList = new ArrayList<Boolean>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getEntryDetail();
    }

    @Override
    public void prepareViews() {
        getIntentData();
        ((IjoomerTextView) getHeaderView().findViewById(R.id.txtBack)).setVisibility(View.VISIBLE);
        ((ImageView) getHeaderView().findViewById(R.id.imgHome)).setVisibility(View.GONE);
        selectorDesc.setPressedDrawableResource(R.drawable.discription_tab_active);
        selectorDesc.setDefaultDrawableResource(R.drawable.discription_tab);

        selectorContact.setPressedDrawableResource(R.drawable.call_us_tab_active);
        selectorContact.setDefaultDrawableResource(R.drawable.call_us_tab);

        selectorShare.setPressedDrawableResource(R.drawable.rate_tab_active);
        selectorShare.setDefaultDrawableResource(R.drawable.rate_tab);

        selectorDays.setPressedDrawableResource(R.drawable.days_tab_active);
        selectorDays.setDefaultDrawableResource(R.drawable.days_tab);

        btnDescription.setBackgroundDrawable(selectorDesc.getSelector());
        btnContact.setBackgroundDrawable(selectorContact.getSelector());
        btnShare.setBackgroundDrawable(selectorShare.getSelector());
        btnDays.setBackgroundDrawable(selectorDays.getSelector());
        imgMap = (ImageView) getHeaderView().findViewById(R.id.imgMap);
        imgMap.setVisibility(View.VISIBLE);
        getEntryDetail();
    }

    public void getEntryDetail() {
        try {
            if (IjoomerApplicationConfiguration.isReloadRequired()) {
                dataProvider.getEntryDetail(getDeviceUDID(),IN_SECTION_ID, IN_ID, new WebCallListenerWithCacheInfo() {

                    @Override
                    public void onProgressUpdate(int progressCount) {
                    }

                    @Override
                    public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2, int pageNo, int pageLimit,
                                               boolean fromCache) {
                        try {
                            IjoomerApplicationConfiguration.isReloadRequired = false;
                            if (data1 != null && data1.size() > 0) {
                                data = data1;
                                prePareDaysList();
                                HashMap<String, String> hashMap = data.get(0);
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = new JSONArray(hashMap.get("field"));
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image1")) {
                                            hashMap.put("image1", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image2")) {
                                            hashMap.put("image2", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image3")) {
                                            hashMap.put("image3", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_description")) {
                                            hashMap.put("description", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_email")) {
                                            hashMap.put("email", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_phone")) {
                                            hashMap.put("phone", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                            hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                            hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_address")) {
                                            hashMap.put("address", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_city")) {
                                            hashMap.put("city", jsonArray.getJSONObject(j).get("value").toString());
                                        }
                                    }
                                } catch (Exception e) {

                                }
                                if (btnContact.isChecked()) {
                                    mEososEntryContactInfoFragment = new EososEntryContactInfoFragment(data);
                                    addFragment(R.id.lnrFragment, mEososEntryContactInfoFragment);
                                } else if (btnDescription.isChecked()) {
                                    mEososEntryDescriptionFragment = new EososEntryDescriptionFragment(data);
                                    addFragment(R.id.lnrFragment, mEososEntryDescriptionFragment);
                                } else if (btnDays.isChecked()) {
                                    mEososEntryDaysFragment = new EososEntryDaysFragment(daysList);
                                    addFragment(R.id.lnrFragment, mEososEntryDaysFragment);
                                } else {
                                    mEososRateListFragment = new EososRateListFragment(data.get(0).get(REVIEWRATING), data.get(0).get(AVERAGERATING), IN_ID, IN_SECTION_ID, data
                                            .get(0).get("title"));
                                    addFragment(R.id.lnrFragment, mEososRateListFragment);
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                try {
                    ArrayList<HashMap<String, String>> data1 = dataProvider.getEntryDetailsFromDb(IN_ID);
                    if (data1 != null && data1.size() > 0) {
                        data = data1;
                        prePareDaysList();
                        HashMap<String, String> hashMap = data.get(0);
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(hashMap.get("field"));
                            for (int j = 0; j < jsonArray.length(); j++) {
                                if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image1")) {
                                    hashMap.put("image1", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image2")) {
                                    hashMap.put("image2", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image3")) {
                                    hashMap.put("image3", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_description")) {
                                    hashMap.put("description", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_email")) {
                                    hashMap.put("email", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_phone")) {
                                    hashMap.put("phone", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                    hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                    hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_address")) {
                                    hashMap.put("address", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_city")) {
                                    hashMap.put("city", jsonArray.getJSONObject(j).get("value").toString());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (btnContact.isChecked()) {
                            mEososEntryContactInfoFragment = new EososEntryContactInfoFragment(data);
                            addFragment(R.id.lnrFragment, mEososEntryContactInfoFragment);
                        } else if (btnDescription.isChecked()) {
                            mEososEntryDescriptionFragment = new EososEntryDescriptionFragment(data);
                            addFragment(R.id.lnrFragment, mEososEntryDescriptionFragment);
                        } else if (btnDays.isChecked()) {
                            mEososEntryDaysFragment = new EososEntryDaysFragment(daysList);
                            addFragment(R.id.lnrFragment, mEososEntryDaysFragment);
                        } else {
                            mEososRateListFragment = new EososRateListFragment(data.get(0).get(REVIEWRATING), data.get(0).get(AVERAGERATING), IN_ID, IN_SECTION_ID, data.get(0)
                                    .get("title"));
                            addFragment(R.id.lnrFragment, mEososRateListFragment);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prePareDaysList() {
        try {

            daysList.clear();
            if (dataProvider.isClubOpen(data.get(0).get("id"), "monday")) {
                daysList.add(true);
            } else {
                daysList.add(false);
            }

            if (dataProvider.isClubOpen(data.get(0).get("id"), "tuesday")) {
                daysList.add(true);
            } else {
                daysList.add(false);
            }

            if (dataProvider.isClubOpen(data.get(0).get("id"), "wednesday")) {
                daysList.add(true);
            } else {
                daysList.add(false);
            }
            if (dataProvider.isClubOpen(data.get(0).get("id"), "thursday")) {
                daysList.add(true);
            } else {
                daysList.add(false);
            }
            if (dataProvider.isClubOpen(data.get(0).get("id"), "friday")) {
                daysList.add(true);
            } else {
                daysList.add(false);
            }
            if (dataProvider.isClubOpen(data.get(0).get("id"), "saturday")) {
                daysList.add(true);
            } else {
                daysList.add(false);
            }
            if (dataProvider.isClubOpen(data.get(0).get("id"), "sunday")) {
                daysList.add(true);
            } else {
                daysList.add(false);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void getIntentData() {
        if (getSmartApplication().readSharedPreferences().getString(SP_DEFAULT_LANDING_SCREEN, "").length() > 0) {
            try {
                JSONObject intentData = new JSONObject(getSmartApplication().readSharedPreferences().getString(SP_LAST_ACTIVITY_INTENT, "")).getJSONObject("itemdata");
                IN_SECTION_ID = intentData.getString("sectionID");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            IN_ID = getIntent().getStringExtra("IN_ID");
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Override
    public void setActionListeners() {

        imgMap.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!IjoomerUtilities.isNetwokReachable()){
                    IjoomerUtilities.getCustomOkDialog(getString(R.string.eosos_entry_details), getString(R.string.code599), getString(R.string.ok), R.layout.ijoomer_ok_dialog,
                            new CustomAlertNeutral() {

                                @Override
                                public void NeutralMethod() {
                                }
                            });
                }else {
                    try {
                        loadNew(EososMapActivity.class, EososEntryDetailActivityNew.this, false, "IN_MAPLIST", data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnDescription.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<HashMap<String, String>> data1 = dataProvider.getEntryDetailsFromDb(IN_ID);
                if (data1 != null && data1.size() > 0) {
                    data = data1;
                    prePareDaysList();
                    HashMap<String, String> hashMap = data.get(0);
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(hashMap.get("field"));
                        for (int j = 0; j < jsonArray.length(); j++) {
                            if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image1")) {
                                hashMap.put("image1", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image2")) {
                                hashMap.put("image2", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image3")) {
                                hashMap.put("image3", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_description")) {
                                hashMap.put("description", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_email")) {
                                hashMap.put("email", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_phone")) {
                                hashMap.put("phone", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_address")) {
                                hashMap.put("address", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_city")) {
                                hashMap.put("city", jsonArray.getJSONObject(j).get("value").toString());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                mEososEntryDescriptionFragment = new EososEntryDescriptionFragment(data);
                addFragment(R.id.lnrFragment, mEososEntryDescriptionFragment);
            }
        });

        btnDays.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<HashMap<String, String>> data1 = dataProvider.getEntryDetailsFromDb(IN_ID);
                if (data1 != null && data1.size() > 0) {
                    data = data1;
                    prePareDaysList();
                    HashMap<String, String> hashMap = data.get(0);
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(hashMap.get("field"));
                        for (int j = 0; j < jsonArray.length(); j++) {
                            if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image1")) {
                                hashMap.put("image1", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image2")) {
                                hashMap.put("image2", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image3")) {
                                hashMap.put("image3", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_description")) {
                                hashMap.put("description", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_email")) {
                                hashMap.put("email", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_phone")) {
                                hashMap.put("phone", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_address")) {
                                hashMap.put("address", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_city")) {
                                hashMap.put("city", jsonArray.getJSONObject(j).get("value").toString());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mEososEntryDaysFragment = new EososEntryDaysFragment(daysList);
                addFragment(R.id.lnrFragment, mEososEntryDaysFragment);
            }
        });

        btnContact.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<HashMap<String, String>> data1 = dataProvider.getEntryDetailsFromDb(IN_ID);
                if (data1 != null && data1.size() > 0) {
                    data = data1;
                    prePareDaysList();
                    HashMap<String, String> hashMap = data.get(0);
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(hashMap.get("field"));
                        for (int j = 0; j < jsonArray.length(); j++) {
                            if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image1")) {
                                hashMap.put("image1", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image2")) {
                                hashMap.put("image2", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image3")) {
                                hashMap.put("image3", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_description")) {
                                hashMap.put("description", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_email")) {
                                hashMap.put("email", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_phone")) {
                                hashMap.put("phone", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_address")) {
                                hashMap.put("address", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_city")) {
                                hashMap.put("city", jsonArray.getJSONObject(j).get("value").toString());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mEososEntryContactInfoFragment = new EososEntryContactInfoFragment(data);
                addFragment(R.id.lnrFragment, mEososEntryContactInfoFragment);
            }
        });

        btnShare.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<HashMap<String, String>> data1 = dataProvider.getEntryDetailsFromDb(IN_ID);
                if (data1 != null && data1.size() > 0) {
                    data = data1;
                    prePareDaysList();
                    HashMap<String, String> hashMap = data.get(0);
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(hashMap.get("field"));
                        for (int j = 0; j < jsonArray.length(); j++) {
                            if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image1")) {
                                hashMap.put("image1", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image2")) {
                                hashMap.put("image2", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image3")) {
                                hashMap.put("image3", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_description")) {
                                hashMap.put("description", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_email")) {
                                hashMap.put("email", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_phone")) {
                                hashMap.put("phone", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_address")) {
                                hashMap.put("address", jsonArray.getJSONObject(j).get("value").toString());
                            } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_city")) {
                                hashMap.put("city", jsonArray.getJSONObject(j).get("value").toString());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mEososRateListFragment = new EososRateListFragment(data.get(0).get(REVIEWRATING), data.get(0).get(AVERAGERATING), IN_ID, IN_SECTION_ID, data.get(0).get("title"));
                addFragment(R.id.lnrFragment, mEososRateListFragment);
            }
        });
    }

    void onEmail(String value, String message) {
        String[] to = value.toString().split(",");
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/html");
        i.putExtra(Intent.EXTRA_EMAIL, to);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            ting(getString(R.string.share_email_no_client));
        }
    }

    void onEmail(String IN_SHARE_CAPTION, String message, String IN_SHARE_SHARELINK) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/html");
        i.putExtra(Intent.EXTRA_SUBJECT, String.format(getString(R.string.share_email_subject), IN_SHARE_CAPTION));
        i.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(IjoomerUtilities.prepareEmailBody("", getString(R.string.saw_this_story_on_the) + " " + getString(R.string.app_name) + " ",
                IN_SHARE_CAPTION, message, IN_SHARE_SHARELINK, "", getString(R.string.site_url))));
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            ting(getString(R.string.share_email_no_client));
        }
    }

    void call(String phone) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        startActivity(callIntent);
    }

    void message(String IN_SHARE_CAPTION, String message, String IN_SHARE_SHARELINK) {
        try {
            Intent localIntent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + ""));
            localIntent.putExtra("sms_body", IN_SHARE_CAPTION + "\n" + message + "\n\n" + IN_SHARE_SHARELINK);
            startActivity(localIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
