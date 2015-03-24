package com.ijoomer.topcarlondon.src;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.ijoomer.common.classes.IjoomerBookSuperMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.classes.ViewHolder;
import com.ijoomer.customviews.IjoomerListView;
import com.ijoomer.customviews.IjoomerTextView;
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
public class BookCarMyBookingListActivity extends IjoomerBookSuperMaster {

	private IjoomerListView lstMyBooking;

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

		provider = new BookCarDataProvider(this);
	}

	@Override
	public void prepareViews() {
		getMyBookingList();
	}

	@Override
	public void setActionListeners() {

		lstMyBooking.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> row = (HashMap<String, String>) listData.get(pos).getValues().get(0);
				try {
					loadNew(BookCarMyBookingActivity.class, BookCarMyBookingListActivity.this, false, "IN_MYBOOKING_DATA", row);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void getMyBookingList() {
		prepareListData(provider.getMyBookingList());
		if (listData.size() == 0) {
			IjoomerUtilities.getCustomOkDialog(getString(R.string.book_now), getString(R.string.yet_booing_data_not_avialable), getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

				@Override
				public void NeutralMethod() {
					try {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("tab", R.drawable.tab_book_now);
						jsonObject.put("tab_active", R.drawable.tab_book_now_active);
						jsonObject.put("itemcaption", "Book Now");
						loadNew(BookCarHomeActivity.class, BookCarMyBookingListActivity.this, true, "IN_OBJ", jsonObject.toString());
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			adapter = getListAdapter();
			lstMyBooking.setAdapter(adapter);
		}
	}

	private void prepareListData(ArrayList<HashMap<String, String>> data) {
		if (data != null && data.size() > 0) {
			for (HashMap<String, String> hashMap : data) {
				SmartListItem item = new SmartListItem();
				item.setItemLayout(R.layout.book_car_mybooking_list_item);
				ArrayList<Object> obj = new ArrayList<Object>();
				obj.add(hashMap);
				item.setValues(obj);
				listData.add(item);
			}
		}
	}

	private SmartListAdapterWithHolder getListAdapter() {
		SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(BookCarMyBookingListActivity.this, R.layout.book_car_mybooking_list_item, listData, new ItemView() {
			@Override
			public View setItemView(final int position, View v, SmartListItem item, final ViewHolder holder) {

				holder.txtPickUpToDropOff = (IjoomerTextView) v.findViewById(R.id.txtPickUpToDropOff);
				holder.txtJourneyDateAndTime = (IjoomerTextView) v.findViewById(R.id.txtJourneyDateAndTime);

				@SuppressWarnings("unchecked")
				final HashMap<String, String> row = (HashMap<String, String>) item.getValues().get(0);
					holder.txtPickUpToDropOff.setText(row.get("pickPoint") + "  " + getString(R.string.to) + "  " + row.get("dropPoint"));
					Calendar cal = IjoomerUtilities.getDateFromString(row.get("journeyDate"));
					SimpleDateFormat format = new SimpleDateFormat("dd MMM,yyyy  HH:mm");
					holder.txtJourneyDateAndTime.setText(format.format(new Date(cal.getTimeInMillis())) + " Hrs");

				return v;
			}

			@Override
			public View setItemView(int position, View v, SmartListItem item) {
				return null;
			}
		});
		return adapterWithHolder;
	}

}