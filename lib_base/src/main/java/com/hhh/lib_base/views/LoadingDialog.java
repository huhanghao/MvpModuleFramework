package com.hhh.lib_base.views;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhh.lib_base.R;


/**
 * 单例的loading对象，里面就维护一个Dialog,防止一个页面有多个dialog展示
 */
public class LoadingDialog {

    // 对象锁
    private static final Object SYNC_LOCK = new Object();

    // 用到的控件
    private static LoadingDialog mLoadingDialog;
    private static TextView tvContent;
    private static Dialog mDialog;


    private static Context mContext;

    public static LoadingDialog getInstance(Context context) {

        mContext = context;

        if (mLoadingDialog == null) {
            synchronized (SYNC_LOCK) {
                if (mLoadingDialog == null) {
                    mLoadingDialog = new LoadingDialog();
                }
            }
        }
        return mLoadingDialog;
    }

    /**
     * 显示加载对话框
     *
     * @param msg        对话框显示内容
     * @param cancelable 对话框是否可以取消
     * @return
     */
    public void showLoading(String msg, boolean cancelable) {
        mDialog = new Dialog(mContext, R.style.CustomProgressDialog);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);
        tvContent = (TextView) view.findViewById(R.id.tv_dialog_content);
        tvContent.setText(msg);

        mDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCanceledOnTouchOutside(cancelable);
        mDialog.show();
    }

    public void showLoading() {
        showLoading("加载中~",false);
    }

    /**
     * 关闭加载对话框
     */
    public void stopLoading() {
        if(mDialog!=null){
            mDialog.cancel();
        }
    }
}
