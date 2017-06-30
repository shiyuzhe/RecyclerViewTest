package com.example.administrator.recyclerviewtest;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/6/21.
 */

public class MyPaddingDecoration extends RecyclerView.ItemDecoration {
    private int divider;

    public MyPaddingDecoration(Context context) {
        //即你要设置的分割线的宽度 --这里设为10dp
        divider = context.getResources().getDimensionPixelSize(R.dimen.divider);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        outRect.left = divider;  //相当于 设置 left padding
//        outRect.top = divider;   //相当于 设置 top padding
//        outRect.right = divider; //相当于 设置 right padding
        outRect.bottom = divider;  //相当于 设置 bottom padding
    }
}
