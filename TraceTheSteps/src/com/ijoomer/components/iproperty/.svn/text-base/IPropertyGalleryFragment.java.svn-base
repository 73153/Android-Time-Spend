package com.ijoomer.components.iproperty;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.classes.ViewHolder;
import com.ijoomer.library.iproperty.IPropertyDetailDataProvider;
import com.ijoomer.tracethesteps.src.R;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.ItemView;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartFragment;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

@SuppressLint({ "ValidFragment" })
public class IPropertyGalleryFragment extends SmartFragment {
	private AQuery androidQuery;
	private GridView grdPhoto;
	private SmartListAdapterWithHolder gridAdapter;
	private boolean isInitial = false;
	private String limit;
	private int pageNo;
	private ProgressBar pbrPhotoGrid;
	private ArrayList<HashMap<String, String>> photoData;
	private ArrayList<SmartListItem> photoList;
	private IPropertyDetailDataProvider photoProvider;
	private String propertyId;

	public IPropertyGalleryFragment(int pageNo, String propertyId, String limit) {
		this.pageNo = pageNo;
		this.propertyId = propertyId;
		this.limit = limit;
	}

	private SmartListAdapterWithHolder getPhotoAdapter() {
		SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(getActivity(), R.layout.iproperty_gallery_grid_item, photoList, new ItemView() {
			public View setItemView(int pos, View view, SmartListItem smartItem) {
				return null;
			}

			public View setItemView(final int pos, View view, SmartListItem smartItem, ViewHolder holder) {

				holder.imgAlbumphoto = ((ImageView) view.findViewById(R.id.imgAlbumphoto));
				@SuppressWarnings("unchecked")
				HashMap<String, String> row = (HashMap<String, String>) smartItem.getValues().get(0);
				androidQuery.id(holder.imgAlbumphoto).image((String) row.get("image"), true, true, ((SmartActivity) getActivity()).getDeviceWidth(), 0);
				holder.imgAlbumphoto.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						final ArrayList<HashMap<String, String>> photoData = new ArrayList<HashMap<String, String>>();
						ArrayList<IPropertyGalleryFragment> fragmentList = ((IPropertyDetailGalleryActivity) getActivity()).getPhotoFragmetStack();
						for (IPropertyGalleryFragment jomPhotoFragment : fragmentList) {
							if (jomPhotoFragment.getPhotoData() != null && jomPhotoFragment.getPhotoData().size() > 0) {
								photoData.addAll(jomPhotoFragment.getPhotoData());
							}
						}
						String selectedIndex = String.valueOf(pos + (-1 + pageNo) * Integer.parseInt(limit));
						try {
							((SmartActivity) getActivity()).loadNew(IPropertyPictureDetailActivity.class, getActivity(), false, "IN_PICTURE_LIST", photoData, "IN_SELECTED_INDEX",
									selectedIndex);
						} catch (Throwable e) {
							e.printStackTrace();
						}

					}
				});
				return view;
			}
		});
		return adapterWithHolder;
	}

	private void prepareGrid(ArrayList<HashMap<String, String>> data) {
		if (data != null && data.size() > 0) {
			photoList.clear();
			for (HashMap<String, String> row : data) {
				SmartListItem item = new SmartListItem();
				item.setItemLayout(R.layout.iproperty_gallery_grid_item);
				ArrayList<Object> obj = new ArrayList<Object>();
				obj.add(row);
				item.setValues(obj);
				photoList.add(item);
			}
		}
	}

	public ArrayList<HashMap<String, String>> getPhotoData() {
		return photoData;
	}

	public void initComponents(View paramView) {
		grdPhoto = ((GridView) paramView.findViewById(R.id.grdPhoto));
		pbrPhotoGrid = ((ProgressBar) paramView.findViewById(R.id.pbrPhotoGrid));
	}

	public boolean isInitial() {
		return isInitial;
	}

	public void notifyChanges() {
		if ((photoList == null) || (photoList.size() <= 0)) {
			photoProvider = new IPropertyDetailDataProvider(getActivity());
			photoProvider.restorePagingSettings();
			photoProvider.setPageNo(pageNo);
			photoProvider.setPageLimit(12);
			androidQuery = new AQuery(getActivity());
			photoList = new ArrayList<SmartListItem>();
			photoProvider.getPictures(propertyId, limit, new WebCallListener() {
				public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
					pbrPhotoGrid.setVisibility(View.GONE);
					if (responseCode == 200) {
						setPhotoData(data1);
						prepareGrid(data1);
						gridAdapter = getPhotoAdapter();
						grdPhoto.setAdapter(gridAdapter);
						gridAdapter.notifyDataSetChanged();
						grdPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							public void onItemClick(AdapterView<?> paramAnonymous2AdapterView, View paramAnonymous2View, int paramAnonymous2Int, long paramAnonymous2Long) {
							}
						});
					} else {
						IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_pictures),
								getString(getResources().getIdentifier("code" + responseCode, "string", getActivity().getPackageName())), getString(R.string.ok),
								R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
									public void NeutralMethod() {
									}
								});
					}

				}

				public void onProgressUpdate(int paramAnonymousInt) {
				}
			});
			return;
		}
		pbrPhotoGrid.setVisibility(8);
		gridAdapter = getPhotoAdapter();
		grdPhoto.setAdapter(gridAdapter);
		gridAdapter.notifyDataSetChanged();
	}

	public void prepareViews(View paramView) {
		if (isInitial())
			notifyChanges();
	}

	public void setActionListeners(View paramView) {
	}

	public void setInitial(boolean isInit) {
		isInitial = isInit;
	}

	public int setLayoutId() {
		return R.layout.iproperty_gallery_gridview;
	}

	public View setLayoutView() {
		return null;
	}

	public void setPhotoData(ArrayList<HashMap<String, String>> paramArrayList) {
		photoData = paramArrayList;
	}
}