package com.app.aljazierah.object.request;

/**
 * Created by Mustanser Iqbal on 12/5/2017.
 */

public class OrderRequest {

    private String user_id;
    private String client_id;
    private int page;
    private String ord_id;

    public OrderRequest(String user_id, int page) {
        this.user_id = user_id;
        this.page = page;
    }

    public OrderRequest(String user_id, String ord_id) {
        this.user_id = user_id;
        this.ord_id = ord_id;
    }

    public OrderRequest(String client_id, String ord_id,String getorderdetail) {
        this.client_id = client_id;
        this.ord_id = ord_id;
    }

    public OrderRequest(String ord_id) {
        this.ord_id = ord_id;
    }
}
