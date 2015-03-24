package com.mycadiz.src;

import android.content.res.Configuration;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.mycadiz.common.classes.IjoomerSplashMaster;
import com.mycadiz.common.classes.IjoomerUtilities;
import com.mycadiz.common.configuration.IjoomerApplicationConfiguration;
import com.mycadiz.common.configuration.IjoomerGlobalConfiguration;
import com.mycadiz.customviews.IjoomerTextView;
import com.mycadiz.library.sobipro.SobiproCategoriesDataProvider;
import com.mycadiz.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;
import com.mycadiz.library.sobipro.SobiproCategoriesDataProvider.ImageDownloadListener;
import com.smart.framework.SmartApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * This Class Contains All Method Related To IjoomerSplashActivity.
 *
 * @author tasol
 *
 */
public class IjoomerSplashActivity extends IjoomerSplashMaster {

    IjoomerGlobalConfiguration globalConfiguration;
    private LinearLayout lnrSync;
    private LinearLayout lnrMain;

    private IjoomerTextView txtLoadingMsg;
    private SeekBar skProgress;
    private IjoomerTextView txtProgress;
    private String IN_PRIVIOUS_ACTIVITY;
    private IjoomerTextView txtSync;

    /**
     * Overrides methods
     */

    @Override
    public int setLayoutId() {
        IjoomerApplicationConfiguration.setDefaultConfiguration(this);
        return R.layout.ijoomer_splash;
    }

