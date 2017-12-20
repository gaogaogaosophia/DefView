package com.example.gaoshengnan.mytestforgit.tools;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by gaoshengnan on 2017/12/20.
 */

public class RetrofitListview extends ListView {
    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }

    public RetrofitListview(Context context) {
        super(context);
    }
}
