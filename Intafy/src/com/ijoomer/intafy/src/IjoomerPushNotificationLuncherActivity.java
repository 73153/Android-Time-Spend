package com.ijoomer.intafy.src;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.ijoomer.caching.IjoomerCaching;
import com.ijoomer.common.classes.IjoomerSuperMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerGlobalConfiguration;
import com.ijoomer.custom.interfaces.IjoomerKeys;
import com.ijoomer.library.intafy.IntafyNetworkDataProvider;
import com.ijoomer.oauth.IjoomerOauth;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.SmartApplication;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tasol on 22/7/13.
 */
public class IjoomerPushNotificationLuncherActivity extends IjoomerSuperMaster implements IjoomerKeys {

    private LinearLayout lnrPbr;
    IjoomerGlobalConfiguration globalConfiguration;
    private String IN_PUSH_TYPE;
    private String IN_PUSH_ID;
    private String IN_MESSAGE_ID;
    private String IN_USER_ID;
    private String IN_USER_AVATAR;
    private String IN_USERNAME;
    private String IN_EVENT_ID;
    private String IN_CIRCLE_ID;
    private String IN_CIRCLE_NAME;
    private String IN_CIRCLE_ISEDIT;

    private Intent gotoIntent;

    @Override
    public String[] setTabItemNames() {
        return new String[0];
    }

    @Override
    public int setTabBarDividerResId() {
        return 0;
    }

    @Override
    public int setTabItemLayoutId() {
        return 0;
    }

    @Override
    public int[] setTabItemOnDrawables() {
        return new int[0];
    }

    @Override
    public int[] setTabItemOffDrawables() {
        return new int[0];
    }

    @Override
    public int[] setTabItemPressDrawables() {
        return new int[0];
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_push_luncher;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public int setHeaderLayoutId() {
        return 0;
    }

    @Override
    public int setFooterLayoutId() {
        return 0;
    }

    @Override
    public void initComponents() {
        lnrPbr = (LinearLayout) findViewById(R.id.lnrPbr);
        globalConfiguration = new IjoomerGlobalConfiguration(this);
        getIntentData();
    }

    @Override
    public void prepareViews() {

    }

    @Override
    public void setActionListeners() {

    }

    private void getIntentData() {
        IN_PUSH_TYPE = getIntent().getStringExtra("IN_PUSH_TYPE") != null ? getIntent().getStringExtra("IN_PUSH_TYPE") : "";
        IN_PUSH_ID = getIntent().getStringExtra("IN_PUSH_ID") != null ? getIntent().getStringExtra("IN_PUSH_ID") : "";

        getPushNotifiactionData();
    }

    private void getPushNotifiactionData() {

        lnrPbr.setVisibility(View.VISIBLE);
        String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
        String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
        if(IN_PUSH_ID.split(":")[0].equals(networkId) && IN_PUSH_ID.split(":")[1].equals(connectedUserId)){
            getPushData();
        }else{
            HashMap<String,String> networkData = new IntafyNetworkDataProvider(IjoomerPushNotificationLuncherActivity.this).getNetwork(IN_PUSH_ID.split(":")[0],IN_PUSH_ID.split(":")[1]);
            IjoomerOauth.getInstance(IjoomerPushNotificationLuncherActivity.this).authenticateUser(networkData, IjoomerPushNotificationLuncherActivity.this,new WebCallListener() {
                @Override
                public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                    if(responseCode==200){
                        getPushData();
                    }else{
                        responseErrorMessageHandler(responseCode,true);
                    }
                }

                @Override
                public void onProgressUpdate(int progressCount) {
                }
            });

        }



    }

    private void getPushData(){
        globalConfiguration.getPushData(IN_PUSH_ID.split(":")[2], new WebCallListener() {
            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                gotoIntent = null;
                if (responseCode == 200) {
                    try {
                        JSONObject pushData = ((JSONObject)data2).getJSONObject("data").getJSONObject("content_data");
                        if (IN_PUSH_TYPE.equals("message")) {
                            gotoIntent = new Intent();
                            gotoIntent.setClassName(IjoomerPushNotificationLuncherActivity.this, "com.ijoomer.components.intafy.IjoomerIntafyMessageDetailListActivity");
                            IN_MESSAGE_ID =pushData.getString("messageId");
                            IN_USER_ID = pushData.getString("fromId");
                            IN_USER_AVATAR = pushData.getString("fromAvatar");
                            IN_USERNAME = pushData.getString("fromName");
                            gotoIntent.putExtra("IN_MESSAGE_ID",IN_MESSAGE_ID);
                            gotoIntent.putExtra("IN_USER_ID",IN_USER_ID);
                            gotoIntent.putExtra("IN_USER_AVATAR",IN_USER_AVATAR);
                            gotoIntent.putExtra("IN_USERNAME",IN_USERNAME);
                            gotoIntent.putExtra("IN_PUSHID",IN_PUSH_ID.split(":")[2]);
                            gotoIntent.putExtra("IN_PUSH_TYPE",IN_PUSH_TYPE);
                        } else if (IN_PUSH_TYPE.equals("group")) {
                            gotoIntent = new Intent();
                            if(pushData.has("circleId")){
                                IN_CIRCLE_ID =pushData.getString("circleId");
                                IN_CIRCLE_NAME = pushData.getString("circleName");
                                IN_CIRCLE_ISEDIT = pushData.getString("isEditDelete");
                                gotoIntent.setClassName(IjoomerPushNotificationLuncherActivity.this, "com.ijoomer.components.intafy.IjoomerIntafyCircleMessageListActivity");
                                gotoIntent.putExtra("IN_CIRCLE_ID",IN_CIRCLE_ID);
                                gotoIntent.putExtra("IN_CIRCLE_NAME",IN_CIRCLE_NAME);
                                gotoIntent.putExtra("IN_CIRCLE_ISEDIT",IN_CIRCLE_ISEDIT);
                                gotoIntent.putExtra("IN_PUSHID",IN_PUSH_ID.split(":")[2]);
                                gotoIntent.putExtra("IN_PUSH_TYPE",IN_PUSH_TYPE);
                            }else{
                                gotoIntent.setClassName(IjoomerPushNotificationLuncherActivity.this, "com.ijoomer.components.intafy.IjoomerIntafyCircleListActivity");
                            }
                        } else if (IN_PUSH_TYPE.equals("event")) {
                            gotoIntent = new Intent();
                            gotoIntent.setClassName(IjoomerPushNotificationLuncherActivity.this, "com.ijoomer.components.intafy.IjoomerIntafyEventDetailsActivity");
                            IN_EVENT_ID =pushData.getString("eventId");
                            gotoIntent.putExtra("IN_EVENT_ID",IN_EVENT_ID);
                            gotoIntent.putExtra("IN_PUSHID",IN_PUSH_ID.split(":")[2]);
                            gotoIntent.putExtra("IN_PUSH_TYPE",IN_PUSH_TYPE);
                        }
                        startActivity(gotoIntent);
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                lnrPbr.setVisibility(View.GONE);
            }

            @Override
            public void onProgressUpdate(int progressCount) {
            }
        });
    }

    /**
     * This method used to shown response message.
     *
     * @param responseCode
     *            represented response code
     * @param finishActivityOnConnectionProblem
     *            represented finish activity on connection problem
     */
    private void responseErrorMessageHandler(final int responseCode, final boolean finishActivityOnConnectionProblem) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_loading_sending_request), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
                getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                    @Override
                    public void NeutralMethod() {
                        if (responseCode == 599 && finishActivityOnConnectionProblem) {
                            finish();
                        }
                    }
                });
    }

}
