package com.ijoomer.intafy.src;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.ViewFlipper;

import com.androidquery.AQuery;
import com.ijoomer.common.classes.IjoomerHomeMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.common.flipanimation.AnimationFactory;
import com.ijoomer.components.intafy.IjoomerIntafyArticleListActivity;
import com.ijoomer.components.intafy.IjoomerIntafyCircleListActivity;
import com.ijoomer.components.intafy.IjoomerIntafyDirectoryListActivity;
import com.ijoomer.components.intafy.IjoomerIntafyEventListActivity;
import com.ijoomer.components.intafy.IjoomerIntafyMessageListActivity;
import com.ijoomer.components.intafy.IjoomerIntafyNetworkAddActivity;
import com.ijoomer.components.intafy.IjoomerIntafyProfileActivity;
import com.ijoomer.components.intafy.IjoomerIntafySettingsActivity;
import com.ijoomer.components.intafy.IjoomerIntafySocialActivity;
import com.ijoomer.components.intafy.IntafyTagHolder;
import com.ijoomer.customviews.IjoomerRadioButton;
import com.ijoomer.customviews.RoundedImageView;
import com.ijoomer.library.intafy.IntafyNetworkDataProvider;
import com.ijoomer.oauth.IjoomerOauth;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.SmartApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 * 
 * @author tasol
 * 
 */
public class IjoomerHomeActivity extends IjoomerHomeMaster implements IntafyTagHolder {

    private LinearLayout lnrNetwork;
    private ViewFlipper viewFlipper;
    private ImageView imgSettings;
    private ImageView imgDirectories;
    private ImageView imgNews;
    private ImageView imgEvent;
    private ImageView imgProfile;
    private ImageView imgCircle;
    private ImageView imgMessage;
    private ImageView imgSocial;
    private ImageView networkLogo;

    private IntafyNetworkDataProvider intafyNetworkDataProvider;
    private AQuery aQuery;
    private boolean isLeftFlip;

	/**
	 * Overrides methods
	 */

	@Override
	public int setLayoutId() {
		return R.layout.ijoomer_home_menu;
	}

	@Override
	public void initComponents() {
        networkLogo = (ImageView) findViewById(R.id.networkLogo);
        lnrNetwork = (LinearLayout) findViewById(R.id.lnrNetwork);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        imgSettings = (ImageView) findViewById(R.id.imgSettings);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        imgNews = (ImageView) findViewById(R.id.imgNews);
        imgEvent = (ImageView) findViewById(R.id.imgEvent);
        imgCircle = (ImageView) findViewById(R.id.imgCircle);
        imgMessage = (ImageView) findViewById(R.id.imgMessage);
        imgSocial = (ImageView) findViewById(R.id.imgSocial);
        imgDirectories = (ImageView) findViewById(R.id.imgDirectories);
        intafyNetworkDataProvider = new IntafyNetworkDataProvider(this);
        aQuery = new AQuery(this);
        if((getIntent().getStringExtra("IN_USERID")!=null?getIntent().getStringExtra("IN_USERID"):"").equals("0")){
            ArrayList<HashMap<String,String>> networks = intafyNetworkDataProvider.getNetworkListFromDB();
            if(networks==null || networks.size()<=0) {
                loadNew(IjoomerIntafyNetworkAddActivity.class, this, false);
            }
        }
	}

    @Override
    protected void onResume() {
        super.onResume();
        if(IjoomerApplicationConfiguration.isReloadRequired()){
            IjoomerOauth.getInstance(this).authenticateUser(this);
            IjoomerApplicationConfiguration.setReloadRequired(false);
        }
        prePareNetwok();
        updateNotifiaction();
    }

