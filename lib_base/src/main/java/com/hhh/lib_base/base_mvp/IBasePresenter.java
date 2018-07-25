package com.hhh.lib_base.base_mvp;

public interface IBasePresenter<V> {
    void attachV(V view);

    void detachV();
}
