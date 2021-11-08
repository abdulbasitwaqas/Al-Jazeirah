package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mustanser Iqbal on 12/3/2017.
 */

public class Product implements Serializable {



    @SerializedName("dish_id")
    @Expose
    private String dishId;
    @SerializedName("dish_kitchen_id")
    @Expose
    private String dishKitchenId;
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
    @SerializedName("dish_cuisine")
    @Expose
    private String dishCuisine;
    @SerializedName("dish_type")
    @Expose
    private String dishType;
    @SerializedName("dish_sub_type")
    @Expose
    private String dishSubType;
    @SerializedName("dish_image")
    @Expose
    private String dishImage;
    @SerializedName("dish_image_more")
    @Expose
    private String dishImageMore;
    @SerializedName("dish_price")
    @Expose
    private String dishPrice;
    @SerializedName("dish_discount_type")
    @Expose
    private String dishDiscountType;
    @SerializedName("dish_discount")
    @Expose
    private String dishDiscount;
    @SerializedName("dish_date")
    @Expose
    private String dishDate;
    @SerializedName("dish_like_count")
    @Expose
    private String dishLikeCount;
    @SerializedName("dish_active")
    @Expose
    private String dishActive;
    @SerializedName("dish_order")
    @Expose
    private String dishOrder;
    @SerializedName("ingredient_ar")
    @Expose
    private String ingredientAr;
    @SerializedName("ingredient_en")
    @Expose
    private String ingredientEn;
    @SerializedName("healthy_ar")
    @Expose
    private String healthyAr;
    @SerializedName("healthy_en")
    @Expose
    private String healthyEn;
    @SerializedName("dish_view")
    @Expose
    private String dishView;
    @SerializedName("dish_likes")
    @Expose
    private String dishLikes;
    @SerializedName("dish_dislike")
    @Expose
    private String dishDislike;
    @SerializedName("dish_especial")
    @Expose
    private String dishEspecial;
    @SerializedName("dish_rate")
    @Expose
    private String dishRate;
    @SerializedName("dish_rate_count")
    @Expose
    private String dishRateCount;
    @SerializedName("dish_person_no")
    @Expose
    private String dishPersonNo;
    @SerializedName("dish_fulfill_requests")
    @Expose
    private String dishFulfillRequests;
    @SerializedName("dish_no_delete")
    @Expose
    private String dishNoDelete;
    @SerializedName("dish_addon_flag")
    @Expose
    private String dishAddonFlag;
    @SerializedName("dish_offers_today")
    @Expose
    private String dishOffersToday;
    @SerializedName("dish_count")
    @Expose
    private String dishCount;
    @SerializedName("material")
    @Expose
    private String material;
    @SerializedName("unit")
    @Expose
    private String unit;
    private boolean isAddedToCart;
    private int count = 0;


    private final static long serialVersionUID = -4214695899142089859L;

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getDishKitchenId() {
        return dishKitchenId;
    }

    public void setDishKitchenId(String dishKitchenId) {
        this.dishKitchenId = dishKitchenId;
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

    public String getDishCuisine() {
        return dishCuisine;
    }

    public void setDishCuisine(String dishCuisine) {
        this.dishCuisine = dishCuisine;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public String getDishSubType() {
        return dishSubType;
    }

    public void setDishSubType(String dishSubType) {
        this.dishSubType = dishSubType;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public String getDishImageMore() {
        return dishImageMore;
    }

    public void setDishImageMore(String dishImageMore) {
        this.dishImageMore = dishImageMore;
    }

    public String getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishDiscountType() {
        return dishDiscountType;
    }

    public void setDishDiscountType(String dishDiscountType) {
        this.dishDiscountType = dishDiscountType;
    }

    public String getDishDiscount() {
        return dishDiscount;
    }

    public void setDishDiscount(String dishDiscount) {
        this.dishDiscount = dishDiscount;
    }

    public String getDishDate() {
        return dishDate;
    }

    public void setDishDate(String dishDate) {
        this.dishDate = dishDate;
    }

    public String getDishLikeCount() {
        return dishLikeCount;
    }

    public void setDishLikeCount(String dishLikeCount) {
        this.dishLikeCount = dishLikeCount;
    }

    public String getDishActive() {
        return dishActive;
    }

    public void setDishActive(String dishActive) {
        this.dishActive = dishActive;
    }

    public String getDishOrder() {
        return dishOrder;
    }

    public void setDishOrder(String dishOrder) {
        this.dishOrder = dishOrder;
    }

    public String getIngredientAr() {
        return ingredientAr;
    }

    public void setIngredientAr(String ingredientAr) {
        this.ingredientAr = ingredientAr;
    }

    public String getIngredientEn() {
        return ingredientEn;
    }

    public void setIngredientEn(String ingredientEn) {
        this.ingredientEn = ingredientEn;
    }

    public String getHealthyAr() {
        return healthyAr;
    }

    public void setHealthyAr(String healthyAr) {
        this.healthyAr = healthyAr;
    }

    public String getHealthyEn() {
        return healthyEn;
    }

    public void setHealthyEn(String healthyEn) {
        this.healthyEn = healthyEn;
    }

    public String getDishView() {
        return dishView;
    }

    public void setDishView(String dishView) {
        this.dishView = dishView;
    }

    public String getDishLikes() {
        return dishLikes;
    }

    public void setDishLikes(String dishLikes) {
        this.dishLikes = dishLikes;
    }

    public String getDishDislike() {
        return dishDislike;
    }

    public void setDishDislike(String dishDislike) {
        this.dishDislike = dishDislike;
    }

    public String getDishEspecial() {
        return dishEspecial;
    }

    public void setDishEspecial(String dishEspecial) {
        this.dishEspecial = dishEspecial;
    }

    public String getDishRate() {
        return dishRate;
    }

    public void setDishRate(String dishRate) {
        this.dishRate = dishRate;
    }

    public String getDishRateCount() {
        return dishRateCount;
    }

    public void setDishRateCount(String dishRateCount) {
        this.dishRateCount = dishRateCount;
    }

    public String getDishPersonNo() {
        return dishPersonNo;
    }

    public void setDishPersonNo(String dishPersonNo) {
        this.dishPersonNo = dishPersonNo;
    }

    public String getDishFulfillRequests() {
        return dishFulfillRequests;
    }

    public void setDishFulfillRequests(String dishFulfillRequests) {
        this.dishFulfillRequests = dishFulfillRequests;
    }

    public String getDishNoDelete() {
        return dishNoDelete;
    }

    public void setDishNoDelete(String dishNoDelete) {
        this.dishNoDelete = dishNoDelete;
    }

    public String getDishAddonFlag() {
        return dishAddonFlag;
    }

    public void setDishAddonFlag(String dishAddonFlag) {
        this.dishAddonFlag = dishAddonFlag;
    }

    public String getDishOffersToday() {
        return dishOffersToday;
    }

    public void setDishOffersToday(String dishOffersToday) {
        this.dishOffersToday = dishOffersToday;
    }

    public String getDishCount() {
        return dishCount;
    }

    public void setDishCount(String dishCount) {
        this.dishCount = dishCount;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isAddedToCart() {
        return isAddedToCart;
    }

    public void setAddedToCart(boolean addedToCart) {
        isAddedToCart = addedToCart;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}