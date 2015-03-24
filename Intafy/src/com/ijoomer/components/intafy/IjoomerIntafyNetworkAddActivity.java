package com.ijoomer.components.intafy;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.customviews.IjoomerEditText;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyNetworkDataProvider;
import com.ijoomer.weservice.WebCallListener;
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
public class IjoomerIntafyNetworkAddActivity extends IjoomerIntafyMaster {

    private IjoomerEditText edtFirstName;
    private IjoomerEditText edtLastName;
    private IjoomerEditText edtEmail;
    private IjoomerEditText edtAuthorisationCode;
    private ImageView imgAddNetwork;
    private IjoomerTextView headerText;

    private IntafyNetworkDataProvider intafyNetworkDataProvider;
    /**
     * Overrides methods
     */


    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_network_add;
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

        edtFirstName = (IjoomerEditText)findViewById(R.id.edtFirstName);
        edtLastName = (IjoomerEditText)findViewById(R.id.edtLastName);
        edtEmail = (IjoomerEditText)findViewById(R.id.edtEmail);
        edtAuthorisationCode = (IjoomerEditText)findViewById(R.id.edtAuthorisationCode);
        imgAddNetwork = (ImageView)findViewById(R.id.imgAddNetwork);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);

        intafyNetworkDataProvider = new IntafyNetworkDataProvider(this);


    }

    @Override
    public void prepareViews() {
        headerText.setText(getString(R.string.intafy_add_network));
    }

    @Override
    public void setActionListeners() {

        imgAddNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                imgAddNetwork.setImageResource(getIcon(ADDICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgAddNetwork.setImageResource(getIcon(ADDICON,false));
                        boolean isValid =true;
                        if(edtFirstName.getText().toString().length()<=0){
                            edtFirstName.setError(getString(R.string.intafy_validation_value_required));
                            isValid=false;
                        }
                        if(edtLastName.getText().toString().length()<=0){
                            edtLastName.setError(getString(R.string.intafy_validation_value_required));
                            isValid=false;
                        }
                        if(edtEmail.getText().toString().length()<=0){
                            edtEmail.setError(getString(R.string.intafy_validation_value_required));
                            isValid=false;
                        }
                        if(!IjoomerUtilities.emailValidator(edtEmail.getText().toString())){
                            edtEmail.setError(getString(R.string.intafy_validation_invalid_email));
                            isValid=false;
                        }
                        if(edtAuthorisationCode.getText().toString().length()<=0){
                            edtAuthorisationCode.setError(getString(R.string.intafy_validation_value_required));
                            isValid=false;
                        }

                        if(isValid){
                            final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                            final String networkId = getSmartApplication().readSharedPreferences().getString(SP_NETWORKID,"0");
                            getSmartApplication().writeSharedPreferences(SP_NETWORKID,"0");
                            intafyNetworkDataProvider.addNetwork(edtFirstName.getText().toString(),edtLastName.getText().toString(),edtEmail.getText().toString(),edtAuthorisationCode.getText().toString(),new WebCallListener() {
                                @Override
                                public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                    if(responseCode==200){
                                        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_networks), getString(R.string.intafy_networks_added_successfully),
                                                getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                                    @Override
                                                    public void NeutralMethod() {
                                                        IjoomerApplicationConfiguration.setReloadRequired(true);
                                                        finish();
                                                    }
                                                });

                                    }else{
                                        getSmartApplication().writeSharedPreferences(SP_NETWORKID,networkId);
                                        responseErrorMessageHandler(responseCode);
                                    }

                                }

                                @Override
                                public void onProgressUpdate(int progressCount) {
                                    proSeekBar.setProgress(progressCount);
                                }
                            });
                        }
                    }
                }, 1000);
            }
        });
    }

    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_network), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
                getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }

}
