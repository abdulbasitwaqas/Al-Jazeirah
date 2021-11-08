package com.app.aljazierah.object.request;

/**
 * Created by Mustanser Iqbal on 12/5/2017.
 */

public class HyperpayKeyResponse {

    private String Amount = "";
    //private String ApprovalCode = "";
   // private String CurrencyISOCode = "";
    //private String GatewayName = "";
   // private String GatewayStatusCode = "";
    //private String GatewayStatusDescription = "";
    private String MerchantID = "";
   // private String MessageID = "";
   // private String RRN = "";
   // private String SecureHash = "";
    private String StatusCode = "";
    private String StatusDescription = "";
    private String TransactionID = "";

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    private String refundId = "";

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

   /* public String getCurrencyISOCode() {
        return CurrencyISOCode;
    }

    public void setCurrencyISOCode(String currencyISOCode) {
        CurrencyISOCode = currencyISOCode;
    }*/

    public String getMerchantID() {
        return MerchantID;
    }

    public void setMerchantID(String merchantID) {
        MerchantID = merchantID;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public String getStatusDescription() {
        return StatusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        StatusDescription = statusDescription;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }
//private String ResponseHashMatch = "";
    //private String execCode = "";


}
