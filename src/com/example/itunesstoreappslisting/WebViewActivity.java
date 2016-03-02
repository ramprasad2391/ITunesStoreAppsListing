//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto
package com.example.itunesstoreappslisting;



import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {
	ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		if(getIntent().getExtras() != null && getIntent().getExtras().containsKey("AppUrl")){
			dialog = new ProgressDialog(this);
			dialog.setMessage("Loading Page...");
			dialog.show();
			String AppUrl = getIntent().getExtras().getString("AppUrl");
			WebView webView = (WebView) findViewById(R.id.webView1);
			webView.setWebViewClient(new MyWebViewClient());
			webView.getSettings().setJavaScriptEnabled(true);
			webView.loadUrl(AppUrl);	
			if(dialog.isShowing()){
				dialog.dismiss();
			}
		}

		
		}
	
	private class MyWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}	
	
}

