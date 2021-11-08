package com.app.aljazierah.object.hyperpayPayment;

import com.app.aljazierah.object.PlaceOrder;


public class GetChectoutId {

    String amount;
    String customer_id;
    String mobile_number ;
    PlaceOrder temp_order;


    public GetChectoutId(String amount, String client_id,String mobile_number , PlaceOrder temp_order) {
        this.amount=amount;
        this.customer_id =client_id;
        this.mobile_number =mobile_number ;
        this.temp_order = temp_order;

    }

}
