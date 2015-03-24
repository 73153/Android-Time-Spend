package com.ijoomer.components.intafy;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.intafy.src.R;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafyInviteCircleActivity extends IjoomerIntafyMaster {

    private IjoomerIntafyInviteCircleTitleListFragment circleTitleListFragment;
    private IjoomerIntafyInviteCircleDateListFragment circleDateListFragment;
    private IjoomerTextView headerLeftText;
    private IjoomerTextView headerRightText;
    private IjoomerTextView headerText;
    private IjoomerTextView footerLeftText;
    private IjoomerTextView footerRightText;
    private LinearLayout lnrHeaderLeft;
    private LinearLayout lnrHeaderRight;
    private LinearLayout lnrFooterLeft;
    private LinearLayout lnrFooterRight;
    private String IN_EVENT_ID;
    private String currentList;

    /**
     * Overrides methods
     */

    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_directory_list;
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
        headerLeftText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst);
        headerRightText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtLast);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        footerLeftText = (IjoomerTextView) getFooterView().findViewById(R.id.txtFirst);
        footerRightText = (IjoomerTextView) getFooterView().findViewById(R.id.txtLast);
        lnrHeaderLeft = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderLeft);
        lnrHeaderRight = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderRight);
        lnrFooterLeft = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterLeft);
        lnrFooterRight = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterRight);
    }

    @Override
    public void prepareViews() {
        IN_EVENT_ID = getIntent().getStringExtra("IN_EVENT_ID")!=null?getIntent().getStringExtra("IN_EVENT_ID"):"0";
        circleTitleListFragment = new IjoomerIntafyInviteCircleTitleListFragment((HashMap<String,String>)getIntent().getSerializableExtra("IN_SELECTED_CIRCLE"),(HashMap<String,String>)getIntent().getSerializableExtra("IN_NEW_SELECTED_CIRCLE"),IN_EVENT_ID);
        circleDateListFragment = new IjoomerIntafyInviteCircleDateListFragment((HashMap<String,String>)getIntent().getSerializableExtra("IN_SELECTED_CIRCLE"),(HashMap<String,String>)getIntent().getSerializableExtra("IN_NEW_SELECTED_CIRCLE"),IN_EVENT_ID);
        currentList="dateWise";
        addFragment(R.id.lnrFragment, circleDateListFragment);

        headerText.setText(getString(R.string.intafy_select_circles));
        headerRightText.setText(getString(R.string.intafy_done));
        headerLeftText.setText(getString(R.string.intafy_cancel));
        footerRightText.setText(getString(R.string.intafy_name));
        footerLeftText.setText(getString(R.string.intafy_recent));
        footerLeftText.setTextColor(getResources().getColor(R.color.blue));
    }

    @Override
    public void setActionListeners() {


        lnrHeaderRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerRightText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        headerRightText.setTextColor(getResources().getColor(R.color.white));
                        if(currentList.equals("dateWise")) {
                            if (circleDateListFragment.getInvitedUsers().size() > 0) {
                                Intent intent = new Intent();
                                intent.putExtra("IN_SELECTED_CIRCLE", circleDateListFragment.getInvitedUsers());
                                setResult(RESULT_OK, intent);
                                finish();
                            }else{
                                Intent intent = new Intent();
                                intent.putExtra("IN_SELECTED_CIRCLE", new HashMap<String,String>());
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }else{
                            if (circleTitleListFragment.getInvitedUsers().size() > 0) {
                                Intent intent = new Intent();
                                intent.putExtra("IN_SELECTED_CIRCLE", circleTitleListFragment.getInvitedUsers());
                                setResult(RESULT_OK, intent);
                                finish();
                            }else{
                                Intent intent = new Intent();
                                intent.putExtra("IN_SELECTED_CIRCLE", new HashMap<String,String>());
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }
                    }
                }, 1000);
            }
        });
        lnrHeaderLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerLeftText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        headerLeftText.setTextColor(getResources().getColor(R.color.white));
                        Intent intent = new Intent();
                        setResult(RESULT_CANCELED, intent);
                        finish();
                    }
                }, 1000);
            }
        });

        lnrFooterLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footerLeftText.setTextColor(getResources().getColor(R.color.blue));
                footerRightText.setTextColor(getResources().getColor(R.color.white));
                currentList="dateWise";
                addFragment(R.id.lnrFragment, circleDateListFragment);
            }
        });
        lnrFooterRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footerRightText.setTextColor(getResources().getColor(R.color.blue));
                footerLeftText.setTextColor(getResources().getColor(R.color.white));
                currentList="titleWise";
                addFragment(R.id.lnrFragment, circleTitleListFragment);
            }
        });
    }
}
