package com.app.aljazierah.object.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mustanser Iqbal on 12/2/2017.
 */

public class Login implements Serializable {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("pass")
    @Expose
    private String pass;

    private final static long serialVersionUID = -9017617797369002618L;

    public Login() {
    }

    public Login(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
