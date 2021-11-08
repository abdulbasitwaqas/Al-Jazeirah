package com.app.aljazierah.object.request;

import com.app.aljazierah.object.PlaceOrder;

import java.util.List;

public class PromocodeRequest {

    private int  area_id;
    private String  promo_code;
    private String  order_value;
    private int  client_id;
    private String  stc_otp;
    private String  mobile;
    private String  add_type;
    private List<PlaceOrder.Cart> cart;

    public PromocodeRequest(int area_id,String add_type,String promo_code,String order_value,int client_id, List<PlaceOrder.Cart> cart){
        this.area_id=area_id;
        this.promo_code=promo_code;
        this.order_value=order_value;
        this.client_id=client_id;
        this.cart=cart;
        this.add_type=add_type;
    }




    public PromocodeRequest(int area_id,String add_type,String promo_code,String order_value,int client_id,
                            List<PlaceOrder.Cart> cart,String  stc_otp,String  mobile){
        this.area_id=area_id;
        this.promo_code=promo_code;
        this.order_value=order_value;
        this.client_id=client_id;
        this.cart=cart;
        this.stc_otp=stc_otp;
        this.mobile=mobile;
        this.add_type=add_type;

    }
}
