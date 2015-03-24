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

import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.classes.ViewHolder;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.library.iproperty.IPropertyDetailDataProvider;
import com.ijoomer.tracethesteps.src.R;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertMagnatic;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

public class IPropertyCommentListActivity extends IPropertyMasterActivity {
	private String IN_COMMENT_LIMIT;
	private HashMap<String, String> IN_PROPERTY_DETAIL;
	private SmartListAdapterWithHolder adapter;
	private IjoomerButton btnAdd;
	private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
	private LinearLayout listFooter;
	private ListView lstComment;
	private SeekBar proSeekBar;
	private IPropertyDetailDataProvider provider;
	private IjoomerTextView txtPrpertyTitle;

	private void getComment(final boolean isShowProgress) {
		provider.restorePagingSettings();

		if (isShowProgress)
			proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.iproperty_comment_list));

		provider.getComments((String) IN_PROPERTY_DETAIL.get(ID), IN_COMMENT_LIMIT, new WebCallListener() {
			public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
				if (responseCode == 200) {
					prepareList(data1, false);
					adapter = getListAdapter();
					lstComment.setAdapter(adapter);
				} else {
					responseMessageHandler(responseCode, true);
				}
			}

			public void onProgressUpdate(int progressCount) {
				if (isShowProgress) {
					proSeekBar.setProgress(progressCount);
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void getIntentData() {
		IN_PROPERTY_DETAIL = ((HashMap<String, String>) (getIntent().getSerializableExtra("IN_PROPERTY_DETAIL") != null ? getIntent().getSerializableExtra("IN_PROPERTY_DETAIL")
				: new HashMap<String, String>()));
		IN_COMMENT_LIMIT = getIntent().getStringExtra("IN_COMMENT_LIMIT") == null ? getIntent().getStringExtra("IN_COMMENT_LIMIT") : "8";
		getComment(true);
	}

	private SmartListAdapterWithHolder getListAdapter() {
		SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(this, R.layout.iproperty_details_comment_list_item, listData, new ItemView() {
			public View setItemView(int pos, View view, SmartListItem smartItem) {
				return null;
			}

			public View setItemView(final int pos, View view, SmartListItem smartItem, ViewHolder holder) {

				holder.iPropertylnrCommentBottom = (LinearLayout) view.findViewById(R.id.lnrCommentBottom);
				holder.iPropertyimgCommentEdit = (ImageView) view.findViewById(R.id.imgCommentEdit);
				holder.iPropertyimgCommentRemove = (ImageView) view.findViewById(R.id.imgCommentRemove);
				holder.iPropertyimgCommentLike = (ImageView) view.findViewById(R.id.imgCommentLike);
				holder.iPropertyimgCommentDislike = (ImageView) view.findViewById(R.id.imgCommentDislike);
				holder.iPropertytxtCommentUser = (IjoomerTextView) view.findViewById(R.id.txtCommentUser);
				holder.iPropertytxtCommentDate = (IjoomerTextView) view.findViewById(R.id.txtCommentDate);
				holder.iPropertytxtComment = (IjoomerTextView) view.findViewById(R.id.txtComment);
				holder.iPropertytxtCommentCount = (IjoomerTextView) view.findViewById(R.id.txtCommentCount);

				@SuppressWarnings("unchecked")
				final HashMap<String, String> row = (HashMap<String, String>) smartItem.getValues().get(0);

				holder.iPropertytxtCommentUser.setText(row.get(USERNAME));
				holder.iPropertytxtCommentDate.setText(row.get(DATE));
				holder.iPropertytxtComment.setText(row.get(COMMENT));
				holder.iPropertytxtCommentCount.setText(row.get(LIKEDISLIKE));
				if (row.get(ISEDIT).equals("1")) {
					holder.iPropertyimgCommentEdit.setVisibility(View.VISIBLE);
				} else {
					holder.iPropertyimgCommentEdit.setVisibility(View.GONE);
				}

				if (row.get(ISDELETE).equals("1")) {
					holder.iPropertyimgCommentRemove.setVisibility(View.VISIBLE);
				} else {
					holder.iPropertyimgCommentRemove.setVisibility(View.GONE);
				}

				if (row.get(ISLIKE).equals("1")) {
					holder.iPropertyimgCommentLike.setVisibility(View.VISIBLE);
					holder.iPropertyimgCommentDislike.setVisibility(View.VISIBLE);
				} else {
					holder.iPropertyimgCommentLike.setVisibility(View.GONE);
					holder.iPropertyimgCommentDislike.setVisibility(View.GONE);
				}

				if (holder.iPropertyimgCommentEdit.getVisibility() == View.GONE && holder.iPropertyimgCommentRemove.getVisibility() == View.GONE
						&& holder.iPropertyimgCommentLike.getVisibility() == View.GONE) {
					holder.iPropertylnrCommentBottom.setVisibility(View.GONE);
				} else {
					holder.iPropertylnrCommentBottom.setVisibility(View.VISIBLE);
				}

				holder.iPropertyimgCommentEdit.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						try {
							loadNew(IPropertyCommentAddActivity.class, IPropertyCommentListActivity.this, false, "IN_PROPERTY_ID", IN_PROPERTY_DETAIL.get(ID), "IN_COMMENT",
									row.get(COMMENT),"IN_COMMENT_ID",row.get(ID));
						} catch (Throwable e) {
							e.printStackTrace();
						}
					}
				});

				holder.iPropertyimgCommentLike.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						provider.likeDislike((String) row.get(ID), "1", new WebCallListener() {
							public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
								if (responseCode == 200) {
									IjoomerApplicationConfiguration.setReloadRequired(true);
									getComment(false);
								} else {
									responseMessageHandler(responseCode, false);
								}
							}

							public void onProgressUpdate(int progressCount) {
							}
						});
					}
				});

				holder.iPropertyimgCommentDislike.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						provider.likeDislike((String) row.get(ID), "0", new WebCallListener() {
							public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
								if (responseCode == 200) {
									IjoomerApplicationConfiguration.setReloadRequired(true);
									getComment(false);
								} else {
									responseMessageHandler(responseCode, false);
								}
							}

							public void onProgressUpdate(int progressCount) {
							}
						});
					}
				});

				holder.iPropertyimgCommentRemove.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						IjoomerUtilities.getCustomConfirmDialog(getString(R.string.iproperty_comment_remove), getString(R.string.are_you_sure), getString(R.string.yes),
								getString(R.string.no), new CustomAlertMagnatic() {
									public void NegativeMethod() {
									}

									public void PositiveMethod() {
										provider.removeComment(row.get(ID), new WebCallListener() {
											public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
												if (responseCode == 200) {
													IjoomerApplicationConfiguration.setReloadRequired(true);
													adapter.remove((SmartListItem) adapter.getItem(pos));
													if (adapter.getCount() == 0)
														IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_comment_list),
																getString(getResources().getIdentifier("code204", "string", getPackageName())), getString(R.string.ok),
																R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

																	@Override
																	public void NeutralMethod() {
																	}
																});
												}
											}

											public void onProgressUpdate(int progressCount) {
											}
										});
									}
								});
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
				item.setItemLayout(R.layout.iproperty_details_comment_list_item);
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
		IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_comment_list), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
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
		lstComment = (ListView) findViewById(R.id.lstComment);
		lstComment.addFooterView(listFooter, null, false);
		txtPrpertyTitle = ((IjoomerTextView) findViewById(R.id.txtPrpertyTitle));
		btnAdd = ((IjoomerButton) findViewById(R.id.btnAdd));
		provider = new IPropertyDetailDataProvider(this);
	}

	public void listFooterInvisible() {
		listFooter.setVisibility(8);
	}

	public void listFooterVisible() {
		listFooter.setVisibility(0);
	}

	protected void onResume() {
		if (IjoomerApplicationConfiguration.isReloadRequired()) {
			IjoomerApplicationConfiguration.setReloadRequired(false);
			getComment(false);
		}
		super.onResume();
	}

	public void prepareViews() {
		getIntentData();
		txtPrpertyTitle.setText(IN_PROPERTY_DETAIL.get(TITLE));
	}
	@Override
	public void setActionListeners() {
		btnAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				try {
					loadNew(IPropertyCommentAddActivity.class, IPropertyCommentListActivity.this, false, "IN_PROPERTY_ID", IN_PROPERTY_DETAIL.get(ID));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		lstComment.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if ((firstVisibleItem + visibleItemCount) >= totalItemCount && totalItemCount > 1) {
					if (!provider.isCalling() && provider.hasNextPage()) {
						listFooterVisible();
						provider.getComments((String) IN_PROPERTY_DETAIL.get("id"), IN_COMMENT_LIMIT, new WebCallListener() {
							public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
								listFooterInvisible();
								if (responseCode == 200) {
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
		return R.layout.iproperty_comment_list;
	}
}