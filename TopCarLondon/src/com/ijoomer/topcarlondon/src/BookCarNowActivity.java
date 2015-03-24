package com.ijoomer.topcarlondon.src;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView.BufferType;

import com.ijoomer.common.classes.IjoomerBookSuperMaster;
import com.ijoomer.common.classes.IjoomerSpannable;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.custom.interfaces.CustomDateClickListner;
import com.ijoomer.custom.interfaces.IjoomerSharedPreferences;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerCheckBox;
import com.ijoomer.customviews.IjoomerEditText;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.SmartApplication;

/**
 * This Class Contains All Method Related To IjoomerRegistrationStep1Activity.
 * 
 * @author tasol
 * 
 */
public class BookCarNowActivity extends IjoomerBookSuperMaster {

	private IjoomerEditText edtJourneyDateTime;
	private IjoomerEditText edtFlightNo;
	private IjoomerEditText edtFirstName;
	private IjoomerEditText edtLastName;
	private IjoomerEditText edtAddress;
	private IjoomerEditText edtContactNo;
	private IjoomerEditText edtEmail;

	private IjoomerTextView txtTermsAcceptance;

	private IjoomerCheckBox icbTermsAcceptance;

	private ProgressBar pbrBookNow;

	private IjoomerButton btnBookNow;

	private BookCarDataProvider provider;

	private JSONObject IN_BOOK_DATA;

	/**
	 * Overrides methods
	 */

	@Override
	public int setLayoutId() {
		return R.layout.book_car_now;
	}

	@Override
	public void initComponents() {


		edtJourneyDateTime = (IjoomerEditText) findViewById(R.id.edtJourneyDateTime);
		edtFlightNo = (IjoomerEditText) findViewById(R.id.edtFlightNo);
		edtFirstName = (IjoomerEditText) findViewById(R.id.edtFirstName);
		edtLastName = (IjoomerEditText) findViewById(R.id.edtLastName);
		edtAddress = (IjoomerEditText) findViewById(R.id.edtAddress);
		edtContactNo = (IjoomerEditText) findViewById(R.id.edtContactNo);
		edtEmail = (IjoomerEditText) findViewById(R.id.edtEmail);
		edtJourneyDateTime = (IjoomerEditText) findViewById(R.id.edtJourneyDateTime);

		txtTermsAcceptance = (IjoomerTextView) findViewById(R.id.txtTermsAcceptance);
		txtTermsAcceptance.setMovementMethod(new LinkMovementMethod());
		txtTermsAcceptance.setText(addClickablePart(Html.fromHtml(getString(R.string.terms_n_condition_first) + "  " + getString(R.string.terms_n_condition_second))), BufferType.SPANNABLE);
		btnBookNow = (IjoomerButton) findViewById(R.id.btnBookNow);
		icbTermsAcceptance = (IjoomerCheckBox) findViewById(R.id.icbTermsAcceptance);
		pbrBookNow = (ProgressBar) findViewById(R.id.pbrBookNow);
		provider = new BookCarDataProvider(this);
	}

	@Override
	public void prepareViews() {
		getIntentData();
		setBookNowData();
	}

