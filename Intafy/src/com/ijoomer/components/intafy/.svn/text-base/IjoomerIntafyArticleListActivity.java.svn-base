package com.ijoomer.components.intafy;

import android.view.View;
import android.widget.LinearLayout;

import com.ijoomer.common.classes.IjoomerIntafyMaster;
import com.ijoomer.common.configuration.IjoomerApplicationConfiguration;
import com.ijoomer.customviews.IjoomerTextView;
import com.ijoomer.intafy.src.R;

/**
 * This Class Contains All Method Related To IjoomerHomeActivity.
 *
 * @author tasol
 *
 */
public class IjoomerIntafyArticleListActivity extends IjoomerIntafyMaster implements IntafyTagHolder {

    private IjoomerIntafyArticleDateListFragment articleDateListFragment;
    private IjoomerIntafyArticleTitleListFragment articleTitleListFragment;
    private IjoomerTextView headerText;
    private IjoomerTextView footerLeftText;
    private IjoomerTextView footerRightText;
    private LinearLayout lnrFooterLeft;
    private LinearLayout lnrFooterRight;
    private String currentList;

    /**
     * Overrides methods
     */


    @Override
    public int setLayoutId() {
        return R.layout.ijoomer_intafy_article_list;
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
        headerText = (IjoomerTextView) getHeaderView().findViewById(R.id.txtHeader);
        footerLeftText = (IjoomerTextView) getFooterView().findViewById(R.id.txtFirst);
        footerRightText = (IjoomerTextView) getFooterView().findViewById(R.id.txtLast);
        lnrFooterLeft = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterLeft);
        lnrFooterRight = (LinearLayout) getFooterView().findViewById(R.id.lnrFooterRight);
    }

    @Override
    public void prepareViews() {
        articleDateListFragment = new IjoomerIntafyArticleDateListFragment();
        articleTitleListFragment = new IjoomerIntafyArticleTitleListFragment();
        addFragment(R.id.lnrFragment, articleDateListFragment);
        currentList="datewise";
        headerText.setText(getString(R.string.intafy_article));
        footerRightText.setText(getString(R.string.intafy_title));
        footerLeftText.setText(getString(R.string.intafy_date));
        footerLeftText.setTextColor(getResources().getColor(R.color.blue));
    }

    @Override
    public void setActionListeners() {
        lnrFooterRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerRightText.setTextColor(getResources().getColor(R.color.blue));
                footerLeftText.setTextColor(getResources().getColor(R.color.white));
                if(articleTitleListFragment!=null){
                    currentList="titlewise";
                    addFragment(R.id.lnrFragment, articleTitleListFragment);
                }
            }
        });
        lnrFooterLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerLeftText.setTextColor(getResources().getColor(R.color.blue));
                footerRightText.setTextColor(getResources().getColor(R.color.white));
                if(articleDateListFragment!=null){
                    currentList="datewise";
                    addFragment(R.id.lnrFragment, articleDateListFragment);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        if(IjoomerApplicationConfiguration.isReloadRequired()){
            if(currentList.equals("datewise")){
                if(articleDateListFragment!=null){
                    articleDateListFragment.update();
                }
            }else{
                if(articleTitleListFragment!=null){
                    articleTitleListFragment.update();
                }
            }
        }
        super.onResume();
    }
}
