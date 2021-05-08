package com.weiwu.youxuanfinanciala.dmin.data.network;

import com.trello.rxlifecycle2.android.BuildConfig;
import com.weiwu.youxuanfinanciala.dmin.AppConstant;
import com.weiwu.youxuanfinanciala.dmin.utils.SPUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  
 * author : 马伟东
 * email : maweidong693@163.com
 * date : 2021/4/25 09:33 
 */
public class DataService {
    private static final long DEFAULT_TIMEOUT = 20000;

    private static volatile ApiService mApiService;


    public static ApiService getApiService() {

        if (mApiService == null) {
            synchronized (DataService.class) {
                if (mApiService == null) {
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

                    if (BuildConfig.DEBUG) {
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                    } else {
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    }

                    OkHttpClient httpClient = new OkHttpClient.Builder()
                            .addInterceptor(chain -> {
                                Request request = chain.request();
                                request = request.newBuilder()
                                        .addHeader(AppConstant.TOKEN_NAME, SPUtils.getValue(AppConstant.USER, AppConstant.USER_TOKEN) == null ? " " : SPUtils.getValue(AppConstant.USER, AppConstant.USER_TOKEN))
                                        .build();
                                return chain.proceed(request);
                            })
                            /*.addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Response response = chain.proceed(chain.request());
                                    if (!response.headers(AppConstant.TOKEN_NAME).isEmpty()) {
                                        StringBuilder tokenSb = new StringBuilder();
                                        List<String> tokenHeaders = response.headers(AppConstant.TOKEN_NAME);
                                        for (int i = 0; i < tokenHeaders.size(); i++) {
                                            tokenSb.append(tokenHeaders.get(i));
                                        }
                                        SPUtils.commitValue(AppConstant.USER, AppConstant.USER_TOKEN, tokenSb.toString());
                                    }
                                    return response;
                                }
                            })*/
                            .addInterceptor(httpLoggingInterceptor)
                            .addInterceptor(new HandleErrorInterceptor())
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .client(httpClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(AppConstant.BASE_URL)
                            .build();
                    mApiService = retrofit.create(ApiService.class);

                }
            }
        }

        return mApiService;
    }
}
