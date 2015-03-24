package com.eosos.components.main;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;
import com.eosos.common.classes.IjoomerUtilities;
import com.eosos.common.classes.ViewHolder;
import com.eosos.custom.interfaces.IjoomerSharedPreferences;
import com.eosos.customviews.IjoomerAutoCompleteTextView;
import com.eosos.customviews.IjoomerRatingBar;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.library.EososDataProvider;
import com.eosos.src.R;
import com.eosos.weservice.WebCallListenerWithCacheInfo;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartFragment;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

public class EososNearByEntryListFragment extends SmartFragment implements EososTagHolder, IjoomerSharedPreferences {

    private ListView lstClubs;
    private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
    public ArrayList<HashMap<String, String>> clubsNearBy = new ArrayList<HashMap<String, String>>();
    private SmartListAdapterWithHolder listAdapterWithHolder;
    private ArrayList<HashMap<String, String>> autoCompleteResultList;
    private IjoomerAutoCompleteTextView edtClub;
    public boolean DistanceInKms = false;
    private EososDataProvider dataProvider;

    ArrayList<HashMap<String, String>> data;
    private String latitude, longitude;

    private AQuery aQuery;

    public EososNearByEntryListFragment(ArrayList<HashMap<String, String>> data) {
        this.data = data;
    }

    @Override
    public int setLayoutId() {
        return R.layout.eosos_near_by_entry_fragment;
    }

    @Override
    public void initComponents(View currentView) {
        lstClubs = (ListView) currentView.findViewById(R.id.directoryList);
        lstClubs.setSelector(R.drawable.list_item_selector);
        aQuery = new AQuery(getActivity());
        edtClub = (IjoomerAutoCompleteTextView) currentView.findViewById(R.id.edtClub);
        lstClubs.setFastScrollEnabled(true);
        ((ImageView) ((SmartActivity) getActivity()).getHeaderView().findViewById(R.id.imgDirection)).setVisibility(View.GONE);
        dataProvider = new EososDataProvider(getActivity());
    }

    @Override
    public void prepareViews(View currentView) {
        edtClub.setAdapter(new autoCompleteAdapter(getActivity(), R.layout.ijoomer_spinner_dropdown_item));
        latitude = ((SmartActivity) getActivity()).getLatitude();
        longitude = ((SmartActivity) getActivity()).getLongitude();
        lstClubs.setFastScrollEnabled(true);
        prepareList(data);
        listAdapterWithHolder = getListAdapter(listData);
        lstClubs.setAdapter(listAdapterWithHolder);
    }

    private class autoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

