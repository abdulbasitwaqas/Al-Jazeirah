package com.app.aljazierah.object.request;

public class AfterSalesServicesMyReturnReq {
    private String name;
    private String warranty;
    private String invoice_number;
    private String invoice_date;
    private String customer_mobile;
    private String email;
    private String city;
    private String region;
    private String problem_description;
    private String user_id;
    private String orderid;
    private String order_number;



    public AfterSalesServicesMyReturnReq(String name, String warranty, String invoice_number,
                                         String invoice_date, String customer_mobile, String email,
                                         String city, String region,  String problem_description,String user_id,String orderid,String order_number) {
        this.name = name;
        this.warranty = warranty;
        this.invoice_number = invoice_number;
        this.invoice_date = invoice_date;
        this.customer_mobile = customer_mobile;
        this.email = email;
        this.city = city;
        this.region = region;
        this.problem_description = problem_description;
        this.user_id = user_id;
        this.orderid = orderid;
        this.order_number = order_number;

    }
}


