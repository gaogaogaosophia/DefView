package com.example.gaoshengnan.mytestforgit.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gaoshengnan.mytestforgit.R;

import java.util.ArrayList;

/**
 * Created by gaoshengnan on 2017/12/20.
 */

public class RetrofitAdapter extends BaseAdapter implements View.OnClickListener{

    private static final int RETROFIT_OPERATION = 0;

    private static final int ITEM_TYPE_COUNT = 1;

    //private static final ArrayList<String> arrayList = new ArrayList<String>(5);

    private TextView textView;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (position > 5) {
            return position % 5;
        }
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return RETROFIT_OPERATION;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_TYPE_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            textView = parent.findViewById(R.id.retrofit2_listview_item);
            textView.setText(getItem(position).toString());
            textView.setOnClickListener(this);
            return textView;
        } else {
            return convertView;
        }
    }

    @Override
    public void onClick(View v) {
//        switch (v.) {
//            case :
//        }
    }
}
