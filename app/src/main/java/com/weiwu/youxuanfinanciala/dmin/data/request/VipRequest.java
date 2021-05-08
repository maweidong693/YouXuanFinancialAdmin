package com.weiwu.youxuanfinanciala.dmin.data.request;

/**
 *  
 * author : 马伟东
 * email : maweidong693@163.com
 * date : 2021/4/30 17:22 
 */
public class VipRequest {

    private String page;
    private String size;

    public VipRequest() {
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public VipRequest(String page, String size) {
        this.page = page;
        this.size = size;
    }
}