        public autoCompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return autoCompleteResultList.size();
        }

        @Override
        public String getItem(int position) {
            return autoCompleteResultList.get(position).get("title");
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(final CharSequence constraint) {
                    final FilterResults filterResults = new FilterResults();
                    autoCompleteResultList = new ArrayList<HashMap<String, String>>();
                    if (constraint != null && constraint.toString().trim().length() > 1) {

                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).get("title").toLowerCase().contains(constraint.toString().toLowerCase())) {
                                autoCompleteResultList.add(data.get(i));
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


    @Override
    public void setActionListeners(View currentView) {
        edtClub.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && (autoCompleteResultList == null || autoCompleteResultList.size() == 0)) {
                    edtClub.setError("No match found");
                    return true;
                }
                return false;
            }
        });
        edtClub.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long arg3) {

                try {
                    if (autoCompleteResultList != null && (HashMap<String, String>) autoCompleteResultList.get(pos) != null) {
                        ((SmartActivity) getActivity()).loadNew(EososEntryDetailActivityNew.class, getActivity(), false, "IN_ID",
                                ((HashMap<String, String>) autoCompleteResultList.get(pos)).get("id"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        lstClubs.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                try {
                    ((SmartActivity) getActivity()).loadNew(EososEntryDetailActivityNew.class, getActivity(), false, "IN_ID", data.get(arg2).get("id"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public ArrayList<HashMap<String, String>> getClubs() {
        return clubsNearBy;
    }


    public void prepareList(ArrayList<HashMap<String, String>> data) {
        if (data != null) {
            listData.clear();
            clubsNearBy.clear();

            for (int i = 0; i < data.size(); i++) {
                HashMap<String, String> hashMap = data.get(i);
                SmartListItem item = new SmartListItem();
                item.setItemLayout(R.layout.eosos_search_list_item);
                ArrayList<Object> obj = new ArrayList<Object>();
                obj.add(hashMap);

                item.setValues(obj);
                clubsNearBy.add(hashMap);
                listData.add(item);
            }
        }
    }

    public SmartListAdapterWithHolder getListAdapter(final ArrayList<SmartListItem> listData) {

        SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(getActivity(), R.layout.eosos_search_list_item, listData, new ItemView() {

            @Override
            public View setItemView(int position, View v, SmartListItem item, final ViewHolder holder) {

                holder.eososTxtTitle = (IjoomerTextView) v.findViewById(R.id.eososTxtTitle);
                holder.eososTxtLocation = (IjoomerTextView) v.findViewById(R.id.eososTxtLocation);
                holder.eososTxtDistance = (IjoomerTextView) v.findViewById(R.id.eososTxtDistance);
                holder.eososImgClub = (ImageView) v.findViewById(R.id.eososImgClub);
                holder.eososTxtCity = (IjoomerTextView) v.findViewById(R.id.eososTxtCity);
                holder.rtbRating = (IjoomerRatingBar) v.findViewById(R.id.rtbRating);
                holder.eososProgress = (ProgressBar) v.findViewById(R.id.progress);
                holder.divider = (View) v.findViewById(R.id.divider);
                holder.divider.setVisibility(View.VISIBLE);
                holder.eososTxtCity.setVisibility(View.GONE);
                holder.eososProgress.setVisibility(View.GONE);

                @SuppressWarnings("unchecked")
                HashMap<String, String> value = (HashMap<String, String>) item.getValues().get(0);

                holder.eososTxtLocation.setText(Html.fromHtml(value.get("city")).toString().trim());
                holder.eososTxtTitle.setText(value.get("title").trim());

                if(DistanceInKms){
                    holder.eososTxtDistance.setText(value.get("distanceKm") + " Kms");
                }else{
                    holder.eososTxtDistance.setText(value.get("distanceMiles") + " Miles");
                }

                try {
                    holder.rtbRating.setStarRating(Float.parseFloat(value.get(AVERAGERATING)) / 2);
                    holder.rtbRating.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(value.get("image_thumb").trim().length()>0){
                    holder.eososProgress.setVisibility(View.GONE);
                    holder.eososImgClub.setImageBitmap(BitmapFactory.decodeFile(value.get("image_thumb")));
                }else {
                    holder.eososProgress.setVisibility(View.VISIBLE);
                    aQuery.ajax(value.get("image1"), Bitmap.class, 0, new AjaxCallback<Bitmap>() {
                        @Override
                        public void callback(String url, Bitmap object, AjaxStatus status) {
                            super.callback(url, object, status);
                            try {
                                String thumbPath = AQUtility.getCacheDir(getActivity()) + "/" + System.currentTimeMillis() + ".png";
                                final int THUMBNAIL_SIZE = 100;
                                object = Bitmap.createScaledBitmap(object, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);
                                FileOutputStream outputStream = new FileOutputStream(thumbPath);
                                object.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                dataProvider.createThumb(url, thumbPath);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            if (object != null)
                                object.recycle();
                            holder.eososProgress.setVisibility(View.GONE);
                        }
                    });
                }
//                try {
//                    if(aQuery.getCachedImage(value.get("image1"))==null){
//                        holder.eososProgress.setVisibility(View.VISIBLE);
//                        aQuery.id(holder.eososImgClub).progress(holder.eososProgress).image(value.get("image1"),true,true);
//                    }else{
//                        holder.eososProgress.setVisibility(View.GONE);
//                        aQuery.id(holder.eososImgClub).image(aQuery.getCachedImage(value.get("image1")));
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                return v;
            }

            @Override
            public View setItemView(int position, View v, SmartListItem item) {
                return null;
            }
        });
        return adapterWithHolder;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    public void changeDistanceUnit(boolean InKms) {
        if (listData.size() > 0) {
            DistanceInKms = InKms;
            listAdapterWithHolder.notifyDataSetChanged();
        }

    }

    private double getDistance(double fromLat, double fromLng, double toLat, double toLng) {
        try {
            float[] dist = new float[1];
            Location.distanceBetween(fromLat, fromLng, toLat, toLng, dist);
            if (DistanceInKms)
                return (dist[0] * 0.001);
            else
                return (dist[0] * 0.00062137119); // / 100000;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
