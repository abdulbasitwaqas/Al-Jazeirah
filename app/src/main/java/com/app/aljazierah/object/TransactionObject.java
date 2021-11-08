package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionObject {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("transaction_type")
    @Expose
    private String transactionType;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("sdt")
    @Expose
    private String sdt;

    public String getOrderId() {
        if(orderId!=null)
            return orderId;
            else
                return "";

        }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
