package com.hhh.lib_base.views;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import com.hhh.lib_base.R;

import java.lang.reflect.Field;

public class CommonShapeButton extends AppCompatButton {

    private int TOP_LEFT = 1;
    private int TOP_RIGHT = 2;
    private int BOTTOM_RIGHT = 4;
    private int BOTTOM_LEFT = 8;

    /**
     * shape模式
     * 矩形（rectangle）、椭圆形(oval)、线形(line)、环形(ring)
     */
    private int mShapeMode = 0;

    /**
     * 填充颜色
     */
    private int mFillColor = 0;

    /**
     * 按压颜色
     */
    private int mPressedColor = 0;

    /**
     * 描边颜色
     */
    private int mStrokeColor = 0;

    /**
     * 描边宽度
     */
    private int mStrokeWidth = 0;

    /**
     * 圆角半径
     */
    private int mCornerRadius = 0;
    /**
     * 圆角位置
     * topLeft、topRight、bottomRight、bottomLeft
     */
    private int mCornerPosition = -1;

    /**
     * 点击动效
     */
    private boolean mActiveEnable = false;

    /**
     * 起始颜色
     */
    private int mStartColor = 0;

    /**
     * 结束颜色
     */
    private int mEndColor = 0;

    /**
     * 渐变方向
     * 0-GradientDrawable.Orientation.TOP_BOTTOM
     * 1-GradientDrawable.Orientation.LEFT_RIGHT
     */
    private int mOrientation = 0;

    /**
     * drawable位置
     * -1-null、0-left、1-top、2-right、3-bottom
     */
    private int mDrawablePosition = -1;

    /**
     * 普通shape样式
     */
    private GradientDrawable normalGradientDrawable = new GradientDrawable();
    /**
     * 按压shape样式
     */
    private GradientDrawable pressedGradientDrawable = new GradientDrawable();
    /**
     * shape样式集合
     */
    private StateListDrawable stateListDrawable = new StateListDrawable();
    // button内容总宽度
    private float contentWidth = 0f;
    // button内容总高度
    private float contentHeight = 0f;

    public CommonShapeButton(Context context) {
        this(context, null);
    }

