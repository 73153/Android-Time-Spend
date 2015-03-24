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
public class IjoomerIntafyInviteUserForEventActivity extends IjoomerIntafyMaster {

    private IjoomerIntafyInviteUserForEventListFragment intafyInviteUserListFragment;
    private IjoomerTextView headerLeftText;
    private IjoomerTextView headerRightText;
    private LinearLayout lnrHeaderLeft;
    private LinearLayout lnrHeaderRight;
    private IjoomerTextView headerText;
    private String IN_EVENT_ID;
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
        return 0;
    }

    @Override
    public void initComponents() {
        headerLeftText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst);
        headerRightText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtLast);
        lnrHeaderLeft = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderLeft);
        lnrHeaderRight = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderRight);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
    }

    @Override
    public void prepareViews() {
        IN_EVENT_ID = getIntent().getStringExtra("IN_EVENT_ID")!=null?getIntent().getStringExtra("IN_EVENT_ID"):"0";
        intafyInviteUserListFragment = new IjoomerIntafyInviteUserForEventListFragment((HashMap<String,String>)getIntent().getSerializableExtra("IN_SELECTED_USER"),(HashMap<String,String>)getIntent().getSerializableExtra("IN_NEW_SELECTED_CIRCLE"),IN_EVENT_ID);
        addFragment(R.id.lnrFragment, intafyInviteUserListFragment);

        headerText.setText(getString(R.string.intafy_select_users));
        headerRightText.setText(getString(R.string.intafy_done));
        headerLeftText.setText(getString(R.string.intafy_cancel));
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
                        if (intafyInviteUserListFragment.getInvitedUsers().size() > 0) {
                            Intent intent = new Intent();
                            intent.putExtra("IN_SELECTED_USER", intafyInviteUserListFragment.getInvitedUsers());
                            setResult(RESULT_OK, intent);
                            finish();
                        }else{
                            Intent intent = new Intent();
                            intent.putExtra("IN_SELECTED_USER", new HashMap<String,String>());
                            setResult(RESULT_OK, intent);
                            finish();
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
    }
}
