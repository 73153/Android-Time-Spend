package com.ijoomer.components.intafy;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
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
public class IjoomerIntafyCircleListActivity extends IjoomerIntafyMaster implements IntafyTagHolder {

    private IjoomerIntafyCircleDateListFragment circleDateListFragment;
    private IjoomerIntafyCircleTitleListFragment circleTitleListFragment;
    private IntafyCircleDataProvider circleDataProvider;
    private boolean IN_ISJOINGROUP;
    private String IN_USER_ID;
    private LinearLayout lnrHeaderLeft;
    private LinearLayout lnrHeaderRight;
    private IjoomerTextView headerLeftText;
    private IjoomerTextView headerRightText;
    private LinearLayout lnrFooterLeft;
    private LinearLayout lnrFooterRight;
    private IjoomerTextView footerLeftText;
    private IjoomerTextView footerRightText;
    private IjoomerTextView headerText;
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
        circleDataProvider = new IntafyCircleDataProvider(this);
        IN_ISJOINGROUP = getIntent().getBooleanExtra("IN_ISJOINGROUP",false);
        IN_USER_ID = getIntent().getStringExtra("IN_USER_ID")!=null ?getIntent().getStringExtra("IN_USER_ID"):"";
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

        circleDateListFragment = new IjoomerIntafyCircleDateListFragment(IN_USER_ID,false,IN_ISJOINGROUP,IN_ISJOINGROUP);
        circleTitleListFragment = new IjoomerIntafyCircleTitleListFragment(IN_USER_ID,false,IN_ISJOINGROUP,IN_ISJOINGROUP);
        addFragment(R.id.lnrFragment, circleDateListFragment);
        currentList="datewise";
        if(getSmartApplication().readSharedPreferences().getString(SP_CONNECTEDUSERTYPE,"").equalsIgnoreCase("admin")) {
            headerLeftText.setText(getString(R.string.intafy_manage));
        }else{
            headerLeftText.setText("");
        }

