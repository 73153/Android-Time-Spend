package com.eosos.components.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.eosos.common.classes.IjoomerUtilities;
import com.eosos.customviews.IjoomerEditText;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.src.R;

public class EososFeedbackActivity extends EososMasterActivity {

	private Spinner spnSelectSubject;
	private IjoomerEditText edtComment;
	private SeekBar seekFeedback;
	private String[] subjects;
	private ArrayList<String> subjectList;
	int progress;

	@Override
	public int setLayoutId() {
		return R.layout.eosos_feedback;
	}

	@Override
	public void initComponents() {

		spnSelectSubject = (Spinner) findViewById(R.id.spnSelectSubject);
		edtComment = (IjoomerEditText) findViewById(R.id.edtComment);
		seekFeedback = (SeekBar) findViewById(R.id.seekFeedback);
		subjects = getResources().getStringArray(R.array.feedbacks);
		subjectList = new ArrayList<String>(Arrays.asList(subjects));

	}

	@Override
	public void prepareViews() {
		((IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader)).setText(getString(R.string.feedback));
        ((IjoomerTextView)getHeaderView().findViewById(R.id.txtBack)).setVisibility(View.VISIBLE);
        ((ImageView)getHeaderView().findViewById(R.id.imgHome)).setVisibility(View.GONE);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.ijoomer_spinner_item, subjectList);
		dataAdapter.setDropDownViewResource(R.layout.ijoomer_spinner_dropdown_item);
		spnSelectSubject.setAdapter(dataAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		seekFeedback.setProgress(0);
	}

	@Override
	public void setActionListeners() {
        ((IjoomerTextView)getHeaderView().findViewById(R.id.txtBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
		seekFeedback.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

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
					String[] to = { "info@eososelite.co.uk"};
					Intent i = new Intent(Intent.ACTION_SEND);
					i.setType("text/html");
					i.putExtra(Intent.EXTRA_EMAIL, to);
					i.putExtra(Intent.EXTRA_SUBJECT, subjects[spnSelectSubject.getSelectedItemPosition()]);
					i.putExtra(Intent.EXTRA_TEXT, edtComment.getText().toString());
					try {
						startActivity(Intent.createChooser(i, "Send mail..."));
					} catch (android.content.ActivityNotFoundException ex) {
						ting(getString(R.string.share_email_no_client));
					}

				}
			}
		});
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
}
