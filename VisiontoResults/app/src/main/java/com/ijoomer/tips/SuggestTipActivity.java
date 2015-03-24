package com.ijoomer.tips;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.ijoomer.tips.customviews.CustomBoldTextView;
import com.ijoomer.tips.customviews.CustomEditTex;
import com.ijoomer.tips.customviews.CustomTextView;
import com.ijoomer.tips.utilities.Utility;
import com.ijoomer.tips.webservices.WebServiceListener;
import com.ijoomer.tips.webservices.WebServicesProvider;

import java.io.File;


public class SuggestTipActivity extends BaseActivity {

    private View view;
    private Button btnSubmit;
    private CustomEditTex edtName;
    private CustomEditTex edtEmail;
    private CustomEditTex edtTwitterHandle;
    private CustomEditTex edtTip;
    private ProgressBar pbrLoading;

    private PopupWindow successPopupWindow;
    private View successPopupView;
    private CustomTextView txtSuccessPopupClose;
    private CustomTextView txtSuccessPopupMessage;
    private CustomBoldTextView txtSuccessPopupTitle;
    private WebServicesProvider servicesProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(this).inflate(R.layout.suggest_tip,content);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        edtName = (CustomEditTex) view.findViewById(R.id.edtName);
        edtTwitterHandle = (CustomEditTex) view.findViewById(R.id.edtTwitterHandle);
        edtEmail = (CustomEditTex) view.findViewById(R.id.edtEmail);
        edtTip = (CustomEditTex) view.findViewById(R.id.edtTip);
        pbrLoading = (ProgressBar) view.findViewById(R.id.pbrLoading);

        servicesProvider = new WebServicesProvider(this);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                boolean isValid=true;
                if(edtName.getText().toString().trim().length()<=0){
                    edtName.setError(getResources().getString(R.string.value_required));
                    isValid=false;
                }
                if(edtEmail.getText().toString().trim().length()<=0){
                    edtEmail.setError(getResources().getString(R.string.value_required));
                    isValid=false;
                }else if(!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()){
                    edtEmail.setError(getResources().getString(R.string.invalid_email));
                    isValid=false;
                }

                if(edtTip.getText().toString().trim().length()<=0){
                    edtTip.setError(getResources().getString(R.string.value_required));
                    isValid=false;
                }

                if(edtTwitterHandle.getText().toString().trim().length()<=0){
                    edtTwitterHandle.setError(getResources().getString(R.string.value_required));
                    isValid=false;
                }

                if(isValid){
                    pbrLoading.setVisibility(View.VISIBLE);
                    servicesProvider.suggestTip(edtName.getText().toString().trim(),edtEmail.getText().toString().trim(),edtTip.getText().toString().trim(),edtTwitterHandle.getText().toString(),new WebServiceListener() {
                        @Override
                        public void onComplete(int code) {
                            pbrLoading.setVisibility(View.GONE);
                            if(code==200 || code == 500) {
                                edtName.setText("");
                                edtEmail.setText("");
                                edtTip.setText("");
                                edtTwitterHandle.setText("");
                                txtSuccessPopupTitle.setText(getResources().getString(R.string.success));
                                txtSuccessPopupMessage.setText(getResources().getString(R.string.thank_you_for_suggestion));
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
        txtHeader.setText(Html.fromHtml(getString(R.string.suggest_tip)));
        imgSlideBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgSlideMenu.setVisibility(View.GONE);
        imgSlideBack.setVisibility(View.VISIBLE);


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
