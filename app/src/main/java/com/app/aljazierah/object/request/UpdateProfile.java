package com.app.aljazierah.object.request;

import java.io.Serializable;

/**
 * Created by Mustanser Iqbal on 12/2/2017.
 */

public class UpdateProfile implements Serializable {

    private String user_id, frist_name, last_name, email, mobile;

    public UpdateProfile(String user_id, String frist_name, String last_name, String email, String mobile) {
        this.user_id = user_id;
        this.frist_name = frist_name;
        this.last_name = last_name;
        this.email = email;
        this.mobile = mobile;
    }
}
