package com.eosos.common.classes;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.Xml;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.eosos.components.main.EososMasterActivity;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.src.R;
import com.eosos.theme.ThemeManager;

import java.net.URLEncoder;

/**
 * This Class Contains All Method Related To IjoomerWebviewClient.
 * 
 * @author tasol
 * 
 */
public class IjoomerWebviewClient extends EososMasterActivity {

	private WebView webExternalLinks;
	private String link = "";
	private String IN_CONTENT = "";
    private String IN_FROM = "";
	SeekBar seekBar;

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

		try {
			webExternalLinks.setBackgroundColor(Color.TRANSPARENT);
			Paint p = new Paint();
			if (Build.VERSION.SDK_INT >= 11) {
				webExternalLinks.setLayerType(WebView.LAYER_TYPE_SOFTWARE, p);
			}
			webExternalLinks.getSettings().setJavaScriptEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void prepareViews() {

		getIntentData();
        if(IN_FROM.trim().length()>0){
            ((IjoomerTextView)getHeaderView().findViewById(R.id.txtHeader)).setText(IN_FROM);
        }
        if(IN_FROM.trim().length()<=0 || link != null && !link.contains("file:///android_asset/")){
		   seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.dialog_loading_please_wait));
        }else{
            ((IjoomerTextView)getHeaderView().findViewById(R.id.txtBack)).setVisibility(View.VISIBLE);
            ((ImageView)getHeaderView().findViewById(R.id.imgHome)).setVisibility(View.GONE);
        }
		webExternalLinks.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (seekBar == null) {
                    if(IN_FROM.trim().length()<=0 || link != null && !link.contains("file:///android_asset/")){
					seekBar = IjoomerUtilities.getLoadingDialog(getString(R.string.dialog_loading_please_wait));
                    }
				}
                if(IN_FROM.trim().length()<=0 || link != null && !link.contains("file:///android_asset/")){
				seekBar.setProgress(progress);
                }
				if (progress == 100) {
					seekBar = null;
				}
			}
		});

		webExternalLinks.setWebViewClient(new WebViewClient());
		webExternalLinks.getSettings().setJavaScriptEnabled(true);
		webExternalLinks.getSettings().setPluginState(PluginState.ON);
		webExternalLinks.getSettings().setSupportZoom(true);
		webExternalLinks.getSettings().setBuiltInZoomControls(true);

		if (link != null && link.length() > 0) {
//			if (!link.startsWith("http://") && !link.startsWith("https://")) {
//				link = "http://" + link;
//			}
            if(link.contains("file:///android_asset/")){
                webExternalLinks.getSettings().setDefaultTextEncodingName("utf-8");
                ((ImageView)getHeaderView().findViewById(R.id.imgHome)).setVisibility(View.GONE);
            }
            webExternalLinks.loadUrl(link);
        } else if (IN_CONTENT != null && IN_CONTENT.trim().length() > 0) {
			StringBuilder sb = new StringBuilder(); // StringBuilder();
			sb.append("<HTML><HEAD><link rel=\"stylesheet\" type=\"text/css\" href=\"weblayout.css\" /></HEAD><body>");
			String str = IN_CONTENT.trim();
			str = str.replaceAll("<iframe width=\"[0-9]*", "<iframe width=\"100\\%");
			str = str.replaceAll("<img[\\w]*", "<img height=\"auto\" style=\"max-width:100\\%\";");
			sb.append(str);
			sb.append("</body></HTML>");
			webExternalLinks.loadDataWithBaseURL("file:///android_asset/css/", sb.toString(), "text/html", "utf-8", null);
		}

	}

	private void getIntentData() {
		try {
			link = this.getIntent().getStringExtra("url");
			if (link == null) {
				IN_CONTENT = this.getIntent().getStringExtra("IN_CONTENT");
			}

		} catch (Exception e) {
			IN_CONTENT = this.getIntent().getStringExtra("IN_CONTENT");
		}

        IN_FROM = getIntent().getStringExtra("IN_FROM")!=null?getIntent().getStringExtra("IN_FROM"):"";
	}

	@Override
	public void onBackPressed() {
		if (webExternalLinks.canGoBack()) {
			webExternalLinks.goBack();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void setActionListeners() {

        if(IN_FROM.trim().length()>0 || link != null && link.contains("file:///android_asset/")){
            ((IjoomerTextView)getHeaderView().findViewById(R.id.txtBack)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
	}
}
