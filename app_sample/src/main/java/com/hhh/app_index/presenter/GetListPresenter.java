package com.hhh.app_index.presenter;

import com.hhh.app_index.v.IGetListInfoView;
import com.hhh.lib_base.base_mvp.XPresenter;

import java.util.ArrayList;


/**
 * 复用获取基础数据presenter的IndexSamplePresenter，实现presenter的复用
 */
public class GetListPresenter extends XPresenter<IGetListInfoView> {


    ArrayList<String> mListStr = new ArrayList<>();

    // 根据pageCount加载数据
    public void getListStr(int pageCount, int pageSum) {
        mListStr.clear();

        for (int i = pageCount * pageSum; i < (pageCount + 1) * pageSum; i++) {
            mListStr.add(String.format("第%s个数据", i));
        }

        getV().returnListInfo(mListStr);
    }
}
