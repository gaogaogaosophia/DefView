package com.example.gaoshengnan.mytestforgit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button showDefView;

    //自定义跳转协议
    private static final String DEF_VIEW_URI = "test://gaogao/defView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        showDefView = findViewById(R.id.show_def_view);
        showDefView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_def_view:
                Uri uri = Uri.parse(DEF_VIEW_URI);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
