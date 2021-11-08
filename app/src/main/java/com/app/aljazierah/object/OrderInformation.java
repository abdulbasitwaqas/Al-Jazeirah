package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderInformation {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("total_price")
    @Expose
    private String total_price;

    @SerializedName("order_code")
    @Expose
    private String order_code;

    @SerializedName("order_created_date")
    @Expose
    private String order_created_date;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getOrder_created_date() {
        return order_created_date;
    }

    public void setOrder_created_date(String order_created_date) {
        this.order_created_date = order_created_date;
    }
}
