package com.ijoomer.components.intafy;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.customviews.IjoomerEditText;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyDirectoriesDataProvider;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.SmartApplication;

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
public class IjoomerIntafyDirectoryListActivity extends IjoomerIntafyMaster implements IntafyTagHolder {

    private LinearLayout lnrSearch;
    private IjoomerEditText edtSearch;
    private ImageView imgSearch;
    private IjoomerIntafyDirectoryListFragment intafyDirectoryListFragment;
    private IjoomerIntafyDirectorySearchListFragment intafyDirectorySearchListFragment;
    private IjoomerIntafyMapFragment ijoomerIntafyMapFragment;
    private String currentList=LIST;
    private boolean isMapShown;
    private IntafyDirectoriesDataProvider directoriesDataProvider;
    private IjoomerTextView headerLeftText;
    private IjoomerTextView headerRightText;
    private IjoomerTextView footerLeftText;
    private IjoomerTextView footerRightText;
    private IjoomerTextView headerText;
    private LinearLayout lnrHeaderLeft;
    private LinearLayout lnrHeaderRight;
    private LinearLayout lnrFooterLeft;
    private LinearLayout lnrFooterRight;

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
        lnrSearch =(LinearLayout) findViewById(R.id.lnrSearch);
        edtSearch = (IjoomerEditText) findViewById(R.id.edtSearch);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        directoriesDataProvider = new IntafyDirectoriesDataProvider(this);
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

        intafyDirectoryListFragment = new IjoomerIntafyDirectoryListFragment();
        addFragment(R.id.lnrFragment, intafyDirectoryListFragment);
        if(getSmartApplication().readSharedPreferences().getString(SP_CONNECTEDUSERTYPE,"").equalsIgnoreCase("admin")) {
            headerLeftText.setText(getString(R.string.intafy_manage));
        }else{
            headerLeftText.setText("");
        }

