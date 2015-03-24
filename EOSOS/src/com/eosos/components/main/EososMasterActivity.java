package com.eosos.components.main;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.eosos.caching.IjoomerCachingConstants;
import com.eosos.common.classes.IjoomerSuperMaster;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.src.R;
import com.smart.framework.SmartActivity;

/**
 * This Class Contains All Method Related To IcmsMasterActivity.
 * 
 * @author tasol
 * 
 */
public abstract class EososMasterActivity extends IjoomerSuperMaster implements EososTagHolder {

	private ImageView imgHome;
    private IjoomerTextView txtBack;

	public EososMasterActivity() {
		super();
		IjoomerCachingConstants.unNormalizeFields = EososCachingConstant.getUnnormlizeFields();
	}

	/**
	 * Overrides methods
	 */
	@Override
	public void loadHeaderComponents() {
		super.loadHeaderComponents();
		imgHome = (ImageView) getHeaderView().findViewById(R.id.imgHome);
		imgHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent("clearStackActivity");
				intent1.setType("text/plain");
				sendBroadcast(intent1);
				loadNew(EososMenuActivity.class, EososMasterActivity.this, true);
			}
		});
        txtBack = (IjoomerTextView) getHeaderView().findViewById(R.id.txtBack);
        txtBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
	}

	@Override
	public int setFooterLayoutId() {
		return R.layout.ijoomer_footer;
	}

	@Override
	public int setHeaderLayoutId() {
		return R.layout.eosos_header;
	}

	@Override
	public View setLayoutView() {
		return null;
	}

	@Override
	public View setBottomAdvertisement() {

		return null;// getAdvertisement("0445b7141d9d4e1b");
	}

	@Override
	public View setTopAdvertisement() {

		return null; // getAdvertisement("0445b7141d9d4e1b");
	}

	@Override
	protected void onResume() {
		super.onResume();
		IjoomerCachingConstants.unNormalizeFields = EososCachingConstant.getUnnormlizeFields();
	}

	@Override
	public int setTabBarDividerResId() {
		return 0;
	}

	@Override
	public int setTabItemLayoutId() {
		return 0;
	}

	@Override
	public String[] setTabItemNames() {
		return null;
	}

	@Override
	public int[] setTabItemOffDrawables() {
		return null;
	}

	@Override
	public int[] setTabItemOnDrawables() {
		return null;
	}

	@Override
	public int[] setTabItemPressDrawables() {
		return null;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

	}
}
