package com.weiwu.youxuanfinanciala.dmin;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 *  
 * author : 马伟东
 * email : maweidong693@163.com
 * date : 2021/4/25 09:34 
 */
public class MyApplication extends Application {

    public static Application mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this;
        ZXingLibrary.initDisplayOpinion(this);
    }
}
