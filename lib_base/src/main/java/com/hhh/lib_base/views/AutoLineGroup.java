package com.hhh.lib_base.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.hhh.lib_base.R;
import com.hhh.lib_core.utils.ResUtils;


/**
 * 自动换行的ViewGroup
 */
public class AutoLineGroup extends ViewGroup {
    private Boolean mIsEqualMode = true;  // 是否是等分模式
    private int mEqualSum = 1;      // 分隔数
    private int equalHeight;        // 等分高度
    private int equalWidth;         // 等分宽度
    private boolean mHasHorizontalDivider;      // 是否有水平等分先
    private boolean mHasverticalDivider;        // 是否有垂直等分析按
    private Paint mPaint;
    private int childCount;                     // 子view数量
    private OnChildClickListener mOnChildClickListener;

    public interface OnChildClickListener {
        void onChildClick(int pos, View view);
    }

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        mOnChildClickListener = onChildClickListener;
    }

    public AutoLineGroup(Context context) {
        super(context, null);
    }

    public AutoLineGroup(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        // 获取对应属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoLineGroup);
        mIsEqualMode = typedArray.getBoolean(R.styleable.AutoLineGroup_is_equal, true);
        mHasHorizontalDivider = typedArray.getBoolean(R.styleable.AutoLineGroup_has_horizontal_divider, false);
        mHasverticalDivider = typedArray.getBoolean(R.styleable.AutoLineGroup_has_vertical_divider, false);
        mEqualSum = typedArray.getInt(R.styleable.AutoLineGroup_equal_sum, 1);
        typedArray.recycle();

        // 当需要画divider的时候，我们启动ondraw的流程
        if (mHasHorizontalDivider | mHasverticalDivider) {
            setWillNotDraw(false);
        }
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

        childCount = getChildCount();

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
            if (dataExtra != 0) {
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
            equalWidth = width / mEqualSum;
            int midH = equalHeight / 2;
            int midW = equalWidth / 2;
            for (int i = 0; i < childCount; i++) {
                childView = getChildAt(i);
                if (View.GONE == childView.getVisibility()) {
                    continue;
                }

                // 设置view的点击
                childView.setTag(i);
                childView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnChildClickListener != null) {
                            int pos = (int) v.getTag();
                            mOnChildClickListener.onChildClick(pos, v);
                        }
                    }
                });

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

                if (childW > equalWidth) {
                    finalL = viewL;
                    finalR = viewR;
                } else {
                    finalL = midW + viewL - childW / 2;
                    finalR = midW + viewL + childW / 2;
                }

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


    @Override
    protected void onDraw(Canvas canvas) {
        if(mPaint==null){
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);
            mPaint.setColor(ResUtils.getColor(R.color.common_bg));
            mPaint.setStrokeWidth(1);
        }

        // 在等分情况下画分隔线
        if (mIsEqualMode) {
            if (mHasHorizontalDivider) {
                // 根据行数进行绘制
                for (int i = 0; i < childCount / mEqualSum; i++) {
                    canvas.drawLine(0, (i + 1) * equalHeight, getWidth(), (i + 1) * equalHeight, mPaint);
                }
            }

            if (mHasverticalDivider) {
                for (int i = 0; i < mEqualSum; i++) {
                    canvas.drawLine((i + 1) * equalWidth, 0, (i + 1) * equalWidth, getHeight(), mPaint);
                }
            }

        }
        super.onDraw(canvas);
    }
}
