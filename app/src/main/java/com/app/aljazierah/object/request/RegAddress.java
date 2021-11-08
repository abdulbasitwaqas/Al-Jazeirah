package com.app.aljazierah.object.request;

/**
 * Created by Mustanser Iqbal on 12/8/2017.
 */

public class RegAddress {

    private String user_id;
    private String add_latitude;
    private String add_longitude;
    private String add_area;
    private String add_city;
    private String add_name;
    private String add_detail;
    private String add_floor;
    private String add_street_name;
    private int add_country = 3;
    private int add_type = 1;

    public RegAddress(String user_id, String add_latitude, String add_longitude, String add_area, String add_city, String add_name, String add_detail,String add_type, String add_floor, String add_street_name) {
        this.user_id = user_id;
        this.add_latitude = add_latitude;
        this.add_longitude = add_longitude;
        this.add_area = add_area;
        this.add_city = add_city;
        this.add_name = add_name;
        this.add_detail = add_detail;
        this.add_floor = add_floor;
        this.add_street_name = add_street_name;
    }


}
