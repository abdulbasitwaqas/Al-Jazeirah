package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class AfterSalesReturnData {
    @SerializedName("return_id")
    @Expose
    private Integer returnId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("customer_mobile")
    @Expose
    private String customerMobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("invoice_date")
    @Expose
    private String invoiceDate;
    @SerializedName("invoice_number")
    @Expose
    private String invoiceNumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("problem_description")
    @Expose
    private String problemDescription;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("warranty")
    @Expose
    private String warranty;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getReturnId() {
        return returnId;
    }

    public void setReturnId(Integer returnId) {
        this.returnId = returnId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

}