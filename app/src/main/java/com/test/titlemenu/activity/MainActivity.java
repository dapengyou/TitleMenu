package com.test.titlemenu.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.test.titlemenu.R;
import com.test.titlemenu.VirtualData;
import com.test.titlemenu.adapter.MainAdapter;
import com.test.titlemenu.bean.MainListBean;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MainAdapter mainAdapter;
    protected boolean isRefresh = true;

    private View headerLayout;
    private int mHeaderHeight;//获得header高度

    private View mRlTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRlTitle = findViewById(R.id.tl_title);

        headerLayout = getLayoutInflater().inflate(R.layout.item_header, null, false);

        headerLayout.measure(0, 0);
        //getMeasuredHeight()返回的是原始测量高度，与屏幕无关，getHeight()返回的是在屏幕上显示的高度
        mHeaderHeight = headerLayout.getMeasuredHeight();
    }

    private void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //设置SwipeRefreshLayout是否可用
        mSwipeRefreshLayout.setEnabled(true);

        mainAdapter = new MainAdapter(VirtualData.getDatas(20));
        //初始化时候为了不被遮盖住  添加一个等高的header
        mainAdapter.addHeaderView(headerLayout);
        mainAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mainAdapter);

    }

    private void setListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (getScollYDistance() - mHeaderHeight >= 0) {
                    mRlTitle.setVisibility(View.VISIBLE);
                } else {
                    mRlTitle.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 计算滚动距离
     *
     * @return
     */
    private int getScollYDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

    @Override
    public void onRefresh() {
//        isRefresh = true;
//        mainAdapter.setEnableLoadMore(false);
        mSwipeRefreshLayout.setEnabled(false);
//        getData();
    }

    @Override
    public void onLoadMoreRequested() {
//        mSwipeRefreshLayout.setEnabled(false);
//        isRefresh = false;
//        mSwipeRefreshLayout.setEnabled(true);
//        mainAdapter.loadMoreEnd();
        mainAdapter.setEnableLoadMore(false);
//        getData();
    }

    /**
     * 获取数据
     */
    private void getData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mainAdapter.setEnableLoadMore(true);
                    mainAdapter.setNewData(VirtualData.getDatas(10));
                } else {
                    mSwipeRefreshLayout.setEnabled(true);
                    List<MainListBean> list = VirtualData.getDatas(10);
                    mainAdapter.addData(list);
                    if (list.size() >= 10) {
                        mainAdapter.loadMoreComplete();
                    } else if (list.size() > 5) {
                        mainAdapter.loadMoreEnd();
                    } else {
                        mainAdapter.loadMoreFail();
                    }
                }
            }
        }, 1000);
    }

}
