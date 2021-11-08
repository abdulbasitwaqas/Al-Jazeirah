package com.app.aljazierah.object.YourOrderDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("discount_amount")
    @Expose
    private String discountAmount;

    @SerializedName("after_vat")
    @Expose
    private String after_vat="0";

    public String getAfter_vat() {
        return after_vat;
    }

    public void setAfter_vat(String after_vat) {
        this.after_vat = after_vat;
    }

    public String getQitaf_rewardpoints() {
        return qitaf_rewardpoints;
    }

    public void setQitaf_rewardpoints(String qitaf_rewardpoints) {
        this.qitaf_rewardpoints = qitaf_rewardpoints;
    }

    @SerializedName("qitaf_rewardpoints")
    @Expose
    private String qitaf_rewardpoints;
    @SerializedName("wallet_transaction_id")
    @Expose
    private String walletTransactionId;
    @SerializedName("ord_id")
    @Expose
    private String ordId;
    @SerializedName("ord_user_id")
    @Expose
    private String ordUserId;
    @SerializedName("ord_total_price")
    @Expose
    private String ordTotalPrice;
    @SerializedName("ord_note")
    @Expose
    private String ordNote;
    @SerializedName("delivery_fee")
    @Expose
    private String ordDeliveryCharge;


    @SerializedName("ord_tax_value")
    @Expose
    private String ordTaxValue;
    @SerializedName("current_status")
    @Expose
    private String currentStatus;

    @SerializedName("enable_return_sales")
    @Expose
    private String enable_return_sales="0";
    @SerializedName("bank_receipt")
    @Expose
    private String bank_receipt;

    @SerializedName("invoice_number")
    @Expose
    private String invoiceNumber;
    @SerializedName("delv_type")
    @Expose
    private String delvType;
    @SerializedName("ord_date")
    @Expose
    private String ordDate;
    @SerializedName("iscancelable")
    @Expose
    private String iscancelable;
    @SerializedName("iseditable")
    @Expose
    private String iseditable;
    @SerializedName("isreorderable")
    @Expose
    private String isreorderable;
    @SerializedName("order_pin")
    @Expose
    private Object orderPin;
    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("prefered_date")
    @Expose
    private String preferedDate;
    @SerializedName("prefered_time")
    @Expose
    private String preferedTime;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("price_without_vat")
    @Expose
    private String priceWithoutVat;
    @SerializedName("amount_vat")
    @Expose
    private String amountVat;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;

    public String getWallet_discount() {
        return wallet_discount;
    }

    public void setWallet_discount(String wallet_discount) {
        this.wallet_discount = wallet_discount;
    }

    @SerializedName("wallet_discount")
    @Expose
    private String wallet_discount= "0";
    @SerializedName("payable_amount")
    @Expose
    private String payableAmount;



    @SerializedName("wallet_amount")
    @Expose
    private String walletAmount ;
    @SerializedName("payment_ref")
    @Expose
    private String paymentTransaction;


    @SerializedName("prod_quality_rating")
    @Expose
    private String prodQualityRating;
    @SerializedName("delivery_time_rating")
    @Expose
    private String deliveryTimeRating;
    @SerializedName("customer_serv_rating")
    @Expose
    private String customerServRating;
    @SerializedName("rating_sdt")
    @Expose
    private Object ratingSdt;
    @SerializedName("promocode")
    @Expose
    private String promocode;
    @SerializedName("is_favorite")
    @Expose
    private Object isFavorite;
    @SerializedName("edit_order_price")
    @Expose
    private String editOrderPrice;
    @SerializedName("recurring")
    @Expose
    private String recurring;
    @SerializedName("area_id")
    @Expose
    private String areaId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("order_status_en")
    @Expose
    private String orderStatusEn;
    @SerializedName("order_status_ar")
    @Expose
    private String orderStatusAr;
    @SerializedName("payment_type_en")
    @Expose
    private String paymentTypeEn;
    @SerializedName("payment_type_ar")
    @Expose
    private String paymentTypeAr;
    @SerializedName("recurring_en")
    @Expose
    private String recurringEn;
    @SerializedName("recurring_ar")
    @Expose
    private String recurringAr;
    @SerializedName("delivery_time_en")
    @Expose
    private String deliveryTimeEn;
    @SerializedName("delivery_time_ar")
    @Expose
    private String deliveryTimeAr;
    @SerializedName("customer_comments")
    @Expose
    private String customer_comments;   @SerializedName("invoice")
    @Expose
    private String invoice =""; @SerializedName("tracking_link")
    @Expose
    private String tracking_link="";

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getTracking_link() {
        return tracking_link;
    }

    public void setTracking_link(String tracking_link) {
        this.tracking_link = tracking_link;
    }

    public String getCoupon_qty() {
        return coupon_qty;
    }

    public void setCoupon_qty(String coupon_qty) {
        this.coupon_qty = coupon_qty;
    }

    @SerializedName("coupon_qty")
    @Expose
    private String coupon_qty ;


    public String getCustomer_comments() {
        return customer_comments;
    }

    public void setCustomer_comments(String customer_comments) {
        this.customer_comments = customer_comments;
    }

    @SerializedName("order_detail")
    @Expose
    private List<OrderDetail> orderDetail = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getWalletTransactionId() {
        return walletTransactionId;
    }

    public void setWalletTransactionId(String walletTransactionId) {
        this.walletTransactionId = walletTransactionId;
    }

    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
    }

    public String getOrdUserId() {
        return ordUserId;
    }

    public void setOrdUserId(String ordUserId) {
        this.ordUserId = ordUserId;
    }

    public String getOrdTotalPrice() {
        return ordTotalPrice;
    }

    public void setOrdTotalPrice(String ordTotalPrice) {
        this.ordTotalPrice = ordTotalPrice;
    }

    public String getOrdNote() {
        return ordNote;
    }

    public void setOrdNote(String ordNote) {
        this.ordNote = ordNote;
    }

    public String getOrdDeliveryCharge() {
        return ordDeliveryCharge;
    }

    public void setOrdDeliveryCharge(String ordDeliveryCharge) {
        this.ordDeliveryCharge = ordDeliveryCharge;
    }

    public String getOrdTaxValue() {
        return ordTaxValue;
    }

    public void setOrdTaxValue(String ordTaxValue) {
        this.ordTaxValue = ordTaxValue;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDelvType() {
        return delvType;
    }

    public void setDelvType(String delvType) {
        this.delvType = delvType;
    }

    public String getOrdDate() {
        return ordDate;
    }

    public void setOrdDate(String ordDate) {
        this.ordDate = ordDate;
    }

    public String getIscancelable() {
        return iscancelable;
    }

    public void setIscancelable(String iscancelable) {
        this.iscancelable = iscancelable;
    }

    public String getIseditable() {
        return iseditable;
    }

    public void setIseditable(String iseditable) {
        this.iseditable = iseditable;
    }

    public String getIsreorderable() {
        return isreorderable;
    }

    public void setIsreorderable(String isreorderable) {
        this.isreorderable = isreorderable;
    }

    public Object getOrderPin() {
        return orderPin;
    }

    public void setOrderPin(Object orderPin) {
        this.orderPin = orderPin;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getPreferedDate() {
        return preferedDate;
    }

    public void setPreferedDate(String preferedDate) {
        this.preferedDate = preferedDate;
    }

    public String getPreferedTime() {
        return preferedTime;
    }

    public void setPreferedTime(String preferedTime) {
        this.preferedTime = preferedTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getPriceWithoutVat() {
        return priceWithoutVat;
    }

    public void setPriceWithoutVat(String priceWithoutVat) {
        this.priceWithoutVat = priceWithoutVat;
    }

    public String getAmountVat() {
        return amountVat;
    }

    public void setAmountVat(String amountVat) {
        this.amountVat = amountVat;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(String payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

    public String getPaymentTransaction() {
        return paymentTransaction;
    }

    public void setPaymentTransaction(String paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
    }

    public String getProdQualityRating() {
        return prodQualityRating;
    }

    public void setProdQualityRating(String prodQualityRating) {
        this.prodQualityRating = prodQualityRating;
    }

    public String getDeliveryTimeRating() {
        return deliveryTimeRating;
    }

    public void setDeliveryTimeRating(String deliveryTimeRating) {
        this.deliveryTimeRating = deliveryTimeRating;
    }

    public String getCustomerServRating() {
        return customerServRating;
    }

    public void setCustomerServRating(String customerServRating) {
        this.customerServRating = customerServRating;
    }

    public Object getRatingSdt() {
        return ratingSdt;
    }

    public void setRatingSdt(Object ratingSdt) {
        this.ratingSdt = ratingSdt;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public Object getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Object isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getEditOrderPrice() {
        return editOrderPrice;
    }

    public void setEditOrderPrice(String editOrderPrice) {
        this.editOrderPrice = editOrderPrice;
    }

    public String getRecurring() {
        return recurring;
    }

    public void setRecurring(String recurring) {
        this.recurring = recurring;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderStatusEn() {
        return orderStatusEn;
    }

    public void setOrderStatusEn(String orderStatusEn) {
        this.orderStatusEn = orderStatusEn;
    }

    public String getOrderStatusAr() {
        return orderStatusAr;
    }

    public void setOrderStatusAr(String orderStatusAr) {
        this.orderStatusAr = orderStatusAr;
    }

    public String getPaymentTypeEn() {
        return paymentTypeEn;
    }

    public void setPaymentTypeEn(String paymentTypeEn) {
        this.paymentTypeEn = paymentTypeEn;
    }

    public String getPaymentTypeAr() {
        return paymentTypeAr;
    }

    public void setPaymentTypeAr(String paymentTypeAr) {
        this.paymentTypeAr = paymentTypeAr;
    }

    public String getRecurringEn() {
        return recurringEn;
    }

    public void setRecurringEn(String recurringEn) {
        this.recurringEn = recurringEn;
    }

    public String getRecurringAr() {
        return recurringAr;
    }

    public void setRecurringAr(String recurringAr) {
        this.recurringAr = recurringAr;
    }

    public String getDeliveryTimeEn() {
        return deliveryTimeEn;
    }

    public void setDeliveryTimeEn(String deliveryTimeEn) {
        this.deliveryTimeEn = deliveryTimeEn;
    }

    public String getDeliveryTimeAr() {
        return deliveryTimeAr;
    }

    public void setDeliveryTimeAr(String deliveryTimeAr) {
        this.deliveryTimeAr = deliveryTimeAr;
    }

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }


    public String getEnable_return_sales() {
        return enable_return_sales;
    }

    public void setEnable_return_sales(String enable_return_sales) {
        this.enable_return_sales = enable_return_sales;
    }


    public String getBank_receipt() {
        return bank_receipt;
    }

    public void setBank_receipt(String bank_receipt) {
        this.bank_receipt = bank_receipt;
    }
}
