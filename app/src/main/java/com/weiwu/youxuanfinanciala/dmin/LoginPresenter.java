package com.weiwu.youxuanfinanciala.dmin;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.weiwu.youxuanfinanciala.dmin.base.IBaseCallBack;
import com.weiwu.youxuanfinanciala.dmin.data.entity.LoginData;
import com.weiwu.youxuanfinanciala.dmin.data.request.LoginRequest;

/**
 *  
 * author : 马伟东
 * email : maweidong693@163.com
 * date : 2021/4/30 17:25 
 */
public class LoginPresenter implements MyContract.ILoginPresenter {

    private MyContract.ILoginView mView;
    private MyContract.ISource mSource;

    public LoginPresenter(MyContract.ISource source) {
        mSource = source;
    }

    @Override
    public void login(LoginRequest body) {
        mSource.login((LifecycleProvider) mView, body, new IBaseCallBack<LoginData>() {
            @Override
            public void onSuccess(LoginData data) {
                mView.getLoginResult(data);
            }

            @Override
            public void onFail(String msg, int statusCode) {
                mView.loginFail(msg, statusCode);
            }
        });
    }

    @Override
    public void attachView(MyContract.ILoginView view) {
        mView = view;
    }

    @Override
    public void detachView(MyContract.ILoginView view) {
        mView = null;
    }
}
