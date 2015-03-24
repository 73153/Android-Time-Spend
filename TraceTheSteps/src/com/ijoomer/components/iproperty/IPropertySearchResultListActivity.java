package com.ijoomer.components.iproperty;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;

import com.androidquery.AQuery;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.classes.ViewHolder;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.library.iproperty.IPropertySearchDataProvider;
import com.ijoomer.tracethesteps.src.R;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

public class IPropertySearchResultListActivity extends IPropertyMasterActivity {
	private ArrayList<HashMap<String, String>> IN_MAP_LIST = new ArrayList<HashMap<String, String>>();
	private String IN_MENUID;
	private String IN_LATITUDE;
	private String IN_LONGITUDE;
	private ArrayList<HashMap<String, String>> IN_SEARCH_FIELD;
	private SmartListAdapterWithHolder adapter;
	private AQuery androidQuery;
	private IjoomerButton btnMap;
	private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
	private LinearLayout listFooter;
	private ListView lstSearchResult;
	private IPropertySearchDataProvider provider;
	private IjoomerTextView txtResultFound;

	@SuppressWarnings("unchecked")
	private void getIntentData() {

		IN_SEARCH_FIELD = ((ArrayList<HashMap<String, String>>) (getIntent().getSerializableExtra("IN_SEARCH_FIELD") != null ? getIntent().getSerializableExtra("IN_SEARCH_FIELD")
				: new ArrayList<HashMap<String, String>>()));
		IN_MENUID = getIntent().getStringExtra("IN_MENUID") != null ? getIntent().getStringExtra("IN_MENUID") : "";
		IN_LATITUDE = getIntent().getStringExtra("IN_LATITUDE") != null ? getIntent().getStringExtra("IN_LATITUDE") : "";
		IN_LONGITUDE = getIntent().getStringExtra("IN_LONGITUDE") != null ? getIntent().getStringExtra("IN_LONGITUDE") : "";
		getSearchResult();
	}

