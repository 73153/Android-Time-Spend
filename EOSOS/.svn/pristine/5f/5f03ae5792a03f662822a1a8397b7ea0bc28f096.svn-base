package com.eosos.components.main;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.eosos.caching.IjoomerCaching;
import com.eosos.common.classes.ViewHolder;
import com.eosos.common.configuration.IjoomerApplicationConfiguration;
import com.eosos.customviews.IjoomerRatingBar;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.library.EososDataProvider;
import com.eosos.library.EososReviewDataProvider;
import com.eosos.src.R;
import com.smart.framework.ItemView;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartFragment;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

public class EososRateListFragment extends SmartFragment implements EososTagHolder {

	private ListView lstRate;
	private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
	private SmartListAdapterWithHolder listAdapterWithHolder;
	private IjoomerRatingBar rtbRating;
	ArrayList<HashMap<String, String>> data;
	float avgRating = 0;
	private IjoomerTextView txtRate;
	private String IN_ID;
	private String IN_SECTION_ID;
	private String IN_TITLE;
    private EososDataProvider eososDataProvider;

	public EososRateListFragment(String reviewRating, String avgRating, String IN_ID, String IN_SECTION_ID, String IN_TITLE) {

		try {
			JSONArray ratingArray = new JSONArray(reviewRating);
			this.data = new IjoomerCaching(getActivity()).parseData(ratingArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			this.avgRating = Float.parseFloat(avgRating);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.IN_ID = IN_ID;
		this.IN_SECTION_ID = IN_SECTION_ID;
		this.IN_TITLE = IN_TITLE;
	}

	@Override
	public int setLayoutId() {
		return R.layout.eosos_rate_list;
	}

	@Override
	public void initComponents(View currentView) {
		lstRate = (ListView) currentView.findViewById(R.id.lstRate);
		rtbRating = (IjoomerRatingBar) currentView.findViewById(R.id.rtbRating);
		txtRate = (IjoomerTextView) currentView.findViewById(R.id.txtRate);
		txtRate.setVisibility(View.GONE);
        eososDataProvider = new EososDataProvider(getActivity());
	}

	@Override
	public void prepareViews(View currentView) {
        if(eososDataProvider.getEntryDetailsFromDb(IN_ID).get(0).get("isRated").equals("1")){
            txtRate.setVisibility(View.GONE);
        }else{
            txtRate.setVisibility(View.VISIBLE);
        }
		if (avgRating > 0) {
			rtbRating.setStarRating(avgRating / 2);
		}

		if (data != null && data.size() > 0) {
			prepareList(data, false, true, 0, 10);
			listAdapterWithHolder = getListAdapter(listData);
			lstRate.setAdapter(listAdapterWithHolder);
		}
	}

	@Override
	public void setActionListeners(View currentView) {

		txtRate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				txtRate.setTextColor(getResources().getColor(R.color.red));
				try {
					((SmartActivity) getActivity()).loadNew(EososAddRatingActivity.class, getActivity(), false, "IN_ID", IN_ID, "IN_SECTION_ID", IN_SECTION_ID, "IN_TITLE",
							IN_TITLE);
					txtRate.setVisibility(View.GONE);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void prepareList(ArrayList<HashMap<String, String>> data, boolean append, boolean fromCache, int pageno, int pagelimit) {
		if (data != null) {
			if (!append) {
				listData.clear();
			}
			for (HashMap<String, String> hashMap : data) {
				SmartListItem item = new SmartListItem();
				item.setItemLayout(R.layout.eosos_rate_list_item);
				ArrayList<Object> obj = new ArrayList<Object>();
				obj.add(hashMap);
				item.setValues(obj);
				if (append) {
					listAdapterWithHolder.add(item);
				} else {
					listData.add(item);
				}
			}

		}
	}

	public SmartListAdapterWithHolder getListAdapter(ArrayList<SmartListItem> listData) {

		SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(getActivity(), R.layout.eosos_rate_list_item, listData, new ItemView() {

			@Override
			public View setItemView(int position, View v, SmartListItem item, final ViewHolder holder) {

				holder.txtName = (IjoomerTextView) v.findViewById(R.id.txtName);
				holder.txtComment = (IjoomerTextView) v.findViewById(R.id.txtComment);
				holder.rtbRating = (IjoomerRatingBar) v.findViewById(R.id.rtbRating);

				@SuppressWarnings("unchecked")
				HashMap<String, String> value = (HashMap<String, String>) item.getValues().get(0);
				holder.txtName.setText(getString(R.string.user_name, value.get(REVIEWUSERNAME)));
				holder.txtComment.setText(value.get(REVIEW));
				try {
					if (value.get(RATINGS) != null) {
						JSONArray ratingArray = new JSONArray(value.get(RATINGS));
						holder.rtbRating.setStarRating(Float.parseFloat(ratingArray.getJSONObject(0).getString(RATINGVOTE)) / 2);
					}
				} catch (JSONException e) {
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

	@Override
	public View setLayoutView() {
		return null;
	}

}
