package com.app.aljazierah.object.request;

public class GetComplaintDetails {
    String client_id;
    String ticket_id;

    public GetComplaintDetails(String client_id,String ticket_id){

        this.client_id=client_id;
        this.ticket_id=ticket_id;
    }

}