	private SmartListAdapterWithHolder getListAdapter() {
		SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(this, R.layout.iproperty_list_item, listData, new ItemView() {
			public View setItemView(int pos, View view, SmartListItem smartItem) {
				return null;
			}

			public View setItemView(int pos, View view, SmartListItem smartItem, ViewHolder holder) {

				holder.iPropertyimgSearchCategoryImage = ((ImageView) view.findViewById(R.id.imgSearchCategoryImage));
				holder.iPropertylnrProperty = ((LinearLayout) view.findViewById(R.id.lnrProperty));
				holder.iPropertytxtSearchCategoryAddress = ((IjoomerTextView) view.findViewById(R.id.txtSearchCategoryAddress));
				holder.iPropertytxtSearchCategoryTitle = ((IjoomerTextView) view.findViewById(R.id.txtSearchCategoryTitle));

				@SuppressWarnings("unchecked")
				final HashMap<String, String> row = (HashMap<String, String>) smartItem.getValues().get(0);

				androidQuery.id(holder.iPropertyimgSearchCategoryImage).image((String) row.get(ICON), true, true);
				holder.iPropertytxtSearchCategoryAddress.setText(prepareAddress(new String[] { row.get(STREET_NUM), row.get(STREET), row.get(STREET2) }));
				holder.iPropertytxtSearchCategoryTitle.setText((CharSequence) row.get("title"));
				holder.iPropertylnrProperty.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						try {
							loadNew(IPropertyDetailsActivity.class, IPropertySearchResultListActivity.this, false, "IN_OBJ", getIntent().getStringExtra("IN_OBJ"),
									"IN_PROPERTY_ID", row.get(ID));
						} catch (Throwable e) {
							e.printStackTrace();
						}
					}
				});
				holder.iPropertyimgSearchCategoryImage.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						try {
							loadNew(IPropertySearchResultDetailsListActivity.class, IPropertySearchResultListActivity.this, false, "IN_OBJ", getIntent().getStringExtra("IN_OBJ"),
									"IN_PROPERTY_DATA", row);
						} catch (Throwable e) {
							e.printStackTrace();
						}
					}
				});
				return view;
			}
		});
		return adapterWithHolder;
	}

	private void getSearchResult() {

		if (IN_LATITUDE.trim().length() > 0 && IN_LONGITUDE.trim().length() > 0) {
			final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.iproperty_search_result));
			provider.submitSearchForm(IN_MENUID, IN_LATITUDE, IN_LONGITUDE, new WebCallListener() {
				public void onCallComplete(final int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
					if (responseCode == 200) {
						btnMap.setVisibility(View.VISIBLE);
						IN_MAP_LIST.clear();
						IN_MAP_LIST.addAll(data1);
						txtResultFound.setText(String.format(getString(R.string.iproperty_search_result_found), provider.getTotalCount()));
						prepareList(data1, false);
						adapter = getListAdapter();
						lstSearchResult.setAdapter(adapter);
					} else {
						if (responseCode == 204) {
							IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_search_result),
									getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.ok), R.layout.ijoomer_ok_dialog,
									new CustomAlertNeutral() {

										@Override
										public void NeutralMethod() {
											finish();
										}
									});
						} else {
							responseMessageHandler(responseCode, true);
						}

					}
				}

				public void onProgressUpdate(int progresCount) {
					proSeekBar.setProgress(progresCount);
				}
			});
		} else {
			final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.iproperty_search_result));
			provider.submitSearchForm(IN_MENUID, IN_SEARCH_FIELD, new WebCallListener() {
				public void onCallComplete(final int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
					if (responseCode == 200) {
						btnMap.setVisibility(View.VISIBLE);
						IN_MAP_LIST.clear();
						IN_MAP_LIST.addAll(data1);
						txtResultFound.setText(String.format(getString(R.string.iproperty_search_result_found), provider.getTotalCount()));
						prepareList(data1, false);
						adapter = getListAdapter();
						lstSearchResult.setAdapter(adapter);
					} else {
						if (responseCode == 204) {
							IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_search_result),
									getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.ok), R.layout.ijoomer_ok_dialog,
									new CustomAlertNeutral() {

										@Override
										public void NeutralMethod() {
											finish();
										}
									});
						} else {
							responseMessageHandler(responseCode, true);
						}

					}
				}

				public void onProgressUpdate(int progresCount) {
					proSeekBar.setProgress(progresCount);
				}
			});
		}

	}

	private void prepareList(ArrayList<HashMap<String, String>> data, boolean isAppend) {

		if (data != null && data.size() > 0) {
			if (!isAppend) {
				listData.clear();
			}
			for (HashMap<String, String> row : data) {
				SmartListItem item = new SmartListItem();
				item.setItemLayout(R.layout.iproperty_list_item);
				ArrayList<Object> obj = new ArrayList<Object>();
				obj.add(row);
				item.setValues(obj);
				if (isAppend) {
					adapter.add(item);
				} else {
					listData.add(item);
				}
			}
		}
	}

	private void responseMessageHandler(final int responseCode, final boolean isFinish) {
		IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_search_result), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
				getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

					@Override
					public void NeutralMethod() {
						if (responseCode == 599 && isFinish) {
							finish();
						}
					}
				});
	}

	public void initComponents() {
		listFooter = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.ijoomer_list_footer, null);
		lstSearchResult = (ListView) findViewById(R.id.lstFavourite);
		txtResultFound = (IjoomerTextView) findViewById(R.id.txtResultFound);
		btnMap = (IjoomerButton) findViewById(R.id.btnMap);
		lstSearchResult.addFooterView(listFooter, null, false);
		androidQuery = new AQuery(this);
		provider = new IPropertySearchDataProvider(this);
	}

	public void listFooterInvisible() {
		listFooter.setVisibility(View.GONE);
	}

	public void listFooterVisible() {
		listFooter.setVisibility(View.VISIBLE);
	}

	public void prepareViews() {
		getIntentData();
	}

	public void setActionListeners() {
		btnMap.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				try {
					loadNew(IPropertyMapActivity.class, IPropertySearchResultListActivity.this, false, "IN_MAPLIST", IN_MAP_LIST, "IN_OBJ", getIntent().getStringExtra("IN_OBJ"));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});

		lstSearchResult.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if ((firstVisibleItem + visibleItemCount) >= totalItemCount && totalItemCount > 1) {
					if (!provider.isCalling() && provider.hasNextPage()) {
						listFooterVisible();
						provider.submitSearchForm(IN_MENUID, IN_SEARCH_FIELD, new WebCallListener() {
							public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
								listFooterInvisible();
								if (responseCode == 200) {
									IN_MAP_LIST.addAll(data1);
									prepareList(data1, true);
								} else {
									responseMessageHandler(responseCode, false);
								}
							}

							public void onProgressUpdate(int progressCount) {
							}
						});
					}
				}
			}

			public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt) {
			}
		});
	}

	public int setLayoutId() {
		return R.layout.iproperty_search_result;
	}
}