package com.app.aljazierah.object.YourOrderDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("dish_id")
    @Expose
    private String dishId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("ord_count")
    @Expose
    private String ordCount;
    @SerializedName("free_goods")
    @Expose
    private String freeGoods;
    @SerializedName("dish_price")
    @Expose
    private String dishPrice;

    public String getPrice_vat() {
        return price_vat;
    }

    public void setPrice_vat(String price_vat) {
        this.price_vat = price_vat;
    }

    @SerializedName("price_vat")
    @Expose
    private String price_vat;
    @SerializedName("dish_discount")
    @Expose
    private String dishDiscount;
    @SerializedName("dish_title_ar")
    @Expose
    private String dishTitleAr;
    @SerializedName("dish_title_en")
    @Expose
    private String dishTitleEn;
    @SerializedName("dish_detail_ar")
    @Expose
    private String dishDetailAr;
    @SerializedName("dish_detail_en")
    @Expose
    private String dishDetailEn;
    @SerializedName("dish_cat")
    @Expose
    private String dishCat;
    @SerializedName("dish_image")
    @Expose
    private String dishImage;
    @SerializedName("dish_discount_type")
    @Expose
    private String dishDiscountType;

    @SerializedName("prod_curr_status")
    @Expose
    private String prod_curr_status;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("comments")
    @Expose
    private String comments;


    @SerializedName("weight")
    @Expose
    private String weight ;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRating_created_at() {
        return rating_created_at;
    }

    public void setRating_created_at(String rating_created_at) {
        this.rating_created_at = rating_created_at;
    }

    @SerializedName("rating_created_at")
    @Expose
    private String rating_created_at;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrdCount() {
        return ordCount;
    }

    public void setOrdCount(String ordCount) {
        this.ordCount = ordCount;
    }

    public String  getFreeGoods() {
        return freeGoods;
    }

    public void setFreeGoods(String freeGoods) {
        this.freeGoods = freeGoods;
    }

    public String getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishDiscount() {
        return dishDiscount;
    }

    public void setDishDiscount(String dishDiscount) {
        this.dishDiscount = dishDiscount;
    }

    public String getDishTitleAr() {
        return dishTitleAr;
    }

    public void setDishTitleAr(String dishTitleAr) {
        this.dishTitleAr = dishTitleAr;
    }

    public String getDishTitleEn() {
        return dishTitleEn;
    }

    public void setDishTitleEn(String dishTitleEn) {
        this.dishTitleEn = dishTitleEn;
    }

    public String getDishDetailAr() {
        return dishDetailAr;
    }

    public void setDishDetailAr(String dishDetailAr) {
        this.dishDetailAr = dishDetailAr;
    }

    public String getDishDetailEn() {
        return dishDetailEn;
    }

    public void setDishDetailEn(String dishDetailEn) {
        this.dishDetailEn = dishDetailEn;
    }

    public String getDishCat() {
        return dishCat;
    }

    public void setDishCat(String dishCat) {
        this.dishCat = dishCat;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public String getDishDiscountType() {
        return dishDiscountType;
    }

    public void setDishDiscountType(String dishDiscountType) {
        this.dishDiscountType = dishDiscountType;
    }

    public String getProd_curr_status() {
        return prod_curr_status;
    }

    public void setProd_curr_status(String prod_curr_status) {
        this.prod_curr_status = prod_curr_status;
    }


    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
