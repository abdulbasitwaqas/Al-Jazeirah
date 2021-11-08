package com.app.aljazierah.object.request;

public class ProductsRequest {

    private int area_id;
    private String customer_id;
    private String add_type;
    private int city_id;

    public ProductsRequest(int area_id,int city_id,String customer_id,String add_type) {
        this.area_id = area_id;
        this.city_id = city_id;
        this.customer_id = customer_id;
        this.add_type = add_type;
    }


}
