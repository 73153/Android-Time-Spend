package com.ijoomer.intafy.src;

import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;

import com.ijoomer.common.classes.IjoomerSplashMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.common.configuration.IjoomerGlobalConfiguration;
import com.ijoomer.oauth.IjoomerOauth;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This Class Contains All Method Related To IjoomerSplashActivity.
 *
 * @author tasol
 *
 */
public class IjoomerSplashActivity extends IjoomerSplashMaster {

    IjoomerGlobalConfiguration globalConfiguration;
    private LinearLayout lnrSync;

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
        globalConfiguration = new IjoomerGlobalConfiguration(IjoomerSplashActivity.this);
        if (Boolean.parseBoolean(getString(R.string.show_url_set_dialog))) {
            if (getSmartApplication().readSharedPreferences().getBoolean(SP_URL_SETTING, false)) {
                if (!getSmartApplication().readSharedPreferences().getBoolean(SP_ICON_PRELOADER, false)) {
                    lnrSync.setVisibility(View.VISIBLE);
                }
                authentication();
            } else {
//                showUrlSettingDialog();
            }
        } else {
            if (!getSmartApplication().readSharedPreferences().getBoolean(SP_ICON_PRELOADER, false)) {
                lnrSync.setVisibility(View.VISIBLE);
            }
            authentication();
        }


        // Starts an Apphance session using a dummy key and QA mode
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Class methods
     */

    /**
     * This method used to get global configuration data.
     */
    private void authentication() {
        final String networkId = getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0");
        getSmartApplication().writeSharedPreferences(SP_NETWORKID,"0");
        globalConfiguration.loadGlobalConfiguration(new WebCallListener() {

            @Override
            public void onProgressUpdate(int progressCount) {
            }

            @Override
            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                if (responseCode == 200) {
                    getSmartApplication().writeSharedPreferences(SP_NETWORKID,networkId);
                    if (IjoomerGlobalConfiguration.isLoginRequired() || IjoomerUtilities.getLoginParams() != null
                            && IjoomerUtilities.getLoginParams().toString().length() > 0) {
                            IjoomerOauth.getInstance(IjoomerSplashActivity.this).autoLogin(IjoomerUtilities.getLoginParams().toString(),
                                    new WebCallListener() {

                                        @Override
                                        public void onProgressUpdate(int progressCount) {
                                        }

                                        @Override
                                        public void onCallComplete(int responseCode, String errorMessage,
                                                                   ArrayList<HashMap<String, String>> data1, Object data2) {
                                            if (responseCode == 200) {
                                                final String networkId = getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0");
                                                getSmartApplication().writeSharedPreferences(SP_NETWORKID,"0");
                                                globalConfiguration.loadGlobalConfiguration(new WebCallListener() {

                                                    @Override
                                                    public void onProgressUpdate(int progressCount) {
                                                    }

                                                    @Override
                                                    public void onCallComplete(int responseCode, String errorMessage,
                                                                               ArrayList<HashMap<String, String>> data1, Object data2) {
                                                        if (responseCode == 200) {
                                                            getSmartApplication().writeSharedPreferences(SP_NETWORKID,networkId);
                                                            try {
                                                                loadNew(IjoomerHomeActivity.class, IjoomerSplashActivity.this,
                                                                        true, "IN_USERID", "0");
                                                            } catch (Throwable e1) {
                                                                e1.printStackTrace();
                                                            }
                                                        } else {
                                                            getSmartApplication().writeSharedPreferences(SP_NETWORKID,networkId);
                                                            responseMessageHandler(responseCode, true);
                                                        }
                                                    }
                                                });

                                            } else {
                                                IjoomerUtilities.getCustomOkDialog(
                                                        getString(R.string.splash),
                                                        getString(getResources().getIdentifier("code" + responseCode, "string",
                                                                getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog,
                                                        new CustomAlertNeutral() {

                                                            @Override
                                                            public void NeutralMethod() {
                                                            }
                                                        });
                                            }
                                        }
                                    });
                    } else {
                        try {
                            loadNew(IjoomerHomeActivity.class, IjoomerSplashActivity.this, true, "IN_USERID", "0");
                        } catch (Throwable e1) {
                            e1.printStackTrace();
                        }
                    }
                } else if (responseCode == 599
                        && getSmartApplication().readSharedPreferences().getString(SP_DEFAULT_LANDING_SCREEN, "").length() > 0) {
                    getSmartApplication().writeSharedPreferences(SP_NETWORKID,networkId);
                    try {
                        loadNew(IjoomerHomeActivity.class, IjoomerSplashActivity.this, true, "IN_USERID", "0");
                    } catch (Throwable e1) {
                        e1.printStackTrace();
                    }
                    finish();

                } else {
                    getSmartApplication().writeSharedPreferences(SP_NETWORKID,networkId);
                    try {
                        loadNew(IjoomerHomeActivity.class, IjoomerSplashActivity.this, true, "IN_USERID", "0");
                    } catch (Throwable e1) {
                        e1.printStackTrace();
                    }
                }
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
    private void responseMessageHandler(final int responseCode, final boolean finishActivityOnConnectionProblem) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.splash),
                getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.intafy_ok),
                R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {
                if (responseCode == 599 && finishActivityOnConnectionProblem) {
                    finish();
                }
            }
        });
    }

}