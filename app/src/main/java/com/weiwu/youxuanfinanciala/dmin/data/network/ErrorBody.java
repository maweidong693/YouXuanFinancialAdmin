package com.weiwu.youxuanfinanciala.dmin.data.network;

public class ErrorBody {

    private int code;
    private String msg;

    @Override
    public String toString() {
        return "ErrorBody{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    public ErrorBody() {
    }

    public ErrorBody(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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
}
