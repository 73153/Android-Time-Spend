package com.ijoomer.components.iproperty;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.library.iproperty.IPropertyDetailDataProvider;
import com.ijoomer.tracethesteps.src.R;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

public class IPropertyPictureAddActivity extends IPropertyMasterActivity {
	private String IN_PICTURE_PATH;
	private String IN_PROPERTY_ID;
	private String IN_PROPERTY_TITLE;
	private IjoomerButton btnSend;
	private ImageView imgAddPicture;
	private boolean isLoginDone = false;
	private IPropertyDetailDataProvider provider;
	private IjoomerTextView txtPropertyTitle;

	private void responseMessageHandler(final int responseCode, final boolean isFinish) {
		IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_picture_preview),
				getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.ok), R.layout.ijoomer_ok_dialog,
				new CustomAlertNeutral() {

					@Override
					public void NeutralMethod() {
						if (responseCode == 599 && isFinish) {
							finish();
						}
					}
				});
	}

	public void initComponents() {
		txtPropertyTitle = (IjoomerTextView) findViewById(R.id.txtPropertyTitle);
		btnSend = (IjoomerButton) findViewById(R.id.btnSend);
		imgAddPicture = (ImageView) findViewById(R.id.imgAddPicture);
		provider = new IPropertyDetailDataProvider(this);
	}

	protected void onResume() {
		if (isLoginDone) {
			isLoginDone = false;
			btnSend.performClick();
		}
		super.onResume();
	}

	public void prepareViews() {
		IN_PICTURE_PATH = getIntent().getStringExtra("IN_PICTURE_PATH") != null ? getIntent().getStringExtra("IN_PICTURE_PATH") : "";
		IN_PROPERTY_ID = getIntent().getStringExtra("IN_PROPERTY_ID") != null ? getIntent().getStringExtra("IN_PROPERTY_ID") : "";
		IN_PROPERTY_TITLE = getIntent().getStringExtra("IN_PROPERTY_TITLE") != null ? getIntent().getStringExtra("IN_PROPERTY_TITLE") : "";

		imgAddPicture.setImageBitmap(decodeFile(IN_PICTURE_PATH));
		if (IN_PROPERTY_TITLE.trim().length() <= 0) {
			txtPropertyTitle.setText(getString(R.string.iproperty_no_title));
		} else {
			txtPropertyTitle.setText(IN_PROPERTY_TITLE);
		}
	}

	public void setActionListeners() {
		btnSend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.iproperty_picture_preview));
				provider.addPicture(IN_PROPERTY_ID, IN_PICTURE_PATH, new WebCallListener() {
					public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
						if (responseCode == 200) {
							IjoomerApplicationConfiguration.setReloadRequired(true);
							finish();
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
	}

	public int setLayoutId() {
		return R.layout.iproperty_add_picture;
	}
}