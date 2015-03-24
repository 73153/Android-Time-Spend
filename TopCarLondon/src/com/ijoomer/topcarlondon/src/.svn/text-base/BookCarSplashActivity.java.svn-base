package com.ijoomer.topcarlondon.src;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.view.View;
import android.widget.RadioGroup;

import com.ijoomer.common.classes.IjoomerBookSuperMaster;
import com.ijoomer.common.classes.IjoomerSuperMaster;

/**
 * This Class Contains All Method Related To IjoomerRegistrationStep1Activity.
 * 
 * @author tasol
 * 
 */
public class BookCarSplashActivity extends IjoomerSuperMaster {

	/**
	 * Overrides methods
	 */

	@Override
	public int setLayoutId() {
		return R.layout.book_car_splash;
	}

	@Override
	public void initComponents() {

		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						try {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("tab", R.drawable.tab_book_now);
							jsonObject.put("tab_active", R.drawable.tab_book_now_active);
							jsonObject.put("itemcaption", "Book Now");
							loadNew(BookCarHomeActivity.class, BookCarSplashActivity.this, true, "IN_OBJ", jsonObject.toString());
							timer.cancel();
						} catch (Throwable e) {
							e.printStackTrace();
						}
					}
				});

			}
		}, 5000, 5000);

	}

	@Override
	public void prepareViews() {
	}

	@Override
	public void setActionListeners() {

	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View setLayoutView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int setHeaderLayoutId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setFooterLayoutId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] setTabItemNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int setTabBarDividerResId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setTabItemLayoutId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] setTabItemOnDrawables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] setTabItemOffDrawables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] setTabItemPressDrawables() {
		// TODO Auto-generated method stub
		return null;
	}

}