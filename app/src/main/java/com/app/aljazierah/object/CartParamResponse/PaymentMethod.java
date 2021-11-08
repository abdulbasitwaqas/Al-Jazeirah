package com.app.aljazierah.object.CartParamResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  class PaymentMethod implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title_en")
    @Expose
    private String titleEn;
    @SerializedName("title_ar")
    @Expose
    private String titleAr;
    @SerializedName("status")
    @Expose
    private String status;

    private  boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
