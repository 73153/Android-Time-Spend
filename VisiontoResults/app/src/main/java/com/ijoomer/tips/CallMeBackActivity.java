package com.ijoomer.tips;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TimePicker;

import com.ijoomer.tips.customviews.CustomBoldTextView;
import com.ijoomer.tips.customviews.CustomEditTex;
import com.ijoomer.tips.customviews.CustomTextView;
import com.ijoomer.tips.webservices.WebServiceListener;
import com.ijoomer.tips.webservices.WebServicesProvider;

import java.util.Calendar;


public class CallMeBackActivity extends BaseActivity {

    private View view;
    private Button btnSubmit;
    private CustomEditTex edtName;
    private CustomEditTex edtPhoneNumber;
    private CustomEditTex edtCountry;
    private CustomEditTex edtPreferredTime;
    private ProgressBar pbrLoading;

    private PopupWindow successPopupWindow;
    private View successPopupView;
    private CustomTextView txtSuccessPopupClose;
    private CustomTextView txtSuccessPopupMessage;
    private CustomBoldTextView txtSuccessPopupTitle;

    private WebServicesProvider servicesProvider;

    private int fromHour;
    private int fromMin;
    private int toHour;
    private int toMin;
    private final int TIME_DIALOG_ID_FROM = 0;
    private final int TIME_DIALOG_ID_TO = 1;
    private int timeSelectedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(this).inflate(R.layout.callmeback,content);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        edtName = (CustomEditTex) view.findViewById(R.id.edtName);
        edtPhoneNumber = (CustomEditTex) view.findViewById(R.id.edtPhoneNumber);
        edtCountry = (CustomEditTex) view.findViewById(R.id.edtCountry);
        edtPreferredTime = (CustomEditTex) view.findViewById(R.id.edtPreferredTime);
        pbrLoading = (ProgressBar) view.findViewById(R.id.pbrLoading);


        edtPreferredTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
               showDialog(TIME_DIALOG_ID_FROM);
            }
        });

        servicesProvider = new WebServicesProvider(this);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                boolean isValid=true;
                if(edtName.getText().toString().trim().length()<=0){
                    edtName.setError(getResources().getString(R.string.value_required));
                    isValid=false;
                }
                if(edtPhoneNumber.getText().toString().trim().length()<=0){
                    edtPhoneNumber.setError(getResources().getString(R.string.value_required));
                    isValid=false;
                }

                if(edtCountry.getText().toString().trim().length()<=0){
                    edtCountry.setError(getResources().getString(R.string.value_required));
                    isValid=false;
                }
                if(edtPreferredTime.getText().toString().trim().length()<=0){
                    edtPreferredTime.setError(getResources().getString(R.string.value_required));
                    isValid=false;
                }

                if(isValid){
                    pbrLoading.setVisibility(View.VISIBLE);
                    servicesProvider.callMeBack(edtName.getText().toString().trim(),edtPhoneNumber.getText().toString().trim(),edtCountry.getText().toString().trim(),edtPreferredTime.getText().toString().trim(),new WebServiceListener() {
                        @Override
                        public void onComplete(int code) {
                            pbrLoading.setVisibility(View.GONE);
                            if(code==200 || code == 500) {
                                edtName.setText("");
                                edtPhoneNumber.setText("");
                                edtCountry.setText("");
                                edtPreferredTime.setText("");
                                txtSuccessPopupTitle.setText(getString(R.string.thank_you));
                                txtSuccessPopupMessage.setText(getString(R.string.call_me_back_submission));
                            }else{
                                txtSuccessPopupTitle.setText(getResources().getString(R.string.error));
                                txtSuccessPopupMessage.setText(getResources().getString(R.string.no_internet_connection));
                            }
                            successPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);
                        }
                    });
                }
            }
        });

        CustomBoldTextView txtHeader = (CustomBoldTextView) findViewById(R.id.txtHeader);
        txtHeader.setText(Html.fromHtml(getString(R.string.call_me_back_header)));

        imgSlideMenu.setVisibility(View.GONE);
        imgSlideBack.setVisibility(View.VISIBLE);
        imgSlideBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        successPopupView = LayoutInflater.from(this).inflate(R.layout.success_popup,null);
        txtSuccessPopupClose = (CustomTextView) successPopupView.findViewById(R.id.txtSuccessPopupClose);
        txtSuccessPopupTitle = (CustomBoldTextView) successPopupView.findViewById(R.id.txtSuccessPopupTitle);
        txtSuccessPopupMessage = (CustomTextView) successPopupView.findViewById(R.id.txtSuccessPopupMessage);
        successPopupWindow = new PopupWindow(successPopupView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        successPopupWindow.setAnimationStyle(R.style.anim_bottom_in_out_style);

        txtSuccessPopupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successPopupWindow.dismiss();
            }
        });

        Calendar c=Calendar.getInstance();
        fromHour=c.get(Calendar.HOUR);
        fromMin=c.get(Calendar.MINUTE);
        toHour=c.get(Calendar.HOUR);
        toMin=c.get(Calendar.MINUTE);
    }

    protected Dialog onCreateDialog(int id)
    {
        switch(id)
        {
            case TIME_DIALOG_ID_FROM:
                timeSelectedCount=0;
                TimePickerDialog timePickerDialogFrom  = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(timeSelectedCount<1){
                            timeSelectedCount++;
                            fromHour=hourOfDay;
                            fromMin=minute;
                            edtPreferredTime.setText(fromHour+":"+fromMin);
                            showDialog(TIME_DIALOG_ID_TO);
                        }
                    }
                }, fromHour, fromMin, false);
                timePickerDialogFrom.setTitle(getString(R.string.from_time));
                return timePickerDialogFrom;
            case TIME_DIALOG_ID_TO:
                timeSelectedCount=0;
                TimePickerDialog timePickerDialogTo  = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(timeSelectedCount<1){
                            toHour=hourOfDay;
                            toMin=minute;
                            edtPreferredTime.setText(edtPreferredTime.getText() + " - " + toHour + ":" + toMin);
                        }
                    }
                }, toHour, toMin, false);
                timePickerDialogTo.setTitle(getString(R.string.to_time));
                return timePickerDialogTo;
        }
        return null;
    }
    @Override
    public void onBackPressed() {
        if(successPopupWindow.isShowing()){
            successPopupWindow.dismiss();
        }else{
            super.onBackPressed();
        }
    }
}
