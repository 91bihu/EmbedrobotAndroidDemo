package com.bihu.embedrobot.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bihu.embedrobot.R;
import com.bihu.embedrobot.util.SharedPerUtil;

public class Activity_WebView extends Activity {


    public WebView msgWebView;

    public TextView title_name=null;

    public TextView title_cache=null;

    private WebSettings webSetting;

    String user_token = "";

    String url = "http://wx.91bihu.com/home/index?token=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_avtivity_webview);

        user_token = getIntent().getStringExtra("token");

        InitView();

    }

    public void InitView() {

        title_name = (TextView)findViewById(R.id.title_name);

        title_name.setText("内嵌机器人");

        title_cache = (TextView)findViewById(R.id.title_tel_iv);

        title_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //清除本地缓存
                SharedPerUtil.getInstanse(Activity_WebView.this).setUserLoginState("");

                SharedPerUtil.getInstanse(Activity_WebView.this).setUserLoginETIME("");

                Intent intent = new Intent(Activity_WebView.this,Activity_UserLogin.class);

                Activity_WebView.this.startActivity(intent);

                Activity_WebView.this.finish();

            }
        });

        msgWebView = (WebView) findViewById(R.id.webview);

        webSetting = msgWebView.getSettings();

        initWebView();

    }

    private void initWebView() {


        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webSetting.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        webSetting.setSupportZoom(true);//是否可以缩放，默认true
        webSetting.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        webSetting.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webSetting.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webSetting.setAppCacheEnabled(true);//是否使用缓存
        webSetting.setDomStorageEnabled(true);//DOM Storage


        msgWebView.loadUrl(url+user_token);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        msgWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });


        //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        msgWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && msgWebView.canGoBack()) {  //表示按返回键 时的操作

                        msgWebView.goBack();   //后退


                        return true;    //已处理
                    }
                }
                return false;
            }
        });
    }


}
