package com.ijoomer.topcarlondon.src;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ijoomer.common.classes.IjoomerBookSuperMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.custom.interfaces.IjoomerSharedPreferences;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerEditText;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.SmartApplication;

/**
 * This Class Contains All Method Related To IjoomerRegistrationStep1Activity.
 * 
 * @author tasol
 * 
 */
public class BookCarContactUsActivity extends IjoomerBookSuperMaster {

	private IjoomerEditText edtYourName;
	private IjoomerEditText edtYourEmail;
	private IjoomerEditText edtYourPhone;
	private IjoomerEditText edtYourMessage;
	private IjoomerButton btnSendMessage;

	private ImageView imgCall;
	private ImageView imgEmail;
	private ImageView imgMap;

	private ProgressBar pbrSendMessage;
	private BookCarDataProvider provide;

	/**
	 * Overrides methods
	 */

	@Override
	public int setLayoutId() {
		return R.layout.book_car_contact_us;
	}

	@Override
	public void initComponents() {

		edtYourName = (IjoomerEditText) findViewById(R.id.edtYourName);
		edtYourEmail = (IjoomerEditText) findViewById(R.id.edtYourEmail);
		edtYourPhone = (IjoomerEditText) findViewById(R.id.edtYourPhone);
		edtYourMessage = (IjoomerEditText) findViewById(R.id.edtYourMessage);
		btnSendMessage = (IjoomerButton) findViewById(R.id.btnSendMessage);
		pbrSendMessage = (ProgressBar) findViewById(R.id.pbrSendMessage);
		imgCall = (ImageView) findViewById(R.id.imgCall);
		imgEmail = (ImageView) findViewById(R.id.imgEmail);
		imgMap = (ImageView) findViewById(R.id.imgMap);

		provide = new BookCarDataProvider(this);
	}

	@Override
	public void prepareViews() {
		getLastData();
	}

	private void getLastData() {
		edtYourName.setText(getSharedData(IjoomerSharedPreferences.SP_FIRSTNAME));
		edtYourEmail.setText(getSharedData(IjoomerSharedPreferences.SP_EMAIL));
		edtYourPhone.setText(getSharedData(IjoomerSharedPreferences.SP_CONTACTNO));
	}

	@Override
	public void setActionListeners() {

		btnSendMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				boolean flag = true;
				if (edtYourName.getText().toString().length() <= 0) {
					flag = false;
					edtYourName.setError(getString(R.string.validation_value_required));
				} else if (edtYourEmail.getText().toString().length() <= 0) {
					flag = false;
					edtYourEmail.setError(getString(R.string.validation_value_required));
				} else if (edtYourPhone.getText().toString().length() <= 0) {
					flag = false;
					edtYourPhone.setError(getString(R.string.validation_value_required));
				} else if (edtYourMessage.getText().toString().length() <= 0) {
					flag = false;
					edtYourMessage.setError(getString(R.string.validation_value_required));
				}

				if (flag) {
					pbrSendMessage.setVisibility(View.VISIBLE);
					storeSharedData();
					provide.sendMessage(edtYourName.getText().toString().trim(), edtYourEmail.getText().toString().trim(), edtYourPhone.getText().toString().trim(), edtYourMessage.getText().toString().trim(), new WebCallListener() {

						@Override
						public void onProgressUpdate(int progressCount) {

						}

						@Override
						public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
							pbrSendMessage.setVisibility(View.GONE);
							if (responseCode == 200) {
								IjoomerUtilities.getCustomOkDialog(getString(R.string.contactus), getString(R.string.message_send_successfully),
										getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

											@Override
											public void NeutralMethod() {
											}
										});
							} else {
								responseMessageHandler(responseCode, true);
							}
						}
					});
				}

			}
		});
		
		imgCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent call = new Intent(Intent.ACTION_DIAL);
				call.setData(Uri.parse("tel:" + getString(R.string.top_car_london_phone_no)));
				startActivity(call);
			}
		});
		
		imgEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 Intent i = new Intent(Intent.ACTION_SEND);
			        i.setType("text/html");
			        i.putExtra(Intent.EXTRA_EMAIL, new String[] {getString(R.string.top_car_london_email)});
			        try {
			            startActivity(Intent.createChooser(i, "Send mail..."));
			        }catch(android.content.ActivityNotFoundException ex) {
			            ting(getString(R.string.share_email_no_client));
			        }
			}
		});
		
		imgMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				loadNew(BookCarContactUsMapActivity.class, BookCarContactUsActivity.this, false);
			}
		});

	}
	
	private void storeSharedData() {
		SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_FIRSTNAME, edtYourName.getText().toString().trim());
		SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_CONTACTNO, edtYourPhone.getText().toString().trim());
		SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_EMAIL, edtYourEmail.getText().toString().trim());
	}

	private String getSharedData(String key) {
		return SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(key, "");
	}
	
	private void responseMessageHandler(final int responseCode, final boolean finishActivityOnConnectionProblem) {

		IjoomerUtilities.getCustomOkDialog(getString(R.string.area_covered), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
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