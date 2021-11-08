package com.app.aljazierah.object.request;

public class UpdateUsername {
    private String client_id;
    private String fullname;

    public UpdateUsername(String client_id, String fullname) {
        this.client_id = client_id;
        this.fullname = fullname;
    }
}
