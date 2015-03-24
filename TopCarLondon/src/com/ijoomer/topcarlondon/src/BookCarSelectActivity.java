package com.ijoomer.topcarlondon.src;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.ijoomer.common.classes.IjoomerBookSuperMaster;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.custom.interfaces.IjoomerSharedPreferences;
import com.ijoomer.customviews.IjoomerButton;
import com.smart.framework.SmartApplication;

/**
 * This Class Contains All Method Related To IjoomerRegistrationStep1Activity.
 * 
 * @author tasol
 * 
 */
public class BookCarSelectActivity extends IjoomerBookSuperMaster {

	private ImageView imgCarSaloon;
	private ImageView imgCarEstate;
	private ImageView imgCarMpv;
	private ImageView imgCarMpvPlus;
	private ImageView imgCarSaloonSelected;
	private ImageView imgCarEstateSelected;
	private ImageView imgCarMpvSelected;
	private ImageView imgCarMpvPlusSelected;
	private IjoomerButton btnNext;

	private final String SALOON = "Saloon";
	private final String ESTATE = "Estate";
	private final String MPV = "MPV";
	private final String MPVPLUS = "MPVPlus";

	/**
	 * Overrides methods
	 */

	@Override
	public int setLayoutId() {
		return R.layout.book_car_select;
	}

	@Override
	public void initComponents() {

		imgCarSaloon = (ImageView) findViewById(R.id.imgCarSaloon);
		imgCarEstate = (ImageView) findViewById(R.id.imgCarEstate);
		imgCarMpv = (ImageView) findViewById(R.id.imgCarMpv);
		imgCarMpvPlus = (ImageView) findViewById(R.id.imgCarMpvPlus);
		imgCarSaloonSelected = (ImageView) findViewById(R.id.imgCarSaloonSelected);
		imgCarEstateSelected = (ImageView) findViewById(R.id.imgCarEstateSelected);
		imgCarMpvSelected = (ImageView) findViewById(R.id.imgCarMpvSelected);
		imgCarMpvPlusSelected = (ImageView) findViewById(R.id.imgCarMpvPlusSelected);
		btnNext = (IjoomerButton) findViewById(R.id.btnNext);
	}

	@Override
	public void prepareViews() {
		getIntentData();
	}

	private void getIntentData() {
		if (getIntent().getStringExtra("IN_LAST_CAR") != null && getIntent().getStringExtra("IN_LAST_CAR").length() > 0) {
			setLastCar(getIntent().getStringExtra("IN_LAST_CAR"));
		}
	}

	private void setLastCar(String value) {
		if (value.equals(SALOON)) {
			toggelCarSelection(R.id.imgCarSaloon);
		} else if (value.equals(ESTATE)) {
			toggelCarSelection(R.id.imgCarEstate);
		} else if (value.equals(MPV)) {
			toggelCarSelection(R.id.imgCarMpv);
		} else if (value.equals(MPVPLUS)) {
			toggelCarSelection(R.id.imgCarMpvPlus);
		}
	}

