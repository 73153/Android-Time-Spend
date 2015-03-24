package com.ijoomer.components.iproperty;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.androidquery.AQuery;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.custom.interfaces.SelectImageDialogListner;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.library.iproperty.IPropertyDetailDataProvider;
import com.ijoomer.library.iproperty.IPropertyFavouriteDataProvider;
import com.ijoomer.tracethesteps.src.R;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertMagnatic;
import com.smart.framework.CustomAlertNeutral;

public class IPropertyDetailsActivity extends IPropertyMasterActivity {
	private final int CAPTURE_IMAGE_USER_AVATAR = 2;
	private String IN_OBJ;
	private String IN_PROPERTY_ID;
	private ArrayList<HashMap<String, String>> MAP_LIST = new ArrayList<HashMap<String, String>>();
	private final int PICK_IMAGE_USER_AVATAR = 1;
	private AQuery androidQuery;
	private IjoomerButton btnAddComment;
	private IjoomerButton btnAddPicture;
	private IjoomerButton btnCommentMore;
	private IjoomerButton btnFavourite;
	private IjoomerButton btnMap;
	private IjoomerButton btnShare;
	private String commentsLimit;
	private IPropertyDetailDataProvider commentsProvider;
	private IPropertyFavouriteDataProvider favouriteProvider;
	private ImageView imgPropertyDetail;
	private LinearLayout lnrComment;
	private LinearLayout lnrPictureFirstRow;
	private LinearLayout lnrPictureSecondRow;
	private ProgressBar pbrLoadComment;
	private ProgressBar pbrLoadPicture;
	private String picturesLimit;
	private IPropertyDetailDataProvider picturesProvider;
	private HashMap<String, String> propertyData;
	private IPropertyDetailDataProvider provider;
	private IjoomerTextView txtNoComment;
	private IjoomerTextView txtNoPicture;
	private IjoomerTextView txtPropertyDetailAddress;
	private IjoomerTextView txtPropertyOverviewLable;
	private IjoomerTextView txtPropertyOverviewValue;