    public CommonShapeButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonShapeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CommonShapeButton, defStyleAttr, 0);

        mShapeMode = array.getInt(R.styleable.CommonShapeButton_csb_shapeMode, 0);
        mFillColor = array.getColor(R.styleable.CommonShapeButton_csb_fillColor, Color.parseColor("#FFFFFF"));
        mPressedColor = array.getColor(R.styleable.CommonShapeButton_csb_pressedColor, Color.parseColor("#666666"));
        mStrokeColor = array.getColor(R.styleable.CommonShapeButton_csb_strokeColor, Color.parseColor("#00000000"));
        mStrokeWidth = array.getDimensionPixelSize(R.styleable.CommonShapeButton_csb_strokeWidth, 0);
        mCornerRadius = array.getDimensionPixelSize(R.styleable.CommonShapeButton_csb_cornerRadius, 0);
        mCornerPosition = array.getInt(R.styleable.CommonShapeButton_csb_cornerPosition, -1);
        mActiveEnable = array.getBoolean(R.styleable.CommonShapeButton_csb_activeEnable, false);
        mDrawablePosition = array.getInt(R.styleable.CommonShapeButton_csb_drawablePosition, -1);
        mStartColor = array.getColor(R.styleable.CommonShapeButton_csb_startColor, Color.parseColor("#FFFFFF"));
        mEndColor = array.getColor(R.styleable.CommonShapeButton_csb_endColor, Color.parseColor("#FFFFFF"));
        mOrientation = array.getColor(R.styleable.CommonShapeButton_csb_orientation, 0);

        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setNormalGradientDrawable();

        if (mActiveEnable) {
            // 5.0以上水波纹效果
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                RippleDrawable rippleDrawable = new RippleDrawable(ColorStateList.valueOf(mPressedColor), normalGradientDrawable, null);
                this.setBackground(rippleDrawable);
            }
            // 5.0以下变色效果
            else {
                setPressGradientDrawable();

                // 注意此处的add顺序，normal必须在最后一个，否则其他状态无效
                int[] colors1 = {android.R.attr.state_pressed};
                int[] colors2 = {};

                stateListDrawable.addState(colors1, pressedGradientDrawable);
                stateListDrawable.addState(colors2, normalGradientDrawable);
                this.setBackground(stateListDrawable);
            }
        } else {
            this.setBackground(normalGradientDrawable);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // 如果xml中配置了drawable则设置padding让文字移动到边缘与drawable靠在一起
        // button中配置的drawable默认贴着边缘
        if (mDrawablePosition > -1) {
            Drawable drawable = getCompoundDrawables()[mDrawablePosition];

            // 图片间距
            int drawablePadding = getCompoundDrawablePadding();
            switch (mDrawablePosition) {
                // 左右drawable
                case 0: {
                    // 图片宽度
                    int drawableWidth = drawable.getIntrinsicWidth();
                    // 获取文字宽度
                    int textWidth = (int) getPaint().measureText(getText().toString());
                    // 内容总宽度
                    contentWidth = textWidth + drawableWidth + drawablePadding;
                    int rightPadding = (int) (getWidth() - contentWidth);
                    // 图片和文字全部靠在左侧
                    setPadding(0, 0, rightPadding, 0);
                }
                case 2: {
                    // 图片宽度
                    int drawableWidth = drawable.getIntrinsicWidth();
                    // 获取文字宽度
                    int textWidth = (int) getPaint().measureText(getText().toString());
                    // 内容总宽度
                    contentWidth = textWidth + drawableWidth + drawablePadding;
                    int rightPadding = (int) (getWidth() - contentWidth);
                    // 图片和文字全部靠在左侧
                    setPadding(0, 0, rightPadding, 0);
                }
                // 上下drawable
                case 1: {
                    // 图片高度
                    int drawableHeight = drawable.getIntrinsicHeight();
                    // 获取文字高度
                    Paint.FontMetrics fm = getPaint().getFontMetrics();
                    // 单行高度
                    float singeLineHeight = (float) Math.ceil(fm.descent - fm.ascent);
                    // 总的行间距
                    float totalLineSpaceHeight = (getLineCount() - 1) * getLineSpacingExtra();
                    float textHeight = singeLineHeight * getLineCount() + totalLineSpaceHeight;
                    // 内容总高度
                    contentHeight = textHeight + drawableHeight + drawablePadding;
                    // 图片和文字全部靠在上侧
                    int bottomPadding = (int) (getHeight() - contentHeight);
                    setPadding(0, 0, 0, bottomPadding);
                }
                case 3: {
                    // 图片高度
                    int drawableHeight = drawable.getIntrinsicHeight();
                    // 获取文字高度
                    Paint.FontMetrics fm = getPaint().getFontMetrics();
                    // 单行高度
                    float singeLineHeight = (float) Math.ceil(fm.descent - fm.ascent);
                    // 总的行间距
                    float totalLineSpaceHeight = (getLineCount() - 1) * getLineSpacingExtra();
                    float textHeight = singeLineHeight * getLineCount() + totalLineSpaceHeight;
                    // 内容总高度
                    contentHeight = textHeight + drawableHeight + drawablePadding;
                    // 图片和文字全部靠在上侧
                    int bottomPadding = (int) (getHeight() - contentHeight);
                    setPadding(0, 0, 0, bottomPadding);
                }
            }

        }
        // 内容居中
        setGravity(Gravity.CENTER);
        // 可点击
        setClickable(true);
        changeTintContextWrapperToActivity();

    }

    /**
     * 从support23.3.0开始View中的getContext方法返回的是TintContextWrapper而不再是Activity
     * 如果使用xml注册onClick属性，就会通过反射到Activity中去找对应的方法
     * 5.0以下系统会反射到TintContextWrapper中去找对应的方法，程序直接crash
     * 所以这里需要针对5.0以下系统单独处理View中的getContext返回值
     */
    private void changeTintContextWrapperToActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Class clazz = this.getClass();
            while (clazz != null) {
                try {
                    Field field = clazz.getDeclaredField("mContext");
                    field.setAccessible(true);
                    field.set(this, getActivity());
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                clazz = clazz.getSuperclass();
            }
        }
    }


    /**
     * 从context中得到真正的activity
     */
    private Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }


    private void setPressGradientDrawable() {
        // 初始化pressed状态
        pressedGradientDrawable.setColor(mPressedColor);
        switch (mShapeMode) {
            case 0: {
                pressedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                break;
            }
            case 1: {
                pressedGradientDrawable.setShape(GradientDrawable.OVAL);
                break;
            }
            case 2: {
                pressedGradientDrawable.setShape(GradientDrawable.LINE);
                break;
            }
            case 3: {
                pressedGradientDrawable.setShape(GradientDrawable.RING);
                break;
            }
        }
        pressedGradientDrawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mCornerRadius, getResources().getDisplayMetrics()));
        pressedGradientDrawable.setStroke(mStrokeWidth, mStrokeColor);
    }

    private void setNormalGradientDrawable() {
        // 渐变色
        if (mStartColor != Color.parseColor("#FFFFFF") && mEndColor != Color.parseColor("#FFFFFF")) {
            int[] colors = {mStartColor, mEndColor};
            normalGradientDrawable.setColors(colors);
            switch (mOrientation) {
                case 0: {
                    normalGradientDrawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                    break;
                }
                case 1: {
                    normalGradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                    break;
                }
            }
        }
        // 填充色
        else {
            normalGradientDrawable.setColor(mFillColor);
        }

        switch (mShapeMode) {
            case 0: {
                normalGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                break;
            }
            case 1: {
                normalGradientDrawable.setShape(GradientDrawable.OVAL);
                break;
            }
            case 2: {
                normalGradientDrawable.setShape(GradientDrawable.LINE);
                break;
            }
            case 3: {
                normalGradientDrawable.setShape(GradientDrawable.RING);
                break;
            }
        }
        // 统一设置圆角半径
        if (mCornerPosition == -1) {
            normalGradientDrawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mCornerRadius, getResources().getDisplayMetrics()));
        }
        // 根据圆角位置设置圆角半径
        else {
            normalGradientDrawable.setCornerRadii(getCornerRadiusByPosition());
        }
        // 默认的透明边框不绘制,否则会导致没有阴影
        if (mStrokeColor != Color.parseColor("#00000000")) {
            normalGradientDrawable.setStroke(mStrokeWidth, mStrokeColor);
        }
    }


    /**
     * 根据圆角位置获取圆角半径
     */
    private float[] getCornerRadiusByPosition() {
        float[] result = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
        float cornerRadius = mCornerRadius;
        if (containsFlag(mCornerPosition, TOP_LEFT)) {
            result[0] = cornerRadius;
            result[1] = cornerRadius;
        }
        if (containsFlag(mCornerPosition, TOP_RIGHT)) {
            result[2] = cornerRadius;
            result[3] = cornerRadius;
        }
        if (containsFlag(mCornerPosition, BOTTOM_RIGHT)) {
            result[4] = cornerRadius;
            result[5] = cornerRadius;
        }
        if (containsFlag(mCornerPosition, BOTTOM_LEFT)) {
            result[6] = cornerRadius;
            result[7] = cornerRadius;
        }
        return result;
    }

    /**
     * 是否包含对应flag
     */
    private Boolean containsFlag(int flagSet, int flag) {
        if (flagSet != -1) {
            return true;
        } else {
            return flag == flagSet;
        }
    }
}
