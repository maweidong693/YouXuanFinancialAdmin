package com.weiwu.youxuanfinanciala.dmin.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gyf.immersionbar.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.weiwu.youxuanfinanciala.dmin.R;
import com.weiwu.youxuanfinanciala.dmin.utils.Logger;
import com.weiwu.youxuanfinanciala.dmin.view.LoadingPage;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class BaseActivity extends RxAppCompatActivity {

    private String TAG;
    private LoadingPage mLoadingPage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        TAG = getClass().getSimpleName();
        ImmersionBar.with(this).init();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

   /* @Override
    public void onStart() {
        super.onStart();
        NetStatusBus.getInstance().register(this);
    }*/

    /*@Override
    public void onStop() {
        super.onStop();
        NetStatusBus.getInstance().unregister(this);
    }*/

    protected void setSpannableString(String text, final int color, Intent intent, TextView view) {
        SpannableString registerSs = new SpannableString(text);
        registerSs.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
//                Toast.makeText(LoginActivity.this, "????????????", Toast.LENGTH_SHORT).show();
//                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(color);
                ds.setUnderlineText(false);
            }
        }, 0, registerSs.length(), Spanned.SPAN_COMPOSING);
        view.append(registerSs);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setHighlightColor(getResources().getColor(android.R.color.transparent));
    }

    protected BaseFragment addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, int containerId, Bundle args) {

        String tag = aClass.getName();

        Logger.d("%s add fragment %s", TAG, aClass.getSimpleName());

        Fragment fragment = manager.findFragmentByTag(tag);


        FragmentTransaction transaction = manager.beginTransaction(); // ??????????????????

        if (fragment == null) {// ????????????
            try {
                fragment = aClass.newInstance(); // ???????????? new ????????? fragment ?????????

                BaseFragment baseFragment = (BaseFragment) fragment; // ???????????????base fragment

                // ?????? fragment ?????????????????? ????????????????????????
//                transaction.setCustomAnimations(baseFragment.enter(), baseFragment.exit(), baseFragment.popEnter(), baseFragment.popExit());

                transaction.add(containerId, fragment, tag);

                if (baseFragment.isNeedToAddBackStack()) { // ?????????????????????????????????
                    transaction.addToBackStack(tag); // ??????????????????????????????tag?????????????????????????????????
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (fragment.isAdded()) {
                if (fragment.isHidden()) {
                    transaction.show(fragment);
                }
            } else {
                transaction.add(containerId, fragment, tag);
            }
        }

        if (fragment != null) {
            fragment.setArguments(args);
            hideBeforeFragment(manager, transaction, fragment);
            transaction.commit();
            return (BaseFragment) fragment;

        }
        return null;
    }

    protected void removeAllFragment(FragmentManager mFragmentManager) {
        for (Fragment fragment : mFragmentManager.getFragments()) {
            mFragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    private void hideBeforeFragment(FragmentManager manager, FragmentTransaction transaction, Fragment currentFragment) {

        List<Fragment> fragments = manager.getFragments();

        for (Fragment fragment : fragments) {
            if (fragment != currentFragment && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
    }


    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int rId) {
        showToast(getString(rId));
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    protected void toolbarBack(Toolbar toolbar) {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void onError(String msg, LoadingPage.OnReloadListener listener) {
        if (mLoadingPage != null && mLoadingPage.getParent() != null) {
            mLoadingPage.onError(msg, listener);
        }
    }

    protected void showLoadingPage(int mode) {
        showLoadingPage(mode, android.R.id.content);
    }

    protected void showLoadingPage(int mode, @IdRes int groupId) {
        showLoadingPage(mode, (ViewGroup) findViewById(groupId));
    }

    protected void showLoadingPage(int mode, ViewGroup group) {
        if (mLoadingPage == null) {
            mLoadingPage = (LoadingPage) LayoutInflater.from(this).inflate(R.layout.layout_loading, null);
        }
        if (mLoadingPage.getParent() == null) {
            group.addView(mLoadingPage);
        }
        mLoadingPage.show(mode);
    }


    protected void dismissLoadingPage() {
        if (mLoadingPage != null) {
            mLoadingPage.dismiss();
        }
    }

}
