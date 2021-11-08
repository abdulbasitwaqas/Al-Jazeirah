package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSlot {
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

    public String getId() {
        return id;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public String getStatus() {
        return status;
    }


}
