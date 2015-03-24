package com.ijoomer.topcarlondon.src;

import pl.mg6.android.maps.extensions.GoogleMap;
import android.location.Address;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ijoomer.common.classes.IjoomerSuperMaster;
import com.ijoomer.common.classes.IjoomerUtilities;

/**
 * This Class Contains All Method Related To JomMapActivity.
 * 
 * @author tasol
 * 
 */
public class BookCarContactUsMapActivity extends IjoomerSuperMaster {

	private GoogleMap googleMap;

	/**
	 * Overrides methods
	 */

	@Override
	public int setLayoutId() {
		return R.layout.book_car_contact_us_map;
	}

	@Override
	public void initComponents() {

		googleMap = getMapView();
		googleMap.moveCamera(CameraUpdateFactory.zoomBy(5));
		googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

	}

	@Override
	public void prepareViews() {
		setMapData();
	}


	@Override
	public void setActionListeners() {
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {

	}

	/**
	 * Class methods
	 */

	/**
	 * This method used to populate map.
	 */
	private void setMapData() {

		String contact = getString(R.string.contact_us_map);
		Address address = IjoomerUtilities.getLatLongFromAddress(contact);
		googleMap.addMarker(new MarkerOptions().title(getString(R.string.app_name)).snippet(getString(R.string.contact_us))
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).position(new LatLng(address.getLatitude(), address.getLongitude())));
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(address.getLatitude(), address.getLongitude())));
		googleMap.moveCamera(CameraUpdateFactory.zoomBy(5));

		googleMap.setMyLocationEnabled(true);
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
