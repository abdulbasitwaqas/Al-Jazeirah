package com.app.aljazierah.object.request;

public class CheckUserOTP {
    private String mobile;
    private String otp;

    public CheckUserOTP(String mobile, String otp) {
        this.mobile = mobile;
        this.otp = otp;
    }
}
