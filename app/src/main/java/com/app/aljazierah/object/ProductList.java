package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductList {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name_ar")
    @Expose
    private String nameAr;
    @SerializedName("name_en")
    @Expose
    private String nameEn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
}
