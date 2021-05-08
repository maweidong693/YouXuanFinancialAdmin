package com.weiwu.youxuanfinanciala.dmin.data.network;

import com.weiwu.youxuanfinanciala.dmin.data.entity.InvData;
import com.weiwu.youxuanfinanciala.dmin.data.entity.LoginData;
import com.weiwu.youxuanfinanciala.dmin.data.entity.VipDetailData;
import com.weiwu.youxuanfinanciala.dmin.data.entity.VipList;
import com.weiwu.youxuanfinanciala.dmin.data.request.LoginRequest;
import com.weiwu.youxuanfinanciala.dmin.data.request.VipDetailRequest;
import com.weiwu.youxuanfinanciala.dmin.data.request.VipRequest;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 *  
 * author : 马伟东
 * email : maweidong693@163.com
 * date : 2021/4/30 17:08 
 */
public interface ApiService {

    @POST("Pub/doLogin")
    Observable<LoginData> login(@Body LoginRequest body);

    @POST("Agent/member_list")
    Observable<VipList> getVipList(@Body VipRequest body);

    @POST("Agent/membe_detail")
    Observable<VipDetailData> getVipDetailData(@Body VipDetailRequest body);

    @POST("Agent/getQr")
    Observable<InvData> getInvUrl();

}
