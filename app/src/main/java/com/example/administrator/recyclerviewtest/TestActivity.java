package com.example.administrator.recyclerviewtest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.recyclerviewtest.bean.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/6/28.
 */

public class TestActivity extends AppCompatActivity {
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<Data> dataList = new ArrayList<>();
    private TestAdapter adapter;
    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 10;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initRefreshLayout();
        initData();
        initRecyclerView();
    }

    private void findView() {
        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private void initRefreshLayout() {
        // 设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        mySwipeRefreshLayout.setProgressViewOffset(true, 50, 100);
        // 设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        mySwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mySwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<Data> list = getDataList(0, PAGE_COUNT);
                adapter.setDates(list);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 模拟网络加载时间，设置不可见
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }

        });
    }

    public List<Data> getDataList(int fristPosition,int lastPositon){
         List<Data> answer = new ArrayList<>();
        for(int i=fristPosition;i<=lastPositon;i++)
            if(dataList.size()>i)
                answer.add(dataList.get(i));
        return answer;
    }

    private void initData(){
        //header数据
        dataList.add(new Data());
        Random random = new Random();
        for(int i=0;i<40;i++){
            Data data = new Data();
            data.setContent("content"+i);
            int commandnum = random.nextInt(5);
            if(i==10)
                commandnum=40;
            List<String> commands = new ArrayList<>();
            for(int j=0;j<commandnum;j++){
                commands.add("commands"+i+"-"+j);
            }
            data.setCommands(commands);
            dataList.add(data);
        }
    }

    private void initRecyclerView() {
        adapter = new TestAdapter(getDataList(0,PAGE_COUNT),getApplicationContext());
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //未隐藏底部提示时,lastVisibleItem是footview,
                    if (adapter.isFadeTips() == false && lastVisibleItem + 1 == adapter.getItemCount()) {
                        Log.i("syz","lastVisibleItem+1:"+lastVisibleItem);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                    if (adapter.isFadeTips() == true && lastVisibleItem + 2 == adapter.getItemCount()) {
                        Log.i("syz","lastVisibleItem+2:"+lastVisibleItem);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
        //item整体点击效果
//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(recyclerView,
//                new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Data data = dataList.get(position);
//                        List<String> commands = data.getCommands();
//                        commands.add(0,"new command");
//                        data.setCommands(commands);
//                        adapter.notifyItemChanged(position);
//                    }
//
//                    @Override
//                    public void onItemLongClick(View view, int position) {
//                    }
//                }));
    }
    private void updateRecyclerView(int fromIndex, int toIndex) {
        List<Data> newDatas = getDataList(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            adapter.addDates(newDatas, true);
        } else {
            adapter.addDates(null, false);
        }
    }
}
