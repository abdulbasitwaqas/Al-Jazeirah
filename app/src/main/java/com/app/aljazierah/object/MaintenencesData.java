package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class MaintenencesData {

    @SerializedName("ticket_id")
    @Expose
    private Integer ticketId;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("area_id")
    @Expose
    private Integer areaId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("assigned_to")
    @Expose
    private String assignedTo;
    @SerializedName("prev_assigned")
    @Expose
    private Object prevAssigned;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("service_slot")
    @Expose
    private String serviceSlot;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("close_ticket_date")
    @Expose
    private Object closeTicketDate;
    @SerializedName("last_reply_time")
    @Expose
    private Object lastReplyTime;
    @SerializedName("closing_user")
    @Expose
    private Object closingUser;
    @SerializedName("rating")
    @Expose
    private Object rating;
    @SerializedName("order_code")
    @Expose
    private Object orderCode;
    @SerializedName("order_id")
    @Expose
    private Object orderId;
    @SerializedName("driver_id")
    @Expose
    private Object driverId;
    @SerializedName("created_by_type")
    @Expose
    private Integer createdByType;
    @SerializedName("file_attachment")
    @Expose
    private Object fileAttachment;
    @SerializedName("source")
    @Expose
    private Integer source;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("purchase_source")
    @Expose
    private String purchaseSource;
    @SerializedName("market_name")
    @Expose
    private Object marketName;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("responsibility_id")
    @Expose
    private Object responsibilityId;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("issue")
    @Expose
    private String issue;
    @SerializedName("warranty")
    @Expose
    private String warranty;
    @SerializedName("invoice_number")
    @Expose
    private String invoiceNumber;
    @SerializedName("invoice_pic")
    @Expose
    private String invoice_pic;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("visible_to_customer")
    @Expose
    private Integer visibleToCustomer;
    /*@SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("area")
    @Expose
    private Area area;*/

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Object getPrevAssigned() {
        return prevAssigned;
    }

    public void setPrevAssigned(Object prevAssigned) {
        this.prevAssigned = prevAssigned;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Object getCloseTicketDate() {
        return closeTicketDate;
    }

    public void setCloseTicketDate(Object closeTicketDate) {
        this.closeTicketDate = closeTicketDate;
    }

    public Object getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(Object lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public Object getClosingUser() {
        return closingUser;
    }

    public void setClosingUser(Object closingUser) {
        this.closingUser = closingUser;
    }

    public Object getRating() {
        return rating;
    }

    public void setRating(Object rating) {
        this.rating = rating;
    }

    public Object getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Object orderCode) {
        this.orderCode = orderCode;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }

    public Object getDriverId() {
        return driverId;
    }

    public void setDriverId(Object driverId) {
        this.driverId = driverId;
    }

    public Integer getCreatedByType() {
        return createdByType;
    }

    public void setCreatedByType(Integer createdByType) {
        this.createdByType = createdByType;
    }

    public Object getFileAttachment() {
        return fileAttachment;
    }

    public void setFileAttachment(Object fileAttachment) {
        this.fileAttachment = fileAttachment;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
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

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseSource() {
        return purchaseSource;
    }

    public void setPurchaseSource(String purchaseSource) {
        this.purchaseSource = purchaseSource;
    }

    public Object getMarketName() {
        return marketName;
    }

    public void setMarketName(Object marketName) {
        this.marketName = marketName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Object getResponsibilityId() {
        return responsibilityId;
    }

    public void setResponsibilityId(Object responsibilityId) {
        this.responsibilityId = responsibilityId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getVisibleToCustomer() {
        return visibleToCustomer;
    }

    public void setVisibleToCustomer(Integer visibleToCustomer) {
        this.visibleToCustomer = visibleToCustomer;
    }

    public String getServiceSlot() {
        return serviceSlot;
    }

    public void setServiceSlot(String serviceSlot) {
        this.serviceSlot = serviceSlot;
    }

    public String getInvoice_pic() {
        return invoice_pic;
    }

    public void setInvoice_pic(String invoice_pic) {
        this.invoice_pic = invoice_pic;
    }


    /*@SerializedName("aftersale_id")
    @Expose
    private Integer aftersaleId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("Full_Name")
    @Expose
    private String fullName;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("Issue")
    @Expose
    private String issue;
    @SerializedName("Warranty")
    @Expose
    private String warranty;
    @SerializedName("Invoice_number")
    @Expose
    private Object invoiceNumber;
    @SerializedName("Invoice_date")
    @Expose
    private Object invoiceDate;
    @SerializedName("Mobile_Number")
    @Expose
    private String mobileNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Region")
    @Expose
    private String region;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Problem_Description")
    @Expose
    private String problemDescription;
    @SerializedName("select_city")
    @Expose
    private String selectCity;
    @SerializedName("opt_phone_number")
    @Expose
    private Object optPhoneNumber;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getAftersaleId() {
        return aftersaleId;
    }

    public void setAftersaleId(Integer aftersaleId) {
        this.aftersaleId = aftersaleId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public Object getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Object invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Object getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Object invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getSelectCity() {
        return selectCity;
    }

    public void setSelectCity(String selectCity) {
        this.selectCity = selectCity;
    }

    public Object getOptPhoneNumber() {
        return optPhoneNumber;
    }

    public void setOptPhoneNumber(Object optPhoneNumber) {
        this.optPhoneNumber = optPhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    }*/
}
