package com.ijoomer.tips;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.ijoomer.tips.customviews.CustomBoldTextView;
import com.ijoomer.tips.customviews.CustomTextView;
import com.ijoomer.tips.utilities.Utility;


public class MyFavouriteActivity extends BaseActivity {

    private View view;
    public LinearLayout container;
    private ProgressBar pbrLoading;
    private MyFavouriteListFragment myFavouriteListFragment;
    private TipsDetailFragment tipsDetailFragment;
    private int currentFragment;

    public PopupWindow successPopupWindow;
    private View successPopupView;
    private CustomTextView txtSuccessPopupClose;
    private CustomTextView txtSuccessPopupMessage;
    private CustomBoldTextView txtSuccessPopupTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(this).inflate(R.layout.myfavourite,content);
        container = (LinearLayout) view.findViewById(R.id.container);
        pbrLoading = (ProgressBar) view.findViewById(R.id.pbrLoading);


        CustomBoldTextView txtHeader = (CustomBoldTextView) findViewById(R.id.txtHeader);
        txtHeader.setText(Html.fromHtml(getString(R.string.my_favourites)));

        successPopupView = LayoutInflater.from(this).inflate(R.layout.success_popup,null);
        txtSuccessPopupClose = (CustomTextView) successPopupView.findViewById(R.id.txtSuccessPopupClose);
        txtSuccessPopupTitle = (CustomBoldTextView) successPopupView.findViewById(R.id.txtSuccessPopupTitle);
        txtSuccessPopupMessage = (CustomTextView) successPopupView.findViewById(R.id.txtSuccessPopupMessage);
        txtSuccessPopupTitle.setText(getString(R.string.no_favourite));
        txtSuccessPopupMessage.setText(getString(R.string.no_favourite_message));
        successPopupWindow = new PopupWindow(successPopupView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        successPopupWindow.setAnimationStyle(R.style.anim_bottom_in_out_style);

        txtSuccessPopupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successPopupWindow.dismiss();
            }
        });

        myFavouriteListFragment = new MyFavouriteListFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.anim_slide_right_in, R.anim.anim_slide_right_out);
        ft.replace(container.getId(),myFavouriteListFragment);
        ft.commit();

        imgSlideBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgSlideMenu.setVisibility(View.VISIBLE);
        imgSlideBack.setVisibility(View.GONE);



    }

    public void gotoTipDetail(int suiteId,int driverId,String suiteName,String driverName,int tipId,String tipName,String tipNote,int favourite,String questionOne,String questionTwo,String questionThree){
        tipsDetailFragment = new TipsDetailFragment();
        Bundle bundle =new Bundle();
        bundle.putInt(Utility.SUITEID,suiteId);
        bundle.putString(Utility.SUITENAME,suiteName);
        bundle.putInt(Utility.DRIVERID,driverId);
        bundle.putString(Utility.DRIVERNAME,driverName);
        bundle.putInt(Utility.TIPID,tipId);
        bundle.putString(Utility.TIPNAME,tipName);
        bundle.putString(Utility.TIPNOTE,tipNote);
        bundle.putInt(Utility.TIPFAVOURITE,favourite);
        bundle.putString(Utility.TIPQUESTIONONE,questionOne);
        bundle.putString(Utility.TIPQUESTIONTWO,questionTwo);
        bundle.putString(Utility.TIPQUESTIONTHREE,questionThree);

        tipsDetailFragment.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.anim_slide_left_in, R.anim.anim_slide_left_out);
        ft.replace(container.getId(),tipsDetailFragment);
        ft.commit();

        CustomBoldTextView txtHeader = (CustomBoldTextView) findViewById(R.id.txtHeader);
        txtHeader.setText(tipName);
        txtHeader.setTextColor(getResources().getColor(R.color.grey));

        imgSlideMenu.setVisibility(View.GONE);
        imgSlideBack.setVisibility(View.VISIBLE);

        currentFragment++;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void hideProgressBar(){
        pbrLoading.setVisibility(View.GONE);
    }
    public void showProgressBar(){
        pbrLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if(currentFragment==1){

            if(tipsDetailFragment.sharePopupWindow.isShowing()){
                tipsDetailFragment.sharePopupWindow.dismiss();
            }else if(tipsDetailFragment.successPopupWindow.isShowing()){
                tipsDetailFragment.successPopupWindow.dismiss();
                if(tipsDetailFragment.isSharePopUpHide){
                    tipsDetailFragment.isSharePopUpHide=false;
                    tipsDetailFragment.sharePopupWindow.showAtLocation(tipsDetailFragment.imgShare, Gravity.NO_GRAVITY, 0, 0);
                }
            }else {
                CustomBoldTextView txtHeader = (CustomBoldTextView) findViewById(R.id.txtHeader);
                txtHeader.setText(Html.fromHtml(getString(R.string.my_favourites)));

                myFavouriteListFragment = new MyFavouriteListFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(R.anim.anim_slide_left_in, R.anim.anim_slide_left_out);
                ft.replace(container.getId(), myFavouriteListFragment);
                ft.commit();

                currentFragment--;

                imgSlideMenu.setVisibility(View.VISIBLE);
                imgSlideBack.setVisibility(View.GONE);
            }

        }else if(successPopupWindow.isShowing()){
            successPopupWindow.dismiss();
        }else{
            finish();
        }
    }
}
