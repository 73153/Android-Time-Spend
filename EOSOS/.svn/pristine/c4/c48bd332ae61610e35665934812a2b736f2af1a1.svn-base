package com.eosos.common.classes;

import com.eosos.caching.IjoomerCachingConstants;
import com.eosos.components.main.EososCachingConstant;

import android.view.View;
import android.widget.RadioGroup;

/**
 * This Class Contains All Method Related To IjoomerHomeMaster.
 * 
 * @author tasol
 * 
 */
public abstract class IjoomerHomeMaster extends IjoomerSuperMaster {

	public IjoomerHomeMaster() {
		super();
		IjoomerCachingConstants.unNormalizeFields = EososCachingConstant.getUnnormlizeFields();
	}

	@Override
	protected void onResume() {
		super.onResume();
		IjoomerCachingConstants.unNormalizeFields = EososCachingConstant.getUnnormlizeFields();
	}

	/**
	 * Override method
	 */

	@Override
	public int setHeaderLayoutId() {
		return 0;
	}

	@Override
	public View setLayoutView() {
		return null;
	}

	@Override
	public View setTopAdvertisement() {
		return null;
	}

	@Override
	public View setBottomAdvertisement() {
		return null;
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

	@Override
	public int setFooterLayoutId() {
		return 0;
	}

}
