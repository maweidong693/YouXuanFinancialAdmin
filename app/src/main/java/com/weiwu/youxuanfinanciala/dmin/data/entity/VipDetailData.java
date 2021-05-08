package com.weiwu.youxuanfinanciala.dmin.data.entity;

/**
 *  
 * author : 马伟东
 * email : maweidong693@163.com
 * date : 2021/5/3 17:03 
 */
public class VipDetailData {

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
        private String real_status;
        private String bank_status;
        private String name;
        private String mobile;
        private int buy_order_num;
        private String buy_order_money;
        private int sell_order_num;
        private String sell_order_money;
        private double income;

        public String getReal_status() {
            return real_status;
        }

        public void setReal_status(String real_status) {
            this.real_status = real_status;
        }

        public String getBank_status() {
            return bank_status;
        }

        public void setBank_status(String bank_status) {
            this.bank_status = bank_status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getBuy_order_num() {
            return buy_order_num;
        }

        public void setBuy_order_num(int buy_order_num) {
            this.buy_order_num = buy_order_num;
        }

        public String getBuy_order_money() {
            return buy_order_money;
        }

        public void setBuy_order_money(String buy_order_money) {
            this.buy_order_money = buy_order_money;
        }

        public int getSell_order_num() {
            return sell_order_num;
        }

        public void setSell_order_num(int sell_order_num) {
            this.sell_order_num = sell_order_num;
        }

        public String getSell_order_money() {
            return sell_order_money;
        }

        public void setSell_order_money(String sell_order_money) {
            this.sell_order_money = sell_order_money;
        }

        public double getIncome() {
            return income;
        }

        public void setIncome(double income) {
            this.income = income;
        }
    }
}