	@Override
	public void setActionListeners() {

		edtJourneyDateTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				IjoomerUtilities.getDateTimeDialog(edtJourneyDateTime.getText().toString().length() > 0 ? edtJourneyDateTime.getText().toString() : "", new CustomDateClickListner() {

					@Override
					public void onClick(String value, Calendar calnder) {
						edtJourneyDateTime.setText(value);
						edtJourneyDateTime.setTag(calnder);
					}
				});
			}
		});

		btnBookNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				boolean flag = true;
				if (edtJourneyDateTime.getText().toString().trim().length() <= 0) {
					flag = false;
					edtJourneyDateTime.setError(getString(R.string.validation_value_required));
				} else if (edtFirstName.getText().toString().trim().length() <= 0) {
					flag = false;
					edtFirstName.setError(getString(R.string.validation_value_required));
				} else if (edtLastName.getText().toString().trim().length() <= 0) {
					flag = false;
					edtLastName.setError(getString(R.string.validation_value_required));
				} else if (edtAddress.getText().toString().trim().length() <= 0) {
					flag = false;
					edtAddress.setError(getString(R.string.validation_value_required));
				} else if (edtContactNo.getText().toString().trim().length() <= 0) {
					flag = false;
					edtContactNo.setError(getString(R.string.validation_value_required));
				} else if (edtEmail.getText().toString().trim().length() <= 0) {
					flag = false;
					edtEmail.setError(getString(R.string.validation_value_required));
				} else if (!icbTermsAcceptance.isChecked()) {
					flag = false;
					ting(getString(R.string.please_confirm_terms_conditions));
				}else if (!IjoomerUtilities.emailValidator(edtEmail.getText().toString().trim())) {
					flag = false;
					edtEmail.setError(getString(R.string.validation_invalid_email));
				}

				if (flag) {
					pbrBookNow.setVisibility(View.VISIBLE);
					storeSharedData();
					try {
						provider.submitBookNow(edtJourneyDateTime.getText().toString().trim(), IN_BOOK_DATA.getString("pickAddress").trim(), IN_BOOK_DATA.getString("pickAddressPostCode").trim(),IN_BOOK_DATA.getString("dropoffAddress").trim(), IN_BOOK_DATA.getString("dropoffAddressPostCode").trim(), edtFlightNo.getText().toString().trim(), IN_BOOK_DATA.getString("vehicle").trim(), IN_BOOK_DATA.getString("babySeat").trim(),
								IN_BOOK_DATA.getString("passenger").trim(), IN_BOOK_DATA.getString("distanceInMiles").trim(), IN_BOOK_DATA.getString("cost").trim(), edtFirstName.getText().toString().trim(), edtLastName.getText().toString().trim(),
								edtAddress.getText().toString().trim(), edtContactNo.getText().toString().trim(), edtEmail.getText().toString().trim(), IN_BOOK_DATA.getString("searchType"), IN_BOOK_DATA.getString("pickUpAddressType"),IN_BOOK_DATA.getString("dropOffAddressType"), new WebCallListener() {

									@Override
									public void onProgressUpdate(int progressCount) {

									}

									@Override
									public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
										pbrBookNow.setVisibility(View.GONE);
										if (responseCode == 200) {
											try {
												loadNew(BookCarMyBookingActivity.class, BookCarNowActivity.this, true, "IN_MYBOOKING_DATA", data1.get(0));
											} catch (Throwable e) {
												e.printStackTrace();
											}
										} else {
											responseMessageHandler(responseCode, true);
										}
									}
								});
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	private void storeSharedData() {
		SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_FIRSTNAME, edtFirstName.getText().toString().trim());
		SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_LASTNAME, edtLastName.getText().toString().trim());
		SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_ADDRESS, edtAddress.getText().toString().trim());
		SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_CONTACTNO, edtContactNo.getText().toString().trim());
		SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_EMAIL, edtEmail.getText().toString().trim());
	}

	private String getSharedData(String key) {
		return SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(key, "");
	}

	private void getIntentData() {
		try {
			IN_BOOK_DATA = new JSONObject(getIntent().getStringExtra("IN_BOOK_DATA") != null ? getIntent().getStringExtra("IN_BOOK_DATA") : "");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void setBookNowData() {
			edtFirstName.setText(getSharedData(IjoomerSharedPreferences.SP_FIRSTNAME));
			edtLastName.setText(getSharedData(IjoomerSharedPreferences.SP_LASTNAME));
			edtAddress.setText(getSharedData(IjoomerSharedPreferences.SP_ADDRESS));
			edtContactNo.setText(getSharedData(IjoomerSharedPreferences.SP_CONTACTNO));
			edtEmail.setText(getSharedData(IjoomerSharedPreferences.SP_EMAIL));
	}

	public SpannableStringBuilder addClickablePart(Spanned strSpanned) {
		String str = strSpanned.toString();
		SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

		if (str.contains(getString(R.string.terms_n_condition_second))) {
			ssb.setSpan(new IjoomerSpannable(Color.parseColor(getString(R.color.yellow)), true) {

				@Override
				public void onClick(View widget) {
					pbrBookNow.setVisibility(View.VISIBLE);
					provider.getTermsAcceptance(new WebCallListener() {

						@Override
						public void onProgressUpdate(int progressCount) {
						}

						@Override
						public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
							pbrBookNow.setVisibility(View.GONE);
							if (responseCode == 200) {
								IjoomerUtilities.getTermsNConditionDialog(getString(R.string.terms_n_condition_second), data1.get(0).get("content"));
							} else {
								responseMessageHandler(responseCode, false);
							}
						}
					});
				}
			}, str.indexOf(getString(R.string.terms_n_condition_second)), str.indexOf(getString(R.string.terms_n_condition_second)) + getString(R.string.terms_n_condition_second).length(), 0);

		}
		return ssb;

	}

	private void responseMessageHandler(final int responseCode, final boolean finishActivityOnConnectionProblem) {

		IjoomerUtilities.getCustomOkDialog(getString(R.string.area_covered), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

			@Override
			public void NeutralMethod() {
				if (responseCode == 599 && finishActivityOnConnectionProblem) {
					finish();
				}
			}
		});

	}

}