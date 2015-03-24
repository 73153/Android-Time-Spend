package com.ijoomer.components.iproperty;



import static com.ijoomer.components.iproperty.IPropertyTagHolder.ITEMDATA;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.custom.interfaces.SelectImageDialogListner;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.library.iproperty.IPropertyDetailDataProvider;
import com.ijoomer.page.indicator.CirclePageIndicator;
import com.ijoomer.tracethesteps.src.R;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

public class IPropertyDetailGalleryActivity extends IPropertyMasterActivity {
	private final int CAPTURE_IMAGE_USER_AVATAR = 2;
	private String IN_ADD_PICTURE;
	private String IN_OBJ;
	private String IN_PROPERTY_ID;
	private String IN_PROPERTY_TITLE;
	private final int PICK_IMAGE_USER_AVATAR = 1;
	private PageAdapter adapter;
	private IjoomerButton btnAdd;
	private CirclePageIndicator indicator;
	private ArrayList<IPropertyGalleryFragment> photoFragmetStack = new ArrayList<IPropertyGalleryFragment>();
	private String picturesLimit;
	private IPropertyDetailDataProvider provider;
	private ViewPager viewPager;

	private void getPictures() {
		provider.getPictures(IN_PROPERTY_ID, picturesLimit, new WebCallListener() {
			public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object paramAnonymousObject) {
				if (responseCode == 200){
					setGallery();
				}else{
					responseErrorMessageHandler(responseCode, false);
				}
			}

			public void onProgressUpdate(int paramAnonymousInt) {
			}
		});
	}
	
	private void responseErrorMessageHandler(final int responseCode, final boolean finishActivityOnConnectionProblem) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.iproperty_pictures), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
                getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {
                
            }
        });

    }

	private void setGallery() {
		adapter = new PageAdapter(getSupportFragmentManager());
		viewPager.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, calculateheight()));
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(0);
		indicator.setPageColor(0);
		indicator.setStrokeColor(Color.parseColor(getString(R.color.orange)));
		indicator.setStrokeWidth(convertSizeToDeviceDependent(1));
		indicator.setRadius(convertSizeToDeviceDependent(3));
		indicator.setFillColor(Color.parseColor(getString(R.color.orange)));
		indicator.setViewPager(viewPager, 0);
		indicator.setSnap(true);
		if (provider.getTotalCount() <= 0)
			indicator.setVisibility(View.GONE);
		indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				((IPropertyGalleryFragment) adapter.getItem(arg0)).notifyChanges();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	public int calculateheight() {
		int calculateHeight;
		int totalCount = provider.getTotalCount();
		if (totalCount < provider.getPageLimit()) {
			calculateHeight = (((totalCount % 4 == 0 ? totalCount / 4 : totalCount / 4 + 1) * 270) / 3);

			return convertSizeToDeviceDependent(calculateHeight);

		}
		return convertSizeToDeviceDependent(270);
	}

	public ArrayList<IPropertyGalleryFragment> getPhotoFragmetStack() {
		return photoFragmetStack;
	}

	public void initComponents() {
		viewPager = ((ViewPager) findViewById(R.id.viewPager));
		indicator = ((CirclePageIndicator) findViewById(R.id.indicator));
		btnAdd = ((IjoomerButton) findViewById(R.id.btnAdd));
		provider = new IPropertyDetailDataProvider(this);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode == RESULT_OK) {
			if (requestCode == PICK_IMAGE_USER_AVATAR)
				try {
					loadNew(IPropertyPictureAddActivity.class, this, false, "IN_PICTURE_PATH", getAbsolutePath(intent.getData()), "IN_PROPERTY_ID", IN_PROPERTY_ID,
							"IN_PROPERTY_TITLE", IN_PROPERTY_TITLE);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			if (requestCode == CAPTURE_IMAGE_USER_AVATAR)
				try {
					loadNew(IPropertyPictureAddActivity.class, this, false, "IN_PICTURE_PATH", getImagePath(), "IN_PROPERTY_ID", IN_PROPERTY_ID, "IN_PROPERTY_TITLE",
							IN_PROPERTY_TITLE);
				} catch (Throwable e) {
					e.printStackTrace();
				}
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

	protected void onResume() {
		if (IjoomerApplicationConfiguration.isReloadRequired()) {
			IjoomerApplicationConfiguration.setReloadRequired(false);
			prepareViews();
		}
		super.onResume();
	}

	public void prepareViews() {
		try {
			IN_PROPERTY_ID = getIntent().getStringExtra("IN_PROPERTY_ID") != null ? getIntent().getStringExtra("IN_PROPERTY_ID") : "";
			IN_OBJ = getIntent().getStringExtra("IN_OBJ") != null ? getIntent().getStringExtra("IN_OBJ") : "";
			IN_PROPERTY_TITLE = getIntent().getStringExtra("IN_PROPERTY_TITLE") != null ? getIntent().getStringExtra("IN_PROPERTY_TITLE") : "";
			IN_ADD_PICTURE = getIntent().getStringExtra("IN_ADD_PICTURE") != null ? getIntent().getStringExtra("IN_ADD_PICTURE") : "0";
			picturesLimit = new JSONObject(IN_OBJ).getJSONObject(ITEMDATA).getString("propertyGalleryImagesLimit");
			if (IN_ADD_PICTURE.equals("1")) {
				btnAdd.setVisibility(View.VISIBLE);
			}
		} catch (Throwable e) {
			picturesLimit = "8";
			e.printStackTrace();
		}
		
		getPictures();

	}

	public void setActionListeners() {
		btnAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				IjoomerUtilities.selectImageDialog(new SelectImageDialogListner() {
					public void onCapture() {
						Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
						localIntent.putExtra("output", setImageUri());
						startActivityForResult(localIntent, 2);
					}

					public void onPhoneGallery() {
						Intent localIntent = new Intent();
						localIntent.setType("image/*");
						localIntent.setAction("android.intent.action.GET_CONTENT");
						startActivityForResult(Intent.createChooser(localIntent, ""), 1);
					}
				});
			}
		});
	}

	public int setLayoutId() {
		return R.layout.iproperty_property_gallery;
	}

	private class PageAdapter extends FragmentStatePagerAdapter {
		private boolean isInitial = true;

		public PageAdapter(FragmentManager fm) {
			super(fm);
		}

		public int getCount() {
			return provider.getTotalCount() % Integer.parseInt(picturesLimit) == 0 ?provider.getTotalCount() / Integer.parseInt(picturesLimit):1 + provider.getTotalCount() / Integer.parseInt(picturesLimit);
		}

		public Fragment getItem(int pos) {
			try {
				return photoFragmetStack.get(pos);
			} catch (Exception e) {
				IPropertyGalleryFragment fragment = new IPropertyGalleryFragment(pos + 1, IN_PROPERTY_ID, picturesLimit);
				if (isInitial) {
					fragment.setInitial(true);
					isInitial = false;
				}
				photoFragmetStack.add(fragment);
				return fragment;
			}
		}

		public int getItemPosition(Object paramObject) {
			return provider.getTotalCount();
		}
	}
}