package com.example.administrator.recyclerviewtest;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.recyclerviewtest.order.OrderContent;


public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {

    private Context mContext;
    private List<OrderContent> list;
    private LayoutInflater mIflater;
    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示


    public MyAdapter2(Context mContext, List<OrderContent> list, boolean hasMore) {
        this.mContext = mContext;
        this.list = list;
        this.hasMore = hasMore;
    }
    int times =0;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder =
                new MyViewHolder(list.get(viewType).getView(mContext, parent, mIflater));
        times++;
        Log.d("MyAdapter2", "times:" + times);
        return holder;
    }


    /**
     * ÿһ��λ�õ�item����Ϊ����һ��������
     * viewType ����Ϊposition
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

//        OrderContent content = list.get(position);
//        if(content instanceof ItemOrderTop){
//            return 0;
//        }
//        if(content instanceof ItemOrderBottom){
//            return 1;
//        }
//        return 2;
        return position;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    // 获取条目数量，之所以要加1是因为增加了一条footView
    @Override
    public int getItemCount() {
        return list.size();
    }
    // 自定义方法，获取列表中数据源的最后一个位置，比getItemCount少1，因为不计上footView
    public int getRealLastPosition() {
        return list.size()-1;
    }


    /**
     * �������
     */
    public void clearList(){
        this.list=new ArrayList<OrderContent>();
    }
    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }

    public List<OrderContent> getList(){
        return list;
    }
    public void setList(List<OrderContent> list){
        this.list = list;
    }
    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    public void updateList(List<OrderContent> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            list.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}

