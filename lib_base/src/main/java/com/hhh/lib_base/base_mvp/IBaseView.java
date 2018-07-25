package com.hhh.lib_base.base_mvp;

import android.os.Bundle;

/**
 * mvp模式中View接口的基类
 */
public interface IBaseView {

    // 初始化View和程序处理入口
    void addView(Bundle savedInstanceState);

    //显示loading
    void showLoading(String title);

    //关闭loading
    void stopLoading();
}
