package com.example.PARM;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class ChanelWebView extends AppCompatActivity {

    private WebView mWebView;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provelist);

        mWebView = (WebView)findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("https://www.chanel.com/kr/fragrance/?gclid=EAIaIQobChMI_JqK4uee8wIVDVpgCh1IAgTKEAAYASAAEgJRFfD_BwE");
    }
}
