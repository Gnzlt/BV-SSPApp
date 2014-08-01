package com.gnzlt.bv_sspapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends ActionBarActivity {

    WebView web;
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setHomeButtonEnabled(true);

        URL = "file:///android_asset/web/index.html";

        web = (WebView) findViewById(R.id.webView);
        web.setWebViewClient(new MyWebViewClient());

        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);

        web.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        web.loadUrl(URL);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack())
        {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
/*
	        if (Uri.parse(url).getHost().equals("m.aucorsa.es")) {
	            // This is my web site, so do not override; let my WebView load the page
	            return false;
	        }
	        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
	        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	        startActivity(intent);
	        return true;
*/
            return false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            web.loadUrl(URL);
            return true;
        }
        if (id == R.id.action_refresh) {
            web.reload();
            return true;
        }
        if (id == R.id.action_logout) {
            android.webkit.CookieManager.getInstance().removeAllCookie();
            web.loadUrl(URL);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
