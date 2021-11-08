package com.app.aljazierah.object.Promotions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Range_Limit {

    @SerializedName("min")
    @Expose
    private String min;

    @SerializedName("max")
    @Expose
    private String max;

    @SerializedName("add_on")
    @Expose
    private String add_on;

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getAdd_on() {
        return add_on;
    }

    public void setAdd_on(String add_on) {
        this.add_on = add_on;
    }
}
