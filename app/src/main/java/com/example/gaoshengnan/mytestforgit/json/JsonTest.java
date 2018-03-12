package com.example.gaoshengnan.mytestforgit.json;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.example.gaoshengnan.mytestforgit.R;
import com.google.gson.Gson;

/**
 * Description:
 * 与JSON解析相关GSON/FAASTJSON/JACKSON的测试类
 * @author: gaoshengnan
 * @email: gaoshengnan@meituan.com
 * Date: 2018/3/12
 */

public class JsonTest extends AppCompatActivity implements View.OnClickListener {

    private static final String JSON_GSON = "test://gaogao/Gson";

    private static final String JSON_TAG = "JSON_TEST";

    private Button gsonTest;
    private Button fastJsonTest;
    private Button jacsonTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        init();
    }

    private void init() {
        gsonTest = findViewById(R.id.gson);
        fastJsonTest = findViewById(R.id.fastjson);
        jacsonTest = findViewById(R.id.jackson);

        gsonTest.setOnClickListener(this);
        fastJsonTest.setOnClickListener(this);
        jacsonTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gson:
                GsonTest();
                Log.i(JSON_TAG,"GSON_TEST");
                break;
            case R.id.fastjson:
                fastJsonTest();
                Log.i(JSON_TAG,"FASTJSON_TEST" );
                break;
            case R.id.jackson:
                jacksonTest();
                Log.i(JSON_TAG,"JACKSON_TEST");
                break;
            default:
                break;
        }
    }

    /**
     * GSON测试方法
     */
    private void GsonTest(){
        Gson gson = new Gson();
        String json = "{\"name\":\"gaogao\",\"age\":24,\"emailAddress\":\"xxxx@gmial.com\"}";
        User user = gson.fromJson(json, User.class);
        Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
    }

    /**
     * FASTJSON测试方法
     */
    private void fastJsonTest(){

    }

    /**
     * JACKSON测试方法
     * 暂时不需要
     */
    private void jacksonTest(){

    }

}
