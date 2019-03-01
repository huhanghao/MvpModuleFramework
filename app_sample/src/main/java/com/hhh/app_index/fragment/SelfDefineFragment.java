package com.hhh.app_index.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hhh.app_index.R;
import com.hhh.app_index.R2;
import com.hhh.app_index.activity.CoverFlowActivity;
import com.hhh.app_index.presenter.GetListPresenter;
import com.hhh.lib_base.XFragment;

import butterknife.BindView;


public class SelfDefineFragment extends XFragment {

    @BindView(R2.id.csb_btn1)
    View csbBtn1;


    @Override
    public GetListPresenter setPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.app_sample_fragment_special_list;
    }

    @Override
    public void addView(Bundle savedInstanceState) {
        csbBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CoverFlowActivity.class));
            }
        });
    }


}
