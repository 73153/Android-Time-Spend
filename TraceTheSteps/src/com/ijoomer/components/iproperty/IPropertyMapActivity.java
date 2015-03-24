package com.ijoomer.components.iproperty;


import static com.ijoomer.components.iproperty.IPropertyTagHolder.ICON;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import pl.mg6.android.maps.extensions.ClusteringSettings;
import pl.mg6.android.maps.extensions.GoogleMap;
import pl.mg6.android.maps.extensions.Marker;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ijoomer.common.classes.IjoomerMapClusterIconProvider;
import com.ijoomer.common.classes.IjoomerMapDirection;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.tracethesteps.src.R;

public class IPropertyMapActivity extends IPropertyMasterActivity implements GoogleMap.OnInfoWindowClickListener {

	private boolean IN_GOTO_DETAILS;
	private boolean IN_GOTO_DIRECTION;
	private ArrayList<HashMap<String, String>> IN_MAPLIST;
	private AQuery androidQuery;
	Bitmap bitmapCreate = null;
	Bitmap bitmapScale = null;
	private IjoomerButton btnMapIndication;
	private GoogleMap googleMap;
	private ImageView imgSearchCategoryImage;
	private LinearLayout lnrDetail;
	private LinearLayout lnrProperty;
	private HashMap<Marker, HashMap<String, String>> markerHashMap;
	private Marker selectedMarker;
	private IjoomerTextView txtResultFound;
	private IjoomerTextView txtSearchCategoryAddress;
	private IjoomerTextView txtSearchCategoryTitle;

	// 180 - 8*8,160 - 9*9,144 - 10*10,120 - 12*12 and 96 - 15*15 grids
	// respectively on zoom level 2.
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

	public IPropertyMapActivity() {

	}

	private void onDataUpdate() {
		Marker m = googleMap.getMarkerShowingInfoWindow();
		if (m != null && !m.isCluster() && m.getData() instanceof MutableData) {
			m.showInfoWindow();
		}
	}

