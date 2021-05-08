package com.weiwu.youxuanfinanciala.dmin;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.weiwu.youxuanfinanciala.dmin.base.IBaseCallBack;
import com.weiwu.youxuanfinanciala.dmin.base.IBasePresenter;
import com.weiwu.youxuanfinanciala.dmin.base.IBaseView;
import com.weiwu.youxuanfinanciala.dmin.data.entity.InvData;
import com.weiwu.youxuanfinanciala.dmin.data.entity.LoginData;
import com.weiwu.youxuanfinanciala.dmin.data.entity.VipDetailData;
import com.weiwu.youxuanfinanciala.dmin.data.entity.VipList;
import com.weiwu.youxuanfinanciala.dmin.data.network.HttpResult;
import com.weiwu.youxuanfinanciala.dmin.data.request.LoginRequest;
import com.weiwu.youxuanfinanciala.dmin.data.request.VipDetailRequest;
import com.weiwu.youxuanfinanciala.dmin.data.request.VipRequest;

import java.util.List;

/**
 *  
 * author : 马伟东
 * email : maweidong693@163.com
 * date : 2021/4/30 17:14 
 */
public interface MyContract {

    interface ILoginView extends IBaseView<ILoginPresenter> {
        void getLoginResult(LoginData data);

        void loginFail(String msg, int code);
    }

    interface IHomeView extends IBaseView<IHomePresenter> {
        void listResult(VipList data);

        void invUrlResult(InvData data);

        void invUrlFail(String msg, int code);

        void listFail(String msg, int code);
    }

    interface IVipDetailView extends IBaseView<IVipDetailPresenter> {
        void vipDetailResult(VipDetailData data);

        void vipDetailResultFail(String msg, int code);
    }

    interface IHomePresenter extends IBasePresenter<IHomeView> {
        void getList(VipRequest body);

        void getInvUrl();
    }

    interface IVipDetailPresenter extends IBasePresenter<IVipDetailView> {
        void getDetailData(VipDetailRequest body);
    }

    interface ILoginPresenter extends IBasePresenter<ILoginView> {
        void login(LoginRequest body);
    }

    interface ISource {
        void login(LifecycleProvider lifecycleProvider, LoginRequest body, IBaseCallBack<LoginData> callBack);

        void getVipList(LifecycleProvider lifecycleProvider, VipRequest body, IBaseCallBack<VipList> callBack);

        void getVipDetailData(LifecycleProvider lifecycleProvider, VipDetailRequest body, IBaseCallBack<VipDetailData> callBack);

        void getInvUrl(LifecycleProvider lifecycleProvider, IBaseCallBack<InvData> callBack);
    }

}
