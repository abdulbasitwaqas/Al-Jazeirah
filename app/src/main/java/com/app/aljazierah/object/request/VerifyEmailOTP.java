package com.app.aljazierah.object.request;

public class VerifyEmailOTP {
    private String user_id;
    private String otp;

    public VerifyEmailOTP(String user_id, String otp) {
        this.user_id = user_id;
        this.otp = otp;
    }
}
