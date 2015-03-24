package com.ijoomer.topcarlondon.src;

import java.util.HashMap;

import com.ijoomer.common.classes.IjoomerBookSuperMaster;
import com.ijoomer.customviews.IjoomerTextView;

/**
 * This Class Contains All Method Related To IjoomerRegistrationStep1Activity.
 * 
 * @author tasol
 * 
 */
public class BookCarAreaCoverdDetailActivity extends IjoomerBookSuperMaster {

	private IjoomerTextView txtAreadCoverNameValue;
	private IjoomerTextView txtAreadCoverDescriptionValue;
	private IjoomerTextView txtAreadCoverAirportRouteValue;
	private IjoomerTextView txtAreadCoverHotelsValue;

	private HashMap<String, String> IN_AREA_COVERD_DATA;


	/**
	 * Overrides methods
	 */

	@Override
	public int setLayoutId() {
		return R.layout.book_car_area_coverd_details;
	}

	@Override
	public void initComponents() {

		txtAreadCoverNameValue = (IjoomerTextView) findViewById(R.id.txtAreadCoverNameValue);
		txtAreadCoverDescriptionValue = (IjoomerTextView) findViewById(R.id.txtAreadCoverDescriptionValue);
		txtAreadCoverAirportRouteValue = (IjoomerTextView) findViewById(R.id.txtAreadCoverAirportRouteValue);
		txtAreadCoverHotelsValue = (IjoomerTextView) findViewById(R.id.txtAreadCoverHotelsValue);
	}

	@Override
	public void prepareViews() {
		getIntentData();
		setAreadCoverdDetail();

	}


	@Override
	public void setActionListeners() {

	}

	@SuppressWarnings("unchecked")
	private void getIntentData() {
		IN_AREA_COVERD_DATA = (HashMap<String, String>) (getIntent().getSerializableExtra("IN_AREA_COVERD_DATA") != null ? getIntent().getSerializableExtra("IN_AREA_COVERD_DATA") : new HashMap<String, String>());
	}

	private void setAreadCoverdDetail() {

		try {
			txtAreadCoverNameValue.setText(IN_AREA_COVERD_DATA.get("area_name"));
			txtAreadCoverDescriptionValue.setText(IN_AREA_COVERD_DATA.get("description"));
			txtAreadCoverAirportRouteValue.setText(IN_AREA_COVERD_DATA.get("airport_route"));
			txtAreadCoverHotelsValue.setText(IN_AREA_COVERD_DATA.get("hotels"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}