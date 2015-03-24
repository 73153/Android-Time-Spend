package com.ijoomer.common.classes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.ijoomer.caching.IjoomerCachingConstants;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.custom.interfaces.IjoomerSharedPreferences;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.menubuilder.MenuDrawer;
import com.ijoomer.topcarlondon.src.R;
import com.smart.android.framework.SmartAndroidActivity;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

/**
 * This Class Contains All Method Related To IjoomerSuperMaster.
 * 
 * @author tasol
 * 
 */
public abstract class IjoomerSuperMaster extends SmartAndroidActivity implements IjoomerSharedPreferences {

	private AQuery androidQuery;

	private ArrayList<SmartListItem> listDataSideMenu = new ArrayList<SmartListItem>();

	private static String screenCaption;
	private final String MENUITEM = "menuitem";
	private final String TAB = "tab";
	private final String TAB_ACTIVE = "tab_active";
	private final String ITEMVIEW = "itemview";
	private final String ITEMDATA = "itemdata";
	private final String ITEMCAPTION = "itemcaption";
	private final String ICON = "icon";
	private String imgPath;
	private static boolean isSideMenuOpen = false;

	private static String previousScreen = "";

	/**
	 * Constructor
	 */
	public IjoomerSuperMaster() {

		IjoomerCachingConstants.unNormalizeFields = CoreCachingConstants.getUnnormlizeFields();
		IjoomerUtilities.mSmartAndroidActivity = this;
		setApplicationOrientation(SCREEN_ORIENTATION_PORTRAIT);
		IjoomerApplicationConfiguration.setDefaultConfiguration(this);
		androidQuery = new AQuery(this);

	}

	/**
	 * Overrides method
	 */

