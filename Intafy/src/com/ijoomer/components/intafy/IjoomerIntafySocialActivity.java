package com.ijoomer.components.intafy;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyCircleDataProvider;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertMagnatic;
import com.smart.framework.CustomAlertNeutral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafySocialActivity extends IjoomerIntafyMaster implements IntafyTagHolder {

    private IjoomerIntafySocialFacebookFragment socialFacebookFragment;
    private IjoomerIntafySocialTwitterFragment socialTwitterFragment;
    private IjoomerIntafySocialAllFragment socialAllFragment;
    private IjoomerTextView headerText;
    private ImageView imgAll;
    private ImageView imgFacebook;
    private ImageView imgTwitter;
    private ImageView imgLinkedin;
    private String currentList;

    /**
     * Overrides methods
     */


    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_social;
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
        return R.layout.ijoomer_intafy_social_footer;
    }

    @Override
    public void initComponents() {
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        imgAll = (ImageView) getFooterView().findViewById(R.id.imgAll);
        imgFacebook = (ImageView) getFooterView().findViewById(R.id.imgFacebook);
        imgTwitter = (ImageView) getFooterView().findViewById(R.id.imgTwitter);
        imgLinkedin = (ImageView) getFooterView().findViewById(R.id.imgLinkedin);
    }

    @Override
    public void prepareViews() {

        socialFacebookFragment = new IjoomerIntafySocialFacebookFragment();
        socialTwitterFragment = new IjoomerIntafySocialTwitterFragment();
        socialAllFragment = new IjoomerIntafySocialAllFragment();
        addFragment(R.id.lnrFragment, socialAllFragment);
        imgAll.setImageResource(R.drawable.list_tab_active);
        currentList = "all";
        headerText.setText(getString(R.string.intafy_social_stream));
    }

    @Override
    public void setActionListeners() {
        imgAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerText.setText(getString(R.string.intafy_social_stream));
                imgAll.setImageResource(R.drawable.list_tab_active);
                imgFacebook.setImageResource(R.drawable.facebook_tab);
                imgTwitter.setImageResource(R.drawable.twitter_tab);
                imgLinkedin.setImageResource(R.drawable.linkdin_tab);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if(!currentList.equals("all")){
                            currentList="all";
                            addFragment(R.id.lnrFragment, socialAllFragment);
                        }
                    }
                }, 1000);
            }
        });
        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerText.setText(getString(R.string.intafy_facebook_social_stream));
                imgAll.setImageResource(R.drawable.list_tab);
                imgFacebook.setImageResource(R.drawable.facebook_tab_active);
                imgTwitter.setImageResource(R.drawable.twitter_tab);
                imgLinkedin.setImageResource(R.drawable.linkdin_tab);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if(!currentList.equals("facebook")){
                            currentList="facebook";
                            addFragment(R.id.lnrFragment, socialFacebookFragment);
                        }
                    }
                }, 1000);
            }
        });
        imgTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerText.setText(getString(R.string.intafy_twitter_social_stream));
                imgAll.setImageResource(R.drawable.list_tab);
                imgFacebook.setImageResource(R.drawable.facebook_tab);
                imgTwitter.setImageResource(R.drawable.twitter_tab_active);
                imgLinkedin.setImageResource(R.drawable.linkdin_tab);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if(!currentList.equals("twitter")){
                            currentList="twitter";
                            addFragment(R.id.lnrFragment, socialTwitterFragment);
                        }
                    }
                }, 1000);
            }
        });
        imgLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerText.setText(getString(R.string.intafy_social_stream));
                imgAll.setImageResource(R.drawable.list_tab);
                imgFacebook.setImageResource(R.drawable.facebook_tab);
                imgTwitter.setImageResource(R.drawable.twitter_tab);
                imgLinkedin.setImageResource(R.drawable.linkdin_tab_active);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if(!currentList.equals("linkedin")){
                            currentList="linkedin";
                            addFragment(R.id.lnrFragment, socialAllFragment);
                        }
                    }
                }, 1000);
            }
        });

    }

}
