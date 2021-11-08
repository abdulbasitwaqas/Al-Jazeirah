package com.app.aljazierah.object.request;

public class FavouriteRequest {

    String client_id,order_id,make_fav;

    public FavouriteRequest(String client_id, String order_id, String make_fav) {
        this.client_id = client_id;
        this.order_id = order_id;
        this.make_fav = make_fav;
    }
}
