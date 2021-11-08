package com.app.aljazierah.object.CartParamResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BanksModel {
    @SerializedName("bank_id")
    @Expose
    private String bank_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("account_title")
    @Expose
    private String account_title;
    @SerializedName("account_no")
    @Expose
    private String account_no;
    @SerializedName("iban")
    @Expose
    private String iban;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    private boolean isSelected=false;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount_title() {
        return account_title;
    }

    public void setAccount_title(String account_title) {
        this.account_title = account_title;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
