package com.hhh.app_index;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

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
import com.hhh.lib_core.event.SampleMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @BindView(R2.id.tv_msg_show)
    TextView tvMsgShow;

    @BindView(R2.id.tv_button_5)
    View tvButton5;

    @BindView(R2.id.tv_button_6)
    View tvButton6;

    private TimePickerView pvTime;

    // 消息事件是否注册
    private boolean isRegistered = false;

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

        tvButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("已注册消息事件");
                if(!isRegistered){
                    isRegistered = true;
                    EventBus.getDefault().register(IndexActivity.this);
                }
            }
        });

        tvButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().unregister(IndexActivity.this);
                ToastUtils.showShort("已解绑册消息时间");
                isRegistered = false;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SampleMessageEvent event) {
        tvMsgShow.setText(event.getmMsg());
        ToastUtils.showShort("已接收消息");
    }

}
