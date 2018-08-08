package com.hhh.lib_base.views;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhh.lib_base.R;
import com.hhh.lib_base.R2;


/**
 * Desc 通用弹出对话框
 * Created by  zl .
 * Time: 2016/9/28  17
 */
public class PopupWindowAlert {

    LinearLayout llRootView;


    private Context context;

    public static final int OK = 1;
    public static final int CANCEL = 0;

    private OnItemListener mOnItemListener;
    private AlertDialog pop;
    private boolean canOutSideClick = true;
    private TextView tv_dialog_title;
    private TextView tv_pop_content;
    private Button btn_no;
    private Button btn_yes;
    private ImageView iv_thumb;
    private LinearLayout ll_root_view;


    public interface OnItemListener {
        void result(int platform);

    }

    public PopupWindowAlert(Context context, OnItemListener mOnItemListener) {
        this.context = context;
        this.mOnItemListener = mOnItemListener;
        initViews();
    }

    /**
     * @param context         上下文
     * @param canOutSideClick 外部点击是否消失
     * @param mOnItemListener 点击监听
     */
    public PopupWindowAlert(Context context, boolean canOutSideClick, OnItemListener mOnItemListener) {
        this.context = context;
        this.mOnItemListener = mOnItemListener;
        this.canOutSideClick = canOutSideClick;
        initViews();
    }

    private void initViews() {

        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_dialog, null);

        tv_dialog_title = view.findViewById(R.id.tv_dialog_title);
        tv_pop_content = view.findViewById(R.id.tv_pop_content);
        btn_no = view.findViewById(R.id.btn_no);
        btn_yes = view.findViewById(R.id.btn_yes);
        iv_thumb = view.findViewById(R.id.iv_thumb);
        ll_root_view = view.findViewById(R.id.ll_root_view);

        btn_no.setOnClickListener(listener);
        btn_yes.setOnClickListener(listener);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(canOutSideClick);
        builder.setView(view);

        pop = builder.create();

        // 解决AlertDialog中edittext不显示输入框的问题
        pop.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }


    /**
     * 设置POPwindow值
     *
     * @param title   对话框标题
     * @param content 对话框内容
     */
    public void setItemData(String title, String content, String tv_no, String tv_yes, String iv_thumb_url) {
        tv_dialog_title.setText(title);
        tv_pop_content.setText(content);

        setButtonVisible(tv_no, tv_yes);

    }

    /**
     * 设置POPwindow值
     *
     * @param title       对话框标题
     * @param contentView 填充到内部的view
     */
    public void setItemData(String title, View contentView, String tv_no, String tv_yes) {
        tv_dialog_title.setText(title);
        llRootView.removeAllViews();
        llRootView.addView(contentView);

        setButtonVisible(tv_no, tv_yes);
    }

    /**
     * 设置POPwindow值
     *
     * @param title   对话框标题
     * @param content 对话框内容
     */
    public void setItemData(String title, String content, String tv_no, String tv_yes) {
        tv_dialog_title.setText(title);
        tv_pop_content.setText(content);

        setButtonVisible(tv_no, tv_yes);
    }

    /**
     * 根据yes，no 展示按钮
     * @param tv_no
     * @param tv_yes
     */
    private void setButtonVisible(String tv_no, String tv_yes) {
        btn_no.setText(tv_no);
        btn_yes.setText(tv_yes);
        if (TextUtils.isEmpty(tv_no)) {
            btn_no.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(tv_yes)) {
            btn_yes.setVisibility(View.GONE);
        }
    }



    public void closePop() {
        if (pop != null) {
            pop.dismiss();
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closePop();
            switch (v.getId()) {
                case R2.id.btn_no:
                    mOnItemListener.result(CANCEL);
                    break;
                case R2.id.btn_yes:
                    mOnItemListener.result(OK);
                    break;
            }
        }
    };

    public void show(){
        pop.show();
    }
}
