package com.hhh.newmodule;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.hhh.lib_api.interfaces.IOnNetEventListener;
import com.hhh.lib_api.services.impl.IHttpClientImp;
import com.hhh.lib_api.token.TokenManager;
import com.hhh.lib_core.utils.ResUtils;

/**
 * Created by nova on 18/02/2018.
 */

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);
        Utils.init(this);
        ResUtils.init(this);

        // 错误信息的整体处理
        IHttpClientImp.setOnEventListener(new IOnNetEventListener() {
            @Override
            public void onAuthError() {
                // to do
                // need test
                ActivityUtils.finishAllActivities();
                ARouter.getInstance().build("/login/login").navigation();
                TokenManager.getInstance().setToken(null);

            }

            @Override
            public void onApiSuccess(String path) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
