package com.app.aljazierah.object.Promotions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Promotion {

    @SerializedName("product_id")
    @Expose
    private List<String> product_id_list =null;

    @SerializedName("category_id")
    @Expose
    private List<String> category_id_list =null;



    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("add_on")
    @Expose
    private String addOn;

    @SerializedName("image_mobile_en")
    @Expose
    private String imageMobileEn;
    @SerializedName("image_mobile_ar")
    @Expose
    private String imageMobileAr;

    @SerializedName("start_date")
    @Expose
    private String start_date;


    @SerializedName("end_date")
    @Expose
    private String end_date;

    public String getInfo_en() {
        return info_en;
    }

    public void setInfo_en(String info_en) {
        this.info_en = info_en;
    }

    public String getInfo_ar() {
        return info_ar;
    }

    public void setInfo_ar(String info_ar) {
        this.info_ar = info_ar;
    }

    @SerializedName("info_en")
    @Expose
    private String info_en;

    @SerializedName("info_ar")
    @Expose
    private String info_ar;
    @SerializedName("promotion_type")
    @Expose
    private String promotion_type;
    @SerializedName("discount_type")
    @Expose
    private String discount_type;
    @SerializedName("min_quantity")
    @Expose
    private String min_quantity;
 /*   @SerializedName("on_products")
    @Expose
    private List<String> on_productsLists;*/

    @SerializedName("foc_prod")
    @Expose
    private List<Foc_prod> foc_prod;


    public List<Range_Limit> getRange_limit() {
        return range_limit;
    }

    public void setRange_limit(List<Range_Limit> range_limit) {
        this.range_limit = range_limit;
    }

    @SerializedName("range_limit")
    @Expose
    private List<Range_Limit> range_limit;

    @SerializedName("promotion_level")
    @Expose
    private String promotion_level;

    @SerializedName("is_offer")
    @Expose
    private String is_offer= "0";

    public String getIs_offer() {
        return is_offer;
    }

    public void setIs_offer(String is_offer) {
        this.is_offer = is_offer;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public List<String> getProduct_id_list() {
        return product_id_list;
    }

    public void setProduct_id_list(List<String> product_id_list) {
        this.product_id_list = product_id_list;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAddOn() {
        return addOn;
    }

    public void setAddOn(String addOn) {
        this.addOn = addOn;
    }



    public String getImageMobileEn() {
        return imageMobileEn;
    }

    public void setImageMobileEn(String imageMobileEn) {
        this.imageMobileEn = imageMobileEn;
    }

    public String getImageMobileAr() {
        return imageMobileAr;
    }

    public void setImageMobileAr(String imageMobileAr) {
        this.imageMobileAr = imageMobileAr;
    }

    public List<Foc_prod> getFoc_prod() {
        return foc_prod;
    }

    public void setFoc_prod(List<Foc_prod> foc_prod) {
        this.foc_prod = foc_prod;
    }

    public String getPromotion_level() {
        return promotion_level;
    }

    public void setPromotion_level(String promotion_level) {
        this.promotion_level = promotion_level;
    }


    public List<String> getCategory_id_list() {
        return category_id_list;
    }

    public void setCategory_id_list(List<String> category_id_list) {
        this.category_id_list = category_id_list;
    }


    public String getPromotion_type() {
        return promotion_type;
    }

    public void setPromotion_type(String promotion_type) {
        this.promotion_type = promotion_type;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public String getMin_quantity() {
        return min_quantity;
    }

    public void setMin_quantity(String min_quantity) {
        this.min_quantity = min_quantity;
    }

/*    public List<String> getOn_productsLists() {
        return on_productsLists;
    }

    public void setOn_productsLists(List<String> on_productsLists) {
        this.on_productsLists = on_productsLists;
    }*/

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }
}
