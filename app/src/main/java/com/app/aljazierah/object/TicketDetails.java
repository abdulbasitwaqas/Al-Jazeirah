package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TicketDetails {

    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("ticket_body")
    @Expose
    private String ticketBody;
    @SerializedName("ticket_assigned_to")
    @Expose
    private String ticketAssignedTo;
    @SerializedName("ticket_prev_assigned")
    @Expose
    private String ticketPrevAssigned;
    @SerializedName("ticket_priority_en")
    @Expose
    private String ticketPriorityEn;
    @SerializedName("ticket_priority_ar")
    @Expose
    private String ticketPriorityAr;
    @SerializedName("close_ticket_date")
    @Expose
    private String closeTicketDate;
    @SerializedName("ticket_last_reply_time")
    @Expose
    private String ticketLastReplyTime;
    @SerializedName("ticket_closing_user")
    @Expose
    private String ticketClosingUser;
    @SerializedName("ticket_rating")
    @Expose
    private String ticketRating;
    @SerializedName("ticket_order_code")
    @Expose
    private String ticketOrderCode;
    @SerializedName("ticket_driver_id")
    @Expose
    private String ticketDriverId;
    @SerializedName("ticket_cc_user_id")
    @Expose
    private String ticketCcUserId;
    @SerializedName("ticket_file_attachment")
    @Expose
    private String ticketFileAttachment;
    @SerializedName("ticket_source")
    @Expose
    private String ticketSource;
    @SerializedName("ticket_fine")
    @Expose
    private String ticketFine;
    @SerializedName("production_time")
    @Expose
    private String productionTime;
    @SerializedName("production_date")
    @Expose
    private String productionDate;
    @SerializedName("ticket_invoice_no")
    @Expose
    private String ticketInvoiceNo;
    @SerializedName("ticket_sku")
    @Expose
    private String ticketSku;
    @SerializedName("ticket_batch_no")
    @Expose
    private String ticketBatchNo;
    @SerializedName("ticket_quantity")
    @Expose
    private String ticketQuantity;
    @SerializedName("ticket_invoice1_num")
    @Expose
    private String ticketInvoice1Num;
    @SerializedName("ticket_solution")
    @Expose
    private String ticketSolution;
    @SerializedName("status")
    @Expose
    private String ticketStatus;
    @SerializedName("ticket_sdt")
    @Expose
    private String ticketSdt;
    @SerializedName("ticket_udt")
    @Expose
    private String ticketUdt;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_title_english")
    @Expose
    private String categoryTitleEnglish;
    @SerializedName("category_title_arabic")
    @Expose
    private String categoryTitleArabic;
    @SerializedName("category_default_rep")
    @Expose
    private String categoryDefaultRep;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("client_area_id")
    @Expose
    private String clientAreaId;
    @SerializedName("client_first_name_en")
    @Expose
    private String clientFirstNameEn;
    @SerializedName("client_last_name_en")
    @Expose
    private String clientLastNameEn;
    @SerializedName("client_first_name_ar")
    @Expose
    private String clientFirstNameAr;
    @SerializedName("client_last_name_ar")
    @Expose
    private String clientLastNameAr;
    @SerializedName("client_email")
    @Expose
    private String clientEmail;
    @SerializedName("client_mobile")
    @Expose
    private String clientMobile;
    @SerializedName("client_pref_lang")
    @Expose
    private String clientPrefLang;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_first_name")
    @Expose
    private String userFirstName;
    @SerializedName("user_last_name")
    @Expose
    private String userLastName;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_avatar")
    @Expose
    private String userAvatar;
    @SerializedName("user_designation")
    @Expose
    private String userDesignation;
    @SerializedName("area_id")
    @Expose
    private String areaId;
    @SerializedName("area_name_en")
    @Expose
    private String areaNameEn;
    @SerializedName("area_name_ar")
    @Expose
    private String areaNameAr;
    @SerializedName("area_areacode")
    @Expose
    private String areaAreacode;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("cities_name_en")
    @Expose
    private String citiesNameEn;
    @SerializedName("cities_name_ar")
    @Expose
    private String citiesNameAr;
    @SerializedName("replies")
    @Expose
    private String replies;
    @SerializedName("pending_first_name")
    @Expose
    private String pendingFirstName;
    @SerializedName("pending_last_name")
    @Expose
    private String pendingLastName;
    @SerializedName("pending_designation")
    @Expose
    private String pendingDesignation;
    @SerializedName("pending_reporting_to")
    @Expose
    private String pendingReportingTo;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("callcenter_reporting_to")
    @Expose
    private String callcenterReportingTo;
    @SerializedName("driver_employee_id")
    @Expose
    private String driverEmployeeId;
    @SerializedName("driver_vehicle_plate_no")
    @Expose
    private String driverVehiclePlateNo;
    @SerializedName("reply")
    @Expose
    private ArrayList<TicketReply> reply;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketBody() {
        return ticketBody;
    }

    public void setTicketBody(String ticketBody) {
        this.ticketBody = ticketBody;
    }

    public String getTicketAssignedTo() {
        return ticketAssignedTo;
    }

    public void setTicketAssignedTo(String ticketAssignedTo) {
        this.ticketAssignedTo = ticketAssignedTo;
    }

    public Object getTicketPrevAssigned() {
        return ticketPrevAssigned;
    }

    public void setTicketPrevAssigned(String ticketPrevAssigned) {
        this.ticketPrevAssigned = ticketPrevAssigned;
    }

    public String getTicketPriorityEn() {
        return ticketPriorityEn;
    }

    public void setTicketPriorityEn(String ticketPriorityEn) {
        this.ticketPriorityEn = ticketPriorityEn;
    }

    public String getTicketPriorityAr() {
        return ticketPriorityAr;
    }

    public void setTicketPriorityAr(String ticketPriorityAr) {
        this.ticketPriorityAr = ticketPriorityAr;
    }

    public String getCloseTicketDate() {
        return closeTicketDate;
    }

    public void setCloseTicketDate(String closeTicketDate) {
        this.closeTicketDate = closeTicketDate;
    }

    public String getTicketLastReplyTime() {
        return ticketLastReplyTime;
    }

    public void setTicketLastReplyTime(String ticketLastReplyTime) {
        this.ticketLastReplyTime = ticketLastReplyTime;
    }

    public String getTicketClosingUser() {
        return ticketClosingUser;
    }

    public void setTicketClosingUser(String ticketClosingUser) {
        this.ticketClosingUser = ticketClosingUser;
    }

    public String getTicketRating() {
        return ticketRating;
    }

    public void setTicketRating(String ticketRating) {
        this.ticketRating = ticketRating;
    }

    public String getTicketOrderCode() {
        return ticketOrderCode;
    }

    public void setTicketOrderCode(String ticketOrderCode) {
        this.ticketOrderCode = ticketOrderCode;
    }

    public String getTicketDriverId() {
        return ticketDriverId;
    }

    public void setTicketDriverId(String ticketDriverId) {
        this.ticketDriverId = ticketDriverId;
    }

    public String getTicketCcUserId() {
        return ticketCcUserId;
    }

    public void setTicketCcUserId(String ticketCcUserId) {
        this.ticketCcUserId = ticketCcUserId;
    }

    public String getTicketFileAttachment() {
        return ticketFileAttachment;
    }

    public void setTicketFileAttachment(String ticketFileAttachment) {
        this.ticketFileAttachment = ticketFileAttachment;
    }

    public String getTicketSource() {
        return ticketSource;
    }

    public void setTicketSource(String ticketSource) {
        this.ticketSource = ticketSource;
    }

    public String getTicketFine() {
        return ticketFine;
    }

    public void setTicketFine(String ticketFine) {
        this.ticketFine = ticketFine;
    }

    public String getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(String productionTime) {
        this.productionTime = productionTime;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getTicketInvoiceNo() {
        return ticketInvoiceNo;
    }

    public void setTicketInvoiceNo(String ticketInvoiceNo) {
        this.ticketInvoiceNo = ticketInvoiceNo;
    }

    public String getTicketSku() {
        return ticketSku;
    }

    public void setTicketSku(String ticketSku) {
        this.ticketSku = ticketSku;
    }

    public String getTicketBatchNo() {
        return ticketBatchNo;
    }

    public void setTicketBatchNo(String ticketBatchNo) {
        this.ticketBatchNo = ticketBatchNo;
    }

    public String getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(String ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public String getTicketInvoice1Num() {
        return ticketInvoice1Num;
    }

    public void setTicketInvoice1Num(String ticketInvoice1Num) {
        this.ticketInvoice1Num = ticketInvoice1Num;
    }

    public String getTicketSolution() {
        return ticketSolution;
    }

    public void setTicketSolution(String ticketSolution) {
        this.ticketSolution = ticketSolution;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getTicketSdt() {
        return ticketSdt;
    }

    public void setTicketSdt(String ticketSdt) {
        this.ticketSdt = ticketSdt;
    }

    public String getTicketUdt() {
        return ticketUdt;
    }

    public void setTicketUdt(String ticketUdt) {
        this.ticketUdt = ticketUdt;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitleEnglish() {
        return categoryTitleEnglish;
    }

    public void setCategoryTitleEnglish(String categoryTitleEnglish) {
        this.categoryTitleEnglish = categoryTitleEnglish;
    }

    public String getCategoryTitleArabic() {
        return categoryTitleArabic;
    }

    public void setCategoryTitleArabic(String categoryTitleArabic) {
        this.categoryTitleArabic = categoryTitleArabic;
    }

    public String getCategoryDefaultRep() {
        return categoryDefaultRep;
    }

    public void setCategoryDefaultRep(String categoryDefaultRep) {
        this.categoryDefaultRep = categoryDefaultRep;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientAreaId() {
        return clientAreaId;
    }

    public void setClientAreaId(String clientAreaId) {
        this.clientAreaId = clientAreaId;
    }

    public String getClientFirstNameEn() {
        return clientFirstNameEn;
    }

    public void setClientFirstNameEn(String clientFirstNameEn) {
        this.clientFirstNameEn = clientFirstNameEn;
    }

    public String getClientLastNameEn() {
        return clientLastNameEn;
    }

    public void setClientLastNameEn(String clientLastNameEn) {
        this.clientLastNameEn = clientLastNameEn;
    }

    public String getClientFirstNameAr() {
        return clientFirstNameAr;
    }

    public void setClientFirstNameAr(String clientFirstNameAr) {
        this.clientFirstNameAr = clientFirstNameAr;
    }

    public String getClientLastNameAr() {
        return clientLastNameAr;
    }

    public void setClientLastNameAr(String clientLastNameAr) {
        this.clientLastNameAr = clientLastNameAr;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientMobile() {
        return clientMobile;
    }

    public void setClientMobile(String clientMobile) {
        this.clientMobile = clientMobile;
    }

    public String getClientPrefLang() {
        return clientPrefLang;
    }

    public void setClientPrefLang(String clientPrefLang) {
        this.clientPrefLang = clientPrefLang;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserDesignation() {
        return userDesignation;
    }

    public void setUserDesignation(String userDesignation) {
        this.userDesignation = userDesignation;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Object getAreaNameEn() {
        return areaNameEn;
    }

    public void setAreaNameEn(String areaNameEn) {
        this.areaNameEn = areaNameEn;
    }

    public Object getAreaNameAr() {
        return areaNameAr;
    }

    public void setAreaNameAr(String areaNameAr) {
        this.areaNameAr = areaNameAr;
    }

    public Object getAreaAreacode() {
        return areaAreacode;
    }

    public void setAreaAreacode(String areaAreacode) {
        this.areaAreacode = areaAreacode;
    }

    public Object getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Object getCitiesNameEn() {
        return citiesNameEn;
    }

    public void setCitiesNameEn(String citiesNameEn) {
        this.citiesNameEn = citiesNameEn;
    }

    public Object getCitiesNameAr() {
        return citiesNameAr;
    }

    public void setCitiesNameAr(String citiesNameAr) {
        this.citiesNameAr = citiesNameAr;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }

    public String getPendingFirstName() {
        return pendingFirstName;
    }

    public void setPendingFirstName(String pendingFirstName) {
        this.pendingFirstName = pendingFirstName;
    }

    public String getPendingLastName() {
        return pendingLastName;
    }

    public void setPendingLastName(String pendingLastName) {
        this.pendingLastName = pendingLastName;
    }

    public String getPendingDesignation() {
        return pendingDesignation;
    }

    public void setPendingDesignation(String pendingDesignation) {
        this.pendingDesignation = pendingDesignation;
    }

    public String getPendingReportingTo() {
        return pendingReportingTo;
    }

    public void setPendingReportingTo(String pendingReportingTo) {
        this.pendingReportingTo = pendingReportingTo;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCallcenterReportingTo() {
        return callcenterReportingTo;
    }

    public void setCallcenterReportingTo(String callcenterReportingTo) {
        this.callcenterReportingTo = callcenterReportingTo;
    }

    public String getDriverEmployeeId() {
        return driverEmployeeId;
    }

    public void setDriverEmployeeId(String driverEmployeeId) {
        this.driverEmployeeId = driverEmployeeId;
    }

    public String getDriverVehiclePlateNo() {
        return driverVehiclePlateNo;
    }

    public void setDriverVehiclePlateNo(String driverVehiclePlateNo) {
        this.driverVehiclePlateNo = driverVehiclePlateNo;
    }

    public ArrayList getReply() {
        return reply;
    }

    public void setReply(ArrayList reply) {
        this.reply = reply;
    }

}
