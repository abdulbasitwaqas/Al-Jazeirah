package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallcenterAgent {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fullname")
    @Expose
    private String fullname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
