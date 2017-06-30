package com.example.administrator.recyclerviewtest;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String Tag = "MainActivity";
    private List<Fruit> fruits = new ArrayList<>();
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private RecyclerView recyclerView;
    private FruitAdapter adapter;
    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 10;
    private GridLayoutManager mLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initRefreshLayout();
        initFruit();
        initRecyclerView();

        //线性布局
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        //设置布局排列方向，默认纵向
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //瀑布流布局
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        //网格布局
//        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
    }

    private void initFruit() {
        for (int i = 0; i < 3; i++) {
            Fruit apple = new Fruit(getRamdomLengthname("apple"), R.mipmap.ic_launcher);
            fruits.add(apple);
            Fruit banana = new Fruit(getRamdomLengthname("banana"), R.mipmap.ic_launcher);
            fruits.add(banana);
            Fruit grape = new Fruit(getRamdomLengthname("grape"), R.mipmap.ic_launcher);
            fruits.add(grape);
            Fruit mango = new Fruit(getRamdomLengthname("mango"), R.mipmap.ic_launcher);
            fruits.add(mango);
            Fruit pineapple = new Fruit(getRamdomLengthname("pineapple"), R.mipmap.ic_launcher);
            fruits.add(pineapple);
        }
    }

    private String getRamdomLengthname(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(name);
        }
        return builder.toString();
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
        // 通过 setEnabled(false) 禁用下拉刷新
//       mySwipeRefreshLayout.setEnabled(false);
        // 设定下拉圆圈的背景
//       mySwipeRefreshLayout.setProgressBackgroundColor(R.color.colorAccent);
        // 设置手势下拉刷新的监听
        mySwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        adapter = new FruitAdapter(getDatas(0, PAGE_COUNT), this, getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //设置padding
        recyclerView.addItemDecoration(new MyPaddingDecoration(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //未隐藏底部提示时,lastVisibleItem是footview,
                    if (adapter.isFadeTips() == false && lastVisibleItem + 1 == adapter.getItemCount()) {
                        Log.i(Tag,"lastVisibleItem+1:"+lastVisibleItem);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (adapter.isFadeTips() == true && lastVisibleItem + 2 == adapter.getItemCount()) {
                        Log.i(Tag,"lastVisibleItem+2:"+lastVisibleItem);
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
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.i(Tag, "onItemClick");
                        List<Fruit> fruits = adapter.getmFruitList();
                        fruits.add(position,fruits.get(position));
                        adapter.setmFruitList(fruits);
                        adapter.notifyItemInserted(position);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        List<Fruit> fruits = adapter.getmFruitList();
                        fruits.remove(position);
                        adapter.setmFruitList(fruits);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position,fruits.size()-position);
                    }
                }));
    }

    private List<Fruit> getDatas(final int firstIndex, final int lastIndex) {
        List<Fruit> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < fruits.size()) {
                resList.add(fruits.get(i));
            }
        }
        return resList;
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        List<Fruit> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            adapter.updateList(newDatas, true);
        } else {
            adapter.updateList(null, false);
        }
    }

    @Override
    public void onRefresh() {
        // 设置可见
        mySwipeRefreshLayout.setRefreshing(true);
        // 重置adapter的数据源为
        adapter.resetDatas();
        // 获取第第0条到第PAGE_COUNT（值为10）条的数据
        updateRecyclerView(0, PAGE_COUNT);
//        adapter2.clearList();
//        // 获取第第0条到第PAGE_COUNT（值为10）条的数据
//        updateRecyclerView2(0, PAGE_COUNT);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 模拟网络加载时间，设置不可见
                mySwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }


}