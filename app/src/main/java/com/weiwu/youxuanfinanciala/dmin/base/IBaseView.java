package com.weiwu.youxuanfinanciala.dmin.base;

import android.app.Activity;

public interface IBaseView<T extends IBasePresenter> {

    void setPresenter(T presenter);

    Activity getActivityObject();
}
