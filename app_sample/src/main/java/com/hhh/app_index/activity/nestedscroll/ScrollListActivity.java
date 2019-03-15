package com.hhh.app_index.activity.nestedscroll;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.hhh.app_index.R;
import com.hhh.app_index.R2;
import com.hhh.app_index.adapter.ListItemAdapter;
import com.hhh.lib_base.XActivity;
import com.hhh.lib_base.base_mvp.IBasePresenter;
import com.hhh.lib_base.views.MyNestedScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 自定义list展示
 */
public class ScrollListActivity extends XActivity {

    @BindView(R2.id.rv_list)
    RecyclerView mRecyclerView;

    @BindView(R2.id.top_1)
    TextView top1;

    @BindView(R2.id.top_2)
    TextView top2;

    @BindView(R2.id.nsv_scroll)
    MyNestedScrollView nsvScroll;

    @BindView(R2.id.srfl_refreshLayout)
    SmartRefreshLayout srflRefreshLayout;

    private int pageCount = 0;
    private ArrayList<String> mDatas = new ArrayList<>();
    private ListItemAdapter adapter;

    @Override
    public IBasePresenter setPresenter() {
        return null;
    }

    @Override
    public void addView(Bundle savedInstanceState) {
        addMainView(R.layout.app_sample_activity_scroll_list);

        generateDatas();

        final View rootView = findViewById(android.R.id.content);

        //线性布局
        adapter = new ListItemAdapter(mDatas);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        adapter.bindToRecyclerView(mRecyclerView);


        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                View topView1 = findViewById(R.id.top_1);
                View topView2 = findViewById(R.id.top_2);

                nsvScroll.setMyScrollHeight(topView1.getHeight());
                nsvScroll.scrollTo(0, topView1.getHeight());
                int rvNewHeight = rootView.getHeight() - topView2.getHeight();

                mRecyclerView.setLayoutParams(new SmartRefreshLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, rvNewHeight));
            }
        });

        srflRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageCount = 0;
                generateDatas();
                adapter.notifyDataSetChanged();
            }
        });

        srflRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageCount++;
                generateDatas();
                adapter.notifyDataSetChanged();
            }
        });

        srflRefreshLayout.autoLoadMore();

//        mRecyclerView.setNestedScrollingEnabled(false);

    }

    private void generateDatas() {
        if(pageCount == 0){
            mDatas.clear();
            srflRefreshLayout.setEnableLoadMore(true);
        }
        if(pageCount == 10)
        {
            srflRefreshLayout.setEnableLoadMore(false);
        }

        for (int i = 0; i < 10; i++) {
            mDatas.add("第 " + i + " 个item");
        }
        srflRefreshLayout.finishRefresh();
        srflRefreshLayout.finishLoadMore();
    }

}
