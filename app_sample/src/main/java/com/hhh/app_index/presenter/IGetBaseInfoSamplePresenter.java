package com.hhh.app_index.presenter;

import com.hhh.app_index.v.IGetBaseInfoView;
import com.hhh.lib_api.path.UrlConstants;
import com.hhh.lib_api.services.impl.IHttpClientImp;
import com.hhh.lib_api.services.interfaces.IHttpClient;
import com.hhh.lib_base.XActivity;
import com.hhh.lib_base.base_mvp.XPresenter;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.HashMap;

import io.reactivex.functions.Consumer;

/**
 * 用到IGetBaseInfoView的基础的Presenter
 * @param <V>
 */
public class IGetBaseInfoSamplePresenter<V extends IGetBaseInfoView> extends XPresenter<V> {

    private IHttpClient mHttpClient;

    public void getDBaseInfoFromNet(XActivity xActivity, String param1, String param2) {
        mHttpClient = IHttpClientImp.getInstance();
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("param1",param1);
        paramMap.put("param2",param2);

        mHttpClient.getWithoutRep( UrlConstants.Common.home,paramMap)
                .compose(xActivity.bindUntilEvent(ActivityEvent.PAUSE))  // 绑定生命周期
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean o) throws Exception {
                        getV().returnBaseInfo("返回基础数据");

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }


}
