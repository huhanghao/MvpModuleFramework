package com.hhh.app_index;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.hhh.app_index.activity.UtilViewSampleActivity;
import com.hhh.app_index.presenter.IndexSamplePresenter;
import com.hhh.app_index.v.IIndexActivityView;
import com.hhh.lib_base.XActivity;

import butterknife.BindView;


@Route(path = "/sample/index")
public class IndexActivity extends XActivity<IndexSamplePresenter> implements IIndexActivityView {

    @BindView(R2.id.tv_button_1)
    View tvButton1;

    @BindView(R2.id.tv_button_2)
    View tvButton2;

    @Override
    public void addView(Bundle savedInstanceState) {
        addMainView(R.layout.app_sample_activity_main);
        setBackIcon();
        setMidTitle("IndexActivity");

        initListener();
    }

    private void initListener() {

        // 跳转至UtilView示例
        tvButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, UtilViewSampleActivity.class);
                startActivity(intent);
            }
        });

        tvButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 访问数据
                getP().getDataFromNet(IndexActivity.this, "gaozeqiu", "123456");
            }
        });
    }

    @Override
    public void returnSampleData(String sampleDataStr) {
        ToastUtils.showShort("获取SampleData");
    }


    @Override
    public void returnBaseInfo(String baseInfo) {

    }

    @Override
    public IndexSamplePresenter setPresenter() {
        return new IndexSamplePresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_sample_send_menu, menu);
        return true;
    }

    @Override
    protected void onMenuClicked(int id) {
        if (id == R.id.send) {
            ToastUtils.showShort("点击menu");
        }
    }
}
