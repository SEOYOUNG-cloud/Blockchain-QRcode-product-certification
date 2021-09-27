package com.example.PARM;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class GucciWebView extends AppCompatActivity {

    private WebView mWebView;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        mWebView = (WebView)findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("https://www.gucci.com/kr/ko/ca/women/handbags-c-women-handbags?&utm_source=google_kr_pc&utm_medium=cpc&utm_campaign=pc_exact&utm_content=N0.Brand_GUCCI&utm_term=%EA%B5%AC%EC%B0%8C&gclid=EAIaIQobChMI6LeetOSe8wIVU1tgCh2EKQwUEAAYASAAEgKQ1PD_BwE");
    }
}
