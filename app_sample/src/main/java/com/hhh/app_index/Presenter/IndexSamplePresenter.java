package com.hhh.app_index.Presenter;

import com.hhh.app_index.IndexActivity;
import com.hhh.lib_api.services.impl.SampleServiceImp;
import com.hhh.lib_api.services.interfaces.ISampleService;
import com.hhh.lib_base.base_mvp.XPresent;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.functions.Consumer;

public class IndexSamplePresenter extends XPresent<IndexActivity> {

    private ISampleService mBookService;

    public void getDataFromNet(IndexActivity indexActivity, String param1, String param2) {
        mBookService = SampleServiceImp.create();
        mBookService.addNewsComment(param1, param2)
                .compose(indexActivity.bindUntilEvent(ActivityEvent.PAUSE))  // 绑定生命周期
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        getV().getSampleData("网络访问成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }


}