	@Override
	public void setActionListeners() {
		imgCarSaloon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				toggelCarSelection(view.getId());
			}
		});
		imgCarSaloonSelected.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				toggelCarSelection(view.getId());
			}
		});
		imgCarEstate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				toggelCarSelection(view.getId());
			}
		});
		imgCarEstateSelected.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				toggelCarSelection(view.getId());
			}
		});
		imgCarMpv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				toggelCarSelection(view.getId());
			}
		});
		imgCarMpvSelected.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				toggelCarSelection(view.getId());
			}
		});
		imgCarMpvPlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				toggelCarSelection(view.getId());
			}
		});
		imgCarMpvPlusSelected.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				toggelCarSelection(view.getId());
			}
		});

		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(IjoomerSharedPreferences.SP_SELECT_VEHICLE, "").length() > 0) {
					IjoomerApplicationConfiguration.setReloadRequired(true);
					finish();
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void toggelCarSelection(int id) {
		switch (id) {
		case R.id.imgCarSaloon:
			imgCarSaloon.setVisibility(View.GONE);
			imgCarEstateSelected.setVisibility(View.GONE);
			imgCarMpvSelected.setVisibility(View.GONE);
			imgCarMpvPlusSelected.setVisibility(View.GONE);
			imgCarSaloonSelected.setVisibility(View.VISIBLE);
			imgCarEstate.setVisibility(View.VISIBLE);
			imgCarMpv.setVisibility(View.VISIBLE);
			imgCarMpvPlus.setVisibility(View.VISIBLE);
			SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_SELECT_VEHICLE, SALOON);
			break;
		case R.id.imgCarEstate:
			imgCarEstate.setVisibility(View.GONE);
			imgCarSaloonSelected.setVisibility(View.GONE);
			imgCarMpvSelected.setVisibility(View.GONE);
			imgCarMpvPlusSelected.setVisibility(View.GONE);
			imgCarEstateSelected.setVisibility(View.VISIBLE);
			imgCarSaloon.setVisibility(View.VISIBLE);
			imgCarMpv.setVisibility(View.VISIBLE);
			imgCarMpvPlus.setVisibility(View.VISIBLE);
			SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_SELECT_VEHICLE, ESTATE);
			break;
		case R.id.imgCarMpv:
			imgCarMpv.setVisibility(View.GONE);
			imgCarSaloonSelected.setVisibility(View.GONE);
			imgCarEstateSelected.setVisibility(View.GONE);
			imgCarMpvPlusSelected.setVisibility(View.GONE);
			imgCarMpvSelected.setVisibility(View.VISIBLE);
			imgCarSaloon.setVisibility(View.VISIBLE);
			imgCarEstate.setVisibility(View.VISIBLE);
			imgCarMpvPlus.setVisibility(View.VISIBLE);
			SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_SELECT_VEHICLE, MPV);
			break;
		case R.id.imgCarMpvPlus:
			imgCarMpvPlus.setVisibility(View.GONE);
			imgCarSaloonSelected.setVisibility(View.GONE);
			imgCarEstateSelected.setVisibility(View.GONE);
			imgCarMpvSelected.setVisibility(View.GONE);
			imgCarMpvPlusSelected.setVisibility(View.VISIBLE);
			imgCarSaloon.setVisibility(View.VISIBLE);
			imgCarEstate.setVisibility(View.VISIBLE);
			imgCarMpv.setVisibility(View.VISIBLE);
			SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_SELECT_VEHICLE, MPVPLUS);
			break;
		case R.id.imgCarSaloonSelected:
			imgCarSaloon.setVisibility(View.VISIBLE);
			imgCarEstateSelected.setVisibility(View.GONE);
			imgCarMpvSelected.setVisibility(View.GONE);
			imgCarMpvPlusSelected.setVisibility(View.GONE);
			imgCarSaloonSelected.setVisibility(View.GONE);
			imgCarEstate.setVisibility(View.VISIBLE);
			imgCarMpv.setVisibility(View.VISIBLE);
			imgCarMpvPlus.setVisibility(View.VISIBLE);
			SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_SELECT_VEHICLE, "");
			break;
		case R.id.imgCarEstateSelected:
			imgCarEstate.setVisibility(View.VISIBLE);
			imgCarSaloonSelected.setVisibility(View.GONE);
			imgCarMpvSelected.setVisibility(View.GONE);
			imgCarMpvPlusSelected.setVisibility(View.GONE);
			imgCarEstateSelected.setVisibility(View.GONE);
			imgCarSaloon.setVisibility(View.VISIBLE);
			imgCarMpv.setVisibility(View.VISIBLE);
			imgCarMpvPlus.setVisibility(View.VISIBLE);
			SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_SELECT_VEHICLE, "");
			break;
		case R.id.imgCarMpvSelected:
			imgCarMpv.setVisibility(View.VISIBLE);
			imgCarSaloonSelected.setVisibility(View.GONE);
			imgCarEstateSelected.setVisibility(View.GONE);
			imgCarMpvPlusSelected.setVisibility(View.GONE);
			imgCarMpvSelected.setVisibility(View.GONE);
			imgCarSaloon.setVisibility(View.VISIBLE);
			imgCarEstate.setVisibility(View.VISIBLE);
			imgCarMpvPlus.setVisibility(View.VISIBLE);
			SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_SELECT_VEHICLE, "");
			break;
		case R.id.imgCarMpvPlusSelected:
			imgCarMpvPlus.setVisibility(View.VISIBLE);
			imgCarSaloonSelected.setVisibility(View.GONE);
			imgCarEstateSelected.setVisibility(View.GONE);
			imgCarMpvSelected.setVisibility(View.GONE);
			imgCarMpvPlusSelected.setVisibility(View.GONE);
			imgCarSaloon.setVisibility(View.VISIBLE);
			imgCarEstate.setVisibility(View.VISIBLE);
			imgCarMpv.setVisibility(View.VISIBLE);
			SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_SELECT_VEHICLE, "");
			break;

		default:
			break;
		}
	}
}