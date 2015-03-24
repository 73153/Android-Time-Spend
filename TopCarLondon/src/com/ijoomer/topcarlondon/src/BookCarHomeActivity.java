package com.ijoomer.topcarlondon.src;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.ijoomer.common.classes.IjoomerBookSuperMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.classes.IjoomerWebviewClient;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.custom.interfaces.IjoomerSharedPreferences;
import com.ijoomer.customviews.IjoomerButton;
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
public class BookCarHomeActivity extends IjoomerBookSuperMaster {

	private LinearLayout lnrPickUp;
	private LinearLayout lnrDropOff;
	private LinearLayout lnrSelectVehicle;
	private LinearLayout lnrNoOfBabySeats;
	private LinearLayout lnrNoOfPassenger;
	private IjoomerTextView txtPickUp;
	private IjoomerTextView txtDropOff;
	private IjoomerTextView txtSelectVehicle;
	private IjoomerTextView txtNoOfBabySeatsValue;
	private IjoomerTextView txtNoOfPassengerValue;

	private IjoomerButton btnGetQuote;
	private Spinner spnNoOfBabySeats;
	private Spinner spnNoOfPassenger;
	private ProgressBar pbrGetQuote;


	private BookCarDataProvider provider;

	/**
	 * Overrides methods
	 */

	@Override
	public int setLayoutId() {
		return R.layout.book_car_home;
	}

	@Override
	public void initComponents() {

		lnrPickUp = (LinearLayout) findViewById(R.id.lnrPickUp);
		lnrDropOff = (LinearLayout) findViewById(R.id.lnrDropOff);
		lnrSelectVehicle = (LinearLayout) findViewById(R.id.lnrSelectVehicle);
		lnrNoOfBabySeats = (LinearLayout) findViewById(R.id.lnrNoOfBabySeats);
		lnrNoOfPassenger = (LinearLayout) findViewById(R.id.lnrNoOfPassenger);
		txtPickUp = (IjoomerTextView) findViewById(R.id.txtPickUp);
		txtDropOff = (IjoomerTextView) findViewById(R.id.txtDropOff);
		txtSelectVehicle = (IjoomerTextView) findViewById(R.id.txtSelectVehicle);
		txtNoOfBabySeatsValue = (IjoomerTextView) findViewById(R.id.txtNoOfBabySeatsValue);
		txtNoOfPassengerValue = (IjoomerTextView) findViewById(R.id.txtNoOfPassengerValue);
		btnGetQuote = (IjoomerButton) findViewById(R.id.btnGetQuote);
		spnNoOfBabySeats = (Spinner) findViewById(R.id.spnNoOfBabySeats);
		spnNoOfPassenger = (Spinner) findViewById(R.id.spnNoOfPassenger);
		pbrGetQuote = (ProgressBar) findViewById(R.id.pbrGetQuote);
		provider = new BookCarDataProvider(this);

	}