	private void placeMarker(HashMap<String, String> row) {
		markerHashMap
				.put(googleMap.addMarker(new MarkerOptions().title((String) row.get(TITLE)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ijoomer_map_pin))
						.position(new LatLng(Double.parseDouble((String) row.get(LATITUDE)), Double.parseDouble((String) row.get(LONGITUDE))))), row);
	}

	private void populateMap() {
		googleMap.setMyLocationEnabled(true);
		if ((IN_MAPLIST != null) && (IN_MAPLIST.size() > 0))
			try {
				googleMap.setClustering(new ClusteringSettings().iconDataProvider(new IjoomerMapClusterIconProvider(getResources())).addMarkersDynamically(true));
				googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble((String) ((HashMap<String, String>) IN_MAPLIST.get(0)).get(LATITUDE)), Double
						.parseDouble((String) ((HashMap<String, String>) IN_MAPLIST.get(0)).get(LONGITUDE)))));
				googleMap.setInfoWindowAdapter(new InfoAdapter());
				googleMap.setOnInfoWindowClickListener(this);
				setUpClusteringViews();
				for (HashMap<String, String> row : IN_MAPLIST) {
					if ((row.get(LATITUDE) != null) && (((String) row.get(LATITUDE)).toString().trim().length() > 0) && (row.get(LONGITUDE) != null)
							&& (((String) row.get(LONGITUDE)).toString().trim().length() > 0))
						placeMarker(row);

				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
	}

	private void setUpClusteringViews() {
		ClusteringSettings localClusteringSettings = new ClusteringSettings();
		localClusteringSettings.addMarkersDynamically(true);
		localClusteringSettings.iconDataProvider(new IjoomerMapClusterIconProvider(getResources()));
		localClusteringSettings.clusterSize(CLUSTER_SIZES[0]);
		googleMap.setClustering(localClusteringSettings);
	}

	public void initComponents() {
		googleMap = getMapView();
		googleMap.moveCamera(CameraUpdateFactory.zoomBy(1));
		googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

		lnrProperty = ((LinearLayout) findViewById(R.id.lnrProperty));
		lnrDetail = ((LinearLayout) findViewById(R.id.lnrDetail));
		imgSearchCategoryImage = ((ImageView) findViewById(R.id.imgSearchCategoryImage));
		btnMapIndication = ((IjoomerButton) findViewById(R.id.btnMapIndication));
		txtSearchCategoryAddress = ((IjoomerTextView) findViewById(R.id.txtSearchCategoryAddress));
		txtSearchCategoryTitle = ((IjoomerTextView) findViewById(R.id.txtSearchCategoryTitle));
		txtResultFound = ((IjoomerTextView) findViewById(R.id.txtResultFound));
		markerHashMap = new HashMap<Marker, HashMap<String, String>>();
		androidQuery = new AQuery(this);
	}

	public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt) {
	}

	public void onInfoWindowClick(Marker marker) {

		if (marker.isCluster()) {
			List<Marker> markers = marker.getMarkers();
			Builder builder = LatLngBounds.builder();
			for (Marker m : markers) {
				builder.include(m.getPosition());
			}
			LatLngBounds bounds = builder.build();
			googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, getResources().getDimensionPixelSize(R.dimen.padding)));
		} else {
			marker.hideInfoWindow();
		}

	}

	protected void onPause() {
		super.onPause();
		handler.removeCallbacks(dataUpdater);
	}

	protected void onResume() {
		super.onResume();
		handler.post(dataUpdater);
	}

	@SuppressWarnings("unchecked")
	public void prepareViews() {
		IN_MAPLIST = ((ArrayList<HashMap<String, String>>) (getIntent().getSerializableExtra("IN_MAPLIST") != null ? getIntent().getSerializableExtra("IN_MAPLIST")
				: new ArrayList<HashMap<String, String>>()));

		txtResultFound.setText(String.format(getString(R.string.iproperty_search_result_found), IN_MAPLIST.size()));
		IN_GOTO_DETAILS = getIntent().getBooleanExtra("IN_GOTO_DETAILS", false);
		IN_GOTO_DIRECTION = getIntent().getBooleanExtra("IN_GOTO_DIRECTION", false);
		if (IN_GOTO_DIRECTION) {
			btnMapIndication.setVisibility(View.VISIBLE);
			if (((HashMap<String, String>) IN_MAPLIST.get(0)).get(TITLE).trim().length() > 0)
				txtResultFound.setText(((HashMap<String, String>) IN_MAPLIST.get(0)).get(TITLE));
		}
		populateMap();
	}

	public void setActionListeners() {
		btnMapIndication.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					loadNew(IjoomerMapDirection.class, IPropertyMapActivity.this, false, "IN_DESTINATION_LAT", ((HashMap<String, String>) IN_MAPLIST.get(0)).get(LATITUDE),
							"IN_DESTINATION_LONG", ((HashMap<String, String>) IN_MAPLIST.get(0)).get(LONGITUDE));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		imgSearchCategoryImage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> row = (HashMap<String, String>) v.getTag();
				if (!IN_GOTO_DETAILS) {
					try {
						loadNew(IPropertySearchResultDetailsListActivity.class, IPropertyMapActivity.this, false, "IN_OBJ", getIntent().getStringExtra("IN_OBJ"),
								"IN_PROPERTY_DATA", row);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				} else {
					try {
						loadNew(IPropertyDetailsActivity.class, IPropertyMapActivity.this, false, "IN_OBJ", getIntent().getStringExtra("IN_OBJ"), "IN_PROPERTY_ID", row.get(ID));
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		});
		lnrProperty.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> row = (HashMap<String, String>) v.getTag();
				try {
					loadNew(IPropertyDetailsActivity.class, IPropertyMapActivity.this, false, "IN_OBJ", getIntent().getStringExtra("IN_OBJ"), "IN_PROPERTY_ID", row.get(ID));
				} catch (Throwable localThrowable) {
					localThrowable.printStackTrace();
				}
			}
		});
	}

	public int setLayoutId() {
		return R.layout.iproperty_map;
	}

	class InfoAdapter implements GoogleMap.InfoWindowAdapter {
		private TextView tv;
		{

			tv = new TextView(IPropertyMapActivity.this);
			tv.setTextColor(Color.WHITE);
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

		public View getInfoWindow(Marker marker) {
			if (!marker.isCluster() && IN_GOTO_DETAILS) {
				HashMap<String, String> row = (HashMap<String, String>) markerHashMap.get(marker);
				if (!IN_GOTO_DIRECTION && selectedMarker == null || selectedMarker.getPosition().latitude != marker.getPosition().latitude
						&& selectedMarker.getPosition().longitude != marker.getPosition().longitude) {
					if (selectedMarker != null) {
						selectedMarker.remove();
					}

					Iterator<Marker> markers = markerHashMap.keySet().iterator();
					while (markers.hasNext()) {
						((Marker) markers.next()).setVisible(true);
					}

					selectedMarker = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ijoomer_map_pin_selected)).position(
							marker.getPosition()));
					marker.setVisible(false);
					lnrDetail.setVisibility(View.VISIBLE);
					lnrProperty.setTag(row);
					((AQuery) androidQuery.id(imgSearchCategoryImage)).image((String) row.get(ICON), true, true);
					imgSearchCategoryImage.setTag(row);
					txtSearchCategoryAddress.setText(prepareAddress(new String[] { row.get(STREET_NUM), row.get(STREET), row.get(STREET2) }));
					txtSearchCategoryTitle.setText((CharSequence) row.get(TITLE));
				}
			}

			return null;
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