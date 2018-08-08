package com.hhh.lib_base;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhh.lib_base.base_mvp.IBasePresenter;
import com.hhh.lib_base.base_mvp.IBaseView;
import com.hhh.lib_base.views.LoadingDialog;
import com.hhh.lib_core.utils.ResUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @param <P>
 */
public abstract class XActivity<P extends IBasePresenter> extends RxAppCompatActivity implements IBaseView {

    //根view
    protected RelativeLayout mRootView;
    //内容view
    protected FrameLayout mContentView;
    //导航条
    protected Toolbar mToolbar;
    //居中title
    protected TextView mTvMidTitle;

    // butterKnif绑定后的返回对象
    private Unbinder mUnbinder;

    // mvp 中的p对象
    private P p;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); // 需要动画切换
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

        // 在用butterknif绑定前获取需要设置的View
        mRootView = (RelativeLayout) View.inflate(this, R.layout.activity_rootview, null);
        mToolbar = mRootView.findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(ResUtils.getColor(R.color.colorPrimary));
        mTvMidTitle = mRootView.findViewById(R.id.toolbar_mid_title);
        mContentView = mRootView.findViewById(R.id.content);
        super.setContentView(mRootView);

        // 把actvity放到栈管理中
        ActivityStackManager.getActivityManager().pushActivity(this);

        // 将toolBar显示到界面
        setSupportActionBar(mToolbar);

        // 设置toolbar,左上角的图片能够点击
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        // 设置默认选项
        setMidTitle(ResUtils.getString(R.string.app_name));

        // 用户写处理方法的入口
        addView(savedInstanceState);

        // 设置menu点击
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onMenuClicked(item.getItemId());
                return true;
            }
        });


    }

    /**
     * 获取presenter
     *
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
     * 将需要的布局添加到baseactivity中的内容布局中
     *
     * @param layoutId
     */
    public void addMainView(int layoutId) {
        if (layoutId > 0) {
            mContentView.removeAllViews();
            mContentView.addView(View.inflate(this, layoutId, null));
            mUnbinder = ButterKnife.bind(this);
        } else {
            try {
                throw new Exception("layout ID can not be 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /********************************设置toolbar相关内容********************************/


    protected void hideToolBar() {
        mToolbar.setVisibility(View.GONE);
    }


    /**
     * 设置toolBar的背景
     *
     * @param res
     */
    protected void setToolBarBg(int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mToolbar.setBackgroundColor(getColor(res));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mToolbar.setBackgroundColor(getResources().getColor(res, getTheme()));
        } else {
            mToolbar.setBackgroundColor(getResources().getColor(res));
        }
    }

    /**
     * 设置中间title
     *
     * @param str
     */
    protected void setMidTitle(String str) {
        setTitle("");
        mTvMidTitle.setVisibility(View.VISIBLE);
        mTvMidTitle.setText(str);
    }

    /**
     * 设置返回按钮
     */
    protected void setBackIcon() {
        mToolbar.setNavigationIcon(R.drawable.ic_back);
    }

    /**
     * 设置返回按钮样式
     *
     * @param resID
     */
    protected void setBackIcon(int resID) {
        mToolbar.setNavigationIcon(resID);
    }

    /**
     * 设置menu点击
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * menu点击
     *
     * @param id
     */
    protected void onMenuClicked(int id) {
    }

    /**
     * 在menu改变的时候需要重新刷新
     */
    protected void updateMenu() {
        invalidateOptionsMenu();
    }

    /********************************设置Loading相关********************************/
    @Override
    public void showLoading(String title) {
        showLoading(title, true);
    }

    @Override
    public void stopLoading() {
        LoadingDialog.getInstance(this).stopLoading();
    }

    public void showError(String msg) {
        mProgressDialog = new ProgressDialog(this);
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
        LoadingDialog.getInstance(this).showLoading(msg, canOutClick);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mUnbinder.unbind();
        if (getP() != null) {
            getP().detachV();
        }
        p = null;
        stopLoading();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.alpha_open,R.anim.alpha_close);
    }
}
