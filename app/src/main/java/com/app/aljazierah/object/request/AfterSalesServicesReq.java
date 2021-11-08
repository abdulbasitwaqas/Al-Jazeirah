package com.app.aljazierah.object.request;

import java.util.ArrayList;

import okhttp3.RequestBody;

public class AfterSalesServicesReq {
    private String user_id;
    private String name;
    private String product_type;
    private String warranty;
    private String invoice_number;
    private String invoice_date;
    private String customer_mobile;
    private String email;
    private String title;
    private String product_issue;
    private String city;
    private String region;
    private String address;
    private String problem_description;
    private String warranty_agreement;
    private String product_category;
    private String service_slots;
    private RequestBody invoice_pic;

    public AfterSalesServicesReq(String user_id,String name, String product_type, String warranty, String invoice_number, String invoice_date, String customer_mobile, String email, String title, String product_issue, String city, String region, String address, String problem_description, String warranty_agreement, String product_category,String service_slots,  RequestBody invoice_pic) {
        this.user_id = user_id;
        this.name = name;
        this.product_type = product_type;
        this.warranty = warranty;
        this.invoice_number = invoice_number;
        this.invoice_date = invoice_date;
        this.customer_mobile = customer_mobile;
        this.email = email;
        this.title = title;
        this.product_issue = product_issue;
        this.city = city;
        this.region = region;
        this.address = address;
        this.problem_description = problem_description;
        this.warranty_agreement = warranty_agreement;
        this.product_category = product_category;
        this.service_slots = service_slots;
        this.invoice_pic = invoice_pic;
    }
}
