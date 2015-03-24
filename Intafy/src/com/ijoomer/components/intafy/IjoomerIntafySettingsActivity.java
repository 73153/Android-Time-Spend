package com.ijoomer.components.intafy;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.SeekBar;

import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerCheckBox;
import com.ijoomer.customviews.IjoomerRadioButton;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertMagnatic;
import com.smart.framework.CustomAlertNeutral;

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
public class IjoomerIntafySettingsActivity extends IjoomerIntafyMaster {


    private LinearLayout lnrLockApp;
    private LinearLayout lnrShowLocation;
    private LinearLayout lnrSelectLanguage;
    private ImageView imgLockAppOn;
    private ImageView imgLockAppOff;
    private ImageView imgLocationOn;
    private ImageView imgLocationOff;
    private IjoomerTextView txtSettingsLanguage;

    private IjoomerButton btnSettingsTerms;
    private IjoomerButton btnSettingsFeedback;
    private IjoomerButton btnSettingsRateApp;
    private IjoomerTextView footerLeftText;
    private IjoomerTextView footerRightText;
    private IjoomerTextView headerText;
    private LinearLayout lnrFooterRight;

    /**
     * Overrides methods
     */



    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_settings;
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

        lnrLockApp = (LinearLayout) findViewById(R.id.lnrLockApp);
        lnrSelectLanguage = (LinearLayout) findViewById(R.id.lnrSelectLanguage);
        lnrShowLocation = (LinearLayout) findViewById(R.id.lnrShowLocation);
        imgLockAppOn = (ImageView) findViewById(R.id.imgLockAppOn);
        imgLockAppOff = (ImageView) findViewById(R.id.imgLockAppOff);
        imgLocationOn = (ImageView) findViewById(R.id.imgLocationOn);
        imgLocationOff = (ImageView) findViewById(R.id.imgLocationOff);
        txtSettingsLanguage = (IjoomerTextView) findViewById(R.id.txtSettingsLanguage);
        btnSettingsTerms = (IjoomerButton) findViewById(R.id.btnSettingsTerms);
        btnSettingsFeedback = (IjoomerButton) findViewById(R.id.btnSettingsFeedback);
        btnSettingsRateApp = (IjoomerButton) findViewById(R.id.btnSettingsRateApp);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        footerLeftText = (IjoomerTextView) getFooterView().findViewById(R.id.txtFirst);
        footerRightText = (IjoomerTextView) getFooterView().findViewById(R.id.txtLast);
        lnrFooterRight = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterRight);
    }

    @Override
    public void prepareViews() {
        headerText.setText(getString(R.string.intafy_settings));
        footerRightText.setText(getString(R.string.intafy_networks));
        footerLeftText.setText(getString(R.string.intafy_settings));
        footerLeftText.setTextColor(getResources().getColor(R.color.blue));
        txtSettingsLanguage.setText(getSmartApplication().readSharedPreferences().getString(SP_APP_LANGUAGE, getString(R.string.intafy_english_language)));
        if(getSmartApplication().readSharedPreferences().getString(SP_ISAPPLOCK,"false").equals("true")){
            imgLockAppOn.setVisibility(View.VISIBLE);
            imgLockAppOff.setVisibility(View.GONE);
        }else{
            imgLockAppOn.setVisibility(View.GONE);
            imgLockAppOff.setVisibility(View.VISIBLE);
        }
        if(getSmartApplication().readSharedPreferences().getString(SP_ISLOCATIONENABLE,"").equals("true")){
            imgLocationOn.setVisibility(View.VISIBLE);
            imgLocationOff.setVisibility(View.GONE);
        }else{
            imgLocationOn.setVisibility(View.GONE);
            imgLocationOff.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setActionListeners() {
        lnrShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGPSOnDialog();
            }
        });

        lnrSelectLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectLanguagePopup();
            }
        });

        lnrLockApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockAppPopup();
            }
        });

        lnrFooterRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footerLeftText.setTextColor(getResources().getColor(R.color.white));
                footerRightText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            footerRightText.setTextColor(getResources().getColor(R.color.white));
                            loadNew(IjoomerIntafyNetworkListActivity.class, IjoomerIntafySettingsActivity.this, true);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }, 1000);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);;
        boolean enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(enabled || getSmartApplication().readSharedPreferences().getString(SP_ISLOCATIONENABLE,"false").equals("true")){
            getSmartApplication().writeSharedPreferences(SP_ISLOCATIONENABLE,"true");
            imgLocationOn.setVisibility(View.VISIBLE);
            imgLocationOff.setVisibility(View.GONE);
        }else{
            imgLocationOn.setVisibility(View.GONE);
            imgLocationOff.setVisibility(View.VISIBLE);
        }
    }

    private void showGPSOnDialog(){
        if(getSmartApplication().readSharedPreferences().getString(SP_ISLOCATIONENABLE,"false").equals("false")) {
            IjoomerUtilities.getCustomConfirmDialog(getString(R.string.intafy_location_service), getString(R.string.intafy_want_to_enable_location_gps), getString(R.string.intafy_confirm), getString(R.string.intafy_cancel), new CustomAlertMagnatic() {
                @Override
                public void PositiveMethod() {
                    getSmartApplication().writeSharedPreferences(SP_ISLOCATIONENABLE,"true");
                    try {
                        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
                        intent.putExtra("enabled", true);
                        sendBroadcast(intent);
                        imgLocationOn.setVisibility(View.VISIBLE);
                        imgLocationOff.setVisibility(View.GONE);
                    }catch (Throwable e){
                        e.printStackTrace();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                        if(!provider.contains("gps")){
                            final Intent intent1 = new Intent();
                            intent1.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                            intent1.addCategory(Intent.CATEGORY_ALTERNATIVE);
                            intent1.setData(Uri.parse("3"));
                            sendBroadcast(intent1);
                        }
                    }
                }

                @Override
                public void NegativeMethod() {

                }
            });
        }else{
            IjoomerUtilities.getCustomConfirmDialog(getString(R.string.intafy_location_service), getString(R.string.intafy_want_to_disable_location_gps), getString(R.string.intafy_confirm), getString(R.string.intafy_cancel), new CustomAlertMagnatic() {
                @Override
                public void PositiveMethod() {
                    getSmartApplication().writeSharedPreferences(SP_ISLOCATIONENABLE,"false");

                    try{
                        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
                        intent.putExtra("enabled", false);
                        sendBroadcast(intent);
                        imgLocationOn.setVisibility(View.GONE);
                        imgLocationOff.setVisibility(View.VISIBLE);
                    }catch (Throwable e){
                        e.printStackTrace();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                        if(!provider.contains("gps")){
                            final Intent intent1 = new Intent();
                            intent1.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                            intent1.addCategory(Intent.CATEGORY_ALTERNATIVE);
                            intent1.setData(Uri.parse("3"));
                            sendBroadcast(intent1);
                        }
                    }
                }

                @Override
                public void NegativeMethod() {

                }
            });
        }
    }

    private void showLockAppPopup(){
        int popupWidth = getDeviceWidth() - convertSizeToDeviceDependent(20);
        int popupHeight = getDeviceHeight() - convertSizeToDeviceDependent(60);
        final PopupWindow popup = new PopupWindow(this);
        LinearLayout viewGroup = (LinearLayout) findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.ijoomer_intafy_settings_lockapp_popup, viewGroup);

        final IjoomerTextView txtLockAppDigit = (IjoomerTextView) layout.findViewById(R.id.txtLockAppDigit);
        final ImageView rdbLockAppOn = (ImageView) layout.findViewById(R.id.rdbLockAppOn);
        final ImageView rdbLockAppOff = (ImageView) layout.findViewById(R.id.rdbLockAppOff);
        final ImageView imgOne = (ImageView) layout.findViewById(R.id.imgOne);
        final ImageView imgTwo = (ImageView) layout.findViewById(R.id.imgTwo);
        final ImageView imgThree = (ImageView) layout.findViewById(R.id.imgThree);
        final ImageView imgFour = (ImageView) layout.findViewById(R.id.imgFour);
        final ImageView imgFive = (ImageView) layout.findViewById(R.id.imgFive);
        final ImageView imgSix = (ImageView) layout.findViewById(R.id.imgSix);
        final ImageView imgSeven = (ImageView) layout.findViewById(R.id.imgSeven);
        final ImageView imgEight = (ImageView) layout.findViewById(R.id.imgEight);
        final ImageView imgNine = (ImageView) layout.findViewById(R.id.imgNine);
        final ImageView imgZero = (ImageView) layout.findViewById(R.id.imgZero);
        final ImageView imgBack = (ImageView) layout.findViewById(R.id.imgBack);
        final ImageView imgSave = (ImageView) layout.findViewById(R.id.imgSave);

        if(getSmartApplication().readSharedPreferences().getString(SP_ISAPPLOCK, "false").equals("true")){
            txtLockAppDigit.setText(getSmartApplication().readSharedPreferences().getString(SP_APPLOCKPASSWORD, "0000"));
            rdbLockAppOff.setVisibility(View.GONE);
            rdbLockAppOn.setVisibility(View.VISIBLE);
        }else{
            rdbLockAppOff.setVisibility(View.VISIBLE);
            rdbLockAppOn.setVisibility(View.GONE);
            txtLockAppDigit.setHint(getSmartApplication().readSharedPreferences().getString(SP_APPLOCKPASSWORD, "0000"));
        }

        rdbLockAppOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdbLockAppOff.setVisibility(View.GONE);
                rdbLockAppOn.setVisibility(View.VISIBLE);
            }
        });

        rdbLockAppOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSmartApplication().writeSharedPreferences(SP_ISAPPLOCK, "false");
                getSmartApplication().writeSharedPreferences(SP_APPLOCKPASSWORD, "0000");
                loadNew(IjoomerIntafySettingsActivity.class, IjoomerIntafySettingsActivity.this, true);
                popup.dismiss();
            }
        });

        imgOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgOne.setImageResource(R.drawable.one_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgOne.setImageResource(R.drawable.one_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"1");
                        }
                    }
                }, 1000);
            }
        });

        imgTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgTwo.setImageResource(R.drawable.two_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgTwo.setImageResource(R.drawable.two_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"2");
                        }
                    }
                }, 1000);
            }
        });
        imgThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgThree.setImageResource(R.drawable.three_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgThree.setImageResource(R.drawable.three_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"3");
                        }
                    }
                }, 1000);
            }
        });
        imgFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFour.setImageResource(R.drawable.four_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgFour.setImageResource(R.drawable.four_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"4");
                        }
                    }
                }, 1000);
            }
        });
        imgFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFive.setImageResource(R.drawable.five_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgFive.setImageResource(R.drawable.five_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"5");
                        }
                    }
                }, 1000);
            }
        });
        imgSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgSix.setImageResource(R.drawable.six_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgSix.setImageResource(R.drawable.six_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"6");
                        }
                    }
                }, 1000);
            }
        });
        imgSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgSeven.setImageResource(R.drawable.seven_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgSeven.setImageResource(R.drawable.seven_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"7");
                        }
                    }
                }, 1000);

            }
        });
        imgEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgEight.setImageResource(R.drawable.eight_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgEight.setImageResource(R.drawable.eight_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"8");
                        }
                    }
                }, 1000);
            }
        });
        imgNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgNine.setImageResource(R.drawable.nine_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgNine.setImageResource(R.drawable.nine_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"9");
                        }
                    }
                }, 1000);
            }
        });
        imgZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgZero.setImageResource(R.drawable.zero_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgZero.setImageResource(R.drawable.zero_btn);
                        if(txtLockAppDigit.getText().length()<4){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString()+"0");
                        }
                    }
                }, 1000);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgBack.setImageResource(R.drawable.back_btn_selected);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgBack.setImageResource(R.drawable.back_btn);
                        if(txtLockAppDigit.getText().length()>0){
                            txtLockAppDigit.setText(txtLockAppDigit.getText().toString().subSequence(0,txtLockAppDigit.getText().toString().length()-1));
                        }
                    }
                }, 1000);
            }
        });

        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgSave.setImageResource(getIcon(SAVEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgSave.setImageResource(getIcon(SAVEICON,false));
                        if(rdbLockAppOn.getVisibility()==View.VISIBLE){
                            if(txtLockAppDigit.getText().length()<4){
                                IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_lock_app).substring(0,getString(R.string.intafy_lock_app).length()-1),getString(R.string.intafy_lock_app_validation),getString(R.string.intafy_ok),0,new CustomAlertNeutral() {
                                    @Override
                                    public void NeutralMethod() {

                                    }
                                });
                            }else{
                                getSmartApplication().writeSharedPreferences(SP_ISAPPLOCK,"true");
                                getSmartApplication().writeSharedPreferences(SP_APPLOCKPASSWORD,txtLockAppDigit.getText().toString().trim());
                                loadNew(IjoomerIntafySettingsActivity.class,IjoomerIntafySettingsActivity.this,true);
                                popup.dismiss();
                            }
                        }else {
                            getSmartApplication().writeSharedPreferences(SP_ISAPPLOCK, "false");
                            getSmartApplication().writeSharedPreferences(SP_APPLOCKPASSWORD,"0000");
                            loadNew(IjoomerIntafySettingsActivity.class,IjoomerIntafySettingsActivity.this,true);
                            popup.dismiss();
                        }
                    }
                }, 1000);
            }
        });

        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable(getResources()));
        popup.setOutsideTouchable(true);
        popup.showAtLocation(layout, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
    }

    private void showSelectLanguagePopup(){
        int popupWidth = getDeviceWidth() - convertSizeToDeviceDependent(20);
        int popupHeight = getDeviceHeight() - convertSizeToDeviceDependent(60);
        final PopupWindow popup = new PopupWindow(this);
        LinearLayout viewGroup = (LinearLayout) findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.ijoomer_intafy_settings_language_popup, viewGroup);

        final ImageView imgEnglishLanguageOn = (ImageView) layout.findViewById(R.id.imgEnglishLanguageOn);
        final ImageView imgEnglishLanguageOff = (ImageView) layout.findViewById(R.id.imgEnglishLanguageOff);
        final ImageView imgArabicLanguageOn = (ImageView) layout.findViewById(R.id.imgArabicLanguageOn);
        final ImageView imgArabicLanguageOff = (ImageView) layout.findViewById(R.id.imgArabicLanguageOff);
        final ImageView imgGermanLanguageOn = (ImageView) layout.findViewById(R.id.imgGermanLanguageOn);
        final ImageView imgGermanLanguageOff = (ImageView) layout.findViewById(R.id.imgGermanLanguageOff);
        final ImageView imgKozukaLanguageOn = (ImageView) layout.findViewById(R.id.imgKozukaLanguageOn);
        final ImageView imgKozukaLanguageOff = (ImageView) layout.findViewById(R.id.imgKozukaLanguageOff);
        final ImageView imgFrenchLanguageOn = (ImageView) layout.findViewById(R.id.imgFrenchLanguageOn);
        final ImageView imgFrenchLanguageOff = (ImageView) layout.findViewById(R.id.imgFrenchLanguageOff);
        final ImageView imgPortuguesLanguageOn = (ImageView) layout.findViewById(R.id.imgPortuguesLanguageOn);
        final ImageView imgPortuguesLanguageOff = (ImageView) layout.findViewById(R.id.imgPortuguesLanguageOff);
        final ImageView imgSpanishLanguageOn = (ImageView) layout.findViewById(R.id.imgSpanishLanguageOn);
        final ImageView imgSpanishLanguageOff = (ImageView) layout.findViewById(R.id.imgSpanishLanguageOff);
        final ImageView imgNederlandsLanguageOn = (ImageView) layout.findViewById(R.id.imgNederlandsLanguageOn);
        final ImageView imgNederlandsLanguageOff = (ImageView) layout.findViewById(R.id.imgNederlandsLanguageOff);
        final ImageView imgItalianLanguageOn = (ImageView) layout.findViewById(R.id.imgItalianLanguageOn);
        final ImageView imgItalianLanguageOff = (ImageView) layout.findViewById(R.id.imgItalianLanguageOff);
        final ImageView imgSwadishLanguageOn = (ImageView) layout.findViewById(R.id.imgSwadishLanguageOn);
        final ImageView imgSwadishLanguageOff = (ImageView) layout.findViewById(R.id.imgSwadishLanguageOff);
        final ImageView imgRussianLanguageOn = (ImageView) layout.findViewById(R.id.imgRussianLanguageOn);
        final ImageView imgRussianLanguageOff = (ImageView) layout.findViewById(R.id.imgRussianLanguageOff);
        final ImageView imgNorwegianLanguageOn = (ImageView) layout.findViewById(R.id.imgNorwegianLanguageOn);
        final ImageView imgNorwegianLanguageOff = (ImageView) layout.findViewById(R.id.imgNorwegianLanguageOff);
        final ImageView imgSave = (ImageView) layout.findViewById(R.id.imgSave);

        if(getSmartApplication().readSharedPreferences().getString(SP_APP_LANGUAGE,getString(R.string.intafy_english_language)).equals(getString(R.string.intafy_english_language))){
            imgEnglishLanguageOn.setVisibility(View.VISIBLE);
            imgEnglishLanguageOff.setVisibility(View.GONE);
        }else if(getSmartApplication().readSharedPreferences().getString(SP_APP_LANGUAGE,getString(R.string.intafy_english_language)).equals(getString(R.string.intafy_arabic_language))){
            imgArabicLanguageOn.setVisibility(View.VISIBLE);
            imgArabicLanguageOff.setVisibility(View.GONE);
        }else if(getSmartApplication().readSharedPreferences().getString(SP_APP_LANGUAGE,getString(R.string.intafy_english_language)).equals(getString(R.string.intafy_german_language))){
            imgGermanLanguageOn.setVisibility(View.VISIBLE);
            imgGermanLanguageOff.setVisibility(View.GONE);
        }else if(getSmartApplication().readSharedPreferences().getString(SP_APP_LANGUAGE,getString(R.string.intafy_english_language)).equals(getString(R.string.intafy_kozuka_language))){
            imgKozukaLanguageOn.setVisibility(View.VISIBLE);
            imgKozukaLanguageOff.setVisibility(View.GONE);
        }else if(getSmartApplication().readSharedPreferences().getString(SP_APP_LANGUAGE,getString(R.string.intafy_english_language)).equals(getString(R.string.intafy_french_language))){
            imgFrenchLanguageOn.setVisibility(View.VISIBLE);
            imgFrenchLanguageOff.setVisibility(View.GONE);
        }else if(getSmartApplication().readSharedPreferences().getString(SP_APP_LANGUAGE,getString(R.string.intafy_english_language)).equals(getString(R.string.intafy_portugues_language))){
            imgPortuguesLanguageOn.setVisibility(View.VISIBLE);
            imgPortuguesLanguageOff.setVisibility(View.GONE);
        }else if(getSmartApplication().readSharedPreferences().getString(SP_APP_LANGUAGE,getString(R.string.intafy_english_language)).equals(getString(R.string.intafy_spanish_language))){
            imgSpanishLanguageOn.setVisibility(View.VISIBLE);
            imgSpanishLanguageOff.setVisibility(View.GONE);
        }else if(getSmartApplication().readSharedPreferences().getString(SP_APP_LANGUAGE,getString(R.string.intafy_english_language)).equals(getString(R.string.intafy_nederlands_language))){
            imgNederlandsLanguageOn.setVisibility(View.VISIBLE);
            imgNederlandsLanguageOff.setVisibility(View.GONE);
        }else if(getSmartApplication().readSharedPreferences().getString(SP_APP_LANGUAGE,getString(R.string.intafy_english_language)).equals(getString(R.string.intafy_italian_language))){
            imgItalianLanguageOn.setVisibility(View.VISIBLE);
            imgItalianLanguageOff.setVisibility(View.GONE);
        }else if(getSmartApplication().readSharedPreferences().getString(SP_APP_LANGUAGE,getString(R.string.intafy_english_language)).equals(getString(R.string.intafy_swedish_language))){
            imgSwadishLanguageOn.setVisibility(View.VISIBLE);
            imgSwadishLanguageOff.setVisibility(View.GONE);
        }else if(getSmartApplication().readSharedPreferences().getString(SP_APP_LANGUAGE,getString(R.string.intafy_english_language)).equals(getString(R.string.intafy_russian_language))){
            imgRussianLanguageOn.setVisibility(View.VISIBLE);
            imgRussianLanguageOff.setVisibility(View.GONE);
        }else if(getSmartApplication().readSharedPreferences().getString(SP_APP_LANGUAGE,getString(R.string.intafy_english_language)).equals(getString(R.string.intafy_norwegian_language))){
            imgNorwegianLanguageOn.setVisibility(View.VISIBLE);
            imgNorwegianLanguageOff.setVisibility(View.GONE);
        }

        imgEnglishLanguageOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEnglishLanguageOn.setVisibility(View.VISIBLE);
                imgEnglishLanguageOff.setVisibility(View.GONE);
                imgArabicLanguageOn.setVisibility(View.GONE);
                imgArabicLanguageOff.setVisibility(View.VISIBLE);
                imgGermanLanguageOn.setVisibility(View.GONE);
                imgGermanLanguageOff.setVisibility(View.VISIBLE);
                imgKozukaLanguageOn.setVisibility(View.GONE);
                imgKozukaLanguageOff.setVisibility(View.VISIBLE);
                imgFrenchLanguageOn.setVisibility(View.GONE);
                imgFrenchLanguageOff.setVisibility(View.VISIBLE);
                imgPortuguesLanguageOn.setVisibility(View.GONE);
                imgPortuguesLanguageOff.setVisibility(View.VISIBLE);
                imgSpanishLanguageOn.setVisibility(View.GONE);
                imgSpanishLanguageOff.setVisibility(View.VISIBLE);
                imgNederlandsLanguageOn.setVisibility(View.GONE);
                imgNederlandsLanguageOff.setVisibility(View.VISIBLE);
                imgItalianLanguageOn.setVisibility(View.GONE);
                imgItalianLanguageOff.setVisibility(View.VISIBLE);
                imgSwadishLanguageOn.setVisibility(View.GONE);
                imgSwadishLanguageOff.setVisibility(View.VISIBLE);
                imgRussianLanguageOn.setVisibility(View.GONE);
                imgRussianLanguageOff.setVisibility(View.VISIBLE);
                imgNorwegianLanguageOn.setVisibility(View.GONE);
                imgNorwegianLanguageOff.setVisibility(View.VISIBLE);
            }
        });
        imgArabicLanguageOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgArabicLanguageOn.setVisibility(View.VISIBLE);
                imgArabicLanguageOff.setVisibility(View.GONE);
                imgEnglishLanguageOn.setVisibility(View.GONE);
                imgEnglishLanguageOff.setVisibility(View.VISIBLE);
                imgGermanLanguageOn.setVisibility(View.GONE);
                imgGermanLanguageOff.setVisibility(View.VISIBLE);
                imgKozukaLanguageOn.setVisibility(View.GONE);
                imgKozukaLanguageOff.setVisibility(View.VISIBLE);
                imgFrenchLanguageOn.setVisibility(View.GONE);
                imgFrenchLanguageOff.setVisibility(View.VISIBLE);
                imgPortuguesLanguageOn.setVisibility(View.GONE);
                imgPortuguesLanguageOff.setVisibility(View.VISIBLE);
                imgSpanishLanguageOn.setVisibility(View.GONE);
                imgSpanishLanguageOff.setVisibility(View.VISIBLE);
                imgNederlandsLanguageOn.setVisibility(View.GONE);
                imgNederlandsLanguageOff.setVisibility(View.VISIBLE);
                imgItalianLanguageOn.setVisibility(View.GONE);
                imgItalianLanguageOff.setVisibility(View.VISIBLE);
                imgSwadishLanguageOn.setVisibility(View.GONE);
                imgSwadishLanguageOff.setVisibility(View.VISIBLE);
                imgRussianLanguageOn.setVisibility(View.GONE);
                imgRussianLanguageOff.setVisibility(View.VISIBLE);
                imgNorwegianLanguageOn.setVisibility(View.GONE);
                imgNorwegianLanguageOff.setVisibility(View.VISIBLE);
            }
        });
        imgGermanLanguageOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgGermanLanguageOn.setVisibility(View.VISIBLE);
                imgGermanLanguageOff.setVisibility(View.GONE);
                imgEnglishLanguageOn.setVisibility(View.GONE);
                imgEnglishLanguageOff.setVisibility(View.VISIBLE);
                imgArabicLanguageOn.setVisibility(View.GONE);
                imgArabicLanguageOff.setVisibility(View.VISIBLE);
                imgKozukaLanguageOn.setVisibility(View.GONE);
                imgKozukaLanguageOff.setVisibility(View.VISIBLE);
                imgFrenchLanguageOn.setVisibility(View.GONE);
                imgFrenchLanguageOff.setVisibility(View.VISIBLE);
                imgPortuguesLanguageOn.setVisibility(View.GONE);
                imgPortuguesLanguageOff.setVisibility(View.VISIBLE);
                imgSpanishLanguageOn.setVisibility(View.GONE);
                imgSpanishLanguageOff.setVisibility(View.VISIBLE);
                imgNederlandsLanguageOn.setVisibility(View.GONE);
                imgNederlandsLanguageOff.setVisibility(View.VISIBLE);
                imgItalianLanguageOn.setVisibility(View.GONE);
                imgItalianLanguageOff.setVisibility(View.VISIBLE);
                imgSwadishLanguageOn.setVisibility(View.GONE);
                imgSwadishLanguageOff.setVisibility(View.VISIBLE);
                imgRussianLanguageOn.setVisibility(View.GONE);
                imgRussianLanguageOff.setVisibility(View.VISIBLE);
                imgNorwegianLanguageOn.setVisibility(View.GONE);
                imgNorwegianLanguageOff.setVisibility(View.VISIBLE);
            }
        });

        imgKozukaLanguageOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgKozukaLanguageOn.setVisibility(View.VISIBLE);
                imgKozukaLanguageOff.setVisibility(View.GONE);
                imgEnglishLanguageOn.setVisibility(View.GONE);
                imgEnglishLanguageOff.setVisibility(View.VISIBLE);
                imgArabicLanguageOn.setVisibility(View.GONE);
                imgArabicLanguageOff.setVisibility(View.VISIBLE);
                imgGermanLanguageOn.setVisibility(View.GONE);
                imgGermanLanguageOff.setVisibility(View.VISIBLE);
                imgFrenchLanguageOn.setVisibility(View.GONE);
                imgFrenchLanguageOff.setVisibility(View.VISIBLE);
                imgPortuguesLanguageOn.setVisibility(View.GONE);
                imgPortuguesLanguageOff.setVisibility(View.VISIBLE);
                imgSpanishLanguageOn.setVisibility(View.GONE);
                imgSpanishLanguageOff.setVisibility(View.VISIBLE);
                imgNederlandsLanguageOn.setVisibility(View.GONE);
                imgNederlandsLanguageOff.setVisibility(View.VISIBLE);
                imgItalianLanguageOn.setVisibility(View.GONE);
                imgItalianLanguageOff.setVisibility(View.VISIBLE);
                imgSwadishLanguageOn.setVisibility(View.GONE);
                imgSwadishLanguageOff.setVisibility(View.VISIBLE);
                imgRussianLanguageOn.setVisibility(View.GONE);
                imgRussianLanguageOff.setVisibility(View.VISIBLE);
                imgNorwegianLanguageOn.setVisibility(View.GONE);
                imgNorwegianLanguageOff.setVisibility(View.VISIBLE);
            }
        });
        imgFrenchLanguageOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgFrenchLanguageOn.setVisibility(View.VISIBLE);
                imgFrenchLanguageOff.setVisibility(View.GONE);
                imgEnglishLanguageOn.setVisibility(View.GONE);
                imgEnglishLanguageOff.setVisibility(View.VISIBLE);
                imgArabicLanguageOn.setVisibility(View.GONE);
                imgArabicLanguageOff.setVisibility(View.VISIBLE);
                imgGermanLanguageOn.setVisibility(View.GONE);
                imgGermanLanguageOff.setVisibility(View.VISIBLE);
                imgKozukaLanguageOn.setVisibility(View.GONE);
                imgKozukaLanguageOff.setVisibility(View.VISIBLE);
                imgPortuguesLanguageOn.setVisibility(View.GONE);
                imgPortuguesLanguageOff.setVisibility(View.VISIBLE);
                imgSpanishLanguageOn.setVisibility(View.GONE);
                imgSpanishLanguageOff.setVisibility(View.VISIBLE);
                imgNederlandsLanguageOn.setVisibility(View.GONE);
                imgNederlandsLanguageOff.setVisibility(View.VISIBLE);
                imgItalianLanguageOn.setVisibility(View.GONE);
                imgItalianLanguageOff.setVisibility(View.VISIBLE);
                imgSwadishLanguageOn.setVisibility(View.GONE);
                imgSwadishLanguageOff.setVisibility(View.VISIBLE);
                imgRussianLanguageOn.setVisibility(View.GONE);
                imgRussianLanguageOff.setVisibility(View.VISIBLE);
                imgNorwegianLanguageOn.setVisibility(View.GONE);
                imgNorwegianLanguageOff.setVisibility(View.VISIBLE);
            }
        });
        imgPortuguesLanguageOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPortuguesLanguageOn.setVisibility(View.VISIBLE);
                imgPortuguesLanguageOff.setVisibility(View.GONE);
                imgEnglishLanguageOn.setVisibility(View.GONE);
                imgEnglishLanguageOff.setVisibility(View.VISIBLE);
                imgArabicLanguageOn.setVisibility(View.GONE);
                imgArabicLanguageOff.setVisibility(View.VISIBLE);
                imgGermanLanguageOn.setVisibility(View.GONE);
                imgGermanLanguageOff.setVisibility(View.VISIBLE);
                imgKozukaLanguageOn.setVisibility(View.GONE);
                imgKozukaLanguageOff.setVisibility(View.VISIBLE);
                imgFrenchLanguageOn.setVisibility(View.GONE);
                imgFrenchLanguageOff.setVisibility(View.VISIBLE);
                imgSpanishLanguageOn.setVisibility(View.GONE);
                imgSpanishLanguageOff.setVisibility(View.VISIBLE);
                imgNederlandsLanguageOn.setVisibility(View.GONE);
                imgNederlandsLanguageOff.setVisibility(View.VISIBLE);
                imgItalianLanguageOn.setVisibility(View.GONE);
                imgItalianLanguageOff.setVisibility(View.VISIBLE);
                imgSwadishLanguageOn.setVisibility(View.GONE);
                imgSwadishLanguageOff.setVisibility(View.VISIBLE);
                imgRussianLanguageOn.setVisibility(View.GONE);
                imgRussianLanguageOff.setVisibility(View.VISIBLE);
                imgNorwegianLanguageOn.setVisibility(View.GONE);
                imgNorwegianLanguageOff.setVisibility(View.VISIBLE);
            }
        });
        imgSpanishLanguageOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSpanishLanguageOn.setVisibility(View.VISIBLE);
                imgSpanishLanguageOff.setVisibility(View.GONE);
                imgEnglishLanguageOn.setVisibility(View.GONE);
                imgEnglishLanguageOff.setVisibility(View.VISIBLE);
                imgArabicLanguageOn.setVisibility(View.GONE);
                imgArabicLanguageOff.setVisibility(View.VISIBLE);
                imgGermanLanguageOn.setVisibility(View.GONE);
                imgGermanLanguageOff.setVisibility(View.VISIBLE);
                imgKozukaLanguageOn.setVisibility(View.GONE);
                imgKozukaLanguageOff.setVisibility(View.VISIBLE);
                imgFrenchLanguageOn.setVisibility(View.GONE);
                imgFrenchLanguageOff.setVisibility(View.VISIBLE);
                imgPortuguesLanguageOn.setVisibility(View.GONE);
                imgPortuguesLanguageOff.setVisibility(View.VISIBLE);
                imgNederlandsLanguageOn.setVisibility(View.GONE);
                imgNederlandsLanguageOff.setVisibility(View.VISIBLE);
                imgItalianLanguageOn.setVisibility(View.GONE);
                imgItalianLanguageOff.setVisibility(View.VISIBLE);
                imgSwadishLanguageOn.setVisibility(View.GONE);
                imgSwadishLanguageOff.setVisibility(View.VISIBLE);
                imgRussianLanguageOn.setVisibility(View.GONE);
                imgRussianLanguageOff.setVisibility(View.VISIBLE);
                imgNorwegianLanguageOn.setVisibility(View.GONE);
                imgNorwegianLanguageOff.setVisibility(View.VISIBLE);
            }
        });
        imgNederlandsLanguageOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgNederlandsLanguageOn.setVisibility(View.VISIBLE);
                imgNederlandsLanguageOff.setVisibility(View.GONE);
                imgEnglishLanguageOn.setVisibility(View.GONE);
                imgEnglishLanguageOff.setVisibility(View.VISIBLE);
                imgArabicLanguageOn.setVisibility(View.GONE);
                imgArabicLanguageOff.setVisibility(View.VISIBLE);
                imgGermanLanguageOn.setVisibility(View.GONE);
                imgGermanLanguageOff.setVisibility(View.VISIBLE);
                imgKozukaLanguageOn.setVisibility(View.GONE);
                imgKozukaLanguageOff.setVisibility(View.VISIBLE);
                imgFrenchLanguageOn.setVisibility(View.GONE);
                imgFrenchLanguageOff.setVisibility(View.VISIBLE);
                imgPortuguesLanguageOn.setVisibility(View.GONE);
                imgPortuguesLanguageOff.setVisibility(View.VISIBLE);
                imgSpanishLanguageOn.setVisibility(View.GONE);
                imgSpanishLanguageOff.setVisibility(View.VISIBLE);
                imgItalianLanguageOn.setVisibility(View.GONE);
                imgItalianLanguageOff.setVisibility(View.VISIBLE);
                imgSwadishLanguageOn.setVisibility(View.GONE);
                imgSwadishLanguageOff.setVisibility(View.VISIBLE);
                imgRussianLanguageOn.setVisibility(View.GONE);
                imgRussianLanguageOff.setVisibility(View.VISIBLE);
                imgNorwegianLanguageOn.setVisibility(View.GONE);
                imgNorwegianLanguageOff.setVisibility(View.VISIBLE);
            }
        });
        imgItalianLanguageOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgItalianLanguageOn.setVisibility(View.VISIBLE);
                imgItalianLanguageOff.setVisibility(View.GONE);
                imgEnglishLanguageOn.setVisibility(View.GONE);
                imgEnglishLanguageOff.setVisibility(View.VISIBLE);
                imgArabicLanguageOn.setVisibility(View.GONE);
                imgArabicLanguageOff.setVisibility(View.VISIBLE);
                imgGermanLanguageOn.setVisibility(View.GONE);
                imgGermanLanguageOff.setVisibility(View.VISIBLE);
                imgKozukaLanguageOn.setVisibility(View.GONE);
                imgKozukaLanguageOff.setVisibility(View.VISIBLE);
                imgFrenchLanguageOn.setVisibility(View.GONE);
                imgFrenchLanguageOff.setVisibility(View.VISIBLE);
                imgPortuguesLanguageOn.setVisibility(View.GONE);
                imgPortuguesLanguageOff.setVisibility(View.VISIBLE);
                imgSpanishLanguageOn.setVisibility(View.GONE);
                imgSpanishLanguageOff.setVisibility(View.VISIBLE);
                imgNederlandsLanguageOn.setVisibility(View.GONE);
                imgNederlandsLanguageOff.setVisibility(View.VISIBLE);
                imgSwadishLanguageOn.setVisibility(View.GONE);
                imgSwadishLanguageOff.setVisibility(View.VISIBLE);
                imgRussianLanguageOn.setVisibility(View.GONE);
                imgRussianLanguageOff.setVisibility(View.VISIBLE);
                imgNorwegianLanguageOn.setVisibility(View.GONE);
                imgNorwegianLanguageOff.setVisibility(View.VISIBLE);
            }
        });
        imgSwadishLanguageOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSwadishLanguageOn.setVisibility(View.VISIBLE);
                imgSwadishLanguageOff.setVisibility(View.GONE);
                imgEnglishLanguageOn.setVisibility(View.GONE);
                imgEnglishLanguageOff.setVisibility(View.VISIBLE);
                imgArabicLanguageOn.setVisibility(View.GONE);
                imgArabicLanguageOff.setVisibility(View.VISIBLE);
                imgGermanLanguageOn.setVisibility(View.GONE);
                imgGermanLanguageOff.setVisibility(View.VISIBLE);
                imgKozukaLanguageOn.setVisibility(View.GONE);
                imgKozukaLanguageOff.setVisibility(View.VISIBLE);
                imgFrenchLanguageOn.setVisibility(View.GONE);
                imgFrenchLanguageOff.setVisibility(View.VISIBLE);
                imgPortuguesLanguageOn.setVisibility(View.GONE);
                imgPortuguesLanguageOff.setVisibility(View.VISIBLE);
                imgSpanishLanguageOn.setVisibility(View.GONE);
                imgSpanishLanguageOff.setVisibility(View.VISIBLE);
                imgNederlandsLanguageOn.setVisibility(View.GONE);
                imgNederlandsLanguageOff.setVisibility(View.VISIBLE);
                imgItalianLanguageOn.setVisibility(View.GONE);
                imgItalianLanguageOff.setVisibility(View.VISIBLE);
                imgRussianLanguageOn.setVisibility(View.GONE);
                imgRussianLanguageOff.setVisibility(View.VISIBLE);
                imgNorwegianLanguageOn.setVisibility(View.GONE);
                imgNorwegianLanguageOff.setVisibility(View.VISIBLE);
            }
        });

        imgRussianLanguageOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgRussianLanguageOn.setVisibility(View.VISIBLE);
                imgRussianLanguageOff.setVisibility(View.GONE);
                imgEnglishLanguageOn.setVisibility(View.GONE);
                imgEnglishLanguageOff.setVisibility(View.VISIBLE);
                imgArabicLanguageOn.setVisibility(View.GONE);
                imgArabicLanguageOff.setVisibility(View.VISIBLE);
                imgGermanLanguageOn.setVisibility(View.GONE);
                imgGermanLanguageOff.setVisibility(View.VISIBLE);
                imgKozukaLanguageOn.setVisibility(View.GONE);
                imgKozukaLanguageOff.setVisibility(View.VISIBLE);
                imgFrenchLanguageOn.setVisibility(View.GONE);
                imgFrenchLanguageOff.setVisibility(View.VISIBLE);
                imgPortuguesLanguageOn.setVisibility(View.GONE);
                imgPortuguesLanguageOff.setVisibility(View.VISIBLE);
                imgSpanishLanguageOn.setVisibility(View.GONE);
                imgSpanishLanguageOff.setVisibility(View.VISIBLE);
                imgNederlandsLanguageOn.setVisibility(View.GONE);
                imgNederlandsLanguageOff.setVisibility(View.VISIBLE);
                imgItalianLanguageOn.setVisibility(View.GONE);
                imgItalianLanguageOff.setVisibility(View.VISIBLE);
                imgSwadishLanguageOn.setVisibility(View.GONE);
                imgSwadishLanguageOff.setVisibility(View.VISIBLE);
                imgNorwegianLanguageOn.setVisibility(View.GONE);
                imgNorwegianLanguageOff.setVisibility(View.VISIBLE);
            }
        });

        imgNorwegianLanguageOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgNorwegianLanguageOn.setVisibility(View.VISIBLE);
                imgNorwegianLanguageOff.setVisibility(View.GONE);
                imgEnglishLanguageOn.setVisibility(View.GONE);
                imgEnglishLanguageOff.setVisibility(View.VISIBLE);
                imgArabicLanguageOn.setVisibility(View.GONE);
                imgArabicLanguageOff.setVisibility(View.VISIBLE);
                imgGermanLanguageOn.setVisibility(View.GONE);
                imgGermanLanguageOff.setVisibility(View.VISIBLE);
                imgKozukaLanguageOn.setVisibility(View.GONE);
                imgKozukaLanguageOff.setVisibility(View.VISIBLE);
                imgFrenchLanguageOn.setVisibility(View.GONE);
                imgFrenchLanguageOff.setVisibility(View.VISIBLE);
                imgPortuguesLanguageOn.setVisibility(View.GONE);
                imgPortuguesLanguageOff.setVisibility(View.VISIBLE);
                imgSpanishLanguageOn.setVisibility(View.GONE);
                imgSpanishLanguageOff.setVisibility(View.VISIBLE);
                imgNederlandsLanguageOn.setVisibility(View.GONE);
                imgNederlandsLanguageOff.setVisibility(View.VISIBLE);
                imgItalianLanguageOn.setVisibility(View.GONE);
                imgItalianLanguageOff.setVisibility(View.VISIBLE);
                imgSwadishLanguageOn.setVisibility(View.GONE);
                imgSwadishLanguageOff.setVisibility(View.VISIBLE);
                imgRussianLanguageOn.setVisibility(View.GONE);
                imgRussianLanguageOff.setVisibility(View.VISIBLE);
            }
        });

        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                imgSave.setImageResource(getIcon(SAVEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgSave.setImageResource(getIcon(SAVEICON,false));
                        if(imgEnglishLanguageOn.getVisibility()==View.VISIBLE){
                            getSmartApplication().writeSharedPreferences(SP_APP_LANGUAGE,getString(R.string.intafy_english_language));
                        }else if(imgArabicLanguageOff.getVisibility()==View.VISIBLE){
                            getSmartApplication().writeSharedPreferences(SP_APP_LANGUAGE,getString(R.string.intafy_arabic_language));
                        }else if(imgSpanishLanguageOn.getVisibility()==View.VISIBLE){
                            getSmartApplication().writeSharedPreferences(SP_APP_LANGUAGE,getString(R.string.intafy_spanish_language));
                        }else if(imgKozukaLanguageOn.getVisibility()==View.VISIBLE){
                            getSmartApplication().writeSharedPreferences(SP_APP_LANGUAGE,getString(R.string.intafy_kozuka_language));
                        }else if(imgPortuguesLanguageOn.getVisibility()==View.VISIBLE){
                            getSmartApplication().writeSharedPreferences(SP_APP_LANGUAGE,getString(R.string.intafy_portugues_language));
                        }else if(imgGermanLanguageOn.getVisibility()==View.VISIBLE){
                            getSmartApplication().writeSharedPreferences(SP_APP_LANGUAGE,getString(R.string.intafy_spanish_language));
                        }else if(imgNederlandsLanguageOn.getVisibility()==View.VISIBLE){
                            getSmartApplication().writeSharedPreferences(SP_APP_LANGUAGE,getString(R.string.intafy_nederlands_language));
                        }else if(imgItalianLanguageOn.getVisibility()==View.VISIBLE){
                            getSmartApplication().writeSharedPreferences(SP_APP_LANGUAGE,getString(R.string.intafy_italian_language));
                        }else if(imgSwadishLanguageOn.getVisibility()==View.VISIBLE){
                            getSmartApplication().writeSharedPreferences(SP_APP_LANGUAGE,getString(R.string.intafy_swedish_language));
                        }else if(imgRussianLanguageOn.getVisibility()==View.VISIBLE){
                            getSmartApplication().writeSharedPreferences(SP_APP_LANGUAGE,getString(R.string.intafy_russian_language));
                        }else if(imgNorwegianLanguageOn.getVisibility()==View.VISIBLE){
                            getSmartApplication().writeSharedPreferences(SP_APP_LANGUAGE,getString(R.string.intafy_norwegian_language));
                        }else if(imgFrenchLanguageOn.getVisibility()==View.VISIBLE){
                            getSmartApplication().writeSharedPreferences(SP_APP_LANGUAGE,getString(R.string.intafy_french_language));
                        }

                        loadNew(IjoomerIntafySettingsActivity.class,IjoomerIntafySettingsActivity.this,true);
                        popup.dismiss();
                    }
                }, 1000);
            }
        });



        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable(getResources()));
        popup.setOutsideTouchable(true);
        popup.showAtLocation(layout, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
    }


}
