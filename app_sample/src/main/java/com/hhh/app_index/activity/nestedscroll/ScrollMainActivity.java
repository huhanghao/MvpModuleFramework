package com.hhh.app_index.activity.nestedscroll;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hhh.app_index.R;
import com.hhh.app_index.R2;
import com.hhh.lib_base.XActivity;
import com.hhh.lib_base.base_mvp.IBasePresenter;
import com.hhh.lib_base.views.CommonShapeButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义list展示
 */
public class ScrollMainActivity extends XActivity {


    @BindView(R2.id.csb_btn1)
    CommonShapeButton csbBtn1;

    @BindView(R2.id.csb_btn2)
    CommonShapeButton csbBtn2;

    @Override
    public IBasePresenter setPresenter() {
        return null;
    }

    @Override
    public void addView(Bundle savedInstanceState) {
        addMainView(R.layout.app_sample_activity_nest_scroll_main);

        csbBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScrollMainActivity.this, ScrollListActivity.class));
            }
        });

        csbBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
