package com.mycadiz.components.sobipro;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycadiz.common.classes.IjoomerMapClusterIconProvider;
import com.mycadiz.customviews.IjoomerTextView;
import com.mycadiz.library.sobipro.SobiproCategoriesDataProvider;
import com.mycadiz.src.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import pl.mg6.android.maps.extensions.ClusteringSettings;
import pl.mg6.android.maps.extensions.GoogleMap;
import pl.mg6.android.maps.extensions.GoogleMap.InfoWindowAdapter;
import pl.mg6.android.maps.extensions.GoogleMap.OnInfoWindowClickListener;
import pl.mg6.android.maps.extensions.Marker;
import pl.mg6.android.maps.extensions.SupportMapFragment;

/**
 * Activity class for SobiproMapActivity view
 *
 * @author tasol
 *
 */

public class SobiproMapActivity extends SobiproMasterActivity implements SobiproTagHolder {
    private HashMap<Marker, HashMap<String, String>> markerHashMap;
    private GoogleMap googleMap;
    private AQuery androidQuery;
    private Bitmap bitmapScale;
    private Bitmap bitmapCreate;
    private static final double[] CLUSTER_SIZES = new double[] { 180, 160, 144, 120, 96 };
    private MutableData[] dataArray = { new MutableData(6, new LatLng(-50, 0)), new MutableData(28, new LatLng(-52, 1)), new MutableData(496, new LatLng(-51, -2)), };
    private Handler handler = new Handler();
    private String IN_TABLE;
    private ArrayList<String> IN_ENTRY_ID_ARRAY;
    private SobiproCategoriesDataProvider dataProvider;
    private int IN_POS;
    private String IN_PAGELAYOUT;
    private String IN_PRIVIOUS_ACTIVITY;

    private ArrayList<String> pageLayouts;

