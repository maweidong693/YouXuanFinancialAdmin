package com.weiwu.youxuanfinanciala.dmin;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.weiwu.youxuanfinanciala.dmin.base.IBaseCallBack;
import com.weiwu.youxuanfinanciala.dmin.data.entity.VipDetailData;
import com.weiwu.youxuanfinanciala.dmin.data.request.VipDetailRequest;

/**
 *  
 * author : 马伟东
 * email : maweidong693@163.com
 * date : 2021/5/3 17:30 
 */
public class VipDetailPresenter implements MyContract.IVipDetailPresenter {

    private MyContract.IVipDetailView mView;
    private MyContract.ISource mSource;

    public VipDetailPresenter(MyContract.ISource source) {
        mSource = source;
    }

    @Override
    public void getDetailData(VipDetailRequest body) {
        mSource.getVipDetailData((LifecycleProvider) mView, body, new IBaseCallBack<VipDetailData>() {
            @Override
            public void onSuccess(VipDetailData data) {
                mView.vipDetailResult(data);
            }

            @Override
            public void onFail(String msg, int statusCode) {
                mView.vipDetailResultFail(msg, statusCode);
            }
        });
    }

    @Override
    public void attachView(MyContract.IVipDetailView view) {
        mView = view;
    }

    @Override
    public void detachView(MyContract.IVipDetailView view) {
        mView = null;
    }
}
