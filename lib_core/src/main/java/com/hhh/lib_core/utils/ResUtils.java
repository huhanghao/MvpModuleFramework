package com.hhh.lib_core.utils;

import android.content.Context;
import android.os.Build;

/**
 * 获取相关资源的util，AndroidUtils没有的方法
 */

public class ResUtils {

    public static int getColor(int res, Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ctx.getColor(res);
        } else {
            return ctx.getResources().getColor(res);
        }
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
}
