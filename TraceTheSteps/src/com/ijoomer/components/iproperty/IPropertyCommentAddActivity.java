package com.ijoomer.components.iproperty;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.View;
import android.widget.SeekBar;

import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerEditText;
import com.ijoomer.library.iproperty.IPropertyDetailDataProvider;
import com.ijoomer.tracethesteps.src.R;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

public class IPropertyCommentAddActivity extends IPropertyMasterActivity {

	private String IN_COMMENT;
	private String IN_COMMENT_ID;
	private String IN_PROPERTY_ID;
	private IjoomerButton btnSend;
	private IjoomerEditText edtComment;
	private IjoomerEditText edtUserEmail;
	private IjoomerEditText edtUserName;
	private IPropertyDetailDataProvider provider;

	private void responseErrorMessageHandler(final int responseCode, final boolean isFinish) {
		IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_comment), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
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
		this.edtComment = ((IjoomerEditText) findViewById(R.id.edtComment));
		this.edtUserName = ((IjoomerEditText) findViewById(R.id.edtUserName));
		this.edtUserEmail = ((IjoomerEditText) findViewById(R.id.edtUserEmail));
		this.btnSend = ((IjoomerButton) findViewById(R.id.btnSend));
		this.provider = new IPropertyDetailDataProvider(this);
	}

	public void prepareViews() {

		IN_PROPERTY_ID = getIntent().getStringExtra("IN_PROPERTY_ID") != null ? getIntent().getStringExtra("IN_PROPERTY_ID") : "";
		IN_COMMENT_ID = getIntent().getStringExtra("IN_COMMENT_ID") != null ? getIntent().getStringExtra("IN_COMMENT_ID") : "";
		IN_COMMENT = getIntent().getStringExtra("IN_COMMENT") != null ? getIntent().getStringExtra("IN_COMMENT") : "";

		if (!isLogin()) {
			edtUserName.setVisibility(View.VISIBLE);
			edtUserEmail.setVisibility(View.VISIBLE);
		}

		if (IN_COMMENT.trim().length() > 0) {
			edtComment.setText(IN_COMMENT);
		}

	}

	public void setActionListeners() {
		this.btnSend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (isLogin()) {
					if (IN_COMMENT_ID.trim().length() > 0) {
						if (IN_COMMENT.equals(edtComment.getText().toString())) {
							finish();
						}
						if (edtComment.getText().toString().trim().length() > 0) {
							final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.iproperty_comment));
							provider.editComment(IN_COMMENT_ID, edtComment.getText().toString().trim(), new WebCallListener() {
								public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
									if (responseCode == 200) {
										IjoomerApplicationConfiguration.setReloadRequired(true);
										finish();
									} else {
										responseErrorMessageHandler(responseCode, true);
									}
								}

								public void onProgressUpdate(int progressCount) {
									proSeekBar.setProgress(progressCount);
								}
							});
						} else {
							edtComment.setError(getString(R.string.validation_value_required));
						}
					} else {
						if (edtComment.getText().toString().trim().length() > 0) {
							final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.iproperty_comment));
							provider.addComment(IN_PROPERTY_ID, edtComment.getText().toString().trim(), new WebCallListener() {
								public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
									if (responseCode == 200) {
										IjoomerApplicationConfiguration.setReloadRequired(true);
										finish();
									} else {
										responseErrorMessageHandler(responseCode, true);
									}
								}

								public void onProgressUpdate(int progressCount) {
									proSeekBar.setProgress(progressCount);
								}
							});
						} else {
							edtComment.setError(getString(R.string.validation_value_required));
						}
					}
				}
				boolean isValidation = true;
				if (edtUserName.getText().toString().trim().length() <= 0) {
					isValidation = false;
					edtUserName.setError(getString(R.string.validation_value_required));
				}
				if (edtUserEmail.getText().toString().trim().length() <= 0) {
					isValidation = false;
					edtUserEmail.setError(getString(R.string.validation_value_required));
				} else if (!IjoomerUtilities.emailValidator(edtUserEmail.getText().toString())) {
					edtUserEmail.setError(getString(R.string.validation_invalid_email));
					isValidation = false;
				}
				if (edtComment.getText().toString().trim().length() <= 0) {
					edtComment.setError(getString(R.string.validation_value_required));
					isValidation = false;
				}
				if (isValidation) {
					final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.iproperty_comment));
					provider.addComment(IN_PROPERTY_ID, edtComment.getText().toString().trim(), edtUserName.getText().toString().trim(), edtUserEmail.getText().toString().trim(),
							new WebCallListener() {
								public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
									if (responseCode == 200) {
										IjoomerApplicationConfiguration.setReloadRequired(true);
										finish();
									} else {
										responseErrorMessageHandler(responseCode, true);
									}
								}

								public void onProgressUpdate(int progressCount) {
									proSeekBar.setProgress(progressCount);
								}
							});

				}
			}
		});
	}

	public int setLayoutId() {
		return R.layout.iproperty_add_comment;
	}
}