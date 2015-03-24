package com.mycadiz.components.sobipro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.mycadiz.common.classes.ViewHolder;
import com.mycadiz.customviews.IjoomerViewPager;
import com.mycadiz.src.R;
import com.smart.framework.ItemView;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This Class Contains All Method Related To SobiproGalleryActivity.
 *
 * @author tasol
 *
 */
@SuppressWarnings("deprecation")
public class SobiproGalleryActivity extends SobiproMasterActivity {

    private Gallery gallary;
    public static IjoomerViewPager viewPager;
    private ProgressBar pbrLoadImage;

    private ArrayList<SmartListItem> listData = new ArrayList<SmartListItem>();
    private JSONArray imagesArray;
    private AQuery androidQuery;
    private PageAdapter adapter;

    private String IN_PHOTOS_PATHS;
    private int IN_INDEX;

    /**
     * Overrides methods
     */

    @Override
    public int setLayoutId() {
        return R.layout.sobipro_gallery;
    }

    @Override
    public void initComponents() {
        viewPager = (IjoomerViewPager) findViewById(R.id.viewPager);
        pbrLoadImage = (ProgressBar) findViewById(R.id.pbrLoadImage);
        gallary = (Gallery) findViewById(R.id.gallary);
        gallary.setSpacing(5);

        androidQuery = new AQuery(this);
        getIntentData();
    }

    @Override
    public void prepareViews() {
        adapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(IN_INDEX, true);

        prepareGallary();
        gallary.setAdapter(getGallaryAdapter());
        gallary.setSelection(IN_INDEX);
    }

    @Override
    public void setActionListeners() {

        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                gallary.setSelection(arg0, true);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        gallary.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                viewPager.setCurrentItem(arg2, true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    /**
     * Class methods
     */

    /**
     * This method used to get intent data.
     */
    private void getIntentData() {
        IN_INDEX = getIntent().getIntExtra("IN_INDEX",0);
        IN_PHOTOS_PATHS = getIntent().getStringExtra("IN_PHOTOS_PATHS") != null ? getIntent().getStringExtra("IN_PHOTOS_PATHS") : "";
        try {
            imagesArray = new JSONArray(IN_PHOTOS_PATHS);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        if(imagesArray.length()<=IN_INDEX){
            IN_INDEX--;
        }
    }

    /**
     * This method used to prepare gallery.
     */
    private void prepareGallary() {

        for (int i = 0; i < imagesArray.length(); i++) {
            SmartListItem item = new SmartListItem();
            item.setItemLayout(R.layout.sobipro_gallary_item);
            ArrayList<Object> obj = new ArrayList<Object>();
            try {
                obj.add(imagesArray.get(i));
            } catch (Throwable e) {
                e.printStackTrace();
            }
            item.setValues(obj);
            listData.add(item);
        }

    }

    /**
     * List adapter for gallery.
     *
     * @return represented {@link com.smart.framework.SmartListAdapterWithHolder}
     */
    private SmartListAdapterWithHolder getGallaryAdapter() {

        SmartListAdapterWithHolder adapterWithHolder = new SmartListAdapterWithHolder(this, R.layout.sobipro_gallary_item, listData, new ItemView() {

            @Override
            public View setItemView(final int position, View v, SmartListItem item, final ViewHolder holder) {
                holder.imgItem = (ImageView) v.findViewById(R.id.imgItem);
                final JSONObject image = (JSONObject) item.getValues().get(0);

                try {
                    if(pbrLoadImage.getVisibility() == View.GONE){
                        pbrLoadImage.setVisibility(View.VISIBLE);
                    }
                    if(androidQuery.getCachedImage(image.getString("image"))!=null) {
                        holder.imgItem.setImageBitmap(androidQuery.getCachedImage(image.getString("image")));
                    }else{
                        androidQuery.id(holder.imgItem).image(image.getString("image"),true,true,getDeviceWidth(),0);
                    }
                    pbrLoadImage.setVisibility(View.GONE);
                } catch (Throwable e) {
                    pbrLoadImage.setVisibility(View.GONE);
                    e.printStackTrace();
                }

                v.setLayoutParams(new Gallery.LayoutParams(70, 70));
                return v;
            }

            @Override
            public View setItemView(int position, View v, SmartListItem item) {
                return null;
            }
        });
        return adapterWithHolder;

    }

    /**
     * Inner class
     *
     * @author tasol
     *
     */
    private class PageAdapter extends FragmentStatePagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int pos) {
            System.gc();
            try {
                SobiproGalleryFragment fragment = new SobiproGalleryFragment(imagesArray.getJSONObject(pos).getString("image"));
                return fragment;
            } catch (Throwable e) {
            }
            return null;
        }

        @Override
        public int getCount() {
            return imagesArray.length();
        }

    }

}
