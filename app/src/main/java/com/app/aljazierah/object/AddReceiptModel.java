package com.app.aljazierah.object;

import okhttp3.RequestBody;

public class AddReceiptModel {


    private RequestBody image;
    private String order_id;
    private String user_id;


    public AddReceiptModel(RequestBody image, String order_id, String user_id) {
        this.image = image;
        this.order_id = order_id;
        this.user_id = user_id;
    }
}
