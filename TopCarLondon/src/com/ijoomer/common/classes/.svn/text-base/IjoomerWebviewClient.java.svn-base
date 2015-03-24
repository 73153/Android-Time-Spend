package com.ijoomer.common.classes;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.ijoomer.customviews.IjoomerButton;
import com.ijoomer.topcarlondon.src.R;
import com.smart.framework.SmartListAdapterWithHolder;
import com.smart.framework.SmartListItem;

/**
 * This Class Contains All Method Related To IjoomerWebviewClient.
 * 
 * @author tasol
 * 
 */
public class IjoomerWebviewClient extends IjoomerSuperMaster {

	private WebView webExternalLinks;
	private IjoomerButton btnClose;
	private ProgressBar pbrLoadUrl;
	private String link = "";

	/**
	 * Override methods
	 */
	@Override
	public int setLayoutId() {
		return R.layout.ijoomer_custom_webview;
	}

	@Override
	public void initComponents() {
		webExternalLinks = (WebView) findViewById(R.id.webExternalLinks);
		btnClose = (IjoomerButton) findViewById(R.id.btnClose);
		pbrLoadUrl = (ProgressBar) findViewById(R.id.pbrLoadUrl);

	}

	@Override
	public void prepareViews() {
		getIntentData();
		pbrLoadUrl.setVisibility(View.VISIBLE);
		webExternalLinks.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (progress == 100) {
					pbrLoadUrl.setVisibility(View.GONE);
				}else{
                    pbrLoadUrl.setVisibility(View.VISIBLE);
                }
			}
		});

		webExternalLinks.setWebViewClient(new WebViewClient());
		webExternalLinks.getSettings().setJavaScriptEnabled(true);
		webExternalLinks.getSettings().setPluginState(PluginState.ON);
        webExternalLinks.getSettings().setSupportZoom(true);
        webExternalLinks.getSettings().setBuiltInZoomControls(true);

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
