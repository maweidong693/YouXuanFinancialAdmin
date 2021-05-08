package com.weiwu.youxuanfinanciala.dmin.data.request;

/**
 *  
 * author : 马伟东
 * email : maweidong693@163.com
 * date : 2021/5/3 17:05 
 */
public class VipDetailRequest {
    private String member_id;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public VipDetailRequest() {
    }

    public VipDetailRequest(String member_id) {
        this.member_id = member_id;
    }
}
