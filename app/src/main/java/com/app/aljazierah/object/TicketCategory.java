package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketCategory {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title_english")
    @Expose
    private String titleEnglish;
    @SerializedName("title_arabic")
    @Expose
    private String titleArabic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getTitleArabic() {
        return titleArabic;
    }

    public void setTitleArabic(String titleArabic) {
        this.titleArabic = titleArabic;
    }

}