    /**
     * This method represented to manage updated data.
     */
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
     * Overrides methods.
     */

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(dataUpdater);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(dataUpdater);
    }

    @Override
    public int setLayoutId() {
        return R.layout.sobipro_map;
    }

    @Override
    public void prepareViews() {
    }

    @Override
    public void setActionListeners() {
    }

    @Override
    public void initComponents() {

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment f = (SupportMapFragment) fm.findFragmentById(R.id.maps);
        googleMap = f.getExtendedMap();
        markerHashMap = new HashMap<Marker, HashMap<String, String>>();
        androidQuery = new AQuery(this);
        dataProvider = new SobiproCategoriesDataProvider(SobiproMapActivity.this);
        getIntentData();
        pageLayouts = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.sobipro_pageLayout)));
        googleMap.setClustering(new ClusteringSettings().iconDataProvider(new IjoomerMapClusterIconProvider(getResources())).addMarkersDynamically(true));

        googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {

            private TextView tv;
            {

                tv = new TextView(SobiproMapActivity.this);
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
                    }
                }
                return null;
            }

            @Override
            public View getInfoWindow(Marker marker) {
                try {
                    if (!marker.isCluster()) {

                        final HashMap<String, String> data = markerHashMap.get(marker);
                        View view;
                        view = LayoutInflater.from(SobiproMapActivity.this).inflate(R.layout.sobipro_map_bubble, null);

                        ImageView imgEntryIcon = (ImageView) view.findViewById(R.id.imgEntryIcon);
                        IjoomerTextView txtTitle = (IjoomerTextView) view.findViewById(R.id.txtTitlePopup);
                        IjoomerTextView txtAddress = (IjoomerTextView) view.findViewById(R.id.txtAddressPopup);


                        androidQuery.id(imgEntryIcon).image(data.get("imgLogo"), true, true, 0,
                                R.drawable.sobipro_default_image);
                        if (data.get("txtTitle") != null && data.get("txtTitle").length() > 0) {
                            txtTitle.setText(data.get("txtTitle"));
                        } else {
                            txtTitle.setVisibility(View.GONE);
                        }

                        if (data.get("txtAddress") != null && data.get("txtAddress").length() > 0) {
                            txtAddress.setText(data.get("txtAddress"));
                        } else {
                            txtAddress.setVisibility(View.GONE);
                        }

                        return view;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

            @Override
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
                    final HashMap<String, String> data = markerHashMap.get(marker);
                    goToEntryDetail(data);
                    marker.hideInfoWindow();
                }
            }
        });

        populateMap();
        setUpClusteringViews();
    }

    /**
     * Class methods.
     */

    /**
     * This method used to get intent data.
     */

    private void getIntentData() {
        try {
            IN_ENTRY_ID_ARRAY = getIntent().getStringArrayListExtra("IN_ENTRY_ID_ARRAY");
            IN_TABLE = getIntent().getStringExtra("IN_TABLE");
            IN_POS = getIntent().getIntExtra("IN_POS", 0);
            IN_PAGELAYOUT = getIntent().getStringExtra("IN_PAGELAYOUT");
            IN_PRIVIOUS_ACTIVITY = getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * This method is used to go in Entry Detail after clicking InfoWindow on
     * map.
     *
     * @param data
     *            represents entry detail data which is going to display.
     *
     */

    private void goToEntryDetail(HashMap<String, String> data) {
        try {
            ArrayList<String> ID_Array = new ArrayList<String>();
            ID_Array.add(data.get(ID));
            loadNew(SobiproEntryDetailActivity.class, SobiproMapActivity.this, false, "IN_ENTRY_ID_ARRAY", ID_Array, "IN_ENTRY_INDEX", 0, "IN_TABLE", IN_TABLE, "IN_POS", IN_POS,
                    "IN_PAGELAYOUT", IN_PAGELAYOUT,"IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is used to update the Data.
     */

    private void onDataUpdate() {
        Marker m = googleMap.getMarkerShowingInfoWindow();
        if (m != null && !m.isCluster() && m.getData() instanceof MutableData) {
            m.showInfoWindow();
        }
    }

    /**
     * This method is used to set map clustering view.
     */

    private void setUpClusteringViews() {
        ClusteringSettings clusteringSettings = new ClusteringSettings();
        clusteringSettings.addMarkersDynamically(true);

        clusteringSettings.iconDataProvider(new IjoomerMapClusterIconProvider(getResources()));

        double clusterSize = CLUSTER_SIZES[3];
        clusteringSettings.clusterSize(clusterSize);

        googleMap.setClustering(clusteringSettings);
    }

    /**
     * This method is used to populate map view with clustering.
     */
    private void populateMap() {
        try {
            if (IN_ENTRY_ID_ARRAY != null && IN_ENTRY_ID_ARRAY.size() > 0) {

                for (int i = 0; i < IN_ENTRY_ID_ARRAY.size(); i++) {
                    ArrayList<HashMap<String, String>> hashMap = dataProvider.getEntriesFromCache(IN_TABLE, IN_ENTRY_ID_ARRAY.get(i));
                    if (i == 0) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(hashMap.get(0).get(LATITUDE)), Double.parseDouble(hashMap.get(0).get(
                                LONGITUDE)))));
                    }

                    final HashMap<String, String> entryData = new HashMap<String, String>();
                    try {

                        if (hashMap.get(0).get(LATITUDE) != null && hashMap.get(0).get(LATITUDE).toString().trim().length() > 0 && hashMap.get(0).get(LONGITUDE) != null
                                && hashMap.get(0).get(LONGITUDE).toString().trim().length() > 0) {
                            JSONArray fields = new JSONArray(hashMap.get(0).get(FIELD));
                            for (int j=0;j<fields.length();j++){
                                JSONObject field = fields.getJSONObject(j);
                                if (field.getString(LABELID).equalsIgnoreCase(FIELDLOGO)) {
                                    entryData.put("imgLogo", field.getString(VALUE));
                                }else if (field.getString(LABELID).equalsIgnoreCase(FIELDADDRESS)) {
                                    entryData.put("txtAddress", field.getString(VALUE));
                                }
                            }

                            entryData.put("txtTitle", hashMap.get(0).get(TITLE));
                            entryData.put(ID, hashMap.get(0).get(ID));
                            entryData.put(LATITUDE, hashMap.get(0).get(LATITUDE));
                            entryData.put(LONGITUDE, hashMap.get(0).get(LONGITUDE));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(hashMap.get(0).get(LATITUDE)), Double.parseDouble(hashMap.get(
                                    0).get(LONGITUDE)))));
                            placeMarker(entryData, 0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } else {
                googleMap.setMyLocationEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to place markers at specific latitude and longitude
     *
     * @param entryData
     *            represented entry data which contains all the latitude and
     *            longitude which will displayed on map.
     */

    private void placeMarker(final HashMap<String, String> entryData, final int FrameDrawable) {
        markerHashMap.put(
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.sobipro_map_pin)).title(entryData.get("txtTitle"))
                        .position(new LatLng(Double.parseDouble(entryData.get(LATITUDE)), Double.parseDouble(entryData.get(LONGITUDE))))), entryData);



    }

    /**
     * This method is used to combine frame image and entry image and make one
     * image which will used to display as a marker.
     *
     * @param frame
     *            represented frame image.
     * @param image
     *            represented entry image.
     * @return
     */

    public Bitmap combineImages(Bitmap frame, Bitmap image) {

        bitmapScale = Bitmap.createScaledBitmap(image, convertSizeToDeviceDependent(45), convertSizeToDeviceDependent(40), true);

        bitmapCreate = Bitmap.createBitmap(frame.getWidth(), frame.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(bitmapCreate);

        comboImage.drawBitmap(bitmapScale, convertSizeToDeviceDependent(7), convertSizeToDeviceDependent(7), null);
        comboImage.drawBitmap(frame, 0, 0, null);
        if (frame != null) {
            try {
                bitmapScale.recycle();
                frame.recycle();
                image.recycle();
                bitmapScale = null;
                frame = null;
                image = null;
            } catch (Throwable e) {
            }
        }
        return bitmapCreate;
    }

    /**
     * Custom class to handle Mutable Data.
     */
    private static class MutableData {

        private int value;
        private LatLng position;

        public MutableData(int value, LatLng position) {
            this.value = value;
            this.position = position;
        }
    }
}