	@Override
	public void loadHeaderComponents() {
		try {
			getScreenRootView().getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {

					Rect r = new Rect();
					getScreenRootView().getWindowVisibleDisplayFrame(r);

					final FrameLayout f = (FrameLayout) findViewById(123);
					int heightDiff = getScreenRootView().getRootView().getHeight() - (r.bottom - r.top);
					if (heightDiff > 100) {
						getFooterView().setVisibility(View.GONE);
						getBottomAdvertiseView().setVisibility(View.GONE);
						f.setVisibility(View.VISIBLE);

						f.setOnTouchListener(new OnTouchListener() {

							@Override
							public boolean onTouch(View v, MotionEvent event) {
								if (isKeyboardHideOnOutsideTouch()) {

									hideSoftKeyboard();
									return true;
								} else {
									return false;
								}
							}
						});
					} else {
						if (getFooterView().getVisibility() == View.GONE && f.getVisibility() == View.VISIBLE && !isSideMenuOpen) {
							getBottomAdvertiseView().setVisibility(View.VISIBLE);
							FrameLayout ff = (FrameLayout) findViewById(123);
							ff.setVisibility(View.GONE);
							ff.setOnTouchListener(null);

							getFooterView().setVisibility(View.VISIBLE);

						}
					}
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		IjoomerCachingConstants.unNormalizeFields = CoreCachingConstants.getUnnormlizeFields();
		try {
			applyTabMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (IjoomerApplicationConfiguration.isReloadRequired()) {
				IjoomerApplicationConfiguration.setDefaultConfiguration(this);
				IjoomerApplicationConfiguration.setReloadRequired(true);
			} else {
				IjoomerApplicationConfiguration.setDefaultConfiguration(this);
			}
			IjoomerUtilities.mSmartAndroidActivity = this;
			isSideMenuOpen = false;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public View setBottomAdvertisement() {

		return null;
	}

	@Override
	public View setTopAdvertisement() {

		return null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onBackPressed() {
		final int drawerState = mMenuDrawer.getDrawerState();
		if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
			mMenuDrawer.closeMenu();
			return;
		}
		super.onBackPressed();
	}

	@Override
	public SmartListAdapterWithHolder getMoreMenuListAdapter(ArrayList<SmartListItem> moreListData) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method used to get screen caption.
	 * 
	 * @return represented {@link String}
	 */
	public String getScreenCaption() {
		return screenCaption;
	}

	/**
	 * This method used to set screen caption.
	 * 
	 * @param screenCaption
	 *            represented screen caption
	 */
	@SuppressWarnings("static-access")
	public void setScreenCaption(String screenCaption) {
		this.screenCaption = screenCaption;
	}

	/**
	 * This method used to set image uri.
	 * 
	 * @return represented {@link Uri}
	 */
	public Uri setImageUri() {
		// Store image in dcim
		File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
		Uri imgUri = Uri.fromFile(file);
		this.imgPath = file.getAbsolutePath();
		return imgUri;
	}

	/**
	 * This method used to get Image path.
	 * 
	 * @return
	 */
	public String getImagePath() {
		return imgPath;
	}

	/**
	 * This method used to get absolute path from uri.
	 * 
	 * @param uri
	 *            represented uri
	 * @return represented {@link String}
	 */
	public String getAbsolutePath(Uri uri) {
		String[] projection = { MediaColumns.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}

	/**
	 * This method used to decode file from string path.
	 * 
	 * @param path
	 *            represented path
	 * @return represented {@link Bitmap}
	 */
	public Bitmap decodeFile(String path) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, o);
			// The new size we want to scale to
			final int REQUIRED_SIZE = 70;

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeFile(path, o2);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * This method used to decode file from uri path.
	 * 
	 * @param path
	 *            represented path
	 * @return represented {@link Bitmap}
	 */
	public Bitmap decodeFile(Uri path) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;

			BitmapFactory.decodeFile(getAbsolutePath(path), o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 70;

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeFile(getAbsolutePath(path), o2);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * This method used to logout from twitter.
	 */
	public void twitterLogout() {
		getSmartApplication().writeSharedPreferences(SP_TWITTER_TOKEN, null);
		getSmartApplication().writeSharedPreferences(SP_TWITTER_SECRET_TOKEN, null);
	}

	/**
	 * This method used to hide soft keyboard.
	 */
	public void hideSoftKeyboard() {
		try {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method used to show soft keyboard.
	 */
	public void showSoftKeyboard() {
		try {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.toggleSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method used to do ellipsize to textview.
	 * 
	 * @param tv
	 *            represented TextView do ellipsize
	 * @param maxLine
	 *            represented max line to show
	 */
	public void doEllipsize(final IjoomerTextView tv, final int maxLine) {
		ViewTreeObserver vto = tv.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {

				ViewTreeObserver obs = tv.getViewTreeObserver();
				obs.removeGlobalOnLayoutListener(this);
				if (maxLine <= 0) {
					int lineEndIndex = tv.getLayout().getLineEnd(0);
					String text = tv.getText().subSequence(0, lineEndIndex - 3) + "...";
					tv.setText(text);
				} else if (tv.getLineCount() >= maxLine) {
					int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
					String text = tv.getText().subSequence(0, lineEndIndex - 3) + "...";
					tv.setText(text);
				}
			}
		});
	}

	/**
	 * This method used to convert json to map.
	 * 
	 * @param object
	 *            represented json object
	 * @return represented {@link Map<String, String>}
	 * @throws JSONException
	 *             represented {@link JSONException}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, String> jsonToMap(JSONObject object) throws JSONException {
		Map<String, String> map = new HashMap();
		Iterator keys = object.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			map.put(key, fromJson(object.get(key)).toString());
		}
		return map;
	}

	/**
	 * This method used to convert json to Object.
	 * 
	 * @param json
	 *            represented json object
	 * @return represented {@link Object}
	 * @throws JSONException
	 *             represented {@link JSONException}
	 */
	private Object fromJson(Object json) throws JSONException {
		if (json == JSONObject.NULL) {
			return null;
		} else if (json instanceof JSONObject) {
			return jsonToMap((JSONObject) json);
		} else if (json instanceof JSONArray) {
			return toList((JSONArray) json);
		} else {
			return json;
		}
	}

	/**
	 * This method used to convert json array to List.
	 * 
	 * @param array
	 *            represented json array
	 * @return represented {@link List}
	 * @throws JSONException
	 *             represented {@link JSONException}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List toList(JSONArray array) throws JSONException {
		List list = new ArrayList();
		int size = array.length();
		for (int i = 0; i < size; i++) {
			list.add(fromJson(array.get(i)));
		}
		return list;
	}

	private JSONArray getTabJsonArray() {
		JSONArray jsonArray = new JSONArray();

		String[] tabBarCaptionArray = getResources().getStringArray(R.array.tab_bar_item);
		ArrayList<LinkedHashMap<String, Integer>> tabBarImageList = getTabImageList();
		try {
			for (int i = 0; i < tabBarCaptionArray.length; i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(TAB, tabBarImageList.get(i).get(TAB));
				jsonObject.put(TAB_ACTIVE, tabBarImageList.get(i).get(TAB_ACTIVE));
				jsonObject.put(ITEMCAPTION, tabBarCaptionArray[i]);
				jsonArray.put(jsonObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	private ArrayList<LinkedHashMap<String, Integer>> getTabImageList() {
		ArrayList<LinkedHashMap<String, Integer>> tabList = new ArrayList<LinkedHashMap<String, Integer>>();
		LinkedHashMap<String, Integer> tab1 = new LinkedHashMap<String, Integer>();
		tab1.put(TAB, R.drawable.tab_book_now);
		tab1.put(TAB_ACTIVE, R.drawable.tab_book_now_active);
		LinkedHashMap<String, Integer> tab2 = new LinkedHashMap<String, Integer>();
		tab2.put(TAB, R.drawable.tab_contact_us);
		tab2.put(TAB_ACTIVE, R.drawable.tab_contact_us_active);
		LinkedHashMap<String, Integer> tab3 = new LinkedHashMap<String, Integer>();
		tab3.put(TAB, R.drawable.tab_my_booking);
		tab3.put(TAB_ACTIVE, R.drawable.tab_my_booking_active);
		LinkedHashMap<String, Integer> tab4 = new LinkedHashMap<String, Integer>();
		tab4.put(TAB, R.drawable.tab_area_map);
		tab4.put(TAB_ACTIVE, R.drawable.tab_area_map_active);

		tabList.add(tab1);
		tabList.add(tab2);
		tabList.add(tab3);
		tabList.add(tab4);

		return tabList;
	}

	/**
	 * This method used to show tab bar.
	 */
	@Override
	public void showTabBar() {

		try {
			LayoutInflater inflater = LayoutInflater.from(this);

			LinearLayout tabbar = new LinearLayout(this);
			tabbar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			tabbar.setGravity(Gravity.CENTER);
			((ViewGroup) getFooterView().getChildAt(0)).removeAllViews();
			((ViewGroup) getFooterView().getChildAt(0)).addView(tabbar, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

			JSONArray tabMenuData = getTabJsonArray();
			for (int i = 0; i < tabMenuData.length(); i++) {

				LinearLayout lnrItem = (LinearLayout) inflater.inflate(R.layout.ijoomer_tab_item, null);
				lnrItem.setId(i);
				lnrItem.setTag(tabMenuData.getJSONObject(i));
				((IjoomerTextView) lnrItem.getChildAt(1)).setText(tabMenuData.getJSONObject(i).getString(ITEMCAPTION));

				lnrItem.setOnClickListener(new OnClickListener() {

					@SuppressWarnings("unchecked")
					@Override
					public void onClick(View v) {

						try {
							JSONObject obj = (JSONObject) v.getTag();
							launchActivity(obj);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

				String intentTabCaption = null;

				try {
					intentTabCaption = new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMCAPTION);
				} catch (Exception e) {
					e.printStackTrace();
					intentTabCaption = null;
				}

				tabbar.addView(lnrItem, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
				try {

					if (intentTabCaption.equals(((JSONObject) tabMenuData.getJSONObject(i)).getString(ITEMCAPTION))) {
						IjoomerMenus.getInstance().setSelectedScreenName(((JSONObject) tabMenuData.getJSONObject(i)).getString(ITEMCAPTION));
						previousScreen = ((JSONObject) tabMenuData.getJSONObject(i)).getString(ITEMCAPTION);
						androidQuery.id((ImageView) lnrItem.getChildAt(0)).image(getResources().getDrawable(tabMenuData.getJSONObject(i).getInt(TAB_ACTIVE)));
					} else {
						androidQuery.id((ImageView) lnrItem.getChildAt(0)).image(getResources().getDrawable(tabMenuData.getJSONObject(i).getInt(TAB)));
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (previousScreen.equals(((JSONObject) tabMenuData.getJSONObject(i)).getString(ITEMCAPTION))) {
						IjoomerMenus.getInstance().setSelectedScreenName(((JSONObject) tabMenuData.getJSONObject(i)).getString(ITEMCAPTION));
						previousScreen = ((JSONObject) tabMenuData.getJSONObject(i)).getString(ITEMCAPTION);
						androidQuery.id((ImageView) lnrItem.getChildAt(0)).image(getResources().getDrawable(tabMenuData.getJSONObject(i).getInt(TAB_ACTIVE)));
					} else {
						androidQuery.id((ImageView) lnrItem.getChildAt(0)).image(getResources().getDrawable(tabMenuData.getJSONObject(i).getInt(TAB)));
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method used to apply tab menu.
	 */
	public void applyTabMenu() {
		showTabBar();
	}

	/**
	 * This method used to get slide menu difference.
	 * 
	 * @param varient
	 *            represented variant
	 * @return represented {@link Integer}
	 */
	public int getSlideDifference(int varient) {
		Resources resources = getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		return ((metrics.densityDpi * varient) / 160);
	}

	/**
	 * This method used to get privacy code from privacy.
	 * 
	 * @param privacy
	 *            represented privacy name
	 * @return represented {@link String}
	 */
	public String getPrivacyCode(String privacy) {

		ArrayList<String> list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.wall_post_type)));

		if (privacy.equals(list.get(0))) {
			return "0";
		} else if (privacy.equals(list.get(1))) {
			return "20";
		} else if (privacy.equals(list.get(2))) {
			return "30";
		} else if (privacy.equals(list.get(3))) {
			return "40";
		}
		return "0";
	}

	/**
	 * This method used to get privacy from privacy code.
	 * 
	 * @param privacy
	 *            represented privacy code
	 * @return represented {@link String}
	 */
	public String getPrivacyString(String privacy) {

		ArrayList<String> list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.wall_post_type)));

		if (privacy.equals("0")) {
			return list.get(0);
		} else if (privacy.equals("20")) {
			return list.get(1);
		} else if (privacy.equals("30")) {
			return list.get(2);
		} else if (privacy.equals("40")) {
			return list.get(3);
		}
		return list.get(0);
	}

	/**
	 * This method used to get privacy index from privacy name or code.
	 * 
	 * @param privacy
	 *            represented privacy code or name
	 * @return represented {@link Integer}
	 */
	public int getPrivacyIndex(String privacy) {

		ArrayList<String> list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.wall_post_type)));

		if (privacy.equals("0") || privacy.equals(list.get(0))) {
			return 0;
		} else if (privacy.equals("20") || privacy.equals(list.get(1))) {
			return 1;
		} else if (privacy.equals("30") || privacy.equals(list.get(2))) {
			return 2;
		} else if (privacy.equals("40") || privacy.equals(list.get(3))) {
			return 3;
		}
		return 0;
	}

	/**
	 * This method used to get privacy from at index.
	 * 
	 * @param privacyAtIndex
	 *            represented privacy index at
	 * @return represented {@link String}
	 */
	public String getPrivacyStringAtIndex(int privacyAtIndex) {

		ArrayList<String> list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.wall_post_type)));

		return list.get(privacyAtIndex);
	}

	/**
	 * This method used to get privacy code from at index.
	 * 
	 * @param privacyAtIndex
	 *            represented privacy index at
	 * @return represented {@link String}
	 */
	public String getPrivacyCodeAtIndex(int privacyAtIndex) {

		ArrayList<String> list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.wall_post_type)));

		String privacy = list.get(privacyAtIndex);

		if (privacy.equals(list.get(0))) {
			return "0";
		} else if (privacy.equals(list.get(1))) {
			return "20";
		} else if (privacy.equals(list.get(2))) {
			return "30";
		} else if (privacy.equals(list.get(3))) {
			return "40";
		}
		return "0";
	}

	/**
	 * This method used to string array from string with (,) separated.
	 * 
	 * @param value
	 *            represented value
	 * @return represented {@link String} array
	 */
	public String[] getStringArray(final String value) {
		try {
			final JSONArray temp = new JSONArray(value);
			int length = temp.length();
			if (length > 0) {
				final String[] recipients = new String[length];
				for (int i = 0; i < length; i++) {
					recipients[i] = temp.getString(i).equalsIgnoreCase("null") ? "1" : temp.getString(i);
				}
				return recipients;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}


	/**
	 * This method used to launch activity.
	 * 
	 * @param obj
	 *            represented json object predefined activity
	 */
	public void launchActivity(JSONObject obj) {
		try {

			setScreenCaption(obj.getString(ITEMCAPTION));
			final String className = IjoomerScreenHolder.originalScreens.get(obj.getString(ITEMCAPTION));

			IjoomerMenus.getInstance().setTabBarData(null);
			IjoomerMenus.getInstance().setSideMenuData(null);

			final Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClassName(IjoomerSuperMaster.this, className);
			// intent.putExtra("IN_USERID", "0");
			/*
			 * if (obj.getString(ITEMVIEW).equals("Login") &&
			 * (getSmartApplication
			 * ().readSharedPreferences().getString(SP_LOGIN_REQ_OBJECT,
			 * "")).length() > 0) { logout(); return; } else if
			 * (obj.getString(ITEMVIEW).equals("Registration")) { logout();
			 * return; }
			 * 
			 * else if (obj.getString(ITEMVIEW).equals("Web")) { try {
			 * intent.setClassName(IjoomerSuperMaster.this, className);
			 * intent.putExtra("url", new
			 * JSONObject(obj.getString(ITEMDATA)).getString("url") + "");
			 * startActivity(intent); return; } catch (Exception e) {
			 * e.printStackTrace(); } }
			 */
			intent.putExtra("IN_OBJ", obj.toString());
			startActivity(intent);
			finish();
		} catch (Exception e) {
			tong(getString(R.string.sdk_error));
			e.printStackTrace();
		}
	}


	/**
	 * This method used to add fragment to given layout id.
	 * 
	 * @param layoutId
	 *            represented layout id
	 * @param fragment
	 *            represented fragment
	 */
	public void addFragment(int layoutId, Fragment fragment) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(layoutId, fragment);
		ft.commit();
	}

	@Override
	public void initTheme() {
	}

	public Uri getVideoPlayURI(String videoUrl) {

		String video_id = "";
		if (videoUrl != null && videoUrl.trim().length() > 0) {

			String expression = "^.*((youtu.be" + "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
			CharSequence input = videoUrl;
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(input);
			if (matcher.matches()) {
				String groupIndex1 = matcher.group(7);
				if (groupIndex1 != null && groupIndex1.length() == 11)
					video_id = groupIndex1;
			}
		}

		if (video_id.trim().length() > 0) {
			return Uri.parse("ytv://" + video_id);
		} else {
			return Uri.parse("mp4://" + videoUrl);
		}
	}

}
