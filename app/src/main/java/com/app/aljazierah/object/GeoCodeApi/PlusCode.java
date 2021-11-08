
package com.app.aljazierah.object.GeoCodeApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlusCode {

    @SerializedName("global_code")
    @Expose
    private String globalCode;

    public String getGlobalCode() {
        return globalCode;
    }

    public void setGlobalCode(String globalCode) {
        this.globalCode = globalCode;
    }

}
