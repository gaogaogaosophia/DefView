package com.example.gaoshengnan.mytestforgit.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gaoshengnan.mytestforgit.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.lang.ref.WeakReference;

/**
 * Description:
 *
 * @author: gaoshengnan
 * @email: gaoshengnan@meituan.com
 * Date: 2018/3/8
 */

public class PicassoTest extends AppCompatActivity implements View.OnClickListener {


    private ImageView imageView;
    private Button picassoNative;
    private Button picassoUri;
    private Button picassoTranformation;

    private PicassoTest picassoTest;

    private static final String PICASSO_TAG = "PICASSO";
    private static final String PIC_URI = "http://d.hiphotos.baidu.com/image/pic/item/8601a18b87d6277fcdb9b01d24381f30e924fc68.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        imageView = findViewById(R.id.imageView);
        picassoNative = findViewById(R.id.picasso_native);
        picassoUri = findViewById(R.id.picasso_uri);
        picassoTranformation = findViewById(R.id.picasso_transformation);

        picassoNative.setOnClickListener(this);
        picassoUri.setOnClickListener(this);
        picassoTranformation.setOnClickListener(this);
    }

    /**
     * 用Picasso下载展示Uri图片
     */
    private void downloadPicWithUri() {
        if (imageView != null) {
            Log.i(PICASSO_TAG, "Downloading Pic!");
            Picasso.with(this).load(PIC_URI).into(imageView);
        } else {
            Log.e(PICASSO_TAG, "ImageView Is Null");
        }
    }

    /**
     * 用Picasso下载展示Native图片
     */
    private void downloadPicWithNative() {
        if (imageView != null) {
            Log.i(PICASSO_TAG, "Downloading Pic!");
            Picasso.with(this).load(R.drawable.beautifulscenery).into(imageView);
        } else {
            Log.e(PICASSO_TAG, "ImageView Is Null");
        }
    }

    /**
     * 用Picasso下载展示本地变换后的图片
     */
    private void downloadPicWithTransformation() {
        if (imageView != null) {
            /**
             * Target是Picasso中下载图片的回调接口
             */
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Log.i(PICASSO_TAG,"Picasso加载成功");
                    imageView.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.e(PICASSO_TAG,"Picasso加载失败");
                    if (null != errorDrawable) {
                        imageView.setImageDrawable(errorDrawable);
                    } else {
                        imageView.setImageResource(R.drawable.errordefault);
                    }
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    Log.i(PICASSO_TAG,"Picasso正在加载");
                }
            };
            imageView.setTag(target);
            Picasso.with(this).load(R.drawable.beautifulscenery).transform(new MyTransformation(this)).into(target);
        } else {
            Log.e(PICASSO_TAG, "ImageView Is Null");
        }
    }

    /**
     * Button点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.picasso_native:
                downloadPicWithNative();
                break;
            case R.id.picasso_uri:
                downloadPicWithUri();
                break;
            case R.id.picasso_transformation:
                downloadPicWithTransformation();
                break;
            default:
                break;
        }
    }

    /**
     * 自定义Picasso转换器
     * 效果：高斯模糊
     */
    public static class MyTransformation implements Transformation {

        /**
         * 渲染脚本
         */
        RenderScript renderScript;

        MyTransformation(Context context) {
            super();
            renderScript = RenderScript.create(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public Bitmap transform(Bitmap bitmap) {
            Bitmap blurredBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

            //为脚本分配内存
            Allocation input = Allocation.createFromBitmap(renderScript, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
            Allocation output = Allocation.createTyped(renderScript, input.getType());

            //加载脚本（固有高斯模糊滤波器）实例
            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
            script.setInput(input);

            // 设置模糊度
            script.setRadius(25);

            //开始使用滤波器
            script.forEach(output);

            output.copyTo(blurredBitmap);

            bitmap.recycle();

            return blurredBitmap;

        }

        @Override
        public String key() {
            return "MyTransformation";
        }
    }
}
