package com.eosos.components.main;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;

import com.androidquery.AQuery;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.src.R;

public class EososFavouritesActivity extends EososMasterActivity {

    private IjoomerTextView txtList;
    private IjoomerTextView  txtMap;
	public ImageView imgMapType;
	public ImageView imgUnit;
	private EososMapFragment mapFragment;
	private EososFavouriteEntryListFragment lstFragment;
    private boolean isUnitPressed;
    private AQuery aQuery;

	@Override
	public int setLayoutId() {
		return R.layout.eosos_entry;
	}

	@Override
	public void initComponents() {
        txtList = (IjoomerTextView) findViewById(R.id.txtList);
        txtMap = (IjoomerTextView) findViewById(R.id.txtMap);
		imgMapType = (ImageView) findViewById(R.id.imgMapType);
        imgUnit = (ImageView) findViewById(R.id.imgUnit);
        isUnitPressed =false;
        aQuery = new AQuery(this);
	}

	@Override
	public void prepareViews() {
		((IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader)).setText(getString(R.string.favourites));
        txtList.setTextColor(getResources().getColor(R.color.red));
        imgUnit.setVisibility(View.GONE);
		lstFragment = new EososFavouriteEntryListFragment();
		addFragment(R.id.lnrFragment, lstFragment);
	}

	@Override
	public void setActionListeners() {

        imgUnit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUnitPressed) {
                    aQuery.id(imgUnit).image(getResources().getDrawable(R.drawable.unit_btn_icon_normal));
                    isUnitPressed = false;
                } else {
                    aQuery.id(imgUnit).image(getResources().getDrawable(R.drawable.unit_btn_icon_selected));
                    isUnitPressed = true;
                }
                lstFragment.changeDistanceUnit(isUnitPressed);
            }
        });
        txtMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                txtMap.setTextColor(getResources().getColor(R.color.red));
                txtList.setTextColor(getResources().getColor(R.color.txt_color));
				imgMapType.setVisibility(View.VISIBLE);
				imgUnit.setVisibility(View.GONE);
				mapFragment = new EososMapFragment(lstFragment.getClubs(),"","favourites");
                ((IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader)).setText(getString(R.string.location));
				addFragment(R.id.lnrFragment, mapFragment);
			}
		});

		txtList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                txtList.setTextColor(getResources().getColor(R.color.red));
                txtMap.setTextColor(getResources().getColor(R.color.txt_color));
				imgMapType.setVisibility(View.GONE);
				imgUnit.setVisibility(View.VISIBLE);
                ((IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader)).setText(getString(R.string.favourites));
				addFragment(R.id.lnrFragment, lstFragment);
			}
		});
		imgMapType.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                aQuery.id(imgMapType).image(getResources().getDrawable(R.drawable.map_btn_icon_selected));
				mapFragment.showPopupMapType(v, new OnDismissListener() {

					@Override
					public void onDismiss() {
                        aQuery.id(imgMapType).image(getResources().getDrawable(R.drawable.map_btn_icon_normal));
					}
				});
			}
		});

	}

}
