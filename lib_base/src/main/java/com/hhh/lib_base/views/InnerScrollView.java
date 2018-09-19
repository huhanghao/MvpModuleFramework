package com.hhh.lib_base.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;


/**
 * scrollView 嵌套时内部使用的crollView
 * 当scroll在顶部的时候向下拖拽或是scroll在底部向上拖拽则父控件动
 */
public class InnerScrollView extends ScrollView {

    public static final int SCROLL_UP = 0x01; // ScrollView正在向上滑动
    public static final int SCROLL_DOWN = 0x10; // ScrollView正在向下滑动
    private static final int SCROLLLIMIT = 2;// 最小的滑动距离

    private int scrollDir = 0; // 滑动方向

    public InnerScrollView(Context context) {
        this(context, null);
    }

    public InnerScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InnerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        View contentView = getChildAt(0);

        // 向下滑动的时候判断是否在顶部
        if ((scrollDir == SCROLL_DOWN) && (getScrollY() < SCROLLLIMIT+1)) {
            getParent().requestDisallowInterceptTouchEvent(false);
            scrollDir = SCROLL_UP;
        } else if ((scrollDir == SCROLL_UP) && (contentView.getMeasuredHeight() <= getHeight() + getScrollY()+SCROLLLIMIT + 1)) {  // 向上滑动的时候判断是否到底
            getParent().requestDisallowInterceptTouchEvent(false);
            scrollDir = SCROLL_DOWN;
        } else {
            // 夺取父控件的拦截权利
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        // 根据滑动状态判断滑动的方向
        if (oldt > t && oldt - t > SCROLLLIMIT) {// 向下
            scrollDir = SCROLL_DOWN;
        } else if (oldt < t && t - oldt > SCROLLLIMIT) {// 向上
            scrollDir = SCROLL_UP;
        }
    }


}
