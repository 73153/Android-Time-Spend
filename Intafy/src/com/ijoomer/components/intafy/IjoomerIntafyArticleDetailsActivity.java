package com.ijoomer.components.intafy;

import android.graphics.Bitmap;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.classes.IjoomerUtilities;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.customviews.RoundedImageView;
import com.ijoomer.intafy.src.R;
import com.ijoomer.library.intafy.IntafyArticleDataProvider;
import com.ijoomer.weservice.WebCallListener;
import com.smart.framework.CustomAlertMagnatic;
import com.smart.framework.CustomAlertNeutral;
import com.smart.framework.SmartActivity;
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
public class IjoomerIntafyArticleDetailsActivity extends IjoomerIntafyMaster implements IntafyTagHolder {

    private IjoomerTextView txtArticleTitle;
    private IjoomerTextView txtArticleDate;
    private IjoomerTextView txtArticleContent;
    private RoundedImageView imgArticleImage;
    private ProgressBar pbrImageLoad;
    private String IN_ARTICLE_ID;
    private String IN_TABLE;
    private IntafyArticleDataProvider articleDataProvider;
    private ImageView imgEdit;
    private ImageView imgDelete;

    private HashMap<String,String> articleDetails;
    private AQuery aQuery;
    private IjoomerTextView headerLeftText;
    private LinearLayout lnrHeaderLeft;
    private IjoomerTextView headerText;

    /**
     * Overrides methods
     */


    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_article_details;
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

        txtArticleTitle = (IjoomerTextView)findViewById(R.id.txtArticleTitle);
        txtArticleDate = (IjoomerTextView)findViewById(R.id.txtArticleDate);
        txtArticleContent = (IjoomerTextView)findViewById(R.id.txtArticleContent);
        pbrImageLoad = (ProgressBar)findViewById(R.id.pbrImageLoad);
        imgArticleImage = (RoundedImageView)findViewById(R.id.imgArticleImage);
        headerLeftText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtFirst);
        lnrHeaderLeft = (LinearLayout) getHeaderView().findViewById(R.id.lnrHeaderLeft);
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        imgEdit = (ImageView)findViewById(R.id.imgEdit);
        imgDelete = (ImageView)findViewById(R.id.imgDelete);


        articleDataProvider = new IntafyArticleDataProvider(this);
        aQuery = new AQuery(this);
    }

    @Override
    public void prepareViews() {
        IN_ARTICLE_ID = getIntent().getStringExtra("IN_ARTICLE_ID")!=null ? getIntent().getStringExtra("IN_ARTICLE_ID"):"0";
        IN_TABLE = getIntent().getStringExtra("IN_TABLE")!=null ? getIntent().getStringExtra("IN_TABLE"):"";
        if(!IN_ARTICLE_ID.equals("0")){
            articleDetails = articleDataProvider.getArticleDetails(IN_TABLE,IN_ARTICLE_ID);
            setArticleDetails();
        }
        headerText.setText(getString(R.string.intafy_article_details));

        headerLeftText.setText(getString(R.string.intafy_back));
    }

    private void setArticleDetails(){
        txtArticleTitle.setText(articleDetails.get(TITLE));
        txtArticleDate.setText(IjoomerUtilities.convertDateStringFormat(articleDetails.get(CREATED), "yyyy-MM-dd", IjoomerApplicationConfiguration.dateFormat));
        txtArticleContent.setText(Html.fromHtml(articleDetails.get(INTROTEXT)));
        txtArticleContent.setMovementMethod(new LinkMovementMethod());
        aQuery.id(imgArticleImage).progress(pbrImageLoad).image(articleDetails.get(IMAGE_INTRO),true,true,150,R.drawable.ic_launcher);
        String connectedUserId = SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SP_CONNECTEDUSERID,"0");
        if(articleDetails.get(CREATED_BY).equals(connectedUserId)){
            imgEdit.setVisibility(View.VISIBLE);
        }
        if(articleDetails.get(DELETEALLOW).equals("1")){
            imgDelete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setActionListeners() {
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEdit.setImageResource(getIcon(EDITICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgEdit.setImageResource(getIcon(EDITICON,false));
                        try{
                            loadNew(IjoomerIntafyArticleAddActivity.class,IjoomerIntafyArticleDetailsActivity.this,true,"IN_ARTICLE_ID",articleDetails.get("id"),"IN_TABLE",IN_TABLE);
                        }catch (Throwable e){
                            e.printStackTrace();
                        }
                    }
                }, 1000);
            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgDelete.setImageResource(getIcon(DELETEICON,true));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgDelete.setImageResource(getIcon(DELETEICON,false));
                        IjoomerUtilities.getCustomConfirmDialog(getString(R.string.intafy_article), getString(R.string.intafy_are_you_sure_remove_article), getString(R.string.intafy_yes), getString(R.string.intafy_no), new CustomAlertMagnatic() {
                            @Override
                            public void PositiveMethod() {
                                final SeekBar seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.intafy_loading_sending_request));
                                articleDataProvider.removeArticle(IN_ARTICLE_ID, new WebCallListener() {
                                    @Override
                                    public void onCallComplete(int responseCode, String errorMessage, ArrayList<HashMap<String, String>> data1, Object data2) {
                                        if (responseCode == 200) {
                                            IjoomerApplicationConfiguration.setReloadRequired(true);
                                            finish();
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
                }, 1000);
            }
        });
        imgArticleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    loadNew(IjoomerIntafyImageViewActivity.class,IjoomerIntafyArticleDetailsActivity.this,false,"IN_IMAGE",articleDetails.get("orignleImage_intro"));
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        });
        lnrHeaderLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerLeftText.setTextColor(getResources().getColor(R.color.blue));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        headerLeftText.setTextColor(getResources().getColor(R.color.white));
                        finish();
                    }
                }, 1000);
            }
        });

    }

    private void responseErrorMessageHandler(final int responseCode) {
        IjoomerUtilities.getCustomOkDialog(getString(R.string.intafy_article), getString(getResources().getIdentifier("code" + responseCode, "string", getPackageName())), getString(R.string.intafy_ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {

            @Override
            public void NeutralMethod() {

            }
        });
    }


}
