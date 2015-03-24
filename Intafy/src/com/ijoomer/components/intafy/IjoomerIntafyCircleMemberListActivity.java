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
public class IjoomerIntafyCircleMemberListActivity extends IjoomerIntafyMaster implements IntafyTagHolder {

    private IjoomerIntafyCircleMemberListFragment circleMemberListFragment;
    private String IN_CIRCLE_ID;
    private String IN_CIRCLE_ISEDIT;
    private String IN_TABLENAME;

    private LinearLayout lnrHeaderLeft;
    private LinearLayout lnrHeaderRight;
    private IjoomerTextView headerLeftText;
    private IjoomerTextView headerRightText;
    private IjoomerTextView headerText;
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
        return 0;
    }

    @Override
    public void initComponents() {
        headerLeftText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        headerRightText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtLast);
        lnrHeaderLeft = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderLeft);
        lnrHeaderRight = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderRight);
    }

    @Override
    public void prepareViews() {
        IN_CIRCLE_ID = getIntent().getStringExtra("IN_CIRCLE_ID")!=null ? getIntent().getStringExtra("IN_CIRCLE_ID"):"0";
        IN_CIRCLE_ISEDIT = getIntent().getStringExtra("IN_CIRCLE_ISEDIT")!=null ? getIntent().getStringExtra("IN_CIRCLE_ISEDIT"):"0";
        IN_TABLENAME = getIntent().getStringExtra("IN_TABLENAME")!=null ? getIntent().getStringExtra("IN_TABLENAME"):"";
        circleMemberListFragment = new IjoomerIntafyCircleMemberListFragment(IN_CIRCLE_ID);
        addFragment(R.id.lnrFragment, circleMemberListFragment);
        headerLeftText.setText(getString(R.string.intafy_cancel));
        if(IN_CIRCLE_ISEDIT.equals("1")){
            headerRightText.setText(getString(R.string.intafy_edit));
        }else{
            headerRightText.setText("");
        }
        headerText.setText(getString(R.string.intafy_users));
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
                        headerRightText.setTextColor(getResources().getColor(R.color.white));
                        try{
                            loadNew(IjoomerIntafyCircleAddActivity.class,IjoomerIntafyCircleMemberListActivity.this,false,"IN_CIRCLE_ID",IN_CIRCLE_ID,"IN_TABLENAME",IN_TABLENAME);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }, 1000);
            }
        });
        lnrHeaderLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    @Override
    protected void onResume() {
        super.onResume();
        if(IjoomerApplicationConfiguration.isReloadRequired()){
            circleMemberListFragment.update();
        }
    }
}
