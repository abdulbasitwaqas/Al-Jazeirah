package com.app.aljazierah.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductByLocation implements Serializable {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("img")
    @Expose
    private List<String> img = new ArrayList<>();

    @SerializedName("cat_ar")
    @Expose
    private String catAr;
    @SerializedName("cat_en")
    @Expose
    private String catEn;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("material")
    @Expose
    private String material;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("name_ar")
    @Expose
    private String nameAr;

    @SerializedName("name_en")
    @Expose
    private String nameEn;

    @SerializedName("description_ar")
    @Expose
    private String descriptionAr;
    @SerializedName("description_en")
    @Expose
    private String descriptionEn;

    @SerializedName("product_sort_app")
    @Expose
    private String productSortApp;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("stock")
    @Expose
    private String stock;


    @SerializedName("category_id")
    @Expose
    private String category_id;

    @SerializedName("is_banner")
    @Expose
    private String is_banner;

    @SerializedName("prod_detail_en")
    @Expose
    private String prod_detail_en;

    @SerializedName("prod_detail_ar")
    @Expose
    private String prod_detail_ar;


    @SerializedName("price_vat")
    @Expose
    private String price_vat;



    @SerializedName("before_discount")
    @Expose
    private String before_discount;
    @SerializedName("show_index")
    @Expose
    private String show_index;

    public String getIs_wish_list_item() {
        return is_wish_list_item;
    }

    public void setIs_wish_list_item(String is_wish_list_item) {
        this.is_wish_list_item = is_wish_list_item;
    }

    public String getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(String catalogue) {
        this.catalogue = catalogue;
    }

    public String getInfo_url() {
        return info_url;
    }

    public void setInfo_url(String info_url) {
        this.info_url = info_url;
    }

    @SerializedName("is_wish_list_item")
    @Expose
    private String is_wish_list_item;
    @SerializedName("catalogue")
    @Expose
    private String catalogue;

    public String getProduct_share_link() {
        return product_share_link;
    }

    public void setProduct_share_link(String product_share_link) {
        this.product_share_link = product_share_link;
    }

    @SerializedName("info_url")
    @Expose
    private String info_url;
    @SerializedName("product_share_link")
    @Expose
    private String product_share_link;

    public Specifications getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Specifications specifications) {
        this.specifications = specifications;
    }

    @SerializedName("Specifications")
    @Expose
    private Specifications specifications;


    @SerializedName("prod_rating")
    @Expose
    private String prod_rating;

    @SerializedName("best_selling")
    @Expose
    private String best_selling;

    public String getBest_selling() {
        return best_selling;
    }

    public void setBest_selling(String best_selling) {
        this.best_selling = best_selling;
    }

    public String getBefore_discount() {
        return before_discount;
    }

    public void setBefore_discount(String before_discount) {
        this.before_discount = before_discount;
    }

    public String getPrice_vat() {
        return price_vat;
    }

    public void setPrice_vat(String price_vat) {
        this.price_vat = price_vat;
    }

    public String getProd_detail_en() {
        return prod_detail_en;
    }

    public void setProd_detail_en(String prod_detail_en) {
        this.prod_detail_en = prod_detail_en;
    }

    public String getProd_detail_ar() {
        return prod_detail_ar;
    }

    public void setProd_detail_ar(String prod_detail_ar) {
        this.prod_detail_ar = prod_detail_ar;
    }

    private int count = 0;
    private boolean isAddedToCart;
    private final static long serialVersionUID = -4214695899142089859L;
    //public boolean promotion_available=false;

    //public String base_quantity="";
    //public String free_items="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getProd_rating() {
        return prod_rating;
    }

    public void setProd_rating(String prod_rating) {
        this.prod_rating = prod_rating;
    }

    public String getCatAr() {
        return catAr;
    }

    public void setCatAr(String catAr) {
        this.catAr = catAr;
    }

    public String getCatEn() {
        return catEn;
    }

    public void setCatEn(String catEn) {
        this.catEn = catEn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getProductSortApp() {
        return productSortApp;
    }

    public void setProductSortApp(String productSortApp) {
        this.productSortApp = productSortApp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getIs_banner() {
        return is_banner;
    }

    public void setIs_banner(String is_banner) {
        this.is_banner = is_banner;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
    public void setAddedToCart(boolean addedToCart) {
        isAddedToCart = addedToCart;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getShow_index() {
        return show_index;
    }

    public void setShow_index(String show_index) {
        this.show_index = show_index;
    }

    public boolean isAddedToCart() {
        return isAddedToCart;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }
}