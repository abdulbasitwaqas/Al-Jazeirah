package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 *
 */

public class Orders implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ord_id")
    @Expose
    private String ordId;
    @SerializedName("ord_total_price")
    @Expose
    private String ordTotalPrice;
    @SerializedName("current_status")
    @Expose
    private String currentStatus;
    @SerializedName("ord_date")
    @Expose
    private String ordDate;
    @SerializedName("iscancelable")
    @Expose
    private String iscancelable;
    @SerializedName("iseditable")
    @Expose
    private String iseditable;
    @SerializedName("isreorderable")
    @Expose
    private String isreorderable;
    @SerializedName("qitaf_rewardpoints")
    @Expose
    private String qitafRewardpoints;
    @SerializedName("is_favorite")
    @Expose
    private String isFavorite;
    @SerializedName("order_status_en")
    @Expose
    private String orderStatusEn;
    @SerializedName("order_status_ar")
    @Expose
    private String orderStatusAr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
    }

    public String getOrdTotalPrice() {
        return ordTotalPrice;
    }

    public void setOrdTotalPrice(String ordTotalPrice) {
        this.ordTotalPrice = ordTotalPrice;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getOrdDate() {
        return ordDate;
    }

    public void setOrdDate(String ordDate) {
        this.ordDate = ordDate;
    }

    public String getIscancelable() {
        return iscancelable;
    }

    public void setIscancelable(String iscancelable) {
        this.iscancelable = iscancelable;
    }

    public String getIseditable() {
        return iseditable;
    }

    public void setIseditable(String iseditable) {
        this.iseditable = iseditable;
    }

    public String getIsreorderable() {
        return isreorderable;
    }

    public void setIsreorderable(String isreorderable) {
        this.isreorderable = isreorderable;
    }

    public String getQitafRewardpoints() {
        return qitafRewardpoints;
    }

    public void setQitafRewardpoints(String qitafRewardpoints) {
        this.qitafRewardpoints = qitafRewardpoints;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getOrderStatusEn() {
        return orderStatusEn;
    }

    public void setOrderStatusEn(String orderStatusEn) {
        this.orderStatusEn = orderStatusEn;
    }

    public String getOrderStatusAr() {
        return orderStatusAr;
    }

    public void setOrderStatusAr(String orderStatusAr) {
        this.orderStatusAr = orderStatusAr;
    }


}
