package com.weiwu.youxuanfinanciala.dmin.base;

public interface IBaseCallBack<T> {
    void onSuccess(T data);

    void onFail(String msg, int code);
}
