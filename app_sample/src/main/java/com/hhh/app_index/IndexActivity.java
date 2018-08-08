package com.hhh.app_index;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.hhh.app_index.activity.FragmentSampleActivity;
import com.hhh.app_index.activity.UtilViewSampleActivity;
import com.hhh.app_index.presenter.IndexSamplePresenter;
import com.hhh.app_index.v.IIndexActivityView;
import com.hhh.lib_base.XActivity;

import java.util.Date;

import butterknife.BindView;


@Route(path = "/sample/index")
public class IndexActivity extends XActivity<IndexSamplePresenter> implements IIndexActivityView {

    @BindView(R2.id.tv_button_1)
    View tvButton1;

    @BindView(R2.id.tv_button_2)
    View tvButton2;

    @BindView(R2.id.tv_button_3)
    View tvButton3;

    @BindView(R2.id.tv_button_4)
    View tvButton4;

    private TimePickerView pvTime;

    @Override
    public void addView(Bundle savedInstanceState) {
        addMainView(R.layout.app_sample_activity_main);
        setBackIcon();
        setMidTitle("IndexActivity");

        initTimePicker();

        initListener();
    }


    private void initTimePicker() {
        //时间选择器
        pvTime = new TimePickerBuilder(IndexActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                ToastUtils.showShort(date.toString());
            }
        }).build();
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

        tvButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, FragmentSampleActivity.class);
                startActivity(intent);
            }
        });

        tvButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });
    }

    @Override
    public void returnSampleData(String sampleDataStr) {
        ToastUtils.showShort("获取SampleData");
    }


    @Override
    public void returnBaseInfo(String baseInfo) {
        ToastUtils.showShort(baseInfo);
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
