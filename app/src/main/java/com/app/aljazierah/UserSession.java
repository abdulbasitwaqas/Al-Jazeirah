package com.app.aljazierah;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class UserSession {
    private static final UserSession instance = new UserSession();
    private SharedPreferences sp;
    private Editor spEditor;
    private String is_subscribe="";


    public static synchronized UserSession getInstance() {
        return instance;
    }

    public void setContext(Context context) {
        this.sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public UserSession(Context context) {
        this.sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public UserSession() {
    }

    public boolean setUserLanguage(String language) {
        spEditor = sp.edit();
        spEditor.putString("user_language", language);
        spEditor.commit();
        return true;
    }

    public String getUserLanguage() {
        return sp.getString("user_language", "ar");
    }


    public boolean setStartDate(String startDate) {
        spEditor = sp.edit();
        spEditor.putString("login_startDate", startDate);
        spEditor.commit();
        return true;
    }

    public String getStartDate() {
        return sp.getString("login_startDate", "");
    }

    public String getCityId() {
        return sp.getString("city_id", "0");
    }

    public boolean setToken(String language) {
        spEditor = sp.edit();
        spEditor.putString("token", language);
        spEditor.commit();
        return true;
    }

    public String getToken() {
        return sp.getString("token", "");
    }

    public boolean setLastAreaUpdatedDate(String token) {
        spEditor = sp.edit();
        spEditor.putString("LastAreaUpdatedDate", token);
        spEditor.commit();
        return true;
    }
    public String getLastAreaUpdatedDate() {
        return sp.getString("LastAreaUpdatedDate", "");
    }

    public boolean setSaveHomeAddressObject(String addressObject) {
        spEditor = sp.edit();
        spEditor.putString("addressObject", addressObject);
        spEditor.commit();
        return true;
    }
    public String getSaveAddressObject() {
        return sp.getString("addressObject", "");
    }



    public boolean setBankID(String bankID) {
        spEditor = sp.edit();
        spEditor.putString("bankID", bankID);
        spEditor.commit();
        return true;
    }
    public String getBankID() {
        return sp.getString("bankID", "0");
    }



    public boolean setIsSubscribe(String is_subscribe) {
        spEditor = sp.edit();
        spEditor.putString("is_subscribe", is_subscribe);
        spEditor.commit();
        return true;
    }
    public String getIsSubscribe() {
        return sp.getString("is_subscribe", "0");
    }



}
