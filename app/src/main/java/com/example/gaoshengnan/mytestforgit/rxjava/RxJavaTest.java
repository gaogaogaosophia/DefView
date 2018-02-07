package com.example.gaoshengnan.mytestforgit.rxjava;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.gaoshengnan.mytestforgit.AnnotationEnum;
import com.example.gaoshengnan.mytestforgit.R;

/**
 * Created by gaoshengnan on 2018/2/6.
 */
public class RxJavaTest extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjavatest);
        initView();
    }

    public void initView(){
        imageView = findViewById(R.id.image);
        imageView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Congratulations! Picture Displayed!", Toast.LENGTH_SHORT);
    }

    public  void setImage(Bitmap bitmap){
        if (null != bitmap) imageView.setImageBitmap(bitmap);
    }

}