	private void getComments() {
		pbrLoadComment.setVisibility(0);
		commentsProvider.restorePagingSettings();
		commentsProvider.getComments(IN_PROPERTY_ID, commentsLimit, new WebCallListener() {
			public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
				pbrLoadComment.setVisibility(View.GONE);
				if (responseCode == 200) {
					setComment(data1);
				} else {
					if (responseCode != 204) {
						responseErrorMessageHandler(responseCode, true);
					}
					setComment(new ArrayList<HashMap<String, String>>());
				}
			}

			public void onProgressUpdate(int paramAnonymousInt) {
			}
		});
	}

	private void getPictures() {
		pbrLoadPicture.setVisibility(0);
		picturesProvider.restorePagingSettings();
		picturesProvider.getPictures(IN_PROPERTY_ID, picturesLimit, new WebCallListener() {
			public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
				pbrLoadPicture.setVisibility(View.GONE);
				if (responseCode == 200) {
					setPicture(data1);
				} else {
					if (responseCode != 204) {
						responseErrorMessageHandler(responseCode, true);
					}
					setPicture(new ArrayList<HashMap<String, String>>());
				}
			}

			public void onProgressUpdate(int paramAnonymousInt) {
			}
		});
	}

	private void getPropertyDetail() {
		final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.iproperty_detail));
		provider.getPropertyDetail(IN_PROPERTY_ID, new WebCallListener() {
			public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
				if (responseCode == 200) {
					btnMap.setVisibility(View.VISIBLE);
					btnFavourite.setVisibility(View.VISIBLE);
					btnShare.setVisibility(View.VISIBLE);
					MAP_LIST.clear();
					MAP_LIST.add((HashMap<String, String>) data1.get(0));
					setDetails((HashMap<String, String>) data1.get(0));
				} else {
					responseErrorMessageHandler(responseCode, true);
				}
			}

			public void onProgressUpdate(int progressCount) {
				proSeekBar.setProgress(progressCount);
			}
		});
	}

	private void responseErrorMessageHandler(final int responseCode, final boolean isFinish) {
		IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_detail), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
				getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

					@Override
					public void NeutralMethod() {
						if (responseCode == 599 && isFinish) {
							finish();
						}
					}
				});
	}

	private void setComment(ArrayList<HashMap<String, String>> data) {
		try {
			lnrComment.removeAllViews();

			if (data.size() > 0) {
				lnrComment.setVisibility(View.VISIBLE);
				txtNoComment.setVisibility(View.GONE);
				if (data.size() > 2) {
					btnCommentMore.setVisibility(View.VISIBLE);
				} else {
					btnCommentMore.setVisibility(View.GONE);
				}
				int length = data.size() < 2 ? data.size() : 2;
				for (int i = 0; i < length; i++) {
					HashMap<String, String> comment = data.get(i);
					View view = LayoutInflater.from(this).inflate(R.layout.iproperty_details_comment_list_item, null);
					LinearLayout lnrCommentBottom = (LinearLayout) view.findViewById(R.id.lnrCommentBottom);
					ImageView imgCommentEdit = (ImageView) view.findViewById(R.id.imgCommentEdit);
					ImageView imgCommentRemove = (ImageView) view.findViewById(R.id.imgCommentRemove);
					ImageView imgCommentLike = (ImageView) view.findViewById(R.id.imgCommentLike);
					ImageView imgCommentDislike = (ImageView) view.findViewById(R.id.imgCommentDislike);
					IjoomerTextView txtComment = (IjoomerTextView) view.findViewById(R.id.txtComment);
					IjoomerTextView txtCommentUser = (IjoomerTextView) view.findViewById(R.id.txtCommentUser);
					IjoomerTextView txtCommentDate = (IjoomerTextView) view.findViewById(R.id.txtCommentDate);
					IjoomerTextView txtCommentCount = (IjoomerTextView) view.findViewById(R.id.txtCommentCount);

					txtComment.setText(comment.get(COMMENT));
					txtCommentUser.setText(comment.get(DATE));
					txtCommentDate.setText(comment.get(USERNAME));
					txtCommentCount.setText(comment.get(LIKEDISLIKE));

					if (comment.get(ISEDIT).equals("1")) {
						imgCommentEdit.setVisibility(View.VISIBLE);
					} else {
						imgCommentEdit.setVisibility(View.GONE);
					}

					if (comment.get(ISDELETE).equals("1")) {
						imgCommentRemove.setVisibility(View.VISIBLE);
					} else {
						imgCommentRemove.setVisibility(View.GONE);
					}

					if (comment.get(ISLIKE).equals("1")) {
						imgCommentLike.setVisibility(View.VISIBLE);
						imgCommentDislike.setVisibility(View.VISIBLE);
					} else {
						imgCommentLike.setVisibility(View.GONE);
						imgCommentDislike.setVisibility(View.GONE);
					}

					if (imgCommentEdit.getVisibility() == View.GONE && imgCommentRemove.getVisibility() == View.GONE && imgCommentLike.getVisibility() == View.GONE) {
						lnrCommentBottom.setVisibility(View.GONE);
					} else {
						lnrCommentBottom.setVisibility(View.VISIBLE);
					}

					imgCommentEdit.setTag(comment);
					imgCommentEdit.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							@SuppressWarnings("unchecked")
							HashMap<String, String> row = (HashMap<String, String>) v.getTag();
							try {
								loadNew(IPropertyCommentAddActivity.class, IPropertyDetailsActivity.this, false, "IN_PROPERTY_ID", IN_PROPERTY_ID, "IN_COMMENT", row.get(COMMENT),
										"IN_COMMENT_ID", row.get(ID));
							} catch (Throwable e) {
								e.printStackTrace();
							}
						}
					});

					imgCommentLike.setTag(comment);
					imgCommentLike.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							@SuppressWarnings("unchecked")
							HashMap<String, String> row = (HashMap<String, String>) v.getTag();
							provider.likeDislike((String) row.get(ID), "1", new WebCallListener() {
								public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
									if (responseCode == 200) {
										IjoomerApplicationConfiguration.setReloadRequired(true);
										getComments();
									} else {
										responseErrorMessageHandler(responseCode, false);
									}
								}

								public void onProgressUpdate(int progressCount) {
								}
							});
						}
					});

					imgCommentDislike.setTag(comment);
					imgCommentDislike.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							@SuppressWarnings("unchecked")
							HashMap<String, String> row = (HashMap<String, String>) v.getTag();
							provider.likeDislike((String) row.get(ID), "0", new WebCallListener() {
								public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
									if (responseCode == 200) {
										IjoomerApplicationConfiguration.setReloadRequired(true);
										getComments();
									} else {
										responseErrorMessageHandler(responseCode, false);
									}
								}

								public void onProgressUpdate(int progressCount) {
								}
							});
						}
					});
					imgCommentRemove.setTag(comment);
					imgCommentRemove.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							@SuppressWarnings("unchecked")
							final HashMap<String, String> row = (HashMap<String, String>) v.getTag();
							IjoomerUtilities.getCustomConfirmDialog(getString(R.string.iproperty_comment_remove), getString(R.string.are_you_sure), getString(R.string.yes),
									getString(R.string.no), new CustomAlertMagnatic() {
										public void NegativeMethod() {
										}

										public void PositiveMethod() {
											provider.removeComment(row.get(ID), new WebCallListener() {
												public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
													if (responseCode == 200) {
														getComments();
													}
												}

												public void onProgressUpdate(int progressCount) {
												}
											});
										}
									});
						}
					});

					lnrComment.addView(view);
				}

			} else {
				lnrComment.setVisibility(View.GONE);
				txtNoComment.setVisibility(View.VISIBLE);
				btnCommentMore.setVisibility(View.GONE);
			}
		} catch (Throwable e) {
			lnrComment.setVisibility(View.GONE);
			txtNoComment.setVisibility(View.VISIBLE);
			btnCommentMore.setVisibility(View.GONE);
			e.printStackTrace();
		}

	}

	private void setDetails(HashMap<String, String> detail) {
		propertyData = detail;
		if (isLogin() && propertyData.get("isMy").equals("1")) {
			btnAddPicture.setVisibility(View.VISIBLE);
		} else {
			btnAddPicture.setVisibility(View.GONE);
		}
		androidQuery.id(imgPropertyDetail).image((String) detail.get(IMAGE), true, false);
		txtPropertyDetailAddress.setText(prepareAddress(new String[] { detail.get(STREET_NUM), detail.get(STREET), detail.get(STREET2) }));
		if (detail.get("description").trim().length() > 0) {
			txtPropertyOverviewValue.setText(Html.fromHtml(detail.get("description").trim()));
		} else {
			txtPropertyOverviewValue.setVisibility(View.GONE);
			txtPropertyOverviewLable.setVisibility(View.GONE);
		}
	}

	private void setPicture(final ArrayList<HashMap<String, String>> data) {
		try {
			lnrPictureFirstRow.removeAllViews();
			lnrPictureSecondRow.removeAllViews();
			if (data.size() == 0) {
				lnrPictureFirstRow.setVisibility(View.GONE);
				lnrPictureSecondRow.setVisibility(View.GONE);
				txtNoPicture.setVisibility(View.VISIBLE);
			} else if (data.size() <= 4) {
				lnrPictureFirstRow.setVisibility(View.VISIBLE);
				lnrPictureSecondRow.setVisibility(View.GONE);
				txtNoPicture.setVisibility(View.GONE);

				for (int i = 0; i < data.size(); i++) {
					View view = LayoutInflater.from(this).inflate(R.layout.iproperty_details_pictures_list_item, null);
					ImageView imgDetailsPhoto = (ImageView) view.findViewById(R.id.imgDetailsPhoto);
					androidQuery.id(imgDetailsPhoto).image(data.get(i).get(IMAGE), true, true);
					HashMap<String, Object> row = new HashMap<String, Object>();
					row.put("index", i);
					row.put("list", data);
					imgDetailsPhoto.setTag(row);
					imgDetailsPhoto.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							@SuppressWarnings("unchecked")
							HashMap<String, Object> row = (HashMap<String, Object>) v.getTag();
							try {
								loadNew(IPropertyPictureDetailActivity.class, IPropertyDetailsActivity.this, false, "IN_PICTURE_LIST", row.get("list"), "IN_SELECTED_INDEX",
										row.get("index").toString());
							} catch (Throwable e) {
								e.printStackTrace();
							}
						}
					});
					lnrPictureFirstRow.addView(view, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0F));

				}
			} else if (data.size() <= 7) {
				lnrPictureFirstRow.setVisibility(View.VISIBLE);
				lnrPictureSecondRow.setVisibility(View.VISIBLE);
				txtNoPicture.setVisibility(View.GONE);

				for (int i = 0; i < data.size(); i++) {
					View view = LayoutInflater.from(this).inflate(R.layout.iproperty_details_pictures_list_item, null);
					ImageView imgDetailsPhoto = (ImageView) view.findViewById(R.id.imgDetailsPhoto);
					androidQuery.id(imgDetailsPhoto).image(data.get(i).get(IMAGE), true, true);
					HashMap<String, Object> row = new HashMap<String, Object>();
					row.put("index", i);
					row.put("list", data);
					imgDetailsPhoto.setTag(row);
					imgDetailsPhoto.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							@SuppressWarnings("unchecked")
							HashMap<String, Object> row = (HashMap<String, Object>) v.getTag();
							try {
								loadNew(IPropertyPictureDetailActivity.class, IPropertyDetailsActivity.this, false, "IN_PICTURE_LIST", row.get("list"), "IN_SELECTED_INDEX",
										row.get("index").toString());
							} catch (Throwable e) {
								e.printStackTrace();
							}
						}
					});

					if (i < 4) {
						lnrPictureFirstRow.addView(view, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0F));
					} else {
						lnrPictureSecondRow.addView(view, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0F));
					}
				}

			} else if (data.size() > 7) {
				for (int i = 0; i < 7; i++) {
					View view = LayoutInflater.from(this).inflate(R.layout.iproperty_details_pictures_list_item, null);
					ImageView imgDetailsPhoto = (ImageView) view.findViewById(R.id.imgDetailsPhoto);
					androidQuery.id(imgDetailsPhoto).image(data.get(i).get(IMAGE), true, true);
					HashMap<String, Object> row = new HashMap<String, Object>();
					row.put("index", i);
					ArrayList<HashMap<String, String>> pictureList = new ArrayList<HashMap<String, String>>();
					for (HashMap<String, String> temp : data) {
						if (pictureList.size() == 7) {
							break;
						} else {
							pictureList.add(temp);
						}
					}
					row.put("list", pictureList);
					imgDetailsPhoto.setTag(row);
					imgDetailsPhoto.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							@SuppressWarnings("unchecked")
							HashMap<String, Object> row = (HashMap<String, Object>) v.getTag();
							try {
								loadNew(IPropertyPictureDetailActivity.class, IPropertyDetailsActivity.this, false, "IN_PICTURE_LIST", row.get("list"), "IN_SELECTED_INDEX",
										row.get("index").toString());
							} catch (Throwable e) {
								e.printStackTrace();
							}
						}
					});

					if (i < 4) {
						lnrPictureFirstRow.addView(view, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0F));
					} else {
						if (i == 4) {
							View moreView = LayoutInflater.from(this).inflate(R.layout.iproperty_details_pictures_list_item, null);
							ImageView imgDetailsPhotoMore = (ImageView) moreView.findViewById(R.id.imgDetailsPhoto);
							androidQuery.id(imgDetailsPhotoMore).image(getResources().getDrawable(R.drawable.iproperty_more_pictures));

							imgDetailsPhotoMore.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									try {
										String propertyTitle = ((HashMap<String, String>) MAP_LIST.get(0)).get(TITLE);
										String isMy = ((HashMap<String, String>) MAP_LIST.get(0)).get("isMy");
										loadNew(IPropertyDetailGalleryActivity.class, IPropertyDetailsActivity.this, false, "IN_PROPERTY_ID", IN_PROPERTY_ID, "IN_OBJ", IN_OBJ,
												"IN_PROPERTY_TITLE",propertyTitle , "IN_ADD_PICTURE",isMy);
									} catch (Throwable e) {
										e.printStackTrace();
									}
								}
							});

							lnrPictureSecondRow.addView(moreView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0F));
						}
						lnrPictureSecondRow.addView(view, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0F));
					}
				}
			}
		} catch (Throwable e) {
			lnrPictureFirstRow.setVisibility(View.GONE);
			lnrPictureSecondRow.setVisibility(View.GONE);
			txtNoPicture.setVisibility(View.VISIBLE);
			e.printStackTrace();
		}
	}

	public void initComponents() {
		lnrComment = (LinearLayout) findViewById(R.id.lnrComment);
		lnrPictureFirstRow = (LinearLayout) findViewById(R.id.lnrPictureFirstRow);
		lnrPictureSecondRow = (LinearLayout) findViewById(R.id.lnrPictureSecondRow);
		txtPropertyDetailAddress = (IjoomerTextView) findViewById(R.id.txtPropertyDetailAddress);
		txtPropertyOverviewValue = (IjoomerTextView) findViewById(R.id.txtPropertyOverviewValue);
		txtPropertyOverviewLable = (IjoomerTextView) findViewById(R.id.txtPropertyOverviewLable);
		txtNoComment = (IjoomerTextView) findViewById(R.id.txtNoComment);
		txtNoPicture = (IjoomerTextView) findViewById(R.id.txtNoPicture);
		btnAddComment = (IjoomerButton) findViewById(R.id.btnAddComment);
		btnAddPicture = (IjoomerButton) findViewById(R.id.btnAddPicture);
		btnCommentMore = (IjoomerButton) findViewById(R.id.btnCommentMore);
		btnMap = (IjoomerButton) findViewById(R.id.btnMap);
		btnFavourite = (IjoomerButton) findViewById(R.id.btnFavourite);
		btnShare = (IjoomerButton) findViewById(R.id.btnShare);
		imgPropertyDetail = (ImageView) findViewById(R.id.imgPropertyDetail);
		pbrLoadComment = (ProgressBar) findViewById(R.id.pbrLoadComment);
		pbrLoadPicture = (ProgressBar) findViewById(R.id.pbrLoadPicture);

		androidQuery = new AQuery(this);
		provider = new IPropertyDetailDataProvider(this);
		commentsProvider = new IPropertyDetailDataProvider(this);
		picturesProvider = new IPropertyDetailDataProvider(this);
		favouriteProvider = new IPropertyFavouriteDataProvider(this);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode == RESULT_OK) {
			if (requestCode == PICK_IMAGE_USER_AVATAR) {
				try {
					loadNew(IPropertyPictureAddActivity.class, this, false, "IN_PICTURE_PATH", getAbsolutePath(intent.getData()), "IN_PROPERTY_ID", IN_PROPERTY_ID,
							"IN_PROPERTY_TITLE", ((HashMap<String, String>) MAP_LIST.get(0)).get(TITLE));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			} else if (requestCode == CAPTURE_IMAGE_USER_AVATAR) {
				try {
					loadNew(IPropertyPictureAddActivity.class, this, false, "IN_PICTURE_PATH", getImagePath(), "IN_PROPERTY_ID", IN_PROPERTY_ID, "IN_PROPERTY_TITLE",
							((HashMap<String, String>) MAP_LIST.get(0)).get(TITLE));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

	protected void onResume() {
		if (IjoomerApplicationConfiguration.isReloadRequired()) {
			IjoomerApplicationConfiguration.setReloadRequired(false);
			getComments();
			getPictures();
		}
		super.onResume();
	}

	public void prepareViews() {
		IN_PROPERTY_ID = getIntent().getStringExtra("IN_PROPERTY_ID") != null ? getIntent().getStringExtra("IN_PROPERTY_ID") : "0";
		IN_OBJ = getIntent().getStringExtra("IN_OBJ") != null ? getIntent().getStringExtra("IN_OBJ") : "";
		try {
			picturesLimit = new JSONObject(IN_OBJ).getJSONObject(ITEMDATA).getString(PROPERTYGALLERYIMAGESLIMIT);
		} catch (Throwable e) {
			picturesLimit = "8";
			e.printStackTrace();
		}

		try {
			commentsLimit = new JSONObject(IN_OBJ).getJSONObject(ITEMDATA).getString(PROPERTYCOMMENTSLIMIT);
		} catch (Throwable e) {
			commentsLimit = "2";
			e.printStackTrace();
		}
		getPropertyDetail();
		getComments();
		getPictures();
	}

	public void setActionListeners() {
		btnShare.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				try {
					loadNew(IPropertyShareSmsActivity.class, IPropertyDetailsActivity.this, false, "IN_SHARE_LINK", ((HashMap<String, String>) MAP_LIST.get(0)).get("shareLink"),
							"IN_PROPERTY_TITLE", ((HashMap<String, String>) MAP_LIST.get(0)).get(TITLE));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		btnMap.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					loadNew(IPropertyMapActivity.class, IPropertyDetailsActivity.this, false, "IN_MAPLIST", MAP_LIST, "IN_OBJ", getIntent().getStringExtra("IN_OBJ"),
							"IN_GOTO_DETAILS", false, "IN_GOTO_DIRECTION", true);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		btnFavourite.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (isLogin()) {
					final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.iproperty_detail));
					favouriteProvider.addPropertyToFavourite(IN_PROPERTY_ID, new WebCallListener() {
						public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
							if (responseCode == 200) {
								IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_detail), getString(R.string.iproperty_add_favourite_sccressfully),
										getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
											public void NeutralMethod() {
											}
										});
							} else {
								responseErrorMessageHandler(responseCode, false);
							}
						}

						public void onProgressUpdate(int progressCount) {
							proSeekBar.setProgress(progressCount);
						}
					});
				}
				if (favouriteProvider.addToFavouriteList(MAP_LIST)) {
					IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_detail), getString(R.string.iproperty_add_favourite_sccressfully), getString(R.string.ok),
							R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
								public void NeutralMethod() {
								}
							});
				} else {
					IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_detail), getString(R.string.iproperty_add_favourite_unsccressfully), getString(R.string.ok),
							R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
								public void NeutralMethod() {
								}
							});
				}
			}
		});
		btnCommentMore.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					loadNew(IPropertyCommentListActivity.class, IPropertyDetailsActivity.this, false, "IN_PROPERTY_DETAIL", propertyData, "IN_COMMENT_LIMIT", commentsLimit);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		btnAddComment.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					loadNew(IPropertyCommentAddActivity.class, IPropertyDetailsActivity.this, false, "IN_PROPERTY_ID", propertyData.get("id"));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		btnAddPicture.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				IjoomerUtilities.selectImageDialog(new SelectImageDialogListner() {
					public void onCapture() {
						Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
						intent.putExtra("output", setImageUri());
						startActivityForResult(intent, CAPTURE_IMAGE_USER_AVATAR);
					}

					public void onPhoneGallery() {
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction("android.intent.action.GET_CONTENT");
						startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE_USER_AVATAR);
					}
				});
			}
		});
	}

	public int setLayoutId() {
		return R.layout.iproperty_details;
	}
}