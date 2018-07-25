package com.hhh.lib_base;

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
import com.hhh.lib_core.utils.ResUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        // 在用butterknif绑定前获取需要设置的View
        mRootView = (RelativeLayout) View.inflate(this, R.layout.activity_rootview, null);
        mToolbar = mRootView.findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(ResUtils.getColor(R.color.colorPrimary));
        mTvMidTitle = mRootView.findViewById(R.id.toolbar_mid_title);
        mContentView = mRootView.findViewById(R.id.content);
        super.setContentView(mRootView);

        // 把actvity放到栈管理中
        ActivityManager.getActivityManager().pushActivity(this);

        // 将toolBar显示到界面
        setSupportActionBar(mToolbar);

        // 设置toolbar,左上角的图片能够点击
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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

        // 设置默认选项
        setMidTitle(ResUtils.getString(R.string.app_name));

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

    protected void setToolBarBg(int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mToolbar.setBackgroundColor(getColor(res));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mToolbar.setBackgroundColor(getResources().getColor(res, getTheme()));
        } else {
            mToolbar.setBackgroundColor(getResources().getColor(res));
        }
    }

    protected void setMidTitle(String str) {
        setTitle("");
        mTvMidTitle.setVisibility(View.VISIBLE);
        mTvMidTitle.setText(str);
    }

    protected void setBackIcon() {
        mToolbar.setNavigationIcon(R.drawable.ic_back);
    }

    protected void setBackIcon(int resID) {
        mToolbar.setNavigationIcon(resID);
    }

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

    }

    @Override
    public void stopLoading() {

    }

    public void showError(String msg) {

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
}
