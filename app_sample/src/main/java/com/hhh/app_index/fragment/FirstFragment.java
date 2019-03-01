package com.hhh.app_index.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.hhh.app_index.R;
import com.hhh.app_index.R2;
import com.hhh.lib_base.XFragment;
import com.hhh.lib_base.base_mvp.IBasePresenter;
import com.hhh.lib_base.image_loader_util.GlideUtils;
import com.hhh.lib_base.views.AutoLineGroup;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;


public class FirstFragment extends XFragment {


    @BindView(R2.id.banner_home)
    Banner bannerHome;

    @BindView(R2.id.alg_sample1)
    AutoLineGroup algSample1;

    @BindView(R2.id.alg_sample2)
    AutoLineGroup algSample2;

    @BindView(R2.id.alg_sample3)
    AutoLineGroup algSample3;

    private ArrayList<String> listPath = new ArrayList<>();
    private ArrayList<String> listTitle = new ArrayList<>();

    private ArrayList<String> mExpressList = new ArrayList<>();
    private ArrayList<String> mDriverList = new ArrayList<>();
    private ArrayList<String> mRepairList = new ArrayList<>();

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

        initView();

        initListener();
    }

    private void initListener() {
        algSample1.setOnChildClickListener(new AutoLineGroup.OnChildClickListener() {
            @Override
            public void onChildClick(int pos, View view) {
                ToastUtils.showShort("点击了：" + pos);
            }
        });
    }

    private void initView() {
        mExpressList.add("1");
        mExpressList.add("2");
        mExpressList.add("3");
        mExpressList.add("4");

        mDriverList.add("1");
        mDriverList.add("2");
        mDriverList.add("3");
        mDriverList.add("4");
        mDriverList.add("5");


        mRepairList.add("1");
        mRepairList.add("2");
        mRepairList.add("3");
        mRepairList.add("4");
        mRepairList.add("5");
        mRepairList.add("6");
        mRepairList.add("7");
        mRepairList.add("8");


        LayoutInflater mInflater = LayoutInflater.from(getContext());
        for (int i = 0; i < mExpressList.size(); i++) {
            View view = mInflater.inflate(R.layout.app_sample_sample_view, null);
            algSample1.addView(view);
        }

        for (int i = 0; i < mDriverList.size(); i++) {
            View view = mInflater.inflate(R.layout.app_sample_sample_view, null);
            algSample2.addView(view);
        }

        for (int i = 0; i < mRepairList.size(); i++) {
            View view = mInflater.inflate(R.layout.app_sample_sample_view, null);
            algSample3.addView(view);
        }

    }

    public void initBanner() {
        for (int i = 0; i < 3; i++) {
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
