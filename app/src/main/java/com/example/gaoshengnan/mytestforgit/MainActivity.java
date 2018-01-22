package com.example.gaoshengnan.mytestforgit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button showDefView;
    private Button retrofitViewAsync;
    private Button retrofitViewSync;
    private Button annotationRepEnum;
    private Button annotationReflect;
    private Button webViewTest;
    private Button jsTest;
    private Retrofit retrofit;
    private Call<ResponseBody> call;
    private RetrofitService retrofitService;
    private DataService dataService;
    private AnnotationEnum annotationEnum;

    //自定义跳转协议
    private static final String DEF_VIEW_URI = "test://gaogao/defView";
    private static final String RETROFIT2_TEST = "test://gaogao/Retrofit2";
    private static final String WEBVIEW_TEST = "test://gaogao/WebViewTest";
    private static final String JS_TEST = "test://gaogao/JSTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        showDefView = findViewById(R.id.show_def_view);
        retrofitViewAsync = findViewById(R.id.retrofit2_async);
        retrofitViewSync = findViewById(R.id.retrofit2_sync);
        annotationRepEnum = findViewById(R.id.annotaition);
        //annotationReflect = findViewById(R.id.a)
        webViewTest = findViewById(R.id.webview_test);
        jsTest = findViewById(R.id.js_test);
        retrofitViewAsync.setOnClickListener(this);
        retrofitViewSync.setOnClickListener(this);
        showDefView.setOnClickListener(this);
        annotationRepEnum.setOnClickListener(this);
        webViewTest.setOnClickListener(this);
        jsTest.setOnClickListener(this);

        annotationEnum = new AnnotationEnum(AnnotationEnum.WINTER);
    }

    @Override
    public void onClick(View v) {
        Uri uri;
        Intent intent = null;
        switch (v.getId()) {
            case R.id.show_def_view:
                //自定义页面
                uri = Uri.parse(DEF_VIEW_URI);
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.retrofit2_async:
//                //retrofit实例页面
//                Uri uriRetrofit = Uri.parse(RETROFIT2_TEST);
//                intent = new Intent(Intent.ACTION_VIEW, uriRetrofit);
//                startActivity(intent);
                AsyncRetrofit();
                break;
            case R.id.retrofit2_sync:
                //主线程不可以做网络请求
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SyncRetrofit();
                    }
                }).start();
                break;
            case R.id.annotaition:
                annotationEnum.testIntDefFlag();
                //annotationEnum.whichSeason();
                break;
            case R.id.webview_test:
                uri = Uri.parse(WEBVIEW_TEST);
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.js_test:
                uri = Uri.parse(JS_TEST);
                intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            default:
                break;
        }
    }

    /**
     * 异步请求
     */
    public void AsyncRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://wiki.sankuai.com/")
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
        call = retrofitService.getUser("gaoshengnan");
        //异步请求
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                try {
                    Log.i("Asynchronous Retrofit", "response = : "+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("TAG", "onFailure");
            }
        });
    }

    /**
     * 同步请求
     */
    public void SyncRetrofit(){
        try {
            call = retrofitService.getPage(1255723626);
            Response<ResponseBody> bodyResponse = call.execute();
            String body = bodyResponse.body().string();//获取返回的字符串
            Log.i("Synchronous Retrofit", "reponse = : "+body);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getData(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://WuXiaolong.me/")//暂定网址
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dataService = retrofit.create(DataService.class);
        ApiInfo apiInfo = new ApiInfo();
        ApiInfo.ApiInfoBean apiInfoBean = apiInfo.new ApiInfoBean();
        apiInfoBean.setKey("888");
        apiInfoBean.setName("gaogao");
        apiInfo.setApiInfo(apiInfoBean);
        call = dataService.getData(apiInfo);
        call.enqueue(new Callback<ResponseBody>() {
            /**
             * 接收到http回复时调用
             * 最好还要判断http给的回复是否是请求成功
             * http状态码在[200,300)之间时表示请求被成功接收
             * @param response
             */
            @Override
            public void onResponse(Response<ResponseBody> response) {
                String body = null;//获取返回体的字符串
                try {
                    body = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("Post Body", "get = :"+body);
            }

            /**
             * 网络异常或其他异常时调用
             * @param t
             */
            @Override
            public void onFailure(Throwable t) {
                Log.i("Post Body","Failure");
            }
        });
    }
}
