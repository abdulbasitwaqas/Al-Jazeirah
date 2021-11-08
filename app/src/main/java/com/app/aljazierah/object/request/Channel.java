package com.app.aljazierah.object.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mustanser Iqbal on 12/2/2017.
 */

public class Channel implements Serializable
{

    @SerializedName("type_id")
    @Expose
    private String typeId;
    private final static long serialVersionUID = -2137915367826126202L;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

}
