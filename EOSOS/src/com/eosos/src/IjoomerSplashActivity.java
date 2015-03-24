package com.eosos.src;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONObject;

import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.eosos.common.classes.IjoomerSplashMaster;
import com.eosos.common.classes.IjoomerUtilities;
import com.eosos.common.configuration.IjoomerApplicationConfiguration;
import com.eosos.common.configuration.IjoomerGlobalConfiguration;
import com.eosos.components.main.EososMenuActivity;
import com.eosos.custom.interfaces.IjoomerSharedPreferences;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.library.EososDataProvider;
import com.eosos.library.EososDataProvider.ImageDownloadListener;
import com.eosos.oauth.IjoomerOauth;
import com.eosos.weservice.WebCallListener;
import com.eosos.weservice.WebCallListenerWithCacheInfo;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.SmartApplication;

/**
 * This Class Contains All Method Related To IjoomerSplashActivity.
 *
 * @author tasol
 *
 */
public class IjoomerSplashActivity extends IjoomerSplashMaster {

    IjoomerGlobalConfiguration globalConfiguration;
    private LinearLayout lnrSync;
    private EososDataProvider dataProvider;
    private String IN_PARENT_ID = "55", IN_SECTION_ID = "54";
    private String IN_FEATUREDFIRST = "No";

    private IjoomerTextView txtLoadingMsg;
    private SeekBar skProgress;
    private IjoomerTextView txtProgress;

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
        exportDatabse("EOSOS");

        lnrSync = (LinearLayout) findViewById(R.id.lnrSync);
        skProgress = (SeekBar) findViewById(R.id.skProgress);
        txtLoadingMsg = (IjoomerTextView) findViewById(R.id.txtLoadingMsg);
        txtProgress = (IjoomerTextView) findViewById(R.id.txtProgress);

        globalConfiguration = new IjoomerGlobalConfiguration(IjoomerSplashActivity.this);
        dataProvider = new EososDataProvider(IjoomerSplashActivity.this);