        if(IN_ISJOINGROUP) {
            headerRightText.setText(getString(R.string.intafy_join));
            headerText.setText(getString(R.string.intafy_circles_join));
            headerLeftText.setText(getString(R.string.intafy_back));
        }else{
            headerRightText.setText(getString(R.string.intafy_delete));
            headerText.setText(getString(R.string.intafy_circles));
            headerLeftText.setText(getString(R.string.intafy_new));
        }
        footerRightText.setText(getString(R.string.intafy_name));
        footerLeftText.setText(getString(R.string.intafy_recent));
        footerLeftText.setTextColor(getResources().getColor(R.color.blue));
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
                        if (IN_ISJOINGROUP) {
                            finish();
                        } else {
                            if (headerLeftText.getText().toString().equals(getString(R.string.intafy_cancel))) {
                                headerLeftText.setText(getString(R.string.intafy_new));
                                headerText.setText(getString(R.string.intafy_circles));
                                if(currentList.equals("datewise")) {
                                    circleDateListFragment.isSelectGroupActive = false;
                                    circleDateListFragment.isDeleteGroup = false;
                                    circleDateListFragment.isJoinGroup = false;
                                    circleDateListFragment.update();
                                }else{
                                    circleTitleListFragment.isSelectGroupActive = false;
                                    circleTitleListFragment.isDeleteGroup = false;
                                    circleTitleListFragment.isJoinGroup = false;
                                    circleTitleListFragment.update();
                                }
                                getFooterView().setVisibility(View.VISIBLE);
                            } else {
                                loadNew(IjoomerIntafyCircleAddActivity.class, IjoomerIntafyCircleListActivity.this, false);
                            }
                        }
                    }
                }, 1000);
            }
        });
        lnrHeaderRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerRightText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        headerRightText.setTextColor(getResources().getColor(R.color.white));
                        if (IN_ISJOINGROUP) {
                            if(currentList.equals("datewise")) {
                                if (circleDateListFragment.isSelectGroupActive) {
                                    final String circleIds = circleDateListFragment.getSelectedCircleId();
                                    if (circleIds.trim().length() > 0) {
                                        final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                                        circleDataProvider.joinGroup(IN_USER_ID,circleIds, new WebCallListener() {
                                            @Override
                                            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                                headerRightText.setTextColor(getResources().getColor(R.color.white));
                                                if (responseCode == 200) {
                                                    circleDateListFragment.update();
                                                } else {
                                                    responseErrorMessageHandler(responseCode);
                                                }
                                            }

                                            @Override
                                            public void onProgressUpdate(int progressCount) {
                                                seekBar.setProgress(progressCount);
                                            }
                                        });
                                    }
                                }else{
                                    circleDateListFragment.isSelectGroupActive = true;
                                    circleDateListFragment.update();
                                }
                            }else{
                                if (circleTitleListFragment.isSelectGroupActive) {
                                    final String circleIds = circleTitleListFragment.getSelectedCircleId();
                                    if (circleIds.trim().length() > 0) {
                                        final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                                        circleDataProvider.joinGroup(IN_USER_ID,circleIds, new WebCallListener() {
                                            @Override
                                            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                                headerRightText.setTextColor(getResources().getColor(R.color.white));
                                                if (responseCode == 200) {
                                                    circleTitleListFragment.update();
                                                } else {
                                                    responseErrorMessageHandler(responseCode);
                                                }
                                            }

                                            @Override
                                            public void onProgressUpdate(int progressCount) {
                                                seekBar.setProgress(progressCount);
                                            }
                                        });
                                    }
                                }else{
                                    circleTitleListFragment.isSelectGroupActive = true;
                                    circleTitleListFragment.update();
                                }
                            }
                        } else {
                            if(currentList.equals("datewise")) {
                                circleDateListFragment.isDeleteGroup = true;
                                if (circleDateListFragment.isSelectGroupActive) {
                                    final String circleIds = circleDateListFragment.getSelectedCircleId();
                                    if (circleIds.trim().length() > 0) {
                                        IjoomerUtilities.getCustomConfirmDialog(getString(R.string.intafy_circles), getString(R.string.intafy_are_you_sure_remove_circle), getString(R.string.intafy_confirm), getString(R.string.intafy_cancel), new CustomAlertMagnatic() {
                                            @Override
                                            public void PositiveMethod() {
                                                final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                                                circleDataProvider.removeCircle("CircleDateWise",circleIds, new WebCallListener() {
                                                    @Override
                                                    public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                                        headerRightText.setTextColor(getResources().getColor(R.color.white));
                                                        if (responseCode == 200) {
                                                            circleDateListFragment.update();
                                                        } else {
                                                            responseErrorMessageHandler(responseCode);
                                                        }
                                                    }

                                                    @Override
                                                    public void onProgressUpdate(int progressCount) {
                                                        seekBar.setProgress(progressCount);
                                                    }
                                                });

                                            }

                                            @Override
                                            public void NegativeMethod() {
                                            }
                                        });
                                    }
                                }else{
                                    circleDateListFragment.isSelectGroupActive = true;
                                    circleDateListFragment.update();
                                }
                            }else{
                                circleTitleListFragment.isDeleteGroup=true;
                                if (circleTitleListFragment.isSelectGroupActive) {
                                    final String circleIds = circleTitleListFragment.getSelectedCircleId();
                                    if (circleIds.trim().length() > 0) {
                                        IjoomerUtilities.getCustomConfirmDialog(getString(R.string.intafy_circles), getString(R.string.intafy_are_you_sure_remove_circle), getString(R.string.intafy_confirm), getString(R.string.intafy_cancel), new CustomAlertMagnatic() {
                                            @Override
                                            public void PositiveMethod() {
                                                final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                                                circleDataProvider.removeCircle("CircleTitleWise",circleIds, new WebCallListener() {
                                                    @Override
                                                    public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                                        headerRightText.setTextColor(getResources().getColor(R.color.white));
                                                        if (responseCode == 200) {
                                                            circleTitleListFragment.update();
                                                        } else {
                                                            responseErrorMessageHandler(responseCode);
                                                        }
                                                    }

                                                    @Override
                                                    public void onProgressUpdate(int progressCount) {
                                                        seekBar.setProgress(progressCount);
                                                    }
                                                });

                                            }

                                            @Override
                                            public void NegativeMethod() {
                                            }
                                        });
                                    }
                                }else{
                                    circleTitleListFragment.isSelectGroupActive = true;
                                    circleTitleListFragment.update();
                                }
                            }
                            headerLeftText.setText(getString(R.string.intafy_cancel));
                            headerText.setText(getString(R.string.intafy_delete_circle));
                        }
                        getFooterView().setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });
        lnrFooterLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footerLeftText.setTextColor(getResources().getColor(R.color.blue));
                footerRightText.setTextColor(getResources().getColor(R.color.white));
                currentList="datewise";
                addFragment(R.id.lnrFragment, circleDateListFragment);
            }
        });
        lnrFooterRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footerRightText.setTextColor(getResources().getColor(R.color.blue));
                footerLeftText.setTextColor(getResources().getColor(R.color.white));
                currentList="titlewise";
                addFragment(R.id.lnrFragment, circleTitleListFragment);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(IjoomerApplicationConfiguration.isReloadRequired()){
            if(currentList.equals("datewise")) {
                circleDateListFragment.update();
            }else{
                circleTitleListFragment.update();
            }
            IjoomerApplicationConfiguration.setReloadRequired(false);
        }
    }

    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_circle), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }

}
