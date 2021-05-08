package com.weiwu.youxuanfinanciala.dmin;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.weiwu.youxuanfinanciala.dmin.base.IBaseCallBack;
import com.weiwu.youxuanfinanciala.dmin.data.entity.InvData;
import com.weiwu.youxuanfinanciala.dmin.data.entity.VipList;
import com.weiwu.youxuanfinanciala.dmin.data.network.HttpResult;
import com.weiwu.youxuanfinanciala.dmin.data.request.LoginRequest;
import com.weiwu.youxuanfinanciala.dmin.data.request.VipRequest;

import java.util.List;

/**
 *  
 * author : 马伟东
 * email : maweidong693@163.com
 * date : 2021/4/30 17:25 
 */
public class HomePresenter implements MyContract.IHomePresenter {

    private MyContract.IHomeView mView;
    private MyContract.ISource mSource;

    public HomePresenter(MyContract.ISource source) {
        mSource = source;
    }

    @Override
    public void getList(VipRequest body) {
        mSource.getVipList((LifecycleProvider) mView, body, new IBaseCallBack<VipList>() {
            @Override
            public void onSuccess(VipList data) {
                mView.listResult(data);
            }

            @Override
            public void onFail(String msg, int statusCode) {
                mView.listFail(msg, statusCode);
            }
        });
    }

    @Override
    public void getInvUrl() {
        mSource.getInvUrl((LifecycleProvider) mView, new IBaseCallBack<InvData>() {
            @Override
            public void onSuccess(InvData data) {
                mView.invUrlResult(data);
            }

            @Override
            public void onFail(String msg, int statusCode) {
                mView.invUrlFail(msg, statusCode);
            }
        });
    }

    @Override
    public void attachView(MyContract.IHomeView view) {
        mView = view;
    }

    @Override
    public void detachView(MyContract.IHomeView view) {
        mView = null;

    }
}
