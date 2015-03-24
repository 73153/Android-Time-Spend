package com.eosos.components.main;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.eosos.common.classes.IjoomerUtilities;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.library.EososDataProvider;
import com.eosos.src.R;
import com.eosos.weservice.WebCallListenerWithCacheInfo;
import com.smart.framework.CustomAlertNeutral;

import java.util.ArrayList;
import java.util.HashMap;

public class EososNearByEntryListActivity extends EososMasterActivity {
    private IjoomerTextView txtList;
    private IjoomerTextView txtMap;
    public ImageView imgMapType;
    public ImageView imgUnit;
    private EososMapFragment mapFragment;
    private EososNearByEntryListFragment lstFragment;
    private EososDataProvider dataProvider;
    private String IN_SECTION_ID = "54";
    public int nearByRangInMile = 15;
    private String latitude, longitude;
    public static boolean isNearData;
    private AQuery aQuery;
    private boolean isUnitPressed;
    private ProgressBar pbrLoading;
    private ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

    @Override
    public int setLayoutId() {
        return R.layout.eosos_near_by_entry;
    }

    @Override
    public void initComponents() {
        txtList = (IjoomerTextView) findViewById(R.id.txtList);
        txtMap = (IjoomerTextView) findViewById(R.id.txtMap);
        imgMapType = (ImageView) findViewById(R.id.imgMapType);
        imgUnit = (ImageView) findViewById(R.id.imgUnit);
        pbrLoading = (ProgressBar) findViewById(R.id.pbrLoading);
        dataProvider = new EososDataProvider(this);
        aQuery = new AQuery(this);
    }

    @Override
    public void prepareViews() {

        ((IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader)).setText(getString(R.string.locations));
        imgMapType.setVisibility(View.VISIBLE);
        imgUnit.setVisibility(View.INVISIBLE);
        latitude = getLatitude();
        longitude = getLongitude();

        txtMap.setTextColor(getResources().getColor(R.color.red));
        isNearData = true;
        imgMapType.setVisibility(View.VISIBLE);
        imgUnit.setVisibility(View.INVISIBLE);
        pbrLoading.setVisibility(View.VISIBLE);
        data = dataProvider.getNearBy(latitude,longitude,nearByRangInMile);
        mapFragment = new EososMapFragment(data, "", "nearby");
        addFragment(R.id.lnrFragment, mapFragment);
        pbrLoading.setVisibility(View.GONE);

        if(data.size()<=0){
            IjoomerUtilities.getCustomOkDialog(getString(R.string.locations), getString(getResources().getIdentifier("code" + (204), "string", getPackageName())),
                    getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                        @Override
                        public void NeutralMethod() {

                        }
                    });
        }
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
                if(!IjoomerUtilities.isNetwokReachable()){
                    IjoomerUtilities.getCustomOkDialog(getString(R.string.locations), getString(R.string.code599), getString(R.string.ok), R.layout.ijoomer_ok_dialog,
                            new CustomAlertNeutral() {

                                @Override
                                public void NeutralMethod() {
                                }
                            });
                }else {
                    if (imgMapType.getVisibility() != View.VISIBLE) {
                        txtMap.setTextColor(getResources().getColor(R.color.red));
                        txtList.setTextColor(getResources().getColor(R.color.txt_color));
                        imgMapType.setVisibility(View.VISIBLE);
                        imgUnit.setVisibility(View.INVISIBLE);
                        mapFragment = new EososMapFragment(data, "", "nearby");
                        addFragment(R.id.lnrFragment, mapFragment);

                        if (data.size() <= 0) {
                            IjoomerUtilities.getCustomOkDialog(getString(R.string.locations), getString(getResources().getIdentifier("code" + (204), "string", getPackageName())),
                                    getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                        @Override
                                        public void NeutralMethod() {

                                        }
                                    }
                            );
                        }
                    }
                }
            }
        });

        txtList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgMapType.getVisibility() != View.INVISIBLE) {
                    txtList.setTextColor(getResources().getColor(R.color.red));
                    txtMap.setTextColor(getResources().getColor(R.color.txt_color));
                    imgMapType.setVisibility(View.INVISIBLE);
                    if (data.size() > 0) {
                        imgUnit.setVisibility(View.VISIBLE);
                    }

                    lstFragment = new EososNearByEntryListFragment(data);
                    addFragment(R.id.lnrFragment, lstFragment);

                    if(data.size()<=0){
                        IjoomerUtilities.getCustomOkDialog(getString(R.string.nearby), getString(getResources().getIdentifier("code" + (204), "string", getPackageName())),
                                getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                    @Override
                                    public void NeutralMethod() {

                                    }
                                });
                    }
                }
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
