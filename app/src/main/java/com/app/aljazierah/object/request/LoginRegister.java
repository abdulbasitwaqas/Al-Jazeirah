package com.app.aljazierah.object.request;

public class LoginRegister {
    private String mobile;
    private String user_name;
    private String password;


    public LoginRegister(String mobile,String user_name,String password) {
        this.mobile = mobile;
        this.user_name = user_name;
        this.password = password;
    }
}
