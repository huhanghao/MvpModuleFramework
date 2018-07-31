package com.hhh.lib_base.base_util_view_relys;

/**
 * Created by Jesson_Yi on 2016/6/22.
 */

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.hhh.lib_base.R;
import com.hhh.lib_core.beans.UrlDrawable;

import java.util.HashSet;


public class GlideImageGeter implements Html.ImageGetter{

    private HashSet<Target> targets;
    private HashSet<GifDrawable> gifDrawables;
    private final Context mContext;
    private final TextView mTextView;

    public  void recycle() {
        targets.clear();
        for (GifDrawable gifDrawable : gifDrawables) {
            gifDrawable.setCallback(null);
            gifDrawable.recycle();
        }
        gifDrawables.clear();
    }

    public GlideImageGeter(Context context, TextView textView) {
        this.mContext = context;
        this.mTextView = textView;
        targets = new HashSet<>();
        gifDrawables = new HashSet<>();
        mTextView.setTag(R.id.img_tag, this);
    }

    @Override
    public Drawable getDrawable(String url) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        RequestBuilder load;
        final Target target;
        if(isGif(url)){
            load = Glide.with(mContext).load(url);
            target = new GifTarget(urlDrawable);
        }else {
            load = Glide.with(mContext).load(url);
            target = new BitmapTarget(urlDrawable);
        }
        targets.add(target);
        load.into(target);
        return urlDrawable;
    }

    private static boolean isGif(String path) {
        int index = path.lastIndexOf('.');
        return index > 0 && "gif".toUpperCase().equals(path.substring(index + 1).toUpperCase());
    }

    private class GifTarget extends SimpleTarget<GifDrawable> {
        private final UrlDrawable urlDrawable;


        private  GifTarget(UrlDrawable urlDrawable) {
            this.urlDrawable = urlDrawable;

        }

        @Override
        public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {

            int w = ScreenUtils.getScreenWidth();
            int hh=resource.getIntrinsicHeight();
            int ww=resource.getIntrinsicWidth() ;
            int high = hh * (w - 50)/ww;
            Rect rect = new Rect(20, 20,w-20,high);
            resource.setBounds(rect);
            urlDrawable.setBounds(rect);
            urlDrawable.setDrawable(resource);
            gifDrawables.add(resource);
            resource.setCallback(mTextView);
            resource.start();
            resource.setLoopCount(-1);
            mTextView.setText(mTextView.getText());
            mTextView.invalidate();
        }
    }

    private class BitmapTarget extends SimpleTarget<BitmapDrawable> {
        private final UrlDrawable urlDrawable;

        public BitmapTarget(UrlDrawable urlDrawable) {
            this.urlDrawable = urlDrawable;
        }

        @Override
        public void onResourceReady(@NonNull BitmapDrawable resource, @Nullable Transition<? super BitmapDrawable> transition) {
            Drawable drawable = resource;

            int w = ScreenUtils.getScreenWidth()/20*19;
            int hh=drawable.getIntrinsicHeight();
            int ww=drawable.getIntrinsicWidth() ;
            int high=hh*(w-40)/ww;
            Rect rect = new Rect(ScreenUtils.getScreenWidth()/20, 20,w,high);
            drawable.setBounds(rect);
            urlDrawable.setBounds(rect);
            urlDrawable.setDrawable(drawable);
            mTextView.setText(mTextView.getText());
            mTextView.invalidate();
        }
    }
}