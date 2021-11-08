package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {

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

    @SerializedName("sub_categories")
    @Expose
    private List<SubCategoriesModel> subCategoriesModelList;


    private boolean is_shown=true;
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

    public List<SubCategoriesModel> getSubCategoriesModelList() {
        return subCategoriesModelList;
    }

    public void setSubCategoriesModelList(List<SubCategoriesModel> subCategoriesModelList) {
        this.subCategoriesModelList = subCategoriesModelList;
    }

    public boolean getIs_shown() {
        return is_shown;
    }

    public void setIs_shown(boolean is_shown) {
        this.is_shown = is_shown;
    }
}
