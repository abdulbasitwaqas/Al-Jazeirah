package com.app.aljazierah.object.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mustanser Iqbal on 12/5/2017.
 */

public class PaymentStatus {

    @SerializedName("TransactionID")
    @Expose
    private String transactionID = "";
    @SerializedName("user_id")
    @Expose
    private String userId = "";

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
