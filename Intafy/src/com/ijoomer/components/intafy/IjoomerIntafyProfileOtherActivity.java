package com.ijoomer.components.intafy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.androidquery.AQuery;
import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.customviews.RoundedImageView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyUserDataProvider;
import com.ijoomer.page.indicator.CirclePageIndicator;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

import org.json.JSONArray;
import org.json.JSONObject;

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
public class IjoomerIntafyProfileOtherActivity extends IjoomerIntafyMaster implements IntafyTagHolder {


    private ViewPager viewPager;
    private CirclePageIndicator indicator;
    private PageAdapter adapter;

    private IntafyUserDataProvider intafyUserDataProvider;
    private String IN_USERID;
    private ProgressBar pbrImageLoad;
    private ProgressBar pbrFieldLoad;

    private RoundedImageView imgUserImage;
    private ImageView imgNewMessage;
    private ImageView imgAddToCircle;
    private IjoomerTextView txtUserFristLastName;
    private IjoomerTextView txtUserStatus;
    private IjoomerTextView txtUserPincode;
    private AQuery androidQuery;
    private JSONArray profileField;
    private HashMap<String,String> profileData;

    private String email;
    private String mobileNo;
    private String phoneNo;
    private IjoomerTextView headerLeftText;
    private IjoomerTextView footerLeftText;
    private IjoomerTextView footerRightText;
    private IjoomerTextView headerText;
    private LinearLayout lnrHeaderLeft;
    private LinearLayout lnrFooterLeft;
    private LinearLayout lnrFooterRight;


