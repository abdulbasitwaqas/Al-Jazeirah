package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategoriesModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("parent_id")
    @Expose
    private String parent_id;

    @SerializedName("name_en")
    @Expose
    private String name_en;

    @SerializedName("name_ar")
    @Expose
    private String name_ar="";

    @SerializedName("description_en")
    @Expose
    private String description_en="";

    @SerializedName("description_ar")
    @Expose
    private String description_ar="";

    private boolean isSelected = false;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName_ar() {
        return name_ar;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }

    public String getDescription_en() {
        return description_en;
    }

    public void setDescription_en(String description_en) {
        this.description_en = description_en;
    }

    public String getDescription_ar() {
        return description_ar;
    }

    public void setDescription_ar(String description_ar) {
        this.description_ar = description_ar;
    }

    public boolean getisSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
