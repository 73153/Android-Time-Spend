package com.ijoomer.components.intafy;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.ijoomer.common.classes.IjoomerMapAddress;
import com.ijoomer.common.classes.IjoomerRegistrationMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.custom.interfaces.CustomClickListner;
import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.customviews.IjoomerEditText;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyUserDataProvider;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertNeutral;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This Class Contains All Method Related To IjoomerRegistrationStep2Activity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafyEditProfileActivity extends IjoomerRegistrationMaster {

    private LinearLayout lnr_form;
    private IjoomerEditText editMap;
    private IjoomerButton btnBack;
    private IjoomerButton btnSubmit;
    private IjoomerTextView txtFirstName;
    private IjoomerTextView txtLastName;
    private IjoomerTextView txtStatus;
    private IjoomerTextView txtEmail;
    private IjoomerEditText edtFirstName;
    private IjoomerEditText edtLastName;
    private IjoomerEditText edtStatus;
    private IjoomerEditText edtEmail;


    private JSONArray IN_FIELD;

    private final String TYPE = "type";
    private final String TEXT = "text";
    private final String TEXTAREA = "textarea";
    private final String DATE = "date";
    private final String TIME = "time";
    private final String PHONE = "phone";
    private final String SELECT = "select";
    private final String MULTIPLESELECT = "multipleselect";
    private final String MAP = "map";
    private final String VALUE = "value";
    private final String OPTIONS = "options";
    private final String CAPTION = "caption";
    private final String REQUIRED = "required";
    final private int GET_ADDRESS_FROM_MAP = 1;
    private IntafyUserDataProvider intafyUserDataProvider;

    private String IN_FIRSTNAME;
    private String IN_LASTNAME;
    private String IN_STATUS;
    private String IN_EMAIL;
    private String IN_PINCODE;
    private IjoomerTextView headerText;

    /**
     * Overrides methods
     */

    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_profile_details_form;
    }

    @Override
    public void initComponents() {
        lnr_form = (LinearLayout) findViewById(R.id.lnr_form);
        btnBack = (IjoomerButton) findViewById(R.id.btnBack);
        btnSubmit = (IjoomerButton) findViewById(R.id.btnSubmit);
        txtFirstName = (IjoomerTextView) findViewById(R.id.txtFirstName);
        txtLastName = (IjoomerTextView) findViewById(R.id.txtLastName);
        txtStatus = (IjoomerTextView) findViewById(R.id.txtStatus);
        txtEmail = (IjoomerTextView) findViewById(R.id.txtEmail);
        edtFirstName = (IjoomerEditText) findViewById(R.id.edtFirstName);
        edtLastName = (IjoomerEditText) findViewById(R.id.edtLastName);
        edtEmail = (IjoomerEditText) findViewById(R.id.edtEmail);
        edtStatus = (IjoomerEditText) findViewById(R.id.edtStatus);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        intafyUserDataProvider = new IntafyUserDataProvider(this);
        getIntentData();
    }

    private void getIntentData(){
        try{
            IN_FIRSTNAME = getIntent().getStringExtra("IN_FIRSTNAME")!=null?getIntent().getStringExtra("IN_FIRSTNAME"):"";
            IN_LASTNAME = getIntent().getStringExtra("IN_LASTNAME")!=null?getIntent().getStringExtra("IN_LASTNAME"):"";
            IN_STATUS = getIntent().getStringExtra("IN_STATUS")!=null?getIntent().getStringExtra("IN_STATUS"):"";
            IN_EMAIL = getIntent().getStringExtra("IN_EMAIL")!=null?getIntent().getStringExtra("IN_EMAIL"):"";
            IN_PINCODE = getIntent().getStringExtra("IN_PINCODE")!=null?getIntent().getStringExtra("IN_PINCODE"):"";
            IN_FIELD = getIntent().getStringExtra("IN_FIELD")!=null? new JSONArray(getIntent().getStringExtra("IN_FIELD")):new JSONArray();
            createForm(IN_FIELD);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void prepareViews() {
        txtFirstName.setText(getString(R.string.intafy_first_name)+" *");
        txtLastName.setText(getString(R.string.intafy_last_name));
        txtStatus.setText(getString(R.string.intafy_status));
        txtEmail.setText(getString(R.string.intafy_email)+" *");

        edtEmail.setText(IN_EMAIL);
        edtFirstName.setText(IN_FIRSTNAME);
        edtLastName.setText(IN_LASTNAME);
        edtStatus.setText(IN_STATUS);
        headerText.setText(getString(R.string.intafy_edit_profile));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GET_ADDRESS_FROM_MAP) {
                editMap.setText(((HashMap<String, String>) data.getSerializableExtra("MAP_ADDRESSS_DATA")).get("address"));
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }

    @Override
    public void setActionListeners() {
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                hideSoftKeyboard();
                submitNewUser();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Class methods
     */


    /**
     * This method used to sumit registration form data.
     */
    private void submitNewUser() {
        boolean validationFlag = true;
        ArrayList<JSONObject> updatedField = new ArrayList<JSONObject>();
        int size = lnr_form.getChildCount();
        for (int i = 0; i < size; i++) {
            LinearLayout v = (LinearLayout) lnr_form.getChildAt(i);
            @SuppressWarnings("unchecked")
            JSONObject field= (JSONObject) v.getTag();

            IjoomerEditText edtValue = null;
            IjoomerEditText edtValue1 = null;
            Spinner spnrValue = null;
            try{
                if (field != null) {
                    if (field.getString(TYPE).equals(TEXT)) {
                        edtValue = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrEdit)).findViewById(R.id.txtValue);
                    } else if (field.getString(TYPE).equals(TEXTAREA)) {
                        edtValue = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrEditArea)).findViewById(R.id.txtValue);
                    } else if (field.getString(TYPE).equals(MAP)) {
                        edtValue = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrEditMap)).findViewById(R.id.txtValue);
                    } else if (field.getString(TYPE).equals(DATE) || field.getString(TYPE).equals(TIME)) {
                        edtValue = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrDate)).findViewById(R.id.txtValue);
                    } else if (field.getString(TYPE).equals(MULTIPLESELECT)) {
                        edtValue = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrMultipleSelect)).findViewById(R.id.txtValue);
                    }
                    if (field.getString(TYPE).equals(PHONE)) {
                        edtValue = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrPhone)).findViewById(R.id.txtValue1);
                        edtValue1 = (IjoomerEditText) ((LinearLayout) v.findViewById(R.id.lnrPhone)).findViewById(R.id.txtValue2);
                        if(edtValue.getText().toString().trim().length()>0 && edtValue1.getText().toString().trim().length()>0) {
                            field.put(VALUE, edtValue.getText().toString().trim() + "-" + edtValue1.getText().toString().trim());
                            updatedField.add(field);
                        }
                    }else if (field.getString(TYPE).equals(SELECT)) {
                        spnrValue = (Spinner) ((LinearLayout) v.findViewById(R.id.lnrSpin)).findViewById(R.id.txtValue);
                        field.put(VALUE, spnrValue.getSelectedItem().toString());
                        updatedField.add(field);
                    } else if (edtValue != null && edtValue.getText().toString().trim().length() <= 0 && (field.getString(REQUIRED).equals("1"))) {
                        edtValue.setError(getString(R.string.intafy_validation_value_required));
                        validationFlag = false;
                    } else {
                        if(field.getString(TYPE).equalsIgnoreCase("date")){
                            field.put(VALUE, IjoomerUtilities.convertDateStringFormat(edtValue.getText().toString().trim(),IjoomerApplicationConfiguration.dateFormat,"yyyy-MM-dd"));
                        }else{
                            field.put(VALUE, edtValue.getText().toString().trim());
                        }
                        updatedField.add(field);
                    }
                }
            }catch (Throwable e){
                e.printStackTrace();
            }
        }

        if(edtFirstName.getText().toString().trim().length()<=0){
            edtFirstName.setError(getString(R.string.intafy_validation_value_required));
            validationFlag=true;
        }
        if(!IjoomerUtilities.emailValidator(edtEmail.getText().toString().trim())){
            edtEmail.setError(getString(R.string.intafy_validation_invalid_email));
            validationFlag=true;
        }

        if (validationFlag) {
            final SeekBar proSeekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
            intafyUserDataProvider.updateUserDetails(edtFirstName.getText().toString().trim(),edtLastName.getText().toString().trim(),edtStatus.getText().toString().trim(),edtEmail.getText().toString().trim(),updatedField, new WebCallListener() {

                @Override
                public void onProgressUpdate(int progressCount) {
                    proSeekBar.setProgress(progressCount);
                }

                @Override
                public void onCallComplete(final int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                    if (responseCode == 200) {
                        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_profile), getString(R.string.intafy_profile_edited_successfully), getString(R.string.intafy_ok),
                                R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                    @Override
                                    public void NeutralMethod() {
                                        IjoomerApplicationConfiguration.setReloadRequired(true);
                                        finish();
                                    }
                                });

                    } else {
                        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_profile), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())),
                                getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

                                    @Override
                                    public void NeutralMethod() {

                                    }
                                });
                    }
                }
            });
        }
    }


    /**
     * This method used to create dynamic registration form.
     */
    private void createForm(JSONArray fields) {
        LayoutInflater inflater = LayoutInflater.from(IjoomerIntafyEditProfileActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 10;

        LinearLayout layout = null;
        int len = fields.length();
        for (int j = 0; j < len; j++) {
            try{
                final JSONObject field = fields.getJSONObject(j);
                View fieldView = inflater.inflate(R.layout.ijoomer_intafy_profile_dynamic_view_item, null);

                if (field.get(TYPE).equals(TEXT)) {
                    final IjoomerEditText edit;
                    layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEdit));
                    layout.setVisibility(View.VISIBLE);
                    edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
                    if (field.getString(VALUE).trim().length() > 0) {
                        edit.setText(field.getString(VALUE));
                    } else {
                        edit.setText(field.getString(VALUE));
                    }
                } else if (field.get(TYPE).equals(TEXTAREA)) {
                    final IjoomerEditText edit;
                    layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEditArea));
                    layout.setVisibility(View.VISIBLE);
                    edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));

                    if (field.getString(VALUE).trim().length() > 0) {
                        edit.setText(field.getString(VALUE));
                    } else {
                    }

                } else if (field.get(TYPE).equals(MAP)) {
                    final IjoomerEditText edit;
                    final ImageView imgMap;
                    layout = ((LinearLayout) fieldView.findViewById(R.id.lnrEditMap));
                    layout.setVisibility(View.VISIBLE);
                    edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
                    imgMap = ((ImageView) layout.findViewById(R.id.imgMap));
                    if (field.getString(VALUE).trim().length() > 0) {
                        edit.setText(field.getString(VALUE));
                    } else {
                        edit.setText(field.getString(VALUE));
                    }

                    imgMap.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            editMap = edit;
                            Intent intent = new Intent(IjoomerIntafyEditProfileActivity.this, IjoomerMapAddress.class);
                            startActivityForResult(intent, GET_ADDRESS_FROM_MAP);
                        }
                    });

                } else if (field.get(TYPE).equals(SELECT)) {
                    layout = ((LinearLayout) fieldView.findViewById(R.id.lnrSpin));
                    layout.setVisibility(View.VISIBLE);
                    final Spinner spn;
                    spn = ((Spinner) layout.findViewById(R.id.txtValue));
                    spn.setAdapter(IjoomerUtilities.getSpinnerAdapter(field));
                } else if (field.get(TYPE).equals(DATE)) {
                    final IjoomerEditText edit;
                    layout = ((LinearLayout) fieldView.findViewById(R.id.lnrDate));
                    layout.setVisibility(View.VISIBLE);
                    edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
                    if(field.getString(VALUE).trim().length()>0) {
                        edit.setText(IjoomerUtilities.convertDateStringFormat(field.getString(VALUE), "yyyy-mm-dd", IjoomerApplicationConfiguration.dateFormat));
                    }
                    edit.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(final View v) {
                            IjoomerUtilities.getDateDialog(((IjoomerEditText) v).getText().toString(), true, new CustomClickListner() {

                                @Override
                                public void onClick(String value) {
                                    ((IjoomerEditText) v).setText(value);
                                    ((IjoomerEditText) v).setError(null);
                                }
                            });

                        }
                    });

                } else if (field.get(TYPE).equals(PHONE)) {
                    final IjoomerEditText edit1;
                    final IjoomerEditText edit2;
                    layout = ((LinearLayout) fieldView.findViewById(R.id.lnrPhone));
                    layout.setVisibility(View.VISIBLE);
                    edit1 = ((IjoomerEditText) layout.findViewById(R.id.txtValue1));
                    edit2 = ((IjoomerEditText) layout.findViewById(R.id.txtValue2));
                    if(field.getString(VALUE).trim().length()>0){
                        if(field.getString(VALUE).contains("+") && field.getString(VALUE).contains("-") && field.getString(VALUE).split("-").length>1) {
                            edit1.setText(field.getString(VALUE).subSequence(1,field.getString(VALUE).length()).toString().split("-")[0]);
                            edit2.setText(field.getString(VALUE).subSequence(1,field.getString(VALUE).length()).toString().split("-")[1]);
                        }else if(field.getString(VALUE).contains("-") && field.getString(VALUE).split("-").length>1){
                            edit1.setText(field.getString(VALUE).split("-")[0]);
                            edit2.setText(field.getString(VALUE).split("-")[1]);
                        }else{
                            edit2.setText(field.getString(VALUE));
                        }
                    }

                } else if (field.get(TYPE).equals(TIME)) {
                    final IjoomerEditText edit;
                    layout = ((LinearLayout) fieldView.findViewById(R.id.lnrDate));
                    layout.setVisibility(View.VISIBLE);
                    edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
                    edit.setText(IjoomerUtilities.convertDateStringFormat(field.getString(VALUE),"kk:mm",IjoomerApplicationConfiguration.dateFormat));
                    edit.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(final View v) {
                            IjoomerUtilities.getTimeDialog(((IjoomerEditText) v).getText().toString(), new CustomClickListner() {

                                @Override
                                public void onClick(String value) {
                                    ((IjoomerEditText) v).setText(value);
                                    ((IjoomerEditText) v).setError(null);
                                }
                            });

                        }
                    });

                } else if (field.get(TYPE).equals(MULTIPLESELECT)) {
                    final IjoomerEditText edit;
                    layout = ((LinearLayout) fieldView.findViewById(R.id.lnrMultipleSelect));
                    layout.setVisibility(View.VISIBLE);
                    edit = ((IjoomerEditText) layout.findViewById(R.id.txtValue));
                    edit.setText(field.getString(VALUE));
                    edit.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(final View v) {
                            try{
                                IjoomerUtilities.getMultiSelectionDialog(field.getString(CAPTION), field.getString(OPTIONS), ((IjoomerEditText) v).getText().toString(), new CustomClickListner() {

                                    @Override
                                    public void onClick(String value) {
                                        ((IjoomerEditText) v).setText(value);
                                    }
                                });
                            }catch (Throwable e){
                                e.printStackTrace();
                            }

                        }
                    });
                }

                if (field.getString(REQUIRED).equalsIgnoreCase("1")) {
                    ((IjoomerTextView) layout.findViewById(R.id.txtLable)).setText(field.getString(CAPTION) + " *");
                } else {
                    ((IjoomerTextView) layout.findViewById(R.id.txtLable)).setText(field.getString(CAPTION));
                }
                fieldView.setTag(field);
                lnr_form.addView(fieldView, params);
            }catch (Throwable e){
                e.printStackTrace();
            }
        }


    }


}