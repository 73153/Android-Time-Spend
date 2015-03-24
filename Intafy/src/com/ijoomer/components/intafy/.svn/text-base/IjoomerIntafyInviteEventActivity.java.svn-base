package com.ijoomer.components.intafy;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyEventDataProvider;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafyInviteEventActivity extends IjoomerIntafyMaster {


    private Spinner spnTypeUserSelection;
    private IjoomerTextView txtUserSelected;
    private ImageView imgAdd;
    private String IN_EVENT_ID;
    private final int INDIVIDUAL_PUBLIC=1;
    private final int CIRCLE=2;
    private HashMap<String,String> selectedUserMap;
    private HashMap<String,String> selectedCircleMap;
    private HashMap<String,String> newselectedUserMap;
    private String selectedUserIds;
    private String selectedUserName;
    private String IN_EVENT_TYPE;
    private String IN_EVENT_USERS;
    private String IN_EVENT_CIRCLE;
    private IntafyEventDataProvider eventDataProvider;
    private IjoomerTextView headerLeftText;
    private LinearLayout lnrHeaderLeft;
    private IjoomerTextView headerText;
    /**
     * Overrides methods
     */


    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_invite_event;
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
        imgAdd=(ImageView)findViewById(R.id.imgAdd);
        headerLeftText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        lnrHeaderLeft = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderLeft);
        eventDataProvider = new IntafyEventDataProvider(this);
    }

    @Override
    public void prepareViews() {
        headerText.setText(getString(R.string.intafy_invite_user_to_event));
        headerLeftText.setText(getString(R.string.intafy_back));
        IN_EVENT_ID = getIntent().getStringExtra("IN_EVENT_ID")!=null?getIntent().getStringExtra("IN_EVENT_ID"):"0";
        IN_EVENT_TYPE = getIntent().getStringExtra("IN_EVENT_TYPE")!=null ?getIntent().getStringExtra("IN_EVENT_TYPE"):"";
        IN_EVENT_USERS = getIntent().getStringExtra("IN_EVENT_USERS")!=null ?getIntent().getStringExtra("IN_EVENT_USERS"):"";
        IN_EVENT_CIRCLE = getIntent().getStringExtra("IN_EVENT_CIRCLE")!=null ?getIntent().getStringExtra("IN_EVENT_CIRCLE"):"";
        spnTypeUserSelection.setAdapter(new IjoomerUtilities.MyCustomAdapter(this, new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.intafy_event_type)))));


        if(IN_EVENT_TYPE.trim().length()>0){
            if(IN_EVENT_TYPE.equalsIgnoreCase(getString(R.string.intafy_public))){
                spnTypeUserSelection.setSelection(3);
            }else{
                setIntialSelectedUser();
            }
        }

    }

    @Override
    public void setActionListeners() {

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SeekBar seekBar =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                eventDataProvider.inviteFriend(spnTypeUserSelection.getSelectedItem().toString().toLowerCase(),IN_EVENT_ID,selectedUserIds,new WebCallListener() {
                    @Override
                    public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                        if (responseCode == 200) {
                            IjoomerApplicationConfiguration.setReloadRequired(true);
                            finish();
                        } else {
                            responseErrorMessageHandler(responseCode);
                        }
                    }

                    @Override
                    public void onProgressUpdate(int progressCount) {
                        seekBar.setProgress(progressCount);
                    }
                });
            }
        });
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
                    if(selectedCircleMap==null){
                        selectedCircleMap = new HashMap<String, String>();
                    }
                    if(newselectedUserMap==null){
                        newselectedUserMap = new HashMap<String, String>();
                    }
                    if(spnTypeUserSelection.getSelectedItemPosition()==1){
                        try {
                            loadNewResult(IjoomerIntafyInviteUserForEventActivity.class,IjoomerIntafyInviteEventActivity.this,INDIVIDUAL_PUBLIC,"IN_SELECTED_USER",selectedUserMap,"IN_NEW_SELECTED_CIRCLE",newselectedUserMap,"IN_EVENT_ID",IN_EVENT_ID);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }else if(spnTypeUserSelection.getSelectedItemPosition()==2){
                        try {
                            loadNewResult(IjoomerIntafyInviteCircleActivity.class,IjoomerIntafyInviteEventActivity.this,CIRCLE,"IN_SELECTED_CIRCLE",selectedCircleMap,"IN_NEW_SELECTED_CIRCLE",newselectedUserMap,"IN_EVENT_ID",IN_EVENT_ID);
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
                            setSelectedUser(selectedUserMap);
                        }else{
                            selectedUserMap = new HashMap<String, String>();
                        }
                        if(selectedUserMap==null){
                            selectedUserMap = new HashMap<String, String>();
                        }
                        newselectedUserMap = new HashMap<String, String>();
                        setSelectedUser(selectedUserMap);
                        try {
                            loadNewResult(IjoomerIntafyInviteUserForEventActivity.class,IjoomerIntafyInviteEventActivity.this,INDIVIDUAL_PUBLIC,"IN_SELECTED_USER",selectedUserMap,"IN_NEW_SELECTED_CIRCLE",newselectedUserMap,"IN_EVENT_ID",IN_EVENT_ID);
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
                            setSelectedUser(selectedCircleMap);
                        }
                        else{
                            selectedCircleMap = new HashMap<String, String>();
                        }
                        if(selectedCircleMap==null){
                            selectedCircleMap = new HashMap<String, String>();
                        }
                        newselectedUserMap = new HashMap<String, String>();
                        setSelectedUser(selectedCircleMap);
                        try {
                            loadNewResult(IjoomerIntafyInviteCircleActivity.class,IjoomerIntafyInviteEventActivity.this,CIRCLE,"IN_SELECTED_CIRCLE",selectedCircleMap,"IN_NEW_SELECTED_CIRCLE",newselectedUserMap,"IN_EVENT_ID",IN_EVENT_ID);
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
    }

    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_article), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

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
            if(IN_EVENT_TYPE.equalsIgnoreCase(getString(R.string.intafy_individual))){
                spnTypeUserSelection.setSelection(1);
            }else if(IN_EVENT_TYPE.equalsIgnoreCase(getString(R.string.intafy_circle))){
                spnTypeUserSelection.setSelection(2);
            }

            txtUserSelected.setText(selectedUserName);
        }

    }
}
