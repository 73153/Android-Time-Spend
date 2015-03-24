package com.ijoomer.topcarlondon.src;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

import com.ijoomer.common.classes.IjoomerBookSuperMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.classes.ViewHolder;
import com.ijoomer.customviews.IjoomerListView;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

/**
 * This Class Contains All Method Related To IjoomerRegistrationStep1Activity.
 * 
 * @author tasol
 * 
 */
public class BookCarAreaCoverdListActivity extends IjoomerBookSuperMaster {

	private IjoomerListView lstMyBooking;

	private ProgressBar pbrGetAreaCover;

	private BookCarDataProvider provider;
	private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
	private SmartListAdapterWithHolder adapter;

	/**
	 * Overrides methods
	 */

	@Override
	public int setLayoutId() {
		return R.layout.book_car_mybooking_list;
	}

	@Override
	public void initComponents() {

		lstMyBooking = (IjoomerListView) findViewById(R.id.lstMyBooking);
		pbrGetAreaCover = (ProgressBar) findViewById(R.id.pbrGetAreaCover);

		provider = new BookCarDataProvider(this);
	}

	@Override
	public void prepareViews() {
		getAreaCoverList();
	}

	@Override
	public void setActionListeners() {

		lstMyBooking.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> row = (HashMap<String, String>) listData.get(pos).getValues().get(0);
				try {
					loadNew(BookCarAreaCoverdDetailActivity.class, BookCarAreaCoverdListActivity.this, false, "IN_AREA_COVERD_DATA", row);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void getAreaCoverList() {
		pbrGetAreaCover.setVisibility(View.VISIBLE);
		provider.getAreaCoverd(new WebCallListener() {

			@Override
			public void onProgressUpdate(int progressCount) {

			}

			@Override
			public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
				pbrGetAreaCover.setVisibility(View.GONE);
				if (responseCode == 200) {
					prepareListData(data1);
					adapter = getListAdapter();
					lstMyBooking.setAdapter(adapter);
				} else {
					responseMessageHandler(responseCode, true);
				}

			}
		});

	}

	private void prepareListData(ArrayList<HashMap<String, String>> data) {
		if (data != null && data.size() > 0) {
			for (HashMap<String, String> hashMap : data) {
				SmartListItem item = new SmartListItem();
				item.setItemLayout(R.layout.book_car_areacoverd_list_item);
				ArrayList<Object> obj = new ArrayList<Object>();
				obj.add(hashMap);
				item.setValues(obj);
				listData.add(item);
			}
		}
	}

	private SmartListAdapterWithHolder getListAdapter() {
		SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(BookCarAreaCoverdListActivity.this, R.layout.book_car_areacoverd_list_item, listData, new ItemView() {
			@Override
			public View setItemView(final int position, View v, SmartListItem item, final ViewHolder holder) {

				holder.txtAreaCoverdTitle = (IjoomerTextView) v.findViewById(R.id.txtAreaCoverdTitle);

				@SuppressWarnings("unchecked")
				final HashMap<String, String> row = (HashMap<String, String>) item.getValues().get(0);

				holder.txtAreaCoverdTitle.setText(row.get("area_name"));

				return v;
			}

			@Override
			public View setItemView(int position, View v, SmartListItem item) {
				return null;
			}
		});
		return adapterWithHolder;
	}
	
	private void responseMessageHandler(final int responseCode, final boolean finishActivityOnConnectionProblem) {

		IjoomerUtilities.getCustomOkDialog(getString(R.string.area_covered), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
				getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

					@Override
					public void NeutralMethod() {
						if (responseCode == 599 && finishActivityOnConnectionProblem) {
							finish();
						}
					}
				});

	}

}