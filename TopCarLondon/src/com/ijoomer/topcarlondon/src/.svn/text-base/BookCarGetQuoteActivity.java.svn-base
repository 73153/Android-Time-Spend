package com.ijoomer.topcarlondon.src;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;

import com.ijoomer.common.classes.IjoomerBookSuperMaster;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerTextView;

/**
 * This Class Contains All Method Related To IjoomerRegistrationStep1Activity.
 * 
 * @author tasol
 * 
 */
public class BookCarGetQuoteActivity extends IjoomerBookSuperMaster {

	
	private IjoomerTextView txtDistanceInMiles;
	private IjoomerTextView txtDistanceInMilesValue;
	private IjoomerTextView txtDistanceCost;
	private IjoomerTextView txtDistanceCostValue;
	private IjoomerTextView txtCarCost;
	private IjoomerTextView txtCarCostValue;
	private IjoomerTextView txtSurchargeCost;
	private IjoomerTextView txtSurchargeCostValue;
	private IjoomerTextView txtTotalCost;
	private IjoomerTextView txtTotalCostValue;
	private IjoomerButton btnBookNow;
	private IjoomerButton btnReset;

	private JSONObject IN_BOOK_DATA;

	/**
	 * Overrides methods
	 */

	@Override
	public int setLayoutId() {
		return R.layout.book_car_get_quote;
	}

	@Override
	public void initComponents() {
		
		txtDistanceInMiles = (IjoomerTextView) findViewById(R.id.txtDistanceInMiles);
		txtDistanceCost = (IjoomerTextView) findViewById(R.id.txtDistanceCost);
		txtCarCost = (IjoomerTextView) findViewById(R.id.txtCarCost);
		txtSurchargeCost = (IjoomerTextView) findViewById(R.id.txtSurchargeCost);
		txtTotalCost = (IjoomerTextView) findViewById(R.id.txtTotalCost);

		txtDistanceInMilesValue = (IjoomerTextView) findViewById(R.id.txtDistanceInMilesValue);
		txtDistanceCostValue = (IjoomerTextView) findViewById(R.id.txtDistanceCostValue);
		txtCarCostValue = (IjoomerTextView) findViewById(R.id.txtCarCostValue);
		txtSurchargeCostValue = (IjoomerTextView) findViewById(R.id.txtSurchargeCostValue);
		txtTotalCostValue = (IjoomerTextView) findViewById(R.id.txtTotalCostValue);
		btnBookNow = (IjoomerButton) findViewById(R.id.btnBookNow);
		btnReset = (IjoomerButton) findViewById(R.id.btnReset);
	}

	@Override
	public void prepareViews() {
		
		txtTotalCostValue.setTypeface(Typeface.DEFAULT_BOLD);
		txtDistanceInMiles.setTypeface(Typeface.DEFAULT_BOLD);
		txtDistanceCost.setTypeface(Typeface.DEFAULT_BOLD);
		txtCarCost.setTypeface(Typeface.DEFAULT_BOLD);
		txtSurchargeCost.setTypeface(Typeface.DEFAULT_BOLD);
		txtTotalCost.setTypeface(Typeface.DEFAULT_BOLD);
		getIntentData();
		setBookNowData();
	}

	@Override
	public void setActionListeners() {

		btnBookNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					loadNew(BookCarNowActivity.class, BookCarGetQuoteActivity.this, true, "IN_BOOK_DATA", IN_BOOK_DATA.toString());
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});

		btnReset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void getIntentData() {
		try {
			IN_BOOK_DATA = new JSONObject(getIntent().getStringExtra("IN_BOOK_DATA") != null ? getIntent().getStringExtra("IN_BOOK_DATA") : "");
		} catch (JSONException e) {
			IN_BOOK_DATA = new JSONObject();
			e.printStackTrace();
		}
	}

	private void setBookNowData() {
		try {
			txtDistanceInMilesValue.setText(IN_BOOK_DATA.getString("distanceInMiles"));
			txtDistanceCostValue.setText(IN_BOOK_DATA.getString("distanceCost"));
			txtCarCostValue.setText(IN_BOOK_DATA.getString("carCost"));
			txtSurchargeCostValue.setText(IN_BOOK_DATA.getString("surchargeCost"));
			txtTotalCostValue.setText(IN_BOOK_DATA.getString("cost"));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}