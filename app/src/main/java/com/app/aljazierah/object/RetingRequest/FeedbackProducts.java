package com.app.aljazierah.object.RetingRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackProducts{

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("name_ar")
    @Expose
    private String nameAr;

    @SerializedName("rating")
    @Expose
    private String rating="";
    @SerializedName("comments")
    @Expose
    private String comments="";

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


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

}