package com.app.aljazierah.object.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mustanser Iqbal on 12/11/2017.
 */

public class CheckOut {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("amount_vat")
    @Expose
    private String amount_vat;
    @SerializedName("price_without_vat")
    @Expose
    private String price_without_vat;
    @SerializedName("payment_type")
    @Expose
    private String payment_type;
    @SerializedName("addressData")
    @Expose
    private AddressData addressData;
    @SerializedName("cart")
    @Expose
    private List<Cart> cart = null;
    @SerializedName("prefered_date")
    @Expose
    private String prefered_date;

    @SerializedName("prefered_time")
    @Expose
    private String prefered_time;



    private final static long serialVersionUID = 3335497248279930278L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AddressData getAddressData() {
        return addressData;
    }

    public void setAddressData(AddressData addressData) {
        this.addressData = addressData;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public String getAmount_vat() {
        return amount_vat;
    }

    public void setAmount_vat(String amount_vat) {
        this.amount_vat = amount_vat;
    }

    public String getPrice_without_vat() {
        return price_without_vat;
    }

    public void setPrice_without_vat(String price_without_vat) {
        this.price_without_vat = price_without_vat;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPrefered_date() {
        return prefered_date;
    }

    public void setPrefered_date(String prefered_date) {
        this.prefered_date = prefered_date;
    }

    public String getPrefered_time() {
        return prefered_time;
    }

    public void setPrefered_time(String prefered_time) {
        this.prefered_time = prefered_time;
    }

    public static class Cart implements Serializable {

        @SerializedName("dish_id")
        @Expose
        private String dishId;
        @SerializedName("count")
        @Expose
        private int count;
        @SerializedName("price")
        @Expose
        private String price;
        private final static long serialVersionUID = 2627588565857742878L;

        public String getDishId() {
            return dishId;
        }

        public void setDishId(String dishId) {
            this.dishId = dishId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

    }

    public static class AddressData implements Serializable {

        @SerializedName("ord_address_id")
        @Expose
        private String ordAddressId;
        private final static long serialVersionUID = -5871969410649873815L;

        public String getOrdAddressId() {
            return ordAddressId;
        }

        public void setOrdAddressId(String ordAddressId) {
            this.ordAddressId = ordAddressId;
        }

    }
}
