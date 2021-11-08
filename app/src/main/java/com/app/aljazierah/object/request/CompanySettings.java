package com.app.aljazierah.object.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CompanySettings implements Serializable
{

    @SerializedName("location_date")
    @Expose
    private String location_date;

    private String user_id;
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getLocation_date() {
        return location_date;
    }

    public void setLocation_date(String location_date) {
        this.location_date = location_date;
    }


    public CompanySettings(String location_date, String user_id) {
        this.location_date = location_date;
        this.user_id = user_id;
    }

    public CompanySettings() {

    }
}
