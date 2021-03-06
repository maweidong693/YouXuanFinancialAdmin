package com.weiwu.youxuanfinanciala.dmin;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.qiniu.android.utils.StringUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.weiwu.youxuanfinanciala.dmin.base.BaseActivity;
import com.weiwu.youxuanfinanciala.dmin.data.entity.InvData;
import com.weiwu.youxuanfinanciala.dmin.data.entity.VipList;
import com.weiwu.youxuanfinanciala.dmin.data.repositories.HomeRepository;
import com.weiwu.youxuanfinanciala.dmin.data.request.VipRequest;
import com.weiwu.youxuanfinanciala.dmin.utils.SPUtils;

public class MainActivity extends BaseActivity implements MyContract.IHomeView, View.OnClickListener {
    private static final int REQUEST_GPS = 1;
    private Toolbar mMainToolbar;
    private SmartRefreshLayout mSrlList;
    private RecyclerView mRvList;
    private MyContract.IHomePresenter mPresenter;
    private VipListAdapter mAdapter;
    private int page = 1;
    private Button mBtOutLogin;
    private Button mBtInv;
    private InvPopup mInvPopup;
    private ImageView mIvInv;
    private Button mBtinvPic;
    private Button mBtinvUrl;
    private String inv_url;
    private Bitmap qrBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).init();
        setPresenter(new HomePresenter(HomeRepository.getInstance()));
        initView();
    }

    private void showNormalDialog() {
        /* @setIcon ?????????????????????
         * @setTitle ?????????????????????
         * @setMessage ???????????????????????????
         * setXXX????????????Dialog???????????????????????????????????????
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("??????");
        normalDialog.setMessage("?????????????????????");
        normalDialog.setPositiveButton("??????",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean b = SPUtils.clearValue(AppConstant.USER);
                        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                    }
                });
        normalDialog.setNegativeButton("??????",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // ??????
        normalDialog.show();
    }

    public void initView() {
        mMainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mSrlList = (SmartRefreshLayout) findViewById(R.id.srl_list);
        mSrlList.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page = page + 1;
                mPresenter.getList(new VipRequest(String.valueOf(page), "10"));
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mAdapter.clearList();
                mPresenter.getList(new VipRequest(String.valueOf(page), "10"));
            }
        });
        mRvList = (RecyclerView) findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new VipListAdapter();
        mRvList.setAdapter(mAdapter);

        mAdapter.setVipItemClickListener(new VipListAdapter.IVipItemClickListener() {
            @Override
            public void mItemClickListener(VipList.DataBean dataBean) {
                int id = dataBean.getId();
                Intent intent = new Intent(MainActivity.this, VipDetailActivity.class);
                intent.putExtra(AppConstant.VIP_DETAIL, id);
                startActivity(intent);
            }
        });

        mPresenter.getList(new VipRequest("1", "10"));
        mBtOutLogin = (Button) findViewById(R.id.bt_out_login);
        mBtOutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNormalDialog();
            }
        });
        mInvPopup = new InvPopup(this);
        mIvInv = mInvPopup.findViewById(R.id.iv_inv);
        mBtinvUrl = mInvPopup.findViewById(R.id.bt_inv_url);
        mBtinvUrl.setOnClickListener(this);
        mBtinvPic = mInvPopup.findViewById(R.id.bt_inv_pic);
        mBtinvPic.setOnClickListener(this);

        mBtInv = (Button) findViewById(R.id.bt_inv);
        mBtInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getInvUrl();
                requestPermissions();
            }
        });
    }

    @Override
    public void listResult(VipList data) {
        if (data != null) {
            mAdapter.setList(data.getData());
            mSrlList.finishRefresh().finishLoadMore();
        }
    }

    @Override
    public void invUrlResult(InvData data) {
        if (data != null) {
            inv_url = data.getData().getInv_url();
            qrBitmap = (Bitmap) CodeUtils.createImage(inv_url, 300, 300, BitmapFactory.decodeResource(getResources(), R.mipmap.icon));
            Glide.with(this).load(qrBitmap).into(mIvInv);

        }
    }

    @Override
    public void invUrlFail(String msg, int code) {
        if (code == 401) {
            showToast("?????????????????????????????????????????????");
            boolean b = SPUtils.clearValue(AppConstant.USER);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }

    @Override
    public void listFail(String msg, int code) {
        if (code == 401) {
            showToast("?????????????????????????????????????????????");
            boolean b = SPUtils.clearValue(AppConstant.USER);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }

    @Override
    public void setPresenter(MyContract.IHomePresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Activity getActivityObject() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_inv_url:
                if (!StringUtils.isNullOrEmpty(inv_url)) {
                    //???????????????????????????
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // ?????????????????????ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", inv_url);
                    // ???ClipData?????????????????????????????????
                    cm.setPrimaryClip(mClipData);
                    showToast("???????????????????????????????????????????????????????????????");
                } else {
                    showToast("???????????????????????????");
                }
                mInvPopup.dismiss();
                break;

            case R.id.bt_inv_pic:
                Uri uri = ImageUtils.saveAlbum(this, qrBitmap, Bitmap.CompressFormat.PNG, 2, true);
                showToast("???????????????????????????????????????????????????");
                mInvPopup.dismiss();
                break;
        }
    }

    public void requestPermissions() {
        //REQUEST_GPS????????????int??????????????????private final int REQUEST_GPS = 1;
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}
                , REQUEST_GPS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//?????????????????????????????????
            mInvPopup.showPopupWindow();
        } else {
//             Permission Denied3
            Toast.makeText(this, "????????????????????????????????????????????????", Toast.LENGTH_LONG).show();
        }

    }
}