package com.ijoomer.components.iproperty;

import android.view.View;
import android.widget.RadioGroup;

import com.ijoomer.caching.IjoomerCachingConstants;
import com.ijoomer.common.classes.IjoomerSuperMaster;

public abstract class IPropertyMasterActivity extends IjoomerSuperMaster implements IPropertyTagHolder {
	public IPropertyMasterActivity() {
		IjoomerCachingConstants.unNormalizeFields = IPropertyCachingConstants.getUnnormlizeFields();
	}

	public boolean isLogin() {
		return getSmartApplication().readSharedPreferences().getString(SP_USERNAME, "").trim().length() > 0
				&& getSmartApplication().readSharedPreferences().getString(SP_PASSWORD, "").trim().length() > 0;
	}

	public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt) {
	}

	protected void onResume() {
		super.onResume();
		IjoomerCachingConstants.unNormalizeFields = IPropertyCachingConstants.getUnnormlizeFields();
	}

	public String prepareAddress(String[] addressArray) {
		String str = "";
		for (int i = 0; i < addressArray.length; i++) {
			if (addressArray[i].trim().length() > 0) {
				if (str.trim().length() <= 0) {
					str = str.concat(addressArray[i]);
				} else {
					str = str.concat("," + addressArray[i]);
				}
			}
		}
		return str;

	}

	public View setBottomAdvertisement() {
		return null;
	}

	public View setLayoutView() {
		return null;
	}

	public int setTabBarDividerResId() {
		return 0;
	}

	public int setTabItemLayoutId() {
		return 0;
	}

	public String[] setTabItemNames() {
		return null;
	}

	public int[] setTabItemOffDrawables() {
		return null;
	}

	public int[] setTabItemOnDrawables() {
		return null;
	}

	public int[] setTabItemPressDrawables() {
		return null;
	}

	public View setTopAdvertisement() {
		return null;
	}
}