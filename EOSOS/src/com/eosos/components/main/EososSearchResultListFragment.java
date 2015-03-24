package com.eosos.components.main;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;
import com.eosos.common.classes.IjoomerUtilities;
import com.eosos.common.classes.ViewHolder;
import com.eosos.custom.interfaces.IjoomerSharedPreferences;
import com.eosos.customviews.IjoomerRatingBar;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.library.EososDataProvider;
import com.eosos.src.R;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartFragment;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

public class EososSearchResultListFragment extends SmartFragment implements EososTagHolder, IjoomerSharedPreferences {

    private ListView lstClubs;

    private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
    public ArrayList<HashMap<String, String>> clubs = new ArrayList<HashMap<String, String>>();
    private SmartListAdapterWithHolder listAdapterWithHolder;
    private EososDataProvider dataProvider;
    private View listFooter;
    private ArrayList<HashMap<String, String>> data;
    private AQuery aQuery;
    private ArrayList<HashMap<String, String>> IN_FIELD;

    public EososSearchResultListFragment() {
    }

    @Override
    public int setLayoutId() {
        return R.layout.eosos_search_entry_fragment;
    }

    @Override
    public void initComponents(View currentView) {
        lstClubs = (ListView) currentView.findViewById(R.id.directoryList);
        lstClubs.setSelector(R.drawable.list_item_selector);
        aQuery = new AQuery(getActivity());
        listFooter = LayoutInflater.from(getActivity()).inflate(R.layout.ijoomer_list_footer, null);
        lstClubs.addFooterView(listFooter, null, false);
        dataProvider = new EososDataProvider(getActivity());
        lstClubs.setFastScrollEnabled(true);
        ((ImageView) ((SmartActivity) getActivity()).getHeaderView().findViewById(R.id.imgDirection)).setVisibility(View.GONE);
    }

    @Override
    public void prepareViews(View currentView) {
        getIntentData();
        if (IN_FIELD != null) {
            getSearchResultFromCache();
        } else {
            lstClubs.setVisibility(View.GONE);
        }

    }

    public void getSearchResultFromCache() {
        data = dataProvider.getPlannerList(IN_FIELD.get(1).get("value"), IN_FIELD.get(0).get("name"));
        if(data!=null && data.size()>0){
            lstClubs.setFastScrollEnabled(true);
            prepareList(data);
            listAdapterWithHolder = getListAdapter(listData);
            lstClubs.setAdapter(listAdapterWithHolder);
        }else{
            IjoomerUtilities.getCustomOkDialog(getString(R.string.favourites), getString(R.string.code204), getString(R.string.ok), R.layout.ijoomer_ok_dialog,
                    new CustomAlertNeutral() {

                        @Override
                        public void NeutralMethod() {

                        }
                    });
        }
    }


    @Override
    public void setActionListeners(View currentView) {
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
        return clubs;
    }

    public void getIntentData() {
        if (((SmartActivity) getActivity()).getSmartApplication().readSharedPreferences().getString(SP_DEFAULT_LANDING_SCREEN, "").length() > 0) {
            try {
                JSONObject intentData = new JSONObject(((SmartActivity) getActivity()).getSmartApplication().readSharedPreferences().getString(SP_LAST_ACTIVITY_INTENT, ""))
                        .getJSONObject("itemdata");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        IN_FIELD = (ArrayList<HashMap<String, String>>) getActivity().getIntent().getSerializableExtra("IN_FIELD");

    }

    public void prepareList(ArrayList<HashMap<String, String>> data) {
        if (data != null) {
            listData.clear();
            clubs.clear();


            for (int i = 0; i < data.size(); i++) {
                HashMap<String, String> hashMap = data.get(i);

                SmartListItem item = new SmartListItem();
                item.setItemLayout(R.layout.eosos_search_list_item);
                ArrayList<Object> obj = new ArrayList<Object>();
                obj.add(hashMap);
                item.setValues(obj);
                clubs.add(hashMap);
                listData.add(item);
            }
        }
    }



    public SmartListAdapterWithHolder getListAdapter(final ArrayList<SmartListItem> listData) {

        SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(getActivity(), R.layout.eosos_search_list_item, listData,
                new ItemView() {

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
                        holder.rtbRating.setVisibility(View.GONE);
                        holder.eososTxtDistance.setVisibility(View.GONE);
                        holder.eososTxtCity.setVisibility(View.GONE);
                        holder.eososProgress.setVisibility(View.GONE);
                        @SuppressWarnings("unchecked")
                        HashMap<String, String> value = (HashMap<String, String>) item.getValues().get(0);

                        holder.eososTxtLocation.setText(Html.fromHtml(value.get("city")).toString().trim());
                        holder.eososTxtTitle.setText(value.get("title").trim());

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
//                        try {
//                            if(aQuery.getCachedImage(value.get("image1"))==null){
//                                holder.eososProgress.setVisibility(View.VISIBLE);
//                                aQuery.id(holder.eososImgClub).progress(holder.eososProgress).image(value.get("image1"),true,true);
//                            }else{
//                                holder.eososProgress.setVisibility(View.GONE);
//                                aQuery.id(holder.eososImgClub).image(aQuery.getCachedImage(value.get("image1")));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
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

}
