package com.ijoomer.tracethesteps.src;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.ijoomer.common.classes.IjoomerLoginMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.custom.interfaces.IjoomerSharedPreferences;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerCheckBox;
import com.ijoomer.customviews.IjoomerEditText;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.oauth.IjoomerOauth;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class IjoomerLoginActivity extends IjoomerLoginMaster {
	private IjoomerButton btnLogin;
	private IjoomerButton btnLogout;
	private IjoomerButton btnFbLogin;
	private IjoomerCheckBox chkRemeberMe;
	private IjoomerEditText edtPassword;
	private IjoomerEditText edtUserName;
	private LinearLayout lnrLogin;
	private LinearLayout lnrLogout;
	private IjoomerTextView txtUserName;
	private IjoomerTextView txtWelcomeNote;
	private IjoomerTextView btnSignUp;
	private IjoomerTextView btnForgetPassword;
	private IjoomerTextView btnForgetUserName;

	private final int FACEBOOK_LOGIN = 1;

	private void responseMessageHandler(final int responseCode, final boolean isFinish) {
		IjoomerUtilities.getCustomOkDialog(getString(R.string.login), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
				getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

					@Override
					public void NeutralMethod() {
						if (responseCode == 599 && isFinish) {
							finish();
						}
					}
				});

	}

	private void setLoginLogout(boolean isLogin) {
		if (isLogin || (getSmartApplication().readSharedPreferences().getString(IjoomerSharedPreferences.SP_USERNAME, "").trim().length() > 0)
				&& (getSmartApplication().readSharedPreferences().getString(SP_PASSWORD, "").trim().length() > 0)) {
			lnrLogin.setVisibility(View.GONE);
			lnrLogout.setVisibility(View.VISIBLE);
			txtUserName.setText(getSmartApplication().readSharedPreferences().getString(IjoomerSharedPreferences.SP_USERNAME, ""));
			txtWelcomeNote
					.setText("Hi " + getSmartApplication().readSharedPreferences().getString(IjoomerSharedPreferences.SP_USERNAME, "") + "\nNice To See You In TraceTheSteps");
		} else {
			lnrLogin.setVisibility(View.VISIBLE);
			lnrLogout.setVisibility(View.GONE);
			edtUserName.setText("");
			edtPassword.setText("");
		}
	}

	public void initComponents() {
		lnrLogin = ((LinearLayout) findViewById(R.id.lnrLogin));
		lnrLogout = ((LinearLayout) findViewById(R.id.lnrLogout));
		edtUserName = ((IjoomerEditText) findViewById(R.id.edtUserName));
		edtPassword = ((IjoomerEditText) findViewById(R.id.edtPassword));
		txtUserName = ((IjoomerTextView) findViewById(R.id.txtUserName));
		txtWelcomeNote = ((IjoomerTextView) findViewById(R.id.txtWelcomeNote));
		chkRemeberMe = ((IjoomerCheckBox) findViewById(R.id.chkRemeberMe));
		btnLogin = ((IjoomerButton) findViewById(R.id.btnLogin));
		btnLogout = ((IjoomerButton) findViewById(R.id.btnLogout));
		btnFbLogin = (IjoomerButton) findViewById(R.id.btnFbLogin);
		btnSignUp = (IjoomerTextView) findViewById(R.id.btnSignUp);
		btnForgetPassword = (IjoomerTextView) findViewById(R.id.btnForgetPassword);
		btnForgetUserName = (IjoomerTextView) findViewById(R.id.btnForgetUserName);
	}

	public void onBackPressed() {
		Intent localIntent = new Intent("clearStackActivity");
		localIntent.setType("text/plain");
		sendBroadcast(localIntent);
		moveTaskToBack(true);
	}

	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == FACEBOOK_LOGIN) {
				try {
					checkFacebookUser(new JSONObject(data.getStringExtra("IN_FACEBOOK_USER")));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void prepareViews() {
		btnSignUp.setText(Html.fromHtml(getString(R.string.create_account)));
		btnForgetPassword.setText(Html.fromHtml(getString(R.string.forget_password)));
		btnForgetUserName.setText(Html.fromHtml(getString(R.string.forget_username)));
		setLoginLogout(false);
	}

	public void setActionListeners() {

		btnForgetUserName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showForgetUserNameDialog();
			}
		});

		btnForgetPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showForgetPasswordDialog();
			}
		});
		btnSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				loadNew(My.class, IjoomerLoginActivity.this, false);

			}
		});

		btnFbLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