	@Override
	public void prepareViews() {

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.no_of_baby_seats_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnNoOfBabySeats.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.book_car_spinner_no_item_selected, this));

	}

	@Override
	protected void onResume() {
		super.onResume();

		
		if (getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS).length() > 0) {
			txtPickUp.setText(getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS));
		}
		if (getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS).length() > 0) {
			txtDropOff.setText(getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS));
		}
		if (getSharedData(IjoomerSharedPreferences.SP_SELECT_VEHICLE).length() > 0) {
			txtSelectVehicle.setText(getSharedData(IjoomerSharedPreferences.SP_SELECT_VEHICLE));
		}
		if (Integer.valueOf(getSharedData(IjoomerSharedPreferences.SP_BABY_SEAT)) != 0) {
			spnNoOfBabySeats.setSelection(Integer.valueOf(getSharedData(IjoomerSharedPreferences.SP_BABY_SEAT)));
		}
		if (Integer.valueOf(getSharedData(IjoomerSharedPreferences.SP_PASSENGER_SEAT)) != 0) {
			spnNoOfPassenger.setSelection(Integer.valueOf(getSharedData(IjoomerSharedPreferences.SP_PASSENGER_SEAT)));
		}
		
		if(IjoomerApplicationConfiguration.isReloadRequired()){
			IjoomerApplicationConfiguration.setReloadRequired(false);
			spnNoOfBabySeats.performClick();
		}
	}

	private String getSharedData(String key) {
		if (key.equals(IjoomerSharedPreferences.SP_BABY_SEAT) || key.equals(IjoomerSharedPreferences.SP_PASSENGER_SEAT)) {
			return "" + SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getInt(key, 0);
		} else {
			return SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(key, "");
		}
	}

	@Override
	public void setActionListeners() {


		lnrPickUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					loadNew(BookCarPickDropAddressActivity.class, BookCarHomeActivity.this, false, "IN_ADDRESS_TYPE", "pickUpAddress");
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});

		lnrDropOff.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					loadNew(BookCarPickDropAddressActivity.class, BookCarHomeActivity.this, false, "IN_ADDRESS_TYPE", "dropOffAddress");
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});

		lnrSelectVehicle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					loadNew(BookCarSelectActivity.class, BookCarHomeActivity.this, false, "IN_LAST_CAR", getSharedData(IjoomerSharedPreferences.SP_SELECT_VEHICLE));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});

		lnrNoOfBabySeats.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				spnNoOfBabySeats.performClick();
			}
		});

		lnrNoOfPassenger.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				spnNoOfPassenger.performClick();
			}
		});

		spnNoOfBabySeats.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int index, long arg3) {
				if (index != 0) {
					txtNoOfBabySeatsValue.setText("(" + getResources().getStringArray(R.array.no_of_baby_seats_array)[index - 1] + ")");
					SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_BABY_SEAT, index - 1);
				}else{
					txtNoOfBabySeatsValue.setText("(" + getResources().getStringArray(R.array.no_of_baby_seats_array)[0] + ")");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		spnNoOfPassenger.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int index, long arg3) {
					txtNoOfPassengerValue.setText("(" + getResources().getStringArray(R.array.no_of_passenger_array)[index] + ")");
					SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(IjoomerSharedPreferences.SP_PASSENGER_SEAT, index);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		btnGetQuote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				boolean flag = true;
				if (getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS).length() <= 0) {
					flag = false;
					ting("Please Select Pickup Address");
				} else if (getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS).length() <= 0) {
					flag = false;
					ting("Please Select Dropoff Address");
				} else if (getSharedData(IjoomerSharedPreferences.SP_SELECT_VEHICLE).length() <= 0) {
					flag = false;
					ting("Please Select Vehicle Address");
				} 

				if (flag) {
					String origin = getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS).replace(" ", "+");
					String destination = getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS).replace(" ", "+");
					origin = origin.replace(",", "");
					origin = origin.replace(" ", "");
					destination = destination.replace(",", "");
					destination = destination.replace(" ", "");
					pbrGetQuote.setVisibility(View.VISIBLE);
					getRouteDistance(origin, destination, new WebCallListener() {

						@Override
						public void onProgressUpdate(int progressCount) {

						}

						@Override
						public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
							provider.getQuote(getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS).replace("", ""), getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS).replace(",", ""), getSharedData(IjoomerSharedPreferences.SP_SELECT_VEHICLE), String.valueOf(getBabySeatCount()), getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS_TYPE), getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS_TYPE), data2.toString(), getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS_CODE),
									getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS_CODE), getSearchType(), new WebCallListener() {

										@Override
										public void onProgressUpdate(int progressCount) {

										}

										@Override
										public void onCallComplete(final int responseCode, String errorMessage, final ArrayList<HashMap<String, String>> data1, Object data2) {
											pbrGetQuote.setVisibility(View.GONE);
											if (responseCode == 200) {
												try {
													loadNew(BookCarGetQuoteActivity.class, BookCarHomeActivity.this, false, "IN_BOOK_DATA", prePareJsonForBookNow(data1.get(0)).toString());
												} catch (Throwable e) {
													e.printStackTrace();
												}
											} else {
												IjoomerUtilities.getCustomOkDialog(getString(R.string.book_now), errorMessage, getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

													@Override
													public void NeutralMethod() {
														if (responseCode == 599 && true) {
															finish();
														}
													}
												});
											}
										}
									});
						}
					});
				}

			}
		});

	}

	private int getBabySeatCount() {
		int babySeat;
		switch (spnNoOfBabySeats.getSelectedItemPosition()) {
		case 1:
			babySeat = 1;
			break;
		case 2:
			babySeat = 2;
			break;
		default:
			babySeat = 0;
			break;
		}

		return babySeat;
	}

	private JSONObject prePareJsonForBookNow(HashMap<String, String> quoteData) {

		JSONObject bookNow = new JSONObject();
		try {
			bookNow.put("pickAddress", getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS));
			bookNow.put("pickAddressPostCode", getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS_CODE));
			bookNow.put("dropoffAddress", getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS));
			bookNow.put("dropoffAddressPostCode", getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS_CODE));
			bookNow.put("pickUpAddressType", getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS_TYPE));
			bookNow.put("dropOffAddressType", getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS_TYPE));
			bookNow.put("vehicle", getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS));
			bookNow.put("searchType", getSearchType());
			bookNow.put("babySeat", spnNoOfBabySeats.getSelectedItemPosition());
			bookNow.put("passenger", spnNoOfPassenger.getSelectedItemPosition() + 1);
			bookNow.put("distanceInMiles", quoteData.get("distance_in_miles"));
			bookNow.put("cost", quoteData.get("total_calculated_cost"));
			bookNow.put("distanceCost", quoteData.get("distance_cost"));
			bookNow.put("carCost", quoteData.get("car_cost"));
			bookNow.put("surchargeCost", quoteData.get("surcharge_cost"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookNow;

	}

	private String getSearchType() {
		if (getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS_TYPE).equals("airports") && getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS_TYPE).equals("airports")) {
			return "Airport To Airport";
		} else if (getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS_TYPE).equals("airports") && getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS_TYPE).equals("postcodes")) {
			return "Airport To Post Code";
		} else if (getSharedData(IjoomerSharedPreferences.SP_PICKUP_ADDRESS_TYPE).equals("postcodes") && getSharedData(IjoomerSharedPreferences.SP_DROPOFF_ADDRESS_TYPE).equals("postcodes")) {
			return "Post Code To Post Code";
		} else {
			return "Post Code To Airport";
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void getRouteDistance(final String origin, final String destination, final WebCallListener target) {

		new AsyncTask<Void, Void, Double>() {
			@Override
			protected Double doInBackground(Void... params) {
				HttpURLConnection conn = null;

				String PLACES_API_BASE = "http://maps.googleapis.com/maps/api/distancematrix";
				String MODE = "driving";
				String OUT_JSON = "/json?";

				StringBuilder jsonResults = new StringBuilder();
				try {
					StringBuilder sb = new StringBuilder(PLACES_API_BASE + OUT_JSON);
					sb.append("origins=" + origin);
					sb.append("&destinations=" + destination);
					sb.append("&mode=" + MODE);
					sb.append("&sensor=false");

					URL url = new URL(sb.toString());
					conn = (HttpURLConnection) url.openConnection();
					InputStreamReader in = new InputStreamReader(conn.getInputStream());

					// Load the results into a StringBuilder
					int read;
					char[] buff = new char[1024];
					while ((read = in.read(buff)) != -1) {
						jsonResults.append(buff, 0, read);
					}
				} catch (MalformedURLException e) {
					return 0.0;
				} catch (IOException e) {
					return 0.0;
				} finally {
					if (conn != null) {
						conn.disconnect();
					}
				}

				try {
					// Create a JSON object hierarchy from the results
					JSONObject jsonObj = new JSONObject(jsonResults.toString());
					String temp[] = jsonObj.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("distance").getString("text").split(" ");
					return ((Double.parseDouble(temp[0]) * 0.62137119));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0.0;
			}

			@Override
			protected void onPostExecute(Double result) {
				target.onCallComplete(200, "", null, result);
				super.onPostExecute(result);
			}
		}.execute();

	}

	class NothingSelectedSpinnerAdapter implements SpinnerAdapter, ListAdapter {

		protected static final int EXTRA = 1;
		protected SpinnerAdapter adapter;
		protected Context context;
		protected int nothingSelectedLayout;
		protected int nothingSelectedDropdownLayout;
		protected LayoutInflater layoutInflater;

		/**
		 * Use this constructor to have NO 'Select One...' item, instead use the
		 * standard prompt or nothing at all.
		 * 
		 * @param spinnerAdapter
		 *            wrapped Adapter.
		 * @param nothingSelectedLayout
		 *            layout for nothing selected, perhaps you want text grayed
		 *            out like a prompt...
		 * @param context
		 */
		public NothingSelectedSpinnerAdapter(SpinnerAdapter spinnerAdapter, int nothingSelectedLayout, Context context) {

			this(spinnerAdapter, nothingSelectedLayout, -1, context);
		}

		/**
		 * Use this constructor to Define your 'Select One...' layout as the
		 * first row in the returned choices. If you do this, you probably don't
		 * want a prompt on your spinner or it'll have two 'Select' rows.
		 * 
		 * @param spinnerAdapter
		 *            wrapped Adapter. Should probably return false for
		 *            isEnabled(0)
		 * @param nothingSelectedLayout
		 *            layout for nothing selected, perhaps you want text grayed
		 *            out like a prompt...
		 * @param nothingSelectedDropdownLayout
		 *            layout for your 'Select an Item...' in the dropdown.
		 * @param context
		 */
		public NothingSelectedSpinnerAdapter(SpinnerAdapter spinnerAdapter, int nothingSelectedLayout, int nothingSelectedDropdownLayout, Context context) {
			this.adapter = spinnerAdapter;
			this.context = context;
			this.nothingSelectedLayout = nothingSelectedLayout;
			this.nothingSelectedDropdownLayout = nothingSelectedDropdownLayout;
			layoutInflater = LayoutInflater.from(context);
		}

		@Override
		public final View getView(int position, View convertView, ViewGroup parent) {
			// This provides the View for the Selected Item in the Spinner, not
			// the dropdown (unless dropdownView is not set).
			if (position == 0) {
				return getNothingSelectedView(parent);
			}
			return adapter.getView(position - EXTRA, null, parent); // Could
																	// re-use
			// the convertView if possible.
		}

		/**
		 * View to show in Spinner with Nothing Selected Override this to do
		 * something dynamic... e.g. "37 Options Found"
		 * 
		 * @param parent
		 * @return
		 */
		protected View getNothingSelectedView(ViewGroup parent) {
			return layoutInflater.inflate(nothingSelectedLayout, parent, false);
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			// BUG! Vote to fix!!
			// http://code.google.com/p/android/issues/detail?id=17128 -
			// Spinner does not support multiple view types
			if (position == 0) {
				return nothingSelectedDropdownLayout == -1 ? new View(context) : getNothingSelectedDropdownView(parent);
			}

			// Could re-use the convertView if possible, use setTag...
			return adapter.getDropDownView(position - EXTRA, null, parent);
		}

		/**
		 * Override this to do something dynamic... For example, "Pick your
		 * favorite of these 37".
		 * 
		 * @param parent
		 * @return
		 */
		protected View getNothingSelectedDropdownView(ViewGroup parent) {
			return layoutInflater.inflate(nothingSelectedDropdownLayout, parent, false);
		}

		@Override
		public int getCount() {
			int count = adapter.getCount();
			return count == 0 ? 0 : count + EXTRA;
		}

		@Override
		public Object getItem(int position) {
			return position == 0 ? null : adapter.getItem(position - EXTRA);
		}

		@Override
		public int getItemViewType(int position) {
			// Doesn't work!! Vote to Fix!
			// http://code.google.com/p/android/issues/detail?id=17128 -
			// Spinner does not support multiple view types
			// This method determines what is the convertView, this should
			// return 1 for pos 0 or return 0 otherwise.
			return position == 0 ? getViewTypeCount() - EXTRA : adapter.getItemViewType(position - EXTRA);
		}

		@Override
		public int getViewTypeCount() {
			return adapter.getViewTypeCount() + EXTRA;
		}

		@Override
		public long getItemId(int position) {
			return adapter.getItemId(position - EXTRA);
		}

		@Override
		public boolean hasStableIds() {
			return adapter.hasStableIds();
		}

		@Override
		public boolean isEmpty() {
			return adapter.isEmpty();
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
			adapter.registerDataSetObserver(observer);
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
			adapter.unregisterDataSetObserver(observer);
		}

		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}

		@Override
		public boolean isEnabled(int position) {
			return position == 0 ? false : true; // Don't allow the 'nothing
													// selected'
													// item to be picked.
		}
	}

}