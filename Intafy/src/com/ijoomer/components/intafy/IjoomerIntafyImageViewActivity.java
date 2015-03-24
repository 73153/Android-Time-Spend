package com.ijoomer.components.intafy;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.intafy.src.R;

/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafyImageViewActivity extends IjoomerIntafyMaster {

    private ImageView imgAvatar;
    private AQuery aQuery;
    private IjoomerTextView headerLeftText;
    private LinearLayout lnrHeaderLeft;

    /**
     * Overrides methods
     */


    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_avatar_show;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public int setHeaderLayoutId() {
        return R.layout.ijoomer_intafy_header;
    }

    @Override
    public int setFooterLayoutId() {
        return 0;
    }

    @Override
    public void initComponents() {
        imgAvatar = (ImageView)findViewById(R.id.imgAvatar);
        headerLeftText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst);
        lnrHeaderLeft = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderLeft);
        aQuery = new AQuery(this);

    }

    @Override
    public void prepareViews() {
        headerLeftText.setText(getString(R.string.intafy_back));
        aQuery.id(imgAvatar).progress(R.id.pbrLoadImage).image(getIntent().getStringExtra("IN_IMAGE"),true,true,getDeviceWidth(),0);
    }

    @Override
    public void setActionListeners() {
        lnrHeaderLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerLeftText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        headerLeftText.setTextColor(getResources().getColor(R.color.white));
                        finish();
                    }
                }, 1000);
            }
        });
    }
}
