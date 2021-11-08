package com.app.aljazierah.object;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store {
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("store_code")
    @Expose
    private String storeCode;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("store_meta")
    @Expose
    private String storeMeta;
    @SerializedName("store_address_id")
    @Expose
    private Integer storeAddressId;
    @SerializedName("store_number")
    @Expose
    private String storeNumber;
    @SerializedName("morning_opentime")
    @Expose
    private String morningOpentime;
    @SerializedName("morning_closetime")
    @Expose
    private String morningClosetime;
    @SerializedName("evening_opentime")
    @Expose
    private String eveningOpentime;
    @SerializedName("evening_closetime")
    @Expose
    private String eveningClosetime;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("name_ar")
    @Expose
    private String nameAr;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreMeta() {
        return storeMeta;
    }

    public void setStoreMeta(String storeMeta) {
        this.storeMeta = storeMeta;
    }

    public Integer getStoreAddressId() {
        return storeAddressId;
    }

    public void setStoreAddressId(Integer storeAddressId) {
        this.storeAddressId = storeAddressId;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getMorningOpentime() {
        return morningOpentime;
    }

    public void setMorningOpentime(String morningOpentime) {
        this.morningOpentime = morningOpentime;
    }

    public String getMorningClosetime() {
        return morningClosetime;
    }

    public void setMorningClosetime(String morningClosetime) {
        this.morningClosetime = morningClosetime;
    }

    public String getEveningOpentime() {
        return eveningOpentime;
    }

    public void setEveningOpentime(String eveningOpentime) {
        this.eveningOpentime = eveningOpentime;
    }

    public String getEveningClosetime() {
        return eveningClosetime;
    }

    public void setEveningClosetime(String eveningClosetime) {
        this.eveningClosetime = eveningClosetime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
