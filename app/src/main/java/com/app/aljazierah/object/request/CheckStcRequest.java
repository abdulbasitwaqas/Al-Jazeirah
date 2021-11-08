package com.app.aljazierah.object.request;

public class CheckStcRequest {
    private String mobile;
    private String user_id;
    private String lang;


    public CheckStcRequest(String user_id,String mobile,String lang){
        this.mobile=mobile;
        this.user_id=user_id;
        this.lang=lang;


    }
    public CheckStcRequest(String user_id,String lang){

        this.user_id=user_id;
        this.lang=lang;


    }

}
