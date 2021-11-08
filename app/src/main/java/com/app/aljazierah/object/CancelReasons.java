package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelReasons {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("reason_ar")
    @Expose
    private String reasonAr;
    @SerializedName("reason_en")
    @Expose
    private String reasonEn;
    @SerializedName("sap_id")
    @Expose
    private String sapId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sdt")
    @Expose
    private String sdt;
    @SerializedName("udt")
    @Expose
    private Object udt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReasonAr() {
        return reasonAr;
    }

    public void setReasonAr(String reasonAr) {
        this.reasonAr = reasonAr;
    }

    public String getReasonEn() {
        return reasonEn;
    }

    public void setReasonEn(String reasonEn) {
        this.reasonEn = reasonEn;
    }

    public String getSapId() {
        return sapId;
    }

    public void setSapId(String sapId) {
        this.sapId = sapId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Object getUdt() {
        return udt;
    }

    public void setUdt(Object udt) {
        this.udt = udt;
    }
}
