package com.hhh.app_index.presenter;

import com.hhh.app_index.IndexActivity;
import com.hhh.app_index.v.IIndexActivityView;
import com.hhh.lib_api.path.UrlConstants;
import com.hhh.lib_api.services.impl.IHttpClientImp;
import com.hhh.lib_api.services.interfaces.IHttpClient;
import com.hhh.lib_base.views.LoadingDialog;
import com.hhh.lib_core.beans.SampleUserBean;
import com.hhh.lib_core.model.UserInfoManager;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.HashMap;

import io.reactivex.functions.Consumer;

/**
 * 复用获取基础数据presenter的IndexSamplePresenter，实现presenter的复用
 */
public class IndexSamplePresenter extends IGetBaseInfoSamplePresenter<IIndexActivityView> {

    private IHttpClient mHttpCLient;

    public void getDataFromNet(final IndexActivity indexActivity, String param1, String param2) {
        mHttpCLient = IHttpClientImp.getInstance();
        LoadingDialog.getInstance(indexActivity).showLoading();

        HashMap<String, String> params = new HashMap<>();
        params.put("username", param1);
        params.put("password", param2);

        mHttpCLient.post(UrlConstants.Common.adv, params, SampleUserBean.class)
                .compose(indexActivity.bindUntilEvent(ActivityEvent.PAUSE))  // 绑定生命周期
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) throws Exception {
                        LoadingDialog.getInstance(indexActivity).stopLoading();
                        SampleUserBean sampleUserBean = (SampleUserBean) object;
                        UserInfoManager.getInstance().saveUserInfo(sampleUserBean);

                        getV().returnSampleData("加在完成了");

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
