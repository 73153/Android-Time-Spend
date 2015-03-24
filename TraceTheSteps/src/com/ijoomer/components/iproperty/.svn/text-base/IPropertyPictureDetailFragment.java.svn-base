package com.ijoomer.components.iproperty;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.ijoomer.customviews.GestureImageView;
import com.ijoomer.customviews.GestureImageViewListener;
import com.ijoomer.tracethesteps.src.R;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartFragment;

public class IPropertyPictureDetailFragment extends SmartFragment implements IPropertyTagHolder {
	private AQuery androidQuery = new AQuery(getActivity());
	private String imagePath;
	private GestureImageView imgPhotoDetail;
	private float scales;

	public IPropertyPictureDetailFragment(String imagepath) {
		this.imagePath = imagepath;
	}

	public void initComponents(View paramView) {
		imgPhotoDetail = (GestureImageView) paramView.findViewById(R.id.imgPhotoDetail);
	}

	public void prepareViews(View paramView) {
		androidQuery.id(this.imgPhotoDetail).image(imagePath, true, true, ((SmartActivity) getActivity()).getDeviceWidth(), 0, new BitmapAjaxCallback() {
			protected void callback(String paramAnonymousString, ImageView paramAnonymousImageView, Bitmap paramAnonymousBitmap, AjaxStatus paramAnonymousAjaxStatus) {
				super.callback(paramAnonymousString, paramAnonymousImageView, paramAnonymousBitmap, paramAnonymousAjaxStatus);
				imgPhotoDetail.setAdjustViewBounds(true);
				imgPhotoDetail.setImageBitmap(paramAnonymousBitmap);
			}
		});
	}

	public void setActionListeners(View v) {
		this.imgPhotoDetail.setGestureImageViewListener(new GestureImageViewListener() {
			public void onPosition(float paramAnonymousFloat1, float paramAnonymousFloat2) {
			}

			public void onScale(float paramAnonymousFloat) {
				if (paramAnonymousFloat > IPropertyPictureDetailFragment.this.scales){
					IPropertyPictureDetailActivity.viewPager.setScrollable(false);
					IPropertyPictureDetailFragment.this.scales = paramAnonymousFloat;
					IPropertyPictureDetailActivity.viewPager.setScrollable(true);
				}
			}

			public void onTouch(float paramAnonymousFloat1, float paramAnonymousFloat2) {
			}
		});
	}

	public int setLayoutId() {
		return R.layout.iproperty_picture_detail_gallary_fragment;
	}

	public View setLayoutView() {
		return null;
	}
}