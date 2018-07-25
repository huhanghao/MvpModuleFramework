package com.hhh.app_index.V;

import com.hhh.lib_base.base_mvp.IBaseView;

/**
 * 复用了基础View，我们的IIndexActivityView
 */
public interface IIndexActivityView extends IBaseView, IGetBaseInfoView {

    /**
     * 获取一个String的对象
     *
     * @param sampleDataStr
     */
    void returnSampleData(String sampleDataStr);

}
