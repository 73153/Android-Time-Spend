package com.eosos.components.main;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.eosos.common.classes.IjoomerWebviewClient;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.src.R;
import com.smart.exception.InvalidKeyFormatException;
import com.smart.exception.NullDataException;
import com.smart.exception.WronNumberOfArgumentsException;

public class EososTermsActivity extends EososMasterActivity {

	IjoomerTextView txtTermsDetail;

	@Override
	public int setLayoutId() {
		return R.layout.eosos_terms;
	}

	@Override
	public void initComponents() {
		txtTermsDetail = (IjoomerTextView) findViewById(R.id.txtTermsDetail);

	}

	@Override
	public void prepareViews() {

	}

	@Override
	public void setActionListeners() {
	}

}
