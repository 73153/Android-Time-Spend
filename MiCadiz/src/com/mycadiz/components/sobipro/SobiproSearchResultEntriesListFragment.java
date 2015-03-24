package com.mycadiz.components.sobipro;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycadiz.common.classes.IjoomerMapClusterIconProvider;
import com.mycadiz.common.classes.IjoomerUtilities;
import com.mycadiz.common.classes.ViewHolder;
import com.mycadiz.customviews.IjoomerListView;
import com.mycadiz.customviews.IjoomerTextView;
import com.mycadiz.library.sobipro.SobiproAdvanceSearchFieldsDataProvider;
import com.mycadiz.src.R;
import com.mycadiz.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartFragment;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import pl.mg6.android.maps.extensions.ClusteringSettings;
import pl.mg6.android.maps.extensions.GoogleMap;
import pl.mg6.android.maps.extensions.Marker;
import pl.mg6.android.maps.extensions.SupportMapFragment;

/**
 * This Fragment Contains All Method Related To SobiproEntriesListFragment.
 *
 * @author tasol
 *
 */
public class SobiproSearchResultEntriesListFragment extends SmartFragment implements SobiproTagHolder {
    private String  IN_SECTION_ID, IN_KEYWORD,IN_PRIVIOUS_ACTIVITY;
    private String  IN_CATEGORY;
    private String  IN_TOWN;
    private String  IN_LATITUDE;
    private String  IN_LONGITUDE;
    private IjoomerListView lstEntries;
    private LinearLayout lnrMap;
    private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
    private SmartListAdapterWithHolder listAdapterWithHolder;
    private AQuery androidAQuery;
    private SobiproAdvanceSearchFieldsDataProvider dataProvider;
    private ArrayList<HashMap<String, String>> entryListData;
    private View listFooter;
    private ArrayList<String> ID_ARRAY;
    private SeekBar proSeekBar;
    private String tableName;

    private HashMap<Marker, HashMap<String, String>> markerHashMap;
    private GoogleMap googleMap;
    private static final double[] CLUSTER_SIZES = new double[] { 180, 160, 144, 120, 96 };
    private MutableData[] dataArray = { new MutableData(6, new LatLng(-50, 0)), new MutableData(28, new LatLng(-52, 1)), new MutableData(496, new LatLng(-51, -2)), };
    private Handler handler = new Handler();
    private LatLngBounds.Builder builder;
    private boolean isIntialMapLoaded;
    private Double latitude;
    private Double longitude;


    public SobiproSearchResultEntriesListFragment(String IN_SECTION_ID, String IN_KEYWORD,String IN_CATEGORY,String IN_TOWN,String IN_PRIVIOUS_ACTIVITY,String IN_LATITUDE,String IN_LONGITUDE) {
        this.IN_KEYWORD = IN_KEYWORD;
        this.IN_SECTION_ID = IN_SECTION_ID;
        this.IN_CATEGORY = IN_CATEGORY;
        this.IN_TOWN = IN_TOWN;
        this.IN_PRIVIOUS_ACTIVITY=IN_PRIVIOUS_ACTIVITY;
        this.IN_LATITUDE=IN_LATITUDE;
        this.IN_LONGITUDE=IN_LONGITUDE;
    }
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
    public void onResume() {
        super.onResume();
        handler.post(dataUpdater);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(dataUpdater);
    }

