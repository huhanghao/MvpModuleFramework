package com.hhh.app_index;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hhh.lib_api.services.impl.SampleServiceImp;
import com.hhh.lib_api.services.interfaces.ISampleService;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.RxActivity;

import io.reactivex.functions.Consumer;


@Route(path = "/index/index")
public class IndexActivity extends RxActivity {


    private ISampleService mBookService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_mine_activity_main);

        mBookService = SampleServiceImp.create();

        mBookService.addNewsComment("12", "32")
                .compose(bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
