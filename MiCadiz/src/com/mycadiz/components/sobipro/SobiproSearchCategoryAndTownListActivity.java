package com.mycadiz.components.sobipro;

import com.mycadiz.src.R;

/**
 * Activity class for SobiproSectionCategoryActivity view
 * 
 * @author tasol
 * 
 */

public class SobiproSearchCategoryAndTownListActivity extends SobiproMasterActivity implements SobiproTagHolder {
    private String IN_SECTION_ID;
    private String IN_TYPE;
    private SobiproSearchCategoryAndTownListFragment categoryAndTownListFragment;
	@Override
	public int setLayoutId() {
		return R.layout.sobipro_search_category_and_town;
	}

	@Override
	public void initComponents() {
		getIntentData();
	}

	@Override
	public void prepareViews() {
        getIntentData();
        categoryAndTownListFragment =  new SobiproSearchCategoryAndTownListFragment(IN_SECTION_ID,IN_TYPE);
        addFragment(R.id.lnrFragment,categoryAndTownListFragment);
	}

	@Override
	public void setActionListeners() {
	}


	/**
	 * Class methods.
	 */

	/**
	 * This method is used to get intent data.
	 */
	private void getIntentData() {
		try {
            IN_SECTION_ID = getIntent().getStringExtra("IN_SECTION_ID")!=null?getIntent().getStringExtra("IN_SECTION_ID"):"";
            IN_TYPE = getIntent().getStringExtra("IN_TYPE")!=null?getIntent().getStringExtra("IN_TYPE"):"";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
