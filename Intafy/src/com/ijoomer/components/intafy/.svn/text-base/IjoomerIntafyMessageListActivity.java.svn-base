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
import com.ijoomer.library.intafy.IntafyMessageDataProvider;
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
public class IjoomerIntafyMessageListActivity extends IjoomerIntafyMaster implements IntafyTagHolder {

    private IjoomerIntafyMessageListFragment messageListFragment;
    private String currentList;
    private IjoomerTextView headerLeftText;
    private IjoomerTextView headerRightText;
    private IjoomerTextView footerLeftText;
    private IjoomerTextView footerRightText;
    private LinearLayout lnrHeaderLeft;
    private LinearLayout lnrHeaderRight;
    private LinearLayout lnrFooterLeft;
    private LinearLayout lnrFooterRight;
    private IjoomerTextView headerText;
    private IntafyMessageDataProvider messageDataProvider;

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
        headerLeftText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst);
        headerRightText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtLast);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        footerLeftText = (IjoomerTextView) getFooterView().findViewById(R.id.txtFirst);
        footerRightText = (IjoomerTextView) getFooterView().findViewById(R.id.txtLast);
        lnrHeaderLeft = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderLeft);
        lnrHeaderRight = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderRight);
        lnrFooterLeft = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterLeft);
        lnrFooterRight = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterRight);
        messageDataProvider = new IntafyMessageDataProvider(this);

    }

    @Override
    public void prepareViews() {

        messageListFragment = new IjoomerIntafyMessageListFragment();
        addFragment(R.id.lnrFragment, messageListFragment);
        currentList=DATE;
        headerText.setText(getString(R.string.intafy_messages));
        headerLeftText.setText(getString(R.string.intafy_new));
        headerRightText.setText(getString(R.string.intafy_delete));
        footerRightText.setText(getString(R.string.intafy_name));
        footerLeftText.setText(getString(R.string.intafy_recent));
        footerLeftText.setTextColor(getResources().getColor(R.color.blue));
    }

    @Override
    public void setActionListeners() {
        lnrFooterLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerLeftText.setTextColor(getResources().getColor(R.color.blue));
                footerRightText.setTextColor(getResources().getColor(R.color.white));
                currentList = DATE;
                messageListFragment.update(currentList,true);
            }
        });
        lnrFooterRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerRightText.setTextColor(getResources().getColor(R.color.blue));
                footerLeftText.setTextColor(getResources().getColor(R.color.white));
                currentList = USER_NAME;
                messageListFragment.update(currentList,true);
            }
        });

        lnrHeaderRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerRightText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        headerRightText.setTextColor(getResources().getColor(R.color.white));
                        if(messageListFragment.isDeleteActive){
                            String selectedMessageId = messageListFragment.getSelectedMessageId();
                            if(selectedMessageId.trim().length()>0) {
                                IjoomerUtilities.getCustomConfirmDialog(getString(R.string.intafy_messages), getString(R.string.intafy_are_you_sure_remove_message_thread), getString(R.string.intafy_confirm), getString(R.string.intafy_cancel), new CustomAlertMagnatic() {
                                    @Override
                                    public void PositiveMethod() {
                                        final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                                        messageDataProvider.removeMessage(messageListFragment.getSelectedMessageId(), true, new WebCallListener() {
                                            @Override
                                            public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                                if (responseCode == 200) {
                                                    messageListFragment.update(currentList,false);
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
                        }else {
                            messageListFragment.isDeleteActive = true;
                            messageListFragment.update(currentList,false);
                            getFooterView().setVisibility(View.GONE);
                            headerLeftText.setText(getString(R.string.intafy_cancel));
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
                        if (headerLeftText.getText().toString().equals(getString(R.string.intafy_cancel))) {
                            headerLeftText.setText(getString(R.string.intafy_new));
                            messageListFragment.isDeleteActive = false;
                            messageListFragment.update(currentList,false);
                            getFooterView().setVisibility(View.VISIBLE);
                        } else {
                            loadNew(IjoomerIntafyMessageAddActivity.class, IjoomerIntafyMessageListActivity.this, false);
                        }
                        headerLeftText.setTextColor(getResources().getColor(R.color.white));
                    }
                }, 1000);
            }
        });

    }

    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_messages), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }

    @Override
    protected void onResume() {
        if(IjoomerApplicationConfiguration.isReloadRequired()){
            if(messageListFragment !=null){
                messageListFragment.update(currentList,false);
            }
            IjoomerApplicationConfiguration.setReloadRequired(false);
        }
        super.onResume();
    }
}
