package com.ijoomer.common.classes;

import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.topcarlondon.src.R;

/**
 * This Class Contains All Method Related To IjoomerSuperMaster.
 * 
 * @author tasol
 * 
 */
public abstract class IjoomerBookSuperMaster extends IjoomerSuperMaster {

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
	}

	@Override
	public int setLayoutId() {
		return 0;
	}

	@Override
	public View setLayoutView() {
		return null;
	}

	@Override
	public int setHeaderLayoutId() {
		return R.layout.ijoomer_header;
	}

	@Override
	public int setFooterLayoutId() {
		return R.layout.ijoomer_footer;
	}


	@Override
	public String[] setTabItemNames() {
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
	public int[] setTabItemOnDrawables() {
		return null;
	}

	@Override
	public int[] setTabItemOffDrawables() {
		return null;
	}

	@Override
	public int[] setTabItemPressDrawables() {
		return null;
	}
	
	@Override
	public View setBottomAdvertisement() {
		View view = LayoutInflater.from(this).inflate(R.layout.book_car_bottom, null);
        IjoomerTextView txtLink = (IjoomerTextView)view.findViewById(R.id.txtLink);
        txtLink.setText(Html.fromHtml(getString(R.string.make_an_extra_money)));
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try {
					loadNew(IjoomerWebviewClient.class, IjoomerBookSuperMaster.this, false, "url", getString(R.string.book_car_make_money));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		return view;
	}

}
