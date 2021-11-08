package com.app.aljazierah.object.CartParamResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public  class LatestOrder implements Serializable {

    @SerializedName("order_code")
    @Expose
    private String order_code;
    @SerializedName("address_id")
    @Expose
    private String address_id;

    @SerializedName("order_items")
    @Expose
    private ArrayList<OrderItems> order_items;







    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public ArrayList<OrderItems> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(ArrayList<OrderItems> order_items) {
        this.order_items = order_items;
    }
}
