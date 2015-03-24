package com.eosos.components.main;

import android.content.Intent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.eosos.common.classes.IjoomerUtilities;
import com.eosos.common.configuration.IjoomerApplicationConfiguration;
import com.eosos.customviews.IjoomerEditText;
import com.eosos.customviews.IjoomerRatingBar;
import com.eosos.library.EososReviewDataProvider;
import com.eosos.src.R;
import com.eosos.weservice.WebCallListener;
import com.smart.framework.CustomAlertMagnatic;
import com.smart.framework.CustomAlertNeutral;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnPublishListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This Fragment Contains All Method Related To IcmsArchivedArticlesFragment.
 * 
 * @author tasol
 * 
 */
public class EososAddRatingActivity extends EososMasterActivity {
	private SeekBar seekRate;
	private IjoomerRatingBar rtbRating;
	private IjoomerEditText edtName, edtComment;
	int progress;
	private EososReviewDataProvider dataProvider;

	private String IN_ID;
	private String IN_SECTION_ID;
	private String IN_TITLE;

	private SimpleFacebook simpleFacebook;
	private SimpleFacebookConfiguration.Builder simpleFacebookConfigurationBuilder;
	private SimpleFacebookConfiguration simpleFacebookConfiguration;

	@Override
	public int setLayoutId() {
		return R.layout.eosos_add_rate;
	}

	@Override
	public View setLayoutView() {
		return null;
	}

	@Override
	public void initComponents() {
		rtbRating = (IjoomerRatingBar) findViewById(R.id.rtbRating);
		edtComment = (IjoomerEditText) findViewById(R.id.edtComment);
		edtName = (IjoomerEditText) findViewById(R.id.edtName);
		seekRate = (SeekBar) findViewById(R.id.seekRate);
		dataProvider = new EososReviewDataProvider(this);

		simpleFacebook = SimpleFacebook.getInstance(this);
		simpleFacebookConfigurationBuilder = new SimpleFacebookConfiguration.Builder();
		simpleFacebookConfigurationBuilder.setAppId(getString(R.string.facebook_app_id));
		simpleFacebookConfigurationBuilder.setPermissions(new Permission[] { Permission.PUBLISH_ACTION});
		simpleFacebookConfiguration = simpleFacebookConfigurationBuilder.build();
		SimpleFacebook.setConfiguration(simpleFacebookConfiguration);
	}

	@Override
	public void prepareViews() {
		getIntentData();
	}

	private void getIntentData() {
		IN_ID = getIntent().getStringExtra("IN_ID") != null ? getIntent().getStringExtra("IN_ID") : "1";
		IN_SECTION_ID = getIntent().getStringExtra("IN_SECTION_ID") != null ? getIntent().getStringExtra("IN_SECTION_ID") : "1";
		IN_TITLE = getIntent().getStringExtra("IN_TITLE") != null ? getIntent().getStringExtra("IN_TITLE") : "1";
	}

	@Override
	public void setActionListeners() {

		seekRate.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				setProgreeHandler(seekBar);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (progress == 100) {

					if (validateFields()) {
						if (rtbRating.getStarRating() > 0) {
							final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.dialog_loading_sending_request));
							dataProvider.addReview(getDeviceUDID(),IN_ID, IN_SECTION_ID, edtName.getText().toString().trim(), edtComment.getText().toString().trim(),
									(rtbRating.getStarRating() * 2) + "", new WebCallListener() {
										@Override
										public void onProgressUpdate(int progressCount) {
											proSeekBar.setProgress(progressCount);
										}

										@Override
										public void onCallComplete(int responseCode, String message, ArrayList<HashMap<String, String>> data1, Object data2) {
											try {
												if (responseCode == 200) {
													seekRate.setVisibility(View.GONE);
													IjoomerApplicationConfiguration.setReloadRequired(true);
													shareOnFacebook();
												} else {
													IjoomerUtilities.getCustomOkDialog(getString(R.string.rate),
															getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.ok),
															R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

																@Override
																public void NeutralMethod() {
																}
															});
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									});
						} else {
							ting(getString(R.string.validate_rate_message));
							seekRate.setProgress(0);
						}

					} else {
						seekRate.setProgress(0);
					}

				}
			}
		});

	}

	public void shareOnFacebook() {
		IjoomerUtilities.getCustomConfirmRatingAndFacebookShareDialog(getString(R.string.rating_text), getString(R.string.facebook_text), new CustomAlertMagnatic() {
			@Override
			public void PositiveMethod() {
				if (simpleFacebook.isLogin()) {
					publishFeed();
				} else {
					simpleFacebook.login(new MyLoginListener());
				}
			}

			@Override
			public void NegativeMethod() {
				finish();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		simpleFacebook.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	class MyLoginListener implements OnLoginListener {

		@Override
		public void onLogin() {
			publishFeed();
		}

        @Override
        public void onNotAcceptingPermissions(Permission.Type type) {
        }

        @Override
		public void onThinking() {
		}

		@Override
		public void onException(Throwable throwable) {
		}

		@Override
		public void onFail(String reason) {
			IjoomerUtilities.getCustomOkDialog(getString(R.string.eosos_entry_details), reason, getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

				@Override
				public void NeutralMethod() {
				}
			});
		}
	}

	private void publishFeed() {
		final Feed feed = new Feed.Builder().setMessage("Just rated " + IN_TITLE + " on Elite Clubs.").setName("").setCaption("").setDescription("")
				.setLink("www.eososelite.co.uk").build();
		simpleFacebook.publish(feed, new onPublishListener());
	}

	class onPublishListener extends OnPublishListener {

		@Override
		public void onComplete(String id) {
			hideProgressDialog();
			IjoomerUtilities.getCustomOkDialog(getString(R.string.eosos_entry_details), getString(R.string.facebook_share_success), getString(R.string.ok),
					R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

						@Override
						public void NeutralMethod() {
							finish();
						}
					});
		}

		@Override
		public void onThinking() {
			showProgressDialog("Doing Facebook Sharing...", EososAddRatingActivity.this, true);
		}

		@Override
		public void onException(Throwable throwable) {
			hideProgressDialog();
			throwable.printStackTrace();
		}

		@Override
		public void onFail(String reason) {
			hideProgressDialog();
			IjoomerUtilities.getCustomOkDialog(getString(R.string.eosos_entry_details), reason, getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

				@Override
				public void NeutralMethod() {
				}
			});
		}
	}

	public void setProgreeHandler(final SeekBar seekBar) {

		progress = seekBar.getProgress();
		if (progress > 50) {

			new Thread(new Runnable() {

				@Override
				public void run() {

					while (progress < 100) {
						try {
							Thread.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								progress += 5;
								seekBar.setProgress(progress);
							}
						});
					}

				}
			}).start();

		} else {
			seekBar.setProgress(0);
		}

	}

	private boolean validateFields() {
		boolean isValid = true;
		if (edtName.getText().toString().trim().length() == 0) {
			isValid = false;
			edtName.setError(getString(R.string.validation_value_required));
		}
		if (edtComment.getText().toString().trim().length() == 0) {
			isValid = false;
			edtComment.setError(getString(R.string.validation_value_required));
		}

		return isValid;
	}

}