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
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class MyActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String Tag = "MainActivity";
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private RecyclerView recyclerView;
    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 10;
    private LinearLayoutManager linearLayoutManager;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private MyAdapter2 adapter2;
    private List<OrderContent> orderContents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        initRefreshLayout();
        initData();
        initRecyclerView2();
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

    @Override
    public void onRefresh() {
        // 设置可见
        mySwipeRefreshLayout.setRefreshing(true);
        // 重置adapter的数据源为空
        adapter2.clearList();
//        // 获取第第0条到第PAGE_COUNT（值为10）条的数据
        updateRecyclerView2(0, PAGE_COUNT);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 模拟网络加载时间，设置不可见
                mySwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }


    //嵌套recyclerview测试


    private void updateRecyclerView2(int fromIndex, int toIndex) {
        List<OrderContent> newDatas = getDatas2(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            adapter2.updateList(newDatas, true);
        } else {
            adapter2.updateList(null, false);
        }
    }
    private List<OrderContent> getDatas2(final int firstIndex, final int lastIndex) {
        List<OrderContent> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < orderContents.size()) {
                resList.add(orderContents.get(i));
            }
        }
        return resList;
    }
    /**
     * 设置数据
     */
    private void initData() {

//        List<OrderGoods> orderGoodses = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            OrderGoods orderGood = new OrderGoods();
            orderGood.setGoodName("商品" + i);
            orderGood.setGoodSn("商品SN" + i);
            ItemOrderIn orderIMiddle = new ItemOrderIn(orderGood);
            orderContents.add(orderIMiddle);
        }
//        //TODO 最外层数据-一般是从接口获取
//        List<Order> orderList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Order order = new Order();
//            orderList.add(order);
//        }
//        orderContents = new ArrayList<OrderContent>();
//        //外部第一个循环，将数据循环读取后存到订单顶部
//        for (int k = 0; k < orderList.size(); k++) {
//            List<OrderGoods> orderGoodses = new ArrayList<>();
//            for (int i = 0; i < k + 1; i++) {
//                OrderGoods orderGood = new OrderGoods();
//                orderGood.setGoodName("商品" + i);
//                orderGood.setGoodSn("商品SN" + i);
//                orderGoodses.add(orderGood);
//            }
//            //TODO 设置顶部数据-需要的数据直接传
//            ItemOrderTop itemOrderTop = new ItemOrderTop();
//            orderContents.add(itemOrderTop);
//            if (orderGoodses == null) {
//                //没有订单
//            } else {
//                //中间for循环，将数据循环读取后存到订单中间部分
//                //TODO 设置中间数据
//                for (int j = 0; j < orderGoodses.size(); j++) {
//                    OrderGoods goods = new OrderGoods();
//                    goods.setGoodName(orderGoodses.get(j).getGoodName());
//                    goods.setGoodSn(orderGoodses.get(j).getGoodSn());
//                    //需要的数据直接传
//                    ItemOrderIn orderIMiddle = new ItemOrderIn(goods);
//                    orderContents.add(orderIMiddle);
//                    Log.i("myLog", "orderContents =" + orderContents);
//                }
//            }
//            //外部第二个循环，将数据循环读取后存到订单底部
//            //TODO 设置底部数据-需要的数据直接传
//            ItemOrderBottom orderBottom = new ItemOrderBottom();
//            orderContents.add(orderBottom);
//
//        }
    }
    /**
     * 初始化View
     */
    private void initRecyclerView2() {
        adapter2 = new MyAdapter2(getApplicationContext(), getDatas2(0, PAGE_COUNT),true);
        linearLayoutManager=new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter2);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastVisibleItem  == adapter2.getRealLastPosition()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView2(adapter2.getRealLastPosition(), adapter2.getRealLastPosition() + PAGE_COUNT);
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
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.i(Tag, "onItemClick"+position);
                        List<OrderContent> list =   adapter2.getList();
                        orderContents.add( position+1,list.get(position));
                        list.add( position+1,list.get(position));
                        adapter2.setList(list);
                        adapter2.notifyItemInserted(position+1);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        List<OrderContent> list =   adapter2.getList();
                        Log.i(Tag, "onItemLongClick"+list.size()+"pos:"+position);
                        orderContents.remove(position);
                        list.remove(position);
                        Log.i(Tag, "onItemLongClick-del-"+list.size());
                        adapter2.setList(list);
                        adapter2.notifyItemRemoved(position);
                        adapter2.notifyItemRangeChanged(position,list.size()-position);
                    }
                }));
    }
}