package com.hhh.app_index.fragment;

import android.os.Bundle;

import com.hhh.app_index.R;
import com.hhh.lib_base.XFragment;
import com.hhh.lib_base.base_mvp.IBasePresenter;


public class SampleFragment extends XFragment {

    @Override
    public IBasePresenter setPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.app_sample_fragment_sample;
    }

    @Override
    public void addView(Bundle savedInstanceState) {

    }
}
