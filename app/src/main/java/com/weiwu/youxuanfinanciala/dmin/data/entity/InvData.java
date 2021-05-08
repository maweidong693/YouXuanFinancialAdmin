package com.weiwu.youxuanfinanciala.dmin.data.entity;

/**
 *  
 * author : 马伟东
 * email : maweidong693@163.com
 * date : 2021/5/3 20:55 
 */
public class InvData {
    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String inv_url;

        public String getInv_url() {
            return inv_url;
        }

        public void setInv_url(String inv_url) {
            this.inv_url = inv_url;
        }
    }
}
