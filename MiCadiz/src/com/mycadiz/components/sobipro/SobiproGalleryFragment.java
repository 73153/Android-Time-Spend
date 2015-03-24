package com.mycadiz.components.sobipro;

import android.annotation.SuppressLint;
import android.view.View;

import com.androidquery.AQuery;
import com.mycadiz.customviews.TouchImageView;
import com.mycadiz.src.R;
import com.smart.framework.SmartActivity;
import com.smart.framework.SmartFragment;

/**
 * This Fragment Contains All Method Related To SobiproGalleryFragment.
 *
 * @author tasol
 *
 */
@SuppressLint("ValidFragment")
public class SobiproGalleryFragment extends SmartFragment implements SobiproTagHolder {

    private TouchImageView imgPhotoDetail;

    private AQuery androidQuery;

    private String imagePath;

    private float scales ;

    /**
     * Constructor
     * @param path represented image path
     */
    public SobiproGalleryFragment(String path) {
        androidQuery = new AQuery(getActivity());
        this.imagePath = path;
    }

    /**
     * Overrides methods
     */

    @Override
    public int setLayoutId() {
        return R.layout.sobipro_gallary_fragment;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public void initComponents(View currentView) {
        imgPhotoDetail = (TouchImageView) currentView.findViewById(R.id.imgPhotoDetail);
    }

    @Override
    public void prepareViews(View currentView) {
        if(androidQuery.getCachedImage(imagePath)!=null) {
            imgPhotoDetail.setImageBitmap(androidQuery.getCachedImage(imagePath));
        }else{
            androidQuery.id(imgPhotoDetail).image(imagePath,true,true,((SmartActivity)getActivity()).getDeviceWidth(),0);
        }
    }

    @Override
    public void setActionListeners(View currentView) {

    }

}
