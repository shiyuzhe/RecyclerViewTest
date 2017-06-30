package com.example.administrator.recyclerviewtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 璁㈠崟甯冨眬-in
 * 浣滆�锛歠ly on 2016/8/24 0024 23:45
 * 閭锛歝ugb_feiyang@163.com
 */
public class ItemOrderBottom implements OrderContent {

    public ItemOrderBottom() {
    }

    @Override
    public int getLayout() {
        return R.layout.item_order_bottom;
    }

    @Override
    public boolean isClickAble() {
        return true;
    }

    @Override
    public View getView(Context mContext, View convertView, LayoutInflater inflater) {
        inflater = LayoutInflater.from(mContext);
        convertView =  inflater.inflate(getLayout(),null);
        //TODO 鏁版嵁灞曠ず-璁㈠崟鍐呭
        return convertView;
    }
}