//				Intent intent = new Intent(getApplicationContext(), FacebookLoginActivity.class);
//				startActivityForResult(intent, FACEBOOK_LOGIN);
			}
		});

		btnLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				hideSoftKeyboard();
				boolean isValidation = true;
				if (edtPassword.getText().toString().trim().length() <= 0) {
					edtPassword.setError(getString(R.string.validation_value_required));
					isValidation = false;
				}
				if (edtUserName.getText().toString().trim().length() <= 0) {
					edtUserName.setError(getString(R.string.validation_value_required));
					isValidation = false;
				}
				if (isValidation)
					IjoomerOauth.getInstance(IjoomerLoginActivity.this).authenticateUser(edtUserName.getText().toString().trim(), edtPassword.getText().toString().trim(),
							new WebCallListener() {
								final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.login));

								public void onCallComplete(int responseCode, String paramAnonymous2String, ArrayList<HashMap<String, String>> data1, Object data2) {
									if (responseCode == 200) {
										if (chkRemeberMe.isChecked()) {
											getSmartApplication().writeSharedPreferences(SP_REMEMBERME, true);
										} else {
											getSmartApplication().writeSharedPreferences(SP_REMEMBERME, false);
										}
										getSmartApplication().writeSharedPreferences(IjoomerSharedPreferences.SP_USERNAME, edtUserName.getText().toString().trim());
										getSmartApplication().writeSharedPreferences(SP_PASSWORD, edtPassword.getText().toString().trim());
										setLoginLogout(true);
									} else {
										responseMessageHandler(responseCode, true);
									}
								}

								public void onProgressUpdate(int progressCount) {
									proSeekBar.setProgress(progressCount);
								}
							});
			}
		});
		btnLogout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				logout(new WebCallListener() {
					public void onCallComplete(int responseCode, String paramAnonymous2String, ArrayList<HashMap<String, String>> data1, Object data2) {
						if (responseCode == 200) {
							getSmartApplication().writeSharedPreferences(SP_USERNAME, "");
							getSmartApplication().writeSharedPreferences(SP_PASSWORD, "");
							getSmartApplication().writeSharedPreferences(SP_REMEMBERME, false);
							setLoginLogout(false);
						} else {
							responseMessageHandler(responseCode, false);
						}
					}

					public void onProgressUpdate(int progress) {
					}
				});
			}
		});
	}

	private void checkFacebookUser(final JSONObject data) {

		if (data.has("errorMessage")) {
			try {
				IjoomerUtilities.getCustomOkDialog(getString(R.string.login), data.getString("errorMessage"), getString(R.string.ok), R.layout.ijoomer_ok_dialog,
						new CustomAlertNeutral() {

							@Override
							public void NeutralMethod() {
							}
						});
			} catch (Throwable e) {
				e.printStackTrace();
			}

		} else {
			final SeekBar progressBar = IjoomerUtilities.getLoadingDialog("Connecting through facebook...");
			IjoomerOauth.getInstance(IjoomerLoginActivity.this).authenticateUserWithFacebook(data, new WebCallListener() {

				@Override
				public void onProgressUpdate(int progressCount) {
					progressBar.setProgress(progressCount);
				}

				@Override
				public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
					if (responseCode == 703) {
						showUserSelectionDialog(data);
					} else if (responseCode == 200) {
						try {
							if (getSmartApplication().readSharedPreferences().getString(SP_LAST_ACTIVITY, "").length() > 0) {

								try {
									Intent intent = new Intent();
									intent.setClassName(IjoomerLoginActivity.this, getSmartApplication().readSharedPreferences().getString(SP_LAST_ACTIVITY, ""));
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									intent.putExtra("IN_OBJ", getSmartApplication().readSharedPreferences().getString(SP_LAST_ACTIVITY_INTENT, ""));
									getSmartApplication().writeSharedPreferences(SP_LAST_ACTIVITY, "");
									startActivity(intent);
									getSmartApplication().writeSharedPreferences(SP_ISLOGOUT, false);
									getSmartApplication().writeSharedPreferences(SP_ISFACEBOOKLOGIN, true);
								} catch (Exception e) {
									try {
										loadNew(IjoomerHomeActivity.class, IjoomerLoginActivity.this, true, "IN_USERID", "0");
									} catch (Throwable e1) {
										e1.printStackTrace();
									}
								} finally {
									finish();
								}

							} else {
								if (getSmartApplication().readSharedPreferences().getString(SP_DEFAULT_LANDING_SCREEN, "").length() > 0) {
									try {
										Intent intent = new Intent();
										intent.setClassName(IjoomerLoginActivity.this, getSmartApplication().readSharedPreferences().getString(SP_DEFAULT_LANDING_SCREEN, ""));
										intent.putExtra("IN_OBJ", getSmartApplication().readSharedPreferences().getString(SP_LAST_ACTIVITY_INTENT, ""));
										intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										startActivity(intent);
									} catch (Exception e) {
										try {
											loadNew(IjoomerHomeActivity.class, IjoomerLoginActivity.this, true, "IN_USERID", "0");
										} catch (Throwable e1) {
											e1.printStackTrace();
										}
									} finally {
										finish();
									}
								} else {
									try {
										loadNew(IjoomerHomeActivity.class, IjoomerLoginActivity.this, true, "IN_USERID", "0");
									} catch (Throwable e1) {
										e1.printStackTrace();
									}
								}
							}

						} catch (Throwable e) {
							e.printStackTrace();
						}
						getSmartApplication().writeSharedPreferences(SP_ISLOGOUT, false);
						getSmartApplication().writeSharedPreferences(SP_ISFACEBOOKLOGIN, true);
					} else {
						if (responseCode != 200) {
							responseMessageHandler(responseCode, true);
						}
					}
				}
			});
		}

	}

	public int setLayoutId() {
		return R.layout.ijoomer_login;
	}
}