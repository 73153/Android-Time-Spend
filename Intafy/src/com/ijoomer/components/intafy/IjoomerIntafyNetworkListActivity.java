package com.ijoomer.components.intafy;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyNetworkDataProvider;
import com.ijoomer.oauth.IjoomerOauth;
import com.smart.framework.CustomAlertMagnatic;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.SmartApplication;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafyNetworkListActivity extends IjoomerIntafyMaster {

    private IjoomerIntafyNetwoekListFragment intafyNetwoekListFragment;
    private IntafyNetworkDataProvider networkDataProvider;
    private IjoomerTextView headerLeftText;
    private IjoomerTextView headerRightText;
    private IjoomerTextView footerLeftText;
    private IjoomerTextView footerRightText;
    private IjoomerTextView headerText;
    private LinearLayout lnrHeaderLeft;
    private LinearLayout lnrHeaderRight;
    private LinearLayout lnrFooterLeft;
    /**
     * Overrides methods
     */


    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_network_list;
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
        networkDataProvider = new IntafyNetworkDataProvider(this);
    }

    @Override
    public void prepareViews() {

        intafyNetwoekListFragment = new IjoomerIntafyNetwoekListFragment();
        addFragment(R.id.lnrFragment,intafyNetwoekListFragment);
        headerLeftText.setText(getString(R.string.intafy_add));
        headerRightText.setText(getString(R.string.intafy_leave));
        footerLeftText.setText(getString(R.string.intafy_settings));
        headerText.setText(getString(R.string.intafy_networks));
        footerRightText.setText(getString(R.string.intafy_networks));
        footerRightText.setTextColor(getResources().getColor(R.color.blue));
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
                        if(headerLeftText.getText().toString().equals(getString(R.string.intafy_back))){
                            intafyNetwoekListFragment.isRemoveActive=false;
                            headerLeftText.setText(getString(R.string.intafy_new));
                            getFooterView().setVisibility(View.VISIBLE);
                            intafyNetwoekListFragment.update(false);
                        }else{
                            loadNew(IjoomerIntafyNetworkAddActivity.class,IjoomerIntafyNetworkListActivity.this,false);
                        }
                        headerLeftText.setTextColor(getResources().getColor(R.color.white));
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
                        if(intafyNetwoekListFragment.isRemoveActive){
                            if(intafyNetwoekListFragment.selectedNetworMap.size()>0) {
                                IjoomerUtilities.getCustomConfirmDialog(getString(R.string.intafy_networks), getString(R.string.intafy_are_you_sure_remove_network), getString(R.string.intafy_confirm), getString(R.string.intafy_cancel), new CustomAlertMagnatic() {
                                    @Override
                                    public void PositiveMethod() {
                                        if (networkDataProvider.removeNetwork(intafyNetwoekListFragment.getSelectedNetworkId(), intafyNetwoekListFragment.getSelectedNetworkUserId())) {
                                            IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_networks), getString(R.string.intafy_networks_removed_successfully),
                                                    getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                                        @Override
                                                        public void NeutralMethod() {
                                                            IjoomerApplicationConfiguration.setReloadRequired(true);
                                                            onResume();
                                                        }
                                                    }
                                            );
                                        }
                                    }

                                    @Override
                                    public void NegativeMethod() {
                                    }
                                });
                            }
                        }else{
                            headerLeftText.setText(getString(R.string.intafy_back));
                            intafyNetwoekListFragment.isRemoveActive=true;
                            intafyNetwoekListFragment.update(false);
                            getFooterView().setVisibility(View.GONE);
                        }

                    }
                }, 1000);
            }
        });
        lnrFooterLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footerRightText.setTextColor(getResources().getColor(R.color.white));
                footerLeftText.setTextColor(getResources().getColor(R.color.blue));
                loadNew(IjoomerIntafySettingsActivity.class,IjoomerIntafyNetworkListActivity.this,true);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(IjoomerApplicationConfiguration.isReloadRequired()){
            intafyNetwoekListFragment.update(true);
            IjoomerOauth.getInstance(this).authenticateUser(this);
        }
    }
}
