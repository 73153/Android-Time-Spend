package com.mycadiz.common.classes;

import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.mycadiz.customviews.IjoomerButton;
import com.mycadiz.src.R;
import com.mycadiz.theme.ThemeManager;

/**
 * This Class Contains All Method Related To IjoomerWebviewClient.
 *
 * @author tasol
 *
 */
public class IjoomerWebviewClient extends IjoomerSuperMaster {

    private WebView webExternalLinks;
    private IjoomerButton btnClose;
    private String link = "";

    /**
     * Override methods
     */
    @Override
    public int setLayoutId() {
        return ThemeManager.getInstance().getWebview();
    }

    @Override
    public void initComponents() {
        webExternalLinks = (WebView) findViewById(R.id.webExternalLinks);
        btnClose = (IjoomerButton) findViewById(R.id.btnClose);

    }

    @Override
    public void prepareViews() {
        getIntentData();
        final SeekBar seekBar = IjoomerUtilities
                .getLoadingDialog(getString(R.string.dialog_loading_please_wait));
        webExternalLinks.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                seekBar.setProgress(progress);
            }
        });

        webExternalLinks.setWebViewClient(new WebViewClient());
        webExternalLinks.getSettings().setJavaScriptEnabled(true);
        webExternalLinks.getSettings().setPluginState(PluginState.ON);
        webExternalLinks.getSettings().setBuiltInZoomControls(true);
        webExternalLinks.getSettings().setSupportZoom(true);



        webExternalLinks.loadUrl(link);


        btnClose.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void setActionListeners() {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
    }

    @Override
    public View setLayoutView() {
        return null;
    }

    @Override
    public int setHeaderLayoutId() {
        return 0;
    }

    @Override
    public int setFooterLayoutId() {
        return 0;
    }

    @Override
    public String[] setTabItemNames() {
        return null;
    }

    @Override
    public int setTabBarDividerResId() {
        return 0;
    }

    @Override
    public int setTabItemLayoutId() {
        return 0;
    }

    @Override
    public int[] setTabItemOnDrawables() {
        return null;
    }

    @Override
    public int[] setTabItemOffDrawables() {
        return null;
    }

    @Override
    public int[] setTabItemPressDrawables() {
        return null;
    }

    private void getIntentData() {
        link = this.getIntent().getStringExtra("url");
    }

}
