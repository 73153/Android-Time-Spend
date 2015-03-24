package com.ijoomer.components.intafy;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.intafy.src.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafyEventListActivity extends IjoomerIntafyMaster implements IntafyTagHolder {

    private IjoomerIntafyEventListFragment intafyEventListFragment;
    private String currentList;
    private IjoomerTextView headerRightText;
    private IjoomerTextView footerLeftText;
    private IjoomerTextView footerRightText;
    private IjoomerTextView headerText;
    private LinearLayout lnrHeaderRight;
    private LinearLayout lnrFooterLeft;
    private LinearLayout lnrFooterRight;

    /**
     * Overrides methods
     */


    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_event_list;
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
        return R.layout.ijoomer_intafy_footer;
    }

    @Override
    public void initComponents() {
        headerRightText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtLast);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        footerLeftText = (IjoomerTextView) getFooterView().findViewById(R.id.txtFirst);
        footerRightText = (IjoomerTextView) getFooterView().findViewById(R.id.txtLast);
        lnrHeaderRight = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderRight);
        lnrFooterLeft = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterLeft);
        lnrFooterRight = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterRight);
    }

    @Override
    public void prepareViews() {

        intafyEventListFragment = new IjoomerIntafyEventListFragment();
        addFragment(R.id.lnrFragment, intafyEventListFragment);
        currentList=MY;
        headerText.setText(getString(R.string.intafy_events));
        headerRightText.setText(getString(R.string.intafy_new));
        footerRightText.setText(getString(R.string.intafy_all));
        footerLeftText.setText(getString(R.string.intafy_my));
        footerLeftText.setTextColor(getResources().getColor(R.color.blue));
    }

    @Override
    public void setActionListeners() {
        lnrHeaderRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerRightText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            headerRightText.setTextColor(getResources().getColor(R.color.white));
                            loadNew(IjoomerIntafyEventAddFirstStepsActivity.class, IjoomerIntafyEventListActivity.this, false, "IN_EVENT_ID", "0");
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }, 1000);
            }
        });
        lnrFooterLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerLeftText.setTextColor(getResources().getColor(R.color.blue));
                footerRightText.setTextColor(getResources().getColor(R.color.white));
                currentList = MY;
                intafyEventListFragment.update(currentList,true);
            }
        });
        lnrFooterRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerRightText.setTextColor(getResources().getColor(R.color.blue));
                footerLeftText.setTextColor(getResources().getColor(R.color.white));
                currentList = ALL;
                intafyEventListFragment.update(currentList,true);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(IjoomerApplicationConfiguration.isReloadRequired()){
            if(intafyEventListFragment!=null){
                intafyEventListFragment.update(currentList,false);
            }
            IjoomerApplicationConfiguration.setReloadRequired(false);
        }

    }
}
