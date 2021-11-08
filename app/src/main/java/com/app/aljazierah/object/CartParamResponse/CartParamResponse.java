package com.app.aljazierah.object.CartParamResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartParamResponse implements Serializable {

    @SerializedName("client_wallet_balance")
    @Expose
    private String clientWalletBalance;
    @SerializedName("delivery_slots")
    @Expose
    private List<DeliverySlot> deliverySlots = null;
    @SerializedName("show_referral")
    @Expose
    private String showReferral;

    @SerializedName("payment_methods")
    @Expose
    private List<PaymentMethod> paymentMethods = null;
    @SerializedName("banks")
    @Expose
    private List<BanksModel> banks = null;

    @SerializedName("latest_orders")
    @Expose
    private ArrayList<LatestOrder> latest_orders = null;

    @SerializedName("show_stc_tamayouz")
    @Expose
    private String show_stc_tamayouz;

    @SerializedName("stc_tamayouz_min_qty")
    @Expose
    private String stc_tamayouz_min_qty;

    public String getDefault_promo() {
        return default_promo;
    }

    public void setDefault_promo(String default_promo) {
        this.default_promo = default_promo;
    }

    @SerializedName("default_promo")
    @Expose
    private String default_promo = "";

    @SerializedName("delivery_fee")
    @Expose
    private String delivery_fee = "";

    public String getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(String delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public String getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(String credit_limit) {
        this.credit_limit = credit_limit;
    }

    @SerializedName("credit_limit")
    @Expose
    private String credit_limit="";

    @SerializedName("wallet_discount")
    @Expose
    private String wallet_discount="";
    @SerializedName("delivery_formula")
    @Expose
    private String delivery_formula="";





    public String getWallet_discount() {
        return wallet_discount;
    }

    public void setWallet_discount(String wallet_discount) {
        this.wallet_discount = wallet_discount;
    }

    public String getStc_tamayouz_min_qty() {
        return stc_tamayouz_min_qty;
    }

    public void setStc_tamayouz_min_qty(String stc_tamayouz_min_qty) {
        this.stc_tamayouz_min_qty = stc_tamayouz_min_qty;
    }

    public String getShow_stc_tamayouz() {
        return show_stc_tamayouz;
    }

    public void setShow_stc_tamayouz(String show_stc_tamayouz) {
        this.show_stc_tamayouz = show_stc_tamayouz;
    }

    public String getClientWalletBalance() {
        return clientWalletBalance;
    }



    public void setClientWalletBalance(String clientWalletBalance) {
        this.clientWalletBalance = clientWalletBalance;
    }

    public List<DeliverySlot> getDeliverySlots() {
        return deliverySlots;
    }

    public void setDeliverySlots(List<DeliverySlot> deliverySlots) {
        this.deliverySlots = deliverySlots;
    }

    public String getShowReferral() {
        return showReferral;
    }

    public void setShowReferral(String showReferral) {
        this.showReferral = showReferral;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<BanksModel> getBanks() {
        return banks;
    }

    public void setBanks(List<BanksModel> banks) {
        this.banks = banks;
    }

    public ArrayList<LatestOrder> getLatest_orders() {
        return latest_orders;
    }

    public void setLatest_orders(ArrayList<LatestOrder> latest_orders) {
        this.latest_orders = latest_orders;
    }


    public String getDelivery_formula() {
        return delivery_formula;
    }

    public void setDelivery_formula(String delivery_formula) {
        this.delivery_formula = delivery_formula;
    }
}




