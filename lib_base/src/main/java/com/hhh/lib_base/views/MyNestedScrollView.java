package com.hhh.lib_base.views;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * 原理参考：https://juejin.im/entry/5ab9e7fc5188255d5f3d4b55
 */
public class MyNestedScrollView extends NestedScrollView {
    private String TAG = MyNestedScrollView.class.getSimpleName();
    /**
     * 该控件滑动的高度，高于这个高度后交给子滑动控件
     */
    int mParentScrollHeight;
    int mScrollY;

    public MyNestedScrollView(Context context) {
        this(context,null);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMyScrollHeight(int scrollLength) {
        this.mParentScrollHeight = scrollLength;
    }

    /**
     * 子控件告诉父控件 开始滑动了
     *
     * @param target
     * @param dx
     * @param dy
     * @param consumed 如果有就返回true
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
        // dy<0 为向下滑动  dy>0 为想上滑动
        if(mScrollY == 0 && dy < 0){
            // 向下且mScrollY=0的情况下则不消耗距离，全部让子控件处理
            return;
        }

        if (mScrollY < mParentScrollHeight) {
            consumed[0] = dx;
            consumed[1] = dy;
            scrollBy(0, dy);
        }
        Log.d(TAG, "------------onNestedPreScroll------------ ");
        Log.d(TAG, "dx " + dx + " dy " + dy + " " + consumed[0] + " " + consumed[1] + " scrollY " + mScrollY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mScrollY = t;
        Log.d(TAG, "------------onScrollChanged------------ ");
        Log.d(TAG, "mScrollY " + mScrollY);
    }
}
