package com.weiwu.youxuanfinanciala.dmin.base;

public interface IBasePresenter<T extends IBaseView> {

    void attachView(T view);

    void detachView(T view);
}
