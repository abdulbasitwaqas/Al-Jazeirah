package com.app.aljazierah.object.request;

public class AddToWishStockAlertRequest {

    String user_id,product_id,type,action;

    public AddToWishStockAlertRequest(String user_id, String product_id, String type, String action) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.type = type;
        this.action = action;
    }
}
