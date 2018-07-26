package com.hhh.lib_core.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hhh.lib_core.beans.SampleUserBean;

/**
 * 用户信息管理对象
 */
public class UserInfoManager {
    // 存储用户信息的key
    public static final String USER_INFO = "USER_INFO";

    // 对象锁
    private static final Object SYNC_LOCK = new Object();
    private static UserInfoManager mUserInfoManager; // 获取用户信息

    private boolean isUserLogin;  // 用户是否登录
    private SampleUserBean mSampleUserBean;

    /**
     * 获取用户数据
     *
     * @return
     */
    public static UserInfoManager getInstance() {
        if (mUserInfoManager == null) {
            synchronized (SYNC_LOCK) {
                if (mUserInfoManager == null) {
                    mUserInfoManager = new UserInfoManager();
                }
            }
        }
        return mUserInfoManager;
    }

    /**
     * 从本地SP中获取用户信息
     *
     * @return 有则返回用户对象，没有则返回null
     */
    public SampleUserBean getUserInfo() {
        if (mSampleUserBean == null) {
            String userINfoStr = LocalSpDataManager.getManager().getStringData(USER_INFO);
            if (TextUtils.isEmpty(userINfoStr)) {
                return null;
            } else {
                Gson gson = new Gson();
                SampleUserBean sampleUserBean = gson.fromJson(userINfoStr, SampleUserBean.class);
                return sampleUserBean;
            }
        } else {
            return mSampleUserBean;
        }
    }


    /**
     * 将用户信息转化为String，保存在本地
     *
     * @param sampleUserBean
     */
    public void saveUserInfo(SampleUserBean sampleUserBean) {
        mSampleUserBean = sampleUserBean;
        Gson gson = new Gson();
        String userINfoStr = gson.toJson(sampleUserBean);
        LocalSpDataManager.getManager().getSp().put(userINfoStr, "");
    }

    /**
     * 用户是否登录了
     */
    public boolean isUserLogin() {
        return isUserLogin;
    }

    /**
     * 设置用户登录标志
     *
     * @param userLogin
     */
    public void setUserLogin(boolean userLogin) {
        isUserLogin = userLogin;
    }

    /**
     * 获取用户token
     *
     * @return
     */
    public String getUserToken() {
        String tokenStr;
        if (mSampleUserBean != null) {
            tokenStr = mSampleUserBean.getToken();
        }else{
            SampleUserBean sampleUserBean = getUserInfo();
            if(sampleUserBean == null){
                tokenStr = "";
            }else{
                tokenStr = sampleUserBean.getToken();
            }

        }
        return tokenStr;
    }

}
