package com.ijoomer.components.iproperty;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

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
import com.ijoomer.library.iproperty.IPropertyFavouriteDataProvider;
import com.ijoomer.library.iproperty.IPropertySearchDataProvider;
import com.ijoomer.tracethesteps.src.R;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

public class IPropertySearchResultDetailsListActivity extends IPropertyMasterActivity {
	private ArrayList<HashMap<String, String>> IN_MAPLIST = new ArrayList<HashMap<String, String>>();
	private String IN_OBJ;
	private HashMap<String, String> IN_PROPERTY_DATA;
	private SmartListAdapterWithHolder adapter;
	private AQuery androidQuery;
	private IjoomerButton btnFavourite;
	private IjoomerButton btnMap;
	private IjoomerButton btnShare;
	private IPropertyFavouriteDataProvider favouriteProvider;
	private ImageView imgSearchResultDetails;
	private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
	private LinearLayout listFooter;
	private LinearLayout listHeader;
	private ListView lstSearchResult;
	private IPropertySearchDataProvider provider;
	private String resultLimit;
	private String shareLink = "";
	private IjoomerTextView txtResultFound;

	private void getCategoryPropertiesList() {
		final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.iproperty_search_result_detail));
		provider.getCategoryProperties(resultLimit, (String) IN_PROPERTY_DATA.get(CAT_ID), (String) IN_PROPERTY_DATA.get(ID), new WebCallListener() {
			public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
				if (responseCode == 200) {
					shareLink = ((String) data2);
					if (shareLink.trim().length() > 0) {
						btnShare.setVisibility(View.VISIBLE);
					}
					IN_MAPLIST.clear();
					txtResultFound.setText(String.format(getString(R.string.iproperty_search_category_step), 1 + provider.getTotalCount()) + " "
							+ (String) IN_PROPERTY_DATA.get(CATEGORY_TITLE));
					data1.add(0, IN_PROPERTY_DATA);
					IN_MAPLIST.addAll(data1);
					prepareList(data1, false);
					adapter = getListAdapter();
					lstSearchResult.setAdapter(adapter);
				} else {
					if (responseCode == 204) {
						IN_MAPLIST.clear();
						IN_MAPLIST.add(IN_PROPERTY_DATA);
						prepareList(IN_MAPLIST, false);
						adapter = getListAdapter();
						lstSearchResult.setAdapter(adapter);
					} else {
						responseMessageHandler(responseCode, true);
					}
				}
			}

			public void onProgressUpdate(int progressCount) {
				proSeekBar.setProgress(progressCount);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void getIntentData() {
		try {
			IN_PROPERTY_DATA = ((HashMap<String, String>) (getIntent().getSerializableExtra("IN_PROPERTY_DATA") != null ? getIntent().getSerializableExtra("IN_PROPERTY_DATA")
					: new HashMap<String, String>()));
			IN_OBJ = getIntent().getStringExtra("IN_OBJ") != null ? getIntent().getStringExtra("IN_OBJ") : "";
			androidQuery.id(imgSearchResultDetails).image((String) IN_PROPERTY_DATA.get(ICON), true, true);
			resultLimit = new JSONObject(IN_OBJ).getJSONObject(ITEMDATA).getString(CATEGORYRESULTLIMIT);
			txtResultFound.setText(String.format(getString(R.string.iproperty_search_category_step), 1) + " " + IN_PROPERTY_DATA.get(CATEGORY_TITLE));
			btnMap.setVisibility(View.VISIBLE);
			btnFavourite.setVisibility(View.VISIBLE);
			getCategoryPropertiesList();
		} catch (Throwable e) {
			resultLimit = "3";
			e.printStackTrace();
		}
	}

	private SmartListAdapterWithHolder getListAdapter() {
		SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(this, R.layout.iproperty_list_item, listData, new ItemView() {
			public View setItemView(int pos, View view, SmartListItem smartItem) {
				return null;
			}

			public View setItemView(int pos, View view, SmartListItem smartItem, ViewHolder holder) {
				holder.iPropertyimgSearchCategoryImage = ((ImageView) view.findViewById(R.id.imgSearchCategoryImage));
				holder.iPropertytxtSearchCategoryAddress = ((IjoomerTextView) view.findViewById(R.id.txtSearchCategoryAddress));
				holder.iPropertytxtSearchCategoryTitle = ((IjoomerTextView) view.findViewById(R.id.txtSearchCategoryTitle));

				@SuppressWarnings("unchecked")
				final HashMap<String, String> row = (HashMap<String, String>) smartItem.getValues().get(0);

				androidQuery.id(holder.iPropertyimgSearchCategoryImage).image((String) row.get(ICON), true, true);
				holder.iPropertytxtSearchCategoryAddress.setText(prepareAddress(new String[] { row.get(STREET_NUM), row.get(STREET), row.get(STREET2) }));
				holder.iPropertytxtSearchCategoryTitle.setText((CharSequence) row.get(TITLE));

				view.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						try {
							loadNew(IPropertyDetailsActivity.class, IPropertySearchResultDetailsListActivity.this, false, "IN_OBJ", IN_OBJ, "IN_PROPERTY_ID", row.get(ID));
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
		listHeader = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.iproperty_search_result_list_details_header, null);
		imgSearchResultDetails = (ImageView) listHeader.findViewById(R.id.imgSearchResultDetails);
		lstSearchResult = (ListView) findViewById(R.id.lstFavourite);
		txtResultFound = (IjoomerTextView) findViewById(R.id.txtResultFound);
		btnMap = (IjoomerButton) findViewById(R.id.btnMap);
		btnFavourite = (IjoomerButton) findViewById(R.id.btnFavourite);
		btnShare = (IjoomerButton) findViewById(R.id.btnShare);
		lstSearchResult.addFooterView(listFooter, null, false);
		lstSearchResult.addHeaderView(listHeader);
		androidQuery = new AQuery(this);
		provider = new IPropertySearchDataProvider(this);
		favouriteProvider = new IPropertyFavouriteDataProvider(this);
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
		btnShare.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					loadNew(IPropertyShareSmsActivity.class, IPropertySearchResultDetailsListActivity.this, false, "IN_SHARE_LINK", shareLink, "IN_PROPERTY_TITLE",
							((HashMap<String, String>) IN_MAPLIST.get(0)).get(TITLE));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		btnMap.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					loadNew(IPropertyMapActivity.class, IPropertySearchResultDetailsListActivity.this, false, "IN_MAPLIST", IN_MAPLIST, "IN_OBJ",
							getIntent().getStringExtra("IN_OBJ"), "IN_GOTO_DETAILS", true);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		btnFavourite.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (isLogin()) {
					final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.iproperty_search_result_detail));
					favouriteProvider.addCategoryToFavourite(IN_PROPERTY_DATA.get(CAT_ID), new WebCallListener() {
						public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
							if (responseCode == 200) {
								IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_search_result_detail), getString(R.string.iproperty_add_favourite_sccressfully),
										getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
											public void NeutralMethod() {
											}
										});
							} else {
								responseMessageHandler(responseCode, false);
							}
						}

						public void onProgressUpdate(int progressCount) {
							proSeekBar.setProgress(progressCount);
						}
					});
				} else {
					if (favouriteProvider.addToFavouriteList(IN_MAPLIST)) {
						IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_search_result_detail), getString(R.string.iproperty_add_favourite_sccressfully),
								getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
									public void NeutralMethod() {
									}
								});
					} else {
						IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_search_result_detail), getString(R.string.iproperty_add_favourite_unsccressfully),
								getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
									public void NeutralMethod() {
									}
								});
					}
				}
			}
		});

		lstSearchResult.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if ((firstVisibleItem + visibleItemCount) >= totalItemCount && totalItemCount > 1) {
					if (!provider.isCalling() && provider.hasNextPage()) {
						listFooterVisible();
						provider.getCategoryProperties(resultLimit, (String) IN_PROPERTY_DATA.get("cat_id"), (String) IN_PROPERTY_DATA.get("id"), new WebCallListener() {
							public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
								listFooterInvisible();
								if (responseCode == 200) {
									IN_MAPLIST.addAll(data1);
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