package com.hhh.app_index.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.hhh.app_index.IndexActivity;
import com.hhh.app_index.v.IIndexActivityView;
import com.hhh.lib_api.services.impl.SampleServiceImp;
import com.hhh.lib_api.services.interfaces.ISampleService;
import com.hhh.lib_base.base_util_view.LoadingDialog;
import com.hhh.lib_core.beans.SampleUserBean;
import com.hhh.lib_core.model.UserInfoManager;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.functions.Consumer;

/**
 * 复用获取基础数据presenter的IndexSamplePresenter，实现presenter的复用
 */
public class IndexSamplePresenter extends IGetBaseInfoSamplePresenter<IIndexActivityView> {

    private ISampleService mSampleService;

    public void getDataFromNet(final IndexActivity indexActivity, String param1, String param2) {
        mSampleService = SampleServiceImp.create();
        LoadingDialog.getInstance(indexActivity).showLoading();
        mSampleService.getUserInfo(param1, param2)
                .compose(indexActivity.bindUntilEvent(ActivityEvent.PAUSE))  // 绑定生命周期
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) throws Exception {
                        LoadingDialog.getInstance(indexActivity).stopLoading();
                        SampleUserBean sampleUserBean = (SampleUserBean) object;
                        UserInfoManager.getInstance().saveUserInfo(sampleUserBean);
                        ToastUtils.showShort("完成了加载数据！");

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String message = throwable.getMessage();
                        indexActivity.showError(message);
                        LoadingDialog.getInstance(indexActivity).stopLoading();
                    }
                });
    }


}
