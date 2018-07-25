package com.hhh.app_index.V;

import com.hhh.lib_base.base_mvp.IBaseView;


/**
 * 一个获取基础信息的View接口
 */
public interface IGetBaseInfoView  extends IBaseView {

    /**
     * 获取一个String的对象
     * @param baseInfo
     */
    void returnBaseInfo(String baseInfo);

}