    public void updateNotifiaction(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
                String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
                ArrayList<HashMap<String,String>> messgePush = intafyNetworkDataProvider.getPushNotificationData(networkId,connectedUserId,"message");
                if(messgePush!=null && messgePush.size()>0){
                    imgMessage.setImageResource(R.drawable.home_messages_push_icon_en);
                }
                ArrayList<HashMap<String,String>> circlePush = intafyNetworkDataProvider.getPushNotificationData(networkId,connectedUserId,"group");
                if(circlePush!=null && circlePush.size()>0){
                    imgCircle.setImageResource(R.drawable.home_circles_push_icon_en);
                }
                ArrayList<HashMap<String,String>> eventPush = intafyNetworkDataProvider.getPushNotificationData(networkId,connectedUserId,"event");
                if(eventPush!=null && eventPush.size()>0){
                    imgEvent.setImageResource(R.drawable.home_event_push_icon_en);
                }
            }
        });
    }

    private void prePareNetwok(){
        ArrayList<HashMap<String,String>> networks = intafyNetworkDataProvider.getNetworkListFromDB();
        if(networks!=null && networks.size()>0){
            try{
                lnrNetwork.removeAllViews();
            }catch (Throwable e){
                e.printStackTrace();
            }
            for (int i=0;i<networks.size();i++){
                View view  = LayoutInflater.from(this).inflate(R.layout.ijoomer_intafy_home_network_item,null,false);
                RoundedImageView imgNetworkIcon = (RoundedImageView) view.findViewById(R.id.imgNetworkIcon);

                aQuery.id(imgNetworkIcon).image(networks.get(i).get(NETWORKICON),true,true);
                if(networks.get(i).get("networkId").equals(getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0"))){
                    aQuery.id(networkLogo).image(networks.get(i).get(NETWORKLOGO),true,true);
                    networkLogo.setVisibility(View.VISIBLE);
                }
                view.setTag(networks.get(i));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final HashMap<String,String> data = (HashMap<String, String>) v.getTag();
                        aQuery.id(networkLogo).image(data.get(NETWORKLOGO),true,true);
                        networkLogo.setVisibility(View.VISIBLE);
                        final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                        IjoomerOauth.getInstance(IjoomerHomeActivity.this).authenticateUser(data, IjoomerHomeActivity.this,new WebCallListener() {
                            @Override
                            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                if(responseCode==200){
                                    if(isLeftFlip){
                                        AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
                                        isLeftFlip=false;
                                    }else{
                                        AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.RIGHT_LEFT);
                                        isLeftFlip=true;
                                    }
                                    aQuery.id(networkLogo).image(data.get(NETWORKLOGO),true,true);
                                    networkLogo.setVisibility(View.VISIBLE);
                                }else{
                                    responseErrorMessageHandler(responseCode);
                                }
                            }

                            @Override
                            public void onProgressUpdate(int progressCount) {
                                proSeekBar.setProgress(progressCount);
                            }
                        });
                    }
                });
                lnrNetwork.addView(view);
            }
        }
    }
    @Override
	public void prepareViews() {
	}

	@Override
	public void setActionListeners() {

        imgSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgSocial.setImageResource(getIcon(HOMESOCIALICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            imgSocial.setImageResource(getIcon(HOMESOCIALICON,false));
                            loadNew(IjoomerIntafySocialActivity.class,IjoomerHomeActivity.this,false);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }, 1000);

            }
        });
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgSettings.setImageResource(getIcon(HOMESETTINGICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            imgSettings.setImageResource(getIcon(HOMESETTINGICON,false));
                            loadNew(IjoomerIntafySettingsActivity.class,IjoomerHomeActivity.this,false);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }, 1000);
            }
        });
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgProfile.setImageResource(getIcon(HOMEPROFILEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgProfile.setImageResource(getIcon(HOMEPROFILEICON,false));
                        if(!getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0").equals("0")) {
                            loadNew(IjoomerIntafyProfileActivity.class, IjoomerHomeActivity.this, false);
                        }else{
                            showAddNetworkDialog();
                        }
                    }
                }, 1000);
            }
        });
        imgDirectories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgDirectories.setImageResource(getIcon(HOMEDIRECTORIESICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgDirectories.setImageResource(getIcon(HOMEDIRECTORIESICON,false));
                        if(!getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0").equals("0")) {
                            loadNew(IjoomerIntafyDirectoryListActivity.class, IjoomerHomeActivity.this, false);
                        }else{
                            showAddNetworkDialog();
                        }
                    }
                }, 1000);
            }
        });
        imgNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgNews.setImageResource(getIcon(HOMENEWSICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgNews.setImageResource(getIcon(HOMENEWSICON,false));
                        if(!getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0").equals("0")) {
                            loadNew(IjoomerIntafyArticleListActivity.class, IjoomerHomeActivity.this, false);
                        }else{
                            showAddNetworkDialog();
                        }
                    }
                }, 1000);
            }
        });
        imgEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEvent.setImageResource(getIcon(HOMEEVENTICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgEvent.setImageResource(getIcon(HOMEEVENTICON,false));
                        String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
                        String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
                        intafyNetworkDataProvider.deletePushNotificationData(networkId,connectedUserId,"event");
                        if(!getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0").equals("0")) {
                            loadNew(IjoomerIntafyEventListActivity.class, IjoomerHomeActivity.this, false);
                        }else{
                            showAddNetworkDialog();
                        }
                    }
                }, 1000);
            }
        });
        imgCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgCircle.setImageResource(getIcon(HOMECIRCLEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgCircle.setImageResource(getIcon(HOMECIRCLEICON,false));
                        String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
                        String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
                        intafyNetworkDataProvider.deletePushNotificationData(networkId,connectedUserId,"group");
                        if(!getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0").equals("0")) {
                            loadNew(IjoomerIntafyCircleListActivity.class, IjoomerHomeActivity.this, false);
                        }else{
                            showAddNetworkDialog();
                        }
                    }
                }, 1000);
            }
        });
        imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgMessage.setImageResource(getIcon(HOMEMESSAGEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgMessage.setImageResource(getIcon(HOMEMESSAGEICON,false));
                        String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
                        String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
                        intafyNetworkDataProvider.deletePushNotificationData(networkId,connectedUserId,"message");
                        if(!getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0").equals("0")) {
                            loadNew(IjoomerIntafyMessageListActivity.class, IjoomerHomeActivity.this, false);
                        }else{
                            showAddNetworkDialog();
                        }
                    }
                }, 1000);
            }
        });

	}
    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_home), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
                getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {
                loadNew(IjoomerIntafyNetworkAddActivity.class,IjoomerHomeActivity.this,false);
            }
        });
    }

    private void showAddNetworkDialog(){
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_network), getString(R.string.intafy_add_network_validation),
                getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                    @Override
                    public void NeutralMethod() {

                    }
                });
    }

}
