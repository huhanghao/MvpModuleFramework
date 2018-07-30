package com.hhh.newmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_button)
    View tvButton;

    static long lastExitRequestTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // 利用arouter进行跳转
        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/sample/index")
                        .navigation(MainActivity.this);
            }
        });
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitOnSecondTime();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 三秒内两次点击 以退出(回桌面)
     */
    private void exitOnSecondTime() {

        if (System.currentTimeMillis() - lastExitRequestTime <= 3000) {
            lastExitRequestTime = 0;
            finish();
            System.exit(0);
        } else {
            ToastUtils.showShort("再次按返回键将退出", MainActivity.this);
            lastExitRequestTime = System.currentTimeMillis();
        }
    }
}
