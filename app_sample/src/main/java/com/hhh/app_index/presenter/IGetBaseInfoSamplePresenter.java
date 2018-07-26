package com.hhh.app_index.presenter;

import com.hhh.app_index.v.IGetBaseInfoView;
import com.hhh.lib_api.services.interfaces.ISampleService;
import com.hhh.lib_base.XActivity;
import com.hhh.lib_base.base_mvp.XPresenter;

/**
 * 用到IGetBaseInfoView的基础的Presenter
 * @param <V>
 */
public class IGetBaseInfoSamplePresenter<V extends IGetBaseInfoView> extends XPresenter<V> {

    private ISampleService mBookService;

    public void getDBaseInfoFromNet(XActivity xActivity, String param1, String param2) {
//        mBookService = SampleServiceImp.create();
//        mBookService.addNewsComment(param1, param2)
//                .compose(xActivity.bindUntilEvent(ActivityEvent.PAUSE))  // 绑定生命周期
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                        getV().returnBaseInfo("返回基础数据");
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                });

    }


}
