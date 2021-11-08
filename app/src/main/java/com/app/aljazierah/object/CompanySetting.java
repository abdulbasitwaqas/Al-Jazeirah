package com.app.aljazierah.object;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.roam.appdatabase.DatabaseManager;
import com.roam.appdatabase.managed.ManagedObject;

import org.json.JSONObject;

/**
 */


@DatabaseTable
public class CompanySetting extends ManagedObject {
    @DatabaseField(defaultValue = "0")
    public int minOrder;

    @DatabaseField(defaultValue = "0")
    public int vat;


    @DatabaseField
    public String curAndroidVer;
    @DatabaseField
    public String is_ticket_enable;

    @DatabaseField(defaultValue = "")
    public String whats_app = "";
    @DatabaseField(defaultValue = "")
    public String helpLine="";
    @DatabaseField(defaultValue = "")
    public String email="";

    @DatabaseField(defaultValue = "1")
    public String sadadDelivery;
    @DatabaseField(defaultValue = "")
    public String stc_qitaf_msg_en;
    @DatabaseField(defaultValue = "")
    public String stc_qitaf_msg_ar;

    @DatabaseField(defaultValue = "")
    public String show_vat;

    @DatabaseField(defaultValue = "")
    public String enable_other_channels;

    @DatabaseField(defaultValue = "0")
    public String show_category;

    @DatabaseField(defaultValue = "")
    public String tc_en;

    @DatabaseField(defaultValue = "")
    public String tc_ar;

    @DatabaseField(defaultValue = "")
    public String facebook;


    @DatabaseField(defaultValue = "")
    public String twitter;


    @DatabaseField(defaultValue = "")
    public String instagram;

    @DatabaseField(defaultValue = "")
    public String snapchat;

        @DatabaseField(defaultValue = "")
    public String after_sale_cities;



    public static void fromJson(JSONObject json) {
        DatabaseManager.getInstance().destroyAllForClass(CompanySetting.class);
        CompanySetting result = DatabaseManager.getInstance().getFirstOfClass(CompanySetting.class);
        boolean created = result == null;
        if (result == null) {
            result = new CompanySetting();
        }
        result.minOrder = json.optInt("min_order");
        result.vat = json.optInt("vat");
        result.helpLine = json.optString("help_line");
        result.sadadDelivery = json.optString("is_sadad_enable");
        result.curAndroidVer = json.optString("cur_android_ver");
        result.is_ticket_enable = json.optString("is_ticket_enable");
        result.stc_qitaf_msg_en = json.optString("stc_qitaf_msg_en");
        result.stc_qitaf_msg_ar = json.optString("stc_qitaf_msg_ar");
        result.show_vat = json.optString("show_vat");
        result.whats_app = json.optString("whats_app");
        result.email = json.optString("email");
        result.enable_other_channels = json.optString("enable_other_channels");
        result.show_category = json.optString("show_category");
        result.tc_ar = json.optString("tc_ar");
        result.tc_en = json.optString("tc_en");
        result.facebook = json.optString("facebook");
        result.instagram = json.optString("instagram");
        result.snapchat = json.optString("snapchat");
        result.twitter = json.optString("twitter");
        result.after_sale_cities = json.optString("after_sale_cities");

        if (created) result.create();
        else result.update();

    }
}
