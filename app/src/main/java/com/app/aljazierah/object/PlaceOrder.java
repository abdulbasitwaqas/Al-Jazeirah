package com.app.aljazierah.object;

import com.app.aljazierah.object.request.HyperpayKeyResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PlaceOrder implements Serializable{
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("include_wallet")
    @Expose
    private String include_wallet = "0";

    @SerializedName("coupons")
    @Expose
    private String coupons = "0";

    public String getCoupons() {
        return coupons;
    }

    public void setCoupons(String coupons) {
        this.coupons = coupons;
    }

    @SerializedName("promocode")
    @Expose
    private String promocode;

    @SerializedName("stc_otp")
    @Expose
    private String stc_otp;

    @SerializedName("payment_type")
    @Expose


    private String payment_type;
    @SerializedName("price_without_vat")
    @Expose

    private String price_without_vat;
    @SerializedName("amount_vat")
    @Expose

    private String amount_vat;

    @SerializedName("prefered_time")
    @Expose

    private String prefered_time;

    @SerializedName("prefered_date")
    @Expose

    private String prefered_date;

    @SerializedName("addressData")
    @Expose

    private addressData addressData;

    @SerializedName("recurring")
    @Expose

    private String recurring;

    @SerializedName("referral")
    @Expose

    private String referral;

    @SerializedName("stc_promo_code")
    @Expose

    private String stc_promo_code;

    @SerializedName("checkout_time")
    @Expose

    private String checkout_time;

    public String getCheckout_time() {
        return checkout_time;
    }

    public void setCheckout_time(String checkout_time) {
        this.checkout_time = checkout_time;
    }

    @SerializedName("cart")
    @Expose

    private List<Cart> cart;

    @SerializedName("foc_items")
    @Expose
    private List<CartPromotion> foc_items;

    private String transaction_id;

    //private JsonObject  applepay_response;
    private String hyperResponse;

    private String  registrationId;

    @SerializedName("response")
    @Expose
    private HyperpayKeyResponse response;
    @SerializedName("is_bank")
    @Expose
    private String is_bank;

    public HyperpayKeyResponse getResponse() {
        return response;
    }

    public void setResponse(HyperpayKeyResponse response) {
        this.response = response;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }


    public String getHyperResponse() {
        return hyperResponse;
    }

    public void setHyperResponse(String hyperResponse) {
        this.hyperResponse = hyperResponse;
    }

    public String getTransaction_id() {
        return transaction_id;
    }
    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }



    public List<CartPromotion> getFoc_items() {
        return foc_items;
    }

    public void setFoc_items(List<CartPromotion> foc_items) {
        this.foc_items = foc_items;
    }


    public PlaceOrder(){

    }

    public String getStc_promo_code() {
        return stc_promo_code;
    }

    public void setStc_promo_code(String stc_promo_code) {
        this.stc_promo_code = stc_promo_code;
    }

    public String getStc_otp() {
        return stc_otp;
    }

    public void setStc_otp(String stc_otp) {
        this.stc_otp = stc_otp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPrice_without_vat() {
        return price_without_vat;
    }

    public void setPrice_without_vat(String price_without_vat) {
        this.price_without_vat = price_without_vat;
    }

    public String getAmount_vat() {
        return amount_vat;
    }

    public void setAmount_vat(String amount_vat) {
        this.amount_vat = amount_vat;
    }

    public String getPrefered_time() {
        return prefered_time;
    }

    public void setPrefered_time(String prefered_time) {
        this.prefered_time = prefered_time;
    }

    public String getPrefered_date() {
        return prefered_date;
    }

    public void setPrefered_date(String prefered_date) {
        this.prefered_date = prefered_date;
    }

    public com.app.aljazierah.object.addressData getAddressData() {
        return addressData;
    }

    public void setAddressData(com.app.aljazierah.object.addressData addressData) {
        this.addressData = addressData;
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

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public String getIs_bank() {
        return is_bank;
    }

    public void setIs_bank(String is_bank) {
        this.is_bank = is_bank;
    }

    public static  class  CartPromotion implements Serializable{

        @SerializedName("id")
        @Expose
        private String Id;
        @SerializedName("quantity")
        @Expose
        private int count;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
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

        @SerializedName("productTitleEn")
        @Expose
        private String productTitleEn;

        public String getProductTitleEn() {
            return productTitleEn;
        }

        public void setProductTitleEn(String productTitleEn) {
            this.productTitleEn = productTitleEn;
        }

        public boolean haspromocode;

        private String product_price;


        public String getProduct_price() {
            return product_price;
        }

        public void setProduct_price(String product_price) {
            this.product_price = product_price;
        }

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


    public PlaceOrder(String user_id, String include_wallet, String coupons, String promocode, String stc_otp, String payment_type, String price_without_vat, String amount_vat, String prefered_time, String prefered_date, com.app.aljazierah.object.addressData addressData, String recurring, String referral, String stc_promo_code, String checkout_time, List<Cart> cart, List<CartPromotion> foc_items, String transaction_id, String hyperResponse, String registrationId, HyperpayKeyResponse response, String is_bank) {
        this.user_id = user_id;
        this.include_wallet = include_wallet;
        this.coupons = coupons;
        this.promocode = promocode;
        this.stc_otp = stc_otp;
        this.payment_type = payment_type;
        this.price_without_vat = price_without_vat;
        this.amount_vat = amount_vat;
        this.prefered_time = prefered_time;
        this.prefered_date = prefered_date;
        this.addressData = addressData;
        this.recurring = recurring;
        this.referral = referral;
        this.stc_promo_code = stc_promo_code;
        this.checkout_time = checkout_time;
        this.cart = cart;
        this.foc_items = foc_items;
        this.transaction_id = transaction_id;
        this.hyperResponse = hyperResponse;
        this.registrationId = registrationId;
        this.response = response;
        this.is_bank = is_bank;
    }
}
