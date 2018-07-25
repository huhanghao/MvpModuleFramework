package com.hhh.app_index.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.blankj.utilcode.util.ToastUtils;
import com.hhh.app_index.R;
import com.hhh.app_index.R2;
import com.hhh.lib_base.base_util_view.BottomUpSelectDialog;
import com.hhh.lib_base.base_util_view.PopupWindowAlert;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UtilViewSampleActivity extends AppCompatActivity {

    @BindView(R2.id.tv_button_bottom_up)
    View tvButtonBottomUp;

    @BindView(R2.id.tv_button_alert)
    View tvButtonAlert;

    @BindView(R2.id.tv_button_dialog1)
    View tvButtonDialog;

    private BottomUpSelectDialog mBottomUpSelectDialog;
    private PopupWindowAlert mPopupWindowAlert;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_sample_activity_util_view_sample);
        ButterKnife.bind(this);

        initBottomUpSelectorDialog();

        initPopupWindowAlert();

        initBottomDialog(UtilViewSampleActivity.this,R.layout.app_sample_bottom_dialog_view);

        initListener();

        String htmlStr = "<h2> hello world </h2> </br> <img src=http:\\/\\/zhongtie.dev.ynbsk.com\\/uploads\\/images\\/20180615\\/91acc5426f06afb5b9c7704233d761c4.jpg />";
    }

    private void initBottomDialog(Context context, int resourceId) {
        dialog = null;
        dialog = new Dialog(context, R.style.bottom_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(resourceId);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottom_dialog_animate);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = dm.widthPixels;
        window.setAttributes(lp);
    }

    private void initListener() {
        tvButtonBottomUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomUpSelectDialog.show();
            }
        });

        tvButtonAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindowAlert.setItemData("组织删除确认", "这是详情内容", "取消", "确定");
                mPopupWindowAlert.show();
            }
        });

        tvButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });



    }

    /**
     * 初始化selector
     */
    private void initBottomUpSelectorDialog() {
        ArrayList<String> nameList = new ArrayList<>();
        nameList.add("选项1");
        nameList.add("选项2");

        mBottomUpSelectDialog = new BottomUpSelectDialog(this, R.style.transparentFrameWindowStyle,
                new BottomUpSelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: {

                                ToastUtils.showShort("选择了  选项1");
                                break;
                            }
                            case 1: {

                                ToastUtils.showShort("选择了  选项2");
                                break;
                            }
                        }
                    }
                }, nameList);
    }

    /**
     *
     */
    public void initPopupWindowAlert() {
        mPopupWindowAlert = new PopupWindowAlert(this, false, new PopupWindowAlert.OnItemListener() {

            @Override
            public void result(int platform) {
                switch (platform) {
                    case 1:

                        mPopupWindowAlert.closePop();
                        break;
                    case 0:

                        mPopupWindowAlert.closePop();
                        break;
                }
            }
        });
    }




}
