package com.example.gaoshengnan.mytestforgit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Observable;
import java.util.Observer;

import com.example.gaoshengnan.mytestforgit.picasso.PicassoTest;
import com.example.gaoshengnan.mytestforgit.rxjava.RxJavaTest;
import com.squareup.picasso.Picasso;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * 声明控件
     */
    private Button showDefView;
    private Button retrofitViewAsync;
    private Button retrofitViewSync;
    private Button annotationRepEnum;
    private Button annotationReflect;
    private Button webViewTest;
    private Button jsTest;
    private Button raJava;
    private Button picasso;
    private Retrofit retrofit;
    private Call<ResponseBody> call;
    private RetrofitService retrofitService;
    private DataService dataService;
    private AnnotationEnum annotationEnum;

    /**
     * 弱引用
     */
    private RxJavaTest rxJavaTest;
    private WeakReference<RxJavaTest> weakReference;

    /**
     * 自定义跳转协议
     */
    private static final String DEF_VIEW_URI = "test://gaogao/defView";
    private static final String RETROFIT2_TEST = "test://gaogao/Retrofit2";
    private static final String WEBVIEW_TEST = "test://gaogao/WebViewTest";
    private static final String JS_TEST = "test://gaogao/JSTest";
    private static final String RX_JAVA = "test://gaogao/RxJava";
    private static final String RX_PICASSO = "test://gaogao/Picasso";

    /**
     * Log打印的Tag
     */
    private static final String RXJAVA_TAG = "Rx_Java";

    /**
     * 图片uri
     */
    private static final String PIC_URI = "http://d.hiphotos.baidu.com/image/pic/item/8601a18b87d6277fcdb9b01d24381f30e924fc68.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化控件和布局
     */
    public void initView(){
        showDefView = findViewById(R.id.show_def_view);
        retrofitViewAsync = findViewById(R.id.retrofit2_async);
        retrofitViewSync = findViewById(R.id.retrofit2_sync);
        annotationRepEnum = findViewById(R.id.annotaition);
        //annotationReflect = findViewById(R.id.a)
        webViewTest = findViewById(R.id.webview_test);
        jsTest = findViewById(R.id.js_test);
        raJava = findViewById(R.id.rxjava_test);
        picasso = findViewById(R.id.picasso_test);
        retrofitViewAsync.setOnClickListener(this);
        retrofitViewSync.setOnClickListener(this);
        showDefView.setOnClickListener(this);
        annotationRepEnum.setOnClickListener(this);
        webViewTest.setOnClickListener(this);
        jsTest.setOnClickListener(this);
        raJava.setOnClickListener(this);
        picasso.setOnClickListener(this);

        annotationEnum = new AnnotationEnum(AnnotationEnum.WINTER);
    }

    /**
     * 控件点击事件
     * @param v
     */
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
                break;
            case R.id.rxjava_test:
                uri = Uri.parse(RX_JAVA);
                intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                downBitMapWithRxjava(PIC_URI);
                break;
            case R.id.picasso_test:
                uri = Uri.parse(RX_PICASSO);
                intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * Retrofit异步请求
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
     * Retrofit同步请求
     */
    public void SyncRetrofit(){
        try {
            call = retrofitService.getPage(1255723626);
            Response<ResponseBody> bodyResponse = call.execute();
            /**
             * 获取返回的字符串
             */
            String body = bodyResponse.body().string();
            Log.i("Synchronous Retrofit", "reponse = : "+body);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getData(){
        retrofit = new Retrofit.Builder()
                /**
                 * 暂定网址
                 */
                .baseUrl("http://WuXiaolong.me/")
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
                /**
                 * 获取返回体的字符串
                 */
                String body = null;
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

    public void downBitMapWithRxjava(final String uri){
        //图片地址判空
        if (null != uri) {
            io.reactivex.Observable<String> observable = io.reactivex.Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext(uri);
                }
            });
            io.reactivex.Observer<String> observer = new io.reactivex.Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.d(RXJAVA_TAG, "Observable and Observer have connected!");
                }

                @Override
                public void onNext(String value) {
                    Log.d(RXJAVA_TAG, "onNext");
                    Bitmap bitmap = BitmapFactory.decodeFile(uri);
                    weakReference = new WeakReference<>(rxJavaTest);
                    weakReference.get().setImage(bitmap);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(RXJAVA_TAG,"ERROR!");
                }

                @Override
                public void onComplete() {
                    Log.d(RXJAVA_TAG, "onCompleted!");
                }
            };
            observable
                    /**
                     * 指定subscribe发生的线程：io线程
                     */
                    .subscribeOn(Schedulers.io())
                    /**
                     * 指定subscriber的回调发生的线程：主线程
                     */
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {
            Log.e("RxJava", "Invalid Uri!");
        }
    }

}