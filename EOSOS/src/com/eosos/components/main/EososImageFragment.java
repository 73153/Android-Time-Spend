package com.eosos.components.main;

import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.eosos.common.classes.IjoomerSuperMaster;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.src.R;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartFragment;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This Fragment Contains All Method Related To SobiproImageFragment.
 * 
 * @author tasol
 * 
 */

public class EososImageFragment extends SmartFragment {
	private ImageView img;
	private ProgressBar progress;
	private AQuery aQuery;
	private String imgUrl;

	/**
	 * Constructor
	 * 
	 * @param imgUrl
	 *            represented image url which will need to display.
	 */

	public EososImageFragment(String imgUrl) {
		this.imgUrl = imgUrl;

	}

	/**
	 * Overrides methods.
	 */

	@Override
	public int setLayoutId() {
		return R.layout.eosos_image_fragment;
	}

	@Override
	public View setLayoutView() {
		return null;
	}

	@Override
	public void initComponents(View currentView) {
		aQuery = new AQuery(getActivity());

		img = (ImageView) currentView.findViewById(R.id.img);
		progress = (ProgressBar) currentView.findViewById(R.id.progress);

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();

	}

	@Override
	public void prepareViews(View currentView) {

		if (!imgUrl.contains("http")) {
			imgUrl = "http://" + imgUrl;
		}
		try {
            if(aQuery.getCachedImage(imgUrl)==null){
                progress.setVisibility(View.VISIBLE);
                aQuery.id(img).image(imgUrl, true, true, 0, 0, new BitmapAjaxCallback() {

                    protected void callback(String url, ImageView iv, android.graphics.Bitmap bm, com.androidquery.callback.AjaxStatus status) {
                        iv.setImageBitmap(bm);
                        progress.setVisibility(View.GONE);
                    }

                    ;

                });
            }else{
                progress.setVisibility(View.GONE);
                img.setImageBitmap(aQuery.getCachedImage(imgUrl));
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setActionListeners(View currentView) {

	}

}
