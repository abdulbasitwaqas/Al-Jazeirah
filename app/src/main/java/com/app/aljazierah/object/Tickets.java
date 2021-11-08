package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tickets {

    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("ticket_body")
    @Expose
    private String ticketBody;
    @SerializedName("ticket_status")
    @Expose
    private String ticketStatus;
    @SerializedName("ticket_date_generated")
    @Expose
    private String ticketDateGenerated;
    @SerializedName("category_title_english")
    @Expose
    private String categoryTitleEnglish;
    @SerializedName("category_title_arabic")
    @Expose
    private String categoryTitleArabic;
    @SerializedName("replies")
    @Expose
    private String replies;
    @SerializedName("pending_first_name")
    @Expose
    private String pendingFirstName;
    @SerializedName("pending_last_name")
    @Expose
    private String pendingLastName;

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

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getTicketDateGenerated() {
        return ticketDateGenerated;
    }

    public void setTicketDateGenerated(String ticketDateGenerated) {
        this.ticketDateGenerated = ticketDateGenerated;
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

}
