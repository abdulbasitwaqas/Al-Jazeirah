package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Specifications {

    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("Lenght")
    @Expose
    private String lenght;
    @SerializedName("display")
    @Expose
    private String display;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("Specification_key_1")
    @Expose
    private String specificationKey1;
    @SerializedName("Specification_key_2")
    @Expose
    private String specificationKey2;
    @SerializedName("Specification_key_3")
    @Expose
    private String specificationKey3;
    @SerializedName("Specification_key_4")
    @Expose
    private String specificationKey4;
    @SerializedName("Specification_key_5")
    @Expose
    private String specificationKey5;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLenght() {
        return lenght;
    }

    public void setLenght(String lenght) {
        this.lenght = lenght;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSpecificationKey1() {
        return specificationKey1;
    }

    public void setSpecificationKey1(String specificationKey1) {
        this.specificationKey1 = specificationKey1;
    }

    public String getSpecificationKey2() {
        return specificationKey2;
    }

    public void setSpecificationKey2(String specificationKey2) {
        this.specificationKey2 = specificationKey2;
    }

    public String getSpecificationKey3() {
        return specificationKey3;
    }

    public void setSpecificationKey3(String specificationKey3) {
        this.specificationKey3 = specificationKey3;
    }

    public String getSpecificationKey4() {
        return specificationKey4;
    }

    public void setSpecificationKey4(String specificationKey4) {
        this.specificationKey4 = specificationKey4;
    }

    public String getSpecificationKey5() {
        return specificationKey5;
    }

    public void setSpecificationKey5(String specificationKey5) {
        this.specificationKey5 = specificationKey5;
    }

}