        if (getSmartApplication().readSharedPreferences().getString(SP_LAST_REQUEST_DATE, "").length() <= 0) {
            lnrSync.setVisibility(View.VISIBLE);
        }
        authentication();
    }

    @Override
    public void prepareViews() {

        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
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
        globalConfiguration.loadGlobalConfiguration(new WebCallListener() {

            @Override
            public void onProgressUpdate(int progressCount) {
            }

            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {

                if (responseCode == 200 || responseCode == 599) {
                    if (getLastRequestTime().length() > 0) {
                        loadNew(EososMenuActivity.class, IjoomerSplashActivity.this, true);
                    }
                    if (IjoomerGlobalConfiguration.isLoginRequired() || IjoomerUtilities.getLoginParams() != null && IjoomerUtilities.getLoginParams().toString().length() > 0) {
                        if (!getSmartApplication().readSharedPreferences().getBoolean(SP_ISLOGOUT, true)) {
                            IjoomerOauth.getInstance(IjoomerSplashActivity.this).autoLogin(IjoomerUtilities.getLoginParams().toString(), new WebCallListener() {

                                @Override
                                public void onProgressUpdate(int progressCount) {
                                }

                                @Override
                                public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                    if (responseCode == 200) {
                                        if (getSmartApplication().readSharedPreferences().getString(SP_DEFAULT_LANDING_SCREEN, "").length() > 0) {
                                            try {
                                                JSONObject intentData = new JSONObject(getSmartApplication().readSharedPreferences().getString(SP_LAST_ACTIVITY_INTENT, ""))
                                                        .getJSONObject("itemdata");
                                                IN_PARENT_ID = intentData.getString("categoryID");
                                                IN_SECTION_ID = intentData.getString("sectionID");
                                                IN_FEATUREDFIRST = intentData.getString("featuredFirst");
                                            } catch (Exception e) {

                                            }
                                        }
                                        getEntries();

                                    } else {
                                        if (getSmartApplication().readSharedPreferences().getString(SP_DEFAULT_LANDING_SCREEN, "").length() > 0) {
                                            try {
                                                JSONObject intentData = new JSONObject(getSmartApplication().readSharedPreferences().getString(SP_LAST_ACTIVITY_INTENT, ""))
                                                        .getJSONObject("itemdata");
                                                IN_PARENT_ID = intentData.getString("categoryID");
                                                IN_SECTION_ID = intentData.getString("sectionID");
                                                IN_FEATUREDFIRST = intentData.getString("featuredFirst");
                                            } catch (Exception e) {

                                            }
                                        }
                                        getEntries();
                                    }
                                }
                            });
                        } else {
                            if (getSmartApplication().readSharedPreferences().getString(SP_DEFAULT_LANDING_SCREEN, "").length() > 0) {
                                try {
                                    JSONObject intentData = new JSONObject(getSmartApplication().readSharedPreferences().getString(SP_LAST_ACTIVITY_INTENT, ""))
                                            .getJSONObject("itemdata");
                                    IN_PARENT_ID = intentData.getString("categoryID");
                                    IN_SECTION_ID = intentData.getString("sectionID");
                                    IN_FEATUREDFIRST = intentData.getString("featuredFirst");
                                } catch (Exception e) {

                                }
                            }
                            getEntries();
                        }
                        // else {
                        // loadNew(IjoomerLoginActivity.class,
                        // IjoomerSplashActivity.this, true);
                        // }
                    } else {
                        if (getSmartApplication().readSharedPreferences().getString(SP_DEFAULT_LANDING_SCREEN, "").length() > 0) {
                            try {
                                JSONObject intentData = new JSONObject(getSmartApplication().readSharedPreferences().getString(SP_LAST_ACTIVITY_INTENT, ""))
                                        .getJSONObject("itemdata");
                                IN_PARENT_ID = intentData.getString("categoryID");
                                IN_SECTION_ID = intentData.getString("sectionID");
                                IN_FEATUREDFIRST = intentData.getString("featuredFirst");
                            } catch (Exception e) {

                            }
                        }
                        getEntries();
                    }

                } else {
                    responseMessageHandler(responseCode, true);
                }
            }
        });
    }

    /**
     * This method used to get entry list.
     */
    private void getEntries() {

        if (getLastRequestTime().length() <= 0) {
            System.out.println("Download Start : " + System.currentTimeMillis());

            txtProgress.setText("5%");
            skProgress.setProgress(5);
            dataProvider.getClubs(getDeviceUDID(),IN_SECTION_ID, IN_PARENT_ID, IN_FEATUREDFIRST, "1", getSmartApplication().readSharedPreferences().getString(SP_LAST_REQUEST_DATE, ""),
                    new WebCallListener() {

                        @Override
                        public void onCallComplete(final int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                        if (responseCode == 200) {

                                            try {
                                                if (!dataProvider.isCalling() && dataProvider.hasNextPage()) {
                                                    getEntries();
                                                } else {
                                                    setLastRequestTime();
                                                    txtLoadingMsg.setText("Loading Media...");
                                                    skProgress.setProgress(0);
                                                    txtProgress.setText("0/0");
                                                    dataProvider.startDownloadEntryImages(new ImageDownloadListener() {

                                                        @Override
                                                        public void onTaskComplete() {
                                                            System.out.println("Download End : " + System.currentTimeMillis());
                                                            lnrSync.setVisibility(View.GONE);
                                                            loadNew(EososMenuActivity.class, IjoomerSplashActivity.this, true);

                                                        }

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
                                                    });

                                                }
                                            } catch (Exception e) {
                                                setLastRequestTime();
                                                loadNew(EososMenuActivity.class, IjoomerSplashActivity.this, true);
                                            }

                                        } else {
                                            responseMessageHandler(responseCode, true);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onProgressUpdate(final int progressCount) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    skProgress.setProgress(progressCount / 2);
                                    txtProgress.setText(progressCount / 2 + "%");
                                    if (progressCount == 100) {
                                        skProgress.setProgress(100);
                                        txtProgress.setText("100%");
                                    }
                                }
                            });

                        }
                    });
        } else {
            dataProvider.getClubs(getDeviceUDID(),IN_SECTION_ID, IN_PARENT_ID, IN_FEATUREDFIRST, "0",
                    getSmartApplication().readSharedPreferences().getString(SP_LAST_REQUEST_DATE, ""), new WebCallListener() {

                        @Override
                        public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                            try {
                                if (responseCode == 200) {

                                    try {
                                        if (!dataProvider.isCalling() && dataProvider.hasNextPage()) {
                                            getEntries();
                                        } else {
                                            setLastRequestTime();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                } else {
                                    if (responseCode != 599) {
                                        responseMessageHandler(responseCode, true);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onProgressUpdate(int progressCount) {
                        }
                    });

        }

    }

    private void setLastRequestTime() {
        SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_LAST_REQUEST_DATE,
                String.valueOf((Calendar.getInstance().getTimeInMillis() / 1000)));
    }

    private String getLastRequestTime() {
        return getSmartApplication().readSharedPreferences().getString(SP_LAST_REQUEST_DATE, "");
    }

    /**
     * This method used to shown response message.
     *
     * @param responseCode
     *            represented response code
     * @param finishActivityOnConnectionProblem
     *            represented finish activity on connection problem
     */
    private void responseMessageHandler(final int responseCode, final boolean finishActivityOnConnectionProblem) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.splash), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
                getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                    @Override
                    public void NeutralMethod() {
                        if (responseCode == 599 && finishActivityOnConnectionProblem) {
                            finish();
                        }
                    }
                });
    }

}