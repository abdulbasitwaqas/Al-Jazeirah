package com.app.aljazierah.object.Promotions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Foc_prod {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name_en")
    @Expose
    private String name_en;

    @SerializedName("name_ar")
    @Expose
    private String name_ar;

    private int count = 0;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName_ar() {
        return name_ar;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }
}
