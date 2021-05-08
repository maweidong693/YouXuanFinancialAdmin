package com.weiwu.youxuanfinanciala.dmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weiwu.youxuanfinanciala.dmin.data.entity.VipList;

import java.util.ArrayList;
import java.util.List;

/**
 *  
 * author : 马伟东
 * email : maweidong693@163.com
 * date : 2021/4/30 18:05 
 */
public class VipListAdapter extends RecyclerView.Adapter<VipListAdapter.VipListViewHolder> {

    private List<VipList.DataBean> mList = new ArrayList<>();


    public void setList(List<VipList.DataBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearList() {
        mList.clear();
    }

    @NonNull
    @Override
    public VipListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VipListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vip, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VipListViewHolder holder, int position) {
        VipList.DataBean data = mList.get(position);
        holder.setData(data);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVipItemClickListener != null) {
                    mVipItemClickListener.mItemClickListener(data);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class VipListViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private TextView mTvSex;
        private TextView mTvRealStatus;
        private TextView mTvPhone;
        private TextView mTvTime;

        public VipListViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mTvSex = (TextView) itemView.findViewById(R.id.tv_sex);
            mTvRealStatus = (TextView) itemView.findViewById(R.id.tv_real_status);
            mTvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }

        public void setData(VipList.DataBean data) {
            mTvName.setText(data.getName());
            mTvSex.setText(data.getSex());
            mTvRealStatus.setText(data.getIs_real());
            mTvPhone.setText(data.getMobile());
            mTvTime.setText(data.getRegister_time());
        }
    }

    private IVipItemClickListener mVipItemClickListener;

    public void setVipItemClickListener(IVipItemClickListener vipItemClickListener) {
        mVipItemClickListener = vipItemClickListener;
    }

    public interface IVipItemClickListener {
        void mItemClickListener(VipList.DataBean dataBean);
    }
}
