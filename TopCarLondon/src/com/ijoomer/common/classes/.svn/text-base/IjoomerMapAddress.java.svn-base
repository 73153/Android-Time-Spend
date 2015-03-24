package com.ijoomer.common.classes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.mg6.android.maps.extensions.GoogleMap;
import pl.mg6.android.maps.extensions.GoogleMap.OnMapClickListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.theme.ThemeManager;
import com.ijoomer.topcarlondon.src.R;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

/**
 * This Class Contains All Method Related To IjoomerMapAddress.
 * 
 * @author tasol
 * 
 */
public class IjoomerMapAddress extends IjoomerSuperMaster implements OnMapClickListener {

	private ListView lstMapAddress;
	private AutoCompleteTextView actMapAdress;
	private IjoomerTextView txtMapAddressHints;
	private GoogleMap googleMap;
	private ProgressBar pbrLstMapAddress;

	ArrayList<HashMap<String, String>> addressList;

	/**
	 * Overrides method
	 */

	@Override
	public int setLayoutId() {
		return ThemeManager.getInstance().getMapAddress();
	}

	@Override
	public void initComponents() {
		googleMap = getMapView();

		lstMapAddress = (ListView) findViewById(R.id.lstMapAddress);
		pbrLstMapAddress = (ProgressBar) findViewById(R.id.pbrLstMapAddress);
		txtMapAddressHints = (IjoomerTextView) findViewById(R.id.txtMapAddressHints);
		actMapAdress = (AutoCompleteTextView) findViewById(R.id.actMapAdress);

		googleMap.setMyLocationEnabled(true);
		googleMap.setOnMapClickListener(this);
	}

