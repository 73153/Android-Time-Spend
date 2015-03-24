package com.eosos.components.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eosos.common.classes.IjoomerMapClusterIconProvider;
import com.eosos.common.classes.IjoomerUtilities;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.library.EososDataProvider;
import com.eosos.src.R;
import com.eosos.weservice.WebCallListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smart.framework.SmartActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import pl.mg6.android.maps.extensions.ClusteringSettings;
import pl.mg6.android.maps.extensions.GoogleMap;
import pl.mg6.android.maps.extensions.GoogleMap.InfoWindowAdapter;
import pl.mg6.android.maps.extensions.GoogleMap.OnInfoWindowClickListener;
import pl.mg6.android.maps.extensions.Marker;

/**
 * This Class Contains All Method Related To JomMapActivity.
 * 
 * @author tasol
 * 
 */
public class EososMapActivity extends EososMasterActivity implements OnInfoWindowClickListener {
	private IjoomerTextView txtRoute;
	private GoogleMap googleMap;
	private String today;

	private String IN_DESTINATION_LAT;
	private String IN_DESTINATION_LONG;

	private BitmapDescriptor bitmapDescriptorActive;
	private BitmapDescriptor bitmapDescriptor;
	private EososDataProvider dataProvider;
	private ImageView imgDirection;
	private ImageView imgMapType;

	private ArrayList<HashMap<String, String>> IN_MAPLIST;

	private HashMap<Marker, HashMap<String, String>> markerHashMap;

	private final double[] CLUSTER_SIZES = new double[] { 180, 160, 144, 120, 96 };

	private MutableData[] dataArray = { new MutableData(6, new LatLng(-50, 0)), new MutableData(28, new LatLng(-52, 1)), new MutableData(496, new LatLng(-51, -2)), };
	private Handler handler = new Handler();
	private Runnable dataUpdater = new Runnable() {

		@Override
		public void run() {
			for (MutableData data : dataArray) {
				data.value = 7 + 3 * data.value;
			}
			onDataUpdate();
			handler.postDelayed(this, 1000);
		}
	};

	/**
	 * Overrides methods
	 */

