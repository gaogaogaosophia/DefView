package com.example.gaoshengnan.mytestforgit.webview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.gaoshengnan.mytestforgit.R;

/**
 * Created by gaoshengnan on 2018/1/22.
 */

public class WebViewTest extends Activity{
    private WebView webView;
    private WebSettings webSettings;
    private TextView webTitile,textBeginLoading,textLoading,textEndLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_test);
        webView = findViewById(R.id.webview);
        webTitile = findViewById(R.id.web_title);
        textBeginLoading = findViewById(R.id.text_beginloading);
        textLoading = findViewById(R.id.text_Loading);
        textEndLoading = findViewById(R.id.text_endLoading);

        webSettings = webView.getSettings();

        //webview的loadUrl方法：
        // 1.网络地址；
        // 2.本地地址：webView.loadUrl("file:///android_asset/XX.html")；
        // 本地文件存放在：assets文件（若没有，则在app中创建）中。
        webView.loadUrl("http://www.baidu.com/");

        //不用浏览器打开，直接在当前页面打开url
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //设置WebChromeClient类
        webView.setWebChromeClient(new WebChromeClient(){
            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    textLoading.setText(progress);
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                    textLoading.setText(progress);
                }
            }
            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                System.out.println("标题");
                webTitile.setText(title);
            }
        });

        //设置WebViewClient类
        webView.setWebViewClient(new WebViewClient(){
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                System.out.println("开始加载了");
                textBeginLoading.setText("开始加载了");
            }
            //设置加载后的函数
            @Override
            public void onPageFinished(WebView view, String url) {
                textEndLoading.setText("加载结束了");
            }
        });

    }

    //点击返回上一页而不是退出浏览器

    /**
     * 默认情况下点击webview的“Back”键，浏览器调用finish()结束自身；
     * 若希望浏览的网页回退而不是退出浏览器，需要在当前Activity中处理并消费掉该Back事件；
     * 重写Activity类的onKeyDown(int keyCoder,KeyEvent event)方法。
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //销毁webview
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != webView) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        webSettings.setJavaScriptEnabled(true);
    }

    //避免webview加载的html里有些js一直在执行动画，且此刻webview挂在了后台，导致非常耗电
    @Override
    protected void onStop() {
        super.onStop();
        webSettings.setJavaScriptEnabled(false);
    }
}
