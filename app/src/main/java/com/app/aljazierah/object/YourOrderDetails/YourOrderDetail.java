package com.app.aljazierah.object.YourOrderDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class YourOrderDetail {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("order")
    @Expose
    private Order order;

    @SerializedName("wallet_used")
    @Expose
    private String walletUsed;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getWalletUsed() {
        return walletUsed;
    }

    public void setWalletUsed(String walletUsed) {
        this.walletUsed = walletUsed;
    }

}
