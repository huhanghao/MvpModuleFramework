package com.hhh.app_index.v;

import com.hhh.lib_base.base_mvp.IBaseView;

import java.util.ArrayList;


/**
 * 一个获取基础信息的View接口
 */
public interface IGetListInfoView extends IBaseView {

    /**
     * 获取String 列表
     * @param strList
     */
    void returnListInfo(ArrayList<String> strList);

}