	@Override
	public void prepareViews() {
		hideSoftKeyboard();
		if (getIntent().getStringExtra("IN_LAST_ADDRESS") != null && getIntent().getStringExtra("IN_LAST_ADDRESS").trim().length() > 0) {
			try {
				Address address = IjoomerUtilities.getLatLongFromAddress(getIntent().getStringExtra("IN_LAST_ADDRESS"));
				setAddressData(address.getLatitude(), address.getLongitude());
				CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(address.getLatitude(), address.getLongitude())).zoom(15).build();
				googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			} catch (Exception e) {
			}
		} else {
			try {
				Address address = IjoomerUtilities.getAddressFromLatLong(0, 0);
				setAddressData(address.getLatitude(), address.getLongitude());
				CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(address.getLatitude(), address.getLongitude())).zoom(15).build();
				googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			} catch (Exception e) {
			}
		}

		actMapAdress.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.ijoomer_map_address_auto_complete_list_item));

	}

	@Override
	public void setActionListeners() {
		actMapAdress.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
				hideSoftKeyboard();
				String str = (String) adapterView.getItemAtPosition(position);
				actMapAdress.setText(str);
				Address address = IjoomerUtilities.getLatLongFromAddress(str);
				if (address == null) {
					IjoomerUtilities.getCustomOkDialog(getString(R.string.address_from_map), getString(R.string.lat_long_not_found), getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

						@Override
						public void NeutralMethod() {

						}
					});
				} else {
					CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(address.getLatitude(), address.getLongitude())).zoom(15).build();
					googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
					setAddressData(address.getLatitude(), address.getLongitude());
					actMapAdress.setText("");
				}

			}
		});
	}

	@Override
	public void onMapClick(LatLng latitudeLongitude) {
		CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitudeLongitude.latitude, latitudeLongitude.longitude)).zoom(15).build();
		googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		setAddressData(latitudeLongitude.latitude, latitudeLongitude.longitude);

	}

	@Override
	public View setLayoutView() {
		return null;
	}

	@Override
	public int setHeaderLayoutId() {
		return 0;
	}

	@Override
	public String[] setTabItemNames() {
		return null;
	}

	@Override
	public int setTabBarDividerResId() {
		return 0;
	}

	@Override
	public int setTabItemLayoutId() {
		return 0;
	}

	@Override
	public int[] setTabItemOnDrawables() {
		return null;
	}

	@Override
	public int[] setTabItemOffDrawables() {
		return null;
	}

	@Override
	public int[] setTabItemPressDrawables() {
		return null;
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {

	}

	@Override
	public int setFooterLayoutId() {
		return 0;
	}

	/**
	 * L Class method
	 */

	/**
	 * This method used to getting address from lat-lng.
	 * 
	 * @param lat
	 *            represented latitude
	 * @param lng
	 *            represented longitude
	 */
	private void setAddressData(double lat, double lng) {
		Geocoder geocoder;
		if (lat != 0 && lng != 0) {
			geocoder = new Geocoder(IjoomerMapAddress.this);
			try {
				List<Address> list = geocoder.getFromLocation(lat, lng, 10);

				if (list != null && list.size() > 0) {
					if (txtMapAddressHints.getVisibility() == View.GONE) {
						pbrLstMapAddress.setVisibility(View.VISIBLE);
					}
					addressList = new ArrayList<HashMap<String, String>>();
					for (Address address : list) {
						HashMap<String, String> data = new HashMap<String, String>();
						if (address.getPostalCode() != null) {
							data.put("address", address.getAddressLine(0));
							data.put("latitude", String.valueOf(address.getLatitude()));
							data.put("longitude", String.valueOf(address.getLongitude()));
							data.put("postalcode", String.valueOf(address.getPostalCode()));
							addressList.add(data);
						}
					}

					lstMapAddress.setAdapter(getListAdapter(prepareList(addressList)));
					if (txtMapAddressHints.getVisibility() == View.VISIBLE) {
						lstMapAddress.setVisibility(View.VISIBLE);
						txtMapAddressHints.setVisibility(View.GONE);
					} else {
						pbrLstMapAddress.setVisibility(View.GONE);
					}

				} else {
					IjoomerUtilities.getCustomOkDialog(getString(R.string.address_from_map), getString(R.string.lat_long_not_found), getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

						@Override
						public void NeutralMethod() {

						}
					});
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public ArrayList<SmartListItem> prepareList(ArrayList<HashMap<String, String>> data) {
		ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
		for (HashMap<String, String> hashMap : data) {
			SmartListItem item = new SmartListItem();
			item.setItemLayout(R.layout.ijoomer_map_address_item);
			ArrayList<Object> obj = new ArrayList<Object>();
			obj.add(hashMap);
			item.setValues(obj);
			listData.add(item);
		}
		return listData;
	}

	/**
	 * List adapter
	 */

	private SmartListAdapterWithHolder getListAdapter(ArrayList<SmartListItem> data) {

		SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(this, R.layout.ijoomer_map_address_item, data, new ItemView() {

			@Override
			public View setItemView(final int position, View v, final SmartListItem item, final ViewHolder holder) {

				holder.txtMapAddressData = (IjoomerTextView) v.findViewById(R.id.txtMapAddressData);

				@SuppressWarnings("unchecked")
				final HashMap<String, String> row = (HashMap<String, String>) item.getValues().get(0);

				holder.txtMapAddressData.setText(row.get("address"));
				holder.txtMapAddressData.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent();
						intent.putExtra("MAP_ADDRESSS_DATA", row);
						setResult(Activity.RESULT_OK, intent);
						finish();
					}
				});

				return v;
			}

			@Override
			public View setItemView(int position, View v, SmartListItem item) {
				return null;
			}

		});
		return adapterWithHolder;

	}

	private final String LOG_TAG = "ExampleApp";

	private final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private final String OUT_JSON = "/json";
	private final String MAP_API_KEY = "AIzaSyAfhmNNTzNy4CpE4bNBMTawVl4ENUzgppc";

	private ArrayList<String> autocomplete(final String input) {

		ArrayList<String> resultList = null;
		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
			StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
			sb.append("?sensor=false&key=" + MAP_API_KEY);
			sb.append("&components=country:uk");
			sb.append("&input=" + URLEncoder.encode(input, "utf8"));

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
			Log.e(LOG_TAG, "Error processing Places API URL", e);
			return resultList;
		} catch (IOException e) {
			Log.e(LOG_TAG, "Error connecting to Places API", e);
			return resultList;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

			// Extract the Place descriptions from the results
			resultList = new ArrayList<String>(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++) {
				resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
			}
		} catch (JSONException e) {
			Log.e(LOG_TAG, "Cannot process JSON results", e);
		}

		return resultList;

	}

	private class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
		private ArrayList<String> resultList;

		public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
		}

		@Override
		public int getCount() {
			return resultList.size();
		}

		@Override
		public String getItem(int index) {
			return resultList.get(index);
		}

		@Override
		public Filter getFilter() {
			Filter filter = new Filter() {
				@Override
				protected FilterResults performFiltering(final CharSequence constraint) {
					final FilterResults filterResults = new FilterResults();
					if (constraint != null) {
						// Retrieve the autocomplete results.

						resultList = autocomplete(constraint.toString());
						filterResults.values = resultList;
						filterResults.count = resultList.size();
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
