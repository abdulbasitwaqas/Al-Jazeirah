package com.app.aljazierah.object.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mustanser Iqbal on 12/2/2017.
 */

public class Register implements Serializable
{

    @SerializedName("frist_name")
    @Expose
    private String fristName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("image")
    @Expose
    private String image = "";
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("pass")
    @Expose
    private String pass;
    @SerializedName("client_type")
    @Expose
    private String clientType;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("client_sub_type")
    @Expose
    private String clientSubType;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("register_date")
    @Expose
    private String registerDate;
    private final static long serialVersionUID = 6750259744900814340L;

    public String getFristName() {
        return fristName;
    }

    public void setFristName(String fristName) {
        this.fristName = fristName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getClientSubType() {
        return clientSubType;
    }

    public void setClientSubType(String clientSubType) {
        this.clientSubType = clientSubType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