    /**
     * Overrides methods
     */



    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_other_profile_details;
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
        return R.layout.ijoomer_intafy_footer;
    }

    @Override
    public void initComponents() {
        pbrImageLoad = (ProgressBar)findViewById(R.id.pbrImageLoad);
        pbrFieldLoad = (ProgressBar)findViewById(R.id.pbrFieldLoad);
        imgUserImage = (RoundedImageView) findViewById(R.id.imgUserImage);
        imgAddToCircle = (ImageView) findViewById(R.id.imgAddToCircle);
        imgNewMessage = (ImageView) findViewById(R.id.imgNewMessage);
        txtUserFristLastName = (IjoomerTextView) findViewById(R.id.txtUserFristLastName);
        txtUserStatus = (IjoomerTextView) findViewById(R.id.txtUserStatus);
        txtUserPincode = (IjoomerTextView) findViewById(R.id.txtUserPincode);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        indicator =(CirclePageIndicator) findViewById(R.id.indicator);
        headerLeftText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        footerLeftText = (IjoomerTextView) getFooterView().findViewById(R.id.txtFirst);
        footerRightText = (IjoomerTextView) getFooterView().findViewById(R.id.txtLast);
        lnrHeaderLeft = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderLeft);
        lnrFooterLeft = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterLeft);
        lnrFooterRight = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterRight);
        intafyUserDataProvider = new IntafyUserDataProvider(this);
        androidQuery = new AQuery(this);
        email="";
        mobileNo="";
        phoneNo="";
    }

    @Override
    public void prepareViews() {
        headerText.setText(getString(R.string.intafy_profile));
        headerLeftText.setText(getString(R.string.intafy_back));
        footerLeftText.setText(getString(R.string.intafy_call));
        footerRightText.setText(getString(R.string.intafy_email));
        getIntentData();
        getProfile();
    }


    @Override
    public void setActionListeners() {
        imgNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgNewMessage.setImageResource(getIcon(NEWMESSAGEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgNewMessage.setImageResource(getIcon(NEWMESSAGEICON,false));
                        try{
                            loadNew(IjoomerIntafyMessageAddActivity.class,IjoomerIntafyProfileOtherActivity.this,false,"IN_USER_ID",IN_USERID,"IN_USER_NAME",profileData.get(USER_NAME));
                        }catch (Throwable e){
                            e.printStackTrace();
                        }
                    }
                }, 1000);
            }
        });
        imgUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    loadNew(IjoomerIntafyImageViewActivity.class,IjoomerIntafyProfileOtherActivity.this,false,"IN_IMAGE",profileData.get(USER_AVATAR));
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        });
        imgAddToCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAddToCircle.setImageResource(getIcon(ADDTOCIRCLEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgAddToCircle.setImageResource(getIcon(ADDTOCIRCLEICON,false));
                        try{
                            loadNew(IjoomerIntafyCircleListActivity.class,IjoomerIntafyProfileOtherActivity.this,false,"IN_ISJOINGROUP",true,"IN_USER_ID",IN_USERID);
                        }catch (Throwable e){
                            e.printStackTrace();
                        }
                    }
                }, 1000);
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
        lnrFooterRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.trim().length() > 0) {
                    footerRightText.setTextColor(getResources().getColor(R.color.blue));
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            footerRightText.setTextColor(getResources().getColor(R.color.white));
                            String[] to = new String[]{email};
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("text/html");
                            i.putExtra(Intent.EXTRA_EMAIL, to);
                            try {
                                startActivity(Intent.createChooser(i, getString(R.string.intafy_send_mail)));
                            } catch (android.content.ActivityNotFoundException ex) {
                                ting(getString(R.string.intafy_share_email_no_client));
                            }
                        }
                    }, 1000);
                }
            }
        });
        lnrFooterLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (mobileNo.trim().length() > 0 || phoneNo.trim().length() > 0) {
                    footerLeftText.setTextColor(getResources().getColor(R.color.blue));
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            footerLeftText.setTextColor(getResources().getColor(R.color.white));
                            showCallPopup(view);
                        }
                    }, 1000);
                }
            }
        });

    }


    private void getIntentData(){
        IN_USERID = getIntent().getStringExtra("IN_USERID")!=null?getIntent().getStringExtra("IN_USERID"):"";
    }
    private void getProfileField(){

        String connectedUserId = getSmartApplication().readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
        String networkId = getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0");
        intafyUserDataProvider.getUserDetails(IN_USERID,connectedUserId,networkId,new WebCallListener() {
            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                try{

                    if(responseCode==200){
                        try{
                            profileField= new JSONArray(data1.get(0).get("fields"));

                            JSONArray jsonArray = new JSONArray();
                            for (int i=0;i<profileField.length();i++){
                                if(profileField.getJSONObject(i).getString(CAPTION).equalsIgnoreCase("mobile number")){
                                    if(profileField.getJSONObject(i).getString(VALUE).trim().length()>0) {
                                        mobileNo="+";
                                        String[] phone = profileField.getJSONObject(i).getString(VALUE).split("-");
                                        for (int j=0;j<phone.length;j++){
                                            mobileNo +=phone[j];
                                        }
                                    }
                                }else if(profileField.getJSONObject(i).getString(CAPTION).equalsIgnoreCase("phone number")){
                                    if(profileField.getJSONObject(i).getString(VALUE).trim().length()>0) {
                                        phoneNo="+";
                                        String[] phone = profileField.getJSONObject(i).getString(VALUE).split("-");
                                        for (int j=0;j<phone.length;j++){
                                            phoneNo +=phone[j];
                                        }
                                    }
                                }
                                if(profileField.getJSONObject(i).getString(VALUE).trim().length()>0){
                                    if(profileField.getJSONObject(i).getString(TYPE).equalsIgnoreCase("phone")){
                                        JSONObject jsonObject = profileField.getJSONObject(i);
                                        String number = "+"+jsonObject.getString(VALUE);
                                        if(number.length()>2) {
                                            jsonObject.put(VALUE, number.substring(0, number.length() - 1));
                                            jsonArray.put(jsonObject);
                                        }
                                    }else{
                                        jsonArray.put(profileField.getJSONObject(i));
                                    }
                                }
                            }
                            adapter = new PageAdapter(getSupportFragmentManager(),jsonArray);
                            if(mobileNo.trim().length()>0 || phoneNo.trim().length()>0){
                                footerLeftText.setTextColor(getResources().getColor(R.color.white));
                            }else{
                                footerLeftText.setTextColor(getResources().getColor(R.color.txt_color));
                            }
                        }catch (Throwable e){
                            e.printStackTrace();
                        }
                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(0);
                        indicator.setPageColor(Color.TRANSPARENT);
                        indicator.setStrokeColor(Color.parseColor(getString(R.color.white)));
                        indicator.setStrokeWidth(convertSizeToDeviceDependent(1));
                        indicator.setRadius(convertSizeToDeviceDependent(3));
                        indicator.setFillColor(Color.parseColor(getString(R.color.white)));
                        indicator.setViewPager(viewPager, 0);
                        indicator.setSnap(true);
                    }else{
                        responseErrorMessageHandler(responseCode);
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
                pbrFieldLoad.setVisibility(View.GONE);
            }

            @Override
            public void onProgressUpdate(int progressCount) {
            }
        });
    }

    private void getProfile(){
        pbrFieldLoad.setVisibility(View.VISIBLE);
        final SeekBar seekBar =  IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
        String connectedUserId = getSmartApplication().readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
        String networkId = getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0");
        ;
        intafyUserDataProvider.getUserProfile(IN_USERID,connectedUserId,networkId,new WebCallListener() {
            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                try{
                    if(responseCode==200){
                        profileData = data1.get(0);
                        androidQuery.id(imgUserImage).progress(pbrImageLoad).image(profileData.get(USER_AVATAR),true,true,150,R.drawable.ic_launcher);
                        if(profileData.get(USER_NAME).trim().length()>0){
                            txtUserFristLastName.setText(profileData.get(USER_NAME) +" "+profileData.get(LASTNAME));
                            txtUserFristLastName.setVisibility(View.VISIBLE);
                        }
                        if(profileData.get(USER_STATUS).trim().length()>0){
                            txtUserStatus.setText(profileData.get(USER_STATUS));
                            txtUserStatus.setVisibility(View.VISIBLE);
                        }
                        if(profileData.get(EMAIL).trim().length()>0){
                            email=profileData.get(EMAIL);
                        }
                        if(profileData.get(PINCODE).trim().length()>0 && !getSmartApplication().readSharedPreferences().getString(SP_CONNECTEDUSERTYPE,"").equalsIgnoreCase("admin")){
                            txtUserPincode.setText(profileData.get(PINCODE));
                            txtUserPincode.setVisibility(View.VISIBLE);
                        }

                        if(email.trim().length()>0){
                            footerRightText.setTextColor(getResources().getColor(R.color.white));
                        }else{
                            footerRightText.setTextColor(getResources().getColor(R.color.txt_color));
                        }
                        getProfileField();
                    }else{
                        responseErrorMessageHandler(responseCode);
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onProgressUpdate(int progressCount) {
                seekBar.setProgress(progressCount);
            }
        });


    }
    private void showCallPopup(View view){

        Rect r = new Rect();
        view.getDrawingRect(r);
        final PopupWindow popup = new PopupWindow(this);
        LinearLayout viewGroup = (LinearLayout) findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.ijoomer_intafy_profile_other_call_popup, viewGroup);

        final IjoomerTextView txtCallMobile = (IjoomerTextView) layout.findViewById(R.id.txtCallMobile);
        final IjoomerTextView txtCallPhone = (IjoomerTextView) layout.findViewById(R.id.txtCallPhone);

        if(mobileNo.trim().length()>0){
            txtCallMobile.setVisibility(View.VISIBLE);
        }
        if(phoneNo.trim().length()>0){
            txtCallPhone.setVisibility(View.VISIBLE);
        }
        final IjoomerTextView txtCancel = (IjoomerTextView) layout.findViewById(R.id.txtCancel);

        txtCallMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtCallMobile.setTextColor(getResources().getColor(R.color.blue));
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileNo));
                                txtCallMobile.setTextColor(getResources().getColor(R.color.white));
                                popup.dismiss();
                                startActivity(intent);
                            }
                        }, 1000);
                    }
                });

            }
        });

        txtCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCallPhone.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNo));
                        txtCallPhone.setTextColor(getResources().getColor(R.color.white));
                        popup.dismiss();
                        startActivity(intent);
                    }
                }, 1000);
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtCancel.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        popup.dismiss();
                        txtCancel.setTextColor(getResources().getColor(R.color.white));
                    }
                }, 1000);
            }
        });

        popup.setContentView(layout);
        popup.setWidth(getDeviceWidth() - convertSizeToDeviceDependent(20));
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable(getResources()));
        popup.showAtLocation(layout,Gravity.BOTTOM, 0,r.bottom-convertSizeToDeviceDependent(25));
    }

    private class PageAdapter extends FragmentStatePagerAdapter {

        private JSONArray fieldJsonArray;
        public PageAdapter(FragmentManager fm,JSONArray jsonArray) {
            super(fm);
            fieldJsonArray =jsonArray;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int pos) {
            JSONArray data = new JSONArray();
            int startIndex=0;
            if(pos>0){
                startIndex = pos*2;
            }
            int endIndex = startIndex+2;
            if(fieldJsonArray.length()<endIndex){
                endIndex=fieldJsonArray.length();
            }
            for (int i=startIndex;i<endIndex;i++){
                try{
                    data.put(fieldJsonArray.get(i));
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
            return new IjoomerIntafyProfileOtherFragment(data);
        }

        @Override
        public int getCount() {
            return fieldJsonArray.length()>2 ? fieldJsonArray.length()%2==0?(fieldJsonArray.length()/2):(fieldJsonArray.length()/2)+1:1;
        }

    }

    /**
     * This method used to shown response message.
     * @param responseCode represented response code
     */
    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_my_profile), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }


}
