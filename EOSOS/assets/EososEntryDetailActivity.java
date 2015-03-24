package com.eosos.components.main;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.Facebook.Permissions;
import com.Facebook.SimpleFacebook;
import com.Facebook.SimpleFacebookConfiguration;
import com.Facebook.entities.Feed;
import com.eosos.common.classes.IjoomerUtilities;
import com.eosos.common.configuration.IjoomerApplicationConfiguration;
import com.eosos.customviews.IjoomerMultiPurposeSelector;
import com.eosos.customviews.IjoomerRadioButton;
import com.eosos.library.EososDataProvider;
import com.eosos.src.R;
import com.eosos.weservice.WebCallListenerWithCacheInfo;
import com.smart.framework.CustomAlertMagnatic;
import com.smart.framework.CustomAlertNeutral;

public class EososEntryDetailActivity extends EososMasterActivity {
	private IjoomerMultiPurposeSelector selectorDesc, selectorContact, selectorShare, selectorDays;
	private IjoomerRadioButton btnDescription, btnContact, btnShare, btnDays;
	private ArrayList<HashMap<String, String>> data;
	private EososDataProvider dataProvider;
	public String IN_ID = "1";
	private ImageView imgMap;
	public boolean isFavourite = false;
	public String IN_SECTION_ID = "54";
	SeekBar proSeekBar;

    private SimpleFacebook simpleFacebook;
    private SimpleFacebookConfiguration.Builder simpleFacebookConfigurationBuilder;
    private SimpleFacebookConfiguration simpleFacebookConfiguration;

    private double rating;
    private String name;
    private String title;

    private ArrayList<Boolean> daysList;

    @Override
	public int setLayoutId() {
		return R.layout.eosos_entry_detail;
	}

	@Override
	public void initComponents() {
		btnDescription = (IjoomerRadioButton) findViewById(R.id.btnDescription);
		btnContact = (IjoomerRadioButton) findViewById(R.id.btnContact);
		btnShare = (IjoomerRadioButton) findViewById(R.id.btnShare);
		btnDays = (IjoomerRadioButton) findViewById(R.id.btnDays);
		selectorDesc = new IjoomerMultiPurposeSelector(EososEntryDetailActivity.this);
		selectorContact = new IjoomerMultiPurposeSelector(EososEntryDetailActivity.this);
		selectorShare = new IjoomerMultiPurposeSelector(EososEntryDetailActivity.this);
		selectorDays = new IjoomerMultiPurposeSelector(EososEntryDetailActivity.this);
		dataProvider = new EososDataProvider(EososEntryDetailActivity.this);
        daysList = new ArrayList<Boolean>();
	}

    @Override
    protected void onResume() {
        super.onResume();
        simpleFacebook = SimpleFacebook.getInstance(this);
        simpleFacebookConfigurationBuilder = new SimpleFacebookConfiguration.Builder();
        simpleFacebookConfigurationBuilder.setAppId(getString(R.string.facebook_app_id));
        simpleFacebookConfigurationBuilder.setPermissions(new Permissions[]{Permissions.PUBLISH_ACTION,Permissions.PUBLISH_STREAM});
        simpleFacebookConfiguration = simpleFacebookConfigurationBuilder.build();
        SimpleFacebook.setConfiguration(simpleFacebookConfiguration);
    }

    @Override
	public void prepareViews() {
		getIntentData();
		isFavourite = dataProvider.isFavourite(IN_ID);
		selectorDesc.setPressedDrawableResource(R.drawable.discription_tab_active);
		selectorDesc.setDefaultDrawableResource(R.drawable.discription_tab);

		selectorContact.setPressedDrawableResource(R.drawable.call_us_tab_active);
		selectorContact.setDefaultDrawableResource(R.drawable.call_us_tab);

		selectorShare.setPressedDrawableResource(R.drawable.rate_tab_active);
		selectorShare.setDefaultDrawableResource(R.drawable.rate_tab);

		selectorDays.setPressedDrawableResource(R.drawable.days_tab_active);
		selectorDays.setDefaultDrawableResource(R.drawable.days_tab);

		btnDescription.setBackgroundDrawable(selectorDesc.getSelector());
		btnContact.setBackgroundDrawable(selectorContact.getSelector());
		btnShare.setBackgroundDrawable(selectorShare.getSelector());
		btnDays.setBackgroundDrawable(selectorDays.getSelector());
		imgMap = (ImageView) getHeaderView().findViewById(R.id.imgMap);
		imgMap.setVisibility(View.VISIBLE);
		getEntryDetail();
	}

