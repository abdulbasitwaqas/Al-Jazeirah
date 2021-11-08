package com.app.aljazierah.object.request;

/**
 * Created by Mustanser Iqbal on 12/5/2017.
 */

public class DeleteAddress {

    private String user_id;
    private String id;

    public DeleteAddress(String user_id, String id) {
        this.user_id = user_id;
        this.id = id;
    }
}
