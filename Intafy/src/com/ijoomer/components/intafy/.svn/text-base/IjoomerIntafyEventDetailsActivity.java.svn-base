package com.ijoomer.components.intafy;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ijoomer.common.classes.IjoomerFileChooserActivity;
import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.customviews.RoundedImageView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyEventDataProvider;
import com.ijoomer.library.intafy.IntafyNetworkDataProvider;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertMagnatic;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.SmartApplication;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafyEventDetailsActivity extends IjoomerIntafyMaster implements IntafyTagHolder {

    private IjoomerTextView txtEventTitle;
    private IjoomerTextView txtEventTime;
    private IjoomerTextView txtEventDate;
    private IjoomerTextView txtEventDescription;
    private IjoomerTextView txtEventAttending;
    private IjoomerTextView txtEventAddress;
    private ProgressBar pbrImageLoad;
    private RoundedImageView imgEventAvatar;
    private ImageView imgEventInvite;
    private ImageView imgEventSave;
    private ImageView imgEditEvent;
    private ImageView imgExportEvent;
    private ImageView imgDeleteEvent;
    private String IN_EVENT_ID;
    private String IN_PUSHID;
    private String IN_PUSH_TYPE;
    private IntafyEventDataProvider eventDataProvider;
    private HashMap<String,String> eventDetails;
    private AQuery aQuery;
    private SeekBar seekBar;
    private IjoomerTextView headerLeftText;
    private IjoomerTextView headerRightText;
    private IjoomerTextView headerText;
    private LinearLayout lnrHeaderLeft;
    private LinearLayout lnrHeaderRight;
    final private int FILE_LOCATION = 1;

    /**
     * Overrides methods
     */


    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_event_details;
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

        txtEventTitle = (IjoomerTextView)findViewById(R.id.txtEventTitle);
        txtEventDate = (IjoomerTextView)findViewById(R.id.txtEventDate);
        txtEventTime = (IjoomerTextView)findViewById(R.id.txtEventTime);
        txtEventDescription = (IjoomerTextView)findViewById(R.id.txtEventDescription);
        txtEventAttending = (IjoomerTextView)findViewById(R.id.txtEventAttending);
        txtEventAddress = (IjoomerTextView)findViewById(R.id.txtEventAddress);

        imgEventInvite = (ImageView)findViewById(R.id.imgEventInvite);
        imgEventSave = (ImageView)findViewById(R.id.imgEventSave);
        pbrImageLoad = (ProgressBar)findViewById(R.id.pbrImageLoad);
        imgEventAvatar = (RoundedImageView)findViewById(R.id.imgEventAvatar);
        imgEditEvent = (ImageView)findViewById(R.id.imgEditEvent);
        imgExportEvent = (ImageView)findViewById(R.id.imgExportEvent);
        imgDeleteEvent = (ImageView)findViewById(R.id.imgDeleteEvent);
        lnrHeaderLeft = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderLeft);
        lnrHeaderRight = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderRight);
        headerLeftText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        headerRightText  = (IjoomerTextView) getHeaderView().findViewById(R.id.txtLast);
        eventDataProvider = new IntafyEventDataProvider(this);
        aQuery = new AQuery(this);

    }

    @Override
    public void prepareViews() {
        IN_EVENT_ID = getIntent().getStringExtra("IN_EVENT_ID")!=null ? getIntent().getStringExtra("IN_EVENT_ID"):"0";
        IN_PUSHID = getIntent().getStringExtra("IN_PUSHID")!=null ? getIntent().getStringExtra("IN_PUSHID"):"";
        IN_PUSH_TYPE = getIntent().getStringExtra("IN_PUSH_TYPE")!=null ? getIntent().getStringExtra("IN_PUSH_TYPE"):"";
        if(IN_PUSHID.trim().length()>0 && IN_PUSH_TYPE.trim().length()>0){
            String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
            String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
            new IntafyNetworkDataProvider(this).deletePushNotificationData(networkId,connectedUserId,IN_PUSH_TYPE);
        }
        if(!IN_EVENT_ID.equals("0")){
            getEvenDetails(true);
        }
        headerText.setText(getString(R.string.intafy_event_details));
        headerLeftText.setText(getString(R.string.intafy_back));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(IjoomerApplicationConfiguration.isReloadRequired()){
            getEvenDetails(false);
        }
    }

    private void getEvenDetails(boolean isProgressShow){
        if(isProgressShow){
            seekBar =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
        }
        String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID, "0");
        String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID, "0");
        eventDataProvider.getEventDetails(IN_EVENT_ID, networkId, connectedUserId, new WebCallListener() {
            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                if(responseCode==200){
                    eventDetails = (HashMap<String,String>)data2;
                    if(eventDetails!=null) {
                        setEventDetails();
                    }
                }else{
                    responseErrorMessageHandler(responseCode);
                }
            }

            @Override
            public void onProgressUpdate(int progressCount) {
                seekBar.setProgress(progressCount);
            }
        });
    }
    private void setEventDetails(){
        txtEventTitle.setText(eventDetails.get(TITLE));
        txtEventAddress.setText(eventDetails.get(LOCATION));
        txtEventDescription.setText(Html.fromHtml(eventDetails.get(DESCRIPTION)));
        txtEventDescription.setMovementMethod(new LinkMovementMethod());
        txtEventDate.setText(IjoomerUtilities.convertDateStringFormat(eventDetails.get(DATE), "yyyy-MM-dd", IjoomerApplicationConfiguration.dateFormat));
        txtEventAddress.setText(eventDetails.get(LOCATION));
        if(eventDetails.containsKey(MEMBERCOUNT) && Integer.valueOf(eventDetails.get(MEMBERCOUNT)) >0){
            txtEventAttending.setText(String.format(getString(R.string.intafy_event_attending_people),eventDetails.get(MEMBERCOUNT)));
        }else{
            txtEventAttending.setText(String.format(getString(R.string.intafy_event_attending_people),getString(R.string.intafy_no)));
        }
        txtEventTime.setText(eventDetails.get(STARTTIME)+" - "+eventDetails.get(ENDTIME));
        aQuery.id(imgEventAvatar).progress(pbrImageLoad).image(eventDetails.get(AVATAR),true,true,150,R.drawable.ic_launcher);

        if(eventDetails.get(ALLOWINVITE).equals("1")){
            imgEventInvite.setVisibility(View.VISIBLE);
        }else{
            imgEventInvite.setVisibility(View.GONE);
        }
        if(eventDetails.get(MYSTATUS).equals("1")) {
            imgExportEvent.setVisibility(View.VISIBLE);
            imgEventSave.setVisibility(View.GONE);
        }else{
            imgExportEvent.setVisibility(View.GONE);
            imgEventSave.setVisibility(View.VISIBLE);
        }

        if(eventDetails.get(MYSTATUS).equals("1") && !eventDetails.get(ALLOWINVITE).equals("1")){
            ((IjoomerTextView) getHeaderView().findViewById(R.id.txtLast)).setText(getString(R.string.intafy_leave));
        }
        try{
            JSONObject menu = new JSONObject(eventDetails.get(MENU));
            if(menu.getString(EDITEVENT).equals("1")){
                imgEditEvent.setVisibility(View.VISIBLE);
            }
            if(menu.getString(DELETEEVENT).equals("1")){
                imgDeleteEvent.setVisibility(View.VISIBLE);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }



    }

    @Override
    public void setActionListeners() {

        lnrHeaderRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                headerRightText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        headerRightText.setTextColor(getResources().getColor(R.color.white));
                        final SeekBar seekBar =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                        eventDataProvider.eventResponse(IN_EVENT_ID,"3",new WebCallListener() {
                            @Override
                            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                if(responseCode==200){
                                    IjoomerApplicationConfiguration.setReloadRequired(true);
                                    finish();
                                }else{
                                    responseErrorMessageHandler(responseCode);
                                }
                            }

                            @Override
                            public void onProgressUpdate(int progressCount) {
                                seekBar.setProgress(progressCount);
                            }
                        });
                    }
                }, 1000);
            }
        });
        imgDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgDeleteEvent.setImageResource(getIcon(DELETEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgDeleteEvent.setImageResource(getIcon(DELETEICON,false));
                        IjoomerUtilities.getCustomConfirmDialog(getString(R.string.intafy_event_details), getString(R.string.intafy_are_you_sure_remove_event), getString(R.string.intafy_confirm), getString(R.string.intafy_cancel), new CustomAlertMagnatic() {
                            @Override
                            public void PositiveMethod() {
                                final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                                eventDataProvider.removeEvent(IN_EVENT_ID, new WebCallListener() {
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

                            @Override
                            public void NegativeMethod() {
                            }
                        });
                    }
                }, 1000);
            }
        });
        imgEditEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEditEvent.setImageResource(getIcon(EDITICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgEditEvent.setImageResource(getIcon(EDITICON,false));
                        try{
                            loadNew(IjoomerIntafyEventAddFirstStepsActivity.class,IjoomerIntafyEventDetailsActivity.this,false,"IN_EVENT_ID",eventDetails.get("id"));
                        }catch (Throwable e){
                            e.printStackTrace();
                        }
                    }
                }, 1000);

            }
        });
        imgExportEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgExportEvent.setImageResource(getIcon(EXPORTICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgExportEvent.setImageResource(getIcon(EXPORTICON,false));
                        try{
                            loadNewResult(IjoomerFileChooserActivity.class, IjoomerIntafyEventDetailsActivity.this,FILE_LOCATION);
                        }catch (Throwable e){
                            e.printStackTrace();
                        }
                    }
                }, 1000);
            }
        });
        imgEventSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEventSave.setImageResource(getIcon(SAVEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgEventSave.setImageResource(getIcon(SAVEICON,false));
                        final SeekBar seekBar =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                        eventDataProvider.eventResponse(IN_EVENT_ID,"2",new WebCallListener() {
                            @Override
                            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                if(responseCode==200){
                                    getEvenDetails(false);
                                    IjoomerApplicationConfiguration.setReloadRequired(true);
                                }else{
                                    responseErrorMessageHandler(responseCode);
                                }
                            }

                            @Override
                            public void onProgressUpdate(int progressCount) {
                                seekBar.setProgress(progressCount);
                            }
                        });
                    }
                }, 1000);
            }
        });
        imgEventInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEventInvite.setImageResource(getIcon(INVITEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgEventInvite.setImageResource(getIcon(INVITEICON,false));
                        try{
                            loadNew(IjoomerIntafyInviteEventActivity.class,IjoomerIntafyEventDetailsActivity.this,false,"IN_EVENT_ID",IN_EVENT_ID,"IN_EVENT_TYPE",eventDetails!=null ?eventDetails.get("type"):"","IN_EVENT_USERS",eventDetails!=null?eventDetails.get("memberIds"):"","IN_EVENT_CIRCLE",eventDetails!=null?eventDetails.get("groupIds"):"");
                        }catch (Throwable e){
                            e.printStackTrace();
                        }
                    }
                }, 1000);
            }
        });

        imgEventAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    loadNew(IjoomerIntafyImageViewActivity.class, IjoomerIntafyEventDetailsActivity.this, false, "IN_IMAGE", eventDetails.get("image_intro"));
                }catch (Throwable e){
                    e.printStackTrace();
                }
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
    }

    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_event_details), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FILE_LOCATION) {
                final String path = data.getStringExtra("IN_PATH");
                aQuery.download(eventDetails.get("icsUrl"), new File(path + eventDetails.get(TITLE)), new AjaxCallback<File>() {
                    @Override
                    public void callback(String url, File object, AjaxStatus status) {
                        super.callback(url, object, status);
                        if (status.getCode() == 200) {
                            IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_event_details),String.format(getString(R.string.file_exported_successfully),eventDetails.get(TITLE)), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                @Override
                                public void NeutralMethod() {

                                }
                            });


                        } else {
                            IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_event_details),status.getMessage(), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                @Override
                                public void NeutralMethod() {

                                }
                            });
                        }
                    }
                });
            }
        }
    }


}
