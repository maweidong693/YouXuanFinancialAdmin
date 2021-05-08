package com.weiwu.youxuanfinanciala.dmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.qiniu.android.utils.StringUtils;
import com.weiwu.youxuanfinanciala.dmin.base.BaseActivity;
import com.weiwu.youxuanfinanciala.dmin.data.entity.LoginData;
import com.weiwu.youxuanfinanciala.dmin.data.repositories.HomeRepository;
import com.weiwu.youxuanfinanciala.dmin.data.request.LoginRequest;
import com.weiwu.youxuanfinanciala.dmin.utils.SPUtils;

public class LoginActivity extends BaseActivity implements MyContract.ILoginView {

    private EditText mEtLoginUsername;
    private EditText mEtLoginPassword;
    private Button mBtLogin;
    private MyContract.ILoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImmersionBar.with(this).init();
        setPresenter(new LoginPresenter(HomeRepository.getInstance()));
        startMain();
        initView();
    }

    public void startMain() {
        String token = SPUtils.getValue(AppConstant.USER, AppConstant.USER_TOKEN);
        if (StringUtils.isNullOrEmpty(token)) {
            return;
        } else {
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }

    private void initView() {
        mEtLoginUsername = (EditText) findViewById(R.id.et_login_username);
        mEtLoginPassword = (EditText) findViewById(R.id.et_login_password);
        mBtLogin = (Button) findViewById(R.id.bt_login);
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = mEtLoginUsername.getText().toString();
                String password = mEtLoginPassword.getText().toString();
                if (StringUtils.isNullOrEmpty(phone)) {
                    showToast("请输入手机号！");
                } else {
                    if (StringUtils.isNullOrEmpty(password)) {
                        showToast("请输入密码！");
                    } else {
                        mPresenter.login(new LoginRequest(phone, password));
                    }
                }
            }
        });
    }

    @Override
    public void getLoginResult(LoginData data) {
        if (data.getCode() == 1) {
            LoginData.DataBean bean = data.getData();
            SPUtils.commitValue(AppConstant.USER, AppConstant.USER_TOKEN, bean.getToken());
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        } else {
            showToast(data.getMsg());
        }
    }

    @Override
    public void loginFail(String msg, int code) {
        showToast(msg);
    }

    @Override
    public void setPresenter(MyContract.ILoginPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Activity getActivityObject() {
        return this;
    }
}