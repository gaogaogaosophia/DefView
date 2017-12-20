package com.example.gaoshengnan.mytestforgit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.gaoshengnan.mytestforgit.adapter.RetrofitAdapter;

/**
 * Created by gaoshengnan on 2017/12/20.
 */

public class Retrofit2Test extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView(){
        setContentView(R.layout.retrofit_demo);
        ListView listView = findViewById(R.id.retrofit2_listview);
        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
        listView.setAdapter(retrofitAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
