package com.ijoomer.tracethesteps.src;

import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;

import com.ijoomer.common.classes.IjoomerSplashMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerGlobalConfiguration;
import com.ijoomer.oauth.IjoomerOauth;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

import java.util.ArrayList;
import java.util.HashMap;

public class IjoomerSplashActivity extends IjoomerSplashMaster {
	IjoomerGlobalConfiguration globalConfiguration;
	private LinearLayout lnrSync;

	private void responseMessageHandler(final int responseCode, final boolean isFinish) {
		IjoomerUtilities.getCustomOkDialog(getString(R.string.splash), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
				getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

					@Override
					public void NeutralMethod() {
						if (responseCode == 599 && isFinish) {
							finish();
						}
					}
				});
	}

	public void initComponents() {
		lnrSync = (LinearLayout) findViewById(R.id.lnrSync);

		globalConfiguration = new IjoomerGlobalConfiguration(IjoomerSplashActivity.this);

		if (Boolean.parseBoolean(getString(R.string.show_url_set_dialog))) {
			if (getSmartApplication().readSharedPreferences().getBoolean(SP_URL_SETTING, false)) {
				if (!getSmartApplication().readSharedPreferences().getBoolean(SP_ICON_PRELOADER, false)) {
					lnrSync.setVisibility(View.VISIBLE);
				}
				authentication();
			} else {
				showUrlSettingDialog();
			}
		} else {
			if (!getSmartApplication().readSharedPreferences().getBoolean(SP_ICON_PRELOADER, false)) {
				lnrSync.setVisibility(View.VISIBLE);
			}
			authentication();
		}
	}

	public void prepareViews() {
		if (!getSmartApplication().readSharedPreferences().getBoolean(SP_REMEMBERME, false)) {
			getSmartApplication().writeSharedPreferences(SP_USERNAME, "");
			getSmartApplication().writeSharedPreferences(SP_PASSWORD, "");
		}
		if (!"mounted".equals(Environment.getExternalStorageState()))
			tong("External SD card not mounted");
	}

	public void setActionListeners() {
	}

	public int setLayoutId() {
		return R.layout.ijoomer_splash;
	}

	public View setLayoutView() {
		return null;
	}

	private void authentication() {
		this.globalConfiguration.loadGlobalConfiguration(new WebCallListener() {
			public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
				if (responseCode == 200) {
						if (getSmartApplication().readSharedPreferences().getBoolean(SP_REMEMBERME, false)) {
							IjoomerOauth.getInstance(IjoomerSplashActivity.this).autoLogin(IjoomerUtilities.getLoginParams().toString(), new WebCallListener() {
								public void onCallComplete(final int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
									if (responseCode == 200) {
										globalConfiguration.loadGlobalConfiguration(new WebCallListener() {

											public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
												if (responseCode == 200) {
													loadNew(IjoomerLoginActivity.class, IjoomerSplashActivity.this, true);
												} else {
													responseMessageHandler(responseCode, true);
												}
											}

											public void onProgressUpdate(int paramAnonymous3Int) {
											}
										});
									} else {
										responseMessageHandler(responseCode, true);
									}
								}

								public void onProgressUpdate(int paramAnonymous2Int) {
								}
							});
						} else {
							if (getSmartApplication().readSharedPreferences().getString(SP_DEFAULT_LANDING_SCREEN, "").length() > 0) {
								try {
									Intent localIntent = new Intent();
									localIntent.setClassName(IjoomerSplashActivity.this, getSmartApplication().readSharedPreferences().getString(SP_DEFAULT_LANDING_SCREEN, ""));
									localIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									localIntent.putExtra("IN_OBJ", getSmartApplication().readSharedPreferences().getString(SP_LAST_ACTIVITY_INTENT, ""));
									startActivity(localIntent);
									finish();
								} catch (Exception e1) {
									try {
										loadNew(IjoomerLoginActivity.class, IjoomerSplashActivity.this, true, "IN_USERID", "0");
									} catch (Throwable e) {
										e.printStackTrace();
									}
								} finally {
									finish();
								}
								try {
									loadNew(IjoomerLoginActivity.class, IjoomerSplashActivity.this, true, "IN_USERID", "0");
								} catch (Throwable e) {
									e.printStackTrace();
								}
							}else{
								loadNew(IjoomerLoginActivity.class, IjoomerSplashActivity.this, true);
							}
						}
					
				} else {
					responseMessageHandler(responseCode, true);
				}
			}

			public void onProgressUpdate(int responseCode) {
			}
		});
	}
}