package com.ijoomer.topcarlondon.src;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.ijoomer.common.classes.IjoomerBookSuperMaster;
import com.ijoomer.common.classes.IjoomerMapAddress;
import com.ijoomer.custom.interfaces.IjoomerSharedPreferences;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerEditText;
import com.ijoomer.customviews.IjoomerRadioButton;
import com.ijoomer.customviews.IjoomerTextView;
import com.smart.framework.SmartApplication;

/**
 * This Class Contains All Method Related To IjoomerRegistrationStep1Activity.
 * 
 * @author tasol
 * 
 */
public class BookCarPickDropAddressActivity extends IjoomerBookSuperMaster {

	private LinearLayout lnrPostCodeAddress;
	private LinearLayout lnrAirportAddress;
	private LinearLayout lnrMain;
	
	private ImageView imgPostCodeMap;
	private IjoomerTextView txtAddressTitle;
	private IjoomerEditText edtPostCodeAddress;
	private IjoomerEditText edtPostCodeAddressPostCode;

	private IjoomerRadioButton rdbAirportAddress;
	private IjoomerRadioButton rdbPostcode;
	private IjoomerRadioButton rdbLcy;
	private IjoomerRadioButton rdbLgwn;
	private IjoomerRadioButton rdbLgws;
	private IjoomerRadioButton rdbLhrtOne;
	private IjoomerRadioButton rdbLhrtThree;
	private IjoomerRadioButton rdbLhrtFour;
	private IjoomerRadioButton rdbLhrtFive;
	private IjoomerRadioButton rdbLtn;
	private IjoomerRadioButton rdbStn;

	private RadioGroup rdgAirportAddress;
	private IjoomerButton btnNext;

	private final int GET_POSTAL = 1;

	private final String PICKUP_ADDRESS = "pickUpAddress";
	private final String DROPOFF_ADDRESS = "dropOffAddress";
	private String currentAddress;

	/**
	 * Overrides methods
	 */

	@Override
	public int setLayoutId() {
		return R.layout.book_car_pick_drop_address;
	}

	@Override
	public void initComponents() {

		lnrPostCodeAddress = (LinearLayout) findViewById(R.id.lnrPostCodeAddress);
		lnrAirportAddress = (LinearLayout) findViewById(R.id.lnrAirportAddress);
		lnrMain = (LinearLayout) findViewById(R.id.lnrMain);
		
		imgPostCodeMap = (ImageView) findViewById(R.id.imgPostCodeMap);
		txtAddressTitle = (IjoomerTextView) findViewById(R.id.txtAddressTitle);
		edtPostCodeAddress = (IjoomerEditText) findViewById(R.id.edtPostCodeAddress);
		edtPostCodeAddressPostCode = (IjoomerEditText) findViewById(R.id.edtPostCodeAddressPostCode);
		rdbAirportAddress = (IjoomerRadioButton) findViewById(R.id.rdbAirportAddress);
		rdbPostcode = (IjoomerRadioButton) findViewById(R.id.rdbPostcode);
		rdbAirportAddress = (IjoomerRadioButton) findViewById(R.id.rdbAirportAddress);
		rdbLcy = (IjoomerRadioButton) findViewById(R.id.rdbLcy);
		rdbLgwn = (IjoomerRadioButton) findViewById(R.id.rdbLgwn);
		rdbLgws = (IjoomerRadioButton) findViewById(R.id.rdbLgws);
		rdbLhrtOne = (IjoomerRadioButton) findViewById(R.id.rdbLhrtOne);
		rdbLhrtThree = (IjoomerRadioButton) findViewById(R.id.rdbLhrtThree);
		rdbLhrtFour = (IjoomerRadioButton) findViewById(R.id.rdbLhrtFour);
		rdbLhrtFive = (IjoomerRadioButton) findViewById(R.id.rdbLhrtFive);
		rdbLtn = (IjoomerRadioButton) findViewById(R.id.rdbLtn);
		rdbStn = (IjoomerRadioButton) findViewById(R.id.rdbStn);
		rdgAirportAddress = (RadioGroup) findViewById(R.id.rdgAirportAddress);
		btnNext = (IjoomerButton) findViewById(R.id.btnNext);
	}

	@Override
	public void prepareViews() {

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (getIntent().getStringExtra("IN_ADDRESS_TYPE") != null) {
			currentAddress = getIntent().getStringExtra("IN_ADDRESS_TYPE").equals(PICKUP_ADDRESS) ? PICKUP_ADDRESS : DROPOFF_ADDRESS;
		} else {
			currentAddress = PICKUP_ADDRESS;
		}
		setLastValue();
	}