    @Override
    public void initComponents() {
        exportDatabse("ijoomer");
        lnrSync = (LinearLayout) findViewById(R.id.lnrSync);
        txtSync =(IjoomerTextView) findViewById(R.id.txtSync);
        lnrMain = (LinearLayout) findViewById(R.id.lnrMain);
        skProgress = (SeekBar) findViewById(R.id.skProgress);
        txtLoadingMsg = (IjoomerTextView) findViewById(R.id.txtLoadingMsg);
        txtProgress = (IjoomerTextView) findViewById(R.id.txtProgress);

        globalConfiguration = new IjoomerGlobalConfiguration(
                IjoomerSplashActivity.this);

        if(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_LNAGUAGE,"").length()>0) {
            changeLanguage(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_LNAGUAGE,""));
            if(getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY")!=null && getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY").length()>0){
                IN_PRIVIOUS_ACTIVITY = getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY").trim();
            }else{
                IN_PRIVIOUS_ACTIVITY = getString(R.string.home).trim();
            }
            if (Boolean.parseBoolean(getString(R.string.show_url_set_dialog))) {
                if (getSmartApplication().readSharedPreferences().getBoolean(
                        SP_URL_SETTING, false)) {
                    if (!getSmartApplication().readSharedPreferences().getBoolean(
                            SP_ICON_PRELOADER, false)) {
                        lnrSync.setVisibility(View.VISIBLE);
                    }
                    authentication();
                }
            } else {
                authentication();
            }
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadNew(IjoomerSelectLanguageActivity.class,
                            IjoomerSplashActivity.this, true);

                }
            }, 500);
        }

        // Starts an Apphance session using a dummy key and QA mode
    }

    private void changeLanguage(String langCode){
        Locale locale;
        if(langCode.equals("es")){
            locale = new Locale(langCode,"ES");
        }else{
            locale = new Locale(langCode,"EN");
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void prepareViews() {
        if (!Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            tong("External SD card not mounted");
        }
    }

    @Override
    public void setActionListeners() {
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    /**
     * Class methods
     */

    /**
     * This method used to get global configuration data.
     */
    private void authentication() {
        if(getSmartApplication().readSharedPreferences().getString(SP_LAST_REQUEST_TIME,"").length()<=0){
            lnrSync.setVisibility(View.VISIBLE);
            txtSync.setText(getString(R.string.sync_with_server));
            globalConfiguration.loadGlobalConfiguration(new WebCallListener() {

                @Override
                public void onProgressUpdate(int progressCount) {
                }

                @Override
                public void onCallComplete(int responseCode, String errorMessage,
                                           ArrayList<HashMap<String, String>> data1, Object data2) {
                    if (responseCode == 200) {
                        globalConfiguration.loadAllData(true,"1",getLastRequestTime(),new WebCallListener() {
                            @Override
                            public void onCallComplete(final int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (responseCode == 200) {
                                            setLastRequestTime();
                                            getSmartApplication().writeSharedPreferences(SP_LAST_REQUEST_TIME,getLastRequestTime());
                                            lnrSync.setVisibility(View.GONE);
                                            lnrMain.setVisibility(View.VISIBLE);
                                            txtLoadingMsg.setText(getString(R.string.alert_loading_media));
                                            skProgress.setProgress(0);
                                            txtProgress.setText("0/0");
                                            getSmartApplication().writeSharedPreferences(SP_ALLIMAGEDOWNLOADED,"false");
                                            new SobiproCategoriesDataProvider(IjoomerSplashActivity.this).startDownloadEntryImages(new ImageDownloadListener(){
                                                @Override
                                                public void onImgeDownload(int total, int countProgress) {
                                                    if (countProgress < 0) {
                                                        IjoomerUtilities.getCustomOkDialog(getString(R.string.splash),
                                                                getString(getResources().getIdentifier("code" + (599), "string", getPackageName())),
                                                                getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                                                    @Override
                                                                    public void NeutralMethod() {
                                                                        finish();
                                                                    }
                                                                });
                                                    } else {
                                                        skProgress.setMax(total);
                                                        skProgress.setProgress(countProgress);
                                                        txtProgress.setText((countProgress) + "/" + (total));
                                                    }
                                                }

                                                @Override
                                                public void onTaskComplete() {
                                                    lnrMain.setVisibility(View.GONE);
                                                    getSmartApplication().writeSharedPreferences(SP_ALLIMAGEDOWNLOADED,"true");
                                                    try {
                                                        loadNew(IjoomerHomeActivity.class,
                                                                IjoomerSplashActivity.this, true,
                                                                "IN_USERID", "0", "IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                                    } catch (Throwable e1) {
                                                        e1.printStackTrace();
                                                    }
                                                }
                                            });

                                        }else{
                                            responseMessageHandler(responseCode, true);
                                        }
                                    }
                                });

                            }

                            @Override
                            public void onProgressUpdate(final int progressCount) {

                            }
                        });
                    }else{
                        responseMessageHandler(responseCode, true);
                    }
                }
            });
        }else{
            lnrSync.setVisibility(View.VISIBLE);
            txtSync.setText(getString(R.string.sync_with_server));
            if(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_LNAGUAGE,"").equals("es")){
                globalConfiguration.loadConfigurationForSpanish(new WebCallListener() {

                    @Override
                    public void onProgressUpdate(int progressCount) {
                    }

                    @Override
                    public void onCallComplete(int responseCode, String errorMessage,ArrayList<HashMap<String, String>> data1, Object data2) {
                        if(responseCode==599){
                            lnrSync.setVisibility(View.GONE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        loadNew(IjoomerHomeActivity.class,IjoomerSplashActivity.this, true,"IN_USERID", "0","IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            },1000);

                        }else{
                            globalConfiguration.loadAllData(false, "0", getLastRequestTime(), new WebCallListener() {
                                @Override
                                public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                    getSmartApplication().writeSharedPreferences(SP_LAST_REQUEST_TIME, getLastRequestTime());
                                    if (responseCode == 200) {
                                        setLastRequestTime();
                                        if(getSmartApplication().readSharedPreferences().getString(SP_ALLIMAGEDOWNLOADED,"false").equals("false")){
                                            lnrMain.setVisibility(View.VISIBLE);
                                            txtLoadingMsg.setText(getString(R.string.alert_loading_media));
                                            skProgress.setProgress(0);
                                            txtProgress.setText("0/0");
                                            getSmartApplication().writeSharedPreferences(SP_ALLIMAGEDOWNLOADED,"false");
                                            new SobiproCategoriesDataProvider(IjoomerSplashActivity.this).startDownloadEntryImages(new ImageDownloadListener(){
                                                @Override
                                                public void onImgeDownload(int total, int countProgress) {
                                                    if (countProgress < 0) {
                                                        IjoomerUtilities.getCustomOkDialog(getString(R.string.splash),
                                                                getString(getResources().getIdentifier("code" + (599), "string", getPackageName())),
                                                                getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                                                    @Override
                                                                    public void NeutralMethod() {
                                                                        finish();
                                                                    }
                                                                });
                                                    } else {
                                                        skProgress.setMax(total);
                                                        skProgress.setProgress(countProgress);
                                                        txtProgress.setText((countProgress) + "/" + (total));
                                                    }
                                                }

                                                @Override
                                                public void onTaskComplete() {
                                                    lnrMain.setVisibility(View.GONE);
                                                    getSmartApplication().writeSharedPreferences(SP_ALLIMAGEDOWNLOADED,"true");
                                                    try {
                                                        loadNew(IjoomerHomeActivity.class,
                                                                IjoomerSplashActivity.this, true,
                                                                "IN_USERID", "0","IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                                    } catch (Throwable e1) {
                                                        e1.printStackTrace();
                                                    }
                                                }
                                            });
                                        }else {
                                            new SobiproCategoriesDataProvider(IjoomerSplashActivity.this).startDownloadEntryImages(new ImageDownloadListener() {
                                                @Override
                                                public void onImgeDownload(int total, int countProgress) {

                                                }

                                                @Override
                                                public void onTaskComplete() {
                                                    lnrSync.setVisibility(View.GONE);
                                                    try {
                                                        loadNew(IjoomerHomeActivity.class, IjoomerSplashActivity.this, true, "IN_USERID", "0", "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                                    } catch (Throwable e1) {
                                                        e1.printStackTrace();
                                                    }
                                                }
                                            });

                                        }
                                    } else {
                                        lnrSync.setVisibility(View.GONE);
                                        try {
                                            loadNew(IjoomerHomeActivity.class, IjoomerSplashActivity.this, true, "IN_USERID", "0", "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                        } catch (Throwable e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onProgressUpdate(int progressCount) {

                                }
                            });



                        }

                    }
                });
            }else{
                globalConfiguration.loadConfigurationForEnglish(new WebCallListener() {

                    @Override
                    public void onProgressUpdate(int progressCount) {
                    }

                    @Override
                    public void onCallComplete(int responseCode, String errorMessage,ArrayList<HashMap<String, String>> data1, Object data2) {
                        if(responseCode==599){
                            lnrSync.setVisibility(View.GONE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        loadNew(IjoomerHomeActivity.class,IjoomerSplashActivity.this, true,"IN_USERID", "0","IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                    } catch (Throwable e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            },1000);

                        }else{
                            globalConfiguration.loadAllData(false, "0", getLastRequestTime(), new WebCallListener() {
                                @Override
                                public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                    getSmartApplication().writeSharedPreferences(SP_LAST_REQUEST_TIME, getLastRequestTime());
                                    if (responseCode == 200) {
                                        setLastRequestTime();
                                        if(getSmartApplication().readSharedPreferences().getString(SP_ALLIMAGEDOWNLOADED,"false").equals("false")){
                                            lnrMain.setVisibility(View.VISIBLE);
                                            txtLoadingMsg.setText(getString(R.string.alert_loading_media));
                                            skProgress.setProgress(0);
                                            txtProgress.setText("0/0");
                                            getSmartApplication().writeSharedPreferences(SP_ALLIMAGEDOWNLOADED,"false");
                                            new SobiproCategoriesDataProvider(IjoomerSplashActivity.this).startDownloadEntryImages(new ImageDownloadListener(){
                                                @Override
                                                public void onImgeDownload(int total, int countProgress) {
                                                    if (countProgress < 0) {
                                                        IjoomerUtilities.getCustomOkDialog(getString(R.string.splash),
                                                                getString(getResources().getIdentifier("code" + (599), "string", getPackageName())),
                                                                getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                                                    @Override
                                                                    public void NeutralMethod() {
                                                                        finish();
                                                                    }
                                                                });
                                                    } else {
                                                        skProgress.setMax(total);
                                                        skProgress.setProgress(countProgress);
                                                        txtProgress.setText((countProgress) + "/" + (total));
                                                    }
                                                }

                                                @Override
                                                public void onTaskComplete() {
                                                    lnrMain.setVisibility(View.GONE);
                                                    getSmartApplication().writeSharedPreferences(SP_ALLIMAGEDOWNLOADED,"true");
                                                    try {
                                                        loadNew(IjoomerHomeActivity.class,
                                                                IjoomerSplashActivity.this, true,
                                                                "IN_USERID", "0","IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
                                                    } catch (Throwable e1) {
                                                        e1.printStackTrace();
                                                    }
                                                }
                                            });
                                        }else {
                                            new SobiproCategoriesDataProvider(IjoomerSplashActivity.this).startDownloadEntryImages(new ImageDownloadListener() {
                                                @Override
                                                public void onImgeDownload(int total, int countProgress) {

                                                }

                                                @Override
                                                public void onTaskComplete() {
                                                    lnrSync.setVisibility(View.GONE);
                                                    try {
                                                        loadNew(IjoomerHomeActivity.class, IjoomerSplashActivity.this, true, "IN_USERID", "0", "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                                    } catch (Throwable e1) {
                                                        e1.printStackTrace();
                                                    }
                                                }
                                            });

                                        }
                                    } else {
                                        lnrSync.setVisibility(View.GONE);
                                        try {
                                            loadNew(IjoomerHomeActivity.class, IjoomerSplashActivity.this, true, "IN_USERID", "0", "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                        } catch (Throwable e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onProgressUpdate(int progressCount) {

                                }
                            });



                        }

                    }
                });
            }
        }


    }

    private void setLastRequestTime(){
        getSmartApplication().writeSharedPreferences(SP_LAST_REQUEST_TIME,String.valueOf(((System.currentTimeMillis()/1000)-900)));
    }

    private String getLastRequestTime(){
        return getSmartApplication().readSharedPreferences().getString(SP_LAST_REQUEST_TIME, "");
    }

    /**
     * This method used to shown response message.
     *
     * @param responseCode
     *            represented response code
     * @param finishActivityOnConnectionProblem
     *            represented finish activity on connection problem
     */
    private void responseMessageHandler(final int responseCode,
                                        final boolean finishActivityOnConnectionProblem) {
        IjoomerUtilities.getCustomOkDialog(
                getString(R.string.splash),
                getString(getResources().getIdentifier("code" + responseCode,
                        "string", getPackageName())), getString(R.string.ok),
                R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                    @Override
                    public void NeutralMethod() {
                        if (responseCode == 599
                                && finishActivityOnConnectionProblem) {
                            finish();
                        }
                    }
                });
    }

}