        headerText.setText(getString(R.string.intafy_directory));
        headerRightText.setText(getString(R.string.intafy_search));
        footerRightText.setText(getString(R.string.intafy_map));
        footerLeftText.setText(getString(R.string.intafy_list));
        footerLeftText.setTextColor(getResources().getColor(R.color.blue));
    }

    @Override
    public void setActionListeners() {

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSearch.setImageResource(getIcon(SEARCHICON, true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgSearch.setImageResource(getIcon(SEARCHICON, false));
                        if (edtSearch.getText().toString().trim().length() > 0) {
                            if(isMapShown){
                                String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
                                String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID,"0");
                                ArrayList<HashMap<String,String>> searchData = directoriesDataProvider.searchMember(edtSearch.getText().toString(),networkId,connectedUserId);
                                if(searchData!=null && searchData.size()>0){
                                    ijoomerIntafyMapFragment.moveToLocation(searchData.get(0).get(LATITUDE),searchData.get(0).get(LONGITUDE));
                                }else{
                                    responseErrorMessageHandler(203);
                                }
                            }else{
                                currentList = SEARCHLIST;
                                intafyDirectorySearchListFragment = new IjoomerIntafyDirectorySearchListFragment(edtSearch.getText().toString());
                                addFragment(R.id.lnrFragment, intafyDirectorySearchListFragment);
                            }
                        } else {
                            edtSearch.setError(getString(R.string.intafy_validation_value_required));
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
                        if (headerRightText.getText().toString().equals(getString(R.string.intafy_search)) || headerRightText.getText().toString().equals(getString(R.string.intafy_cancel))) {
                            if(headerRightText.getText().toString().equals(getString(R.string.intafy_search))){
                                lnrSearch.setVisibility(View.VISIBLE);
                                headerRightText.setText(getString(R.string.intafy_cancel));
                            }else{
                                headerRightText.setText(getString(R.string.intafy_search));
                                lnrSearch.setVisibility(View.GONE);
                                if(currentList.equals(SEARCHLIST)){
                                    currentList=LIST;
                                    addFragment(R.id.lnrFragment, intafyDirectoryListFragment);
                                }
                            }
                        } else {
                            if(currentList.equals(SEARCHLIST)){
                                final String blockUserId = intafyDirectorySearchListFragment.getBlockUserId();
                                if (blockUserId.trim().length() > 0) {
                                    final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                                    directoriesDataProvider.blockUser(blockUserId, new WebCallListener() {
                                        @Override
                                        public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                            if (responseCode == 200) {
                                                String[] userIdArray = blockUserId.trim().split(",");
                                                String queryUserId = "";
                                                for (String userId : userIdArray) {
                                                    queryUserId = " "+USER_ID+"='" + userId + "' or ";
                                                }
                                                String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID, "0");
                                                String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID, "0");
                                                directoriesDataProvider.updateBlockUser(queryUserId.substring(0, queryUserId.length() - 4), networkId, connectedUserId);
                                                intafyDirectorySearchListFragment.update();
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
                                final String blockUserId = intafyDirectoryListFragment.getBlockUserId();
                                if (blockUserId.trim().length() > 0) {
                                    final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                                    directoriesDataProvider.blockUser(blockUserId.trim(), new WebCallListener() {
                                        @Override
                                        public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                            if (responseCode == 200) {
                                                String[] userIdArray = blockUserId.trim().split(",");
                                                String queryUserId = "";
                                                for (String userId : userIdArray) {
                                                    queryUserId = " "+USER_ID+"='" + userId + "' or ";
                                                }
                                                String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID, "0");
                                                String networkId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_NETWORKID, "0");
                                                directoriesDataProvider.updateBlockUser(queryUserId.substring(0, queryUserId.length() - 4), networkId, connectedUserId);
                                                intafyDirectoryListFragment.update();
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
                        headerLeftText.setTextColor(getResources().getColor(R.color.white));;
                        if(currentList.equals(SEARCHLIST)){
                            if(headerLeftText.getText().toString().equals(getString(R.string.intafy_manage))){
                                headerLeftText.setText(getString(R.string.intafy_cancel));
                                headerRightText.setText(getString(R.string.intafy_block));
                                intafyDirectorySearchListFragment.enableBlock();
                                getFooterView().setVisibility(View.GONE);
                            }else{
                                headerLeftText.setText(getString(R.string.intafy_manage));
                                headerRightText.setText(getString(R.string.intafy_search));
                                intafyDirectorySearchListFragment.disableBlock();
                                getFooterView().setVisibility(View.VISIBLE);
                            }
                        }else{
                            if(headerLeftText.getText().toString().equals(getString(R.string.intafy_manage))){
                                headerLeftText.setText(getString(R.string.intafy_cancel));
                                headerRightText.setText(getString(R.string.intafy_block));
                                intafyDirectoryListFragment.enableBlock();
                                getFooterView().setVisibility(View.GONE);
                            }else{
                                headerLeftText.setText(getString(R.string.intafy_manage));
                                headerRightText.setText(getString(R.string.intafy_search));
                                intafyDirectoryListFragment.disableBlock();
                                getFooterView().setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }, 1000);
            }
        });
        lnrFooterLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footerLeftText.setTextColor(getResources().getColor(R.color.blue));
                footerRightText.setTextColor(getResources().getColor(R.color.white));
                headerText.setText(getString(R.string.intafy_directory));
                if(getSmartApplication().readSharedPreferences().getString(SP_CONNECTEDUSERTYPE,"").equalsIgnoreCase("admin")) {
                    headerLeftText.setText(getString(R.string.intafy_manage));
                }else{
                    headerLeftText.setText("");
                }
                isMapShown=false;
                if(currentList.equals(SEARCHLIST)){
                    addFragment(R.id.lnrFragment, intafyDirectorySearchListFragment);
                }else {
                    addFragment(R.id.lnrFragment, intafyDirectoryListFragment);
                }
            }
        });
        lnrFooterRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footerLeftText.setTextColor(getResources().getColor(R.color.white));
                footerRightText.setTextColor(getResources().getColor(R.color.blue));
                headerText.setText(getString(R.string.intafy_directory_map));
                headerLeftText.setText("");
                isMapShown=true;
                if(currentList.equals(SEARCHLIST)){
                    ijoomerIntafyMapFragment = new IjoomerIntafyMapFragment(intafyDirectorySearchListFragment.searchData);
                    addFragment(R.id.lnrFragment, ijoomerIntafyMapFragment);
                }else{
                    ijoomerIntafyMapFragment = new IjoomerIntafyMapFragment(intafyDirectoryListFragment.memberData);
                    addFragment(R.id.lnrFragment, ijoomerIntafyMapFragment);
                }

            }
        });

    }

    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_directory), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }



}
