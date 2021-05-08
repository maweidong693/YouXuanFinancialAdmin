package com.weiwu.youxuanfinanciala.dmin.data.request;

/**
 *  
 * author : 马伟东
 * email : maweidong693@163.com
 * date : 2021/4/30 17:13 
 */
public class LoginRequest {
private String tel;
private String password;

    public LoginRequest() {
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRequest(String tel, String password) {
        this.tel = tel;
        this.password = password;
    }
}
