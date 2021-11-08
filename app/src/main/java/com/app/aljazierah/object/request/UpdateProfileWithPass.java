package com.app.aljazierah.object.request;

import java.io.Serializable;



public class UpdateProfileWithPass implements Serializable {

    private String user_id, first_name, new_password,old_password,email, is_subscribe;

    public UpdateProfileWithPass(String user_id, String first_name,String email, String old_password,String new_password, String is_subscribe) {
        this.first_name = first_name;
        this.email = email;
        this.user_id = user_id;
        this.old_password = old_password;
        this.new_password = new_password;
        this.is_subscribe = is_subscribe;
    }
}
