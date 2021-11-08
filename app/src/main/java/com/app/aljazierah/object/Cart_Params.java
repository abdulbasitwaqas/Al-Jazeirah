package com.app.aljazierah.object;

import java.util.List;

public class Cart_Params {
    int client_id;
    String fcmToken;

    String area_id;
    String city_id;
    String address_id;
    List<PlaceOrder.Cart> cart;

    public Cart_Params(String area_id,String city_id,String address_id,int userID,String fcmToken,List<PlaceOrder.Cart> cart) {
        this.client_id = userID;
        this.fcmToken=fcmToken;
        this.cart=cart;
        this.area_id=area_id;
        this.city_id=city_id;
        this.address_id=address_id;
    }
}
