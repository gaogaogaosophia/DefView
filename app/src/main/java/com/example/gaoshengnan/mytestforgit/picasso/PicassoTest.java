package com.example.gaoshengnan.mytestforgit.picasso;

import android.content.Context;
import android.graphics.Bitmap;
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

import com.example.gaoshengnan.mytestforgit.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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
     * 用Picasso下载展示Native变换后的图片
     */
    private void downloadPicWithTransformation() {
        if (imageView != null) {
            Log.i(PICASSO_TAG, "Downloading Pic!");
            Picasso.with(this).load(R.drawable.beautifulscenery).transform(new MyTransformation(this)).into(imageView);
        } else {
            Log.e(PICASSO_TAG, "ImageView Is Null");
        }
    }

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

    public static class MyTransformation implements Transformation {

        RenderScript renderScript;

        public MyTransformation(Context context) {
            super();
            renderScript = RenderScript.create(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public Bitmap transform(Bitmap bitmap) {
            Bitmap blurredBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

            // Allocate memory for Renderscript to work with
            Allocation input = Allocation.createFromBitmap(renderScript, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
            Allocation output = Allocation.createTyped(renderScript, input.getType());

            // Load up an instance of the specific script that we want to use.
            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
            script.setInput(input);

            // Set the blur radius
            script.setRadius(25);

            // Start the ScriptIntrinisicBlur
            script.forEach(output);

            // Copy the output to the blurred bitmap
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