	@Override
	public int setLayoutId() {
		return R.layout.eosos_map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initComponents() {
		imgMapType = (ImageView) findViewById(R.id.imgMapType);
		txtRoute = (IjoomerTextView) findViewById(R.id.txtRoute);

		try {
			IN_MAPLIST = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("IN_MAPLIST");
			googleMap = getMapView();
			dataProvider = new EososDataProvider(EososMapActivity.this);
			markerHashMap = new HashMap<Marker, HashMap<String, String>>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void prepareViews() {
		((IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader)).setText(getString(R.string.location));
		((IjoomerTextView) getHeaderView().findViewById(R.id.txtBack)).setVisibility(View.VISIBLE);
		((ImageView) getHeaderView().findViewById(R.id.imgHome)).setVisibility(View.GONE);
		try {
			bitmapDescriptorActive = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location_img1));
			bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location_img2));

			SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
			Date d = new Date();
			today = sdf.format(d).toLowerCase();
			Log.e("today", today);
		} catch (Exception e) {
			e.printStackTrace();
		}
		imgDirection = (ImageView) getHeaderView().findViewById(R.id.imgDirection);
		imgDirection.setVisibility(View.VISIBLE);
		populateMap();
	}

	@Override
	protected void onResume() {
		super.onResume();
		handler.post(dataUpdater);
		txtRoute.setTextColor(getResources().getColor(R.color.txt_color));
	}

	@Override
	protected void onPause() {
		super.onPause();
		handler.removeCallbacks(dataUpdater);
	}

	@Override
	public void onInfoWindowClick(Marker marker) {

	}

	@Override
	public void setActionListeners() {
		imgDirection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// getMyLocation();
				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						new LatLng(Double.parseDouble(IN_MAPLIST.get(0).get(LATITUDE)), Double.parseDouble(IN_MAPLIST.get(0).get(LONGITUDE))), 12));

			}
		});

		imgMapType.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imgMapType.setImageResource(R.drawable.map_btn_icon_selected);
				showPopupMapType(v, new PopupWindow.OnDismissListener() {

					@Override
					public void onDismiss() {
						imgMapType.setImageResource(R.drawable.map_btn_icon_normal);
					}
				});
			}
		});

		txtRoute.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				txtRoute.setTextColor(getResources().getColor(R.color.red));
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + IN_DESTINATION_LAT + "," + IN_DESTINATION_LONG
						+ "&daddr=" + getLatitude() + "," + getLongitude()));
				intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
				startActivity(intent);
			}
		});

	}

	private void getMyLocation() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Address address = IjoomerUtilities.getAddressFromLatLong(0, 0);
					googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 12));
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {

	}

	/**
	 * Class methods
	 */

	/**
	 * This method used to set clustering view.
	 */
	private void setUpClusteringViews() {
		ClusteringSettings clusteringSettings = new ClusteringSettings();
		clusteringSettings.addMarkersDynamically(true);

		clusteringSettings.iconDataProvider(new IjoomerMapClusterIconProvider(getResources()));

		double clusterSize = CLUSTER_SIZES[0];
		clusteringSettings.clusterSize(clusterSize);

		googleMap.setClustering(clusteringSettings);
	}

	/**
	 * This method used to populate map.
	 */
	private void populateMap() {

		if (IN_MAPLIST != null && IN_MAPLIST.size() > 0) {

			try {
				googleMap.clear();
				googleMap.setClustering(new ClusteringSettings().iconDataProvider(new IjoomerMapClusterIconProvider(getResources())).addMarkersDynamically(true));
				for (HashMap<String, String> mapItem : IN_MAPLIST) {
					if (mapItem.get(LATITUDE) != null && mapItem.get(LATITUDE).trim().length() > 0 && mapItem.get(LONGITUDE) != null && mapItem.get(LONGITUDE).trim().length() > 0) {
						placeMarker(mapItem);
					}
				}
				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						new LatLng(Double.parseDouble(IN_MAPLIST.get(0).get(LATITUDE)), Double.parseDouble(IN_MAPLIST.get(0).get(LONGITUDE))), 12));

				IN_DESTINATION_LAT = IN_MAPLIST.get(0).get(LATITUDE);
				IN_DESTINATION_LONG = IN_MAPLIST.get(0).get(LONGITUDE);
				googleMap.setInfoWindowAdapter(new InfoAdapter());
				googleMap.setOnInfoWindowClickListener(this);
				setUpClusteringViews();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * This method used to place marker on map.
	 * 
	 * @param markerData
	 *            represented markar data.
	 */
	private void placeMarker(final HashMap<String, String> markerData) {
		try {
			if (today != null && dataProvider.isClubOpen(markerData.get("id"), today)) {
				markerHashMap.put(
						googleMap.addMarker(new MarkerOptions().title(markerData.get("title")).icon(bitmapDescriptorActive)
								.position(new LatLng(Double.parseDouble(markerData.get("latitude")), Double.parseDouble(markerData.get("longitude"))))), markerData);
			} else {
				markerHashMap.put(
						googleMap.addMarker(new MarkerOptions().title(markerData.get("title")).icon(bitmapDescriptor)
								.position(new LatLng(Double.parseDouble(markerData.get("latitude")), Double.parseDouble(markerData.get("longitude"))))), markerData);
			}
		} catch (Exception e) {
			markerHashMap.put(
					googleMap.addMarker(new MarkerOptions().title(markerData.get("title")).icon(bitmapDescriptorActive)
							.position(new LatLng(Double.parseDouble(markerData.get("latitude")), Double.parseDouble(markerData.get("longitude"))))), markerData);
		}

	}

	/**
	 * This method used to on map data update.
	 */
	private void onDataUpdate() {
		Marker m = googleMap.getMarkerShowingInfoWindow();
		if (m != null && !m.isCluster() && m.getData() instanceof MutableData) {
			m.showInfoWindow();
		}
	}

	/**
	 * Custom marker info adapter.
	 * 
	 * @author tasol
	 * 
	 */
	class InfoAdapter implements InfoWindowAdapter {

		private TextView tv;
		{

			tv = new TextView(EososMapActivity.this);
			tv.setTextColor(Color.BLACK);
		}

		private Collator collator = Collator.getInstance();
		private Comparator<Marker> comparator = new Comparator<Marker>() {
			public int compare(Marker lhs, Marker rhs) {
				String leftTitle = lhs.getTitle();
				String rightTitle = rhs.getTitle();
				if (leftTitle == null && rightTitle == null) {
					return 0;
				}
				if (leftTitle == null) {
					return 1;
				}
				if (rightTitle == null) {
					return -1;
				}
				return collator.compare(leftTitle, rightTitle);
			}
		};

		@Override
		public View getInfoContents(Marker marker) {
			if (marker.isCluster()) {
				List<Marker> markers = marker.getMarkers();
				int i = 0;
				String text = "";
				while (i < 3 && markers.size() > 0) {
					Marker m = Collections.min(markers, comparator);
					String title = m.getTitle();
					if (title == null) {
						break;
					}
					text += title + "\n";
					markers.remove(m);
					i++;
				}
				if (text.length() == 0) {
					text = "Markers with mutable data";
				} else if (markers.size() > 0) {
					text += "and " + markers.size() + " more...";
				} else {
					text = text.substring(0, text.length() - 1);
				}
				tv.setText(text);
				return tv;
			} else {
				Object data = marker.getData();
				if (data instanceof MutableData) {
					MutableData mutableData = (MutableData) data;
					tv.setText("Value: " + mutableData.value);
					return tv;
				} else {
					return null;
				}
			}
		}

		@Override
		public View getInfoWindow(Marker marker) {
			if (!marker.isCluster()) {
				final HashMap<String, String> data = markerHashMap.get(marker);

				View view = LayoutInflater.from(EososMapActivity.this).inflate(R.layout.eosos_map_bubble, null);

				IjoomerTextView txtTitle = (IjoomerTextView) view.findViewById(R.id.txtTitle);
				ImageView imgInfo = (ImageView) view.findViewById(R.id.imgInfo);
				imgInfo.setVisibility(View.GONE);

				txtTitle.setText(data.get("title"));

				return view;
			} else {
				return null;
			}

		}

	}

	public static Rect locateView(View v) {
		int[] loc_int = new int[2];
		if (v == null)
			return null;
		try {

			v.getLocationOnScreen(loc_int);
		} catch (NullPointerException npe) {
			// Happens when the view doesn't exist on screen anymore.
			return null;
		}
		Rect location = new Rect();
		location.left = loc_int[0];
		location.top = loc_int[1];
		location.right = location.left + v.getWidth();
		location.bottom = location.top + v.getHeight();

		Log.e("Rleft", location.left + "");
		Log.e("Rtop", location.top + "");
		Log.e("Rright", location.right + "");
		Log.e("Rbottom", location.bottom + "");
		// Rect(247, 440,277, 470)
		return location;
	}

	public void showPopupMapType(View v, final PopupWindow.OnDismissListener dismiss) {

		Rect rect = locateView(v);
		// v.getDrawingRect(rect);
		// Rect(247, 440 - 277, 470)

		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.eosos_maptype_popup, null);
		final IjoomerTextView txtNormal = (IjoomerTextView) layout.findViewById(R.id.txtNormal);
		final IjoomerTextView txtHybrid = (IjoomerTextView) layout.findViewById(R.id.txtHybrid);
		final IjoomerTextView txtSatellite = (IjoomerTextView) layout.findViewById(R.id.txtSatellite);

		final PopupWindow popup = new PopupWindow(EososMapActivity.this);
		popup.setAnimationStyle(R.style.animation);
		popup.setContentView(layout);
		popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		// popup.setWidth(((SmartActivity) getActivity()).getDeviceWidth() / 2);
		popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		popup.setFocusable(true);
		popup.setBackgroundDrawable(new BitmapDrawable(getResources()));
		popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

			@Override
			public void onDismiss() {
				dismiss.onDismiss();
			}
		});
		popup.showAtLocation(layout, Gravity.LEFT | Gravity.BOTTOM, (rect.left - v.getWidth() / 2), getDeviceHeight() - rect.top);
		if (popup.isShowing()) {

			txtNormal.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						popup.dismiss();
						if (googleMap.getMapType() != GoogleMap.MAP_TYPE_NORMAL)
							googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			txtHybrid.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						popup.dismiss();
						if (googleMap.getMapType() != GoogleMap.MAP_TYPE_HYBRID)
							googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			txtSatellite.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						popup.dismiss();
						if (googleMap.getMapType() != GoogleMap.MAP_TYPE_SATELLITE)
							googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

		}

	}

	public class JSONParser {
		InputStream is = null;
		JSONObject jObj = null;
		String json = "";

		public JSONParser() {
		}

		public void getJSONFromUrl(final String url, final WebCallListener target) {
			new AsyncTask<Void, Void, String>() {
				protected String doInBackground(Void... params) {
					HttpURLConnection httpURLConnection = null;
					StringBuilder stringBuilder = new StringBuilder();
					try {
						httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
						InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());

						int read;
						char[] buff = new char[1024];
						while ((read = inputStreamReader.read(buff)) != -1) {
							stringBuilder.append(buff, 0, read);
						}
						return stringBuilder.toString();
					} catch (MalformedURLException localMalformedURLException) {
						return "";
					} catch (IOException localIOException) {
						return "";
					} finally {
						if (httpURLConnection != null)
							httpURLConnection.disconnect();
					}
				}

				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					target.onCallComplete(200, null, null, result);
				}
			}.execute();
		}
	}

	/**
	 * Inner class
	 * 
	 * @author tasol
	 * 
	 */
	private class MutableData {

		private int value;
		@SuppressWarnings("unused")
		private LatLng position;

		public MutableData(int value, LatLng position) {
			this.value = value;
			this.position = position;
		}
	}

}
