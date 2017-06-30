package com.example.administrator.recyclerviewtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 璁㈠崟甯冨眬-in
 * 浣滆�锛歠ly on 2016/8/24 0024 23:45
 * 閭锛歝ugb_feiyang@163.com
 */
public class ItemOrderIn implements OrderContent {

    private List<OrderGoods> list;
    private OrderGoods orderGoods;

    public ItemOrderIn(OrderGoods orderGoods) {
        this.orderGoods = orderGoods;
        list = new ArrayList<OrderGoods>();
        list.add(orderGoods);
    }

    @Override
    public int getLayout() {
        return R.layout.item_order_in;
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
        TextView nameTv = (TextView) convertView.findViewById(R.id.good_name);
        nameTv.setText(orderGoods.getGoodName());
        TextView snTv = (TextView) convertView.findViewById(R.id.good_sn);
        snTv.setText(orderGoods.getGoodSn());
        return convertView;
    }
}
