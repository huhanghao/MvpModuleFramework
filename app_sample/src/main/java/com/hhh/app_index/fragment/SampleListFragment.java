package com.hhh.app_index.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hhh.app_index.R;
import com.hhh.app_index.R2;
import com.hhh.app_index.adapter.SampleListItemAdapter;
import com.hhh.app_index.presenter.GetListPresenter;
import com.hhh.app_index.v.IGetListInfoView;
import com.hhh.lib_base.XFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;


public class SampleListFragment extends XFragment<GetListPresenter> implements IGetListInfoView {

    @BindView(R2.id.srfl_refreshLayout)
    SmartRefreshLayout srflRefreshLayout;

    @BindView(R2.id.rv_list)
    RecyclerView rvList;

    private ArrayList<String> mStrList = new ArrayList<>();
    private int pageCount = 0;
    private int pageSum = 10;
    private SampleListItemAdapter mSampleListItemAdapter;


    @Override
    public GetListPresenter setPresenter() {
        return new GetListPresenter();

    }

    @Override
    public int getLayoutId() {
        return R.layout.app_sample_fragment_list;
    }

    @Override
    public void addView(Bundle savedInstanceState) {

        mSampleListItemAdapter = new SampleListItemAdapter(mStrList);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(mLinearLayoutManager);
        mSampleListItemAdapter.bindToRecyclerView(rvList);
        mSampleListItemAdapter.setEmptyView(R.layout.list_empty_view);


        srflRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageCount = 0;
                getP().getListStr(pageCount, pageSum);
            }
        });
        srflRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageCount++;
                getP().getListStr(pageCount, pageSum);
            }
        });
    }

    @Override
    public void returnListInfo(ArrayList<String> strList) {

        mStrList.addAll(strList);
        mSampleListItemAdapter.notifyDataSetChanged();

        if (pageCount == 0) {
            srflRefreshLayout.finishRefresh(1000);
        } else {
            srflRefreshLayout.finishLoadMore();
        }

    }
}
