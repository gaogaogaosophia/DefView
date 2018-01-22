package com.example.gaoshengnan.mytestforgit.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.gaoshengnan.mytestforgit.R;

/**
 * Created by gaoshengnan on 2018/1/22.
 */

public class JSTest extends Activity {

    private WebView webView;
    private Button button_js;


    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.js_test);
        webView = findViewById(R.id.webview_js);
        button_js = findViewById(R.id.btn_js);
        WebSettings webSettings = webView.getSettings();
        //设置与JS交互的权限
        webSettings.setJavaScriptEnabled(true);
        //js访问android，定义接口
        webView.addJavascriptInterface(new JSTest(),"control");
        //设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //先载入js代码，格式：file:///android_asset/文件名.html
        webView.loadUrl("file:///android_asset/javascript.html");
        //android调用js方法，2种
        button_js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //必须另开线程进行JS调用（否则无法调用）为什么？？？
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        //方法1：通过evaluateJavascript
                        //条件限制：Android 4.4 以上
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            webView.evaluateJavascript("javascript:sayHello()", new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String value) {

                                }
                            });
                        } else {
                            //方法2：通过loadUrl，注意对应的方法名要对应上
                            //1.无参无返回值
                            webView.loadUrl("javascript:sayHello()");
                            //2.有参无返回值
                            webView.loadUrl("javascript:alertMessage(\"" + "我是android传过来的内容" + "\")");
                            //3.有参有返回值
                            webView.loadUrl("javascript:sumToJava(1,2)");
                        }
                    }
                });
            }
        });

        /**
         * 由于允许js弹窗，所以需要支持js对话框
         * webview只是载体，内容渲染需要使用WebChromeClient类去实现
         * 通过设置WebChromeClient对象处理javascript的对话框
         * 设置响应js的alert函数
         */
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(JSTest.this);
                builder.setTitle("说明");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
                return true;
            }
        });

        //webview默认不处理https请求
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
    }

    //js调用android方法
    @JavascriptInterface
    public void onSumResult(int result) {
        Toast.makeText(getApplicationContext(), "android调用js方法(4.4前)，在android中的回调方法，入参是1和2，js返回结果是：" + result,
                Toast.LENGTH_LONG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
