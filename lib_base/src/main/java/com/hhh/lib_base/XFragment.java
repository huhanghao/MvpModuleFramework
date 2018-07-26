package com.hhh.lib_base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhh.lib_base.base_mvp.IBasePresenter;
import com.hhh.lib_base.base_mvp.IBaseView;
import com.hhh.lib_base.base_util_view.LoadingDialog;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class XFragment<P extends IBasePresenter> extends RxFragment implements IBaseView {
    protected P p;
    private View rootView;
    protected LayoutInflater layoutInflater;
    private Unbinder unbinder;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 生成布局
        layoutInflater = inflater;
        if (rootView == null && getLayoutId() > 0) {
            rootView = inflater.inflate(getLayoutId(), null);
        }
        // 通过butterKnif绑定view
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 处理主流程入口
        addView(savedInstanceState);
    }

    /**
     * 获取presenter
     * @return
     */
    protected P getP() {
        if (p == null) {
            p = setPresenter();
            if (p != null) {
                p.attachV(this);
            }
        }
        return p;
    }

    /**
     * 设置presenter
     *
     * @return
     */
    public abstract P setPresenter();


    /**
     * 获取布局id
     *
     * @return
     */
    public abstract int getLayoutId();


    @Override
    public void showLoading(String title) {
        showLoading(title, true);
    }

    @Override
    public void stopLoading() {
        LoadingDialog.getInstance(getActivity()).stopLoading();
    }

    public void showError(String msg) {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle("出现异常");
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.setCancelable(true); // 能够返回
        mProgressDialog.show();
    }


    /**
     * loading
     */
    public void showLoading(String msg, boolean canOutClick) {
        LoadingDialog.getInstance(getActivity()).showLoading(msg, canOutClick);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (p != null) {
            p.detachV();
        }
        unbinder.unbind();
    }


}
