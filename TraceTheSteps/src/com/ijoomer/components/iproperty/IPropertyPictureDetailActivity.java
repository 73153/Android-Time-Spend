package com.ijoomer.components.iproperty;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.ijoomer.common.classes.ViewHolder;
import com.ijoomer.customviews.IjoomerViewPager;
import com.ijoomer.tracethesteps.src.R;
import com.smart.framework.ItemView;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

public class IPropertyPictureDetailActivity extends IPropertyMasterActivity {
	public static IjoomerViewPager viewPager;
	private ArrayList<HashMap<String, String>> IN_PICTURE_LIST;
	private String IN_SELECTED_INDEX;
	private PageAdapter adapter;
	private AQuery androidQuery;
	private Gallery gallary;
	private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
	private ProgressBar pbrLoadImage;

	private SmartListAdapterWithHolder getGallaryAdapter() {
		SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(this, R.layout.iproperty_picture_detail_gallary_item, listData, new ItemView() {
			public View setItemView(int pos, View view, SmartListItem smartItem) {
				return null;
			}

			public View setItemView(int pos, View view, SmartListItem smartItem, final ViewHolder holder) {
				holder.imgItem = ((ImageView) view.findViewById(R.id.imgItem));
				@SuppressWarnings("unchecked")
				HashMap<String, String> row = (HashMap<String, String>) smartItem.getValues().get(0);
				if (pbrLoadImage.getVisibility() == View.GONE) {
					pbrLoadImage.setVisibility(View.VISIBLE);
				}
				androidQuery.id(holder.imgItem).image(row.get(IMAGE), true, true, getDeviceWidth(), 0, new BitmapAjaxCallback() {
					protected void callback(String paramAnonymous2String, ImageView paramAnonymous2ImageView, Bitmap paramAnonymous2Bitmap, AjaxStatus paramAnonymous2AjaxStatus) {
						super.callback(paramAnonymous2String, paramAnonymous2ImageView, paramAnonymous2Bitmap, paramAnonymous2AjaxStatus);
						holder.imgItem.setImageBitmap(paramAnonymous2Bitmap);
						pbrLoadImage.setVisibility(8);
					}
				});
				view.setLayoutParams(new Gallery.LayoutParams(70, 70));
				return view;
			}
		});
		return adapterWithHolder;
	}

	@SuppressWarnings("unchecked")
	private void getIntentData() {
		IN_PICTURE_LIST = ((ArrayList<HashMap<String, String>>) (getIntent().getSerializableExtra("IN_PICTURE_LIST") != null ? getIntent().getSerializableExtra("IN_PICTURE_LIST")
				: new ArrayList<HashMap<String, String>>()));
		IN_SELECTED_INDEX = getIntent().getStringExtra("IN_SELECTED_INDEX") == null ? "0" : getIntent().getStringExtra("IN_SELECTED_INDEX");

		prepareGallary();
		gallary.setAdapter(getGallaryAdapter());
		adapter = new PageAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		gallary.setSelection(Integer.parseInt(IN_SELECTED_INDEX), true);
		viewPager.setCurrentItem(Integer.parseInt(IN_SELECTED_INDEX), true);
	}

	private void prepareGallary() {
		for (HashMap<String, String> row : IN_PICTURE_LIST) {
			SmartListItem item = new SmartListItem();
			item.setItemLayout(R.layout.iproperty_picture_detail_gallary_item);
			ArrayList<Object> obj = new ArrayList<Object>();
			obj.add(row);
			item.setValues(obj);
			listData.add(item);
		}
	}

	public void initComponents() {
		viewPager = (IjoomerViewPager) findViewById(R.id.viewPager);
		pbrLoadImage = (ProgressBar) findViewById(R.id.pbrLoadImage);
		gallary = (Gallery) findViewById(R.id.gallary);
		gallary.setSpacing(5);
		androidQuery = new AQuery(this);
	}

	public void prepareViews() {
		getIntentData();
	}

	public void setActionListeners() {
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			public void onPageScrollStateChanged(int paramAnonymousInt) {
			}

			public void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2) {
			}

			public void onPageSelected(int paramAnonymousInt) {
				gallary.setSelection(paramAnonymousInt, true);
			}
		});
		gallary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
				IPropertyPictureDetailActivity.viewPager.setCurrentItem(paramAnonymousInt, true);
			}

			public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {
			}
		});
	}

	public int setFooterLayoutId() {
		return 0;
	}

	public int setHeaderLayoutId() {
		return 0;
	}

	public int setLayoutId() {
		return R.layout.iproperty_picture_detail_gallery;
	}

	private class PageAdapter extends FragmentStatePagerAdapter {
		public PageAdapter(FragmentManager arg2) {
			super(arg2);
		}

		public int getCount() {
			return IN_PICTURE_LIST.size();
		}

		public Fragment getItem(int paramInt) {
			System.gc();
			try {
				IPropertyPictureDetailFragment fragment = new IPropertyPictureDetailFragment((String) ((HashMap<String, String>) IN_PICTURE_LIST.get(paramInt)).get("image"));
				return fragment;
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return null;
		}

		public int getItemPosition(Object paramObject) {
			return IN_PICTURE_LIST.size();
		}
	}
}