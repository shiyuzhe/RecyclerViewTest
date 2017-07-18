package com.example.administrator.recyclerviewtest;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.recyclerviewtest.bean.Data;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Data> dates;
    private Context context;    // 上下文Context
    private int normalType = 0;
    private int headerType = 1;
    private int footType = 2;       // 底部的提示View
    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示
    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler

    public TestAdapter(List<Data> dates, Context context){
        this.dates = dates;
        this.context = context;
    }

    public List<Data> getDates() {
        return dates;
    }

    public void setDates(List<Data> dates) {
        this.dates = dates;
        notifyDataSetChanged();
    }

    public void addDates(List<Data> newdates, boolean hasMore)  {
        if(newdates!=null)
        dates .addAll(newdates);
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return headerType;
        else   if (position == getItemCount() - 1)
            return footType;
        else
            return normalType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == headerType)
            return new headerHolder(LayoutInflater.from(context).inflate(R.layout.testheader,parent,false));
        else if(viewType == normalType)
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.testlayout, parent,false));
        else
            return  new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview, parent,false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof headerHolder){
            ((headerHolder)holder).header.setText("this is header!");
        }
        if(holder instanceof  ViewHolder){
            final Data data = dates.get(position);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            ((ViewHolder) holder).commands.setLayoutManager(linearLayoutManager);
            final Test2Adapter adapter = new Test2Adapter(data.getCommands(), context);
            ((ViewHolder) holder).commands.setAdapter(adapter);
            ((ViewHolder) holder).content.setText(data.getContent());
            ((ViewHolder) holder).content.setOnClickListener(new View.OnClickListener() {
                                                                 @Override
                                                                 public void onClick(View v) {

                                                                     List<String> commands = data.getCommands();
                                                                     commands.add(0, "new command");
                                                                     data.setCommands(commands);
                                                                     notifyItemChanged(position);
                                                                 }
                                                             }
            );
        }
        if(holder instanceof FootHolder){
            // 之所以要设置可见，是因为我在没有更多数据时会隐藏了这个footView
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”
            if (hasMore == true) {
                // 不隐藏footView提示
                fadeTips = false;
                if (dates.size() > 0) {
                    // 如果查询数据发现增加之后，就显示正在加载更多
                    ((FootHolder) holder).tips.setText("正在加载更多...");
                }
            } else {
                if (dates.size() > 0) {
                    // 如果查询数据发现并没有增加时，就显示没有更多数据了
                    ((FootHolder) holder).tips.setText("没有更多数据了");
                    // 然后通过延时加载模拟网络请求的时间，在500ms后执行
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 隐藏提示条
                            ((FootHolder) holder).tips.setVisibility(View.GONE);
                            // 将fadeTips设置true
                            fadeTips = true;
                            // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多
                            hasMore = true;
                        }
                    }, 500);
                }
            }
        }


    }

    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }

    @Override
    public int getItemCount() {
        return dates.size()+1;
    }

    public int getRealLastPosition() {
        return dates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        RecyclerView commands;

        public ViewHolder(View view) {
            super(view);
            content = (TextView) view.findViewById(R.id.content);
            commands = (RecyclerView) view.findViewById(R.id.commands);
        }
    }

    class headerHolder extends RecyclerView.ViewHolder {
        TextView header;
        public headerHolder(View view) {
            super(view);
            header = (TextView) view.findViewById(R.id.header);
        }
    }
    // // 底部footView的ViewHolder，用以缓存findView操作
    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;
        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.tips);
        }
    }


}
