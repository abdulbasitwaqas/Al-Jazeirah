package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ComplaintObject {
    @SerializedName("ticket_categories")
    @Expose
    private ArrayList<TicketCategory> ticketCategories;
    @SerializedName("order_information")
    @Expose
    private ArrayList<OrderInformation> orderInformation;
    @SerializedName("callcenter_agents")
    @Expose
    private ArrayList<CallcenterAgent> callcenterAgents;
    @SerializedName("product_list")
    @Expose
    private ArrayList<ProductList> productList;

    public ArrayList<TicketCategory> getTicketCategories() {
        return ticketCategories;
    }

    public void setTicketCategories(ArrayList<TicketCategory> ticketCategories) {
        this.ticketCategories = ticketCategories;
    }

    public ArrayList<OrderInformation> getOrderInformation() {
        return orderInformation;
    }

    public void setOrderInformation(ArrayList<OrderInformation> orderInformation) {
        this.orderInformation = orderInformation;
    }

    public ArrayList<CallcenterAgent> getCallcenterAgents() {
        return callcenterAgents;
    }

    public void setCallcenterAgents(ArrayList<CallcenterAgent> callcenterAgents) {
        this.callcenterAgents = callcenterAgents;
    }

    public ArrayList<ProductList> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductList> productList) {
        this.productList = productList;
    }
}
