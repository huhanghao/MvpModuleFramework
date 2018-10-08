package com.hhh.app_index.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hhh.app_index.R;
import com.hhh.app_index.R2;
import com.hhh.lib_base.XActivity;
import com.hhh.lib_base.base_mvp.IBasePresenter;
import com.hhh.lib_base.image_loader_util.GlideUtils;
import com.hhh.lib_base.image_loader_util.transform.CircleTransformation;
import com.hhh.lib_base.image_loader_util.transform.RadiusTransformation;
import com.hhh.lib_base.views.BottomUpSelectDialog;
import com.hhh.lib_base.views.HTMLView;
import com.hhh.lib_base.views.PopupWindowAlert;
import com.hhh.lib_core.event.SampleMessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UtilViewSampleActivity extends XActivity {

    @BindView(R2.id.tv_button_bottom_up)
    View tvButtonBottomUp;

    @BindView(R2.id.tv_button_alert)
    View tvButtonAlert;

    @BindView(R2.id.tv_button_dialog1)
    View tvButtonDialog;

    @BindView(R2.id.tv_button_show_loading)
    View tvButtonShowLoading;

    @BindView(R2.id.tv_button_hide_loading)
    View tvButtonHideLoading;

    @BindView(R2.id.tv_show_error)
    View tvShowError;

    @BindView(R2.id.hv_html)
    HTMLView hvHtml;

    @BindView(R2.id.tv_button_send_msg)
    View tvButtonSendMsg;

    @BindView(R2.id.iv_img1)
    ImageView ivImg1;

    @BindView(R2.id.iv_img2)
    ImageView ivImg2;

    @BindView(R2.id.iv_img3)
    ImageView ivImg3;

    @BindView(R2.id.iv_img4)
    ImageView ivImg4;

    @BindView(R2.id.et_msg_show)
    EditText etMsgShow;

    private BottomUpSelectDialog mBottomUpSelectDialog;
    private PopupWindowAlert mPopupWindowAlert;
    private Dialog dialog;


    private String htmlContent = "<p>云闪闪平台苹果版的APP已上架，使用苹果手机的朋友可以去公众号商务中心下载使用，APP体验更流畅</p>" +
            "<\\/p><p><img title=\"\" src=\"http://img1.cache.netease.com/catchpic/7/7F/7F9C353236E073FA3FD66708AFA58935.png\"width=\"300\" height=\"291\">" + "<\\/p>";

    public static final String cat_thumbnail = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/resources/cat_thumbnail.jpg";

    @Override
    public void addView(Bundle savedInstanceState) {
        addMainView(R.layout.app_sample_activity_util_view_sample);

        setMidTitle("UtilViewSampleActivity");

        initBottomUpSelectorDialog();

        initPopupWindowAlert();

        initBottomDialog(UtilViewSampleActivity.this, R.layout.app_sample_bottom_dialog_view);

        initListener();

        hvHtml.setRichText(htmlContent);

        hvHtml.setOnRichTextImageClickListener(new HTMLView.OnRichTextImageClickListener() {
            @Override
            public void imageClicked(List<String> imageUrls, int position) {
                ToastUtils.showShort("点击图片为：" + imageUrls + "， position = " + position);
            }
        });

        initImage();

    }

    private void initImage() {

        Glide.with(this).load(cat_thumbnail).apply(GlideUtils.getBaseOponion().transform(new RadiusTransformation(5))).into(ivImg1);

        Glide.with(this).load(R.mipmap.app_sample_start).apply(GlideUtils.getBaseOponion().transform(new CircleTransformation())).into(ivImg2);

        Glide.with(this).load(R.mipmap.app_sample_start).apply(GlideUtils.getBaseOponion().override(400, 400)).into(ivImg3);

        Glide.with(this).load(R.mipmap.app_sample_start).apply(GlideUtils.getBaseOponion()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                // 将图片的宽缩放到与屏幕相同，高等比例缩放
                int imageWitdh = resource.getIntrinsicWidth();
                int imageHeight = resource.getIntrinsicHeight();

                int screenWdth = ScreenUtils.getScreenWidth();

                ViewGroup.LayoutParams layoutParams = ivImg4.getLayoutParams();
                layoutParams.width = screenWdth;
                float ratio = (float) screenWdth / imageWitdh;
                int height = (int) (imageHeight * ratio);
                layoutParams.height = height;

                ivImg4.setLayoutParams(layoutParams);
                ivImg4.setImageDrawable(resource);
            }
        });

    }

    @Override
    public IBasePresenter setPresenter() {
        return null;
    }

    private void initBottomDialog(Context context, int resourceId) {
        dialog = null;
        dialog = new Dialog(context, R.style.bottom_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(resourceId);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottom_dialog_animate);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = dm.widthPixels;
        window.setAttributes(lp);
    }

    private void initListener() {
        tvButtonBottomUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomUpSelectDialog.show();
            }
        });

        tvButtonAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindowAlert.setItemData("组织删除确认", "这是详情内容", "取消", "确定");
                mPopupWindowAlert.show();
            }
        });

        tvButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        tvButtonShowLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading("展示loading中", false);
            }
        });

        tvButtonHideLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLoading();
            }
        });

        tvShowError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showError("出现了异常，请稍后再试~");
            }
        });

        tvButtonSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SampleMessageEvent messageEvent = new SampleMessageEvent();
                messageEvent.setmMsg(etMsgShow.getText().toString());
                EventBus.getDefault().post(messageEvent);
                ToastUtils.showShort("已发送消息事件");
            }
        });

    }

    /**
     * 初始化selector
     */
    private void initBottomUpSelectorDialog() {
        ArrayList<String> nameList = new ArrayList<>();
        nameList.add("选项1");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");
        nameList.add("选项2");

        mBottomUpSelectDialog = new BottomUpSelectDialog(this, R.style.transparentFrameWindowStyle,
                new BottomUpSelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: {

                                ToastUtils.showShort("选择了  选项1");
                                break;
                            }
                            case 1: {

                                ToastUtils.showShort("选择了  选项2");
                                break;
                            }
                        }
                    }
                }, nameList);
    }

    /**
     *
     */
    public void initPopupWindowAlert() {
        mPopupWindowAlert = new PopupWindowAlert(this, false, new PopupWindowAlert.OnItemListener() {

            @Override
            public void result(int platform) {
                switch (platform) {
                    case 1:

                        mPopupWindowAlert.closePop();
                        break;
                    case 0:

                        mPopupWindowAlert.closePop();
                        break;
                }
            }
        });
    }

}
