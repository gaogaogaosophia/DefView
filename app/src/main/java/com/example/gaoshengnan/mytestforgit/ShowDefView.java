package com.example.gaoshengnan.mytestforgit;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by gaoshengnan on 2017/12/13.
 */

public class ShowDefView extends Activity {
    private DefView defView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.def_view);
        defView = findViewById(R.id.def_view);
        defView.setPaintColor(Color.RED);
    }
}
