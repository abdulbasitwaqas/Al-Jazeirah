package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PagesModel {

    @SerializedName("en")
    @Expose
    private String en;


    @SerializedName("ar")
    @Expose
    private String ar;



    @SerializedName("page_id")
    @Expose
    private String page_id;


    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getPage_id() {
        return page_id;
    }

    public void setPage_id(String page_id) {
        this.page_id = page_id;
    }
}
