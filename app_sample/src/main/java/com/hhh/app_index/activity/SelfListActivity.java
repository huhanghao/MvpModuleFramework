package com.hhh.app_index.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.hhh.app_index.R;
import com.hhh.app_index.R2;
import com.hhh.lib_base.XActivity;
import com.hhh.lib_base.base_mvp.IBasePresenter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 自定义list展示
 */
public class SelfListActivity extends XActivity {

    @BindView(R2.id.rv_list)
    RecyclerView rvList;

    private ArrayList<String> mDatas = new ArrayList<>();

    @Override
    public IBasePresenter setPresenter() {
        return null;
    }

    @Override
    public void addView(Bundle savedInstanceState) {
        addMainView(R.layout.app_sample_activity_self_list);


        generateDatas();

    }

    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }
}
