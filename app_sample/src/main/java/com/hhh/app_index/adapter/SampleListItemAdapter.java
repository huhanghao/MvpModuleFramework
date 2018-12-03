package com.hhh.app_index.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hhh.app_index.R;

import java.util.List;

/**
 * Created by nova on 08/03/2018.
 */

public class SampleListItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public SampleListItemAdapter(@Nullable List<String> data) {
        super(R.layout.app_sample_string_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_string, item);


    }

}