    @Override
    public int setLayoutId() {
        return R.layout.sobipro_entries_fragment;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public void initComponents(View currentView) {

        dataProvider = new SobiproAdvanceSearchFieldsDataProvider(getActivity());
        lstEntries = (IjoomerListView) currentView.findViewById(R.id.lstEntries);
        lnrMap = (LinearLayout) currentView.findViewById(R.id.lnrMap);
        lnrMap.setVisibility(View.VISIBLE);
        listFooter = LayoutInflater.from(getActivity()).inflate(R.layout.ijoomer_list_footer, null);
        lstEntries.addFooterView(listFooter, null, false);
        ID_ARRAY = new ArrayList<String>();
        androidAQuery = new AQuery(getActivity());

        FragmentManager fm = getActivity().getSupportFragmentManager();
        SupportMapFragment f = (SupportMapFragment) fm.findFragmentById(R.id.maps);
        googleMap = f.getExtendedMap();
        googleMap.moveCamera(CameraUpdateFactory.zoomBy(1));
        markerHashMap = new HashMap<Marker, HashMap<String, String>>();

        try{
            googleMap.setClustering(new ClusteringSettings().iconDataProvider(new IjoomerMapClusterIconProvider(getResources())).addMarkersDynamically(true));

            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                private TextView tv;
                {

                    tv = new TextView(getActivity());
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
                            view = LayoutInflater.from(getActivity()).inflate(R.layout.sobipro_map_bubble, null);

                            ImageView imgEntryIcon = (ImageView) view.findViewById(R.id.imgEntryIcon);
                            IjoomerTextView txtTitle = (IjoomerTextView) view.findViewById(R.id.txtTitlePopup);
                            IjoomerTextView txtAddress = (IjoomerTextView) view.findViewById(R.id.txtAddressPopup);


                            if(data.containsKey("imgLogo") && data.get("imgLogo")!=null &&data.get("imgLogo").trim().length()>0){
                                androidAQuery.id(imgEntryIcon).image(data.get("imgLogo"), true, true,0,
                                        R.drawable.sobipro_default_image);
                            }else{
                                androidAQuery.id(imgEntryIcon).image(R.drawable.sobipro_default_image);
                            }
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

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                @Override
                public void onInfoWindowClick(Marker marker) {
                    if (marker.isCluster()) {
                        List<Marker> markers = marker.getMarkers();
                        LatLngBounds.Builder builder = LatLngBounds.builder();
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

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void goToEntryDetail(HashMap<String, String> data) {
        try {
            ArrayList<String> ID_Array = new ArrayList<String>();
            ID_Array.add(data.get(ID));
            ((SmartActivity)getActivity()).loadNew(SobiproEntryDetailActivity.class, getActivity(), false, "IN_ENTRY_ID_ARRAY", ID_Array, "IN_ENTRY_INDEX", 0, "IN_TABLE", tableName, "IN_POS", 0,
                    "IN_PAGELAYOUT", "","IN_PRIVIOUS_ACTIVITY",IN_PRIVIOUS_ACTIVITY);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void prepareViews(View currentView) {
        listFooterInvisible();
        if(Boolean.parseBoolean(getString(R.string.local_database))){
            tableName="entries";
        }else{
            tableName=SOBIPROSEARCHENTRIES;
        }
        getEntries();
    }



    @Override
    public void setActionListeners(View currentView) {
        lstEntries.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(!Boolean.parseBoolean(getString(R.string.local_database))){
                    if (listData.size()>1 && (firstVisibleItem + visibleItemCount) >= totalItemCount && totalItemCount > 1) {
                        listFooterInvisible();
                        if (!dataProvider.isCalling() && dataProvider.hasNextPage()) {
                            listFooterVisible();
                            dataProvider.search(tableName,IN_SECTION_ID,IN_KEYWORD,IN_LATITUDE,IN_LONGITUDE,IN_CATEGORY,IN_TOWN,new WebCallListener() {
                                @Override
                                public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                    try {
                                        listFooterInvisible();
                                        if (responseCode == 200) {
                                            if (data1 != null && data1.size() > 0) {
                                                prepareList(data1, true);
                                                entryListData.addAll(data1);
                                                populateMap();
                                                setUpClusteringViews();
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onProgressUpdate(int progressCount) {

                                }
                            } );
                        }
                    }
                }
            }
        });

    }

    /**
     * Class Methods.
     */


    /**
     * This method is used to make footer visible.
     */

    public void listFooterVisible() {
        listFooter.setVisibility(View.VISIBLE);
    }

    /**
     * This method is used to make footer invisible.
     */

    public void listFooterInvisible() {
        listFooter.setVisibility(View.GONE);
    }
    private double getDistance(double fromLat, double fromLng, double toLat, double toLng) {
        try {
            float[] dist = new float[1];
            Location.distanceBetween(fromLat, fromLng, toLat, toLng, dist);
            // return Math.floor((dist[0] / 1000) * 100) / 100;
            return (dist[0] * 0.00062137119); // / 100000;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * This method used to get entry list.
     */
    private void getEntries() {
        latitude = Double.parseDouble(IN_LATITUDE);
        longitude = Double.parseDouble(IN_LATITUDE);
        if(Boolean.parseBoolean(getString(R.string.local_database))){
            if(IN_KEYWORD.trim().length()>0){
                ArrayList<HashMap<String,String>> entries  = dataProvider.getPreSearchEntriesFromDB(IN_LATITUDE,IN_LONGITUDE,IN_KEYWORD,IN_SECTION_ID);
                if(entries!=null && entries.size()>0){
                    entryListData = entries;
                    prepareList(entries,false);
                    listAdapterWithHolder = getListAdapter(listData);
                    lstEntries.setAdapter(listAdapterWithHolder);
                    populateMap();
                    setUpClusteringViews();
//                    dataProvider.restorePagingSettings();
//                    dataProvider.searchLocal(tableName, IN_SECTION_ID, IN_KEYWORD,IN_LATITUDE,IN_LONGITUDE, IN_CATEGORY, IN_TOWN,new WebCallListener() {
//                        @Override
//                        public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
//                            if (responseCode == 200) {
//                                ArrayList<HashMap<String,String>> entries  = dataProvider.getPreSearchEntriesFromDB(IN_LATITUDE,IN_LONGITUDE,IN_KEYWORD,IN_SECTION_ID);
//                                if(entries!=null && entries.size()>0){
//                                    entryListData = entries;
//                                    prepareList(entries,false);
//                                    listAdapterWithHolder = getListAdapter(listData);
//                                    lstEntries.setAdapter(listAdapterWithHolder);
//                                    populateMap();
//                                    setUpClusteringViews();
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onProgressUpdate(int progressCount) {
//                        }
//                    });
                }else{
                    populateMap();
                    setUpClusteringViews();
                    IjoomerUtilities.getCustomOkDialog(getString(R.string.sobipro_entries_list),
                            getString(getResources().getIdentifier("code204", "string", getActivity().getPackageName())), getString(R.string.ok),
                            R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
                                @Override
                                public void NeutralMethod() {
                                    getActivity().finish();
                                }
                            });
                }

            }else{
                ArrayList<HashMap<String,String>> entries = dataProvider.getSearchEntriesCategoryTownFromDB(IN_LATITUDE,IN_LONGITUDE,IN_CATEGORY,IN_TOWN,IN_SECTION_ID);

                if(entries!=null && entries.size()>0){
                    entryListData = entries;
                    prepareList(entries, false);
                    listAdapterWithHolder = getListAdapter(listData);
                    lstEntries.setAdapter(listAdapterWithHolder);
                    populateMap();
                    setUpClusteringViews();
//                    dataProvider.restorePagingSettings();
//                    dataProvider.searchLocal(tableName, IN_SECTION_ID, IN_KEYWORD,IN_LATITUDE,IN_LONGITUDE, IN_CATEGORY, IN_TOWN,new WebCallListener() {
//                        @Override
//                        public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
//                            if (responseCode == 200) {
//                                ArrayList<HashMap<String,String>> entries = dataProvider.getSearchEntriesCategoryTownFromDB(IN_LATITUDE,IN_LONGITUDE,IN_CATEGORY,IN_TOWN,IN_SECTION_ID);
//
//                                if(entries!=null && entries.size()>0){
//                                    entryListData = entries;
//                                    prepareList(entries, false);
//                                    listAdapterWithHolder = getListAdapter(listData);
//                                    lstEntries.setAdapter(listAdapterWithHolder);
//                                    populateMap();
//                                    setUpClusteringViews();
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onProgressUpdate(int progressCount) {
//                        }
//                    });
                }else{
                    populateMap();
                    setUpClusteringViews();
                    IjoomerUtilities.getCustomOkDialog(getString(R.string.sobipro_entries_list),
                            getString(getResources().getIdentifier("code204", "string", getActivity().getPackageName())), getString(R.string.ok),
                            R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
                                @Override
                                public void NeutralMethod() {
                                    getActivity().finish();
                                }
                            });
                }
            }
        }else{
            dataProvider.restorePagingSettings();
            proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.dialog_loading_sending_request));
            dataProvider.search(tableName, IN_SECTION_ID, IN_KEYWORD,IN_LATITUDE,IN_LONGITUDE, IN_CATEGORY, IN_TOWN,new WebCallListener() {
                @Override
                public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                    if (responseCode == 200) {
                        if(data1 != null && data1.size()>0){
                            entryListData = data1;
                            prepareList(data1, false);
                            listAdapterWithHolder = getListAdapter(listData);
                            lstEntries.setAdapter(listAdapterWithHolder);
                            populateMap();
                            setUpClusteringViews();
                        }else{
                            populateMap();
                            setUpClusteringViews();
                            IjoomerUtilities.getCustomOkDialog(getString(R.string.sobipro_entries_list),
                                    getString(getResources().getIdentifier("code204", "string", getActivity().getPackageName())), getString(R.string.ok),
                                    R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
                                        @Override
                                        public void NeutralMethod() {
                                            getActivity().finish();
                                        }
                                    });
                        }
                    } else {
                        populateMap();
                        setUpClusteringViews();
                        IjoomerUtilities.getCustomOkDialog(getString(R.string.sobipro_entries_list),
                                getString(getResources().getIdentifier("code" + responseCode, "string", getActivity().getPackageName())), getString(R.string.ok),
                                R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
                                    @Override
                                    public void NeutralMethod() {
                                        getActivity().finish();
                                    }
                                });
                    }
                }

                @Override
                public void onProgressUpdate(int progressCount) {
                    proSeekBar.setProgress(progressCount);
                }
            });
        }
    }

    /**
     * This method is used to prepare initial list from response data.
     *
     * @param data
     *            : data from response.
     *
     *
     */
    public void prepareList(ArrayList<HashMap<String, String>> data, boolean append) {
        if (data != null) {
            if (!append) {
                listData.clear();
            }
            int i = 0;
            for (HashMap<String, String> hashMap : data) {

                SmartListItem item = new SmartListItem();
                item.setItemLayout(R.layout.sobipro_entries_item);
                ArrayList<Object> obj = new ArrayList<Object>();
                obj.add(hashMap);
                item.setValues(obj);
                if (append) {
                    listAdapterWithHolder.add(item);
                } else {
                    listData.add(item);
                }
                i++;
            }

        }
    }

    /**
     * List adapter for entry list.
     *
     * @param listData
     *            represented entry data
     * @return represented {@link com.smart.framework.SmartListAdapterWithHolder}
     */

    public SmartListAdapterWithHolder getListAdapter(ArrayList<SmartListItem> listData) {

        SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(getActivity(), R.layout.sobipro_entries_item, listData, new ItemView() {
            @Override
            public View setItemView(final int position, View v, SmartListItem item, ViewHolder holder) {

                holder.lnrEntry = (LinearLayout) v.findViewById(R.id.lnrEntry);
                holder.lnrThingsToDoEntry = (LinearLayout) v.findViewById(R.id.lnrThingsToDoEntry);
                holder.imgEntryIcon = (ImageView) v.findViewById(R.id.imgEntryIcon);
                holder.txtTitle = (IjoomerTextView) v.findViewById(R.id.txtTitle);
                holder.txtThingsToDoTitle = (IjoomerTextView) v.findViewById(R.id.txtThingsToDoTitle);
                holder.txtAddress = (IjoomerTextView) v.findViewById(R.id.txtAddress);
                holder.txtPhone = (IjoomerTextView) v.findViewById(R.id.txtPhone);
                holder.txtDistance = (IjoomerTextView) v.findViewById(R.id.txtDistance);

                @SuppressWarnings("unchecked")
                final HashMap<String, String> value = (HashMap<String, String>) item.getValues().get(0);
                try {
                    if(value.get(SECTION)!=null && value.get(SECTION).equalsIgnoreCase("Things To Do")){
                        holder.lnrEntry.setVisibility(View.GONE);
                        holder.lnrThingsToDoEntry.setVisibility(View.VISIBLE);
                        holder.txtThingsToDoTitle.setText(value.get(TITLE));

                        holder.lnrThingsToDoEntry.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    prepareIDS();
                                    ((SmartActivity) getActivity()).loadNew(SobiproEntryDetailActivity.class, getActivity(), false, "IN_CAT_ID", "0", "IN_ENTRY_ID_ARRAY", ID_ARRAY,
                                            "IN_ENTRY_INDEX", position, "IN_TABLE", tableName, "IN_POS", 0, "IN_PAGELAYOUT", "", "IN_FEATUREDFIRST", "No", "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }else{
                        holder.txtTitle.setText(value.get(TITLE));
                        holder.lnrEntry.setVisibility(View.VISIBLE);
                        holder.lnrThingsToDoEntry.setVisibility(View.GONE);
                        lnrMap.setVisibility(View.VISIBLE);
                        JSONArray fields = new JSONArray(value.get(FIELD));
                        for (int i=0;i<fields.length();i++){
                            JSONObject field = fields.getJSONObject(i);
                            if (field.getString(LABELID).equalsIgnoreCase(FIELDADDRESS)) {
                                if(field.getString(VALUE).trim().length()>0){
                                    holder.txtAddress.setText(field.getString(VALUE));
                                    holder.txtAddress.setVisibility(View.VISIBLE);
                                }else{
                                    holder.txtAddress.setVisibility(View.GONE);
                                }
                            }else if (field.getString(LABELID).equalsIgnoreCase(FIELDTELEPHONENUMBER)) {
                                if(field.getString(VALUE).trim().length()>0){
                                    holder.txtPhone.setText(field.getString(VALUE));
                                    holder.txtPhone.setTag(field.getString(VALUE));
                                    holder.txtPhone.setVisibility(View.VISIBLE);
                                }else{
                                    holder.txtPhone.setVisibility(View.INVISIBLE);
                                }
                            }else if(field.getString(LABELID).equalsIgnoreCase(FIELD_TOWN)){
                                holder.txtAddress.setText(holder.txtAddress.getText()+" "+field.getString(VALUE));
                                holder.txtAddress.setVisibility(View.VISIBLE);
                            }else if (field.getString(LABELID).equalsIgnoreCase(FIELDLOGO)) {
                                if(field.getString(VALUE).trim().length()<=0){
                                    androidAQuery.id(holder.imgEntryIcon).image(getResources().getDrawable(R.drawable.sobipro_default_image));
                                }else if(androidAQuery.getCachedImage(field.getString(VALUE))!=null){
                                    holder.imgEntryIcon.setImageBitmap(androidAQuery.getCachedImage(field.getString(VALUE)));
                                }else{
                                    androidAQuery.id(holder.imgEntryIcon).image(field.getString(VALUE),true,true,((SmartActivity)getActivity()).getDeviceWidth(),R.drawable.sobipro_default_image);
                                }
                            }
                        }

                        holder.txtTitle.setText(value.get(TITLE));
                        try{
                            if(value.get("distance").equals("0")){
                                holder.txtDistance.setVisibility(View.GONE);
                            }else{
                                holder.txtDistance.setText(value.get("distance")+" "+getString(R.string.sobipro_km));
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                            holder.txtDistance.setVisibility(View.GONE);
                        }

                        holder.txtPhone.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + view.getTag().toString()));
                                startActivity(intent);
                            }
                        });
                        holder.txtTitle.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    prepareIDS();
                                    ((SmartActivity) getActivity()).loadNew(SobiproEntryDetailActivity.class, getActivity(), false, "IN_CAT_ID", "0", "IN_ENTRY_ID_ARRAY", ID_ARRAY,
                                            "IN_ENTRY_INDEX", position, "IN_TABLE", tableName, "IN_POS", 0, "IN_PAGELAYOUT", "", "IN_FEATUREDFIRST", "No", "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        holder.imgEntryIcon.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    prepareIDS();
                                    ((SmartActivity) getActivity()).loadNew(SobiproEntryDetailActivity.class, getActivity(), false, "IN_CAT_ID", "0", "IN_ENTRY_ID_ARRAY", ID_ARRAY,
                                            "IN_ENTRY_INDEX", position, "IN_TABLE", tableName, "IN_POS", 0, "IN_PAGELAYOUT", "", "IN_FEATUREDFIRST", "No", "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY==null?"":IN_PRIVIOUS_ACTIVITY);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        holder.txtAddress.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                try {
                                    prepareIDS();
                                    ((SmartActivity) getActivity()).loadNew(SobiproEntryDetailActivity.class, getActivity(), false, "IN_CAT_ID", "0", "IN_ENTRY_ID_ARRAY", ID_ARRAY,
                                            "IN_ENTRY_INDEX", position, "IN_TABLE", tableName, "IN_POS", 0, "IN_PAGELAYOUT", "", "IN_FEATUREDFIRST", "No", "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY==null?"":IN_PRIVIOUS_ACTIVITY);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            /*try {
                                ArrayList<String> ID_ARRAY = new ArrayList<String>();
                                ID_ARRAY.add(value.get(ID));
                                ((SmartActivity) getActivity()).loadNew(SobiproMapActivity.class, getActivity(), false, "IN_ENTRY_ID_ARRAY", ID_ARRAY, "IN_TABLE", tableName, "IN_POS",
                                        0, "IN_PAGELAYOUT", "", "IN_PRIVIOUS_ACTIVITY", IN_PRIVIOUS_ACTIVITY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }*/
                            }
                        });

                        holder.txtDistance.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    if (((SmartActivity) getActivity()).getLatitude() != null && ((SmartActivity) getActivity()).getLatitude().length() > 0) {
                                        //((SmartActivity) getActivity()).loadNew(IjoomerMapDirection.class, getActivity(), false, "IN_DESTINATION_LAT",value.get(LATITUDE),"IN_DESTINATION_LONG",value.get(LONGITUDE));
                                        String uri = "http://maps.google.com/maps?f=d&hl=es&saddr="+((SmartActivity)getActivity()).getLatitude()+","+((SmartActivity)getActivity()).getLongitude()+"&daddr="+value.get(LATITUDE)+","+value.get(LONGITUDE);
                                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                        startActivity(intent);
                                    }
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return v;
            }

            @Override
            public View setItemView(int position, View v, SmartListItem item) {
                return null;
            }
        });
        return adapterWithHolder;
    }

    /**
     * This method is used to prepare ID to get the entry's id which will used
     * in entry detail.
     */

    public void prepareIDS() {
        ID_ARRAY.clear();
        for (SmartListItem row : listData) {

            ID_ARRAY.add(((HashMap<String, String>) row.getValues().get(0)).get(ID));
        }
    }

    /**
     * This method is used to update the Data.
     */

    private void onDataUpdate() {
        try{
            Marker m = googleMap.getMarkerShowingInfoWindow();
            if (m != null && !m.isCluster() && m.getData() instanceof MutableData) {
                m.showInfoWindow();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method is used to set map clustering view.
     */

    private void setUpClusteringViews() {
        try{
            ClusteringSettings clusteringSettings = new ClusteringSettings();
            clusteringSettings.addMarkersDynamically(true);

            clusteringSettings.iconDataProvider(new IjoomerMapClusterIconProvider(getResources()));

            double clusterSize = CLUSTER_SIZES[3];
            clusteringSettings.clusterSize(clusterSize);

            googleMap.setClustering(clusteringSettings);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method is used to populate map view with clustering.
     */
    private void populateMap() {
        try{
            googleMap.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            builder = new LatLngBounds.Builder();
            if (entryListData != null && entryListData.size() > 0) {

                for (int i = 0; i < entryListData.size(); i++) {
                    HashMap<String, String> hashMap = entryListData.get(i);

                    final HashMap<String, String> entryData = new HashMap<String, String>();
                    try {

                        if (hashMap.get(LATITUDE) != null && hashMap.get(LATITUDE).trim().length() > 0 && hashMap.get(LONGITUDE) != null
                                && hashMap.get(LONGITUDE).trim().length() > 0) {
                            JSONArray fields = new JSONArray(hashMap.get(FIELD));
                            for (int j=0;j<fields.length();j++){
                                JSONObject field = fields.getJSONObject(j);
                                if (field.getString(LABELID).equalsIgnoreCase(FIELDLOGO)) {
                                    entryData.put("imgLogo", field.getString(VALUE));
                                }else if (field.getString(LABELID).equalsIgnoreCase(FIELDADDRESS)) {
                                    entryData.put("txtAddress", field.getString(VALUE));
                                }
                            }

                            entryData.put("txtTitle", hashMap.get(TITLE));
                            entryData.put(ID, hashMap.get(ID));
                            entryData.put(LATITUDE, hashMap.get(LATITUDE));
                            entryData.put(LONGITUDE, hashMap.get(LONGITUDE));
                            placeMarker(entryData);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }else{
                builder = new LatLngBounds.Builder();
                builder.include(new LatLng(36.529687900000000000,-6.292656899999997000));
            }
            lnrMap.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if(!isIntialMapLoaded){
                        isIntialMapLoaded=true;
                        LatLngBounds bounds;
                        builder.include(new LatLng(36.741961900000000000,-5.166411899999957000));
                        builder.include(new LatLng(36.509936700000000000,-4.886352299999999000));
                        builder.include(new LatLng(36.693600000000000000,-6.132429999999999000));
                        builder.include(new LatLng(36.529687900000000000,-6.292656899999997000));
                        builder.include(new LatLng(36.012714300000000000,-5.602954400000044000));

                        bounds = builder.build();
                        try {
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, ((SmartActivity) getActivity()).getDeviceWidth(), lnrMap.getMeasuredHeight(), ((SmartActivity) getActivity()).convertSizeToDeviceDependent(20));
                            googleMap.animateCamera(cameraUpdate);
                        }catch (Throwable e){
                            e.printStackTrace();
                        }
                    }
                }
            });
            googleMap.setMyLocationEnabled(true);
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

    private void placeMarker(final HashMap<String, String> entryData) {
        builder.include(new LatLng(Double.parseDouble(entryData.get(LATITUDE)), Double.parseDouble(entryData.get(LONGITUDE))));
        markerHashMap.put(
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.sobipro_map_pin)).title(entryData.get("txtTitle"))
                        .position(new LatLng(Double.parseDouble(entryData.get(LATITUDE)), Double.parseDouble(entryData.get(LONGITUDE))))), entryData);



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
