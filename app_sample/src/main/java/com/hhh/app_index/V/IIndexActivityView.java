package com.hhh.app_index.V;

import com.hhh.app_index.Presenter.IndexSamplePresenter;
import com.hhh.lib_base.base_mvp.IBaseView;

public interface IIndexActivityView extends IBaseView<IndexSamplePresenter> {

    /**
     * 获取一个String的对象
     * @param sampleDataStr
     */
    void getSampleData(String sampleDataStr);

}