	private void setLastValue() {
		if (currentAddress.equals(PICKUP_ADDRESS)) {
			if (getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS).length() > 0) {
				setAirportLastValue(getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS), getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS_CODE));
			}
		} else {
			if (getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS).length() > 0) {
				setAirportLastValue(getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS), getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS_CODE));
			}
		}
	}
	
	private String getSharedData(String key) {
		if (key.equals(IjoomerSharedPreferences.SP_BABY_SEAT) || key.equals(IjoomerSharedPreferences.SP_PASSENGER_SEAT)) {
			return "" + SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getInt(key, 0);
		} else {
			return SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(key, "");
		}
	}

	private void setAirportLastValue(String value, String postCode) {
		if (value.equals(rdbLcy.getText().toString())) {
			rdbLcy.setChecked(true);
			showAirport();
		} else if (value.equals(rdbLgwn.getText().toString())) {
			rdbLgwn.setChecked(true);
			showAirport();
		} else if (value.equals(rdbLgws.getText().toString())) {
			rdbLgws.setChecked(true);
			showAirport();
		} else if (value.equals(rdbLhrtFive.getText().toString())) {
			rdbLhrtFive.setChecked(true);
			showAirport();
		} else if (value.equals(rdbLhrtFour.getText().toString())) {
			rdbLhrtFour.setChecked(true);
			showAirport();
		} else if (value.equals(rdbLhrtOne.getText().toString())) {
			rdbLhrtOne.setChecked(true);
			showAirport();
		} else if (value.equals(rdbLhrtThree.getText().toString())) {
			rdbLhrtThree.setChecked(true);
			showAirport();
		} else if (value.equals(rdbLtn.getText().toString())) {
			rdbLtn.setChecked(true);
			showAirport();
		} else if (value.equals(rdbStn.getText().toString())) {
			rdbStn.setChecked(true);
			showAirport();
		} else if (value.length() > 0 || postCode.length() > 0) {
			edtPostCodeAddress.setText(value);
			edtPostCodeAddressPostCode.setText(postCode);
			showPostCode();
		} else {
			showAirport();
		}

	}

	private String getAirpotAddress() {
		if (rdgAirportAddress.getCheckedRadioButtonId() == rdbLcy.getId()) {
			return rdbLcy.getText().toString();
		} else if (rdgAirportAddress.getCheckedRadioButtonId() == rdbLgwn.getId()) {
			return rdbLgwn.getText().toString();
		} else if (rdgAirportAddress.getCheckedRadioButtonId() == rdbLgws.getId()) {
			return rdbLgws.getText().toString();
		} else if (rdgAirportAddress.getCheckedRadioButtonId() == rdbLhrtFive.getId()) {
			return rdbLhrtFive.getText().toString();
		} else if (rdgAirportAddress.getCheckedRadioButtonId() == rdbLhrtFour.getId()) {
			return rdbLhrtFour.getText().toString();
		} else if (rdgAirportAddress.getCheckedRadioButtonId() == rdbLhrtOne.getId()) {
			return rdbLhrtOne.getText().toString();
		} else if (rdgAirportAddress.getCheckedRadioButtonId() == rdbLhrtThree.getId()) {
			return rdbLhrtThree.getText().toString();
		} else if (rdgAirportAddress.getCheckedRadioButtonId() == rdbLtn.getId()) {
			return rdbLtn.getText().toString();
		} else if (rdgAirportAddress.getCheckedRadioButtonId() == rdbStn.getId()) {
			return rdbStn.getText().toString();
		} else {
			return "";
		}
	}

	private void resetAirportRadio() {
		rdbLcy.setChecked(false);
		rdbLgwn.setChecked(false);
		rdbLgws.setChecked(false);
		rdbLhrtFive.setChecked(false);
		rdbLhrtFour.setChecked(false);
		rdbLhrtOne.setChecked(false);
		rdbLhrtThree.setChecked(false);
		rdbLtn.setChecked(false);
		rdbStn.setChecked(false);
	}

	private void resetPostCode() {
		edtPostCodeAddress.setText("");
		edtPostCodeAddressPostCode.setText("");
	}

	@Override
	public void setActionListeners() {

		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (currentAddress.equals(PICKUP_ADDRESS)) {
					if (lnrAirportAddress.getVisibility() == View.VISIBLE) {
						if (getAirpotAddress().length() > 0) {
							SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_PICKUP_ADDRESS, getAirpotAddress());
							SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_PICKUP_ADDRESS_TYPE, "airports");
							SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_PICKUP_ADDRESS_CODE, "");
							currentAddress = DROPOFF_ADDRESS;
							txtAddressTitle.setText(getString(R.string.drop_off_address));
							resetAirportRadio();
							resetPostCode();
							setLastValue();
						}
					} else {
						if (edtPostCodeAddress.getText().toString().length() > 0) {
							SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_PICKUP_ADDRESS, edtPostCodeAddress.getText().toString());
							SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_PICKUP_ADDRESS_TYPE, "postcodes");
							SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_PICKUP_ADDRESS_CODE, edtPostCodeAddressPostCode.getText().toString());
							currentAddress = DROPOFF_ADDRESS;
							txtAddressTitle.setText(getString(R.string.drop_off_address));
							resetAirportRadio();
							resetPostCode();
							setLastValue();
						}
					}
					applyRotation(1, 0, 90);
				} else {
					if (lnrAirportAddress.getVisibility() == View.VISIBLE) {
						if (getAirpotAddress().length() > 0) {
							SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS, getAirpotAddress());
							SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS_TYPE, "airports");
							SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS_CODE, "");
							try {
								loadNew(BookCarSelectActivity.class, BookCarPickDropAddressActivity.this, true, "IN_LAST_CAR", getSharedData(IjoomerSharedPreferences.SP_SELECT_VEHICLE));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else {
						if (edtPostCodeAddress.getText().toString().length() > 0) {
							SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS, edtPostCodeAddress.getText().toString());
							SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS_TYPE, "postcodes");
							SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS_CODE, edtPostCodeAddressPostCode.getText().toString());
							try {
								loadNew(BookCarSelectActivity.class, BookCarPickDropAddressActivity.this, true, "IN_LAST_CAR", getSharedData(IjoomerSharedPreferences.SP_SELECT_VEHICLE));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		});

		rdbAirportAddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showAirport();
			}
		});

		rdbPostcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPostCode();
			}
		});

		imgPostCodeMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					loadNewResult(IjoomerMapAddress.class, BookCarPickDropAddressActivity.this, GET_POSTAL, "IN_LAST_ADDRESS", edtPostCodeAddress.getText().toString().trim());
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	private void applyRotation(int position, float start, float end) {
		final float centerX = lnrMain.getWidth() / 2.0f;
		final float centerY = lnrMain.getHeight() / 2.0f;

		final Rotate rotation = new Rotate(start, end, centerX, centerY, 310.0f, true);
		rotation.setDuration(200);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(position));

		lnrMain.startAnimation(rotation);
	}

	private final class DisplayNextView implements Animation.AnimationListener {
		private final int mPosition;

		private DisplayNextView(int position) {
			mPosition = position;
		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			lnrMain.post(new SwapViews(mPosition));
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	private final class SwapViews implements Runnable {
		private final int mPosition;

		public SwapViews(int position) {
			mPosition = position;
		}

		public void run() {
			final float centerX = lnrMain.getWidth() / 2.0f;
			final float centerY = lnrMain.getHeight() / 2.0f;
			Rotate rotation;

			if (mPosition > -1)
				rotation = new Rotate(270, 360, centerX, centerY, 310.0f, false);
			else
				rotation = new Rotate(90, 0, centerY, centerX, 310.0f, false);

			rotation.setDuration(200);
			rotation.setFillAfter(true);
			rotation.setInterpolator(new DecelerateInterpolator());

			lnrMain.startAnimation(rotation);
		}
	}

	private void showPostCode() {
		lnrPostCodeAddress.setVisibility(View.VISIBLE);
		lnrAirportAddress.setVisibility(View.GONE);
		rdbPostcode.setChecked(true);
		rdbAirportAddress.setChecked(false);
		((android.widget.LinearLayout.LayoutParams) rdbPostcode.getLayoutParams()).setMargins(0, 0, 0, 0);
		((android.widget.LinearLayout.LayoutParams) rdbAirportAddress.getLayoutParams()).setMargins(0, 0, -convertSizeToDeviceDependent(5), 0);
	}

	private void showAirport() {
		lnrPostCodeAddress.setVisibility(View.GONE);
		lnrAirportAddress.setVisibility(View.VISIBLE);
		rdbPostcode.setChecked(false);
		rdbAirportAddress.setChecked(true);
		((android.widget.LinearLayout.LayoutParams) rdbAirportAddress.getLayoutParams()).setMargins(0, 0, 0, 0);
		((android.widget.LinearLayout.LayoutParams) rdbPostcode.getLayoutParams()).setMargins(-convertSizeToDeviceDependent(5), 0, 0, 0);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == GET_POSTAL) {
				edtPostCodeAddress.setText(((HashMap<String, String>) data.getSerializableExtra("MAP_ADDRESSS_DATA")).get("address"));
				edtPostCodeAddressPostCode.setText(((HashMap<String, String>) data.getSerializableExtra("MAP_ADDRESSS_DATA")).get("postalcode"));
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}