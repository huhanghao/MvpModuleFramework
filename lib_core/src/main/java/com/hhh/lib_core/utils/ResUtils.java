package com.hhh.lib_core.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * 获取相关资源的util，AndroidUtils没有的方法
 */

public class ResUtils {

    private static Resources mResouces = null;
    private static Context mContext = null;

    public static void init(Context context) {
        mContext = context;
        mResouces = context.getResources();
    }


    /**
     * 获取AppContext
     * @return
     */
    public static Context getAppContext() {
        return mContext;
    }

    public static boolean noContainsEmoji(String str) {//真为不含有表情
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }


    /**
     * 获取drawable
     * @param drawableID
     * @return
     */
    public static Drawable getDrawable(int drawableID) {
        if (drawableID < 0) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mResouces.getDrawable(drawableID, null);
        } else {
            return mResouces.getDrawable(drawableID);
        }
    }

    /**
     * 获取颜色
     * @param id
     * @return
     */
    public static int getColor(int id) {
        return mResouces.getColor(id);
    }

    /**
     * 获取String
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return mResouces.getString(resId);
    }
}
