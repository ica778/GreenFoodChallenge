package com.soyiz.greenfoodchallenge;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Integer[] resIds;

    public ImageAdapter(Context mContext, Integer[] resIds) {
        this.mContext = mContext;
        this.resIds = resIds;
    }

    @Override
    public int getCount() {
        return resIds.length;
    }

    @Override
    public Object getItem(int i) {
        return resIds[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            ImageView iv = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
            params.gravity = Gravity.CENTER;
            iv.setLayoutParams(params);
            view = iv;
        }
        ((ImageView) view).setImageResource(resIds[i]);
        return view;
    }
}