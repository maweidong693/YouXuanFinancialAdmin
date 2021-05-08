package com.weiwu.youxuanfinanciala.dmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.gyf.immersionbar.ImmersionBar;
import com.weiwu.youxuanfinanciala.dmin.base.BaseActivity;
import com.weiwu.youxuanfinanciala.dmin.data.entity.VipDetailData;
import com.weiwu.youxuanfinanciala.dmin.data.repositories.HomeRepository;
import com.weiwu.youxuanfinanciala.dmin.data.request.VipDetailRequest;
import com.weiwu.youxuanfinanciala.dmin.utils.SPUtils;

public class VipDetailActivity extends BaseActivity implements MyContract.IVipDetailView {

    private Toolbar mOrderDetailToolbar;
    private TextView mTvVipPhone;
    private TextView mTvVipName;
    private TextView mTvVipReal;
    private TextView mTvVipBank;
    private TextView mTvVipBuyNum;
    private TextView mTvVipBuyMoney;
    private TextView mTvSellOrderNum;
    private TextView mTvSellOrderMoney;
    private TextView mTvIncome;
    private MyContract.IVipDetailPresenter mPresenter;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_detail);
        ImmersionBar.with(this).init();
        mId = getIntent().getIntExtra(AppConstant.VIP_DETAIL, 0);
        setPresenter(new VipDetailPresenter(HomeRepository.getInstance()));
        initView();
        toolbarBack(mOrderDetailToolbar);
    }

    private void initView() {
        mOrderDetailToolbar = (Toolbar) findViewById(R.id.order_detail_toolbar);
        mTvVipPhone = (TextView) findViewById(R.id.tv_vip_phone);
        mTvVipName = (TextView) findViewById(R.id.tv_vip_name);
        mTvVipReal = (TextView) findViewById(R.id.tv_vip_real);
        mTvVipBank = (TextView) findViewById(R.id.tv_vip_bank);
        mTvVipBuyNum = (TextView) findViewById(R.id.tv_vip_buy_num);
        mTvVipBuyMoney = (TextView) findViewById(R.id.tv_vip_buy_money);
        mTvSellOrderNum = (TextView) findViewById(R.id.tv_sell_order_num);
        mTvSellOrderMoney = (TextView) findViewById(R.id.tv_sell_order_money);
        mTvIncome = (TextView) findViewById(R.id.tv_income);
        mPresenter.getDetailData(new VipDetailRequest(String.valueOf(mId)));
    }

    @Override
    public void vipDetailResult(VipDetailData data) {
        if (data != null) {
            VipDetailData.DataBean bean = data.getData();
            mTvVipPhone.setText(bean.getMobile());
            mTvVipName.setText(bean.getName());
            mTvVipReal.setText(bean.getReal_status());
            mTvVipBank.setText(bean.getBank_status());
            mTvVipBuyNum.setText(String.valueOf(bean.getBuy_order_num()));
            mTvVipBuyMoney.setText(bean.getBuy_order_money());
            mTvSellOrderNum.setText(String.valueOf(bean.getSell_order_num()));
            mTvSellOrderMoney.setText(bean.getSell_order_money());
            mTvIncome.setText(bean.getIncome() + "元");
        }
    }

    @Override
    public void vipDetailResultFail(String msg, int code) {
        if (code == 401) {
            showToast("身份信息验证失败，请重新登录！");
            boolean b = SPUtils.clearValue(AppConstant.USER);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }

    @Override
    public void setPresenter(MyContract.IVipDetailPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Activity getActivityObject() {
        return this;
    }
}