package com.app.aljazierah.object.request;

public class CancelOrderRequest {

    private String client_id;
    private String order_id;
    private String cancel_reason_id;
    private String refund_to_wallet;
    private String refund_to_bank;



    public CancelOrderRequest(String client_id, String order_id, String cancel_reason_id,String refund_to_wallet,String refund_to_bank) {
        this.client_id = client_id;
        this.order_id = order_id;
        this.cancel_reason_id = cancel_reason_id;
        this.refund_to_wallet = refund_to_wallet;
        this.refund_to_bank = refund_to_bank;
    }
}
