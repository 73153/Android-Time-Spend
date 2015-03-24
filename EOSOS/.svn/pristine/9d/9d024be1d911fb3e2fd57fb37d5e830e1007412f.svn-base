package com.eosos.components.main;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.eosos.common.classes.FacebookSharingActivity;
import com.eosos.common.classes.IJoomerTwitterShareActivity;
import com.eosos.library.EososDataProvider;
import com.eosos.src.R;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartFragment;

/**
 * This Fragment Contains All Method Related To IcmsArchivedArticlesFragment.
 * 
 * @author tasol
 * 
 */
public class EososEntryShareFragment extends SmartFragment implements EososTagHolder {

	private EososDataProvider dataProvider;
	private SeekBar seekFb, seekTw, seekMsg, seekPhone;
	private ArrayList<HashMap<String, String>> data;
	int progress;

	public EososEntryShareFragment(ArrayList<HashMap<String, String>> data) {
		this.data = data;
	}

	/**
	 * Overrides methods
	 */
	@Override
	public int setLayoutId() {
		return R.layout.eosos_entry_share_fragment;
	}

	@Override
	public void initComponents(View currentView) {
		dataProvider = new EososDataProvider(getActivity());
		seekFb = (SeekBar) currentView.findViewById(R.id.seekFb);
		seekTw = (SeekBar) currentView.findViewById(R.id.seekTw);
		seekMsg = (SeekBar) currentView.findViewById(R.id.seekMsg);
		seekPhone = (SeekBar) currentView.findViewById(R.id.seekPhone);
	}

	@Override
	public void onResume() {
		super.onResume();
		seekFb.setProgress(0);
		seekTw.setProgress(0);
		seekMsg.setProgress(0);
		seekPhone.setProgress(0);
	}

	@Override
	public void prepareViews(View currentView) {
	}

	@Override
	public void setActionListeners(View currentView) {

		

		seekPhone.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(final SeekBar seekBar) {
				
				setProgreeHandler(seekBar);

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (progress == 100) {
					try {
						((EososEntryDetailActivityNew) getActivity()).message(data.get(0).get("title"), data.get(0).get("description"), data.get(0).get("sharelink"));
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}

			}
		});
		seekMsg.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

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
					try {
						((EososEntryDetailActivityNew) getActivity()).onEmail(data.get(0).get("title"), data.get(0).get("description"), data.get(0).get("sharelink"));
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		});

		seekTw.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

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
					try {
						((SmartActivity) getActivity()).loadNew(IJoomerTwitterShareActivity.class, getActivity(), false, "IN_TWIT_MESSAGE", data.get(0).get("description"),
								"IN_TWIT_IMAGE", data.get(0).get("image1"));
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		});

		seekFb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

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
					try {
						((SmartActivity) getActivity()).loadNew(FacebookSharingActivity.class, getActivity(), false, "IN_SHARE_CAPTION", "", "IN_SHARE_CAPTION", "",
								"IN_SHARE_DESCRIPTION", data.get(0).get("description"), "IN_SHARE_SHARELINK", "", "IN_SHARE_THUMB", data.get(0).get("image1"), "IN_SHARE_MESSAGE",
								"");
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		});

	}

	@Override
	public View setLayoutView() {
		return null;
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
						getActivity().runOnUiThread(new Runnable() {

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

}