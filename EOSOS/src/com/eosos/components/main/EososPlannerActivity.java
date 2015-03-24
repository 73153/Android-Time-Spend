package com.eosos.components.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;

import com.eosos.customviews.IjoomerAutoCompleteTextView;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.library.EososDataProvider;
import com.eosos.src.R;

public class EososPlannerActivity extends EososMasterActivity {

	private IjoomerAutoCompleteTextView edtCity;
	private final String NAME = "name";
	private final String VALUE = "value";
	private Spinner spinnerNight;
	private IjoomerTextView txtPlan;
	EososDataProvider dataProvider;
	private ArrayList<HashMap<String, String>> autoCompleteResultList;
	ArrayList<String> nightList;
	private String IN_PARENT_ID = "55", IN_SECTION_ID = "54";
	private String IN_FEATUREDFIRST = "No";
	private boolean validateCity = false;
	private ArrayList<HashMap<String, String>> plannerField;
	private ArrayList<HashMap<String, String>> cities;

	@Override
	public int setLayoutId() {
		return R.layout.eosos_planner;
	}

	@Override
	public void initComponents() {
		edtCity = (IjoomerAutoCompleteTextView) findViewById(R.id.edtCity);
		spinnerNight = (Spinner) findViewById(R.id.spnSelectNight);
        txtPlan = (IjoomerTextView) findViewById(R.id.txtPlan);
		dataProvider = new EososDataProvider(EososPlannerActivity.this);
		String[] nightArray = getResources().getStringArray(R.array.nights);
		nightList = new ArrayList<String>(Arrays.asList(nightArray));
		plannerField = new ArrayList<HashMap<String, String>>();
	}

	@Override
	public void prepareViews() {
		// getIntentData();
		cities = dataProvider.getCityList();
		((IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader)).setText(getString(R.string.planner));

		edtCity.setAdapter(new autoCompleteAdapter(this, R.layout.ijoomer_spinner_dropdown_item));
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.ijoomer_spinner_item, nightList);
		dataAdapter.setDropDownViewResource(R.layout.ijoomer_spinner_dropdown_item);
		spinnerNight.setAdapter(dataAdapter);
	}

    @Override
    protected void onResume() {
        super.onResume();
        txtPlan.setTextColor(getResources().getColor(R.color.txt_color));
    }

    @Override
	public void setActionListeners() {

		edtCity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// edtCity.requestFocus();
				// edtCity.setFocusable(true);
				edtCity.setText("");
				validateCity = false;
				edtCity.setFocusable(true);
				edtCity.setFocusableInTouchMode(true);

			}
		});

        txtPlan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                txtPlan.setTextColor(getResources().getColor(R.color.red));
				if (edtCity.getText().toString().trim().length() == 0) {
					edtCity.setError(getResources().getString(R.string.validation_value_required));
                    txtPlan.setTextColor(getResources().getColor(R.color.txt_color));
				} else if (!validateCity) {
					edtCity.setError("No match found");
                    txtPlan.setTextColor(getResources().getColor(R.color.txt_color));
				} else {
					edtCity.setError(null);
					try {
						plannerField=new ArrayList<HashMap<String,String>>();
						HashMap<String, String> field = new HashMap<String, String>();

						field.put(NAME, "field_" + nightList.get(spinnerNight.getSelectedItemPosition()).toLowerCase());
						field.put(VALUE, "1");
						plannerField.add(field);
						field = new HashMap<String, String>();

						field.put(NAME, "field_city");
						field.put(VALUE, edtCity.getText().toString());

						plannerField.add(field);

						loadNew(EososSearchResultActivity.class, EososPlannerActivity.this, false, "IN_FROM_PLANNER", true, "IN_FIELD", plannerField);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public void getIntentData() {
		if (getSmartApplication().readSharedPreferences().getString(SP_DEFAULT_LANDING_SCREEN, "").length() > 0) {
			try {
				JSONObject intentData = new JSONObject(getSmartApplication().readSharedPreferences().getString(SP_LAST_ACTIVITY_INTENT, "")).getJSONObject("itemdata");
				IN_PARENT_ID = intentData.getString("categoryID");
				IN_SECTION_ID = intentData.getString("sectionID");
				IN_FEATUREDFIRST = intentData.getString("featuredFirst");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	private class autoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
		private ArrayList<String> autoCompleteResultList;

		public autoCompleteAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
		}

		@Override
		public int getCount() {
			return autoCompleteResultList.size();
		}

		@Override
		public String getItem(int index) {
			try {
				validateCity = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return autoCompleteResultList.get(index);
		}

		@Override
		public Filter getFilter() {
			Filter filter = new Filter() {
				@Override
				protected FilterResults performFiltering(final CharSequence constraint) {
					final FilterResults filterResults = new FilterResults();
					autoCompleteResultList = new ArrayList<String>();
					if (constraint != null) {

						for (HashMap<String, String> city : cities) {
							if (city.get("value").toLowerCase().contains(constraint.toString().toLowerCase())) {
								autoCompleteResultList.add(city.get("value"));
							}
						}

						filterResults.values = autoCompleteResultList;
						filterResults.count = autoCompleteResultList.size();
					}
					return filterResults;

				}

				@Override
				protected void publishResults(CharSequence constraint, FilterResults results) {
					if (results != null && results.count > 0) {
						notifyDataSetChanged();
					} else {
						notifyDataSetInvalidated();
					}
				}
			};
			return filter;
		}
	}

}
