package com.app.aljazierah.object.RetingRequest;

import java.util.List;

public class RetingRequest {
    String client_id,order_id,block_popup;
    List<Items> items;
    public RetingRequest(String client_id, String order_id,List<Items> items,String block_popup) {
        this.client_id = client_id;
        this.order_id = order_id;
        this.items = items;
        this.block_popup = block_popup;
    }



}
