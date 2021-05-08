package com.weiwu.youxuanfinanciala.dmin.data.repositories;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.weiwu.youxuanfinanciala.dmin.MyContract;
import com.weiwu.youxuanfinanciala.dmin.base.BaseRepository;
import com.weiwu.youxuanfinanciala.dmin.base.IBaseCallBack;
import com.weiwu.youxuanfinanciala.dmin.base.ServerException;
import com.weiwu.youxuanfinanciala.dmin.data.entity.InvData;
import com.weiwu.youxuanfinanciala.dmin.data.entity.LoginData;
import com.weiwu.youxuanfinanciala.dmin.data.entity.VipDetailData;
import com.weiwu.youxuanfinanciala.dmin.data.entity.VipList;
import com.weiwu.youxuanfinanciala.dmin.data.network.DataService;
import com.weiwu.youxuanfinanciala.dmin.data.network.HttpResult;
import com.weiwu.youxuanfinanciala.dmin.data.request.LoginRequest;
import com.weiwu.youxuanfinanciala.dmin.data.request.VipDetailRequest;
import com.weiwu.youxuanfinanciala.dmin.data.request.VipRequest;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 *  
 * author : 马伟东
 * email : maweidong693@163.com
 * date : 2021/4/30 17:19 
 */
public class HomeRepository extends BaseRepository implements MyContract.ISource {

    public volatile static HomeRepository mHomeRepository;

    public static HomeRepository getInstance() {
        if (mHomeRepository == null) {
            synchronized (HomeRepository.class) {
                if (mHomeRepository == null) {
                    mHomeRepository = new HomeRepository();
                }
            }
        }
        return mHomeRepository;
    }

    @Override
    public void login(LifecycleProvider lifecycleProvider, LoginRequest body, IBaseCallBack<LoginData> callBack) {
        observerNoMap(lifecycleProvider, DataService.getApiService().login(body), callBack);
    }

    @Override
    public void getVipList(LifecycleProvider lifecycleProvider, VipRequest body, IBaseCallBack<VipList> callBack) {
        observerNoMap(lifecycleProvider, DataService.getApiService().getVipList(body), callBack);
    }

    @Override
    public void getVipDetailData(LifecycleProvider lifecycleProvider, VipDetailRequest body, IBaseCallBack<VipDetailData> callBack) {
        observerNoMap(lifecycleProvider, DataService.getApiService().getVipDetailData(body), callBack);
    }

    @Override
    public void getInvUrl(LifecycleProvider lifecycleProvider, IBaseCallBack<InvData> callBack) {
        observerNoMap(lifecycleProvider, DataService.getApiService().getInvUrl(), callBack);
    }
}
