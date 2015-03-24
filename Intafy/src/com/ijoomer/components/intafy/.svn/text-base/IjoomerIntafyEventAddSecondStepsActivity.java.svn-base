package com.ijoomer.components.intafy;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.androidquery.AQuery;
import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.customviews.RoundedImageView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyEventDataProvider;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafyEventAddSecondStepsActivity extends IjoomerIntafyMaster {


    private Spinner spnTypeUserSelection;
    private IjoomerTextView txtUserSelected;
    private ProgressBar pbrImageLoad;
    private RoundedImageView imgEventAvatar;
    private ImageView imgPublish;

    private final int INDIVIDUAL_PUBLIC=1;
    private final int CIRCLE=2;
    private String IN_EVENT_ID;
    private String IN_EVENT_TITLE;
    private String IN_EVENT_LOCATION;
    private String IN_EVENT_STARTDATE;
    private String IN_EVENT_ENDDATE;
    private String IN_EVENT_AVATAR;
    private String IN_EVENT_TYPE;
    private String IN_EVENT_USERS;
    private String IN_EVENT_CIRCLE;
    private String IN_EVENT_DESCRIPTIONS;
    private String selectedUserIds;
    private String selectedUserName;
    private HashMap<String,String> selectedUserMap;
    private HashMap<String,String> selectedCircleMap;
    private HashMap<String,String> newselectedUserMap;
    private IntafyEventDataProvider eventDataProvider;
    private AQuery aQuery;
    private IjoomerTextView headerLeftText;
    private LinearLayout lnrHeaderLeft;
    private IjoomerTextView headerText;
    /**
     * Overrides methods
     */


    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_event_add_second_step;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public int setHeaderLayoutId() {
        return R.layout.ijoomer_intafy_header;
    }

    @Override
    public int setFooterLayoutId() {
        return 0;
    }

    @Override
    public void initComponents() {
        spnTypeUserSelection = (Spinner) findViewById(R.id.spnTypeUserSelection);
        txtUserSelected = (IjoomerTextView) findViewById(R.id.txtUserSelected);
        pbrImageLoad = (ProgressBar)findViewById(R.id.pbrImageLoad);
        imgEventAvatar = (RoundedImageView)findViewById(R.id.imgEventAvatar);
        imgPublish = (ImageView)findViewById(R.id.imgPublish);
        headerLeftText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst);
        lnrHeaderLeft = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderLeft);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        spnTypeUserSelection.setAdapter(new IjoomerUtilities.MyCustomAdapter(this, new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.intafy_event_type)))));
        eventDataProvider = new IntafyEventDataProvider(this);
        aQuery = new AQuery(this);
    }

    @Override
    public void prepareViews() {

        headerLeftText.setText(getString(R.string.intafy_back));
        getIntentData();
    }

    private void getIntentData(){
        IN_EVENT_ID = getIntent().getStringExtra("IN_EVENT_ID")!=null ?getIntent().getStringExtra("IN_EVENT_ID"):"0";
        IN_EVENT_TITLE = getIntent().getStringExtra("IN_EVENT_TITLE")!=null ?getIntent().getStringExtra("IN_EVENT_TITLE"):"";
        IN_EVENT_LOCATION = getIntent().getStringExtra("IN_EVENT_LOCATION")!=null ?getIntent().getStringExtra("IN_EVENT_LOCATION"):"";
        IN_EVENT_STARTDATE = getIntent().getStringExtra("IN_EVENT_STARTDATE")!=null ?getIntent().getStringExtra("IN_EVENT_STARTDATE"):"";
        IN_EVENT_ENDDATE = getIntent().getStringExtra("IN_EVENT_ENDDATE")!=null ?getIntent().getStringExtra("IN_EVENT_ENDDATE"):"";
        IN_EVENT_DESCRIPTIONS = getIntent().getStringExtra("IN_EVENT_DESCRIPTIONS")!=null ?getIntent().getStringExtra("IN_EVENT_DESCRIPTIONS"):"";
        IN_EVENT_AVATAR = getIntent().getStringExtra("IN_EVENT_AVATAR")!=null ?getIntent().getStringExtra("IN_EVENT_AVATAR"):"";
        IN_EVENT_TYPE = getIntent().getStringExtra("IN_EVENT_TYPE")!=null ?getIntent().getStringExtra("IN_EVENT_TYPE"):"";
        IN_EVENT_USERS = getIntent().getStringExtra("IN_EVENT_USERS")!=null ?getIntent().getStringExtra("IN_EVENT_USERS"):"";
        IN_EVENT_CIRCLE = getIntent().getStringExtra("IN_EVENT_CIRCLE")!=null ?getIntent().getStringExtra("IN_EVENT_CIRCLE"):"";


        if(!IN_EVENT_ID.equals("0")){
            headerText.setText(getString(R.string.intafy_edit_event));
            imgPublish.setImageResource(getIcon(SAVEICON,false));
        }else{
            headerText.setText(getString(R.string.intafy_add_event));
            imgPublish.setImageResource(getIcon(PUBLISHICON,false));
        }
        if(IN_EVENT_TYPE.trim().length()>0){
            if(IN_EVENT_TYPE.equalsIgnoreCase(getString(R.string.intafy_public))){
                spnTypeUserSelection.setSelection(3);
            }else{
                setIntialSelectedUser();
            }

        }
        if(IN_EVENT_AVATAR.contains("http")){
            aQuery.id(imgEventAvatar).progress(pbrImageLoad).image(IN_EVENT_AVATAR,true,true,150,R.drawable.ic_launcher);
        }else{
            pbrImageLoad.setVisibility(View.GONE);
            if(IN_EVENT_AVATAR.trim().length()>0){
                imgEventAvatar.setImageBitmap(decodeFile(IN_EVENT_AVATAR));
            }else{
                imgEventAvatar.setImageResource(R.drawable.ijoomer_background);
            }
        }
    }

    @Override
    public void setActionListeners() {
        lnrHeaderLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerLeftText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        headerLeftText.setTextColor(getResources().getColor(R.color.white));
                        finish();
                    }
                }, 1000);
            }
        });

        txtUserSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUserSelected.getText().toString().trim().length()>0){
                    if(selectedUserMap==null){
                        selectedUserMap = new HashMap<String, String>();
                    }
                    if(newselectedUserMap==null){
                        newselectedUserMap = new HashMap<String, String>();
                    }
                    if(selectedCircleMap==null){
                        selectedCircleMap = new HashMap<String, String>();
                    }
                    if(spnTypeUserSelection.getSelectedItemPosition()==1){
                        try {
                            loadNewResult(IjoomerIntafyInviteUserForEventActivity.class,IjoomerIntafyEventAddSecondStepsActivity.this,INDIVIDUAL_PUBLIC,"IN_SELECTED_USER",selectedUserMap,"IN_NEW_SELECTED_CIRCLE",newselectedUserMap,"IN_EVENT_ID",IN_EVENT_ID);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }else if(spnTypeUserSelection.getSelectedItemPosition()==2){
                        try {
                            loadNewResult(IjoomerIntafyInviteCircleActivity.class,IjoomerIntafyEventAddSecondStepsActivity.this,CIRCLE,"IN_SELECTED_CIRCLE",selectedCircleMap,"IN_NEW_SELECTED_CIRCLE",newselectedUserMap,"IN_EVENT_ID",IN_EVENT_ID);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        spnTypeUserSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                switch (position){
                    case 0:
                        txtUserSelected.setText("");
                        break;
                    case 1:
                        if(IN_EVENT_USERS.trim().length()>0){
                            String[] users = IN_EVENT_USERS.split(",");
                            selectedUserMap = new HashMap<String, String>();
                            for (int i=0;i<users.length;i++){
                                String[] user = users[i].split(":");
                                selectedUserMap.put(user[0],user[1]);
                            }
                        }else{
                            selectedUserMap = new HashMap<String, String>();
                        }
                        if(selectedUserMap==null){
                            selectedUserMap = new HashMap<String, String>();
                        }
                        newselectedUserMap = new HashMap<String, String>();
                        setSelectedUser(selectedUserMap);
                        try {
                            loadNewResult(IjoomerIntafyInviteUserForEventActivity.class,IjoomerIntafyEventAddSecondStepsActivity.this,INDIVIDUAL_PUBLIC,"IN_SELECTED_USER",selectedUserMap,"IN_NEW_SELECTED_CIRCLE",newselectedUserMap,"IN_EVENT_ID",IN_EVENT_ID);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        if(IN_EVENT_CIRCLE.trim().length()>0){
                            String[] users = IN_EVENT_CIRCLE.split(",");
                            selectedCircleMap = new HashMap<String, String>();
                            for (int i=0;i<users.length;i++){
                                String[] user = users[i].split(":");
                                selectedCircleMap.put(user[0],user[1]);
                            }
                        }else{
                            selectedCircleMap = new HashMap<String, String>();
                        }
                        if(selectedCircleMap==null){
                            selectedCircleMap = new HashMap<String, String>();
                        }
                        newselectedUserMap = new HashMap<String, String>();
                        setSelectedUser(selectedCircleMap);
                        try {
                            loadNewResult(IjoomerIntafyInviteCircleActivity.class,IjoomerIntafyEventAddSecondStepsActivity.this,CIRCLE,"IN_SELECTED_CIRCLE",selectedCircleMap,"IN_NEW_SELECTED_CIRCLE",newselectedUserMap,"IN_EVENT_ID",IN_EVENT_ID);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        imgPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!IN_EVENT_ID.equals("0")){
                    imgPublish.setImageResource(getIcon(SAVEICON,true));
                }else{
                    imgPublish.setImageResource(getIcon(PUBLISHICON,true));
                }
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if(!IN_EVENT_ID.equals("0")){
                            imgPublish.setImageResource(getIcon(SAVEICON,false));
                        }else{
                            imgPublish.setImageResource(getIcon(PUBLISHICON,false));
                        }
                        if (spnTypeUserSelection.getSelectedItemPosition() == 0) {
                            IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_event), getString(R.string.intafy_event_type_validation), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                @Override
                                public void NeutralMethod() {
                                    spnTypeUserSelection.performClick();
                                }
                            });
                        } else {
                            final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                            eventDataProvider.addOrEditEvent(IN_EVENT_ID,IN_EVENT_AVATAR,IN_EVENT_TITLE,IN_EVENT_LOCATION, IN_EVENT_STARTDATE, IN_EVENT_ENDDATE, spnTypeUserSelection.getSelectedItem().toString().toLowerCase(), selectedUserIds, IN_EVENT_DESCRIPTIONS, new WebCallListener() {
                                @Override
                                public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                    if (responseCode == 200) {
                                        IjoomerApplicationConfiguration.setReloadRequired(true);
                                        finish();
                                    } else {
                                        if(responseCode==416){
                                            IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_article),errorMessage, getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                                @Override
                                                public void NeutralMethod() {

                                                }
                                            });
                                        }else{
                                            responseErrorMessageHandler(responseCode);
                                        }
                                    }
                                }

                                @Override
                                public void onProgressUpdate(int progressCount) {
                                    seekBar.setProgress(progressCount);
                                }
                            });
                        }
                    }
                }, 1000);
            }
        });
    }

    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_event), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode == INDIVIDUAL_PUBLIC){
                setSelectedUser((HashMap<String,String>)data.getSerializableExtra("IN_SELECTED_USER")!=null ?(HashMap<String,String>)data.getSerializableExtra("IN_SELECTED_USER"):new HashMap<String, String>());
            }else if(requestCode==CIRCLE){
                setSelectedUser((HashMap<String,String>)data.getSerializableExtra("IN_SELECTED_CIRCLE")!=null ?(HashMap<String,String>)data.getSerializableExtra("IN_SELECTED_CIRCLE"):new HashMap<String, String>());
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setSelectedUser(HashMap<String,String> selectedUsers){
        if(spnTypeUserSelection.getSelectedItem().toString().equals("Circle")){
            newselectedUserMap = selectedUsers;
            selectedUserName="";
            selectedUserIds="";
            Iterator<String> itr = newselectedUserMap.keySet().iterator();
            while (itr.hasNext()) {
                String key = itr.next();
                boolean isNewUser = true;
                if(IN_EVENT_CIRCLE.trim().length()>0){
                    String[] eventUsers = IN_EVENT_CIRCLE.split(",");
                    for (String user :eventUsers){
                        if(user.split(":")[0].equals(key)){
                            isNewUser = false;
                            break;
                        }
                    }
                }
                if(isNewUser) {
                    selectedUserIds += key + ",";
                }
                selectedUserName += newselectedUserMap.get(key) + ",";
            }
            if(selectedUserIds.trim().length()>1){
                selectedUserIds = selectedUserIds.substring(0,selectedUserIds.length()-1);
            }
            if(selectedUserName.trim().length()>1){
                selectedUserName = selectedUserName.substring(0,selectedUserName.length()-1);
            }

            txtUserSelected.setText(selectedUserName);
        }else{
            newselectedUserMap = selectedUsers;
            selectedUserName="";
            selectedUserIds="";
            Iterator<String> itr = newselectedUserMap.keySet().iterator();
            while (itr.hasNext()) {
                String key = itr.next();
                boolean isNewUser = true;
                if(IN_EVENT_USERS.trim().length()>0){
                    String[] eventUsers = IN_EVENT_USERS.split(",");
                    for (String user :eventUsers){
                        if(user.split(":")[0].equals(key)){
                            isNewUser = false;
                            break;
                        }
                    }
                }
                if(isNewUser) {
                    selectedUserIds += key + ",";
                }
                selectedUserName += newselectedUserMap.get(key) + ",";
            }
            if(selectedUserIds.trim().length()>1){
                selectedUserIds = selectedUserIds.substring(0,selectedUserIds.length()-1);
            }
            if(selectedUserName.trim().length()>1){
                selectedUserName = selectedUserName.substring(0,selectedUserName.length()-1);
            }

            txtUserSelected.setText(selectedUserName);
        }
    }
    private void setIntialSelectedUser(){
        String[] users=null;
        if(IN_EVENT_TYPE.equalsIgnoreCase(getString(R.string.intafy_individual))){
            if(IN_EVENT_USERS.trim().length()>0){
                users = IN_EVENT_USERS.split(",");
            }
        }else if(IN_EVENT_TYPE.equalsIgnoreCase(getString(R.string.intafy_circle))){
            if(IN_EVENT_CIRCLE.trim().length()>0){
                users = IN_EVENT_CIRCLE.split(",");
            }
        }
        if(users!=null) {
            selectedUserMap = new HashMap<String, String>();
            for (int i = 0; i < users.length; i++) {
                String[] user = users[i].split(":");
                selectedUserMap.put(user[0], user[1]);
            }
            selectedUserName = "";
            selectedUserIds = "";
            Iterator<String> itr = selectedUserMap.keySet().iterator();
            while (itr.hasNext()) {
                String key = itr.next();
                selectedUserIds += key + ",";
                selectedUserName += selectedUserMap.get(key) + ",";
            }
            if (selectedUserIds.trim().length() > 1) {
                selectedUserIds = selectedUserIds.substring(0, selectedUserIds.length() - 1);
            }
            if (selectedUserName.trim().length() > 1) {
                selectedUserName = selectedUserName.substring(0, selectedUserName.length() - 1);
            }

            txtUserSelected.setText(selectedUserName);
        }
        if(IN_EVENT_TYPE.equalsIgnoreCase(getString(R.string.intafy_individual))){
            spnTypeUserSelection.setSelection(1);
        }else if(IN_EVENT_TYPE.equalsIgnoreCase(getString(R.string.intafy_circle))){
            spnTypeUserSelection.setSelection(2);
        }

    }
}