	public void getEntryDetail() {
		try {
            if(IjoomerApplicationConfiguration.isReloadRequired()){
                dataProvider.getEntryDetail(IN_SECTION_ID, IN_ID, IjoomerApplicationConfiguration.isReloadRequired(), new WebCallListenerWithCacheInfo() {

                    @Override
                    public void onProgressUpdate(int progressCount) {
                    }

                    @Override
                    public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2, int pageNo, int pageLimit,
                                               boolean fromCache) {
                        try {
                            IjoomerApplicationConfiguration.isReloadRequired = false;
                            if (data1 != null && data1.size() > 0) {
                                data = data1;
                                prePareDaysList();
                                title = data.get(0).get("title");
                                HashMap<String, String> hashMap = data.get(0);
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = new JSONArray(hashMap.get("field"));
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image1")) {
                                            hashMap.put("image1", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image2")) {
                                            hashMap.put("image2", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image3")) {
                                            hashMap.put("image3", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_description")) {
                                            hashMap.put("description", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_email")) {
                                            hashMap.put("email", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_phone")) {
                                            hashMap.put("phone", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                            hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                            hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_address")) {
                                            hashMap.put("address", jsonArray.getJSONObject(j).get("value").toString());
                                        } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_city")) {
                                            hashMap.put("city", jsonArray.getJSONObject(j).get("value").toString());
                                        }
                                    }
                                } catch (Exception e) {

                                }
                                if (btnContact.isChecked())
                                    addFragment(R.id.lnrFragment, new EososEntryContactInfoFragment(data));
                                else if (btnDescription.isChecked())
                                    addFragment(R.id.lnrFragment, new EososEntryDescriptionFragment(data));
                                else if (btnDays.isChecked())
                                    addFragment(R.id.lnrFragment, new EososEntryDaysFragment(daysList
                                    ));
                                else
                                    loadRateListFragment();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }else{
                try {
                    ArrayList<HashMap<String,String>> data1 = dataProvider.getEntryDetailsFromDb(IN_ID);
                    if (data1 != null && data1.size() > 0) {
                        data = data1;
                        prePareDaysList();
                        title = data.get(0).get("title");
                        HashMap<String, String> hashMap = data.get(0);
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(hashMap.get("field"));
                            for (int j = 0; j < jsonArray.length(); j++) {
                                if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image1")) {
                                    hashMap.put("image1", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image2")) {
                                    hashMap.put("image2", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_image3")) {
                                    hashMap.put("image3", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_description")) {
                                    hashMap.put("description", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_email")) {
                                    hashMap.put("email", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_phone")) {
                                    hashMap.put("phone", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                    hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_website")) {
                                    hashMap.put("website", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_address")) {
                                    hashMap.put("address", jsonArray.getJSONObject(j).get("value").toString());
                                } else if (jsonArray.getJSONObject(j).get("labelid").toString().equalsIgnoreCase("field_city")) {
                                    hashMap.put("city", jsonArray.getJSONObject(j).get("value").toString());
                                }
                            }
                        } catch (Exception e) {

                        }
                        if (btnContact.isChecked())
                            addFragment(R.id.lnrFragment, new EososEntryContactInfoFragment(data));
                        else if (btnDescription.isChecked())
                            addFragment(R.id.lnrFragment, new EososEntryDescriptionFragment(data));
                        else if (btnDays.isChecked())
                            addFragment(R.id.lnrFragment, new EososEntryDaysFragment(daysList));
                        else
                            loadRateListFragment();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    private void prePareDaysList(){
        try {

            daysList.clear();
            if (dataProvider.isClubOpen(data.get(0).get("id"), "monday")) {
                daysList.add(true);
            }else{
                daysList.add(false);
            }

            if (dataProvider.isClubOpen(data.get(0).get("id"), "tuesday")) {
                daysList.add(true);
            }else{
                daysList.add(false);
            }

            if (dataProvider.isClubOpen(data.get(0).get("id"), "wednesday")) {
                daysList.add(true);
            }else{
                daysList.add(false);
            }
            if (dataProvider.isClubOpen(data.get(0).get("id"), "thursday")) {
                daysList.add(true);
            }else{
                daysList.add(false);
            }
            if (dataProvider.isClubOpen(data.get(0).get("id"), "friday")) {
                daysList.add(true);
            }else{
                daysList.add(false);
            }
            if (dataProvider.isClubOpen(data.get(0).get("id"), "saturday")) {
                daysList.add(true);
            }else{
                daysList.add(false);
            }
            if (dataProvider.isClubOpen(data.get(0).get("id"), "sunday")) {
                daysList.add(true);
            }else{
                daysList.add(false);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public void shareOnFacebook(double rating,String name){
        this.rating = rating;
        this.name = name;
        IjoomerUtilities.getCustomConfirmRatingAndFacebookShareDialog(getString(R.string.rating_text),getString(R.string.facebook_text),new CustomAlertMagnatic() {
            @Override
            public void PositiveMethod() {
                if(simpleFacebook.isLogin()){
                    publishFeed();
                }else{
                    simpleFacebook.login(new OnLoginListener());
                }
            }

            @Override
            public void NegativeMethod() {

            }
        });
    }

	public void getIntentData() {
		if (getSmartApplication().readSharedPreferences().getString(SP_DEFAULT_LANDING_SCREEN, "").length() > 0) {
			try {
				JSONObject intentData = new JSONObject(getSmartApplication().readSharedPreferences().getString(SP_LAST_ACTIVITY_INTENT, "")).getJSONObject("itemdata");
				IN_SECTION_ID = intentData.getString("sectionID");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			IN_ID = getIntent().getStringExtra("IN_ID");
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void setActionListeners() {

		imgMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					loadNew(EososMapActivity.class, EososEntryDetailActivity.this, false, "IN_MAPLIST", data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnDescription.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (data != null)
					addFragment(R.id.lnrFragment, new EososEntryDescriptionFragment(data));
			}
		});

		btnDays.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (daysList.size()>0)
					addFragment(R.id.lnrFragment, new EososEntryDaysFragment(daysList));
			}
		});

		btnContact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (data != null)
					addFragment(R.id.lnrFragment, new EososEntryContactInfoFragment(data));
			}
		});

		btnShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loadRateListFragment();
			}
		});
	}

	public void loadAddRateFragment() {
		addFragment(R.id.lnrFragment, new EososAddRatingActivity());
	}

	public void loadRateListFragment() {
		if (data != null) {

			try {
				addFragment(R.id.lnrFragment, new EososRateListFragment(data.get(0).get(REVIEWRATING), data.get(0).get(AVERAGERATING)));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	void onEmail(String value, String message) {
		String[] to = value.toString().split(",");
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/html");
		i.putExtra(Intent.EXTRA_EMAIL, to);
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			ting(getString(R.string.share_email_no_client));
		}
	}

	void onEmail(String IN_SHARE_CAPTION, String message, String IN_SHARE_SHARELINK) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/html");
		i.putExtra(Intent.EXTRA_SUBJECT, String.format(getString(R.string.share_email_subject), IN_SHARE_CAPTION));
		i.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(IjoomerUtilities.prepareEmailBody("", getString(R.string.saw_this_story_on_the) + " " + getString(R.string.app_name) + " ",
				IN_SHARE_CAPTION, message, IN_SHARE_SHARELINK, "", getString(R.string.site_url))));
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			ting(getString(R.string.share_email_no_client));
		}
	}

	void call(String phone) {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + phone));
		startActivity(callIntent);
	}

	void message(String IN_SHARE_CAPTION, String message, String IN_SHARE_SHARELINK) {
		try {
			Intent localIntent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + ""));
			localIntent.putExtra("sms_body", IN_SHARE_CAPTION + "\n" + message + "\n\n" + IN_SHARE_SHARELINK);
			startActivity(localIntent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showDialog() {
		IjoomerUtilities.getCustomOkDialog("Information", "Under Construction", getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

			@Override
			public void NeutralMethod() {

			}
		});
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        simpleFacebook.onActivityResult(this, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    class OnLoginListener implements SimpleFacebook.OnLoginListener{


        @Override
        public void onLogin() {
            publishFeed();
        }

        @Override
        public void onNotAcceptingPermissions() {
        }

        @Override
        public void onThinking() {
        }

        @Override
        public void onException(Throwable throwable) {
        }

        @Override
        public void onFail(String reason) {
            IjoomerUtilities.getCustomOkDialog(getString(R.string.eosos_entry_details),reason, getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                @Override
                public void NeutralMethod() {
                }
            });
        }
    }


    private void publishFeed(){
        final Feed feed = new Feed.Builder()
                .setMessage("Just rated " + title+" on Elite Clubs.")
                .setName("")
                .setCaption("")
                .setDescription("")
                .setLink("www.eososelite.co.uk")
                .build();
        simpleFacebook.publish(feed, new onPublishListener());
    }
    class onPublishListener implements SimpleFacebook.OnPublishListener{


        @Override
        public void onComplete(String id) {
            hideProgressDialog();
            IjoomerUtilities.getCustomOkDialog(getString(R.string.eosos_entry_details),getString(R.string.facebook_share_success), getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                @Override
                public void NeutralMethod() {
                }
            });
        }

        @Override
        public void onThinking() {
            showProgressDialog("Doing Facebook Sharing...",EososEntryDetailActivity.this,true);
        }

        @Override
        public void onException(Throwable throwable) {
            hideProgressDialog();
            throwable.printStackTrace();
        }

        @Override
        public void onFail(String reason) {
            hideProgressDialog();
            IjoomerUtilities.getCustomOkDialog(getString(R.string.eosos_entry_details),reason, getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                @Override
                public void NeutralMethod() {
                }
            });
        }
    }

}
