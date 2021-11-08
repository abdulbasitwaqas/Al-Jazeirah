package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class addressData implements Serializable {
    @SerializedName("ord_address_id")
    @Expose
    String ord_address_id;

    @SerializedName("add_area")
    @Expose
    String add_area;

    @SerializedName("add_name")
    @Expose
    String add_name;

    @SerializedName("add_detail")
    @Expose
    String add_detail;

    @SerializedName("add_latitude")
    @Expose
    String add_latitude;

    @SerializedName("add_longitude")
    @Expose
    String add_longitude;

    @SerializedName("add_street_name")
    @Expose
    String add_street_name;
    @SerializedName("add_type")
    @Expose
    String add_type;

    public String getAdd_type() {
        return add_type;
    }

    public void setAdd_type(String add_type) {
        this.add_type = add_type;
    }

    public String getOrd_address_id() {
        return ord_address_id;
    }

    public void setOrd_address_id(String ord_address_id) {
        this.ord_address_id = ord_address_id;
    }

    public String getAdd_area() {
        return add_area;
    }

    public void setAdd_area(String add_area) {
        this.add_area = add_area;
    }

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
    }

    public String getAdd_detail() {
        return add_detail;
    }

    public void setAdd_detail(String add_detail) {
        this.add_detail = add_detail;
    }

    public String getAdd_latitude() {
        return add_latitude;
    }

    public void setAdd_latitude(String add_latitude) {
        this.add_latitude = add_latitude;
    }

    public String getAdd_longitude() {
        return add_longitude;
    }

    public void setAdd_longitude(String add_longitude) {
        this.add_longitude = add_longitude;
    }

    public String getAdd_street_name() {
        return add_street_name;
    }

    public void setAdd_street_name(String add_street_name) {
        this.add_street_name = add_street_name;
    }
}
