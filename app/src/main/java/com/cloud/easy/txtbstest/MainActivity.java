package com.cloud.easy.txtbstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


/**
 * android和js交互
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private WebView mWebView;
    private WebSettings settings;
    private Button btn1,btn2;
    String str = "\"我是传过来的值\"";
    String s = "javascript:get_android2("+str+")";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFindViewById();
        initListener();
        mWebView.loadUrl("file:///android_asset/index.html");
        Log.e("@@@@",s);
    }

    private void initFindViewById() {
        mWebView = findViewById(R.id.mWebView);
        settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                Log.e("onPageFinished()","调用了onPageFinished");
            }
        });
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
    }

    private void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                mWebView.evaluateJavascript("javascript:get_android1()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.e("@@@",s+"-------");
                    }
                });
                break;
            case R.id.btn2:
                //mWebView.loadUrl("javascript:get_android2("+"1111111111"+")");

               // mWebView.evaluateJavascript("javascript:get_android2(我是传过来的值)",null);
                //String s = "javascript:get_android2('"+"我是传过来的值"+"')";
                mWebView.evaluateJavascript( s,null);
                break;
        }
    }
}
