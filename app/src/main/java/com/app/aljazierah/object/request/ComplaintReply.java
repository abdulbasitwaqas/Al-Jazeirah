package com.app.aljazierah.object.request;

public class ComplaintReply {

    String client_id;
    String ticket_id;
    String message;
    String file_attachment;

    public ComplaintReply(String client_id, String ticket_id, String message, String file_attachment) {
        this.client_id = client_id;
        this.ticket_id = ticket_id;
        this.message = message;
        this.file_attachment = file_attachment;
    }

}
