package com.mycadiz.components.sobipro;

import android.support.v4.view.ViewPager;

import com.mycadiz.src.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Activity class for SobiproSectionCategoryActivity view
 * 
 * @author tasol
 * 
 */

public class SobiproSectionCategoryActivity extends SobiproMasterActivity implements SobiproTagHolder {
    private ViewPager viewPager;
    private String IN_SECTION_ID;
    private String IN_CAT_ID = "0";
    private JSONObject IN_OBJ;
    private String IN_PRIVIOUS_ACTIVITY;
    private String IN_PAGELAYOUT;
    private String IN_FEATUREDFIRST = "No";
	private ArrayList<String> pageLayouts;
    private int itemsPerPage = 9;
    private ArrayList<HashMap<String,String>> IN_SUB_CATEGORIES;
	@Override
	public int setLayoutId() {
		return R.layout.sobipro_section_category;
	}

	@Override
	public void initComponents() {
		getIntentData();
		pageLayouts = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.sobipro_pageLayout)));
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        itemsPerPage = 12;
	}

	@Override
	public void prepareViews() {
		try {
			switch (pageLayouts.indexOf(IN_PAGELAYOUT)) {
			case 1:
//				if ((IN_SECTION_ID != null && IN_SECTION_ID.length() > 0) && Integer.parseInt(IN_CAT_ID) == 0)
//					addFragment(R.id.lnrFragment, new SobiproCarSectionCategoryGridFragment(IN_SECTION_ID, IN_CAT_ID, IN_PAGELAYOUT, IN_FEATUREDFIRST));
//				else if ((IN_SECTION_ID != null && IN_SECTION_ID.length() > 0) && Integer.parseInt(IN_CAT_ID) > 0)
//					addFragment(R.id.lnrFragment, new SobiproCarEntriesListFragment(IN_SECTION_ID,IN_CAT_ID, 0, IN_PAGELAYOUT, IN_FEATUREDFIRST));
				break;
			case 2:
//				if ((IN_SECTION_ID != null && IN_SECTION_ID.length() > 0))
//					addFragment(R.id.lnrFragment, new SobiproRestaurantEntriesListFragment(IN_SECTION_ID, IN_PAGELAYOUT, IN_FEATUREDFIRST));
				break;
			default:
                    addFragment(R.id.lnrFragment, new SobiproSectionCategoryGridFragment(IN_SECTION_ID, IN_CAT_ID, pageLayouts.get(0), IN_FEATUREDFIRST, IN_SUB_CATEGORIES, IN_PRIVIOUS_ACTIVITY));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setActionListeners() {
	}

	@Override
	public void initTheme() {
		switch (pageLayouts.indexOf(IN_PAGELAYOUT)) {
		case 1:
			themes = new SobiproTheme[1];
			IMAGE_MAX_SIZE = 1;
			themes[0] = new SobiproTheme();
			themes[0].setCarTheme(getResources().getColor(R.color.sobipro_car_theme_dark_color), getResources().getColor(R.color.sobipro_car_theme_light_color), getResources()
					.getColor(R.color.sobipro_car_theme_text_color), getResources().getColor(R.color.sobipro_car_theme_grid_border_color), R.drawable.ic_launcher,
					R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher);

			break;

		case 2:
			themes = new SobiproTheme[1];
			IMAGE_MAX_SIZE = 1;
			themes[0] = new SobiproTheme();
			break;

		default:
			IMAGE_MAX_SIZE = 1;
			themes = new SobiproTheme[1];
			themes[0] = new SobiproTheme(getResources().getColor(R.color.sobipro_orange), getResources().getColor(R.color.sobipro_lightorange), R.drawable.ic_launcher,
					R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
					R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher);
			break;
		}

	}
	
	/**
	 * Class methods.
	 */

	/**
	 * This method is used to get intent data.
	 */
	private void getIntentData() {
		try {
			IN_OBJ = new JSONObject(new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString(ITEMDATA));
			IN_PAGELAYOUT = IN_OBJ.getString(PAGELAYOUT);
            IN_PRIVIOUS_ACTIVITY = getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY")!=null?getIntent().getStringExtra("IN_PRIVIOUS_ACTIVITY"):new JSONObject(getIntent().getStringExtra("IN_OBJ")).getString("itemcaption");
			IN_FEATUREDFIRST = IN_OBJ.getString(FEATUREDFIRST);
			IN_SECTION_ID = IN_OBJ.getString(SECTION_ID);
			IN_CAT_ID = IN_OBJ.getString(CAT_ID);
            IN_SUB_CATEGORIES = ((ArrayList<HashMap<String,String>>) (getIntent().getSerializableExtra("IN_SUB_CATEGORIES")!=null?getIntent().getSerializableExtra("IN_SUB_CATEGORIES"):new ArrayList<HashMap<String, String>>()));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
