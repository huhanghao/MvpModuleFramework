package com.hhh.lib_base.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhh.lib_base.R;


/**
 * 自动换行的ViewGroup
 */
public class AutoLineGroup extends ViewGroup {

    private Boolean mIsEqualMode = true;  // 是否是等分模式
    private int mEqualSum = 1;
    private LayoutInflater mInflater;
    private int equalHeight;

    public AutoLineGroup(Context context) {
        super(context, null);
    }

    public AutoLineGroup(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoLineGroup);
        mIsEqualMode = typedArray.getBoolean(R.styleable.AutoLineGroup_is_equal, true);
        mEqualSum = typedArray.getInt(R.styleable.AutoLineGroup_equal_sum, 1);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasure = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getSize(heightMeasureSpec);

        int maxLineWidth = 0;
        int totalHeight = 0;
        int curLineWidth = 0;
        int curLineHeight = 0;

        int chlidWidth;
        int childHeight;
        View childView = null;

        int childCount = getChildCount();

        // 在等分的情况下，高度只由数量决定;  在非等分资情况下，要去一个一个测量
        if (mIsEqualMode) {
            for (int i = 0; i < childCount; i++) {

                childView = getChildAt(i);
                if (View.GONE == childView.getVisibility()) {
                    continue;
                }
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                childHeight = childView.getMeasuredHeight();

                maxLineWidth = Math.max(maxLineWidth, childHeight);
            }
            equalHeight = maxLineWidth;
            // 找到最大的孩子的高度作为需要的高度
            int count = 0;
            count = childCount / mEqualSum;
            int dataExtra = childCount % mEqualSum;
            if(dataExtra!= 0 ){
                count++;
            }
            totalHeight = count * maxLineWidth;

        } else {
            // 根据孩子个数计算高度
            for (int i = 0; i < childCount; i++) {

                childView = getChildAt(i);
                if (View.GONE == childView.getVisibility()) {
                    continue;
                }
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                chlidWidth = childView.getMeasuredWidth();
                childHeight = childView.getMeasuredHeight();

                if ((curLineWidth + chlidWidth) > (widthMeasure - getPaddingLeft() - getPaddingRight())) {
                    maxLineWidth = Math.max(maxLineWidth, curLineWidth);
                    totalHeight += curLineHeight;
                    curLineWidth = chlidWidth;
                    curLineHeight = childHeight;
                } else {
                    curLineWidth += chlidWidth;
                    curLineHeight = Math.max(curLineHeight, childHeight);
                }

                if (i == childCount - 1) {
                    maxLineWidth = Math.max(maxLineWidth, curLineHeight);
                    totalHeight += childHeight;
                }
            }
        }

        setMeasuredDimension(widthMode != MeasureSpec.EXACTLY ? maxLineWidth + getPaddingLeft() + getPaddingRight() : widthMeasure,
                heightMode != MeasureSpec.EXACTLY ? totalHeight + getPaddingTop() + getPaddingBottom() : heightMeasure);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View childView = null;

        int childCount = getChildCount();
        int width = getWidth();
        int rightLimit = width - getPaddingRight();

        int baseLeft = 0 + getPaddingLeft();
        int baseTop = 0 + getPaddingTop();

        int curLeft = baseLeft;
        int curTop = baseTop;

        int viewL, viewT, viewR, viewB;
        int finalL, finalR;
        int childWidth;
        int childHeight;

        int childW, childH;

        int lastChildHeight = 0;

        // 等分的情况
        if (mIsEqualMode) {
            int count = 0;
            int equalWidth = width / mEqualSum;
            int midH = equalHeight / 2;
            int midW = equalWidth / 2;
            for (int i = 0; i < childCount; i++) {
                childView = getChildAt(i);
                if (View.GONE == childView.getVisibility()) {
                    continue;
                }
                // 获取child的宽高
                childW = childView.getMeasuredWidth();
                childH = childView.getMeasuredHeight();

                if (count % mEqualSum == 0) {
                    if (i != 0) {
                        curTop = curTop + equalHeight;
                    }
                    viewL = baseLeft;
                    viewT = curTop;
                    viewR = viewL + equalWidth;
                    viewB = viewT + equalHeight;
                    curLeft = baseLeft + equalWidth;
                } else {
                    viewL = curLeft;
                    viewT = curTop;
                    viewR = viewL + equalWidth;
                    viewB = viewT + equalHeight;
                    curLeft = curLeft + equalWidth;
                }
                // 让child的布局位于等分布局中间
                finalL = midW + viewL - childW / 2;
                finalR = midW + viewL + childW / 2;
                childView.layout(finalL, viewT, finalR, viewB);


                count++;
            }

        } else {
            for (int i = 0; i < childCount; i++) {
                childView = getChildAt(i);
                if (View.GONE == childView.getVisibility()) {
                    continue;
                }

                // 获取child的宽高
                childW = childView.getMeasuredWidth();
                childH = childView.getMeasuredHeight();

                childWidth = childW;
                childHeight = childH;

                if (curLeft + childWidth > rightLimit) {
                    curTop = curTop + lastChildHeight;
                    viewL = baseLeft;
                    viewT = curTop;
                    viewR = viewL + childW;
                    viewB = viewT + childH;
                    curLeft = baseLeft + childWidth;
                } else {
                    viewL = curLeft;
                    viewT = curTop;
                    viewR = viewL + childW;
                    viewB = viewT + childH;
                    curLeft = curLeft + childWidth;
                }

                lastChildHeight = childHeight;
                childView.layout(viewL, viewT, viewR, viewB);
            }

        }
    }
}
