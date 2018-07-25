package com.hhh.app_index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.hhh.app_index.Activity.UtilViewSampleActivity;
import com.hhh.app_index.Presenter.IndexSamplePresenter;
import com.hhh.app_index.V.IIndexActivityView;
import com.hhh.lib_base.XActivity;

import butterknife.BindView;


@Route(path = "/sample/index")
public class IndexActivity extends XActivity<IndexSamplePresenter> implements IIndexActivityView {

    @BindView(R2.id.tv_button_1)
    View tvButton1;

    @Override
    public void addView(Bundle savedInstanceState) {
        addMainView(R.layout.app_sample_activity_main);

        // 跳转至UtilView示例
        tvButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this,UtilViewSampleActivity.class);
                startActivity(intent);
            }
        });

        // 访问数据
        getP().getDataFromNet(this,"1","2");

    }

    @Override
    public IndexSamplePresenter newP() {
        return new IndexSamplePresenter();
    }

    @Override
    public void getSampleData(String sampleDataStr) {
        ToastUtils.showShort("sampleDataStr");
    }
}
