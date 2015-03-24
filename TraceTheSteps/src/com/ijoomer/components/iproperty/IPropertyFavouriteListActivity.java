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
import com.ijoomer.tracethesteps.src.R;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertMagnatic;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

public class IPropertyFavouriteListActivity extends IPropertyMasterActivity {
	private String IN_OBJ;
	private SmartListAdapterWithHolder adapter;
	private AQuery androidQuery;
	private IjoomerButton btnDelete;
	private String limit;
	private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
	private LinearLayout listFooter;
	private ListView lstFavourite;
	private IPropertyFavouriteDataProvider provider;

	private void getFavouriteList() {
		if (isLogin()) {
			final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.iproperty_favourite_list));
			provider.getFavouriteList(limit, new WebCallListener() {
				public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
					if (responseCode == 200) {
						prepareList(data1, false);
						adapter = getListAdapter();
						lstFavourite.setAdapter(adapter);
					} else {
						prepareList(data1, false);
						adapter = getListAdapter();
						lstFavourite.setAdapter(adapter);
						responseErrorMessageHandler(responseCode, true);
					}
				}

				public void onProgressUpdate(int paramAnonymousInt) {
					proSeekBar.setProgress(paramAnonymousInt);
				}
			});
		} else {
			prepareList(provider.getFavouriteListDB(), false);
			adapter = getListAdapter();
			lstFavourite.setAdapter(adapter);
		}
	}

	private SmartListAdapterWithHolder getListAdapter() {
		SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(this, R.layout.iproperty_list_item, listData, new ItemView() {
			public View setItemView(int pos, View view, SmartListItem smartItem) {
				return null;
			}

			public View setItemView(final int pos, View view, SmartListItem smartItem, ViewHolder holder) {
				holder.iPropertyimgSearchCategoryImage = ((ImageView) view.findViewById(R.id.imgSearchCategoryImage));
				holder.iPropertyimgFavouriteRemove = ((ImageView) view.findViewById(R.id.imgFavouriteRemove));
				holder.iPropertylnrProperty = ((LinearLayout) view.findViewById(R.id.lnrProperty));
				holder.iPropertytxtSearchCategoryAddress = ((IjoomerTextView) view.findViewById(R.id.txtSearchCategoryAddress));
				holder.iPropertytxtSearchCategoryTitle = ((IjoomerTextView) view.findViewById(R.id.txtSearchCategoryTitle));

				@SuppressWarnings("unchecked")
				final HashMap<String, String> row = (HashMap<String, String>) smartItem.getValues().get(0);

				androidQuery.id(holder.iPropertyimgSearchCategoryImage).image((String) row.get(IMAGE), true, true);
				if (row.containsKey(ISDELETE) && row.get(ISDELETE).equals("1")) {
					holder.iPropertyimgFavouriteRemove.setVisibility(View.VISIBLE);
				} else {
					holder.iPropertyimgFavouriteRemove.setVisibility(View.GONE);
				}

				holder.iPropertytxtSearchCategoryAddress.setText(prepareAddress(new String[] { row.get(STREET_NUM), row.get(STREET), row.get(STREET2) }));
				holder.iPropertytxtSearchCategoryTitle.setText((CharSequence) row.get(TITLE));

				holder.iPropertylnrProperty.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						try {
							loadNew(IPropertyDetailsActivity.class, IPropertyFavouriteListActivity.this, false, "IN_OBJ", getIntent().getStringExtra("IN_OBJ"), "IN_PROPERTY_ID",
									row.get(ID));
						} catch (Throwable e) {
							e.printStackTrace();
						}
					}
				});

				holder.iPropertyimgFavouriteRemove.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						IjoomerUtilities.getCustomConfirmDialog(getString(R.string.iproperty_favourite_remove), getString(R.string.are_you_sure), getString(R.string.yes),
								getString(R.string.no), new CustomAlertMagnatic() {
									public void NegativeMethod() {
									}

									public void PositiveMethod() {
										if (isLogin()) {
											provider.deleteToFavourite(row.get(ID), new WebCallListener() {
												public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
													if (responseCode == 200) {
														adapter.remove((SmartListItem) adapter.getItem(pos));
														if (adapter.getCount() > 0) {
															IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_favourite_remove),
																	getString(R.string.iproperty_remove_favourite_sccressfully), getString(R.string.ok),
																	R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

																		@Override
																		public void NeutralMethod() {
																		}
																	});
														} else {
															btnDelete.setVisibility(View.GONE);
															IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_favourite_remove), getString(getResources()
																	.getIdentifier("code204", "string", getPackageName())), getString(R.string.ok), R.layout.ijoomer_ok_dialog,
																	new CustomAlertNeutral() {

																		@Override
																		public void NeutralMethod() {
																		}
																	});
														}
													} else {
														responseErrorMessageHandler(responseCode, false);
													}
												}

												public void onProgressUpdate(int paramAnonymous4Int) {
												}
											});
										} else {
											if (provider.removeToFavouriteList((String) row.get(ID))) {
												adapter.remove((SmartListItem) adapter.getItem(pos));
												if (adapter.getCount() > 1) {
													IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_favourite_remove),
															getString(R.string.iproperty_remove_favourite_sccressfully), getString(R.string.ok), R.layout.ijoomer_ok_dialog,
															new CustomAlertNeutral() {

																@Override
																public void NeutralMethod() {
																}
															});
												} else {
													btnDelete.setVisibility(View.GONE);
													IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_favourite_remove), getString(getResources()
															.getIdentifier("code204", "string", getPackageName())), getString(R.string.ok), R.layout.ijoomer_ok_dialog,
															new CustomAlertNeutral() {

																@Override
																public void NeutralMethod() {
																}
															});
												}
											} else {
												IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_favourite_remove),
														getString(R.string.iproperty_remove_favourite_unsccressfully), getString(R.string.ok), R.layout.ijoomer_ok_dialog,
														new CustomAlertNeutral() {

															@Override
															public void NeutralMethod() {
															}
														});
											}
										}
									}
								});

					}
				});

				holder.iPropertyimgSearchCategoryImage.setOnClickListener(new View.OnClickListener() {
					public void onClick(View paramAnonymous2View) {
						try {
							loadNew(IPropertyDetailsActivity.class, IPropertyFavouriteListActivity.this, false, "IN_OBJ", getIntent().getStringExtra("IN_OBJ"), "IN_PROPERTY_ID",
									row.get(ID));
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
				row.put(ISDELETE, "0");
				obj.add(row);
				item.setValues(obj);
				if (isAppend) {
					adapter.add(item);
				} else {
					listData.add(item);
				}
			}
			btnDelete.setVisibility(View.VISIBLE);
		} else {
			listData.clear();
			btnDelete.setVisibility(View.GONE);
			IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_favourite_list), getString(getResources().getIdentifier("code204", "string", getPackageName())),
					getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

						@Override
						public void NeutralMethod() {
						}
					});
		}
	}

	private void responseErrorMessageHandler(final int responseCode, final boolean isFinish) {
		IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_favourite_list),
				getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.ok), R.layout.ijoomer_ok_dialog,
				new CustomAlertNeutral() {

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
		lstFavourite = (ListView) findViewById(R.id.lstFavourite);
		lstFavourite.addFooterView(listFooter, null, false);
		btnDelete = ((IjoomerButton) findViewById(R.id.btnDelete));
		androidQuery = new AQuery(this);
		provider = new IPropertyFavouriteDataProvider(this);
	}

	public void listFooterInvisible() {
		listFooter.setVisibility(8);
	}

	public void listFooterVisible() {
		listFooter.setVisibility(0);
	}

	public void prepareViews() {

		try {
			limit = getIntent().getStringExtra("IN_OBJ") != null ? new JSONObject(IN_OBJ).getJSONObject("itemdata").getString("propertyFavouritesLimit") : "5";
		} catch (Throwable e) {
			e.printStackTrace();
			limit = "5";
		}

		getFavouriteList();
	}

	public void setActionListeners() {
		btnDelete.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				if (btnDelete.getText().toString().equals(getString(R.string.iproperty_delete))) {
					btnDelete.setText(getString(R.string.cancel));
					for (SmartListItem item : listData) {
						((HashMap<String, String>) item.getValues().get(0)).put("isDelete", "1");
					}
				} else {
					btnDelete.setText(getString(R.string.iproperty_delete));
					for (SmartListItem item : listData) {
						((HashMap<String, String>) item.getValues().get(0)).put("isDelete", "0");
					}
				}
				adapter.notifyDataSetChanged();
			}
		});

		lstFavourite.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (isLogin() && firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount > 1) {
					if (!provider.isCalling() && provider.hasNextPage()) {
						listFooterVisible();
						provider.getFavouriteList(limit, new WebCallListener() {
							public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
								listFooterInvisible();
								if (responseCode == 200) {
									prepareList(data1, true);
								} else {
									responseErrorMessageHandler(responseCode, true);
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
		return R.layout.iproperty_favourite;
	}
}