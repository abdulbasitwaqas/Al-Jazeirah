package com.app.aljazierah.object.request;

public class AddComplaint {

   String client_id;
     String order_code;
     String callcenter_user;
     String category;
     String ticket_text;
     String batch_in;
     String quantity_in;
     String file_attachment;
     String sku_in;

    public AddComplaint(String client_id, String order_code, String callcenter_user, String category, String ticket_text,
                         String batch_in, String quantity_in, String image, String sku_in){

        this.client_id=client_id;
        this.order_code=order_code;
        this.callcenter_user=callcenter_user;
        this.category=category;
        this.ticket_text=ticket_text;
        this.batch_in=batch_in;
        this.quantity_in=quantity_in;
        this.file_attachment=image;
        this.sku_in=sku_in;

    }

}
