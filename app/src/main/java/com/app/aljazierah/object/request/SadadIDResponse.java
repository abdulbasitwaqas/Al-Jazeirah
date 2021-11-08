package com.app.aljazierah.object.request;

/**
 * Created by Mustanser Iqbal on 12/5/2017.
 */

public class SadadIDResponse {

    private String Amount = "";
    private String ApprovalCode = "";
    private String CurrencyISOCode = "";
    private String GatewayName = "";
    private String GatewayStatusCode = "";
    private String GatewayStatusDescription = "";
    private String MerchantID = "";
    private String MessageID = "";
    private String RRN = "";
    private String SecureHash = "";
    private String StatusCode = "";
    private String StatusDescription = "";
    private String TransactionID = "";
    private String ResponseHashMatch = "";
    private String execCode = "";

    public void setTransactionID(String transactionID) {
        this.TransactionID = transactionID;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getApprovalCode() {
        return ApprovalCode;
    }

    public void setApprovalCode(String approvalCode) {
        ApprovalCode = approvalCode;
    }

    public String getCurrencyISOCode() {
        return CurrencyISOCode;
    }

    public void setCurrencyISOCode(String currencyISOCode) {
        CurrencyISOCode = currencyISOCode;
    }

    public String getGatewayName() {
        return GatewayName;
    }

    public void setGatewayName(String gatewayName) {
        GatewayName = gatewayName;
    }

    public String getGatewayStatusCode() {
        return GatewayStatusCode;
    }

    public void setGatewayStatusCode(String gatewayStatusCode) {
        GatewayStatusCode = gatewayStatusCode;
    }

    public String getGatewayStatusDescription() {
        return GatewayStatusDescription;
    }

    public void setGatewayStatusDescription(String gatewayStatusDescription) {
        GatewayStatusDescription = gatewayStatusDescription;
    }

    public String getMerchantID() {
        return MerchantID;
    }

    public void setMerchantID(String merchantID) {
        MerchantID = merchantID;
    }

    public String getMessageID() {
        return MessageID;
    }

    public void setMessageID(String messageID) {
        MessageID = messageID;
    }

    public String getRRN() {
        return RRN;
    }

    public void setRRN(String RRN) {
        this.RRN = RRN;
    }

    public String getSecureHash() {
        return SecureHash;
    }

    public void setSecureHash(String secureHash) {
        SecureHash = secureHash;
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

    public String getResponseHashMatch() {
        return ResponseHashMatch;
    }

    public void setResponseHashMatch(String responseHashMatch) {
        ResponseHashMatch = responseHashMatch;
    }

    public String getExecCode() {
        return execCode;
    }

    public void setExecCode(String execCode) {
        this.execCode = execCode;
    }
}
