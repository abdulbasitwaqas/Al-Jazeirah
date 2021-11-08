package com.app.aljazierah.object.hyperpayPayment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mustanser Iqbal on 12/11/2017.
 */

public class CheckOutWithHyperPay {
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

    @SerializedName("recurring")
    @Expose

    private String recurring;

    @SerializedName("referral")
    @Expose

    private String referral;
    @SerializedName("include_wallet")
    @Expose
    private String include_wallet;

    @SerializedName("promocode")
    @Expose
    private String promocode;

    @SerializedName("transactionID")
    @Expose
    private String transactionID;

    private String cart_id;
    private String hyperResponse;

    public String getHyperResponse() {
        return hyperResponse;
    }

    public void setHyperResponse(String hyperResponse) {
        this.hyperResponse = hyperResponse;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getRecurring() {
        return recurring;
    }

    public void setRecurring(String recurring) {
        this.recurring = recurring;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getInclude_wallet() {
        return include_wallet;
    }

    public void setInclude_wallet(String include_wallet) {
        this.include_wallet = include_wallet;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
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

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
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

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
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
        String ord_address_id;

        @SerializedName("add_area")
        @Expose
        String add_area;

        @SerializedName("add_name")
        @Expose
        String add_name;

        @SerializedName("add_detail")
        @Expose
        String add_detail;

        @SerializedName("add_latitude")
        @Expose
        String add_latitude;

        @SerializedName("add_longitude")
        @Expose
        String add_longitude;

        @SerializedName("add_street_name")
        @Expose
        String add_street_name;

        @Override
        public String toString() {
            return "{" +
                    "ordAddressId:'" + ord_address_id + '\'' +
                    '}';
        }

        public String getOrd_address_id() {
            return ord_address_id;
        }

        public void setOrd_address_id(String ord_address_id) {
            this.ord_address_id = ord_address_id;
        }

        public String getAdd_area() {
            return add_area;
        }

        public void setAdd_area(String add_area) {
            this.add_area = add_area;
        }

        public String getAdd_name() {
            return add_name;
        }

        public void setAdd_name(String add_name) {
            this.add_name = add_name;
        }

        public String getAdd_detail() {
            return add_detail;
        }

        public void setAdd_detail(String add_detail) {
            this.add_detail = add_detail;
        }

        public String getAdd_latitude() {
            return add_latitude;
        }

        public void setAdd_latitude(String add_latitude) {
            this.add_latitude = add_latitude;
        }

        public String getAdd_longitude() {
            return add_longitude;
        }

        public void setAdd_longitude(String add_longitude) {
            this.add_longitude = add_longitude;
        }

        public String getAdd_street_name() {
            return add_street_name;
        }

        public void setAdd_street_name(String add_street_name) {
            this.add_street_name = add_street_name;
        }
    }
}
