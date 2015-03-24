package com.ijoomer.components.intafy;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.intafy.src.R;
import com.smart.framework.SmartFragment;

import org.json.JSONArray;

/**
 * This Fragment Contains All Method Related To JomAlbumAllFragment.
 *
 * @author tasol
 *
 */
@SuppressLint("ValidFragment")
public class IjoomerIntafyProfileOtherFragment extends SmartFragment implements IntafyTagHolder {


    private JSONArray data;

    private IjoomerTextView txtField1Label;
    private IjoomerTextView txtField1Value;
    private IjoomerTextView txtField2Label;
    private IjoomerTextView txtField2Value;
    private LinearLayout lnrField1;
    private LinearLayout lnrField2;
    public IjoomerIntafyProfileOtherFragment(JSONArray data) {
        this.data=data;
    }

    /**
     * Overrides method
     */

    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_profile_other_details_fragment;
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public void initComponents(View currentView) {

        lnrField1 = (LinearLayout) currentView.findViewById(R.id.lnrField1);
        lnrField2 = (LinearLayout) currentView.findViewById(R.id.lnrField2);
        txtField1Label = (IjoomerTextView) currentView.findViewById(R.id.txtField1Label);
        txtField1Value = (IjoomerTextView) currentView.findViewById(R.id.txtField1Value);
        txtField2Label = (IjoomerTextView) currentView.findViewById(R.id.txtField2Label);
        txtField2Value = (IjoomerTextView) currentView.findViewById(R.id.txtField2Value);


    }

    @Override
    public void prepareViews(View currentView) {
        setProfileField();
    }

    @Override
    public void setActionListeners(View currentView) {
    }

    /**
     * Class methods
     */

    private void setProfileField(){

        try{
            if(data.length()>0){
                txtField1Label.setText(data.getJSONObject(0).getString(CAPTION));
                if(data.getJSONObject(0).getString(CAPTION).toLowerCase().contains("date")){
                    txtField1Value.setText(IjoomerUtilities.convertDateStringFormat(data.getJSONObject(0).getString(VALUE), "yyyy-MM-dd", IjoomerApplicationConfiguration.dateFormat));
                }else{
                    txtField1Value.setText(data.getJSONObject(0).getString(VALUE));
                }
                lnrField1.setVisibility(View.VISIBLE);
            }
            if(data.length()>1){
                txtField2Label.setText(data.getJSONObject(1).getString(CAPTION));
                if(data.getJSONObject(1).getString(CAPTION).toLowerCase().contains("date")){
                    txtField2Value.setText(IjoomerUtilities.convertDateStringFormat(data.getJSONObject(1).getString(VALUE), "yyyy-MM-dd", IjoomerApplicationConfiguration.dateFormat));
                }else{
                    txtField2Value.setText(data.getJSONObject(1).getString(VALUE));
                }
                lnrField2.setVisibility(View.VISIBLE);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }


    }

}
