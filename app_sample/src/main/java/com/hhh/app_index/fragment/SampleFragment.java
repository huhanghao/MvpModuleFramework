package com.hhh.app_index.fragment;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hhh.app_index.R;
import com.hhh.app_index.R2;
import com.hhh.lib_base.XFragment;
import com.hhh.lib_base.base_mvp.IBasePresenter;
import com.hhh.lib_base.image_loader_util.GlideUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;


public class SampleFragment extends XFragment {


    @BindView(R2.id.banner_home)
    Banner bannerHome;

    private ArrayList<String> listPath = new ArrayList<>();
    private ArrayList<String> listTitle = new ArrayList<>();

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
        initBanner();
    }

    public void initBanner() {
        for (int i = 0; i <3; i++) {
            listPath.add("http://img1.cache.netease.com/catchpic/7/7F/7F9C353236E073FA3FD66708AFA58935.png");
            listTitle.add("http://img1.cache.netease.com/catchpic/7/7F/7F9C353236E073FA3FD66708AFA58935.png");
        }
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        bannerHome.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        bannerHome.setImageLoader(new MImageLoader());
        //设置图片网址或地址的集合
        bannerHome.setImages(listPath);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        bannerHome.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        bannerHome.setBannerTitles(listTitle);
        //设置轮播间隔时间
        bannerHome.setDelayTime(4000);
        //设置是否为自动轮播，默认是“是”。
        bannerHome.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        bannerHome.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {

                    }
                })
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    //自定义的图片加载器
    private class MImageLoader extends ImageLoader {
        @Override
        public void displayImage(final Context context, Object path, final ImageView imageView) {
            Glide.with(context).load((String) path).apply(GlideUtils.getBaseOponion()).into(imageView);
        }
    }

}
