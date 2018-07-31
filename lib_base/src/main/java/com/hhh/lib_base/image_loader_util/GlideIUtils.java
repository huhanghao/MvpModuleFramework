package com.hhh.lib_base.image_loader_util;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hhh.lib_base.R;

/**
 * 图片加载、处理工具
 */
public class GlideIUtils {

    public static RequestOptions getBaseOponion(){
        return  new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.mipmap.ic_launcher);
    }

}
