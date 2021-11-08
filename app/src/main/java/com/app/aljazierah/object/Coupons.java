package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Coupons implements Serializable {
    @SerializedName("coupon_qty")
    @Expose
    private String coupon_qty = "0.0";
    @SerializedName("products")
    @Expose
    private ArrayList<String> products;


    public ArrayList<String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }



    public String getCoupon_qty() {
        return coupon_qty;
    }

    public void setCoupon_qty(String coupon_qty) {
        this.coupon_qty = coupon_qty;
    }


}
